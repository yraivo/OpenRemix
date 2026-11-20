package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiverMix extends GenLayer {
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;

    public int[] getInts(int i, int j, int k, int l) {
        int[] aint = this.biomePatternGeneratorChain.getInts(i, j, k, l);
        GenLayer.method_da2ac5c94686ee11e9e6();

        int[] aint1 = this.riverPatternGeneratorChain.getInts(i, j, k, l);
        int[] aint2 = IntCache.getIntCache(k * l);

        int i1 = 0;

        while (i1 < k * l) {
            {
                if (aint[i1] != BiomeGenBase.ocean.biomeID
                        && aint[i1] != BiomeGenBase.deepOcean.biomeID) {
                    if (aint1[i1] == BiomeGenBase.river.biomeID) {
                        if (aint[i1] == BiomeGenBase.icePlains.biomeID) {
                            aint2[i1] = BiomeGenBase.frozenRiver.biomeID;

                            
                        } else {

                            if (aint[i1] != BiomeGenBase.mushroomIsland.biomeID
                                    && aint[i1] != BiomeGenBase.mushroomIslandShore.biomeID) {
                                aint2[i1] = aint1[i1] & 0xFF;


                            } else {

                                aint2[i1] = BiomeGenBase.mushroomIslandShore.biomeID;


                            }
                        }
                    } else {

                        aint2[i1] = aint[i1];


                    }
                } else {

                    aint2[i1] = aint[i1];
                }
            }

            i1++;
        }

        return aint2;
    }

    public GenLayerRiverMix(long i, GenLayer bMzValue, GenLayer bMxValue) {
        super(i);
        this.biomePatternGeneratorChain = bMzValue;
        this.riverPatternGeneratorChain = bMxValue;
    }

    public void initWorldGenSeed(long i) {
        this.biomePatternGeneratorChain.initWorldGenSeed(i);
        this.riverPatternGeneratorChain.initWorldGenSeed(i);
        super.initWorldGenSeed(i);
    }
}
