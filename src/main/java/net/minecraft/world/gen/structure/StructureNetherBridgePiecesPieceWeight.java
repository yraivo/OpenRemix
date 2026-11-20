package net.minecraft.world.gen.structure;

class StructureNetherBridgePiecesPieceWeight {
    public boolean e;
    public int cz;
    public int c;
    public Class weightClass;
    public int dD;

    public StructureNetherBridgePiecesPieceWeight(Class oclass, int i, int j, boolean flag) {
        this.weightClass = oclass;
        this.cz = i;
        this.dD = j;
        this.e = flag;
    }

    public boolean aY() {
        return this.dD == 0 || this.c < this.dD;
    }

    public StructureNetherBridgePiecesPieceWeight(Class oclass, int i, int j) {
        this(oclass, i, j, false);
    }

    public boolean method_3baace372e02b00ebb50(int i) {
        return this.dD == 0 || this.c < this.dD;
    }
}
