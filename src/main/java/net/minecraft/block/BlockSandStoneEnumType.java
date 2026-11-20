package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockSandStoneEnumType implements StringSerializable {
    DEFAULT("DEFAULT", 0, 0, "sandstone", "default"),
    CHISELED("CHISELED", 1, 1, "chiseled_sandstone", "chiseled"),
    SMOOTH("SMOOTH", 2, 2, "smooth_sandstone", "smooth");
    private static BlockSandStoneEnumType[] META_LOOKUP;

    private static BlockSandStoneEnumType[] VZ;

    private String name;

    private String unlocalizedName;
    private int metadata;

    @Override
    public String toString() {
        return this.name;
    }

    static {
        net.minecraft.block.BlockSandStoneEnumType.META_LOOKUP = new BlockSandStoneEnumType[values().length];
        VZ =
                new BlockSandStoneEnumType[] {
                    net.minecraft.block.BlockSandStoneEnumType.DEFAULT,
                    net.minecraft.block.BlockSandStoneEnumType.CHISELED,
                    net.minecraft.block.BlockSandStoneEnumType.SMOOTH
                };
        for (BlockSandStoneEnumType class_25585562b2de2cf805f3 : values()) {
            net.minecraft.block.BlockSandStoneEnumType.META_LOOKUP[class_25585562b2de2cf805f3.getMetadata()] =
                    class_25585562b2de2cf805f3;
        }
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static BlockSandStoneEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockSandStoneEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockSandStoneEnumType.META_LOOKUP[i];
    }

    BlockSandStoneEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.metadata = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    public String getName() {
        return this.name;
    }

    public int getMetadata() {
        return this.metadata;
    }
}
