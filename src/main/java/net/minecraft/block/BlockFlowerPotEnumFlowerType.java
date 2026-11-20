package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockFlowerPotEnumFlowerType implements StringSerializable {
    EMPTY("EMPTY", 0, "empty"),
    POPPY("POPPY", 1, "rose"),
    BLUE_ORCHID("BLUE_ORCHID", 2, "blue_orchid"),
    ALLIUM("ALLIUM", 3, "allium"),
    HOUSTONIA("HOUSTONIA", 4, "houstonia"),
    RED_TULIP("RED_TULIP", 5, "red_tulip"),
    ORANGE_TULIP("ORANGE_TULIP", 6, "orange_tulip"),
    WHITE_TULIP("WHITE_TULIP", 7, "white_tulip"),
    PINK_TULIP("PINK_TULIP", 8, "pink_tulip"),
    OXEYE_DAISY("OXEYE_DAISY", 9, "oxeye_daisy"),
    DANDELION("DANDELION", 10, "dandelion"),
    OAK_SAPLING("OAK_SAPLING", 11, "oak_sapling"),
    SPRUCE_SAPLING("SPRUCE_SAPLING", 12, "spruce_sapling"),
    BIRCH_SAPLING("BIRCH_SAPLING", 13, "birch_sapling"),
    JUNGLE_SAPLING("JUNGLE_SAPLING", 14, "jungle_sapling"),
    ACACIA_SAPLING("ACACIA_SAPLING", 15, "acacia_sapling"),
    DARK_OAK_SAPLING("DARK_OAK_SAPLING", 16, "dark_oak_sapling"),
    MUSHROOM_RED("MUSHROOM_RED", 17, "mushroom_red"),
    MUSHROOM_BROWN("MUSHROOM_BROWN", 18, "mushroom_brown"),
    DEAD_BUSH("DEAD_BUSH", 19, "dead_bush"),
    FERN("FERN", 20, "fern"),
    CACTUS("CACTUS", 21, "cactus");
    private static BlockFlowerPotEnumFlowerType[] TE;

    private String name;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    BlockFlowerPotEnumFlowerType(String text, int j, String textValue) {
        this.name = textValue;
    }

    @Override
    public String toString() {
        return this.name;
    }

    static {
        TE =
                new BlockFlowerPotEnumFlowerType[] {
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.EMPTY,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.POPPY,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.BLUE_ORCHID,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.ALLIUM,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.HOUSTONIA,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.RED_TULIP,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.ORANGE_TULIP,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.WHITE_TULIP,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.PINK_TULIP,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.OXEYE_DAISY,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.DANDELION,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.OAK_SAPLING,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.SPRUCE_SAPLING,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.BIRCH_SAPLING,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.JUNGLE_SAPLING,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.ACACIA_SAPLING,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.DARK_OAK_SAPLING,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.MUSHROOM_RED,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.MUSHROOM_BROWN,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.DEAD_BUSH,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.FERN,
                    net.minecraft.block.BlockFlowerPotEnumFlowerType.CACTUS
                };
    }

    public String getName() {
        return this.name;
    }
}
