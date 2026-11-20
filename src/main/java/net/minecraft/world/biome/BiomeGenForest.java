package net.minecraft.world.biome;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.biome.BiomeGenForestCreateMutatedBiomeBiomeGenMutated;
import net.minecraft.world.biome.BiomeGenForestCreateMutatedBiomeBiomeGenMutated2;

import java.util.Random;

public class BiomeGenForest extends BiomeGenBase {
    private static String bIv;
    public static WorldGenForest bLd;
    public static WorldGenForest bLe;
    protected static WorldGenCanopyTree bLf;

    private int field_150632_aF;

    protected BiomeGenBase createMutatedBiome(int i) {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            BiomeGenForest biomeGenForest = new BiomeGenForest(i, 1);
            biomeGenForest.setHeight(new BiomeGenBaseHeight(this.minHeight, this.maxHeight + 0.2F));
            biomeGenForest.setBiomeName(bIv);
            biomeGenForest.func_150557_a(6976549, true);
            biomeGenForest.setFillerBlockMetadata(8233509);
            return biomeGenForest;
        } else {
            return (BiomeGenBase)
                    (this.biomeID != BiomeGenBase.birchForest.biomeID
                                    && this.biomeID != BiomeGenBase.birchForestHills.biomeID
                            ? new BiomeGenForestCreateMutatedBiomeBiomeGenMutated(this, i, this)
                            : new BiomeGenForestCreateMutatedBiomeBiomeGenMutated2(this, i, this));
        }
    }

    protected BiomeGenBase func_150557_a(int colorValue, boolean flag) {
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = colorValue;
            if (flag) {
                this.field_150609_ah = (this.field_150609_ah & 16711422) >> 1;
            }

            return this;
        } else {
            return super.func_150557_a(colorValue, flag);
        }
    }

    // region String decoding support

    // endregion

    public BlockFlowerEnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
        if (this.field_150632_aF == 1) {
            double d0 =
                    MathHelper.clamp(
                            (1.0
                                            + net.minecraft.world.biome.BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a(
                                                    blockPos.getX() / 48.0, blockPos.getZ() / 48.0))
                                    / 2.0,
                            0.0,
                            0.9999);
            BlockFlowerEnumFlowerType blockFlowerEnumFlowerType =
                    BlockFlowerEnumFlowerType.values()[
                            (int) (d0 * BlockFlowerEnumFlowerType.values().length)];
            return blockFlowerEnumFlowerType == BlockFlowerEnumFlowerType.BLUE_ORCHID
                    ? BlockFlowerEnumFlowerType.POPPY
                    : blockFlowerEnumFlowerType;
        } else {
            return super.pickRandomFlower(random, blockPos);
        }
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        boolean flag = WorldChunkManager.d();
        if (this.field_150632_aF == 3) {
            int i = 0;

            while (i < 4) {
                int j = 0;

                while (j < 4) {
                    label69:
                    {
                        int k = i * 4 + 1 + 8 + random.nextInt(3);
                        int l = j * 4 + 1 + 8 + random.nextInt(3);
                        BlockPos blockPosValue = world.getHorizon(blockPos.add(k, 0, l));
                        if (random.nextInt(20) == 0) {
                            WorldGenBigMushroom worldGenBigMushroom = new WorldGenBigMushroom();
                            worldGenBigMushroom.generate(world, random, blockPosValue);
                            if (!flag) {
                                break label69;
                            }
                        }

                        WorldGenAbstractTree worldGenAbstractTree = this.genBigTreeChance(random);
                        worldGenAbstractTree.func_175904_e();
                        if (worldGenAbstractTree.generate(world, random, blockPosValue)) {
                            worldGenAbstractTree.generatePodzol(
                                    world, random, blockPosValue);
                        }
                    }

                    j++;
                    if (flag) {
                        break;
                    }
                }

                i++;
                if (flag) {
                    break;
                }
            }
        }

        int j1 = random.nextInt(5) - 3;
        if (this.field_150632_aF == 1) {
            j1 += 2;
        }

        int k1 = 0;

        label55:
        while (k1 < j1) {
            label83:
            {
                int l1 = random.nextInt(3);
                if (l1 == 0) {
                    net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(
                            BlockDoublePlantEnumPlantType.SYRINGA);
                    if (!flag) {
                        break label83;
                    }
                }

                if (l1 == 1) {
                    net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(
                            BlockDoublePlantEnumPlantType.ROSE);
                    if (!flag) {
                        break label83;
                    }
                }

                if (l1 == 2) {
                    net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(
                            BlockDoublePlantEnumPlantType.PAEONIA);
                }
            }

            int i2 = 0;

            while (true) {
                if (i2 < 5) {
                    int j2 = random.nextInt(16) + 8;
                    int k2 = random.nextInt(16) + 8;
                    int i1 = random.nextInt(world.getHorizon(blockPos.add(j2, 0, k2)).getY() + 32);
                    if (!net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(
                            world,
                            random,
                            new BlockPos(blockPos.getX() + j2, i1, blockPos.getZ() + k2))) {
                        i2++;
                        if (!flag) {
                            continue;
                        }
                    }
                }

                k1++;
                if (flag) {
                    break label55;
                }
                break;
            }
        }

        super.decorate(world, random, blockPos);
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return (WorldGenAbstractTree)
                (this.field_150632_aF == 3 && random.nextInt(3) > 0
                        ? bLf
                        : (this.field_150632_aF != 2 && random.nextInt(5) != 0
                                ? this.worldGeneratorTrees
                                : bLd));
    }

    public int getGrassColorAtPos(BlockPos blockPos) {
        int grassColorAtPos = super.getGrassColorAtPos(blockPos);
        return this.field_150632_aF == 3
                ? (grassColorAtPos & 16711422) + 2634762 >> 1
                : grassColorAtPos;
    }

    public BiomeGenForest(int i, int j) {
        super(i);
        this.field_150632_aF = j;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        if (this.field_150632_aF == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }

        this.setFillerBlockMetadata(5159473);
        this.setTemperatureRainfall(0.7F, 0.8F);
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = 3175492;
            this.setTemperatureRainfall(0.6F, 0.6F);
        }

        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(
                    new BiomeGenBaseSpawnListEntry(EntityWolf.class, 5, 4, 4));
        }

        if (this.field_150632_aF == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }

    // region String decoding support

    // endregion

    static {
        bIv = "Flower Forest";
        bLe = new WorldGenForest(false, true);
        bLd = new WorldGenForest(false, false);
        bLf = new WorldGenCanopyTree(false);
    }
}
