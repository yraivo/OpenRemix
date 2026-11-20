package net.minecraft.world.gen.structure;

public enum StructureStrongholdPiecesStrongholdDoor {
    OPENING("OPENING", 0),
    WOOD_DOOR("WOOD_DOOR", 1),
    GRATES("GRATES", 2),
    IRON_DOOR("IRON_DOOR", 3);

    private static StructureStrongholdPiecesStrongholdDoor[] aiI;

    // region String decoding support

    // endregion

    StructureStrongholdPiecesStrongholdDoor(String text, int j) {}

    static {
        aiI =
                new StructureStrongholdPiecesStrongholdDoor[] {
                    net.minecraft.world.gen.structure.StructureStrongholdPiecesStrongholdDoor.OPENING,
                    net.minecraft.world.gen.structure.StructureStrongholdPiecesStrongholdDoor.WOOD_DOOR,
                    net.minecraft.world.gen.structure.StructureStrongholdPiecesStrongholdDoor.GRATES,
                    net.minecraft.world.gen.structure.StructureStrongholdPiecesStrongholdDoor.IRON_DOOR
                };
    }

    // region String decoding support

    // endregion

}
