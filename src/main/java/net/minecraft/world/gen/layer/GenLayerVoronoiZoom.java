package net.minecraft.world.gen.layer;

public class GenLayerVoronoiZoom extends GenLayer {
    public GenLayerVoronoiZoom(long i, GenLayer bIFValue) {
        super(i);
        super.parent = bIFValue;
    }

    public int[] getInts(int i, int j, int k, int l) {
        i -= 2;
        j -= 2;
        int i1 = i >> 2;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int j1 = j >> 2;
        int k1 = (k >> 2) + 2;
        int l1 = (l >> 2) + 2;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int i2 = k1 - 1 << 2;

        int j2 = l1 - 1 << 2;
        int[] aint1 = IntCache.getIntCache(i2 * j2);
        int l2 = 0;

        while (l2 < l1 - 1) {
            int k2 = 0;
            int i3 = aint[k2 + 0 + (l2 + 0) * k1];
            int j3 = aint[k2 + 0 + (l2 + 1) * k1];

            while (true) {
                if (k2 < k1 - 1) {

                    this.method_91c6fd2b8b1ce492fa7c(k2 + i1 << 2, l2 + j1 << 2);
                    double d1 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                    double d2 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                    this.method_91c6fd2b8b1ce492fa7c(k2 + i1 + 1 << 2, l2 + j1 << 2);
                    double d3 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                    double d4 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                    this.method_91c6fd2b8b1ce492fa7c(k2 + i1 << 2, l2 + j1 + 1 << 2);
                    double d5 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6;
                    double d6 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                    this.method_91c6fd2b8b1ce492fa7c(k2 + i1 + 1 << 2, l2 + j1 + 1 << 2);
                    double d7 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                    double d8 = (this.nextInt(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
                    int k3 = aint[k2 + 1 + (l2 + 0) * k1] & 0xFF;
                    int l3 = aint[k2 + 1 + (l2 + 1) * k1] & 0xFF;
                    int i4 = 0;

                    while (i4 < 4) {
                        int j4 = ((l2 << 2) + i4) * i2 + (k2 << 2);
                        int k4 = 0;

                        while (k4 < 4) {
                            {
                                double d9 = (i4 - d2) * (i4 - d2) + (k4 - d1) * (k4 - d1);
                                double d10 = (i4 - d4) * (i4 - d4) + (k4 - d3) * (k4 - d3);
                                double d11 = (i4 - d6) * (i4 - d6) + (k4 - d5) * (k4 - d5);
                                double d12 = (i4 - d8) * (i4 - d8) + (k4 - d7) * (k4 - d7);
                                if (d9 < d10 && d9 < d11 && d9 < d12) {
                                    aint1[j4++] = i3;

                                    
                                } else {

                                    if (d10 < d9 && d10 < d11 && d10 < d12) {
                                        aint1[j4++] = k3;


                                    } else {

                                        if (d11 < d9 && d11 < d10 && d11 < d12) {
                                            aint1[j4++] = j3;


                                        } else {

                                            aint1[j4++] = l3;
                                        }
                                    }
                                }
                            }

                            k4++;
                        }

                        i4++;
                    }

                    i3 = k3;
                    j3 = l3;
                    k2++;

                    continue;
                }

                l2++;

                break;
            }
        }

        int[] aint2 = IntCache.getIntCache(k * l);
        int l4 = 0;

        while (l4 < l) {
            System.arraycopy(aint1, (l4 + (j & 3)) * i2 + (i & 3), aint2, l4 * k, k);
            l4++;
        }

        return aint2;
    }
}
