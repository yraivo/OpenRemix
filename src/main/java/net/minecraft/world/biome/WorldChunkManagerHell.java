package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerHell extends WorldChunkManager {
    private float rainfall;
    private BiomeGenBase biomeGenerator;

    public BiomeGenBase getBiomeGenerator(BlockPos blockPos) {
        return this.biomeGenerator;
    }

    public BiomeGenBase[] loadBlockGeneratorData(
            BiomeGenBase[] biomeGenBaseArray, int p171_1, int p171_2, int p171_3, int p171_4) {
        if (biomeGenBaseArray == null || biomeGenBaseArray.length < p171_3 * p171_4) {
            biomeGenBaseArray = new net.minecraft.world.biome.BiomeGenBase[p171_3 * p171_4];
        }

        java.util.Arrays.fill(biomeGenBaseArray, 0, p171_3 * p171_4, this.biomeGenerator);
        return biomeGenBaseArray;
    }

    public BiomeGenBase[] getBiomeGenAt(
            BiomeGenBase[] biomeGenBaseArray,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            boolean p171_5) {
        return this.loadBlockGeneratorData(biomeGenBaseArray, p171_1, p171_2, p171_3, p171_4);
    }

    public WorldChunkManagerHell(BiomeGenBase bIHValue, float rainfallValue) {
        this.biomeGenerator = bIHValue;
        this.rainfall = rainfallValue;
    }

    public float[] getRainfall(float[] p171_0, int p171_1, int p171_2, int p171_3, int p171_4) {
        if (p171_0 == null || p171_0.length < p171_3 * p171_4) {
            p171_0 = new float[p171_3 * p171_4];
        }

        java.util.Arrays.fill(p171_0, 0, p171_3 * p171_4, this.rainfall);
        return p171_0;
    }

    public BlockPos findBiomePosition(
            int p171_0, int p171_1, int p171_2, List list, Random random) {
        return list.contains(this.biomeGenerator)
                ? new net.minecraft.util.BlockPos(
                        p171_0 - p171_2 + random.nextInt(p171_2 * 2 + 1),
                        0,
                        p171_1 - p171_2 + random.nextInt(p171_2 * 2 + 1))
                : null;
    }

    public BiomeGenBase[] getBiomesForGeneration(
            BiomeGenBase[] biomeGenBaseArray, int k, int l, int i, int j) {
        if (biomeGenBaseArray == null || biomeGenBaseArray.length < i * j) {
            biomeGenBaseArray = new BiomeGenBase[i * j];
        }

        Arrays.fill(biomeGenBaseArray, 0, i * j, this.biomeGenerator);
        return biomeGenBaseArray;
    }

    public boolean areBiomesViable(int p171_0, int p171_1, int p171_2, List list) {
        return list.contains(this.biomeGenerator);
    }
}
