package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockWallEnumType implements StringSerializable {
    NORMAL("NORMAL", 0, 0, "cobblestone", "normal"),
    MOSSY("MOSSY", 1, 1, "mossy_cobblestone", "mossy");
    private static BlockWallEnumType[] aes;

    private static BlockWallEnumType[] META_LOOKUP;

    private String unlocalizedName;
    private String name;
    private int meta;

    @Override
    public String toString() {
        return this.name;
    }

    public static BlockWallEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockWallEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockWallEnumType.META_LOOKUP[i];
    }

    public String getName() {
        return this.name;
    }

    static {
        net.minecraft.block.BlockWallEnumType.META_LOOKUP = new BlockWallEnumType[values().length];
        aes =
                new BlockWallEnumType[] {
                    net.minecraft.block.BlockWallEnumType.NORMAL, net.minecraft.block.BlockWallEnumType.MOSSY
                };
        for (BlockWallEnumType class_fa60db92ce46ad665c04 : values()) {
            net.minecraft.block.BlockWallEnumType.META_LOOKUP[class_fa60db92ce46ad665c04.getMetadata()] =
                    class_fa60db92ce46ad665c04;
        }
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    // region String decoding support

    // endregion

    BlockWallEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    // region String decoding support

    // endregion

    public int getMetadata() {
        return this.meta;
    }
}
