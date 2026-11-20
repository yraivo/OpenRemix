package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AnvilSaveConverter extends SaveFormatOld {

    private static Logger logger;

    public boolean isOldMapFormat(String text) {
        WorldInfo worldInfo = this.getWorldInfo(text);
        return worldInfo != null && worldInfo.getSaveVersion() != this.getSaveVersion();
    }

    public boolean convertMapFormat(String text, IProgressUpdate iProgressUpdate) {
        iProgressUpdate.setLoadingProgress(0);
        ArrayList arrayList = Lists.newArrayList();
        String textValue = ExtendedBlockStorage.cJ();
        ArrayList arrayListValue = Lists.newArrayList();
        String textValue2 = textValue;
        ArrayList arrayListValue2 = Lists.newArrayList();
        File file1 = new File(this.savesDirectory, text);
        File file2 = new File(file1, "DIM-1");
        File file3 = new File(file1, "DIM1");
        net.minecraft.world.chunk.storage.AnvilSaveConverter.logger.info("Scanning folders...");
        this.addRegionFilesToCollection(file1, arrayList);
        if (file2.exists()) {
            this.addRegionFilesToCollection(file2, arrayListValue);
        }

        if (file3.exists()) {
            this.addRegionFilesToCollection(file3, arrayListValue2);
        }

        int i = arrayList.size() + arrayListValue.size() + arrayListValue2.size();
        net.minecraft.world.chunk.storage.AnvilSaveConverter.logger.info(
                "Total conversion count is " + i);
        WorldInfo worldInfo = this.getWorldInfo(text);
        Object object = null;
        if (worldInfo.getTerrainType() == WorldType.FLAT) {
            object = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
        } else {
            object =
                    new WorldChunkManager(
                            worldInfo.getSeed(),
                            worldInfo.getTerrainType(),
                            worldInfo.getGeneratorOptions());
        }

        this.convertFile(
                new File(file1, "region"),
                arrayList,
                (WorldChunkManager) object,
                0,
                i,
                iProgressUpdate);
        this.convertFile(
                new File(file2, "region"),
                arrayListValue,
                new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F),
                arrayList.size(),
                i,
                iProgressUpdate);
        this.convertFile(
                new File(file3, "region"),
                arrayListValue2,
                new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F),
                arrayList.size() + arrayListValue.size(),
                i,
                iProgressUpdate);
        worldInfo.setSaveVersion(19133);
        if (worldInfo.getTerrainType() == WorldType.DEFAULT_1_1) {
            worldInfo.setTerrainType(WorldType.DEFAULT);
        }

        this.createFile(text);
        ISaveHandler iSaveHandler = this.getSaveLoader(text, false);
        iSaveHandler.saveWorldInfo(worldInfo);
        if (textValue2 == null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return true;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public AnvilSaveConverter(File file1) {
        super(file1);
    }

    static {
        net.minecraft.world.chunk.storage.AnvilSaveConverter.logger = LogManager.getLogger();
    }

    private void convertFile(
            File file1,
            Iterable iterable,
            WorldChunkManager worldChunkManager,
            int i,
            int j,
            IProgressUpdate iProgressUpdate) {
        String text = ExtendedBlockStorage.cJ();
        Iterator iterator = iterable.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            File file2 = (File) iterator.next();
            this.convertChunks(file1, file2, worldChunkManager, i, j, iProgressUpdate);
            i++;
            int k = (int) Math.round(100.0 * i / j);
            iProgressUpdate.setLoadingProgress(k);
            if (textValue == null) {
                break;
            }
        }
    }

    public boolean ag(String text) {
        WorldInfo worldInfo = this.getWorldInfo(text);
        return worldInfo != null && worldInfo.getSaveVersion() == 19132;
    }

    public List getSaveList() throws AnvilConverterException {
        String text = ExtendedBlockStorage.cJ();
        if (this.savesDirectory != null
                && this.savesDirectory.exists()
                && this.savesDirectory.isDirectory()) {
            ArrayList arrayList = Lists.newArrayList();
            File[] afile = this.savesDirectory.listFiles();
            File[] afile1 = afile;
            int length = afile.length;
            int j = 0;

            while (j < length) {
                File file1 = afile1[j];
                if (file1.isDirectory()) {
                    String name = file1.getName();
                    WorldInfo worldInfo = this.getWorldInfo(name);
                    if (worldInfo != null
                            && (worldInfo.getSaveVersion() == 19132
                                    || worldInfo.getSaveVersion() == 19133)) {
                        int k = worldInfo.getSaveVersion() != this.getSaveVersion() ? 1 : 0;
                        String textValue = worldInfo.getWorldName();
                        if (StringUtils.isEmpty(textValue)) {
                            textValue = name;
                        }

                        long l = 0L;
                        arrayList.add(
                                new SaveFormatComparator(
                                        name,
                                        textValue,
                                        worldInfo.getLastTimePlayed(),
                                        l,
                                        worldInfo.getGameType(),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(k),
                                        worldInfo.isHardcoreModeEnabled(),
                                        worldInfo.areCommandsAllowed()));
                    }
                }

                j++;
                if (text == null) {
                    break;
                }
            }

            return arrayList;
        } else {
            throw new AnvilConverterException(
                    "Unable to read or access folder where game worlds are saved!");
        }
    }

    public ISaveHandler getSaveLoader(String text, boolean flag) {
        return new AnvilSaveHandler(this.savesDirectory, text, flag);
    }

    private void convertChunks(
            File file1,
            File file2,
            WorldChunkManager worldChunkManager,
            int i,
            int j,
            IProgressUpdate iProgressUpdate) {
        String text = ExtendedBlockStorage.cJ();

        try {
            String name = file2.getName();
            RegionFile regionFile = new RegionFile(file2);
            RegionFile regionFileValue =
                    new RegionFile(
                            new File(
                                    file1,
                                    name.substring(0, name.length() - ".mcr".length()) + ".mca"));
            int k = 0;

            while (k < 32) {
                int l = 0;

                while (l < 32) {
                    label41:
                    if (regionFile.isChunkSaved(k, l) && !regionFileValue.isChunkSaved(k, l)) {
                        DataInputStream datainputstream = regionFile.getChunkDataInputStream(k, l);
                        if (datainputstream == null) {
                            net.minecraft.world.chunk.storage.AnvilSaveConverter.logger.warn(
                                    "Failed to fetch input stream");
                            if (text != null) {
                                break label41;
                            }
                        }

                        CompoundTag compoundTag = CompressedStreamTools.read(datainputstream);
                        datainputstream.close();
                        CompoundTag compoundTagValue = compoundTag.getCompoundTag("Level");
                        ChunkLoaderAnvilConverterData chunkLoaderAnvilConverterData =
                                ChunkLoader.load(compoundTagValue);
                        CompoundTag compoundTagValue2 = new CompoundTag();
                        CompoundTag compoundTagValue3 = new CompoundTag();
                        compoundTagValue2.setTag("Level", compoundTagValue3);
                        ChunkLoader.convertToAnvilFormat(
                                chunkLoaderAnvilConverterData,
                                compoundTagValue3,
                                worldChunkManager);
                        DataOutputStream dataoutputstream =
                                regionFileValue.getChunkDataOutputStream(k, l);
                        CompressedStreamTools.write(compoundTagValue2, dataoutputstream);
                        dataoutputstream.close();
                    }

                    l++;
                    if (text == null) {
                        break;
                    }
                }

                l = (int) Math.round(100.0 * (i * 1024) / (j * 1024));
                int i1 = (int) Math.round(100.0 * ((k + 1) * 32 + i * 1024) / (j * 1024));
                if (i1 > l) {
                    iProgressUpdate.setLoadingProgress(i1);
                }

                k++;
                if (text == null) {
                    break;
                }
            }

            regionFile.method_8d9b536bcdc9991c48fb();
            regionFileValue.method_8d9b536bcdc9991c48fb();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    private void createFile(String text) {
        String textValue = ExtendedBlockStorage.cJ();
        File file1 = new File(this.savesDirectory, text);
        String textValue2 = textValue;
        if (!file1.exists()) {
            net.minecraft.world.chunk.storage.AnvilSaveConverter.logger.warn(
                    "Unable to create level.dat_mcr backup");
            if (textValue2 != null) {
                return;
            }
        }

        File file2 = new File(file1, "level.dat");
        if (!file2.exists()) {
            net.minecraft.world.chunk.storage.AnvilSaveConverter.logger.warn(
                    "Unable to create level.dat_mcr backup");
            if (textValue2 != null) {
                return;
            }
        }

        File file3 = new File(file1, "level.dat_mcr");
        if (!file2.renameTo(file3)) {
            net.minecraft.world.chunk.storage.AnvilSaveConverter.logger.warn(
                    "Unable to create level.dat_mcr backup");
        }
    }

    protected int getSaveVersion() {
        return 19133;
    }

    private void addRegionFilesToCollection(File file1, Collection collection) {
        File file2 = new File(file1, "region");
        File[] afile = file2.listFiles(new McrRegionFilenameFilter(this));
        if (afile != null) {
            Collections.addAll(collection, afile);
        }
    }

    public void flushCache() {
        RegionFileCache.clearRegionFileReferences();
    }

    public String getName() {
        return "Anvil";
    }
}
