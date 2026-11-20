package net.minecraft.world.gen.structure;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class MapGenScatteredFeatureStart extends StructureStart {
    public MapGenScatteredFeatureStart(World world, Random random, int i, int j) {
        this(world, random, i, j, StructureComponent.cR());
    }

    private MapGenScatteredFeatureStart(
            World world, Random random, int i, int j, int initialFlowToken) {
        super(i, j);

        label23:
        {
            int l = initialFlowToken;

            int k = l;
            BiomeGenBase biomeGenForCoords =
                    world.getBiomeGenForCoords(new BlockPos(i * 16 + 8, 0, j * 16 + 8));
            if (biomeGenForCoords != BiomeGenBase.jungle
                    && biomeGenForCoords != BiomeGenBase.jungleHills) {
                if (biomeGenForCoords == BiomeGenBase.swampland) {
                    ComponentScatteredFeaturePiecesSwampHut
                            componentScatteredFeaturePiecesSwampHut =
                                    new ComponentScatteredFeaturePiecesSwampHut(
                                            random, i * 16, j * 16);
                    this.components.add(componentScatteredFeaturePiecesSwampHut);
                    if (k != 0) {
                        break label23;
                    }
                }

                if (biomeGenForCoords != BiomeGenBase.desert
                        && biomeGenForCoords != BiomeGenBase.desertHills) {
                    break label23;
                }

                ComponentScatteredFeaturePiecesDesertPyramid
                        componentScatteredFeaturePiecesDesertPyramid =
                                new ComponentScatteredFeaturePiecesDesertPyramid(
                                        random, i * 16, j * 16);
                this.components.add(componentScatteredFeaturePiecesDesertPyramid);
                if (k != 0) {
                    break label23;
                }
            }

            ComponentScatteredFeaturePiecesJunglePyramid
                    componentScatteredFeaturePiecesJunglePyramid =
                            new ComponentScatteredFeaturePiecesJunglePyramid(
                                    random, i * 16, j * 16);
            this.components.add(componentScatteredFeaturePiecesJunglePyramid);
        }

        this.cj();
    }

    public MapGenScatteredFeatureStart() {}
}
