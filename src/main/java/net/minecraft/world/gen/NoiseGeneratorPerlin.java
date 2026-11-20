package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
    private int field_151602_b;
    private NoiseGeneratorSimplex[] field_151603_a;

    public NoiseGeneratorPerlin(Random random, int i) {
        this.field_151602_b = i;
        this.field_151603_a = new NoiseGeneratorSimplex[i];

        for (int j = 0; j < i; j++) {
            this.field_151603_a[j] = new NoiseGeneratorSimplex(random);
        }
    }

    public double[] func_151599_a(
            double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4) {
        return this.func_151600_a(adouble, d0, d1, i, j, d2, d3, d4, 0.5);
    }

    public double func_151601_a(double d0, double d1) {
        MapGenBase.b();

        double d2 = 0.0;

        double d3 = 1.0;
        int i = 0;

        while (i < this.field_151602_b) {
            d2 += this.field_151603_a[i].method_e371b491639ecd96d2dc(d0 * d3, d1 * d3) / d3;
            d3 /= 2.0;
            i++;
        }

        return d2;
    }

    public double[] func_151600_a(
            double[] adouble,
            double d0,
            double d1,
            int i,
            int j,
            double d2,
            double d3,
            double d4,
            double d5) {
        String[] astring;
        {
            astring = MapGenBase.b();
            if (adouble != null && adouble.length >= i * j) {
                int k = 0;

                while (k < adouble.length) {
                    adouble[k] = 0.0;
                    k++;
                }

                
            } else {

                adouble = new double[i * j];
            }
        }

        double d7 = 1.0;
        double d6 = 1.0;
        int l = 0;

        while (l < this.field_151602_b) {
            this.field_151603_a[l].method_d08868c0a0f6f8752634(
                    adouble, d0, d1, i, j, d2 * d6 * d7, d3 * d6 * d7, 0.55 / d7);
            d6 *= d4;
            d7 *= d5;
            l++;
        }

        return adouble;
    }
}
