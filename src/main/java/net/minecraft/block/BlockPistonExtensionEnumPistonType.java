package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockPistonExtensionEnumPistonType implements StringSerializable {
    DEFAULT("DEFAULT", 0, "normal"),
    STICKY("STICKY", 1, "sticky");

    private static BlockPistonExtensionEnumPistonType[] UA;

    private String VARIANT;

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.VARIANT;
    }

    BlockPistonExtensionEnumPistonType(String text, int j, String textValue) {
        this.VARIANT = textValue;
    }

    static {
        UA =
                new BlockPistonExtensionEnumPistonType[] {
                    net.minecraft.block.BlockPistonExtensionEnumPistonType.DEFAULT,
                    net.minecraft.block.BlockPistonExtensionEnumPistonType.STICKY
                };
    }

    public String getName() {
        return this.VARIANT;
    }

    // region String decoding support

    // endregion

}
