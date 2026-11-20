package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockPrismarineEnumType implements StringSerializable {
    ROUGH("ROUGH", 0, 0, "prismarine", "rough"),
    BRICKS("BRICKS", 1, 1, "prismarine_bricks", "bricks"),
    DARK("DARK", 2, 2, "dark_prismarine", "dark");

    private static BlockPrismarineEnumType[] UU;

    private static BlockPrismarineEnumType[] META_LOOKUP;

    private int meta;
    private String unlocalizedName;
    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getName() {
        return this.name;
    }

    static {
        net.minecraft.block.BlockPrismarineEnumType.META_LOOKUP = new BlockPrismarineEnumType[values().length];
        UU =
                new BlockPrismarineEnumType[] {
                    net.minecraft.block.BlockPrismarineEnumType.ROUGH,
                    net.minecraft.block.BlockPrismarineEnumType.BRICKS,
                    net.minecraft.block.BlockPrismarineEnumType.DARK
                };
        for (BlockPrismarineEnumType class_c7e9fede87cfa35b6afe : values()) {
            net.minecraft.block.BlockPrismarineEnumType.META_LOOKUP[class_c7e9fede87cfa35b6afe.getMetadata()] =
                    class_c7e9fede87cfa35b6afe;
        }
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public static BlockPrismarineEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockPrismarineEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockPrismarineEnumType.META_LOOKUP[i];
    }

    public int getMetadata() {
        return this.meta;
    }

    BlockPrismarineEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }
}
