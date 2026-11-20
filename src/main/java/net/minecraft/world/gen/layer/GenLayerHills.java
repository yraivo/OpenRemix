package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerHills extends GenLayer {
    private GenLayer bMx;
    private static String h;
    private static Logger logger;

    // region String decoding support

    // endregion

    public int[] getInts(int i, int j, int k, int l) {
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint = this.parent.getInts(i - 1, j - 1, k + 2, l + 2);

        int[] aint1 = this.bMx.getInts(i - 1, j - 1, k + 2, l + 2);
        int[] aint2 = IntCache.getIntCache(k * l);
        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                    int k1 = aint[j1 + 1 + (i1 + 1) * (k + 2)];
                    int l1 = aint1[j1 + 1 + (i1 + 1) * (k + 2)];
                    int i2 = (l1 - 2) % 29 == 0 ? 1 : 0;
                    if (k1 > 255) {
                        net.minecraft.world.gen.layer.GenLayerHills.logger.debug(h + k1);
                    }

                    label201:
                    {
                        if (k1 != 0 && l1 >= 2 && (l1 - 2) % 29 == 1 && k1 < 128) {
                            if (BiomeGenBase.getBiome(k1 + 128) != null) {
                                aint2[j1 + i1 * k] = k1 + 128;

                                break label201;
                            }

                            aint2[j1 + i1 * k] = k1;

                            break label201;
                        }

                        if (this.nextInt(3) != 0 && i2 == 0) {
                            aint2[j1 + i1 * k] = k1;

                            break label201;
                        }

                        int j2;
                        {
                            j2 = k1;
                            if (k1 == BiomeGenBase.desert.biomeID) {
                                j2 = BiomeGenBase.desertHills.biomeID;

                                
                            } else {

                                if (k1 == BiomeGenBase.forest.biomeID) {
                                    j2 = BiomeGenBase.forestHills.biomeID;


                                } else {

                                    if (k1 == BiomeGenBase.birchForest.biomeID) {
                                        j2 = BiomeGenBase.birchForestHills.biomeID;


                                    } else {

                                        if (k1 == BiomeGenBase.roofedForest.biomeID) {
                                            j2 = BiomeGenBase.plains.biomeID;


                                        } else {

                                            if (k1 == BiomeGenBase.taiga.biomeID) {
                                                j2 = BiomeGenBase.taigaHills.biomeID;


                                            } else {

                                                if (k1 == BiomeGenBase.megaTaiga.biomeID) {
                                                    j2 = BiomeGenBase.megaTaigaHills.biomeID;


                                                } else {

                                                    if (k1 == BiomeGenBase.coldTaiga.biomeID) {
                                                        j2 = BiomeGenBase.coldTaigaHills.biomeID;


                                                    } else {

                                                        if (k1 == BiomeGenBase.plains.biomeID) {
                                                            if (this.nextInt(3) == 0) {
                                                                j2 = BiomeGenBase.forestHills.biomeID;


                                                            } else {

                                                                j2 = BiomeGenBase.forest.biomeID;


                                                            }
                                                        } else {

                                                            if (k1 == BiomeGenBase.icePlains.biomeID) {
                                                                j2 = BiomeGenBase.iceMountains.biomeID;


                                                            } else {

                                                                if (k1 == BiomeGenBase.jungle.biomeID) {
                                                                    j2 = BiomeGenBase.jungleHills.biomeID;


                                                                } else {

                                                                    if (k1 == BiomeGenBase.ocean.biomeID) {
                                                                        j2 = BiomeGenBase.deepOcean.biomeID;


                                                                    } else {

                                                                        if (k1 == BiomeGenBase.extremeHills.biomeID) {
                                                                            j2 = BiomeGenBase.extremeHillsPlus.biomeID;


                                                                        } else {

                                                                            if (k1 == BiomeGenBase.savanna.biomeID) {
                                                                                j2 = BiomeGenBase.savannaPlateau.biomeID;


                                                                            } else {

                                                                                if (method_3be63dceb97b64e5a50f(
                                                                                        k1, BiomeGenBase.mesaPlateau_F.biomeID)) {
                                                                                    j2 = BiomeGenBase.mesa.biomeID;


                                                                                } else {

                                                                                    if (k1 == BiomeGenBase.deepOcean.biomeID
                                                                                            && this.nextInt(3) == 0) {
                                                                                        int k2 = this.nextInt(2);
                                                                                        if (k2 == 0) {
                                                                                            j2 = BiomeGenBase.plains.biomeID;


                                                                                        } else {

                                                                                            j2 = BiomeGenBase.forest.biomeID;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (i2 != 0 && j2 != k1) {
                            if (BiomeGenBase.getBiome(j2 + 128) != null) {
                                j2 += 128;

                                
                            } else {

                                j2 = k1;
                            }
                        }

                        if (j2 == k1) {
                            aint2[j1 + i1 * k] = k1;

                            break label201;
                        }

                        int l3 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                        int l2 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                        int i3 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                        int j3 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                        int k3 = 0;
                        if (method_3be63dceb97b64e5a50f(l3, k1)) {
                            k3++;
                        }

                        if (method_3be63dceb97b64e5a50f(l2, k1)) {
                            k3++;
                        }

                        if (method_3be63dceb97b64e5a50f(i3, k1)) {
                            k3++;
                        }

                        if (method_3be63dceb97b64e5a50f(j3, k1)) {
                            k3++;
                        }

                        if (k3 >= 3) {
                            aint2[j1 + i1 * k] = j2;

                            break label201;
                        }

                        aint2[j1 + i1 * k] = k1;
                    }

                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }

        return aint2;
    }

    // region String decoding support

    // endregion

    static {
        h = "old! ";
        net.minecraft.world.gen.layer.GenLayerHills.logger = LogManager.getLogger();
    }

    public GenLayerHills(long i, GenLayer bIFValue, GenLayer bMxValue) {
        super(i);
        this.parent = bIFValue;
        this.bMx = bMxValue;
    }
}
