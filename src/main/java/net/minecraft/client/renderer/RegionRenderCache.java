package net.minecraft.client.renderer;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkEnumCreateEntityType;
import optifine.DynamicLights;
import optifine.OptiFineConfig;

import java.util.ArrayDeque;
import java.util.Arrays;

public class RegionRenderCache extends ChunkCache {
    private static final BlockState DEFAULT_STATE = Blocks.air.getDefaultState();
    private static ArrayDeque<int[]> cacheLights = new ArrayDeque<>();
    private BlockPos position;
    private static ArrayDeque<BlockState[]> cacheStates = new ArrayDeque<>();
    private BlockState[] blockStates;
    private static int maxCacheSize =
            OptiFineConfig.limit(Runtime.getRuntime().availableProcessors(), 1, 32);
    private int[] combinedLights;

    private static BlockState[] allocateStates(int i) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        ArrayDeque arraydeque = net.minecraft.client.renderer.RegionRenderCache.cacheStates;
        ArrayDeque arraydeque1 = net.minecraft.client.renderer.RegionRenderCache.cacheStates;
        synchronized (net.minecraft.client.renderer.RegionRenderCache.cacheStates) {
        }
        int j = k;

        try {
            BlockState[] blockStateArray;
            {
                blockStateArray = net.minecraft.client.renderer.RegionRenderCache.cacheStates.pollLast();
                if (blockStateArray != null && blockStateArray.length >= i) {
                    Arrays.fill(blockStateArray, null);
                    if (!(j == 0)) {

                        blockStateArray = new BlockState[i];
                    }
                } else {

                    blockStateArray = new BlockState[i];
                }
            }

            return blockStateArray;
        } finally {
        }
    }

    public static void freeLights(int[] aint) {
        ArrayDeque arraydeque = net.minecraft.client.renderer.RegionRenderCache.cacheLights;
        synchronized (net.minecraft.client.renderer.RegionRenderCache.cacheLights) {
            if (net.minecraft.client.renderer.RegionRenderCache.cacheLights.size() < net.minecraft.client.renderer.RegionRenderCache.maxCacheSize) {
                net.minecraft.client.renderer.RegionRenderCache.cacheLights.add(aint);
            }
        }
    }

    public void freeBuffers() {
        freeLights(this.combinedLights);
        freeStates(this.blockStates);
    }

    public int getCombinedLight(BlockPos blockPos, int i) {
        int j = this.getPositionIndex(blockPos);
        int k = this.combinedLights[j];
        if (k == -1) {
            k = super.getCombinedLight(blockPos, i);
            if (OptiFineConfig.isDynamicLights()
                    && !this.getBlockState(blockPos).getBlock().isOpaqueCube()) {
                k = DynamicLights.getCombinedLight(blockPos, k);
            }

            this.combinedLights[j] = k;
        }

        return k;
    }

    public TileEntity getTileEntity(BlockPos blockPos) {
        int i = (blockPos.getX() >> 4) - this.chunkX;
        int j = (blockPos.getZ() >> 4) - this.chunkZ;
        return this.chunkArray[i][j].getTileEntity(blockPos, ChunkEnumCreateEntityType.QUEUED);
    }

    public static void freeStates(BlockState[] blockStateArray) {
        ArrayDeque arraydeque = net.minecraft.client.renderer.RegionRenderCache.cacheStates;
        synchronized (net.minecraft.client.renderer.RegionRenderCache.cacheStates) {
            if (net.minecraft.client.renderer.RegionRenderCache.cacheStates.size() < net.minecraft.client.renderer.RegionRenderCache.maxCacheSize) {
                net.minecraft.client.renderer.RegionRenderCache.cacheStates.add(blockStateArray);
            }
        }
    }

    public RegionRenderCache(World world, BlockPos blockPos, BlockPos blockPosValue, int i) {
        super(world, blockPos, blockPosValue, i);
        this.position = blockPos.subtract(new Vec3i(i, i, i));

        this.combinedLights = method_5c4e403abb95e0030e7d(8000);
        Arrays.fill(this.combinedLights, -1);
        this.blockStates = allocateStates(8000);
    }

    private int getPositionIndex(BlockPos blockPos) {
        int deltaX = blockPos.getX() - this.position.getX();
        int j = blockPos.getY() - this.position.getY();
        int k = blockPos.getZ() - this.position.getZ();
        return deltaX * 400 + k * 20 + j;
    }

    public BlockState getBlockState(BlockPos blockPos) {
        int positionIndex = this.getPositionIndex(blockPos);
        BlockState blockState = this.blockStates[positionIndex];
        if (blockState == null) {
            blockState = this.getBlockStateRaw(blockPos);
            this.blockStates[positionIndex] = blockState;
        }

        return blockState;
    }

    private BlockState getBlockStateRaw(BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            int i = (blockPos.getX() >> 4) - this.chunkX;
            int j = (blockPos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[i][j].getBlockState(blockPos);
        } else {
            return net.minecraft.client.renderer.RegionRenderCache.DEFAULT_STATE;
        }
    }

    private static int[] method_5c4e403abb95e0030e7d(int i) {
        ArrayDeque arraydeque = net.minecraft.client.renderer.RegionRenderCache.cacheLights;
        synchronized (net.minecraft.client.renderer.RegionRenderCache.cacheLights) {
            int[] aint = net.minecraft.client.renderer.RegionRenderCache.cacheLights.pollLast();
            if (aint == null || aint.length < i) {
                aint = new int[i];
            }

            return aint;
        }
    }
}
