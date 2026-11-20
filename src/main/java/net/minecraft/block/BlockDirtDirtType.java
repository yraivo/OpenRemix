package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockDirtDirtType implements StringSerializable {
    DIRT("DIRT", 0, 0, "dirt", "default"),
    COARSE_DIRT("COARSE_DIRT", 1, 1, "coarse_dirt", "coarse"),
    PODZOL("PODZOL", 2, 2, "podzol");
    private static BlockDirtDirtType[] METADATA_LOOKUP;

    private static BlockDirtDirtType[] Sp;

    private int metadata;
    private String name;
    private String unlocalizedName;

    static {
        net.minecraft.block.BlockDirtDirtType.METADATA_LOOKUP = new BlockDirtDirtType[values().length];
        Sp =
                new BlockDirtDirtType[] {
                    net.minecraft.block.BlockDirtDirtType.DIRT,
                    net.minecraft.block.BlockDirtDirtType.COARSE_DIRT,
                    net.minecraft.block.BlockDirtDirtType.PODZOL
                };
        for (BlockDirtDirtType class_69c256d0899932c1e81b : values()) {
            net.minecraft.block.BlockDirtDirtType.METADATA_LOOKUP[class_69c256d0899932c1e81b.getMetadata()] =
                    class_69c256d0899932c1e81b;
        }
    }

    public int getMetadata() {
        return this.metadata;
    }

    public static BlockDirtDirtType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockDirtDirtType.METADATA_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockDirtDirtType.METADATA_LOOKUP[i];
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public String getName() {
        return this.name;
    }

    BlockDirtDirtType(String text, int k, int j, String textValue, String textValue2) {
        this.metadata = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    // region String decoding support

    // endregion

    BlockDirtDirtType(String text, int j, int k, String textValue) {
        this(text, j, k, textValue, textValue);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
