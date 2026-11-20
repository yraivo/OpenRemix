package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockStoneSlabNewEnumType implements StringSerializable {
    RED_SANDSTONE("RED_SANDSTONE", 0, 0, "red_sandstone");
    private static BlockStoneSlabNewEnumType[] META_LOOKUP;

    private static BlockStoneSlabNewEnumType[] adY;

    private int meta;
    private String name;

    public String getName() {
        return this.name;
    }

    public String getUnlocalizedName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public int getMetadata() {
        return this.meta;
    }

    static {
        net.minecraft.block.BlockStoneSlabNewEnumType.META_LOOKUP =
                new BlockStoneSlabNewEnumType[values().length];
        adY = new BlockStoneSlabNewEnumType[] {net.minecraft.block.BlockStoneSlabNewEnumType.RED_SANDSTONE};
        for (BlockStoneSlabNewEnumType class_9ab0fbd5ee32028cb138 : values()) {
            net.minecraft.block.BlockStoneSlabNewEnumType.META_LOOKUP[class_9ab0fbd5ee32028cb138.getMetadata()] =
                    class_9ab0fbd5ee32028cb138;
        }
    }

    BlockStoneSlabNewEnumType(String text, int k, int j, String textValue) {
        this.meta = j;
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    public static BlockStoneSlabNewEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockStoneSlabNewEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockStoneSlabNewEnumType.META_LOOKUP[i];
    }

    @Override
    public String toString() {
        return this.name;
    }
}
