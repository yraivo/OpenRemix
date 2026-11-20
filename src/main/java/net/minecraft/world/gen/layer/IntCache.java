package net.minecraft.world.gen.layer;

import com.google.common.collect.Lists;

import java.util.List;

public class IntCache {

    private static List freeLargeArrays;
    private static List freeSmallArrays;

    private static List inUseLargeArrays;
    private static int intCacheSize;
    private static List inUseSmallArrays;

    public static synchronized void resetIntCache() {
        if (!net.minecraft.world.gen.layer.IntCache.freeLargeArrays.isEmpty()) {
            net.minecraft.world.gen.layer.IntCache.freeLargeArrays.remove(net.minecraft.world.gen.layer.IntCache.freeLargeArrays.size() - 1);
        }

        if (!net.minecraft.world.gen.layer.IntCache.freeSmallArrays.isEmpty()) {
            net.minecraft.world.gen.layer.IntCache.freeSmallArrays.remove(net.minecraft.world.gen.layer.IntCache.freeSmallArrays.size() - 1);
        }

        net.minecraft.world.gen.layer.IntCache.freeLargeArrays.addAll(net.minecraft.world.gen.layer.IntCache.inUseLargeArrays);
        net.minecraft.world.gen.layer.IntCache.freeSmallArrays.addAll(net.minecraft.world.gen.layer.IntCache.inUseSmallArrays);
        net.minecraft.world.gen.layer.IntCache.inUseLargeArrays.clear();
        net.minecraft.world.gen.layer.IntCache.inUseSmallArrays.clear();
    }

    static {
        net.minecraft.world.gen.layer.IntCache.intCacheSize = 256;
        net.minecraft.world.gen.layer.IntCache.freeSmallArrays = Lists.newArrayList();
        net.minecraft.world.gen.layer.IntCache.inUseSmallArrays = Lists.newArrayList();
        net.minecraft.world.gen.layer.IntCache.freeLargeArrays = Lists.newArrayList();
        net.minecraft.world.gen.layer.IntCache.inUseLargeArrays = Lists.newArrayList();
    }

    public static synchronized int[] getIntCache(int i) {
        if (i <= 256) {
            if (net.minecraft.world.gen.layer.IntCache.freeSmallArrays.isEmpty()) {
                int[] aint4 = new int[256];
                net.minecraft.world.gen.layer.IntCache.inUseSmallArrays.add(aint4);
                return aint4;
            } else {
                int[] aint3 = (int[]) net.minecraft.world.gen.layer.IntCache.freeSmallArrays.remove(net.minecraft.world.gen.layer.IntCache.freeSmallArrays.size() - 1);
                net.minecraft.world.gen.layer.IntCache.inUseSmallArrays.add(aint3);
                return aint3;
            }
        } else if (i > net.minecraft.world.gen.layer.IntCache.intCacheSize) {
            net.minecraft.world.gen.layer.IntCache.intCacheSize = i;
            net.minecraft.world.gen.layer.IntCache.freeLargeArrays.clear();
            net.minecraft.world.gen.layer.IntCache.inUseLargeArrays.clear();
            int[] aint2 = new int[net.minecraft.world.gen.layer.IntCache.intCacheSize];
            net.minecraft.world.gen.layer.IntCache.inUseLargeArrays.add(aint2);
            return aint2;
        } else if (net.minecraft.world.gen.layer.IntCache.freeLargeArrays.isEmpty()) {
            int[] aint1 = new int[net.minecraft.world.gen.layer.IntCache.intCacheSize];
            net.minecraft.world.gen.layer.IntCache.inUseLargeArrays.add(aint1);
            return aint1;
        } else {
            int[] aint = (int[]) net.minecraft.world.gen.layer.IntCache.freeLargeArrays.remove(net.minecraft.world.gen.layer.IntCache.freeLargeArrays.size() - 1);
            net.minecraft.world.gen.layer.IntCache.inUseLargeArrays.add(aint);
            return aint;
        }
    }

    public static synchronized String getCacheSizes() {
        return "cache: "
                + net.minecraft.world.gen.layer.IntCache.freeLargeArrays.size()
                + ", tcache: "
                + net.minecraft.world.gen.layer.IntCache.freeSmallArrays.size()
                + ", allocated: "
                + net.minecraft.world.gen.layer.IntCache.inUseLargeArrays.size()
                + ", tallocated: "
                + net.minecraft.world.gen.layer.IntCache.inUseSmallArrays.size();
    }
}
