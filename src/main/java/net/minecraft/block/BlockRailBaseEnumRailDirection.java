package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockRailBaseEnumRailDirection implements StringSerializable {
    NORTH_SOUTH("NORTH_SOUTH", 0, 0, "north_south"),
    EAST_WEST("EAST_WEST", 1, 1, "east_west"),
    ASCENDING_EAST("ASCENDING_EAST", 2, 2, "ascending_east"),
    ASCENDING_WEST("ASCENDING_WEST", 3, 3, "ascending_west"),
    ASCENDING_NORTH("ASCENDING_NORTH", 4, 4, "ascending_north"),
    ASCENDING_SOUTH("ASCENDING_SOUTH", 5, 5, "ascending_south"),
    SOUTH_EAST("SOUTH_EAST", 6, 6, "south_east"),
    SOUTH_WEST("SOUTH_WEST", 7, 7, "south_west"),
    NORTH_WEST("NORTH_WEST", 8, 8, "north_west"),
    NORTH_EAST("NORTH_EAST", 9, 9, "north_east");
    private static BlockRailBaseEnumRailDirection[] Vr;

    private static BlockRailBaseEnumRailDirection[] META_LOOKUP;

    private int meta;
    private String name;

    public static BlockRailBaseEnumRailDirection byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockRailBaseEnumRailDirection.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockRailBaseEnumRailDirection.META_LOOKUP[i];
    }

    BlockRailBaseEnumRailDirection(String text, int k, int j, String textValue) {
        this.meta = j;
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    public boolean isAscending() {
        return this == net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_NORTH
                || this == net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_EAST
                || this == net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_SOUTH
                || this == net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_WEST;
    }

    public String getName() {
        return this.name;
    }

    public int getMetadata() {
        return this.meta;
    }

    @Override
    public String toString() {
        return this.name;
    }

    static {
        net.minecraft.block.BlockRailBaseEnumRailDirection.META_LOOKUP =
                new BlockRailBaseEnumRailDirection[values().length];
        Vr =
                new BlockRailBaseEnumRailDirection[] {
                    net.minecraft.block.BlockRailBaseEnumRailDirection.NORTH_SOUTH,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.EAST_WEST,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_EAST,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_WEST,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_NORTH,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.ASCENDING_SOUTH,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.SOUTH_EAST,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.SOUTH_WEST,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.NORTH_WEST,
                    net.minecraft.block.BlockRailBaseEnumRailDirection.NORTH_EAST
                };
        for (BlockRailBaseEnumRailDirection class_6bdef280c14b07c6a30d : values()) {
            net.minecraft.block.BlockRailBaseEnumRailDirection.META_LOOKUP[
                            class_6bdef280c14b07c6a30d.getMetadata()] =
                    class_6bdef280c14b07c6a30d;
        }
    }

    // region String decoding support

    // endregion

}
