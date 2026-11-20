package net.minecraft.client.renderer.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import optifine.BlockPosM;
import optifine.OptiFineConfig;
import optifine.Reflector;
import optifine.ReflectorForge;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.XrayModule;

import shadersmod.client.SVertexBuilder;

import java.nio.FloatBuffer;
import java.util.concurrent.locks.ReentrantLock;

public class RenderChunk {
    private static boolean legacyChunkUpdateFlow;
    private World world;
    private int frameIndex;
    private boolean playerUpdatePending;
    private int index;
    private VertexBuffer[] vertexBuffers;
    private BlockPos position;
    private ReentrantLock lockCompiledChunk;
    public static int renderChunksUpdated;
    private boolean isMipmaps;
    public BoundingBox boundingBox;
    public CompiledChunk compiledChunk;
    private FloatBuffer modelviewMatrix;
    private EnumWorldBlockLayer[] blockLayersSingle;
    private boolean needsUpdate;
    private BlockPos[] positionOffsets16;
    private ReentrantLock lockCompileTask;
    private boolean fixBlockLayers;
    private ChunkCompileTaskGenerator compileTask;
    private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
    private RenderGlobal renderGlobal;

    public boolean setFrameIndex(int frameIndexValue) {
        if (this.frameIndex == frameIndexValue) {
            return false;
        }

        this.frameIndex = frameIndexValue;
        return true;
    }

    public BlockPos getPositionOffset16(Facing facing) {
        int index = facing.getIndex();
        BlockPos blockPos = this.positionOffsets16[index];
        if (blockPos == null) {
            blockPos = this.getPosition().offset(facing, 16);
            this.positionOffsets16[index] = blockPos;
        }

        return blockPos;
    }

    public ReentrantLock getLockCompileTask() {
        return this.lockCompileTask;
    }

    private void initModelviewMatrix() {
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        float fx = 1.000001F;
        GlStateManager.translate(-8.0F, -8.0F, -8.0F);
        GlStateManager.scale(fx, fx, fx);
        GlStateManager.translate(8.0F, 8.0F, 8.0F);
        GlStateManager.getFloat(2982, this.modelviewMatrix);
        GlStateManager.popMatrix();
    }

    protected RegionRenderCache createRegionRenderCache(
            World world, BlockPos blockPos, BlockPos blockPosValue, int i) {
        return new RegionRenderCache(world, blockPos, blockPosValue, i);
    }

    public static boolean isLegacyChunkUpdateFlow() {
        return net.minecraft.client.renderer.chunk.RenderChunk.legacyChunkUpdateFlow;
    }

    public void resortTransparency(
            float f, float f1, float f2, ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        CompiledChunk compiledChunk = chunkCompileTaskGenerator.getCompiledChunk();
        if (compiledChunk.getState() != null
                && !compiledChunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
            WorldRenderer worldRenderer =
                    chunkCompileTaskGenerator
                            .getRegionRenderCacheBuilder()
                            .getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
            this.preRenderBlocks(worldRenderer, this.position);
            worldRenderer.setVertexState(compiledChunk.getState());
            this.postRenderBlocks(
                    EnumWorldBlockLayer.TRANSLUCENT, f, f1, f2, worldRenderer, compiledChunk);
        }
    }

    public void multModelviewMatrix() {
        GlStateManager.multMatrix(this.modelviewMatrix);
    }

    public boolean isNeedsUpdate() {
        return this.needsUpdate;
    }

    private void postRenderBlocks(
            EnumWorldBlockLayer enumWorldBlockLayer,
            float f,
            float f1,
            float f2,
            WorldRenderer worldRenderer,
            CompiledChunk compiledChunk) {
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT
                && !compiledChunk.isLayerEmpty(enumWorldBlockLayer)) {
            compiledChunk.setState(worldRenderer.getVertexState(f, f1, f2));
        }

        worldRenderer.draw();
    }

    protected void finishCompileTask() {
        this.lockCompileTask.lock();

        try {
            if (this.compileTask != null
                    && this.compileTask.getStatus() != ChunkCompileTaskGeneratorStatus.DONE) {
                this.compileTask.finish();
                this.compileTask = null;
            }
        } finally {
            this.lockCompileTask.unlock();
        }
    }

    public static void setLegacyChunkUpdateFlow(boolean flag) {
        net.minecraft.client.renderer.chunk.RenderChunk.legacyChunkUpdateFlow = flag;
    }

    private void preRenderBlocks(WorldRenderer worldRenderer, BlockPos blockPos) {
        worldRenderer.startDrawing(7);
        worldRenderer.setVertexFormat(DefaultVertexFormats.BLOCK);
        worldRenderer.setTranslation(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
    }

    public RenderChunk(World world, RenderGlobal renderGlobal, BlockPos blockPos, int i) {
        super();
        boolean initialFlowFlag = usesNormalChunkUpdateFlow();

        boolean flag1 = initialFlowFlag;

        this.positionOffsets16 = new BlockPos[Facing.VALUES.length];
        this.blockLayersSingle = new EnumWorldBlockLayer[1];
        boolean flag = flag1;
        this.isMipmaps = OptiFineConfig.dL();
        this.fixBlockLayers =
                remix.compat.compiler.JvmBooleanSlots.fromInt((!Reflector.Ip.exists() ? 1 : 0));
        this.playerUpdatePending = false;
        this.compiledChunk = CompiledChunk.DUMMY;
        this.lockCompileTask = new ReentrantLock();
        this.lockCompiledChunk = new ReentrantLock();
        this.compileTask = null;
        this.modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
        this.vertexBuffers = new VertexBuffer[EnumWorldBlockLayer.values().length];
        this.frameIndex = -1;
        this.needsUpdate = true;
        this.world = world;
        this.renderGlobal = renderGlobal;
        this.index = i;
        if (!blockPos.equals(this.getPosition())) {
            this.setPosition(blockPos);
        }

        if (OpenGlHelper.useVbo()) {
            int j = 0;

            while (j < EnumWorldBlockLayer.values().length) {
                this.vertexBuffers[j] = new VertexBuffer(DefaultVertexFormats.BLOCK);
                j++;
                if (!flag) {
                    Entity.setLegacyGlobalFlowMarker(new String[2]);
                    break;
                }
            }
        }
    }

    private EnumWorldBlockLayer fixBlockLayer(
            Block block, EnumWorldBlockLayer enumWorldBlockLayer) {
        if (this.isMipmaps) {
            if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT) {
                if (block instanceof BlockRedstoneWire) {
                    return enumWorldBlockLayer;
                }

                if (block instanceof BlockCactus) {
                    return enumWorldBlockLayer;
                }

                return EnumWorldBlockLayer.CUTOUT_MIPPED;
            }
        } else if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT_MIPPED) {
            return EnumWorldBlockLayer.CUTOUT;
        }

        return enumWorldBlockLayer;
    }

    public CompiledChunk getCompiledChunk() {
        return this.compiledChunk;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public void setPosition(BlockPos aBtValue) {
        this.stopCompileTask();
        boolean flag1 = usesNormalChunkUpdateFlow();
        this.position = aBtValue;
        this.boundingBox = new BoundingBox(aBtValue, aBtValue.add(16, 16, 16));
        boolean flag = flag1;
        this.initModelviewMatrix();
        int i = 0;

        while (i < this.positionOffsets16.length) {
            this.positionOffsets16[i] = null;
            i++;
            if (!flag) {
                break;
            }
        }
    }

    public VertexBuffer getVertexBufferByLayer(int i) {
        return this.vertexBuffers[i];
    }

    public void deleteGlResources() {
        boolean flag1 = usesNormalChunkUpdateFlow();
        this.stopCompileTask();
        boolean flag = flag1;
        this.world = null;
        int i = 0;

        while (i < EnumWorldBlockLayer.values().length) {
            if (this.vertexBuffers[i] != null) {
                this.vertexBuffers[i].deleteGlBuffers();
            }

            i++;
            if (!flag) {
                break;
            }
        }
    }

    public boolean hasPendingPlayerUpdate() {
        return this.playerUpdatePending;
    }

    public static boolean usesNormalChunkUpdateFlow() {
        boolean flag = isLegacyChunkUpdateFlow();
        return !flag;
    }

    static {
        setLegacyChunkUpdateFlow(false);
    }

    private boolean isWorldPlayerUpdate() {
        if (this.world instanceof ClientWorld) {
            ClientWorld aBrValue = (ClientWorld) this.world;
            return aBrValue.isPlayerUpdate();
        } else {
            return false;
        }
    }

    public void setNeedsUpdate(boolean flag) {
        boolean flag2 = usesNormalChunkUpdateFlow();
        this.needsUpdate = flag;
        boolean flag1 = flag2;
        if (this.needsUpdate) {
            if (!this.isWorldPlayerUpdate()) {
                return;
            }

            this.playerUpdatePending = true;
            if (flag1) {
                return;
            }
        }

        this.playerUpdatePending = false;
    }

    public void rebuildChunk(
            float cameraX, float cameraY, float cameraZ, ChunkCompileTaskGenerator task) {
        CompiledChunk compiledChunk = new CompiledChunk();
        boolean compileGeometry = usesNormalChunkUpdateFlow();
        BlockPos origin = this.position;
        BlockPos corner = origin.add(15, 15, 15);

        task.getLock().lock();
        RegionRenderCache cache;
        try {
            if (task.getStatus() != ChunkCompileTaskGeneratorStatus.COMPILING
                    || this.world == null) {
                return;
            }
            cache =
                    this.createRegionRenderCache(
                            this.world, origin.add(-1, -1, -1), corner.add(1, 1, 1), 1);
            if (Reflector.MinecraftForgeClient_onRebuildChunk.exists()) {
                Reflector.call(
                        Reflector.MinecraftForgeClient_onRebuildChunk,
                        new Object[] {this.world, this.position, cache});
            }
            task.setCompiledChunk(compiledChunk);
        } finally {
            task.getLock().unlock();
        }

        if (!compileGeometry) {
            return;
        }
        VisGraph visibility = new VisGraph();
        if (!cache.extendedLevelsInChunkCache()) {
            renderChunksUpdated++;
            Reflector.ForgeBlock_hasTileEntity.exists();
            boolean hasForgeLayerPredicate = Reflector.forgeCanRenderBlockInLayer.exists();
            boolean hasForgeLayerSetter = Reflector.forgeSetRenderLayer.exists();

            for (Object entry : BlockPosM.getAllInBoxPosM(origin, corner)) {
                BlockPosM blockPos = (BlockPosM) entry;
                BlockState state = cache.getBlockState(blockPos);
                Block block = state.getBlock();
                if (block.isOpaqueCube()) {
                    visibility.markOpaque(blockPos);
                }

                this.collectChunkTileEntity(cache, blockPos, state, compiledChunk);
                EnumWorldBlockLayer[] layers =
                        hasForgeLayerPredicate
                                ? ENUM_WORLD_BLOCK_LAYERS
                                : this.getFallbackBlockLayers(block);
                for (EnumWorldBlockLayer layer : layers) {
                    if (hasForgeLayerPredicate
                            && !Reflector.callBoolean(
                                    block,
                                    Reflector.forgeCanRenderBlockInLayer,
                                    new Object[] {layer})) {
                        continue;
                    }
                    if (hasForgeLayerSetter) {
                        Reflector.callVoid(Reflector.forgeSetRenderLayer, new Object[] {layer});
                    }
                    EnumWorldBlockLayer renderLayer =
                            this.fixBlockLayers ? this.fixBlockLayer(block, layer) : layer;
                    this.compileBlockLayer(
                            task,
                            compiledChunk,
                            cache,
                            origin,
                            blockPos,
                            state,
                            block,
                            renderLayer);
                }
            }

            for (EnumWorldBlockLayer layer : ENUM_WORLD_BLOCK_LAYERS) {
                if (!compiledChunk.isLayerStarted(layer)) {
                    continue;
                }
                WorldRenderer vertices =
                        task.getRegionRenderCacheBuilder().getWorldRendererByLayer(layer);
                if (OptiFineConfig.isShaders()) {
                    SVertexBuilder.calcNormalChunkLayer(vertices);
                }
                this.postRenderBlocks(layer, cameraX, cameraY, cameraZ, vertices, compiledChunk);
            }
        }
        compiledChunk.setVisibility(visibility.computeVisibility());
    }

    private void collectChunkTileEntity(
            RegionRenderCache cache,
            BlockPos position,
            BlockState state,
            CompiledChunk compiledChunk) {
        if (ReflectorForge.blockHasTileEntity(state)) {
            TileEntity tileEntity = cache.getTileEntity(new BlockPos(position));
            if (tileEntity != null
                    && TileEntityRendererDispatcher.instance.hasSpecialRenderer(tileEntity)) {
                compiledChunk.addTileEntity(tileEntity);
            }
        }
    }

    private EnumWorldBlockLayer[] getFallbackBlockLayers(Block block) {
        this.blockLayersSingle[0] = block.getBlockLayer();
        RemixClient.INSTANCE.getClientServices();
        XrayModule xray = ClientServices.moduleManager.xray;
        if (xray.isEnabled() && !xray.isVisibleBlock(block)) {
            this.blockLayersSingle[0] = EnumWorldBlockLayer.TRANSLUCENT;
        }
        return this.blockLayersSingle;
    }

    private void compileBlockLayer(
            ChunkCompileTaskGenerator task,
            CompiledChunk compiledChunk,
            RegionRenderCache cache,
            BlockPos origin,
            BlockPos blockPos,
            BlockState state,
            Block block,
            EnumWorldBlockLayer layer) {
        if (block.getRenderType() == -1) {
            return;
        }
        WorldRenderer vertices =
                task.getRegionRenderCacheBuilder().getWorldRendererByLayerId(layer.ordinal());
        vertices.setBlockLayer(layer);
        if (!compiledChunk.isLayerStarted(layer)) {
            compiledChunk.setLayerStarted(layer);
            this.preRenderBlocks(vertices, origin);
        }
        if (MinecraftClient.getMinecraft()
                .getBlockRendererDispatcher()
                .renderBlock(state, blockPos, cache, vertices)) {
            compiledChunk.setLayerUsed(layer);
        }
    }

    public ChunkCompileTaskGenerator createChunkRebuildTask() {
        this.lockCompileTask.lock();

        ChunkCompileTaskGenerator chunkCompileTaskGenerator;
        try {
            this.finishCompileTask();
            this.compileTask =
                    new ChunkCompileTaskGenerator(
                            this, ChunkCompileTaskGeneratorType.REBUILD_CHUNK);
            chunkCompileTaskGenerator = this.compileTask;
        } finally {
            this.lockCompileTask.unlock();
        }

        return chunkCompileTaskGenerator;
    }

    public ChunkCompileTaskGenerator createTransparencyResortTask() {
        this.lockCompileTask.lock();

        ChunkCompileTaskGenerator chunkCompileTaskGenerator;
        try {
            if (this.compileTask != null
                    && this.compileTask.getStatus() == ChunkCompileTaskGeneratorStatus.PENDING) {
                return null;
            }

            if (this.compileTask != null
                    && this.compileTask.getStatus() != ChunkCompileTaskGeneratorStatus.DONE) {
                this.compileTask.finish();
                this.compileTask = null;
            }

            this.compileTask =
                    new ChunkCompileTaskGenerator(
                            this, ChunkCompileTaskGeneratorType.RESORT_TRANSPARENCY);
            this.compileTask.setCompiledChunk(this.compiledChunk);
            ChunkCompileTaskGenerator aBAValue = this.compileTask;
            chunkCompileTaskGenerator = aBAValue;
        } finally {
            this.lockCompileTask.unlock();
        }

        return chunkCompileTaskGenerator;
    }

    public void setCompiledChunk(CompiledChunk aBwValue) {
        this.lockCompiledChunk.lock();

        try {
            this.compiledChunk = aBwValue;
        } finally {
            this.lockCompiledChunk.unlock();
        }
    }

    public void stopCompileTask() {
        this.finishCompileTask();
        this.compiledChunk = CompiledChunk.DUMMY;
    }

    public boolean isCompileTaskAbsentOrPending() {
        this.lockCompileTask.lock();

        int i;
        try {
            i =
                    this.compileTask != null
                                    && this.compileTask.getStatus()
                                            != ChunkCompileTaskGeneratorStatus.PENDING
                            ? 0
                            : 1;
        } finally {
            this.lockCompileTask.unlock();
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(i);
    }
}
