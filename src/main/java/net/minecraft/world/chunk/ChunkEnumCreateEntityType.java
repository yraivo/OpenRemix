package net.minecraft.world.chunk;

public enum ChunkEnumCreateEntityType {
    IMMEDIATE("IMMEDIATE", 0),
    QUEUED("QUEUED", 1),
    CHECK("CHECK", 2);
    private static ChunkEnumCreateEntityType[] aix;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        aix =
                new ChunkEnumCreateEntityType[] {
                    net.minecraft.world.chunk.ChunkEnumCreateEntityType.IMMEDIATE,
                    net.minecraft.world.chunk.ChunkEnumCreateEntityType.QUEUED,
                    net.minecraft.world.chunk.ChunkEnumCreateEntityType.CHECK
                };
    }

    // region String decoding support

    // endregion

    ChunkEnumCreateEntityType(String text, int j) {}
}
