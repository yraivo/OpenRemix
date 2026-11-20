package net.minecraft.world.biome;

public class BiomeCacheBlock {
    BiomeCache bJi;
    public long lastAccessTime;
    public BiomeGenBase[] biomes;
    public int zPosition;
    public int xPosition;
    public float[] rainfallValues;

    public BiomeCacheBlock(BiomeCache bJiValue, int i, int j) {
        this.bJi = bJiValue;
        this.rainfallValues = new float[256];
        this.biomes = new BiomeGenBase[256];
        this.xPosition = i;
        this.zPosition = j;
        BiomeCache.access$000(bJiValue).getRainfall(this.rainfallValues, i << 4, j << 4, 16, 16);
        BiomeCache.access$000(bJiValue).getBiomeGenAt(this.biomes, i << 4, j << 4, 16, 16, false);
    }

    public BiomeGenBase getBiomeGenAt(int i, int j) {
        return this.biomes[i & 15 | (j & 15) << 4];
    }
}
