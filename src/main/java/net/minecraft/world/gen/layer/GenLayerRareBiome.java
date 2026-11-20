package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRareBiome extends GenLayer {
    public GenLayerRareBiome(long i, GenLayer bIFValue) {
        super(i);
        this.parent = bIFValue;
    }

    public int[] getInts(int i, int j, int k, int l) {
        int[] aint = this.parent.getInts(i - 1, j - 1, k + 2, l + 2);
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint1 = IntCache.getIntCache(k * l);
        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    {
                        this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                        int k1 = aint[j1 + 1 + (i1 + 1) * (k + 2)];
                        if (this.nextInt(57) == 0) {
                            if (k1 == BiomeGenBase.plains.biomeID) {
                                aint1[j1 + i1 * k] = BiomeGenBase.plains.biomeID + 128;

                                
                            } else {

                                aint1[j1 + i1 * k] = k1;


                            }
                        } else {

                            aint1[j1 + i1 * k] = k1;
                        }
                    }

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
