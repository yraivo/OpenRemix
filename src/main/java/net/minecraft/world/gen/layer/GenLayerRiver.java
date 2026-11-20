package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiver extends GenLayer {
    private int method_46b7d87f71f96e2a0dba(int i) {
        return i >= 2 ? 2 + (i & 1) : i;
    }

    public int[] getInts(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        int k1 = k + 2;
        GenLayer.method_da2ac5c94686ee11e9e6();

        int l1 = l + 2;

        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(k * l);
        int i2 = 0;

        while (i2 < l) {
            int j2 = 0;

            while (true) {
                if (j2 < k) {
                    {
                        int k2 = this.method_46b7d87f71f96e2a0dba(aint[j2 + 0 + (i2 + 1) * k1]);
                        int l2 = this.method_46b7d87f71f96e2a0dba(aint[j2 + 2 + (i2 + 1) * k1]);
                        int i3 = this.method_46b7d87f71f96e2a0dba(aint[j2 + 1 + (i2 + 0) * k1]);
                        int j3 = this.method_46b7d87f71f96e2a0dba(aint[j2 + 1 + (i2 + 2) * k1]);
                        int k3 = this.method_46b7d87f71f96e2a0dba(aint[j2 + 1 + (i2 + 1) * k1]);
                        if (k3 == k2 && k3 == i3 && k3 == l2 && k3 == j3) {
                            aint1[j2 + i2 * k] = -1;

                            
                        } else {

                            aint1[j2 + i2 * k] = BiomeGenBase.river.biomeID;
                        }
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

    public GenLayerRiver(long i, GenLayer bIFValue) {
        super(i);
        super.parent = bIFValue;
    }
}
