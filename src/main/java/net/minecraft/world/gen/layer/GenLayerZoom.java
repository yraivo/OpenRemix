package net.minecraft.world.gen.layer;

public class GenLayerZoom extends GenLayer {
    public GenLayerZoom(long i, GenLayer bIFValue) {
        super(i);
        super.parent = bIFValue;
    }

    public int[] getInts(int i, int j, int k, int l) {
        int i1 = i >> 1;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int j1 = j >> 1;
        int k1 = (k >> 1) + 2;
        int l1 = (l >> 1) + 2;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int i2 = k1 - 1 << 1;
        int j2 = l1 - 1 << 1;

        int[] aint1 = IntCache.getIntCache(i2 * j2);
        int l2 = 0;

        while (l2 < l1 - 1) {
            int k2 = (l2 << 1) * i2;
            int i3 = 0;
            int j3 = aint[i3 + 0 + (l2 + 0) * k1];
            int k3 = aint[i3 + 0 + (l2 + 1) * k1];

            while (true) {
                if (i3 < k1 - 1) {
                    this.method_91c6fd2b8b1ce492fa7c(i3 + i1 << 1, l2 + j1 << 1);
                    int l3 = aint[i3 + 1 + (l2 + 0) * k1];
                    int i4 = aint[i3 + 1 + (l2 + 1) * k1];
                    aint1[k2] = j3;
                    aint1[k2++ + i2] = this.selectRandom(new int[] {j3, k3});
                    aint1[k2] = this.selectRandom(new int[] {j3, l3});
                    aint1[k2++ + i2] = this.selectModeOrRandom(j3, l3, k3, i4);
                    j3 = l3;
                    k3 = i4;
                    i3++;

                    continue;
                }

                l2++;

                break;
            }
        }

        int[] aint2 = IntCache.getIntCache(k * l);
        int j4 = 0;

        while (j4 < l) {
            System.arraycopy(aint1, (j4 + (j & 1)) * i2 + (i & 1), aint2, j4 * k, k);
            j4++;
        }

        return aint2;
    }

    public static GenLayer magnify(long i, GenLayer genLayer, int j) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        GenLayer genLayerValue = genLayer;

        int k = 0;

        while (k < j) {
            genLayerValue = new GenLayerZoom(i + k, genLayerValue);
            k++;
        }

        return genLayerValue;
    }
}
