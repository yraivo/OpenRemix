package net.minecraft.world.gen.layer;

public class GenLayerEdge extends GenLayer {
    private GenLayerEdgeMode bMw;

    private int[] getIntsHeatIce(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        int k1 = 1 + k + 1;
        int l1 = 1 + l + 1;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint = this.parent.getInts(i1, j1, k1, l1);

        int[] aint1 = IntCache.getIntCache(k * l);
        int i2 = 0;

        while (i2 < l) {
            int j2 = 0;

            while (true) {
                if (j2 < k) {
                    int k2 = aint[j2 + 1 + (i2 + 1) * k1];
                    if (k2 == 4) {
                        int l2 = aint[j2 + 1 + (i2 + 1 - 1) * k1];
                        int i3 = aint[j2 + 1 + 1 + (i2 + 1) * k1];
                        int j3 = aint[j2 + 1 - 1 + (i2 + 1) * k1];
                        int k3 = aint[j2 + 1 + (i2 + 1 + 1) * k1];
                        int l3 = l2 != 2 && i3 != 2 && j3 != 2 && k3 != 2 ? 0 : 1;
                        int i4 = l2 != 1 && i3 != 1 && j3 != 1 && k3 != 1 ? 0 : 1;
                        if (i4 != 0 || l3 != 0) {
                            k2 = 3;
                        }
                    }

                    aint1[j2 + i2 * k] = k2;
                    j2++;

                    continue;
                }

                i2++;

                break;
            }
        }

        return aint1;
    }

    public int[] getInts(int i, int j, int k, int l) {
        switch (this.bMw) {
            case COOL_WARM:
            default:
                return this.getIntsCoolWarm(i, j, k, l);
            case HEAT_ICE:
                return this.getIntsHeatIce(i, j, k, l);
            case SPECIAL:
                return this.getIntsSpecial(i, j, k, l);
        }
    }

    private int[] getIntsCoolWarm(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int k1 = 1 + k + 1;

        int l1 = 1 + l + 1;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(k * l);
        int i2 = 0;

        while (i2 < l) {
            int j2 = 0;

            while (true) {
                if (j2 < k) {
                    this.method_91c6fd2b8b1ce492fa7c(j2 + i, i2 + j);
                    int k2 = aint[j2 + 1 + (i2 + 1) * k1];
                    if (k2 == 1) {
                        int l2 = aint[j2 + 1 + (i2 + 1 - 1) * k1];
                        int i3 = aint[j2 + 1 + 1 + (i2 + 1) * k1];
                        int j3 = aint[j2 + 1 - 1 + (i2 + 1) * k1];
                        int k3 = aint[j2 + 1 + (i2 + 1 + 1) * k1];
                        int l3 = l2 != 3 && i3 != 3 && j3 != 3 && k3 != 3 ? 0 : 1;
                        int i4 = l2 != 4 && i3 != 4 && j3 != 4 && k3 != 4 ? 0 : 1;
                        if (l3 != 0 || i4 != 0) {
                            k2 = 2;
                        }
                    }

                    aint1[j2 + i2 * k] = k2;
                    j2++;

                    continue;
                }

                i2++;

                break;
            }
        }

        return aint1;
    }

    public GenLayerEdge(long i, GenLayer bIFValue, GenLayerEdgeMode bMwValue) {
        super(i);
        this.parent = bIFValue;
        this.bMw = bMwValue;
    }

    private int[] getIntsSpecial(int i, int j, int k, int l) {
        int[] aint = this.parent.getInts(i, j, k, l);
        int[] aint1 = IntCache.getIntCache(k * l);
        GenLayer.method_da2ac5c94686ee11e9e6();

        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                    int k1 = aint[j1 + i1 * k];
                    if (k1 != 0 && this.nextInt(13) == 0) {
                        k1 |= 1 + this.nextInt(15) << 8 & 3840;
                    }

                    aint1[j1 + i1 * k] = k1;
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
