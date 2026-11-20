package net.minecraft.client.multiplayer;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.particle.EntityFireworkStarterFX;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveDataMemoryStorage;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraft.world.storage.WorldInfo;

import optifine.BlockPosM;
import optifine.DynamicLights;
import optifine.OptiFineConfig;
import optifine.PlayerControllerOF;
import optifine.Reflector;

import remix.input.KeyAction;
import remix.input.KeyBindingDispatcher;

import java.util.Random;
import java.util.Set;

public class ClientWorld extends World {
    private boolean playerUpdate;
    private Set previousActiveChunkSet;

    private BlockPosM randomTickPosM;
    private MinecraftClient mc;
    private ChunkProviderClient clientChunkProvider;
    public ClientPlayHandler sendQueue;
    private Set entityList;
    private static Entity[] legacyClientWorldFlowState;
    private Set entitySpawnQueue;

    public void tick() {
        super.tick();
        Entity[] entityArray = getLegacyClientWorldFlowState();
        long k = this.getTotalWorldTime();

        this.setTotalWorldTime(k + 1L);
        Entity[] entityArrayValue = entityArray;
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            long j = this.getWorldTime();

            this.setWorldTime(j + 1L);
        }

        this.theProfiler.startSection("reEntryProcessing");
        int i = 0;

        while (i < 10 && !this.entitySpawnQueue.isEmpty()) {
            Entity entity = (Entity) this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(entity);
            if (!this.loadedEntities.contains(entity)) {
                this.spawnEntityInWorld(entity);
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        this.theProfiler.endStartSection("chunkCache");
        this.clientChunkProvider.unloadQueuedChunks();
        this.theProfiler.endStartSection("blocks");
        this.updateBlocks();
        this.theProfiler.endSection();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyClientWorldFlowState(new Entity[1]);
        }
    }

    public void setWorldScoreboard(Scoreboard bFTValue) {
        this.worldScoreboard = bFTValue;
    }

    // region String decoding support

    // endregion

    static MinecraftClient access$200(ClientWorld clientWorld) {
        return clientWorld.mc;
    }

    public boolean setBlockState(BlockPos blockPos, BlockState blockState, int i) {
        this.playerUpdate = this.isPlayerActing();
        boolean flag = super.setBlockState(blockPos, blockState, i);
        this.playerUpdate = false;
        return flag;
    }

    static Set access$000(ClientWorld clientWorld) {
        return clientWorld.entityList;
    }

    private boolean isPlayerActing() {
        if (this.mc.playerController instanceof PlayerControllerOF) {
            PlayerControllerOF playerController = (PlayerControllerOF) this.mc.playerController;
            return playerController.isActing();
        } else {
            return false;
        }
    }

    public static void setLegacyClientWorldFlowState(Entity[] aOtValue) {
        net.minecraft.client.multiplayer.ClientWorld.legacyClientWorldFlowState = aOtValue;
    }

    public ClientWorld(
            ClientPlayHandler clientPlayHandler,
            WorldSettings worldSettings,
            int i,
            EnumDifficulty enumDifficulty,
            Profiler profiler) {
        this(
                clientPlayHandler,
                worldSettings,
                i,
                enumDifficulty,
                profiler,
                getLegacyClientWorldFlowState());
    }

    private ClientWorld(
            ClientPlayHandler aOrValue,
            WorldSettings worldSettings,
            int i,
            EnumDifficulty enumDifficulty,
            Profiler profiler,
            Entity[] initialFlowMarker) {
        super(
                new SaveHandlerMP(),
                new WorldInfo(worldSettings, "MpServer"),
                WorldProvider.getProviderForDimension(i),
                profiler,
                true);

        Entity[] entityArray = initialFlowMarker;

        Entity[] entityArrayValue = entityArray;
        this.entityList = Sets.newHashSet();
        this.entitySpawnQueue = Sets.newHashSet();
        this.mc = MinecraftClient.getMinecraft();
        this.previousActiveChunkSet = Sets.newHashSet();
        this.randomTickPosM = new BlockPosM(0, 0, 0, 3);
        this.playerUpdate = false;
        this.sendQueue = aOrValue;
        this.getWorldInfo().setDifficulty(enumDifficulty);
        this.provider.registerWorld(this);
        this.setSpawnLocation(new BlockPos(8, 64, 8));
        this.chunkProvider = this.createChunkProvider();
        this.mapStorage = new SaveDataMemoryStorage();
        this.updateSkylightSubtracted();
        this.calculateInitialWeather();
        Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] {this});
        if (this.mc.playerController != null
                && this.mc.playerController.getClass() == PlayerController.class) {
            this.mc.playerController = new PlayerControllerOF(this.mc, aOrValue);
        }

        if (entityArrayValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    protected void onEntityAdded(Entity entity) {
        super.onEntityAdded(entity);
        if (this.entitySpawnQueue.contains(entity)) {
            this.entitySpawnQueue.remove(entity);
        }
    }

    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
    }

    protected int getRenderDistanceChunks() {
        return this.mc.gameSettings.renderDistanceChunks;
    }

    protected void updateBlocks() {
        super.updateBlocks();
        Entity[] entityArray = getLegacyClientWorldFlowState();
        this.previousActiveChunkSet.retainAll(this.activeChunkSet);
        Entity[] entityArrayValue = entityArray;
        if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
            this.previousActiveChunkSet.clear();
        }

        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_8284 = (this.activeChunkSet).iterator();
                (__recoveryIterator171_8284).hasNext(); ) {
            ChunkCoordIntPair chunkCoordIntPair =
                    (ChunkCoordIntPair) (__recoveryIterator171_8284).next();
            if (!this.previousActiveChunkSet.contains(chunkCoordIntPair)) {
                int j = chunkCoordIntPair.chunkXPos * 16;
                int k = chunkCoordIntPair.chunkZPos * 16;
                this.theProfiler.startSection("getChunk");
                Chunk chunkFromChunkCoords =
                        this.getChunkFromChunkCoords(
                                chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
                this.playMoodSoundAndCheckLight(j, k, chunkFromChunkCoords);
                this.theProfiler.endSection();
                this.previousActiveChunkSet.add(chunkCoordIntPair);
                if (++i >= 10) {
                    return;
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }
    }

    public boolean invalidateRegionAndSetBlock(BlockPos blockPos, BlockState blockState) {
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        this.invalidateBlockReceiveRegion(x, j, k, x, j, k);
        return super.setBlockState(blockPos, blockState, 3);
    }

    public void playSound(
            double d0, double d1, double d2, String text, float f, float f1, boolean flag) {
        double distanceSq = this.mc.getRenderViewEntity().getDistanceSq(d0, d1, d2);
        Entity[] entityArray = getLegacyClientWorldFlowState();
        PositionedSoundRecord positionedSoundRecord =
                new PositionedSoundRecord(
                        new ResourceLocation(text), f, f1, (float) d0, (float) d1, (float) d2);
        Entity[] entityArrayValue = entityArray;
        if (flag && distanceSq > 100.0) {
            double d4 = Math.sqrt(distanceSq) / 40.0;
            this.mc.getSoundHandler().playDelayedSound(positionedSoundRecord, (int) (d4 * 20.0));
            if (entityArrayValue == null) {
                return;
            }
        }

        this.mc.getSoundHandler().playSound(positionedSoundRecord);
    }

    public void addEntityToWorld(int i, Entity entity) {
        Entity entityValue = this.getEntityByID(i);
        if (entityValue != null) {
            this.removeEntity(entityValue);
        }

        this.entityList.add(entity);
        entity.setEntityId(i);
        if (!this.spawnEntityInWorld(entity)) {
            this.entitySpawnQueue.add(entity);
        }

        this.entitiesById.addKey(i, entity);
    }

    public void playSoundAtPos(BlockPos blockPos, String text, float f, float f1, boolean flag) {
        this.playSound(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                text,
                f,
                f1,
                flag);
    }

    static Set access$100(ClientWorld clientWorld) {
        return clientWorld.entitySpawnQueue;
    }

    public Entity removeEntityFromWorld(int i) {
        Entity entity = (Entity) this.entitiesById.removeObject(i);
        if (entity != null) {
            this.entityList.remove(entity);
            this.removeEntity(entity);
        }

        return entity;
    }

    public void invalidateBlockReceiveRegion(int i, int j, int k, int l, int i1, int j1) {}

    public void doVoidFogParticles(int i, int j, int k) {
        Entity[] entityArray = getLegacyClientWorldFlowState();
        byte b0 = 16;
        Entity[] entityArrayValue = entityArray;
        Random random = new Random();
        ItemStack heldItem = this.mc.player.getHeldItem();
        int l =
                this.mc.playerController.getCurrentGameType() == WorldSettingsGameType.CREATIVE
                                && heldItem != null
                                && Block.getBlockFromItem(heldItem.getItem()) == Blocks.barrier
                        ? 1
                        : 0;
        BlockPosM aOoValue = this.randomTickPosM;
        int i1 = 0;

        while (i1 < 1000) {
            int j1 = i + this.rand.nextInt(b0) - this.rand.nextInt(b0);
            int k1 = j + this.rand.nextInt(b0) - this.rand.nextInt(b0);
            int l1 = k + this.rand.nextInt(b0) - this.rand.nextInt(b0);
            aOoValue.setXyz(j1, k1, l1);
            BlockState blockState = this.getBlockState(aOoValue);
            blockState.getBlock().randomDisplayTick(this, aOoValue, blockState, random);
            if (l != 0 && blockState.getBlock() == Blocks.barrier) {
                this.spawnParticle(
                        EnumParticleTypes.BARRIER,
                        j1 + 0.5F,
                        k1 + 0.5F,
                        l1 + 0.5F,
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
            }

            i1++;
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    public boolean isPlayerUpdate() {
        return this.playerUpdate;
    }

    // region Static initialization and string tables
    static {
        setLegacyClientWorldFlowState(((net.minecraft.entity.Entity[]) (null)));
    }

    // endregion

    public boolean spawnEntityInWorld(Entity entity) {
        boolean flag = super.spawnEntityInWorld(entity);
        Entity[] entityArray = getLegacyClientWorldFlowState();
        this.entityList.add(entity);
        Entity[] entityArrayValue = entityArray;
        if (!flag) {
            this.entitySpawnQueue.add(entity);
            if (entityArrayValue == null) {
                return flag;
            }
        }

        if (entity instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart) entity));
        }

        return flag;
    }

    public void setWorldTime(long worldTime) {
        boolean legacyDaylightOverride = getLegacyClientWorldFlowState() != null;
        boolean daylightCycle = worldTime >= 0L;
        if (!daylightCycle) {
            worldTime = -worldTime;
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
        }
        if (daylightCycle || legacyDaylightOverride) {
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
        }
        super.setWorldTime(worldTime);
    }

    public void doPreChunk(int chunkX, int chunkZ, boolean load) {
        boolean legacyUnloadAfterLoad = getLegacyClientWorldFlowState() != null;
        if (KeyBindingDispatcher.noKeyActionDispatched
                || KeyAction.eligibilityCheckedAfterInputRead) {
            if (load) {
                this.clientChunkProvider.loadChunk(chunkX, chunkZ);
            }
            if (!load || legacyUnloadAfterLoad) {
                this.clientChunkProvider.unloadChunk(chunkX, chunkZ);
            }
        }

        if (!load) {
            this.markBlockRangeForRenderUpdate(
                    chunkX * 16, 0, chunkZ * 16, chunkX * 16 + 15, 256, chunkZ * 16 + 15);
        }
    }

    public void removeEntity(Entity entity) {
        super.removeEntity(entity);
        this.entityList.remove(entity);
    }

    public int getCombinedLight(BlockPos blockPos, int i) {
        int j = super.getCombinedLight(blockPos, i);
        if (OptiFineConfig.isDynamicLights()) {
            j = DynamicLights.getCombinedLight(blockPos, j);
        }

        return j;
    }

    protected void onEntityRemoved(Entity entity) {
        Entity[] entityArray = getLegacyClientWorldFlowState();
        super.onEntityRemoved(entity);
        Entity[] entityArrayValue = entityArray;

        if (this.entityList.contains(entity)) {
            if (entity.isEntityAlive()) {
                this.entitySpawnQueue.add(entity);

                if (entityArrayValue == null) {
                    return;
                }
            }

            this.entityList.remove(entity);
        }
    }

    public void removeAllEntities() {
        Entity[] entityArray = getLegacyClientWorldFlowState();
        this.loadedEntities.removeAll(this.unloadedEntityList);
        int i = 0;
        Entity[] entityArrayValue = entityArray;

        while (i < this.unloadedEntityList.size()) {
            Entity entity = (Entity) this.unloadedEntityList.get(i);
            int j = entity.chunkCoordX;
            int k = entity.chunkCoordZ;
            if (entity.addedToChunk && this.isChunkLoaded(j, k, true)) {
                this.getChunkFromChunkCoords(j, k).removeEntity(entity);
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        i = 0;

        while (i < this.unloadedEntityList.size()) {
            this.onEntityRemoved((Entity) this.unloadedEntityList.get(i));
            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        this.unloadedEntityList.clear();
        i = 0;

        while (i < this.loadedEntities.size()) {
            Entity entity = (Entity) this.loadedEntities.get(i);
            Entity mount = entity.ridingEntity;
            boolean keepValidMount =
                    mount != null
                            && !mount.isDead
                            && mount.riddenByEntity == entity
                            && entityArrayValue == null;
            if (!keepValidMount) {
                if (mount != null) {
                    mount.riddenByEntity = null;
                    entity.ridingEntity = null;
                }
                if (entity.isDead) {
                    int chunkX = entity.chunkCoordX;
                    int chunkZ = entity.chunkCoordZ;
                    if (entity.addedToChunk && this.isChunkLoaded(chunkX, chunkZ, true)) {
                        this.getChunkFromChunkCoords(chunkX, chunkZ).removeEntity(entity);
                    }
                    this.loadedEntities.remove(i--);
                    this.onEntityRemoved(entity);
                }
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public CrashReportCategory addWorldInfoToCrashReport(CrashReport crashReport) {
        CrashReportCategory crashReportCategory = super.addWorldInfoToCrashReport(crashReport);
        crashReportCategory.addCrashSectionCallable(
                "Forced entities", new ClientForcedEntitiesCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Retry entities", new ClientRetryEntitiesCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Server brand", new ClientBrandCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Server type", new ClientServerTypeCrashDetail(this));
        return crashReportCategory;
    }

    public Entity getEntityByID(int i) {
        return (Entity)
                (i == this.mc.player.getEntityId() ? this.mc.player : super.getEntityByID(i));
    }

    public static Entity[] getLegacyClientWorldFlowState() {
        return net.minecraft.client.multiplayer.ClientWorld.legacyClientWorldFlowState;
    }

    protected IChunkProvider createChunkProvider() {
        this.clientChunkProvider = new ChunkProviderClient(this);
        return this.clientChunkProvider;
    }

    public void makeFireworks(
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            CompoundTag compoundTag) {
        this.mc.effectRenderer.addEffect(
                new EntityFireworkStarterFX(
                        this, d0, d1, d2, d3, d4, d5, this.mc.effectRenderer, compoundTag));
    }

    protected void updateWeather() {}
}
