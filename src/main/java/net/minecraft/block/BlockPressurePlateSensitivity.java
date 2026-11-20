package net.minecraft.block;

public enum BlockPressurePlateSensitivity {
    EVERYTHING("EVERYTHING", 0),
    MOBS("MOBS", 1);

    private static BlockPressurePlateSensitivity[] UO;

    BlockPressurePlateSensitivity(String text, int j) {}

    static {
        UO =
                new BlockPressurePlateSensitivity[] {
                    net.minecraft.block.BlockPressurePlateSensitivity.EVERYTHING,
                    net.minecraft.block.BlockPressurePlateSensitivity.MOBS
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
