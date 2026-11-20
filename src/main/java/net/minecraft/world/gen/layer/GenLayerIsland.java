package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer {
    public int[] getInts(int i, int j, int k, int l) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint = IntCache.getIntCache(k * l);

        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    this.method_91c6fd2b8b1ce492fa7c(i + j1, j + i1);
                    aint[j1 + i1 * k] = this.nextInt(10) == 0 ? 1 : 0;
                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }

        if (i > -k && i <= 0 && j > -l && j <= 0) {
            aint[-i + -j * k] = 1;
        }

        return aint;
    }

    public GenLayerIsland(long i) {
        super(i);
    }
}
