package net.minecraft.world.gen.structure;

public class StructureStrongholdPiecesPieceWeight {
    public int instancesSpawned;
    public Class pieceClass;
    public int instancesLimit;
    public int pieceWeight;

    public boolean canSpawnMoreStructuresOfType(int i) {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }

    public StructureStrongholdPiecesPieceWeight(Class oclass, int pieceWeightValue, int j) {
        this.pieceClass = oclass;
        this.pieceWeight = pieceWeightValue;
        this.instancesLimit = j;
    }

    public boolean canSpawnMoreStructures() {
        return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
    }
}
