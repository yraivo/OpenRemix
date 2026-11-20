package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.google.common.collect.Lists;

import java.util.Random;

public class BiomeGenMutated extends BiomeGenBase {
    private static String[] packedTextFragments;
    protected BiomeGenBase baseBiome;

    // region String decoding support

    // endregion

    public void decorate(World world, Random random, BlockPos blockPos) {
        this.baseBiome.theBiomeDecorator.decorate(world, random, this, blockPos);
    }

    public boolean isEqualTo(BiomeGenBase biomeGenBase) {
        return this.baseBiome.isEqualTo(biomeGenBase);
    }

    public float getSpawningChance() {
        return this.baseBiome.getSpawningChance();
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return this.baseBiome.genBigTreeChance(random);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public BiomeGenMutated(int i, BiomeGenBase bLqValue) {
        super(i);
        this.baseBiome = bLqValue;
        this.func_150557_a(bLqValue.color, true);
        this.biomeName = bLqValue.biomeName + packedTextFragments[0];
        this.topBlock = bLqValue.topBlock;
        this.fillerBlock = bLqValue.fillerBlock;
        this.fillerBlockMetadata = bLqValue.fillerBlockMetadata;
        this.minHeight = bLqValue.minHeight;
        this.maxHeight = bLqValue.maxHeight;
        this.temperature = bLqValue.temperature;
        this.rainfall = bLqValue.rainfall;
        this.waterColorMultiplier = bLqValue.waterColorMultiplier;
        this.enableSnow = bLqValue.enableSnow;
        this.enableRain = bLqValue.enableRain;
        this.spawnableCreatureList = Lists.newArrayList(bLqValue.spawnableCreatureList);
        this.spawnableMonsterList = Lists.newArrayList(bLqValue.spawnableMonsterList);
        this.spawnableCaveCreatureList = Lists.newArrayList(bLqValue.spawnableCaveCreatureList);
        this.spawnableWaterCreatureList = Lists.newArrayList(bLqValue.spawnableWaterCreatureList);
        this.temperature = bLqValue.temperature;
        this.rainfall = bLqValue.rainfall;
        this.minHeight = bLqValue.minHeight + 0.1F;
        this.maxHeight = bLqValue.maxHeight + 0.2F;
    }

    public int getFoliageColorAtPos(BlockPos blockPos) {
        return this.baseBiome.getFoliageColorAtPos(blockPos);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.world.biome.BiomeGenMutated.packedTextFragments[0] = new String(" M");
    }

    // endregion

    public Class getBiomeClass() {
        return this.baseBiome.getBiomeClass();
    }

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        this.baseBiome.genTerrainBlocks(world, random, chunkPrimer, i, j, d0);
    }

    public BiomeGenBaseTempCategory getTempCategory() {
        return this.baseBiome.getTempCategory();
    }

    public int getGrassColorAtPos(BlockPos blockPos) {
        return this.baseBiome.getGrassColorAtPos(blockPos);
    }
}
