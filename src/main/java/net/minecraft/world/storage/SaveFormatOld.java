package net.minecraft.world.storage;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.IProgressUpdate;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveFormatOld implements ISaveFormat {

    protected File savesDirectory;
    private static String[] packedTextFragments;
    private static Logger logger;

    public boolean convertMapFormat(String text, IProgressUpdate iProgressUpdate) {
        return false;
    }

    static {
        net.minecraft.world.storage.SaveFormatOld.packedTextFragments = new String[3];
        net.minecraft.world.storage.SaveFormatOld.packedTextFragments[0] = new String("");
        net.minecraft.world.storage.SaveFormatOld.logger = LogManager.getLogger();
    }

    public boolean canLoadWorld(String text) {
        File file1 = new File(this.savesDirectory, text);
        return file1.isDirectory();
    }

    public void flushCache() {}

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public WorldInfo getWorldInfo(String text) {
        File file1 = new File(this.savesDirectory, text);
        if (!file1.exists()) {
            return null;
        }

        File file2 = new File(file1, "level.dat");
        if (file2.exists()) {
            try {
                CompoundTag compoundTag =
                        CompressedStreamTools.readCompressed(new FileInputStream(file2));
                CompoundTag compoundTagValue = compoundTag.getCompoundTag("Data");
                return new WorldInfo(compoundTagValue);
            } catch (Exception exception1) {
                net.minecraft.world.storage.SaveFormatOld.logger.error("Exception reading " + file2, exception1);
            }
        }

        file2 = new File(file1, "level.dat_old");
        if (file2.exists()) {
            try {
                CompoundTag compoundTagValue2 =
                        CompressedStreamTools.readCompressed(new FileInputStream(file2));
                CompoundTag compoundTagValue3 = compoundTagValue2.getCompoundTag("Data");
                return new WorldInfo(compoundTagValue3);
            } catch (Exception exception) {
                net.minecraft.world.storage.SaveFormatOld.logger.error("Exception reading " + file2, exception);
            }
        }

        return null;
    }

    public boolean b(String text) {
        File file1 = new File(this.savesDirectory, text);
        if (file1.exists()) {
            return false;
        }

        try {
            file1.mkdir();
            file1.delete();
            return true;
        } catch (Throwable throwable) {
            net.minecraft.world.storage.SaveFormatOld.logger.warn("Couldn't make new level", throwable);
            return false;
        }
    }

    public List getSaveList() throws AnvilConverterException {
        ArrayList arrayList = Lists.newArrayList();
        WorldInfo.YP();

        int i = 0;

        while (i < 5) {
            String text = "World" + (i + 1);
            WorldInfo worldInfo = this.getWorldInfo(text);
            if (worldInfo != null) {
                arrayList.add(
                        new SaveFormatComparator(
                                text,
                                packedTextFragments[0],
                                worldInfo.getLastTimePlayed(),
                                worldInfo.getSizeOnDisk(),
                                worldInfo.getGameType(),
                                false,
                                worldInfo.isHardcoreModeEnabled(),
                                worldInfo.areCommandsAllowed()));
            }

            i++;
        }

        return arrayList;
    }

    public boolean deleteWorldDirectory(String text) {
        try {
            return remix.util.io.FileActions.deleteWorld(this.savesDirectory, text);
        } catch (java.io.IOException failure) {
            return false;
        }
    }

    public boolean isOldMapFormat(String text) {
        return false;
    }

    public void renameWorld(String text, String textValue) {
        File file1 = new File(this.savesDirectory, text);
        if (file1.exists()) {
            File file2 = new File(file1, "level.dat");
            if (file2.exists()) {
                try {
                    CompoundTag compoundTag =
                            CompressedStreamTools.readCompressed(new FileInputStream(file2));
                    CompoundTag compoundTagValue = compoundTag.getCompoundTag("Data");
                    compoundTagValue.setString("LevelName", textValue);
                    CompressedStreamTools.writeCompressed(
                            compoundTag, new FileOutputStream(file2));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public boolean ag(String text) {
        return false;
    }

    public ISaveHandler getSaveLoader(String text, boolean flag) {
        return new SaveHandler(this.savesDirectory, text, flag);
    }

    public String getName() {
        return "Old Format";
    }

    public SaveFormatOld(File file1) {
        if (!file1.exists()) {
            file1.mkdirs();
        }

        this.savesDirectory = file1;
    }

    protected static boolean deleteFiles(File[] afile) {
        return false;
    }
}
