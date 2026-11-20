package net.minecraft.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeGenDesert extends BiomeGenBase {
    public BiomeGenDesert(int i) {
        super(i);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState();
        this.fillerBlock = Blocks.sand.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
        this.spawnableCreatureList.clear();
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        if (random.nextInt(1000) == 0) {
            int i = random.nextInt(16) + 8;
            int j = random.nextInt(16) + 8;
            net.minecraft.util.BlockPos blockPosValue = world.getHorizon(blockPos.add(i, 0, j)).offsetUp();
            new net.minecraft.world.gen.feature.WorldGenDesertWells().generate(world, random, blockPosValue);
        }
    }
}
