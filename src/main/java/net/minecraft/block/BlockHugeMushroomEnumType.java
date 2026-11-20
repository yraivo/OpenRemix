package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockHugeMushroomEnumType implements StringSerializable {
    NORTH_WEST("NORTH_WEST", 0, 1, "north_west"),
    NORTH("NORTH", 1, 2, "north"),
    NORTH_EAST("NORTH_EAST", 2, 3, "north_east"),
    WEST("WEST", 3, 4, "west"),
    CENTER("CENTER", 4, 5, "center"),
    EAST("EAST", 5, 6, "east"),
    SOUTH_WEST("SOUTH_WEST", 6, 7, "south_west"),
    SOUTH("SOUTH", 7, 8, "south"),
    SOUTH_EAST("SOUTH_EAST", 8, 9, "south_east"),
    STEM("STEM", 9, 10, "stem"),
    ALL_INSIDE("ALL_INSIDE", 10, 0, "all_inside"),
    ALL_OUTSIDE("ALL_OUTSIDE", 11, 14, "all_outside"),
    ALL_STEM("ALL_STEM", 12, 15, "all_stem");
    private static BlockHugeMushroomEnumType[] META_LOOKUP;

    private static BlockHugeMushroomEnumType[] TT;

    private int meta;

    private String name;

    public int getMetadata() {
        return this.meta;
    }

    @Override
    public String toString() {
        return this.name;
    }

    BlockHugeMushroomEnumType(String text, int k, int j, String textValue) {
        this.meta = j;
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockHugeMushroomEnumType.META_LOOKUP = new BlockHugeMushroomEnumType[16];
        TT =
                new BlockHugeMushroomEnumType[] {
                    net.minecraft.block.BlockHugeMushroomEnumType.NORTH_WEST,
                    net.minecraft.block.BlockHugeMushroomEnumType.NORTH,
                    net.minecraft.block.BlockHugeMushroomEnumType.NORTH_EAST,
                    net.minecraft.block.BlockHugeMushroomEnumType.WEST,
                    net.minecraft.block.BlockHugeMushroomEnumType.CENTER,
                    net.minecraft.block.BlockHugeMushroomEnumType.EAST,
                    net.minecraft.block.BlockHugeMushroomEnumType.SOUTH_WEST,
                    net.minecraft.block.BlockHugeMushroomEnumType.SOUTH,
                    net.minecraft.block.BlockHugeMushroomEnumType.SOUTH_EAST,
                    net.minecraft.block.BlockHugeMushroomEnumType.STEM,
                    net.minecraft.block.BlockHugeMushroomEnumType.ALL_INSIDE,
                    net.minecraft.block.BlockHugeMushroomEnumType.ALL_OUTSIDE,
                    net.minecraft.block.BlockHugeMushroomEnumType.ALL_STEM
                };
        for (BlockHugeMushroomEnumType class_1f226c02985a1dbf844b : values()) {
            net.minecraft.block.BlockHugeMushroomEnumType.META_LOOKUP[class_1f226c02985a1dbf844b.getMetadata()] =
                    class_1f226c02985a1dbf844b;
        }
    }

    // region String decoding support

    // endregion

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public static BlockHugeMushroomEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockHugeMushroomEnumType.META_LOOKUP.length) {
            i = 0;
        }

        BlockHugeMushroomEnumType blockHugeMushroomEnumType =
                net.minecraft.block.BlockHugeMushroomEnumType.META_LOOKUP[i];
        return blockHugeMushroomEnumType == null
                ? net.minecraft.block.BlockHugeMushroomEnumType.META_LOOKUP[0]
                : blockHugeMushroomEnumType;
    }
}
