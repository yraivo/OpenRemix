package net.minecraft.util;

import java.util.Random;
import java.util.UUID;

public class MathHelper {
    public static final float PI = (float) Math.PI;
    private static final float ia = 11.377778F;
    private static final float[] fastSinTable = new float[4096];
    public static boolean fastMath = false;
    private static final int a = 4095;
    private static int mE;
    private static final int cz = 4096;
    private static final float[] sinTable = new float[65536];
    public static final float DEGREES_TO_RADIANS = (float) (Math.PI / 180.0);
    private static final float ic = 360.0F;
    private static final float hY = 651.8986F;
    private static int[] multiplyDeBruijnBitPosition;

    public static double parseDoubleWithDefaultAndMinimum(String text, double d0, double d1) {
        return Math.max(d1, parseDoubleWithDefault(text, d0));
    }

    public static int truncateDoubleToInt(double d0) {
        return (int) (d0 + 1024.0) - 1024;
    }

    public static float nextFloat(Random random, float f, float f1) {
        return f >= f1 ? f : random.nextFloat() * (f1 - f) + f;
    }

    public static float clamp(float f, float f1, float f2) {
        return f < f1 ? f1 : (f > f2 ? f2 : f);
    }

    public static int positiveModulo(int i, int j) {
        return (i % j + j) % j;
    }

    public static int absFloor(double d0) {
        return (int) (d0 >= 0.0 ? d0 : -d0 + 1.0);
    }

    public static float abs(float f) {
        return f >= 0.0F ? f : -f;
    }

    public static long coordinateRandom(int i, int j, int k) {
        long i1 = i * 3129871;
        long j1 = k;

        long l = i1 ^ j1 * 116129781L ^ j;
        i1 = l * l;

        i1 *= 42317861L;

        return i1 + l * 11L;
    }

    public static boolean epsilonEquals(float f, float f1) {
        return abs(f1 - f) < 1.0E-5F;
    }

    public static int floorLog2(int i) {
        return log2DeBruijn(i) - (isPowerOfTwo(i) ? 0 : 1);
    }

    public static int floorDiv(int i, int j) {
        return i < 0 ? -((-i - 1) / j) - 1 : i / j;
    }

    private static int log2DeBruijn(int i) {
        i = isPowerOfTwo(i) ? i : roundUpToPowerOfTwo(i);
        long j = i;

        return net.minecraft.util.MathHelper.multiplyDeBruijnBitPosition[(int) (j * 125613361L >> 27) & 31];
    }

    public static long positionRandom(Vec3i vec3i) {
        return coordinateRandom(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public static int floor(double d0) {
        int i = (int) d0;
        return d0 < i ? i - 1 : i;
    }

    public static void method_09e43cd8dae4fda1c39e(int i) {
        mE = i;
    }

    private static boolean isPowerOfTwo(int i) {
        return i != 0 && (i & i - 1) == 0;
    }

    public static int abs(int i) {
        return i >= 0 ? i : -i;
    }

    public static UUID randomUuid(Random random) {
        long k = random.nextLong();

        k &= -61441L;

        long i = k | 16384L;
        k = random.nextLong();

        k &= 4611686018427387903L;

        long j = k | -9223372036854775808L;
        return new UUID(i, j);
    }

    public static float sin(float f) {
        return fastMath
                ? fastSinTable[(int) (f * 651.8986F) & 4095]
                : sinTable[(int) (f * 10430.378F) & 65535];
    }

    public static int method_7eec03da329a017ec143() {
        return mE;
    }

    public static float cos(float f) {
        return fastMath
                ? fastSinTable[(int) ((f + (float) (Math.PI / 2)) * 651.8986F) & 4095]
                : sinTable[(int) (f * 10430.378F + 16384.0F) & 65535];
    }

    // region Static initialization and string tables
    static {
        method_09e43cd8dae4fda1c39e(0);

        for (int i = 0; i < 65536; i++) {
            sinTable[i] = (float) Math.sin(i * Math.PI * 2.0 / 65536.0);
        }

        net.minecraft.util.MathHelper.multiplyDeBruijnBitPosition =
                new int[] {
                    0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21,
                    19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
                };

        for (int j = 0; j < 4096; j++) {
            fastSinTable[j] = (float) Math.sin((j + 0.5F) / 4096.0F * (float) (Math.PI * 2));
        }

        for (int b0 = 0; b0 < 360; b0 += 90) {
            fastSinTable[(int) (b0 * 11.377778F) & 4095] =
                    (float) Math.sin(b0 * (float) (Math.PI / 180.0));
        }
    }

    // endregion

    public static double clampedLerp(double d0, double d1, double d2) {
        return d2 < 0.0 ? d0 : (d2 > 1.0 ? d1 : d0 + (d1 - d0) * d2);
    }

    public static int ceil(float f) {
        int i = (int) f;
        return f > i ? i + 1 : i;
    }

    public static int multiplyColor(int i, int j) {
        int k = (i & 0xFF0000) >> 16;
        int l = (j & 0xFF0000) >> 16;
        int i1 = (i & 0xFF00) >> 8;
        int j1 = (j & 0xFF00) >> 8;
        int k1 = (i & 0xFF) >> 0;
        int l1 = (j & 0xFF) >> 0;
        int i2 = (int) ((float) k * l / 255.0F);
        int j2 = (int) ((float) i1 * j1 / 255.0F);
        int k2 = (int) ((float) k1 * l1 / 255.0F);
        return i & 0xFF000000 | i2 << 16 | j2 << 8 | k2;
    }

    public static long floorLong(double d0) {
        long i = (long) d0;
        long j;
        if (d0 < i) {

            j = i - 1L;
        } else {
            j = i;
        }

        return j;
    }

    public static int packRgb(int i, int j, int k) {
        int l = (i << 8) + j;
        return (l << 8) + k;
    }

    public static int roundUpToPowerOfTwo(int i) {
        int j = i - 1;
        j |= j >> 1;
        j |= j >> 2;
        j |= j >> 4;
        j |= j >> 8;
        j |= j >> 16;
        return j + 1;
    }

    public static int ceil(double d0) {
        int i = (int) d0;
        return d0 > i ? i + 1 : i;
    }

    public static double average(long[] along) {

        long i = 0L;
        long[] along1 = along;
        int j = along.length;
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int k = 0;
        String textValue = text;

        while (k < j) {
            long l = along1[k];
            i += l;
            k++;
            if (textValue == null) {
                break;
            }
        }

        return (double) i / along.length;
    }

    public static int roundUpToMultiple(int i, int j) {
        if (j == 0) {
            return 0;
        }

        if (i == 0) {
            return j;
        }

        if (i < 0) {
            j *= -1;
        }

        int k = i % j;
        return k == 0 ? i : i + j - k;
    }

    public static double nextDouble(Random random, double d0, double d1) {
        return d0 >= d1 ? d0 : random.nextDouble() * (d1 - d0) + d0;
    }

    public static double parseDoubleWithDefault(String text, double d0) {
        try {
            return Double.parseDouble(text);
        } catch (Throwable throwable) {
            return d0;
        }
    }

    public static int clamp(int i, int j, int k) {
        return i < j ? j : (i > k ? k : i);
    }

    public static double clamp(double d0, double d1, double d2) {
        return d0 < d1 ? d1 : (d0 > d2 ? d2 : d0);
    }

    public static int parseIntWithDefaultAndMinimum(String text, int i, int j) {
        return Math.max(j, parseIntWithDefault(text, i));
    }

    public static float sqrt(double d0) {
        return (float) Math.sqrt(d0);
    }

    public static int nextIntInclusive(Random random, int i, int j) {
        return i >= j ? i : random.nextInt(j - i + 1) + i;
    }

    public static float wrapAngleTo180(float f) {
        f %= 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }

        if (f < -180.0F) {
            f += 360.0F;
        }

        return f;
    }

    public static double maxAbs(double d0, double d1) {
        if (d0 < 0.0) {
            d0 = -d0;
        }

        if (d1 < 0.0) {
            d1 = -d1;
        }

        return d0 > d1 ? d0 : d1;
    }

    public static int parseIntWithDefault(String text, int i) {
        try {
            return Integer.parseInt(text);
        } catch (Throwable throwable) {
            return i;
        }
    }

    public static int floor(float f) {
        int i = (int) f;
        return f < i ? i - 1 : i;
    }

    public static int packNormalizedRgb(float f, float f1, float f2) {
        return packRgb(floor(f * 255.0F), floor(f1 * 255.0F), floor(f2 * 255.0F));
    }

    public static double wrapAngleTo180(double d0) {
        d0 %= 360.0;
        if (d0 >= 180.0) {
            d0 -= 360.0;
        }

        if (d0 < -180.0) {
            d0 += 360.0;
        }

        return d0;
    }

    public static float sqrt(float f) {
        return (float) Math.sqrt(f);
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 126 : 0;
    }
}
