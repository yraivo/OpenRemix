package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesStart extends StructureNetherBridgePiecesCrossing3 {
    public List vl;
    public List iy = Lists.newArrayList();
    public List secondaryWeights;
    public StructureNetherBridgePiecesPieceWeight theNetherBridgePieceWeight;

    public StructureNetherBridgePiecesStart(Random random, int i, int j) {
        super(random, i, j);
        this.vl = Lists.newArrayList();
        int j1 = StructureComponent.f();
        StructureNetherBridgePiecesPieceWeight[] primaryComponents = StructureNetherBridgePieces.getPrimaryComponents();
        int l = primaryComponents.length;
        int k = j1;
        int i1 = 0;

        while (i1 < l) {
            StructureNetherBridgePiecesPieceWeight structureNetherBridgePiecesPieceWeight = primaryComponents[i1];
            structureNetherBridgePiecesPieceWeight.c = 0;
            this.vl.add(structureNetherBridgePiecesPieceWeight);
            i1++;
            if (k != 0) {
                break;
            }
        }

        this.secondaryWeights = Lists.newArrayList();

        for (StructureNetherBridgePiecesPieceWeight structureNetherBridgePiecesPieceWeightValue :
                StructureNetherBridgePieces.getSecondaryComponents()) {
            structureNetherBridgePiecesPieceWeightValue.c = 0;
            this.secondaryWeights.add(structureNetherBridgePiecesPieceWeightValue);
            if (k != 0) {
                break;
            }
        }
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
    }

    public StructureNetherBridgePiecesStart() {
        this.iy = com.google.common.collect.Lists.newArrayList();
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
    }
}
