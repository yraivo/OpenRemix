package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockStoneBrickEnumType implements StringSerializable {
    DEFAULT("DEFAULT", 0, 0, "stonebrick", "default"),
    MOSSY("MOSSY", 1, 1, "mossy_stonebrick", "mossy"),
    CRACKED("CRACKED", 2, 2, "cracked_stonebrick", "cracked"),
    CHISELED("CHISELED", 3, 3, "chiseled_stonebrick", "chiseled");
    private static BlockStoneBrickEnumType[] adJ;

    private static BlockStoneBrickEnumType[] TYPES_ARRAY;

    private String name;
    private int meta;
    private String unlocalizedName;

    // region String decoding support

    // endregion

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static BlockStoneBrickEnumType getStateFromMeta(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockStoneBrickEnumType.TYPES_ARRAY.length) {
            i = 0;
        }

        return net.minecraft.block.BlockStoneBrickEnumType.TYPES_ARRAY[i];
    }

    static {
        net.minecraft.block.BlockStoneBrickEnumType.TYPES_ARRAY = new BlockStoneBrickEnumType[values().length];
        adJ =
                new BlockStoneBrickEnumType[] {
                    net.minecraft.block.BlockStoneBrickEnumType.DEFAULT,
                    net.minecraft.block.BlockStoneBrickEnumType.MOSSY,
                    net.minecraft.block.BlockStoneBrickEnumType.CRACKED,
                    net.minecraft.block.BlockStoneBrickEnumType.CHISELED
                };
        for (BlockStoneBrickEnumType class_12655d5ae28837847e4e : values()) {
            net.minecraft.block.BlockStoneBrickEnumType.TYPES_ARRAY[
                            class_12655d5ae28837847e4e.getMetaFromState()] =
                    class_12655d5ae28837847e4e;
        }
    }

    BlockStoneBrickEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    public int getMetaFromState() {
        return this.meta;
    }

    public String getVariantName() {
        return this.unlocalizedName;
    }

    // region String decoding support

    // endregion

}
