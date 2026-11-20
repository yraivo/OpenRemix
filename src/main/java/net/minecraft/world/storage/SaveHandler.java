package net.minecraft.world.storage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveHandler implements IPlayerFileData, ISaveHandler {
    private File worldDirectory;

    private File playersDirectory;
    private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
    private String saveDirectoryName;

    private File mapDataDir;
    private static Logger logger;

    public CompoundTag readPlayerData(PlayerEntity playerEntity) {
        CompoundTag playerData = null;
        try {
            File dataFile =
                    new File(this.playersDirectory, playerEntity.getUniqueId().toString() + ".dat");
            if (dataFile.exists() && dataFile.isFile()) {
                playerData = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));
            }
        } catch (Exception failure) {
            net.minecraft.world.storage.SaveHandler.logger.warn(
                    "Failed to load player data for " + playerEntity.getName());
        }

        if (playerData != null) {
            playerEntity.readFromNBT(playerData);
        }
        return playerData;
    }

    private void setSessionLock() {
        try {
            File file1 = new File(this.worldDirectory, "session.lock");
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

            try {
                dataoutputstream.writeLong(this.initializationTime);
            } finally {
                dataoutputstream.close();
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    public void flush() {}

    public WorldInfo loadWorldInfo() {
        File file1 = new File(this.worldDirectory, "level.dat");
        if (file1.exists()) {
            try {
                CompoundTag compoundTag =
                        CompressedStreamTools.readCompressed(new FileInputStream(file1));
                CompoundTag compoundTagValue = compoundTag.getCompoundTag("Data");
                return new WorldInfo(compoundTagValue);
            } catch (Exception exception1) {
                exception1.printStackTrace();
            }
        }

        file1 = new File(this.worldDirectory, "level.dat_old");
        if (file1.exists()) {
            try {
                CompoundTag compoundTagValue2 =
                        CompressedStreamTools.readCompressed(new FileInputStream(file1));
                CompoundTag compoundTagValue3 = compoundTagValue2.getCompoundTag("Data");
                return new WorldInfo(compoundTagValue3);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public void writePlayerData(PlayerEntity playerEntity) {
        try {
            CompoundTag compoundTag = new CompoundTag();
            playerEntity.writeToNBT(compoundTag);
            File file1 =
                    new File(
                            this.playersDirectory,
                            playerEntity.getUniqueId().toString() + ".dat.tmp");
            File file2 =
                    new File(this.playersDirectory, playerEntity.getUniqueId().toString() + ".dat");

            try {
                CompressedStreamTools.writeCompressed(compoundTag, new FileOutputStream(file1));
                if (file2.exists()) {
                    file2.delete();
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception);
            }

            file1.renameTo(file2);
        } catch (Exception exception1) {
            net.minecraft.world.storage.SaveHandler.logger.warn(
                    "Failed to save player data for " + playerEntity.getName());
        }
    }

    public IPlayerFileData getPlayerNBTManager() {
        return this;
    }

    public File getWorldDirectory() {
        return this.worldDirectory;
    }

    public void saveWorldInfo(WorldInfo worldInfo) {
        CompoundTag compoundTag = worldInfo.getNBTTagCompound();
        CompoundTag compoundTagValue = new CompoundTag();
        compoundTagValue.setTag("Data", compoundTag);

        try {
            File file1 = new File(this.worldDirectory, "level.dat_new");
            File file2 = new File(this.worldDirectory, "level.dat_old");
            File file3 = new File(this.worldDirectory, "level.dat");

            try {
                CompressedStreamTools.writeCompressed(
                        compoundTagValue, new FileOutputStream(file1));
                if (file2.exists()) {
                    file2.delete();
                }
            } catch (Exception exception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception1);
            }

            try {
                file3.renameTo(file2);
                if (file3.exists()) {
                    file3.delete();
                }
            } catch (Exception exception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception2);
            }

            try {
                file1.renameTo(file3);
                if (file1.exists()) {
                    file1.delete();
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception);
            }
        } catch (Exception exception3) {
            exception3.printStackTrace();
        }
    }

    public String getWorldDirectoryName() {
        return this.saveDirectoryName;
    }

    public String[] getAvailablePlayerDat() {
        WorldInfo.YP();

        String[] astring = this.playersDirectory.list();

        if (astring == null) {
            astring = new String[0];
        }

        int i = 0;

        while (i < astring.length) {
            try {
                if (astring[i].endsWith(".dat")) {
                    astring[i] = astring[i].substring(0, astring[i].length() - 4);
                }
            } catch (RuntimeException runtimeexception) {
                throw (runtimeexception);
            }

            i++;
        }

        return astring;
    }

    public IChunkLoader getChunkLoader(WorldProvider worldProvider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    public SaveHandler(File file1, String text, boolean flag) {
        this.worldDirectory = new File(file1, text);
        this.worldDirectory.mkdirs();
        this.playersDirectory = new File(this.worldDirectory, "playerdata");
        this.mapDataDir = new File(this.worldDirectory, "data");
        this.mapDataDir.mkdirs();
        this.saveDirectoryName = text;
        if (flag) {
            this.playersDirectory.mkdirs();
        }

        this.setSessionLock();
    }

    public void saveWorldInfoWithPlayer(WorldInfo worldInfo, CompoundTag compoundTag) {
        CompoundTag compoundTagValue = worldInfo.cloneNBTCompound(compoundTag);
        CompoundTag compoundTagValue2 = new CompoundTag();
        compoundTagValue2.setTag("Data", compoundTagValue);

        try {
            File file1 = new File(this.worldDirectory, "level.dat_new");
            File file2 = new File(this.worldDirectory, "level.dat_old");
            File file3 = new File(this.worldDirectory, "level.dat");

            try {
                CompressedStreamTools.writeCompressed(
                        compoundTagValue2, new FileOutputStream(file1));
                if (file2.exists()) {
                    file2.delete();
                }
            } catch (Exception exception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception1);
            }

            try {
                file3.renameTo(file2);
                if (file3.exists()) {
                    file3.delete();
                }
            } catch (Exception exception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception2);
            }

            try {
                file1.renameTo(file3);
                if (file1.exists()) {
                    file1.delete();
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception);
            }
        } catch (Exception exception3) {
            exception3.printStackTrace();
        }
    }

    static {
        net.minecraft.world.storage.SaveHandler.logger = LogManager.getLogger();
    }

    public void checkSessionLock() throws MinecraftException {
        try {
            File file1 = new File(this.worldDirectory, "session.lock");
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

            try {
                if (datainputstream.readLong() != this.initializationTime) {
                    throw new MinecraftException(
                            "The save is being accessed from another location, aborting");
                }
            } finally {
                datainputstream.close();
            }
        } catch (IOException ioexception) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    public File getMapFileFromName(String text) {
        return new File(this.mapDataDir, text + ".dat");
    }
}
