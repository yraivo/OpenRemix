package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockStairsEnumHalf implements StringSerializable {
    TOP("TOP", 0, "top"),
    BOTTOM("BOTTOM", 1, "bottom");

    private static BlockStairsEnumHalf[] adi;

    private String name;

    // region String decoding support

    // endregion

    static {
        adi =
                new BlockStairsEnumHalf[] {
                    net.minecraft.block.BlockStairsEnumHalf.TOP, net.minecraft.block.BlockStairsEnumHalf.BOTTOM
                };
    }

    @Override
    public String toString() {
        return this.name;
    }

    BlockStairsEnumHalf(String text, int j, String textValue) {
        this.name = textValue;
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

}
