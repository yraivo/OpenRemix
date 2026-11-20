package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagList;

public class ChunkLoaderAnvilConverterData {
    public boolean terrainPopulated;
    public long lastUpdated;
    public NibbleArrayReader data;
    public int x;
    public byte[] heightmap;
    public NBTTagList entities;
    public NBTTagList tileEntities;
    public NibbleArrayReader blockLight;
    public int z;
    public NBTTagList tileTicks;
    public byte[] blocks;
    public NibbleArrayReader skyLight;

    public ChunkLoaderAnvilConverterData(int i, int j) {
        this.x = i;
        this.z = j;
    }
}
