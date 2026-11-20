package net.minecraft.world.chunk.storage;

import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.io.IOException;

public interface IChunkLoader {
    void saveExtraData();

    void chunkTick();

    void saveChunk(World world, Chunk chunk) throws MinecraftException, IOException;

    Chunk loadChunk(World world, int i, int j) throws IOException;

    void saveExtraChunkData(World world, Chunk chunk);
}
