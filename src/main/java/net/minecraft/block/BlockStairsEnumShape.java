package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockStairsEnumShape implements StringSerializable {
    STRAIGHT("STRAIGHT", 0, "straight"),
    INNER_LEFT("INNER_LEFT", 1, "inner_left"),
    INNER_RIGHT("INNER_RIGHT", 2, "inner_right"),
    OUTER_LEFT("OUTER_LEFT", 3, "outer_left"),
    OUTER_RIGHT("OUTER_RIGHT", 4, "outer_right");
    private static BlockStairsEnumShape[] ads;

    private String name;

    public String getName() {
        return this.name;
    }

    BlockStairsEnumShape(String text, int j, String textValue) {
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        ads =
                new BlockStairsEnumShape[] {
                    net.minecraft.block.BlockStairsEnumShape.STRAIGHT,
                    net.minecraft.block.BlockStairsEnumShape.INNER_LEFT,
                    net.minecraft.block.BlockStairsEnumShape.INNER_RIGHT,
                    net.minecraft.block.BlockStairsEnumShape.OUTER_LEFT,
                    net.minecraft.block.BlockStairsEnumShape.OUTER_RIGHT
                };
    }

    @Override
    public String toString() {
        return this.name;
    }
}
