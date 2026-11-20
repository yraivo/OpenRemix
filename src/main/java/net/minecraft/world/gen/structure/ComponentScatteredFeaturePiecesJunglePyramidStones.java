package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;

import java.util.Random;

class ComponentScatteredFeaturePiecesJunglePyramidStones extends StructureComponentBlockSelector {
    private ComponentScatteredFeaturePiecesJunglePyramidStones() {}

    public void selectBlocks(Random random, int j, int k, int l, boolean flag) {
        int i = StructureComponent.f();
        if (random.nextFloat() < 0.4F) {
            this.blockstate = Blocks.cobblestone.getDefaultState();
            if (i == 0) {
                return;
            }
        }

        this.blockstate = Blocks.mossy_cobblestone.getDefaultState();
    }

    ComponentScatteredFeaturePiecesJunglePyramidStones(
            ScatteredFeatureAccessMarker componentScatteredFeaturePiecesSwitchEnumFacing) {
        this();
    }
}
