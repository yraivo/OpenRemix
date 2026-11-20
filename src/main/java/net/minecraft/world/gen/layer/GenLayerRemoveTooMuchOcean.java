package net.minecraft.world.gen.layer;

public class GenLayerRemoveTooMuchOcean extends GenLayer {
    public GenLayerRemoveTooMuchOcean(long i, GenLayer bIFValue) {
        super(i);
        this.parent = bIFValue;
    }

    public int[] getInts(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        int k1 = k + 2;
        int l1 = l + 2;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(k * l);

        int i2 = 0;

        while (i2 < l) {
            int j2 = 0;

            while (true) {
                if (j2 < k) {
                    int k2 = aint[j2 + 1 + (i2 + 1 - 1) * (k + 2)];
                    int l2 = aint[j2 + 1 + 1 + (i2 + 1) * (k + 2)];
                    int i3 = aint[j2 + 1 - 1 + (i2 + 1) * (k + 2)];
                    int j3 = aint[j2 + 1 + (i2 + 1 + 1) * (k + 2)];
                    int k3 = aint[j2 + 1 + (i2 + 1) * k1];
                    aint1[j2 + i2 * k] = k3;
                    this.method_91c6fd2b8b1ce492fa7c(j2 + i, i2 + j);
                    if (k3 == 0
                            && k2 == 0
                            && l2 == 0
                            && i3 == 0
                            && j3 == 0
                            && this.nextInt(2) == 0) {
                        aint1[j2 + i2 * k] = 1;
                    }

                    j2++;

                    continue;
                }

                i2++;

                break;
            }
        }

        return aint1;
    }
}
