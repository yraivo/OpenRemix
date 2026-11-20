package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {
    private static final double[] field_152383_g =
            new double[] {
                0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0
            };
    private static final double[] field_152384_h =
            new double[] {
                1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0
            };
    public double yCoord;
    private int[] permutations;
    private static final double[] field_152382_f =
            new double[] {
                1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0
            };
    public double xCoord;
    private static final double[] field_152381_e =
            new double[] {
                1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0
            };
    public double zCoord;
    private static final double[] field_152385_i =
            new double[] {
                0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0
            };

    public NoiseGeneratorImproved(Random random) {
        this(random, MapGenBase.b());
    }

    private NoiseGeneratorImproved(Random random, String[] initialFlowMarker) {
        super();

        this.permutations = new int[512];
        this.xCoord = random.nextDouble() * 256.0;

        this.yCoord = random.nextDouble() * 256.0;
        this.zCoord = random.nextDouble() * 256.0;
        int i = 0;

        while (i < 256) {
            this.permutations[i] = i++;
        }

        i = 0;

        while (i < 256) {
            int j = random.nextInt(256 - i) + i;
            int k = this.permutations[i];
            this.permutations[i] = this.permutations[j];
            this.permutations[j] = k;
            this.permutations[i + 256] = this.permutations[i];
            i++;
        }
    }

    public NoiseGeneratorImproved() {
        this(new Random());
    }

    public final double func_76309_a(int i, double d0, double d1) {
        int j = i & 15;
        return net.minecraft.world.gen.NoiseGeneratorImproved.field_152384_h[j] * d0
                + net.minecraft.world.gen.NoiseGeneratorImproved.field_152385_i[j] * d1;
    }

    public final double lerp(double d0, double d1, double d2) {
        return d1 + d0 * (d2 - d1);
    }

    public void method_f94e53b6e23d1a0068f9(
            double[] adouble,
            double d0,
            double d1,
            double d2,
            int i,
            int j,
            int k,
            double d3,
            double d4,
            double d5,
            double d6) {
        MapGenBase.b();

        if (j == 1) {

            double d11 = 0.0;
            double d12 = 0.0;
            int i2 = 0;
            double d13 = 1.0 / d6;
            int k2 = 0;

            while (k2 < i) {
                double d7 = d0 + k2 * d3 + this.xCoord;
                int l2 = (int) d7;
                if (d7 < l2) {
                    l2--;
                }

                int i3 = l2 & 0xFF;
                d7 -= l2;
                double d8 = d7 * d7 * d7 * (d7 * (d7 * 6.0 - 15.0) + 10.0);
                int j1 = 0;

                while (j1 < k) {
                    double d9 = d2 + j1 * d5 + this.zCoord;
                    int k1 = (int) d9;
                    if (d9 < k1) {
                        k1--;
                    }

                    int l1 = k1 & 0xFF;
                    d9 -= k1;
                    double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0 - 15.0) + 10.0);
                    int l = this.permutations[i3] + 0;
                    int j3 = this.permutations[l] + l1;
                    int k3 = this.permutations[i3 + 1] + 0;
                    int i1 = this.permutations[k3] + l1;
                    d11 =
                            this.lerp(
                                    d8,
                                    this.func_76309_a(this.permutations[j3], d7, d9),
                                    this.grad(this.permutations[i1], d7 - 1.0, 0.0, d9));
                    d12 =
                            this.lerp(
                                    d8,
                                    this.grad(this.permutations[j3 + 1], d7, 0.0, d9 - 1.0),
                                    this.grad(this.permutations[i1 + 1], d7 - 1.0, 0.0, d9 - 1.0));
                    double d14 = this.lerp(d10, d11, d12);
                    int j2 = i2++;
                    adouble[j2] += d14 * d13;
                    j1++;
                }

                k2++;
            }

            return;
        }

        int i6 = 0;
        double d22 = 1.0 / d6;
        int j6 = -1;

        double d23 = 0.0;
        double d18 = 0.0;
        double d24 = 0.0;
        double d19 = 0.0;
        int k6 = 0;

        while (k6 < i) {
            double d20 = d0 + k6 * d3 + this.xCoord;
            int l6 = (int) d20;
            if (d20 < l6) {
                l6--;
            }

            int i7 = l6 & 0xFF;
            d20 -= l6;
            double d21 = d20 * d20 * d20 * (d20 * (d20 * 6.0 - 15.0) + 10.0);
            int l7 = 0;

            while (l7 < k) {
                double d25 = d2 + l7 * d5 + this.zCoord;
                int i8 = (int) d25;
                if (d25 < i8) {
                    i8--;
                }

                int l3 = i8 & 0xFF;
                d25 -= i8;
                double d15 = d25 * d25 * d25 * (d25 * (d25 * 6.0 - 15.0) + 10.0);
                int i4 = 0;

                while (i4 < j) {
                    double d16 = d1 + i4 * d4 + this.yCoord;
                    int j4 = (int) d16;
                    if (d16 < j4) {
                        j4--;
                    }

                    int k4 = j4 & 0xFF;
                    d16 -= j4;
                    double d17 = d16 * d16 * d16 * (d16 * (d16 * 6.0 - 15.0) + 10.0);
                    if (i4 == 0 || k4 != j6) {
                        j6 = k4;
                        int l4 = this.permutations[i7] + k4;
                        int i5 = this.permutations[l4] + l3;
                        int j5 = this.permutations[l4 + 1] + l3;
                        int k5 = this.permutations[i7 + 1] + k4;
                        int j7 = this.permutations[k5] + l3;
                        int l5 = this.permutations[k5 + 1] + l3;
                        d23 =
                                this.lerp(
                                        d21,
                                        this.grad(this.permutations[i5], d20, d16, d25),
                                        this.grad(this.permutations[j7], d20 - 1.0, d16, d25));
                        d18 =
                                this.lerp(
                                        d21,
                                        this.grad(this.permutations[j5], d20, d16 - 1.0, d25),
                                        this.grad(
                                                this.permutations[l5], d20 - 1.0, d16 - 1.0, d25));
                        d24 =
                                this.lerp(
                                        d21,
                                        this.grad(this.permutations[i5 + 1], d20, d16, d25 - 1.0),
                                        this.grad(
                                                this.permutations[j7 + 1],
                                                d20 - 1.0,
                                                d16,
                                                d25 - 1.0));
                        d19 =
                                this.lerp(
                                        d21,
                                        this.grad(
                                                this.permutations[j5 + 1],
                                                d20,
                                                d16 - 1.0,
                                                d25 - 1.0),
                                        this.grad(
                                                this.permutations[l5 + 1],
                                                d20 - 1.0,
                                                d16 - 1.0,
                                                d25 - 1.0));
                    }

                    double d26 = this.lerp(d17, d23, d18);
                    double d27 = this.lerp(d17, d24, d19);
                    double d28 = this.lerp(d15, d26, d27);
                    int k7 = i6++;
                    adouble[k7] += d28 * d22;
                    i4++;
                }

                l7++;
            }

            k6++;
        }
    }

    public final double grad(int i, double d0, double d1, double d2) {
        int j = i & 15;
        return net.minecraft.world.gen.NoiseGeneratorImproved.field_152381_e[j] * d0
                + net.minecraft.world.gen.NoiseGeneratorImproved.field_152382_f[j] * d1
                + net.minecraft.world.gen.NoiseGeneratorImproved.field_152383_g[j] * d2;
    }
}
