package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ChunkCoordIntPair;

class AnvilChunkLoaderPendingChunk {
    public ChunkCoordIntPair chunkCoordinate;
    public CompoundTag nbtTags;

    public AnvilChunkLoaderPendingChunk(ChunkCoordIntPair bxPValue, CompoundTag bvDValue) {
        this.chunkCoordinate = bxPValue;
        this.nbtTags = bvDValue;
    }
}
