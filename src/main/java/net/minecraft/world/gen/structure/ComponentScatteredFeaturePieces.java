package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesDesertPyramid;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesSwampHut;

public class ComponentScatteredFeaturePieces {

    public static void registerScatteredFeaturePieces() {
        MapGenStructureIO.registerStructureComponent(
                ComponentScatteredFeaturePiecesDesertPyramid.class, "TeDP");
        MapGenStructureIO.registerStructureComponent(
                ComponentScatteredFeaturePiecesJunglePyramid.class, "TeJP");
        MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePiecesSwampHut.class, "TeSH");
    }


}
