package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

public class StructureOceanMonumentPiecesRoomDefinition {
    boolean e;
    int a;
    public StructureOceanMonumentPiecesRoomDefinition[] bMV =
            new StructureOceanMonumentPiecesRoomDefinition[6];
    public boolean d;
    public boolean[] aCM = new boolean[6];
    int f;

    public int method_beea3e16fe3ec0995eb9() {
        int j = 0;
        int l = StructureComponent.cR();
        int k = 0;
        int i = l;

        while (k < 6) {
            if (this.aCM[k]) {
                j++;
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return j;
    }

    public void method_8d9b536bcdc9991c48fb() {
        int k = StructureComponent.f();
        int j = 0;
        int i = k;

        while (j < 6) {
            this.aCM[j] =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((this.bMV[j] != null ? 1 : 0));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public StructureOceanMonumentPiecesRoomDefinition(int i) {
        this.a = i;
    }

    public boolean method_b417d7500ec59d69e373() {
        return this.a >= 75;
    }

    public void method_e47fe69d6ddf5ffe6746(
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        this.bMV[facing.getIndex()] = structureOceanMonumentPiecesRoomDefinition;
        structureOceanMonumentPiecesRoomDefinition.bMV[facing.getOpposite().getIndex()] = this;
    }

    public boolean method_3baace372e02b00ebb50(int i) {
        int j = StructureComponent.cR();
        if (this.e) {
            return true;
        }

        this.f = i;
        int k = 0;

        while (k < 6) {
            if (this.bMV[k] != null
                    && this.aCM[k]
                    && this.bMV[k].f != i
                    && this.bMV[k].method_3baace372e02b00ebb50(i)) {
                return true;
            }

            k++;
            if (j == 0) {
                break;
            }
        }

        return false;
    }
}
