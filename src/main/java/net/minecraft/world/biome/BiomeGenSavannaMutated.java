package net.minecraft.world.biome;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeGenSavannaMutated extends BiomeGenMutated {
    public void decorate(World world, Random random, BlockPos blockPos) {
        this.theBiomeDecorator.decorate(world, random, this, blockPos);
    }

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        {
            boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            boolean flag = flag1;
            if (d0 > 1.75) {
                this.topBlock = Blocks.stone.getDefaultState();
                this.fillerBlock = Blocks.stone.getDefaultState();
                if (!(flag)) {

                    if (d0 > -0.5) {
                        this.topBlock =
                                Blocks.dirt
                                        .getDefaultState()
                                        .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.COARSE_DIRT);
                    }
                }
            } else {

                if (d0 > -0.5) {
                    this.topBlock =
                            Blocks.dirt
                                    .getDefaultState()
                                    .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.COARSE_DIRT);
                }
            }
        }

        this.generateBiomeTerrain(world, random, chunkPrimer, i, j, d0);
    }

    public BiomeGenSavannaMutated(int i, BiomeGenBase biomeGenBase) {
        super(i, biomeGenBase);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 2;
        this.theBiomeDecorator.grassPerChunk = 5;
    }
}
