package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DynamicLight {
    private double offsetY;
    private double lastPosX;
    private Entity entity = null;
    private long timeCheckMs;
    private BlockPosM AP;
    private double lastPosZ;
    private int lastLightLevel;
    private double lastPosY;
    private boolean underwater;

    private Set<BlockPos> setLitChunkPos;

    public void update(RenderGlobal renderGlobal) {
        if (OptiFineConfig.isDynamicLightsFast()) {
            long timeCheckMsValue = System.currentTimeMillis();

            if (timeCheckMsValue < this.timeCheckMs + 500L) {
                return;
            }

            this.timeCheckMs = timeCheckMsValue;
        }

        double d6 = this.entity.posX - 0.5;
        double d0 = this.entity.posY - 0.5 + this.offsetY;
        double d1 = this.entity.posZ - 0.5;
        int j = DynamicLights.getLightLevel(this.entity);
        double d2 = d6 - this.lastPosX;
        double d3 = d0 - this.lastPosY;
        double d4 = d1 - this.lastPosZ;
        double d5 = 0.1;
        if (Math.abs(d2) > d5
                || Math.abs(d3) > d5
                || Math.abs(d4) > d5
                || this.lastLightLevel != j) {
            this.lastPosX = d6;
            this.lastPosY = d0;
            this.lastPosZ = d1;
            this.lastLightLevel = j;
            this.underwater = false;
            ClientWorld clientWorld = renderGlobal.getWorld();
            if (clientWorld != null) {
                this.AP.setXyz(MathHelper.floor(d6), MathHelper.floor(d0), MathHelper.floor(d1));
                BlockState blockState = clientWorld.getBlockState(this.AP);
                Block block = blockState.getBlock();
                this.underwater =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (block == Blocks.water ? 1 : 0));
            }

            HashSet hashset = new HashSet();
            if (j > 0) {
                Facing facing = (MathHelper.floor(d6) & 15) >= 8 ? Facing.EAST : Facing.WEST;
                Facing facingValue = (MathHelper.floor(d0) & 15) >= 8 ? Facing.UP : Facing.DOWN;
                Facing facingValue2 =
                        (MathHelper.floor(d1) & 15) >= 8 ? Facing.SOUTH : Facing.NORTH;
                BlockPos blockPos = new BlockPos(d6, d0, d1);
                RenderChunk renderChunk = renderGlobal.getRenderChunk(blockPos);
                RenderChunk renderChunkValue = renderGlobal.getRenderChunk(renderChunk, facing);
                RenderChunk renderChunkValue2 =
                        renderGlobal.getRenderChunk(renderChunk, facingValue2);
                RenderChunk renderChunkValue3 =
                        renderGlobal.getRenderChunk(renderChunkValue, facingValue2);
                RenderChunk renderChunkValue4 =
                        renderGlobal.getRenderChunk(renderChunk, facingValue);
                RenderChunk renderChunkValue5 =
                        renderGlobal.getRenderChunk(renderChunkValue4, facing);
                RenderChunk renderChunkValue6 =
                        renderGlobal.getRenderChunk(renderChunkValue4, facingValue2);
                RenderChunk renderChunkValue7 =
                        renderGlobal.getRenderChunk(renderChunkValue5, facingValue2);
                this.updateChunkLight(renderChunk, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue2, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue3, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue4, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue5, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue6, this.setLitChunkPos, hashset);
                this.updateChunkLight(renderChunkValue7, this.setLitChunkPos, hashset);
            }

            this.updateLitChunks(renderGlobal);
            this.setLitChunkPos = hashset;
        }
    }

    public Entity getEntity() {
        return this.entity;
    }


    public DynamicLight(Entity entity) {
        this.offsetY = 0.0;
        this.lastPosX = -2.14748365E9F;
        this.lastPosY = -2.14748365E9F;
        this.lastPosZ = -2.14748365E9F;
        this.lastLightLevel = 0;
        this.underwater = false;

        this.timeCheckMs = 0L;
        this.setLitChunkPos = new HashSet<>();
        this.AP = new BlockPosM(0, 0, 0);
        this.entity = entity;
        this.offsetY = entity.getEyeHeight();
    }

    public int getLastLightLevel() {
        return this.lastLightLevel;
    }

    public boolean isUnderwater() {
        return this.underwater;
    }

    public double getLastPosZ() {
        return this.lastPosZ;
    }

    private void updateChunkLight(RenderChunk renderChunk, Set<BlockPos> set, Set<BlockPos> set1) {
        if (renderChunk != null) {
            CompiledChunk compiledChunk = renderChunk.getCompiledChunk();
            if (compiledChunk != null && !compiledChunk.isEmpty()) {
                renderChunk.setNeedsUpdate(true);
            }

            BlockPos blockPos = renderChunk.getPosition();
            if (set != null) {
                set.remove(blockPos);
            }

            if (set1 != null) {
                set1.add(blockPos);
            }
        }
    }

    public double getLastPosX() {
        return this.lastPosX;
    }

    public double getLastPosY() {
        return this.lastPosY;
    }

    public void updateLitChunks(RenderGlobal renderGlobal) {
        int j = ReflectorMethod.f();
        Iterator iterator = this.setLitChunkPos.iterator();
        int i = j;

        while (iterator.hasNext()) {
            BlockPos blockPos = (BlockPos) iterator.next();
            RenderChunk renderChunk = renderGlobal.getRenderChunk(blockPos);
            this.updateChunkLight(renderChunk, (Set<BlockPos>) null, (Set<BlockPos>) null);
            if (i != 0) {
                break;
            }
        }
    }

    public double getOffsetY() {
        return this.offsetY;
    }

    @Override
    public String toString() {
        return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
    }
}
