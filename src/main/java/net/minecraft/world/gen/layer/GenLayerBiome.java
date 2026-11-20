package net.minecraft.world.gen.layer;

import net.minecraft.entity.Entity;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettingsFactory;

public class GenLayerBiome extends GenLayer {
    private BiomeGenBase[] bHq;
    private BiomeGenBase[] bMs;
    private BiomeGenBase[] bMt;
    private ChunkProviderSettings bMu;
    private BiomeGenBase[] bMv;

    public GenLayerBiome(long i, GenLayer bIFValue, WorldType worldType, String text) {
        super(i);

        {
            this.bMt =
                    new BiomeGenBase[] {
                        BiomeGenBase.desert,
                        BiomeGenBase.desert,
                        BiomeGenBase.desert,
                        BiomeGenBase.savanna,
                        BiomeGenBase.savanna,
                        BiomeGenBase.plains
                    };
            GenLayer.method_da2ac5c94686ee11e9e6();

            this.bMs =
                    new BiomeGenBase[] {
                        BiomeGenBase.forest,
                        BiomeGenBase.roofedForest,
                        BiomeGenBase.extremeHills,
                        BiomeGenBase.plains,
                        BiomeGenBase.birchForest,
                        BiomeGenBase.swampland
                    };
            this.bMv =
                    new BiomeGenBase[] {
                        BiomeGenBase.forest,
                        BiomeGenBase.extremeHills,
                        BiomeGenBase.taiga,
                        BiomeGenBase.plains
                    };

            this.bHq =
                    new BiomeGenBase[] {
                        BiomeGenBase.icePlains,
                        BiomeGenBase.icePlains,
                        BiomeGenBase.icePlains,
                        BiomeGenBase.coldTaiga
                    };
            this.parent = bIFValue;
            if (worldType == WorldType.DEFAULT_1_1) {
                this.bMt =
                        new BiomeGenBase[] {
                            BiomeGenBase.desert,
                            BiomeGenBase.forest,
                            BiomeGenBase.extremeHills,
                            BiomeGenBase.swampland,
                            BiomeGenBase.plains,
                            BiomeGenBase.taiga
                        };
                this.bMu = null;

                
            } else {

                if (worldType == WorldType.CUSTOMIZED) {
                    this.bMu = ChunkProviderSettingsFactory.jsonToFactory(text).WS();


                } else {

                    this.bMu = null;
                }
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            GenLayer.b(new Entity[1]);
        }
    }

    public int[] getInts(int i, int j, int k, int l) {
        int[] aint = this.parent.getInts(i, j, k, l);
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint1 = IntCache.getIntCache(k * l);
        int i1 = 0;

        while (i1 < l) {
            int j1 = 0;

            while (true) {
                if (j1 < k) {
                    {
                        this.method_91c6fd2b8b1ce492fa7c(j1 + i, i1 + j);
                        int k1 = aint[j1 + i1 * k];
                        int l1 = (k1 & 3840) >> 8;
                        k1 &= -3841;
                        if (this.bMu != null && this.bMu.fixedBiome >= 0) {
                            aint1[j1 + i1 * k] = this.bMu.fixedBiome;

                            
                        } else {

                            if (isBiomeOceanic(k1)) {
                                aint1[j1 + i1 * k] = k1;


                            } else {

                                if (k1 == BiomeGenBase.mushroomIsland.biomeID) {
                                    aint1[j1 + i1 * k] = k1;


                                } else {

                                    if (k1 == 1) {
                                        if (l1 > 0) {
                                            if (this.nextInt(3) == 0) {
                                                aint1[j1 + i1 * k] = BiomeGenBase.mesaPlateau.biomeID;


                                            } else {

                                                aint1[j1 + i1 * k] = BiomeGenBase.mesaPlateau_F.biomeID;


                                            }
                                        } else {

                                            aint1[j1 + i1 * k] =
                                                    this.bMt[this.nextInt(this.bMt.length)]
                                                            .biomeID;


                                        }
                                    } else {

                                        if (k1 == 2) {
                                            if (l1 > 0) {
                                                aint1[j1 + i1 * k] = BiomeGenBase.jungle.biomeID;


                                            } else {

                                                aint1[j1 + i1 * k] =
                                                        this.bMs[this.nextInt(this.bMs.length)]
                                                                .biomeID;


                                            }
                                        } else {

                                            if (k1 == 3) {
                                                if (l1 > 0) {
                                                    aint1[j1 + i1 * k] = BiomeGenBase.megaTaiga.biomeID;


                                                } else {

                                                    aint1[j1 + i1 * k] =
                                                            this.bMv[this.nextInt(this.bMv.length)]
                                                                    .biomeID;


                                                }
                                            } else {

                                                if (k1 == 4) {
                                                    aint1[j1 + i1 * k] =
                                                            this.bHq[this.nextInt(this.bHq.length)]
                                                                    .biomeID;


                                                } else {

                                                    aint1[j1 + i1 * k] = BiomeGenBase.mushroomIsland.biomeID;
                                                }
                                            }
                                        }
                                    }
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
}
