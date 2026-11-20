package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.StructureStrongholdPiecesPieceWeight;

public final class StructureStrongholdPiecesPieceWeight1 extends StructureStrongholdPiecesPieceWeight {
    public StructureStrongholdPiecesPieceWeight1(Class oclass, int i, int j) {
        super(oclass, i, j);
    }

    public boolean canSpawnMoreStructuresOfType(int i) {
        return super.canSpawnMoreStructuresOfType(i) && i > 4;
    }
}
