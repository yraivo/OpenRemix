package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockStoneEnumType implements StringSerializable {
    STONE("STONE", 0, 0, "stone"),
    GRANITE("GRANITE", 1, 1, "granite"),
    GRANITE_SMOOTH("GRANITE_SMOOTH", 2, 2, "smooth_granite", "graniteSmooth"),
    DIORITE("DIORITE", 3, 3, "diorite"),
    DIORITE_SMOOTH("DIORITE_SMOOTH", 4, 4, "smooth_diorite", "dioriteSmooth"),
    ANDESITE("ANDESITE", 5, 5, "andesite"),
    ANDESITE_SMOOTH("ANDESITE_SMOOTH", 6, 6, "smooth_andesite", "andesiteSmooth");

    private static BlockStoneEnumType[] BLOCKSTATES;

    private static BlockStoneEnumType[] adt;

    private String name;

    private int meta;
    private String unlocalizedName;

    // region String decoding support

    // endregion

    BlockStoneEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    public int getMetaFromState() {
        return this.meta;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockStoneEnumType.BLOCKSTATES = new BlockStoneEnumType[values().length];
        adt =
                new BlockStoneEnumType[] {
                    net.minecraft.block.BlockStoneEnumType.STONE,
                    net.minecraft.block.BlockStoneEnumType.GRANITE,
                    net.minecraft.block.BlockStoneEnumType.GRANITE_SMOOTH,
                    net.minecraft.block.BlockStoneEnumType.DIORITE,
                    net.minecraft.block.BlockStoneEnumType.DIORITE_SMOOTH,
                    net.minecraft.block.BlockStoneEnumType.ANDESITE,
                    net.minecraft.block.BlockStoneEnumType.ANDESITE_SMOOTH
                };
        for (BlockStoneEnumType class_2bb37347fad71f4bb1ab : values()) {
            net.minecraft.block.BlockStoneEnumType.BLOCKSTATES[class_2bb37347fad71f4bb1ab.getMetaFromState()] =
                    class_2bb37347fad71f4bb1ab;
        }
    }

    public static BlockStoneEnumType getStateFromMeta(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockStoneEnumType.BLOCKSTATES.length) {
            i = 0;
        }

        return net.minecraft.block.BlockStoneEnumType.BLOCKSTATES[i];
    }

    @Override
    public String toString() {
        return this.name;
    }

    BlockStoneEnumType(String text, int j, int k, String textValue) {
        this(text, j, k, textValue, textValue);
    }

    public String getName() {
        return this.name;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }
}
