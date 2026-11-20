package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockDoorEnumDoorHalf implements StringSerializable {
    UPPER("UPPER", 0),
    LOWER("LOWER", 1);
    private static BlockDoorEnumDoorHalf[] Sx;

    public String getName() {
        return this == net.minecraft.block.BlockDoorEnumDoorHalf.UPPER ? "upper" : "lower";
    }

    BlockDoorEnumDoorHalf(String text, int j) {}

    static {
        Sx =
                new BlockDoorEnumDoorHalf[] {
                    net.minecraft.block.BlockDoorEnumDoorHalf.UPPER, net.minecraft.block.BlockDoorEnumDoorHalf.LOWER
                };
    }

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.getName();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
