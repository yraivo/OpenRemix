package net.minecraft.world;

public class ColorizerFoliage {
    private static int[] foliageBuffer = new int[65536];

    public static int getFoliageColorBasic() {
        return 4764952;
    }

    public static int getFoliageColorPine() {
        return 6396257;
    }

    public static void setFoliageBiomeColorizer(int[] aint) {
        net.minecraft.world.ColorizerFoliage.foliageBuffer = aint;
    }

    public static int getFoliageColor(double p171_0, double p171_1) {
        p171_1 *= p171_0;
        int i = (int) ((1.0 - p171_0) * 255.0);
        int j = (int) ((1.0 - p171_1) * 255.0);
        return net.minecraft.world.ColorizerFoliage.foliageBuffer[j << 8 | i];
    }

    public static int getFoliageColorBirch() {
        return 8431445;
    }
}
