package net.minecraft.block;

import net.minecraft.util.StringSerializable;

enum BlockDoublePlantEnumBlockHalf implements StringSerializable {
    UPPER("UPPER", 0),
    LOWER("LOWER", 1);
    private static BlockDoublePlantEnumBlockHalf[] SJ;

    BlockDoublePlantEnumBlockHalf(String text, int j) {}

    @Override
    public String toString() {
        return this.getName();
    }

    // region String decoding support

    // endregion

    static {
        SJ =
                new BlockDoublePlantEnumBlockHalf[] {
                    net.minecraft.block.BlockDoublePlantEnumBlockHalf.UPPER,
                    net.minecraft.block.BlockDoublePlantEnumBlockHalf.LOWER
                };
    }

    public String getName() {
        return this == net.minecraft.block.BlockDoublePlantEnumBlockHalf.UPPER ? "upper" : "lower";
    }

    // region String decoding support

    // endregion

}
