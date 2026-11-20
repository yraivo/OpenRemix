package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.StructureStrongholdPiecesPieceWeight;

public final class StructureStrongholdPiecesPieceWeightArray extends StructureStrongholdPiecesPieceWeight {
    public StructureStrongholdPiecesPieceWeightArray(Class oclass, int i, int j) {
        super(oclass, i, j);
    }

    public boolean canSpawnMoreStructuresOfType(int i) {
        return super.canSpawnMoreStructuresOfType(i) && i > 5;
    }
}
