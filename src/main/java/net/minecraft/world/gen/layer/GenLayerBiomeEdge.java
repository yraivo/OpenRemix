package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBaseTempCategory;

public class GenLayerBiomeEdge extends GenLayer {
    private boolean canBiomesBeNeighbors(int i, int j) {
        if (method_3be63dceb97b64e5a50f(i, j)) {
            return true;
        } else {
            BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(i);
            BiomeGenBase biomeGenBaseValue = BiomeGenBase.getBiome(j);
            if (biomeGenBase != null && biomeGenBaseValue != null) {
                BiomeGenBaseTempCategory tempCategory = biomeGenBase.getTempCategory();
                BiomeGenBaseTempCategory tempCategoryValue = biomeGenBaseValue.getTempCategory();
                return tempCategory == tempCategoryValue
                        || tempCategory == BiomeGenBaseTempCategory.MEDIUM
                        || tempCategoryValue == BiomeGenBaseTempCategory.MEDIUM;
            } else {
                return false;
            }
        }
    }

    public int[] getInts(int i, int j, int k, int l) {
        int[] aint = this.parent.getInts(i - 1, j - 1, k + 2, l + 2);
        int[] aint1 = IntCache.getIntCache(k * l);
        GenLayer.method_da2ac5c94686ee11e9e6();

        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                    int k1 = aint[j1 + 1 + (i1 + 1) * (k + 2)];
                    if (!this.replaceBiomeEdgeIfNecessary(
                                    aint,
                                    aint1,
                                    j1,
                                    i1,
                                    k,
                                    k1,
                                    BiomeGenBase.extremeHills.biomeID,
                                    BiomeGenBase.extremeHillsEdge.biomeID)
                            && !this.replaceBiomeEdge(
                                    aint,
                                    aint1,
                                    j1,
                                    i1,
                                    k,
                                    k1,
                                    BiomeGenBase.mesaPlateau_F.biomeID,
                                    BiomeGenBase.mesa.biomeID)
                            && !this.replaceBiomeEdge(
                                    aint,
                                    aint1,
                                    j1,
                                    i1,
                                    k,
                                    k1,
                                    BiomeGenBase.mesaPlateau.biomeID,
                                    BiomeGenBase.mesa.biomeID)
                            && !this.replaceBiomeEdge(
                                    aint,
                                    aint1,
                                    j1,
                                    i1,
                                    k,
                                    k1,
                                    BiomeGenBase.megaTaiga.biomeID,
                                    BiomeGenBase.taiga.biomeID)) {
                        if (k1 == BiomeGenBase.desert.biomeID) {
                            int l1 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                            int i2 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                            int j2 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                            int k2 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                            if (l1 != BiomeGenBase.icePlains.biomeID
                                    && i2 != BiomeGenBase.icePlains.biomeID
                                    && j2 != BiomeGenBase.icePlains.biomeID
                                    && k2 != BiomeGenBase.icePlains.biomeID) {
                                aint1[j1 + i1 * k] = k1;

                                
                            } else {

                                aint1[j1 + i1 * k] = BiomeGenBase.extremeHillsPlus.biomeID;


                            }
                        } else {

                            if (k1 == BiomeGenBase.swampland.biomeID) {
                                int l2 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                                int i3 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                                int j3 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                                int k3 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                                if (l2 != BiomeGenBase.desert.biomeID
                                        && i3 != BiomeGenBase.desert.biomeID
                                        && j3 != BiomeGenBase.desert.biomeID
                                        && k3 != BiomeGenBase.desert.biomeID
                                        && l2 != BiomeGenBase.coldTaiga.biomeID
                                        && i3 != BiomeGenBase.coldTaiga.biomeID
                                        && j3 != BiomeGenBase.coldTaiga.biomeID
                                        && k3 != BiomeGenBase.coldTaiga.biomeID
                                        && l2 != BiomeGenBase.icePlains.biomeID
                                        && i3 != BiomeGenBase.icePlains.biomeID
                                        && j3 != BiomeGenBase.icePlains.biomeID
                                        && k3 != BiomeGenBase.icePlains.biomeID) {
                                    if (l2 != BiomeGenBase.jungle.biomeID
                                            && k3 != BiomeGenBase.jungle.biomeID
                                            && i3 != BiomeGenBase.jungle.biomeID
                                            && j3 != BiomeGenBase.jungle.biomeID) {
                                        aint1[j1 + i1 * k] = k1;


                                    } else {

                                        aint1[j1 + i1 * k] = BiomeGenBase.jungleEdge.biomeID;


                                    }
                                } else {

                                    aint1[j1 + i1 * k] = BiomeGenBase.plains.biomeID;


                                }
                            } else {

                                aint1[j1 + i1 * k] = k1;
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

    private boolean replaceBiomeEdge(
            int[] aint, int[] aint1, int i, int j, int k, int l, int i1, int j1) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        if (l != i1) {
            return false;
        }

        int k1 = aint[i + 1 + (j + 1 - 1) * (k + 2)];
        int l1 = aint[i + 1 + 1 + (j + 1) * (k + 2)];
        int i2 = aint[i + 1 - 1 + (j + 1) * (k + 2)];
        int j2 = aint[i + 1 + (j + 1 + 1) * (k + 2)];
        if (method_3be63dceb97b64e5a50f(k1, i1)
                && method_3be63dceb97b64e5a50f(l1, i1)
                && method_3be63dceb97b64e5a50f(i2, i1)
                && method_3be63dceb97b64e5a50f(j2, i1)) {
            aint1[i + j * k] = l;

            return true;
        }

        aint1[i + j * k] = j1;
        return true;
    }

    private boolean replaceBiomeEdgeIfNecessary(
            int[] aint, int[] aint1, int i, int j, int k, int l, int i1, int j1) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        if (!method_3be63dceb97b64e5a50f(l, i1)) {
            return false;
        }

        int k1 = aint[i + 1 + (j + 1 - 1) * (k + 2)];
        int l1 = aint[i + 1 + 1 + (j + 1) * (k + 2)];
        int i2 = aint[i + 1 - 1 + (j + 1) * (k + 2)];
        int j2 = aint[i + 1 + (j + 1 + 1) * (k + 2)];
        if (this.canBiomesBeNeighbors(k1, i1)
                && this.canBiomesBeNeighbors(l1, i1)
                && this.canBiomesBeNeighbors(i2, i1)
                && this.canBiomesBeNeighbors(j2, i1)) {
            aint1[i + j * k] = l;

            return true;
        }

        aint1[i + j * k] = j1;
        return true;
    }

    public GenLayerBiomeEdge(long i, GenLayer bIFValue) {
        super(i);
        this.parent = bIFValue;
    }
}
