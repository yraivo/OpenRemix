package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorSimplex {
    private static final double iz;
    private static final double iw;
    public static final double um;
    public double rd;
    public double ix;
    private int[] field_151608_f = new int[512];
    public double ul;
    private static int[][] field_151611_e;

    static {
        net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e =
                new int[][] {
                    {1, 1, 0},
                    {-1, 1, 0},
                    {1, -1, 0},
                    {-1, -1, 0},
                    {1, 0, 1},
                    {-1, 0, 1},
                    {1, 0, -1},
                    {-1, 0, -1},
                    {0, 1, 1},
                    {0, -1, 1},
                    {0, 1, -1},
                    {0, -1, -1}
                };
        um = Math.sqrt(3.0);
        iz = 0.5 * (NoiseGeneratorSimplex.um - 1.0);
        iw = (3.0 - NoiseGeneratorSimplex.um) / 6.0;
    }

    public double method_e371b491639ecd96d2dc(double d0, double d1) {
        String[] astring;
        int i;
        int j;
        double d4;
        double d8;
        double d9;
        byte b0;
        byte b1;
        {
            double d2 = 0.5 * (um - 1.0);
            double d3 = (d0 + d1) * d2;
            i = method_d529d072fec854a03c46(d0 + d3);
            j = method_d529d072fec854a03c46(d1 + d3);
            String[] astring1 = MapGenBase.b();
            d4 = (3.0 - um) / 6.0;
            double d5 = (i + j) * d4;
            double d6 = i - d5;
            double d7 = j - d5;
            astring = astring1;
            d8 = d0 - d6;
            d9 = d1 - d7;
            if (d8 > d9) {
                b0 = 1;
                b1 = 0;

                
            } else {

                b0 = 0;
                b1 = 1;
            }
        }

        double d10;
        double d11;
        double d12;
        double d13;
        int j1;
        int k1;
        double d15;
        {
            d10 = d8 - b0 + d4;
            d11 = d9 - b1 + d4;
            d12 = d8 - 1.0 + 2.0 * d4;
            d13 = d9 - 1.0 + 2.0 * d4;
            int kx = i & 0xFF;
            int l = j & 0xFF;
            int i1 = this.field_151608_f[kx + this.field_151608_f[l]] % 12;
            j1 = this.field_151608_f[kx + b0 + this.field_151608_f[l + b1]] % 12;
            k1 = this.field_151608_f[kx + 1 + this.field_151608_f[l + 1]] % 12;
            double d14 = 0.5 - d8 * d8 - d9 * d9;
            if (d14 < 0.0) {
                d15 = 0.0;

                
            } else {

                d14 *= d14;
                d15 =
                        d14
                                * d14
                                * method_7ce97e44b7182cc3eed9(
                                        net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e[i1], d8, d9);
            }
        }

        double d17;
        {
            double d16 = 0.5 - d10 * d10 - d11 * d11;
            if (d16 < 0.0) {
                d17 = 0.0;

                
            } else {

                d16 *= d16;
                d17 =
                        d16
                                * d16
                                * method_7ce97e44b7182cc3eed9(
                                        net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e[j1], d10, d11);
            }
        }

        double d18 = 0.5 - d12 * d12 - d13 * d13;
        if (d18 < 0.0) {
            double d19 = 0.0;

            return 70.0 * (d15 + d17 + d19);
        }

        d18 *= d18;
        double d20 =
                d18
                        * d18
                        * method_7ce97e44b7182cc3eed9(
                                net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e[k1], d12, d13);
        return 70.0 * (d15 + d17 + d20);
    }

    public void method_d08868c0a0f6f8752634(
            double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4) {
        int kx = 0;
        MapGenBase.b();

        int l = 0;

        while (l < j) {
            double d5 = (d1 + l) * d3 + this.rd;
            int i1 = 0;

            while (true) {
                if (i1 < i) {
                    int j1;
                    int k1;
                    double d11;
                    double d12;
                    byte b0;
                    byte b1;
                    {
                        double d6 = (d0 + i1) * d2 + this.ul;
                        double d7 = (d6 + d5) * iz;
                        j1 = method_d529d072fec854a03c46(d6 + d7);
                        k1 = method_d529d072fec854a03c46(d5 + d7);
                        double d8 = (j1 + k1) * iw;
                        double d9 = j1 - d8;
                        double d10 = k1 - d8;
                        d11 = d6 - d9;
                        d12 = d5 - d10;
                        if (d11 > d12) {
                            b0 = 1;
                            b1 = 0;

                            
                        } else {

                            b0 = 0;
                            b1 = 1;
                        }
                    }

                    double d13;
                    double d14;
                    double d15;
                    double d16;
                    int k2;
                    int l2;
                    double d18;
                    {
                        d13 = d11 - b0 + iw;
                        d14 = d12 - b1 + iw;
                        d15 = d11 - 1.0 + 2.0 * iw;
                        d16 = d12 - 1.0 + 2.0 * iw;
                        int l1 = j1 & 0xFF;
                        int i2 = k1 & 0xFF;
                        int j2 = this.field_151608_f[l1 + this.field_151608_f[i2]] % 12;
                        k2 = this.field_151608_f[l1 + b0 + this.field_151608_f[i2 + b1]] % 12;
                        l2 = this.field_151608_f[l1 + 1 + this.field_151608_f[i2 + 1]] % 12;
                        double d17 = 0.5 - d11 * d11 - d12 * d12;
                        if (d17 < 0.0) {
                            d18 = 0.0;

                            
                        } else {

                            d17 *= d17;
                            d18 =
                                    d17
                                            * d17
                                            * method_7ce97e44b7182cc3eed9(
                                                    net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e[j2],
                                                    d11,
                                                    d12);
                        }
                    }

                    double d20;
                    {
                        double d19 = 0.5 - d13 * d13 - d14 * d14;
                        if (d19 < 0.0) {
                            d20 = 0.0;

                            
                        } else {

                            d19 *= d19;
                            d20 =
                                    d19
                                            * d19
                                            * method_7ce97e44b7182cc3eed9(
                                                    net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e[k2],
                                                    d13,
                                                    d14);
                        }
                    }

                    double d22;
                    {
                        double d21 = 0.5 - d15 * d15 - d16 * d16;
                        if (d21 < 0.0) {
                            d22 = 0.0;

                            
                        } else {

                            d21 *= d21;
                            d22 =
                                    d21
                                            * d21
                                            * method_7ce97e44b7182cc3eed9(
                                                    net.minecraft.world.gen.NoiseGeneratorSimplex.field_151611_e[l2],
                                                    d15,
                                                    d16);
                        }
                    }

                    int i3 = kx++;
                    adouble[i3] += 70.0 * (d18 + d20 + d22) * d4;
                    i1++;

                    continue;
                }

                l++;

                break;
            }
        }
    }

    private static double method_7ce97e44b7182cc3eed9(int[] aint, double d0, double d1) {
        return aint[0] * d0 + aint[1] * d1;
    }

    public NoiseGeneratorSimplex(Random random) {
        this.ul = random.nextDouble() * 256.0;
        this.rd = random.nextDouble() * 256.0;
        this.ix = random.nextDouble() * 256.0;
        int i = 0;

        while (i < 256) {
            this.field_151608_f[i] = i++;
        }

        for (int l = 0; l < 256; l++) {
            int j = random.nextInt(256 - l) + l;
            int kx = this.field_151608_f[l];
            this.field_151608_f[l] = this.field_151608_f[j];
            this.field_151608_f[j] = kx;
            this.field_151608_f[l + 256] = this.field_151608_f[l];
        }
    }

    private static int method_d529d072fec854a03c46(double d0) {
        return d0 > 0.0 ? (int) d0 : (int) d0 - 1;
    }

    public NoiseGeneratorSimplex() {
        this(new Random());
    }
}
