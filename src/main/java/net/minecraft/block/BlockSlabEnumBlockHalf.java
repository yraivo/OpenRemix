package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockSlabEnumBlockHalf implements StringSerializable {
    TOP("TOP", 0, "top"),
    BOTTOM("BOTTOM", 1, "bottom");

    private static BlockSlabEnumBlockHalf[] adc;

    private String halfName;

    static {
        adc =
                new BlockSlabEnumBlockHalf[] {
                    net.minecraft.block.BlockSlabEnumBlockHalf.TOP, net.minecraft.block.BlockSlabEnumBlockHalf.BOTTOM
                };
    }

    BlockSlabEnumBlockHalf(String text, int j, String textValue) {
        this.halfName = textValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.halfName;
    }

    public String getName() {
        return this.halfName;
    }

    // region String decoding support

    // endregion

}
