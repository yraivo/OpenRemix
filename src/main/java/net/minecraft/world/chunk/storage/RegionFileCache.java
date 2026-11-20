package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RegionFileCache {
    private static String[] packedTextFragments;
    private static Map regionsByFilename;

    static {
        net.minecraft.world.chunk.storage.RegionFileCache.packedTextFragments = new String[2];
        net.minecraft.world.chunk.storage.RegionFileCache.packedTextFragments[0] = new String(".");
        net.minecraft.world.chunk.storage.RegionFileCache.regionsByFilename = Maps.newHashMap();
    }

    public static synchronized RegionFile createOrLoadRegionFile(File file1, int i, int j) {
        File file2 = new File(file1, "region");
        File file3 = new File(file2, "r." + (i >> 5) + packedTextFragments[0] + (j >> 5) + ".mca");
        RegionFile regionFile = (RegionFile) net.minecraft.world.chunk.storage.RegionFileCache.regionsByFilename.get(file3);
        if (regionFile != null) {
            return regionFile;
        }

        if (!file2.exists()) {
            file2.mkdirs();
        }

        if (net.minecraft.world.chunk.storage.RegionFileCache.regionsByFilename.size() >= 256) {
            clearRegionFileReferences();
        }

        RegionFile regionFileValue = new RegionFile(file3);
        net.minecraft.world.chunk.storage.RegionFileCache.regionsByFilename.put(file3, regionFileValue);
        return regionFileValue;
    }

    public static synchronized void clearRegionFileReferences() {
        String text = ExtendedBlockStorage.cJ();
        Iterator iterator = net.minecraft.world.chunk.storage.RegionFileCache.regionsByFilename.values().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            RegionFile regionFile = (RegionFile) iterator.next();

            try {
                if (regionFile != null) {
                    regionFile.method_8d9b536bcdc9991c48fb();
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

            if (textValue == null) {
                break;
            }
        }

        net.minecraft.world.chunk.storage.RegionFileCache.regionsByFilename.clear();
    }

    public static DataInputStream getChunkInputStream(File file1, int i, int j) {
        RegionFile orLoadRegionFile = createOrLoadRegionFile(file1, i, j);
        return orLoadRegionFile.getChunkDataInputStream(i & 31, j & 31);
    }

    public static DataOutputStream getChunkOutputStream(File file1, int i, int j) {
        RegionFile orLoadRegionFile = createOrLoadRegionFile(file1, i, j);
        return orLoadRegionFile.getChunkDataOutputStream(i & 31, j & 31);
    }
}
