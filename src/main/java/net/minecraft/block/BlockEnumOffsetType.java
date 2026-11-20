package net.minecraft.block;

public enum BlockEnumOffsetType {
    NONE("NONE", 0),
    XZ("XZ", 1),
    XYZ("XYZ", 2);
    private static BlockEnumOffsetType[] bNw;

    // region String decoding support

    // endregion

    static {
        bNw =
                new BlockEnumOffsetType[] {
                    net.minecraft.block.BlockEnumOffsetType.NONE, net.minecraft.block.BlockEnumOffsetType.XZ, net.minecraft.block.BlockEnumOffsetType.XYZ
                };
    }

    // region String decoding support

    // endregion

    BlockEnumOffsetType(String text, int j) {}

    // region String decoding support

    // endregion

}
