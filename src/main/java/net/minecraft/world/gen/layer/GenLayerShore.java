package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;

public class GenLayerShore extends GenLayer {
    public int[] getInts(int i, int j, int k, int l) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint = this.parent.getInts(i - 1, j - 1, k + 2, l + 2);
        int[] aint1 = IntCache.getIntCache(k * l);
        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    {
                        this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                        int k1 = aint[j1 + 1 + (i1 + 1) * (k + 2)];
                        BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(k1);
                        if (k1 == BiomeGenBase.mushroomIsland.biomeID) {
                            int l1 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                            int i2 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                            int j2 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                            int k2 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                            if (l1 != BiomeGenBase.ocean.biomeID
                                    && i2 != BiomeGenBase.ocean.biomeID
                                    && j2 != BiomeGenBase.ocean.biomeID
                                    && k2 != BiomeGenBase.ocean.biomeID) {
                                aint1[j1 + i1 * k] = k1;

                                
                            } else {

                                aint1[j1 + i1 * k] = BiomeGenBase.mushroomIslandShore.biomeID;


                            }
                        } else {

                            if (biomeGenBase != null
                                    && biomeGenBase.getBiomeClass() == BiomeGenJungle.class) {
                                int l2 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                                int k3 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                                int j4 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                                int i5 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                                if (this.method_3baace372e02b00ebb50(l2)
                                        && this.method_3baace372e02b00ebb50(k3)
                                        && this.method_3baace372e02b00ebb50(j4)
                                        && this.method_3baace372e02b00ebb50(i5)) {
                                    if (!isBiomeOceanic(l2)
                                            && !isBiomeOceanic(k3)
                                            && !isBiomeOceanic(j4)
                                            && !isBiomeOceanic(i5)) {
                                        aint1[j1 + i1 * k] = k1;


                                    } else {

                                        aint1[j1 + i1 * k] = BiomeGenBase.beach.biomeID;


                                    }
                                } else {

                                    aint1[j1 + i1 * k] = BiomeGenBase.jungleEdge.biomeID;


                                }
                            } else {

                                if (k1 != BiomeGenBase.extremeHills.biomeID
                                        && k1 != BiomeGenBase.extremeHillsPlus.biomeID
                                        && k1 != BiomeGenBase.extremeHillsEdge.biomeID) {
                                    if (biomeGenBase != null && biomeGenBase.isSnowyBiome()) {
                                        this.method_96fa394870d131ce6251(
                                                aint, aint1, j1, i1, k, k1, BiomeGenBase.coldBeach.biomeID);


                                    } else {

                                        if (k1 != BiomeGenBase.mesa.biomeID
                                                && k1 != BiomeGenBase.mesaPlateau_F.biomeID) {
                                            if (k1 != BiomeGenBase.ocean.biomeID
                                                    && k1 != BiomeGenBase.deepOcean.biomeID
                                                    && k1 != BiomeGenBase.river.biomeID
                                                    && k1 != BiomeGenBase.swampland.biomeID) {
                                                int i3 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                                                int l3 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                                                int k4 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                                                int j5 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                                                if (!isBiomeOceanic(i3)
                                                        && !isBiomeOceanic(l3)
                                                        && !isBiomeOceanic(k4)
                                                        && !isBiomeOceanic(j5)) {
                                                    aint1[j1 + i1 * k] = k1;


                                                } else {

                                                    aint1[j1 + i1 * k] = BiomeGenBase.beach.biomeID;


                                                }
                                            } else {

                                                aint1[j1 + i1 * k] = k1;


                                            }
                                        } else {

                                            int j3 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                                            int i4 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                                            int l4 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                                            int k5 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                                            if (!isBiomeOceanic(j3)
                                                    && !isBiomeOceanic(i4)
                                                    && !isBiomeOceanic(l4)
                                                    && !isBiomeOceanic(k5)) {
                                                if (this.aj(j3) && this.aj(i4) && this.aj(l4) && this.aj(k5)) {
                                                    aint1[j1 + i1 * k] = k1;


                                                } else {

                                                    aint1[j1 + i1 * k] = BiomeGenBase.desert.biomeID;


                                                }
                                            } else {

                                                aint1[j1 + i1 * k] = k1;


                                            }
                                        }
                                    }
                                } else {

                                    this.method_96fa394870d131ce6251(
                                            aint, aint1, j1, i1, k, k1, BiomeGenBase.stoneBeach.biomeID);
                                }
                            }
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

    private boolean method_3baace372e02b00ebb50(int i) {
        return BiomeGenBase.getBiome(i) != null
                        && BiomeGenBase.getBiome(i).getBiomeClass() == BiomeGenJungle.class
                ? true
                : i == BiomeGenBase.jungleEdge.biomeID
                        || i == BiomeGenBase.jungle.biomeID
                        || i == BiomeGenBase.jungleHills.biomeID
                        || i == BiomeGenBase.forest.biomeID
                        || i == BiomeGenBase.taiga.biomeID
                        || isBiomeOceanic(i);
    }

    private boolean aj(int i) {
        return BiomeGenBase.getBiome(i) instanceof BiomeGenMesa;
    }

    private void method_96fa394870d131ce6251(
            int[] aint, int[] aint1, int i, int j, int k, int l, int i1) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        if (isBiomeOceanic(l)) {
            aint1[i + j * k] = l;

            return;
        }

        int j1 = aint[i + 1 + (j + 1 - 1) * (k + 2)];
        int k1 = aint[i + 1 + 1 + (j + 1) * (k + 2)];
        int l1 = aint[i + 1 - 1 + (j + 1) * (k + 2)];
        int i2 = aint[i + 1 + (j + 1 + 1) * (k + 2)];
        if (!isBiomeOceanic(j1)
                && !isBiomeOceanic(k1)
                && !isBiomeOceanic(l1)
                && !isBiomeOceanic(i2)) {
            aint1[i + j * k] = l;

            return;
        }

        aint1[i + j * k] = i1;
    }

    public GenLayerShore(long i, GenLayer bIFValue) {
        super(i);
        this.parent = bIFValue;
    }
}
