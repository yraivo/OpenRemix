package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockStoneSlabEnumType implements StringSerializable {
    STONE("STONE", 0, 0, "stone"),
    SAND("SAND", 1, 1, "sandstone", "sand"),
    WOOD("WOOD", 2, 2, "wood_old", "wood"),
    COBBLESTONE("COBBLESTONE", 3, 3, "cobblestone", "cobble"),
    BRICK("BRICK", 4, 4, "brick"),
    SMOOTHBRICK("SMOOTHBRICK", 5, 5, "stone_brick", "smoothStoneBrick"),
    NETHERBRICK("NETHERBRICK", 6, 6, "nether_brick", "netherBrick"),
    QUARTZ("QUARTZ", 7, 7, "quartz");

    private static BlockStoneSlabEnumType[] META_LOOKUP;

    private static BlockStoneSlabEnumType[] adN;

    private String name;
    private int meta;
    private String unlocalizedName;

    static {
        net.minecraft.block.BlockStoneSlabEnumType.META_LOOKUP = new BlockStoneSlabEnumType[values().length];
        adN =
                new BlockStoneSlabEnumType[] {
                    net.minecraft.block.BlockStoneSlabEnumType.STONE,
                    net.minecraft.block.BlockStoneSlabEnumType.SAND,
                    net.minecraft.block.BlockStoneSlabEnumType.WOOD,
                    net.minecraft.block.BlockStoneSlabEnumType.COBBLESTONE,
                    net.minecraft.block.BlockStoneSlabEnumType.BRICK,
                    net.minecraft.block.BlockStoneSlabEnumType.SMOOTHBRICK,
                    net.minecraft.block.BlockStoneSlabEnumType.NETHERBRICK,
                    net.minecraft.block.BlockStoneSlabEnumType.QUARTZ
                };
        for (BlockStoneSlabEnumType class_fc068d8e5c7ea3ef1a01 : values()) {
            net.minecraft.block.BlockStoneSlabEnumType.META_LOOKUP[class_fc068d8e5c7ea3ef1a01.getMetadata()] =
                    class_fc068d8e5c7ea3ef1a01;
        }
    }

    // region String decoding support

    // endregion

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    BlockStoneSlabEnumType(String text, int j, int k, String textValue) {
        this(text, j, k, textValue, textValue);
    }

    // region String decoding support

    // endregion

    BlockStoneSlabEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public static BlockStoneSlabEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockStoneSlabEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockStoneSlabEnumType.META_LOOKUP[i];
    }

    public int getMetadata() {
        return this.meta;
    }
}
