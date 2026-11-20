package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockRedSandstoneEnumType implements StringSerializable {
    DEFAULT("DEFAULT", 0, 0, "red_sandstone", "default"),
    CHISELED("CHISELED", 1, 1, "chiseled_red_sandstone", "chiseled"),
    SMOOTH("SMOOTH", 2, 2, "smooth_red_sandstone", "smooth");
    private static BlockRedSandstoneEnumType[] VC;

    private static BlockRedSandstoneEnumType[] META_LOOKUP;

    private int meta;
    private String unlocalizedName;
    private String name;

    static {
        net.minecraft.block.BlockRedSandstoneEnumType.META_LOOKUP =
                new BlockRedSandstoneEnumType[values().length];
        VC =
                new BlockRedSandstoneEnumType[] {
                    net.minecraft.block.BlockRedSandstoneEnumType.DEFAULT,
                    net.minecraft.block.BlockRedSandstoneEnumType.CHISELED,
                    net.minecraft.block.BlockRedSandstoneEnumType.SMOOTH
                };
        for (BlockRedSandstoneEnumType class_b0e07c5862dc336937b6 : values()) {
            net.minecraft.block.BlockRedSandstoneEnumType.META_LOOKUP[
                            class_b0e07c5862dc336937b6.getMetaFromState()] =
                    class_b0e07c5862dc336937b6;
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public static BlockRedSandstoneEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockRedSandstoneEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockRedSandstoneEnumType.META_LOOKUP[i];
    }

    public int getMetaFromState() {
        return this.meta;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    BlockRedSandstoneEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }
}
