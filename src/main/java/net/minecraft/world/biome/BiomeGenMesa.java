package net.minecraft.world.biome;

import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Arrays;
import java.util.Random;

public class BiomeGenMesa extends BiomeGenBase {
    private boolean field_150626_aH;

    private boolean field_150620_aI;
    private BlockState[] field_150621_aC;
    private NoiseGeneratorPerlin bLk;
    private NoiseGeneratorPerlin bLl;

    private NoiseGeneratorPerlin field_150625_aG;
    private long field_150622_aD;

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        boolean flag = WorldChunkManager.d();
        if (this.field_150621_aC == null || this.field_150622_aD != world.getSeed()) {
            this.method_8e4325f4ac811c35fbd6(world.getSeed());
        }

        if (this.bLl == null || this.bLk == null || this.field_150622_aD != world.getSeed()) {
            Random random1 = new Random(this.field_150622_aD);
            this.bLl = new NoiseGeneratorPerlin(random1, 4);
            this.bLk = new NoiseGeneratorPerlin(random1, 1);
        }

        this.field_150622_aD = world.getSeed();
        double d5 = 0.0;
        if (this.field_150626_aH) {
            int k = (i & -16) + (j & 15);
            int l = (j & -16) + (i & 15);
            double d1 = Math.min(Math.abs(d0), this.bLl.func_151601_a(k * 0.25, l * 0.25));
            if (d1 > 0.0) {
                double d2 = 0.001953125;
                double d3 = Math.abs(this.bLk.func_151601_a(k * d2, l * d2));
                d5 = d1 * d1 * 2.5;
                double d4 = Math.ceil(d3 * 50.0) + 14.0;
                if (d5 > d4) {
                    d5 = d4;
                }

                d5 += 64.0;
            }
        }

        int l1 = i & 15;
        int i2 = j & 15;

        BlockState blockState = Blocks.stained_hardened_clay.getDefaultState();
        BlockState bKPValue = this.fillerBlock;
        int i1 = (int) (d0 / 3.0 + 3.0 + random.nextDouble() * 0.25);
        int j2 = Math.cos(d0 / 3.0 * Math.PI) > 0.0 ? 1 : 0;
        int j1 = -1;
        byte b1 = 0;
        int k1 = 255;

        while (k1 >= 0) {
            if (chunkPrimer.getBlockState(i2, k1, l1).getBlock().getMaterial() == Material.AIR
                    && k1 < (int) d5) {
                chunkPrimer.setBlockState(i2, k1, l1, Blocks.stone.getDefaultState());
            }

            label148:
            {
                if (k1 <= random.nextInt(5)) {
                    chunkPrimer.setBlockState(i2, k1, l1, Blocks.bedrock.getDefaultState());
                    if (!flag) {
                        break label148;
                    }
                }

                BlockState blockStateValue = chunkPrimer.getBlockState(i2, k1, l1);
                if (blockStateValue.getBlock().getMaterial() == Material.AIR) {
                    j1 = -1;
                    if (!flag) {
                        break label148;
                    }
                }

                label121:
                if (blockStateValue.getBlock() == Blocks.stone) {
                    if (j1 == -1) {
                        b1 = 0;
                        if (i1 <= 0) {
                            blockState = null;
                            bKPValue = Blocks.stone.getDefaultState();
                        } else if (k1 >= 59 && k1 <= 64) {
                            blockState = Blocks.stained_hardened_clay.getDefaultState();
                            bKPValue = this.fillerBlock;
                        }

                        if (k1 < 63
                                && (blockState == null
                                        || blockState.getBlock().getMaterial() == Material.AIR)) {
                            blockState = Blocks.water.getDefaultState();
                        }

                        j1 = i1 + Math.max(0, k1 - 63);
                        if (k1 >= 62) {
                            if (this.field_150620_aI && k1 > 86 + i1 * 2) {
                                if (j2 != 0) {
                                    chunkPrimer.setBlockState(
                                            i2,
                                            k1,
                                            l1,
                                            Blocks.dirt
                                                    .getDefaultState()
                                                    .withProperty(
                                                            BlockDirt.VARIANT,
                                                            BlockDirtDirtType.COARSE_DIRT));
                                    if (!flag) {
                                        break label121;
                                    }
                                }

                                chunkPrimer.setBlockState(
                                        i2, k1, l1, Blocks.grass.getDefaultState());
                                if (!flag) {
                                    break label121;
                                }
                            }

                            if (k1 > 66 + i1) {
                                BlockState blockStateValue2;
                                label105:
                                {
                                    if (k1 >= 64 && k1 <= 127) {
                                        if (j2 != 0) {
                                            blockStateValue2 =
                                                    Blocks.hardened_clay.getDefaultState();
                                            if (!flag) {
                                                break label105;
                                            }
                                        }

                                        blockStateValue2 = this.func_180629_a(i, k1, j);
                                        if (!flag) {
                                            break label105;
                                        }
                                    }

                                    blockStateValue2 =
                                            Blocks.stained_hardened_clay
                                                    .getDefaultState()
                                                    .withProperty(
                                                            BlockColored.COLOR,
                                                            EnumDyeColor.ORANGE);
                                }

                                chunkPrimer.setBlockState(i2, k1, l1, blockStateValue2);
                                if (!flag) {
                                    break label121;
                                }
                            }

                            chunkPrimer.setBlockState(i2, k1, l1, this.topBlock);
                            b1 = 1;
                            if (!flag) {
                                break label121;
                            }
                        }

                        chunkPrimer.setBlockState(i2, k1, l1, bKPValue);
                        if (bKPValue.getBlock() != Blocks.stained_hardened_clay) {
                            break label121;
                        }

                        chunkPrimer.setBlockState(
                                i2,
                                k1,
                                l1,
                                bKPValue.getBlock()
                                        .getDefaultState()
                                        .withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                        if (!flag) {
                            break label121;
                        }
                    }

                    if (j1 > 0) {
                        j1--;
                        if (b1 != 0) {
                            chunkPrimer.setBlockState(
                                    i2,
                                    k1,
                                    l1,
                                    Blocks.stained_hardened_clay
                                            .getDefaultState()
                                            .withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                            if (!(!flag)) {

                                BlockState blockStateValue3 = this.func_180629_a(i, k1, j);
                                chunkPrimer.setBlockState(i2, k1, l1, blockStateValue3);
                            }
                        } else {

                            BlockState blockStateValue3 = this.func_180629_a(i, k1, j);
                            chunkPrimer.setBlockState(i2, k1, l1, blockStateValue3);
                        }
                    }
                }
            }

            k1--;
            if (flag) {
                break;
            }
        }
    }


    public int getFoliageColorAtPos(BlockPos blockPos) {
        return 10387789;
    }

    protected BiomeGenBase createMutatedBiome(int i) {
        BiomeGenMesa biomeGenMesa;
        {
            boolean flag = WorldChunkManager.d();
            int j = this.biomeID == BiomeGenBase.mesa.biomeID ? 1 : 0;
            biomeGenMesa =
                    new BiomeGenMesa(
                            i,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(j),
                            this.field_150620_aI);
            if (j == 0) {
                biomeGenMesa.setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
                biomeGenMesa.setBiomeName(this.biomeName + " M");
                if (!(!flag)) {

                    biomeGenMesa.setBiomeName(this.biomeName + " (Bryce)");
                }
            } else {

                biomeGenMesa.setBiomeName(this.biomeName + " (Bryce)");
            }
        }

        biomeGenMesa.func_150557_a(this.color, true);
        return biomeGenMesa;
    }

    public int getGrassColorAtPos(BlockPos blockPos) {
        return 9470285;
    }

    private void method_8e4325f4ac811c35fbd6(long i) {
        boolean flag1 = WorldChunkManager.d();
        this.field_150621_aC = new BlockState[64];
        Arrays.fill(this.field_150621_aC, Blocks.hardened_clay.getDefaultState());
        Random random = new Random(i);
        this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);
        boolean flag = flag1;
        int i2 = 0;

        while (i2 < 64) {
            i2 += random.nextInt(5) + 1;
            if (i2 < 64) {
                this.field_150621_aC[i2] =
                        Blocks.stained_hardened_clay
                                .getDefaultState()
                                .withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
            }

            i2++;
            if (flag) {
                break;
            }
        }

        i2 = random.nextInt(4) + 2;
        int j = 0;

        label104:
        while (j < i2) {
            int k = random.nextInt(3) + 1;
            int l = random.nextInt(64);
            int i1 = 0;

            while (true) {
                if (l + i1 < 64 && i1 < k) {
                    this.field_150621_aC[l + i1] =
                            Blocks.stained_hardened_clay
                                    .getDefaultState()
                                    .withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
                    i1++;
                    if (!flag) {
                        continue;
                    }
                }

                j++;
                if (flag) {
                    break label104;
                }
                break;
            }
        }

        j = random.nextInt(4) + 2;
        int j2 = 0;

        label90:
        while (j2 < j) {
            int k2 = random.nextInt(3) + 2;
            int i3 = random.nextInt(64);
            int j1 = 0;

            while (true) {
                if (i3 + j1 < 64 && j1 < k2) {
                    this.field_150621_aC[i3 + j1] =
                            Blocks.stained_hardened_clay
                                    .getDefaultState()
                                    .withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
                    j1++;
                    if (!flag) {
                        continue;
                    }
                }

                j2++;
                if (flag) {
                    break label90;
                }
                break;
            }
        }

        j2 = random.nextInt(4) + 2;
        int l2 = 0;

        label76:
        while (l2 < j2) {
            int j3 = random.nextInt(3) + 1;
            int l3 = random.nextInt(64);
            int k1 = 0;

            while (true) {
                if (l3 + k1 < 64 && k1 < j3) {
                    this.field_150621_aC[l3 + k1] =
                            Blocks.stained_hardened_clay
                                    .getDefaultState()
                                    .withProperty(BlockColored.COLOR, EnumDyeColor.RED);
                    k1++;
                    if (!flag) {
                        continue;
                    }
                }

                l2++;
                if (flag) {
                    break label76;
                }
                break;
            }
        }

        l2 = random.nextInt(3) + 3;
        int k3 = 0;
        int i4 = 0;

        while (i4 < l2) {
            byte b0 = 1;
            k3 += random.nextInt(16) + 4;
            int l1 = 0;

            while (true) {
                if (k3 + l1 < 64 && l1 < b0) {
                    this.field_150621_aC[k3 + l1] =
                            Blocks.stained_hardened_clay
                                    .getDefaultState()
                                    .withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
                    if (k3 + l1 > 1 && random.nextBoolean()) {
                        this.field_150621_aC[k3 + l1 - 1] =
                                Blocks.stained_hardened_clay
                                        .getDefaultState()
                                        .withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
                    }

                    if (k3 + l1 < 63 && random.nextBoolean()) {
                        this.field_150621_aC[k3 + l1 + 1] =
                                Blocks.stained_hardened_clay
                                        .getDefaultState()
                                        .withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
                    }

                    l1++;
                    if (!flag) {
                        continue;
                    }
                }

                i4++;
                if (flag) {
                    return;
                }
                break;
            }
        }
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return this.worldGeneratorTrees;
    }

    private BlockState func_180629_a(int i, int j, int l) {
        int k =
                (int)
                        Math.round(
                                this.field_150625_aG.func_151601_a(i * 1.0 / 512.0, i * 1.0 / 512.0)
                                        * 2.0);
        return this.field_150621_aC[(j + k + 64) % 64];
    }

    public BiomeGenMesa(int i, boolean aJUValue, boolean aJoValue) {
        super(i);
        this.field_150626_aH = aJUValue;
        this.field_150620_aI = aJoValue;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0F, 0.0F);
        this.spawnableCreatureList.clear();
        this.topBlock =
                Blocks.sand
                        .getDefaultState()
                        .withProperty(BlockSand.VARIANT_PROP, BlockSandEnumType.RED_SAND);
        this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();
        if (aJoValue) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        super.decorate(world, random, blockPos);
    }
}
