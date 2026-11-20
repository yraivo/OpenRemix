package net.minecraft.world;

public class ColorizerGrass {
    private static int[] grassBuffer = new int[65536];

    public static void setGrassBiomeColorizer(int[] aint) {
        net.minecraft.world.ColorizerGrass.grassBuffer = aint;
    }

    public static int getGrassColor(double p171_0, double p171_1) {
        p171_1 *= p171_0;
        int i = (int) ((1.0 - p171_0) * 255.0);
        int j = (int) ((1.0 - p171_1) * 255.0);
        int k = j << 8 | i;
        return k > net.minecraft.world.ColorizerGrass.grassBuffer.length
                ? -65281
                : net.minecraft.world.ColorizerGrass.grassBuffer[k];
    }
}
