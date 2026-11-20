package net.minecraft.block;

import net.minecraft.util.Facing;
import net.minecraft.util.StringSerializable;

public enum BlockLeverEnumOrientation implements StringSerializable {
    DOWN_X("DOWN_X", 0, 0, "down_x", Facing.DOWN),
    EAST("EAST", 1, 1, "east", Facing.EAST),
    WEST("WEST", 2, 2, "west", Facing.WEST),
    SOUTH("SOUTH", 3, 3, "south", Facing.SOUTH),
    NORTH("NORTH", 4, 4, "north", Facing.NORTH),
    UP_Z("UP_Z", 5, 5, "up_z", Facing.UP),
    UP_X("UP_X", 6, 6, "up_x", Facing.UP),
    DOWN_Z("DOWN_Z", 7, 7, "down_z", Facing.DOWN);
    private static BlockLeverEnumOrientation[] Um;

    private static BlockLeverEnumOrientation[] META_LOOKUP;

    private Facing facing;
    private String name;

    private int meta;

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    @Override
    public String toString() {
        return this.name;
    }

    BlockLeverEnumOrientation(String text, int k, int j, String textValue, Facing facing) {
        this.meta = j;
        this.name = textValue;
        this.facing = facing;
    }

    static {
        net.minecraft.block.BlockLeverEnumOrientation.META_LOOKUP =
                new BlockLeverEnumOrientation[values().length];
        Um =
                new BlockLeverEnumOrientation[] {
                    net.minecraft.block.BlockLeverEnumOrientation.DOWN_X,
                    net.minecraft.block.BlockLeverEnumOrientation.EAST,
                    net.minecraft.block.BlockLeverEnumOrientation.WEST,
                    net.minecraft.block.BlockLeverEnumOrientation.SOUTH,
                    net.minecraft.block.BlockLeverEnumOrientation.NORTH,
                    net.minecraft.block.BlockLeverEnumOrientation.UP_Z,
                    net.minecraft.block.BlockLeverEnumOrientation.UP_X,
                    net.minecraft.block.BlockLeverEnumOrientation.DOWN_Z
                };
        for (BlockLeverEnumOrientation class_49d45d90decd5ca21a96 : values()) {
            net.minecraft.block.BlockLeverEnumOrientation.META_LOOKUP[
                            class_49d45d90decd5ca21a96.getMetadata()] =
                    class_49d45d90decd5ca21a96;
        }
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public static BlockLeverEnumOrientation forFacings(Facing facing, Facing facingValue) {
        switch (facing) {
            case DOWN:
                switch (facingValue.getAxis()) {
                    case X:
                        return net.minecraft.block.BlockLeverEnumOrientation.DOWN_X;
                    case Z:
                        return net.minecraft.block.BlockLeverEnumOrientation.DOWN_Z;
                    default:
                        throw new IllegalArgumentException(
                                "Invalid entityFacing " + facingValue + " for facing " + facing);
                }
            case UP:
                switch (facingValue.getAxis()) {
                    case X:
                        return net.minecraft.block.BlockLeverEnumOrientation.UP_X;
                    case Z:
                        return net.minecraft.block.BlockLeverEnumOrientation.UP_Z;
                    default:
                        throw new IllegalArgumentException(
                                "Invalid entityFacing " + facingValue + " for facing " + facing);
                }
            case NORTH:
                return net.minecraft.block.BlockLeverEnumOrientation.NORTH;
            case SOUTH:
                return net.minecraft.block.BlockLeverEnumOrientation.SOUTH;
            case WEST:
                return net.minecraft.block.BlockLeverEnumOrientation.WEST;
            case EAST:
                return net.minecraft.block.BlockLeverEnumOrientation.EAST;
            default:
                throw new IllegalArgumentException("Invalid facing: " + facing);
        }
    }

    public static BlockLeverEnumOrientation byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockLeverEnumOrientation.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockLeverEnumOrientation.META_LOOKUP[i];
    }

    public Facing getFacing() {
        return this.facing;
    }

    // region String decoding support

    // endregion

    public int getMetadata() {
        return this.meta;
    }
}
