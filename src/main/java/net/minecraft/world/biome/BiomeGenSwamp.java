package net.minecraft.world.biome;

import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenSwamp extends BiomeGenBase {
    public int getGrassColorAtPos(BlockPos blockPos) {
        double d0 =
                net.minecraft.world.biome.BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a(
                        blockPos.getX() * 0.0225, blockPos.getZ() * 0.0225);
        return d0 < -0.1 ? 5011004 : 6975545;
    }

    public BlockFlowerEnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
        return BlockFlowerEnumFlowerType.BLUE_ORCHID;
    }

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
        double d1 = net.minecraft.world.biome.BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a(i * 0.25, j * 0.25);
        boolean flag = flag1;
        if (d1 > 0.0) {
            int k = i & 15;
            int l = j & 15;
            int i1 = 255;

            while (i1 >= 0) {
                if (chunkPrimer.getBlockState(l, i1, k).getBlock().getMaterial() != Material.AIR) {
                    if (i1 != 62
                            || chunkPrimer.getBlockState(l, i1, k).getBlock() == Blocks.water) {
                        break;
                    }

                    chunkPrimer.setBlockState(l, i1, k, Blocks.water.getDefaultState());
                    if (!(d1 < 0.12)) {
                        break;
                    }

                    chunkPrimer.setBlockState(l, i1 + 1, k, Blocks.waterlily.getDefaultState());
                    if (flag) {
                        break;
                    }
                }

                i1--;
                if (!flag) {
                    break;
                }
            }
        }

        this.generateBiomeTerrain(world, random, chunkPrimer, i, j, d0);
    }

    public int getFoliageColorAtPos(BlockPos blockPos) {
        return 6975545;
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return this.worldGeneratorSwamp;
    }

    protected BiomeGenSwamp(int i) {
        super(i);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 1;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.mushroomsPerChunk = 8;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.theBiomeDecorator.clayPerChunk = 1;
        this.theBiomeDecorator.waterlilyPerChunk = 4;
        this.theBiomeDecorator.sandPerChunk2 = 0;
        this.theBiomeDecorator.sandPerChunk = 0;
        this.theBiomeDecorator.grassPerChunk = 5;
        this.waterColorMultiplier = 14745518;
        this.spawnableMonsterList.add(new BiomeGenBaseSpawnListEntry(SlimeEntity.class, 1, 1, 1));
    }
}
