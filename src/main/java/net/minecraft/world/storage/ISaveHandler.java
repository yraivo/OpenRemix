package net.minecraft.world.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

import java.io.File;

public interface ISaveHandler {
    File getWorldDirectory();

    String getWorldDirectoryName();

    WorldInfo loadWorldInfo();

    File getMapFileFromName(String text);

    void checkSessionLock() throws MinecraftException;

    void flush();

    void saveWorldInfo(WorldInfo worldInfo);

    void saveWorldInfoWithPlayer(WorldInfo worldInfo, CompoundTag compoundTag);

    IPlayerFileData getPlayerNBTManager();

    IChunkLoader getChunkLoader(WorldProvider worldProvider);
}
