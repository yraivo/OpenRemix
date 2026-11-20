package net.minecraft.world.gen.layer;

public class GenLayerSmooth extends GenLayer {
    public int[] getInts(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int k1 = k + 2;

        int l1 = l + 2;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(k * l);
        int i2 = 0;

        while (i2 < l) {
            int j2 = 0;

            while (true) {
                if (j2 < k) {
                    int k3;
                    {
                        int k2 = aint[j2 + 0 + (i2 + 1) * k1];
                        int l2 = aint[j2 + 2 + (i2 + 1) * k1];
                        int i3 = aint[j2 + 1 + (i2 + 0) * k1];
                        int j3 = aint[j2 + 1 + (i2 + 2) * k1];
                        k3 = aint[j2 + 1 + (i2 + 1) * k1];
                        if (k2 == l2 && i3 == j3) {
                            this.method_91c6fd2b8b1ce492fa7c(j2 + i, i2 + j);
                            if (this.nextInt(2) == 0) {
                                k3 = k2;

                                
                            } else {

                                k3 = i3;


                            }
                        } else {

                            if (k2 == l2) {
                                k3 = k2;
                            }

                            if (i3 == j3) {
                                k3 = i3;
                            }
                        }
                    }

                    aint1[j2 + i2 * k] = k3;
                    j2++;

                    continue;
                }

                i2++;

                break;
            }
        }

        return aint1;
    }

    public GenLayerSmooth(long i, GenLayer bIFValue) {
        super(i);
        super.parent = bIFValue;
    }
}
