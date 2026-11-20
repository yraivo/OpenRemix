package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockBedEnumPartType implements StringSerializable {
    HEAD("HEAD", 0, "head"),
    FOOT("FOOT", 1, "foot");
    private static BlockBedEnumPartType[] Sl;

    private String name;

    public String getName() {
        return this.name;
    }

    BlockBedEnumPartType(String text, int j, String textValue) {
        this.name = textValue;
    }

    @Override
    public String toString() {
        return this.name;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        Sl =
                new BlockBedEnumPartType[] {
                    net.minecraft.block.BlockBedEnumPartType.HEAD, net.minecraft.block.BlockBedEnumPartType.FOOT
                };
    }

    // region String decoding support

    // endregion

}
