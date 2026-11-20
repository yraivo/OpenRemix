package net.minecraft.world.gen.layer;

public class GenLayerRiverInit extends GenLayer {
    public GenLayerRiverInit(long i, GenLayer bIFValue) {
        super(i);
        this.parent = bIFValue;
    }

    public int[] getInts(int i, int j, int k, int l) {
        int[] aint = this.parent.getInts(i, j, k, l);
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint1 = IntCache.getIntCache(k * l);

        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                    aint1[j1 + i1 * k] =
                            aint[j1 + i1 * k] > 0
                                    ? this.nextInt(299999) + 2
                                    : 0;
                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }

        return aint1;
    }
}
