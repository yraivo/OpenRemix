package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockTallGrassEnumType implements StringSerializable {
    DEAD_BUSH("DEAD_BUSH", 0, 0, "dead_bush"),
    GRASS("GRASS", 1, 1, "tall_grass"),
    FERN("FERN", 2, 2, "fern");

    private static BlockTallGrassEnumType[] META_LOOKUP;

    private static BlockTallGrassEnumType[] aee;

    private String name;

    private int meta;

    // region String decoding support

    // endregion

    BlockTallGrassEnumType(String text, int k, int j, String textValue) {
        this.meta = j;
        this.name = textValue;
    }

    static {
        net.minecraft.block.BlockTallGrassEnumType.META_LOOKUP = new BlockTallGrassEnumType[values().length];
        aee =
                new BlockTallGrassEnumType[] {
                    net.minecraft.block.BlockTallGrassEnumType.DEAD_BUSH,
                    net.minecraft.block.BlockTallGrassEnumType.GRASS,
                    net.minecraft.block.BlockTallGrassEnumType.FERN
                };
        for (BlockTallGrassEnumType class_4d470c0aea47cc2e92ca : values()) {
            net.minecraft.block.BlockTallGrassEnumType.META_LOOKUP[class_4d470c0aea47cc2e92ca.getMeta()] =
                    class_4d470c0aea47cc2e92ca;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getMeta() {
        return this.meta;
    }

    public static BlockTallGrassEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockTallGrassEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockTallGrassEnumType.META_LOOKUP[i];
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

}
