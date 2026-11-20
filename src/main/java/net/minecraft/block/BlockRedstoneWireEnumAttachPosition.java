package net.minecraft.block;

import net.minecraft.util.StringSerializable;

enum BlockRedstoneWireEnumAttachPosition implements StringSerializable {
    UP("UP", 0, "up"),
    SIDE("SIDE", 1, "side"),
    NONE("NONE", 2, "none");

    private static BlockRedstoneWireEnumAttachPosition[] VI;

    private String name;

    BlockRedstoneWireEnumAttachPosition(String text, int j, String textValue) {
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.getName();
    }

    // region String decoding support

    // endregion

    static {
        VI =
                new BlockRedstoneWireEnumAttachPosition[] {
                    net.minecraft.block.BlockRedstoneWireEnumAttachPosition.UP,
                    net.minecraft.block.BlockRedstoneWireEnumAttachPosition.SIDE,
                    net.minecraft.block.BlockRedstoneWireEnumAttachPosition.NONE
                };
    }

    public String getName() {
        return this.name;
    }
}
