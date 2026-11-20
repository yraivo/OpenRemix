package net.minecraft.world.biome;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LongHashMap;

import com.google.common.collect.Lists;

import java.util.List;

public class BiomeCache {
    private WorldChunkManager chunkManager;
    private long lastCleanupTime;
    private LongHashMap cacheMap = new LongHashMap();
    private List cache = Lists.newArrayList();

    public void cleanupCache() {
        boolean flag1 = WorldChunkManager.d();
        long lastCleanupTimeValue = MinecraftServer.getCurrentTimeMillis();
        boolean flag = flag1;
        long j = lastCleanupTimeValue - this.lastCleanupTime;

        if (j <= 7500L) {

            if (j >= 0L) {
                return;
            }
        }

        this.lastCleanupTime = lastCleanupTimeValue;
        int k = 0;

        while (k < this.cache.size()) {
            label20:
            {
                BiomeCacheBlock biomeCacheBlock =
                        (BiomeCacheBlock) this.cache.get(k);
                long l = lastCleanupTimeValue - biomeCacheBlock.lastAccessTime;

                if (l <= 30000L) {

                    if (l >= 0L) {
                        break label20;
                    }
                }

                this.cache.remove(k--);
                long j1 = biomeCacheBlock.xPosition;

                long k1 = j1 & 4294967295L;
                long l1 = biomeCacheBlock.zPosition;

                long i1 = k1 | (l1 & 4294967295L) << 32;
                this.cacheMap.remove(i1);
            }

            k++;
            if (flag) {
                break;
            }
        }
    }

    public BiomeCacheBlock getBiomeCacheBlock(int i, int j) {
        i >>= 4;
        j >>= 4;
        long l = i;

        l &= 4294967295L;
        long i1 = j;

        long k = l | (i1 & 4294967295L) << 32;
        BiomeCacheBlock valueByKey =
                (BiomeCacheBlock) this.cacheMap.getValueByKey(k);
        if (valueByKey == null) {
            valueByKey = new BiomeCacheBlock(this, i, j);
            this.cacheMap.add(k, valueByKey);
            this.cache.add(valueByKey);
        }

        valueByKey.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
        return valueByKey;
    }

    public BiomeGenBase[] getCachedBiomes(int i, int j) {
        return this.getBiomeCacheBlock(i, j).biomes;
    }

    public BiomeGenBase func_180284_a(int i, int j, BiomeGenBase biomeGenBase) {
        BiomeGenBase biomeGenBaseValue = this.getBiomeCacheBlock(i, j).getBiomeGenAt(i, j);
        return biomeGenBaseValue == null ? biomeGenBase : biomeGenBaseValue;
    }

    static WorldChunkManager access$000(BiomeCache biomeCache) {
        return biomeCache.chunkManager;
    }

    public BiomeCache(WorldChunkManager bJhValue) {
        this.chunkManager = bJhValue;
    }
}
