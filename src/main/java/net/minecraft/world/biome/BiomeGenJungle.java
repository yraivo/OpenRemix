package net.minecraft.world.biome;

import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.BlockTallGrassEnumType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeGenJungle extends BiomeGenBase {
    private boolean field_150614_aC;

    public BiomeGenJungle(int i, boolean amEValue) {
        super(i);
        this.field_150614_aC = amEValue;
        if (amEValue) {
            this.theBiomeDecorator.treesPerChunk = 2;
        } else {
            this.theBiomeDecorator.treesPerChunk = 50;
        }

        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;
        if (!amEValue) {
            this.spawnableMonsterList.add(
                    new BiomeGenBaseSpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }

        this.spawnableCreatureList.add(
                new BiomeGenBaseSpawnListEntry(EntityChicken.class, 10, 4, 4));
    }

    @Override
    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return (WorldGenAbstractTree)
                (random.nextInt(10) == 0
                        ? this.worldGeneratorBigTree
                        : (random.nextInt(2) == 0
                                ? new WorldGenShrub(
                                        BlockPlanksEnumType.JUNGLE.getMetadata(),
                                        BlockPlanksEnumType.OAK.getMetadata())
                                : (!this.field_150614_aC && random.nextInt(3) == 0
                                        ? new WorldGenMegaJungle(
                                                false,
                                                10,
                                                20,
                                                BlockPlanksEnumType.JUNGLE.getMetadata(),
                                                BlockPlanksEnumType.JUNGLE.getMetadata())
                                        : new WorldGenTrees(
                                                false,
                                                4 + random.nextInt(7),
                                                BlockPlanksEnumType.JUNGLE.getMetadata(),
                                                BlockPlanksEnumType.JUNGLE.getMetadata(),
                                                true))));
    }

    @Override
    public void decorate(World world, Random random, BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        int i = random.nextInt(16) + 8;
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
        int j = random.nextInt(16) + 8;
        boolean flag = flag1;
        int k = random.nextInt(world.getHorizon(blockPos.add(i, 0, j)).getY() * 2);
        new WorldGenMelon().generate(world, random, blockPos.add(i, k, j));
        WorldGenVines worldGenVines = new WorldGenVines();
        j = 0;

        while (j < 50) {
            k = random.nextInt(16) + 8;

            int l = random.nextInt(16) + 8;
            worldGenVines.generate(world, random, blockPos.add(k, 128, l));
            j++;
            if (!flag) {
                break;
            }
        }
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random random) {
        return random.nextInt(4) == 0
                ? new WorldGenTallGrass(BlockTallGrassEnumType.FERN)
                : new WorldGenTallGrass(BlockTallGrassEnumType.GRASS);
    }
}
