package net.minecraft.world.gen;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class MapGenCavesHell extends MapGenBase {
    protected void method_93a9bbf28d4bd38dd5f5(
            long i, int j, int k, ChunkPrimer chunkPrimer, double d0, double d1, double d2) {
        this.method_ece16e2f451ddb15747a(
                i,
                j,
                k,
                chunkPrimer,
                d0,
                d1,
                d2,
                1.0F + this.rand.nextFloat() * 6.0F,
                0.0F,
                0.0F,
                -1,
                -1,
                0.5);
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
        double d4 = j * 16 + 8;
        double d5 = k * 16 + 8;
        String[] astring1 = MapGenBase.b();
        float f3 = 0.0F;
        float f4 = 0.0F;
        Random random = new Random(i);
        String[] astring = astring1;
        if (i1 <= 0) {
            int j1 = this.range * 16 - 16;
            i1 = j1 - random.nextInt(j1 / 4);
        }

        byte b1 = 0;
        if (l == -1) {
            l = i1 / 2;
            b1 = 1;
        }

        int k1 = random.nextInt(i1 / 2) + i1 / 4;
        int l1 = random.nextInt(6) == 0 ? 1 : 0;

        while (l < i1) {
            double d6;
            double d7;
            {
                d6 = 1.5 + MathHelper.sin(l * (float) Math.PI / i1) * f * 1.0F;
                d7 = d6 * d3;
                float f5 = MathHelper.cos(f2);
                float f6 = MathHelper.sin(f2);
                d0 += MathHelper.cos(f1) * f5;
                d1 += f6;
                d2 += MathHelper.sin(f1) * f5;
                if (l1 != 0) {
                    f2 *= 0.92F;

                    
                } else {

                    f2 *= 0.7F;
                }
            }

            f2 += f4 * 0.1F;
            f1 += f3 * 0.1F;
            f4 *= 0.9F;
            f3 *= 0.75F;
            f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (b1 == 0 && l == k1 && f > 1.0F) {
                this.method_ece16e2f451ddb15747a(
                        random.nextLong(),
                        j,
                        k,
                        chunkPrimer,
                        d0,
                        d1,
                        d2,
                        random.nextFloat() * 0.5F + 0.5F,
                        f1 - (float) (Math.PI / 2),
                        f2 / 3.0F,
                        l,
                        i1,
                        1.0);
                this.method_ece16e2f451ddb15747a(
                        random.nextLong(),
                        j,
                        k,
                        chunkPrimer,
                        d0,
                        d1,
                        d2,
                        random.nextFloat() * 0.5F + 0.5F,
                        f1 + (float) (Math.PI / 2),
                        f2 / 3.0F,
                        l,
                        i1,
                        1.0);
                return;
            }

            if (b1 != 0 || random.nextInt(4) != 0) {
                double d8 = d0 - d4;
                double d9 = d2 - d5;
                double d10 = i1 - l;
                double d11 = f + 2.0F + 16.0F;
                if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
                    return;
                }

                if (d0 >= d4 - 16.0 - d6 * 2.0
                        && d2 >= d5 - 16.0 - d6 * 2.0
                        && d0 <= d4 + 16.0 + d6 * 2.0
                        && d2 <= d5 + 16.0 + d6 * 2.0) {
                    int i2 = MathHelper.floor(d0 - d6) - j * 16 - 1;
                    int j2 = MathHelper.floor(d0 + d6) - j * 16 + 1;
                    int k2 = MathHelper.floor(d1 - d7) - 1;
                    int l2 = MathHelper.floor(d1 + d7) + 1;
                    int i3 = MathHelper.floor(d2 - d6) - k * 16 - 1;
                    int j3 = MathHelper.floor(d2 + d6) - k * 16 + 1;
                    if (i2 < 0) {
                        i2 = 0;
                    }

                    if (j2 > 16) {
                        j2 = 16;
                    }

                    if (k2 < 1) {
                        k2 = 1;
                    }

                    if (l2 > 120) {
                        l2 = 120;
                    }

                    if (i3 < 0) {
                        i3 = 0;
                    }

                    if (j3 > 16) {
                        j3 = 16;
                    }

                    byte b0 = 0;
                    int k3 = i2;

                    while (b0 == 0 && k3 < j2) {
                        int l3 = i3;

                        while (b0 == 0 && l3 < j3) {
                            int i4 = l2 + 1;

                            while (b0 == 0 && i4 >= k2 - 1) {
                                if (i4 >= 0 && i4 < 128) {
                                    BlockState blockState = chunkPrimer.getBlockState(k3, i4, l3);
                                    if (blockState.getBlock() == Blocks.flowing_lava
                                            || blockState.getBlock() == Blocks.lava) {
                                        b0 = 1;
                                    }

                                    if (i4 != k2 - 1
                                            && k3 != i2
                                            && k3 != j2 - 1
                                            && l3 != i3
                                            && l3 != j3 - 1) {
                                        i4 = k2;
                                    }
                                }

                                i4--;
                            }

                            l3++;
                        }

                        k3++;
                    }

                    if (b0 == 0) {
                        k3 = i2;

                        while (k3 < j2) {
                            double d14 = (k3 + j * 16 + 0.5 - d0) / d6;
                            int k4 = i3;

                            while (k4 < j3) {
                                double d12 = (k4 + k * 16 + 0.5 - d2) / d6;
                                int j4 = l2;

                                while (j4 > k2) {
                                    double d13 = (j4 - 1 + 0.5 - d1) / d7;
                                    if (d13 > -0.7 && d14 * d14 + d13 * d13 + d12 * d12 < 1.0) {
                                        BlockState blockStateValue =
                                                chunkPrimer.getBlockState(k3, j4, k4);
                                        if (blockStateValue.getBlock() == Blocks.netherrack
                                                || blockStateValue.getBlock() == Blocks.dirt
                                                || blockStateValue.getBlock() == Blocks.grass) {
                                            chunkPrimer.setBlockState(
                                                    k3, j4, k4, Blocks.air.getDefaultState());
                                        }
                                    }

                                    j4--;
                                }

                                k4++;
                            }

                            k3++;
                        }

                        if (b1 != 0 && astring != null) {
                            break;
                        }
                    }
                }
            }

            l++;
        }
    }

    @Override
    protected void recursiveGenerate(
            World world, int i, int j, int k, int l, ChunkPrimer chunkPrimer) {
        MapGenBase.b();

        int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);

        if (this.rand.nextInt(5) != 0) {
            i1 = 0;
        }

        int j1 = 0;

        while (j1 < i1) {
            double d0 = i * 16 + this.rand.nextInt(16);
            double d1 = this.rand.nextInt(128);
            double d2 = j * 16 + this.rand.nextInt(16);
            int k1 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.method_93a9bbf28d4bd38dd5f5(
                        this.rand.nextLong(), k, l, chunkPrimer, d0, d1, d2);
                k1 += this.rand.nextInt(4);
            }

            int l1 = 0;

            while (true) {
                if (l1 < k1) {
                    float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
                    float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                    float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
                    this.method_ece16e2f451ddb15747a(
                            this.rand.nextLong(),
                            k,
                            l,
                            chunkPrimer,
                            d0,
                            d1,
                            d2,
                            f2 * 2.0F,
                            f,
                            f1,
                            0,
                            0,
                            0.5);
                    l1++;

                    continue;
                }

                j1++;

                break;
            }
        }
    }
}
