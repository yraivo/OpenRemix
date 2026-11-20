package net.minecraft.world.gen;

import net.minecraft.util.MathHelper;

import java.util.Random;

public class NoiseGeneratorOctaves extends NoiseGenerator {
    private int octaves;
    private NoiseGeneratorImproved[] generatorCollection;

    public double[] generateNoiseOctaves(
            double[] adouble, int i, int j, int k, int l, double d0, double d1, double d2) {
        return this.generateNoiseOctaves(adouble, i, 10, j, k, 1, l, d0, 1.0, d1);
    }

    public NoiseGeneratorOctaves(Random random, int octavesValue) {
        MapGenBase.b();

        this.octaves = octavesValue;

        this.generatorCollection = new NoiseGeneratorImproved[octavesValue];
        int j = 0;

        while (j < octavesValue) {
            this.generatorCollection[j] = new NoiseGeneratorImproved(random);
            j++;
        }
    }

    public double[] generateNoiseOctaves(
            double[] adouble,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            double d0,
            double d1,
            double d2) {
        String[] astring;
        {
            astring = MapGenBase.b();
            if (adouble == null) {
                adouble = new double[l * i1 * j1];

                
            } else {

                int k1 = 0;

                while (k1 < adouble.length) {
                    adouble[k1] = 0.0;
                    k1++;
                }
            }
        }

        double d6 = 1.0;
        int l1 = 0;

        while (l1 < this.octaves) {
            double d3 = i * d6 * d0;
            double d4 = j * d6 * d1;
            double d5 = k * d6 * d2;
            long i2 = MathHelper.floorLong(d3);
            long j2 = MathHelper.floorLong(d5);
            d3 -= i2;
            d5 -= j2;

            i2 %= 16777216L;

            j2 %= 16777216L;
            d3 += i2;
            d5 += j2;
            this.generatorCollection[l1].method_f94e53b6e23d1a0068f9(
                    adouble, d3, d4, d5, l, i1, j1, d0 * d6, d1 * d6, d2 * d6, d6);
            d6 /= 2.0;
            l1++;
        }

        return adouble;
    }
}
