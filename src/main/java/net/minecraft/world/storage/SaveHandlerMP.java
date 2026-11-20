package net.minecraft.world.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

import java.io.File;

public class SaveHandlerMP implements ISaveHandler {

    private static String a;

    public WorldInfo loadWorldInfo() {
        return null;
    }

    public void saveWorldInfoWithPlayer(WorldInfo worldInfo, CompoundTag compoundTag) {}

    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }

    public void checkSessionLock() throws MinecraftException {}

    public String getWorldDirectoryName() {
        return a;
    }

    public IChunkLoader getChunkLoader(WorldProvider worldProvider) {
        return null;
    }

    public void saveWorldInfo(WorldInfo worldInfo) {}

    static {
        a = "none";
    }

    // region String decoding support

    // endregion

    public File getWorldDirectory() {
        return null;
    }

    public File getMapFileFromName(String text) {
        return null;
    }

    // region String decoding support

    // endregion

    public void flush() {}
}
