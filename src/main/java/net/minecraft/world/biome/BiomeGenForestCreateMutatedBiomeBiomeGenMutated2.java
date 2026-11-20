package net.minecraft.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenForestCreateMutatedBiomeBiomeGenMutated2 extends BiomeGenMutated {
    BiomeGenForest bLr;

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return random.nextBoolean() ? BiomeGenForest.bLe : BiomeGenForest.bLd;
    }

    public BiomeGenForestCreateMutatedBiomeBiomeGenMutated2(BiomeGenForest bLrValue, int i, BiomeGenBase biomeGenBase) {
        super(i, biomeGenBase);
        this.bLr = bLrValue;
    }
}
