package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenMutated;

import java.util.Random;

public class BiomeGenForestCreateMutatedBiomeBiomeGenMutated extends BiomeGenMutated {
    BiomeGenForest bLr;

    public void decorate(World world, Random random, BlockPos blockPos) {
        this.baseBiome.decorate(world, random, blockPos);
    }

    public BiomeGenForestCreateMutatedBiomeBiomeGenMutated(BiomeGenForest bLrValue, int i, BiomeGenBase biomeGenBase) {
        super(i, biomeGenBase);
        this.bLr = bLrValue;
    }
}
