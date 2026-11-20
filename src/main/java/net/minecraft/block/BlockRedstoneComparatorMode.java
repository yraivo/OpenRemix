package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockRedstoneComparatorMode implements StringSerializable {
    COMPARE("COMPARE", 0, "compare"),
    SUBTRACT("SUBTRACT", 1, "subtract");
    private static BlockRedstoneComparatorMode[] VG;

    private String name;

    // region String decoding support

    // endregion

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.name;
    }

    static {
        VG =
                new BlockRedstoneComparatorMode[] {
                    net.minecraft.block.BlockRedstoneComparatorMode.COMPARE,
                    net.minecraft.block.BlockRedstoneComparatorMode.SUBTRACT
                };
    }

    // region String decoding support

    // endregion

    BlockRedstoneComparatorMode(String text, int j, String textValue) {
        this.name = textValue;
    }
}
