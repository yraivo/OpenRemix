package net.minecraft.world.gen;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class MapGenRavine extends MapGenBase {
    private float[] bLC = new float[1024];

    protected void recursiveGenerate(
            World world, int i, int j, int k, int l, ChunkPrimer chunkPrimer) {
        MapGenBase.b();

        if (this.rand.nextInt(50) == 0) {
            double d0 = i * 16 + this.rand.nextInt(16);
            double d1 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
            double d2 = j * 16 + this.rand.nextInt(16);
            byte b0 = 1;
            int i1 = 0;

            while (i1 < b0) {
                float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
                this.method_ece16e2f451ddb15747a(
                        this.rand.nextLong(), k, l, chunkPrimer, d0, d1, d2, f2, f, f1, 0, 0, 3.0);
                i1++;
            }
        }
    }

    protected void method_ece16e2f451ddb15747a(
            long i,
            int j,
            int k,
            ChunkPrimer chunkPrimer,
            double d0,
            double d1,
            double d2,
            float f,
            float f1,
            float f2,
            int l,
            int i1,
            double d3) {
        Random random = new Random(i);
        double d4 = j * 16 + 8;
        String[] astring1 = MapGenBase.b();
        double d5 = k * 16 + 8;
        String[] astring = astring1;
        float f3 = 0.0F;
        float f4 = 0.0F;
        if (i1 <= 0) {
            int j1 = this.range * 16 - 16;
            i1 = j1 - random.nextInt(j1 / 4);
        }

        byte b2 = 0;
        if (l == -1) {
            l = i1 / 2;
            b2 = 1;
        }

        float f5 = 1.0F;
        int k1 = 0;

        while (k1 < 256) {
            if (k1 == 0 || random.nextInt(3) == 0) {
                f5 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
            }

            this.bLC[k1] = f5 * f5;
            k1++;
        }

        while (l < i1) {
            double d13 = 1.5 + MathHelper.sin(l * (float) Math.PI / i1) * f * 1.0F;
            double d6 = d13 * d3;
            double d14 = d13 * (random.nextFloat() * 0.25 + 0.75);
            d6 *= random.nextFloat() * 0.25 + 0.75;
            float f6 = MathHelper.cos(f2);
            float f7 = MathHelper.sin(f2);
            d0 += MathHelper.cos(f1) * f6;
            d1 += f7;
            d2 += MathHelper.sin(f1) * f6;
            f2 *= 0.7F;
            f2 += f4 * 0.05F;
            f1 += f3 * 0.05F;
            f4 *= 0.8F;
            f3 *= 0.5F;
            f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (b2 != 0 || random.nextInt(4) != 0) {
                double d7 = d0 - d4;
                double d8 = d2 - d5;
                double d9 = i1 - l;
                double d10 = f + 2.0F + 16.0F;
                if (d7 * d7 + d8 * d8 - d9 * d9 > d10 * d10) {
                    return;
                }

                if (d0 >= d4 - 16.0 - d14 * 2.0
                        && d2 >= d5 - 16.0 - d14 * 2.0
                        && d0 <= d4 + 16.0 + d14 * 2.0
                        && d2 <= d5 + 16.0 + d14 * 2.0) {
                    int l1 = MathHelper.floor(d0 - d14) - j * 16 - 1;
                    int i2 = MathHelper.floor(d0 + d14) - j * 16 + 1;
                    int j2 = MathHelper.floor(d1 - d6) - 1;
                    int k2 = MathHelper.floor(d1 + d6) + 1;
                    int l2 = MathHelper.floor(d2 - d14) - k * 16 - 1;
                    int i3 = MathHelper.floor(d2 + d14) - k * 16 + 1;
                    if (l1 < 0) {
                        l1 = 0;
                    }

                    if (i2 > 16) {
                        i2 = 16;
                    }

                    if (j2 < 1) {
                        j2 = 1;
                    }

                    if (k2 > 248) {
                        k2 = 248;
                    }

                    if (l2 < 0) {
                        l2 = 0;
                    }

                    if (i3 > 16) {
                        i3 = 16;
                    }

                    byte b0 = 0;
                    int j3 = l1;

                    while (b0 == 0 && j3 < i2) {
                        int k3 = l2;

                        while (b0 == 0 && k3 < i3) {
                            int l3 = k2 + 1;

                            while (b0 == 0 && l3 >= j2 - 1) {
                                if (l3 >= 0 && l3 < 256) {
                                    BlockState blockState = chunkPrimer.getBlockState(j3, l3, k3);
                                    if (blockState.getBlock() == Blocks.flowing_water
                                            || blockState.getBlock() == Blocks.water) {
                                        b0 = 1;
                                    }

                                    if (l3 != j2 - 1
                                            && j3 != l1
                                            && j3 != i2 - 1
                                            && k3 != l2
                                            && k3 != i3 - 1) {
                                        l3 = j2;
                                    }
                                }

                                l3--;
                            }

                            k3++;
                        }

                        j3++;
                    }

                    if (b0 == 0) {
                        j3 = l1;

                        while (j3 < i2) {
                            double d15 = (j3 + j * 16 + 0.5 - d0) / d14;
                            int j4 = l2;

                            while (j4 < i3) {
                                double d11 = (j4 + k * 16 + 0.5 - d2) / d14;
                                byte b1 = 0;
                                if (d15 * d15 + d11 * d11 < 1.0) {
                                    int i4 = k2;

                                    while (i4 > j2) {
                                        double d12 = (i4 - 1 + 0.5 - d1) / d6;
                                        if ((d15 * d15 + d11 * d11) * this.bLC[i4 - 1]
                                                        + d12 * d12 / 6.0
                                                < 1.0) {
                                            BlockState blockStateValue =
                                                    chunkPrimer.getBlockState(j3, i4, j4);
                                            if (blockStateValue.getBlock() == Blocks.grass) {
                                                b1 = 1;
                                            }

                                            if (blockStateValue.getBlock() == Blocks.stone
                                                    || blockStateValue.getBlock() == Blocks.dirt
                                                    || blockStateValue.getBlock() == Blocks.grass) {
                                                if (i4 - 1 < 10) {
                                                    chunkPrimer.setBlockState(
                                                            j3,
                                                            i4,
                                                            j4,
                                                            Blocks.flowing_lava.getDefaultState());

                                                    
                                                } else {

                                                    chunkPrimer.setBlockState(
                                                            j3, i4, j4, Blocks.air.getDefaultState());
                                                    if (b1 != 0
                                                            && chunkPrimer
                                                                            .getBlockState(
                                                                                    j3, i4 - 1, j4)
                                                                            .getBlock()
                                                                    == Blocks.dirt) {
                                                        chunkPrimer.setBlockState(
                                                                j3,
                                                                i4 - 1,
                                                                j4,
                                                                this.worldObj.getBiomeGenForCoords(
                                                                                new BlockPos(
                                                                                        j3 + j * 16,
                                                                                        0,
                                                                                        j4 + k * 16))
                                                                        .topBlock);
                                                    }
                                                }
                                            }
                                        }

                                        i4--;
                                    }
                                }

                                j4++;
                            }

                            j3++;
                        }

                        if (b2 != 0 && astring != null) {
                            break;
                        }
                    }
                }
            }

            l++;
        }
    }
}
