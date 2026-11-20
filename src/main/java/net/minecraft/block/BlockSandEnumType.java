package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.StringSerializable;

public enum BlockSandEnumType implements StringSerializable {
    SAND("SAND", 0, 0, "sand", "default", MapColor.sandColor),
    RED_SAND("RED_SAND", 1, 1, "red_sand", "red", MapColor.dirtColor);
    private static BlockSandEnumType[] META_LOOKUP;

    private static BlockSandEnumType[] VP;

    private MapColor mapColor;
    private int meta;

    private String unlocalizedName;
    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    // region String decoding support

    // endregion

    BlockSandEnumType(
            String text, int k, int j, String textValue, String textValue2, MapColor mapColor) {
        this.meta = j;
        this.name = textValue;
        this.mapColor = mapColor;
        this.unlocalizedName = textValue2;
    }

    // region String decoding support

    // endregion

    public static BlockSandEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockSandEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockSandEnumType.META_LOOKUP[i];
    }

    public String getName() {
        return this.name;
    }

    public int getMetadata() {
        return this.meta;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    static {
        net.minecraft.block.BlockSandEnumType.META_LOOKUP = new BlockSandEnumType[values().length];
        VP =
                new BlockSandEnumType[] {
                    net.minecraft.block.BlockSandEnumType.SAND, net.minecraft.block.BlockSandEnumType.RED_SAND
                };
        for (BlockSandEnumType class_c5f377ec20c03afe97f5 : values()) {
            net.minecraft.block.BlockSandEnumType.META_LOOKUP[class_c5f377ec20c03afe97f5.getMetadata()] =
                    class_c5f377ec20c03afe97f5;
        }
    }
}
