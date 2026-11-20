package net.minecraft.world.gen.structure;

public class StructureVillagePiecesPieceWeight {
    public int villagePiecesSpawned;
    public int villagePiecesLimit;
    public Class villagePieceClass;
    public int villagePieceWeight;

    public boolean canSpawnMoreVillagePieces() {
        return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
    }

    public boolean canSpawnMoreVillagePiecesOfType(int i) {
        return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
    }

    public StructureVillagePiecesPieceWeight(Class oclass, int i, int j) {
        this.villagePieceClass = oclass;
        this.villagePieceWeight = i;
        this.villagePiecesLimit = j;
    }
}
