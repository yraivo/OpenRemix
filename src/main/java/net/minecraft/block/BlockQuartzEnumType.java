package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockQuartzEnumType implements StringSerializable {
    DEFAULT("DEFAULT", 0, 0, "default", "default"),
    CHISELED("CHISELED", 1, 1, "chiseled", "chiseled"),
    LINES_Y("LINES_Y", 2, 2, "lines_y", "lines"),
    LINES_X("LINES_X", 3, 3, "lines_x", "lines"),
    LINES_Z("LINES_Z", 4, 4, "lines_z", "lines");
    private static BlockQuartzEnumType[] Vf;

    private static BlockQuartzEnumType[] TYPES_ARRAY;

    private String unlocalizedName;

    private String field_176805_h;
    private int meta;

    public int getMetaFromState() {
        return this.meta;
    }

    static {
        net.minecraft.block.BlockQuartzEnumType.TYPES_ARRAY = new BlockQuartzEnumType[values().length];
        Vf =
                new BlockQuartzEnumType[] {
                    net.minecraft.block.BlockQuartzEnumType.DEFAULT,
                    net.minecraft.block.BlockQuartzEnumType.CHISELED,
                    net.minecraft.block.BlockQuartzEnumType.LINES_Y,
                    net.minecraft.block.BlockQuartzEnumType.LINES_X,
                    net.minecraft.block.BlockQuartzEnumType.LINES_Z
                };
        for (BlockQuartzEnumType class_be37632cd12eeaf94307 : values()) {
            net.minecraft.block.BlockQuartzEnumType.TYPES_ARRAY[class_be37632cd12eeaf94307.getMetaFromState()] =
                    class_be37632cd12eeaf94307;
        }
    }

    // region String decoding support

    // endregion

    BlockQuartzEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.field_176805_h = textValue;
        this.unlocalizedName = textValue2;
    }

    @Override
    public String toString() {
        return this.unlocalizedName;
    }

    // region String decoding support

    // endregion

    public static BlockQuartzEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockQuartzEnumType.TYPES_ARRAY.length) {
            i = 0;
        }

        return net.minecraft.block.BlockQuartzEnumType.TYPES_ARRAY[i];
    }

    public String getName() {
        return this.field_176805_h;
    }
}
