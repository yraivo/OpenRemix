package net.minecraft.block;

import net.minecraft.init.Blocks;

public enum BlockFlowerEnumFlowerColor {
    YELLOW("YELLOW", 0),
    RED("RED", 1);

    private static BlockFlowerEnumFlowerColor[] SV;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    BlockFlowerEnumFlowerColor(String text, int j) {}

    static {
        SV =
                new BlockFlowerEnumFlowerColor[] {
                    net.minecraft.block.BlockFlowerEnumFlowerColor.YELLOW, net.minecraft.block.BlockFlowerEnumFlowerColor.RED
                };
    }

    // region String decoding support

    // endregion

    public BlockFlower getBlock() {
        return this == net.minecraft.block.BlockFlowerEnumFlowerColor.YELLOW
                ? Blocks.yellow_flower
                : Blocks.red_flower;
    }
}
