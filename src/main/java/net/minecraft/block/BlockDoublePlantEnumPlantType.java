package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockDoublePlantEnumPlantType implements StringSerializable {
    SUNFLOWER("SUNFLOWER", 0, 0, "sunflower"),
    SYRINGA("SYRINGA", 1, 1, "syringa"),
    GRASS("GRASS", 2, 2, "double_grass", "grass"),
    FERN("FERN", 3, 3, "double_fern", "fern"),
    ROSE("ROSE", 4, 4, "double_rose", "rose"),
    PAEONIA("PAEONIA", 5, 5, "paeonia");
    private static BlockDoublePlantEnumPlantType[] SS;

    private static BlockDoublePlantEnumPlantType[] META_LOOKUP;

    private int meta;

    private String unlocalizedName;
    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    BlockDoublePlantEnumPlantType(String text, int j, int k, String textValue) {
        this(text, j, k, textValue, textValue);
    }

    public static BlockDoublePlantEnumPlantType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockDoublePlantEnumPlantType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockDoublePlantEnumPlantType.META_LOOKUP[i];
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    // region String decoding support

    // endregion

    public String getName() {
        return this.name;
    }

    static {
        net.minecraft.block.BlockDoublePlantEnumPlantType.META_LOOKUP =
                new BlockDoublePlantEnumPlantType[values().length];
        SS =
                new BlockDoublePlantEnumPlantType[] {
                    net.minecraft.block.BlockDoublePlantEnumPlantType.SUNFLOWER,
                    net.minecraft.block.BlockDoublePlantEnumPlantType.SYRINGA,
                    net.minecraft.block.BlockDoublePlantEnumPlantType.GRASS,
                    net.minecraft.block.BlockDoublePlantEnumPlantType.FERN,
                    net.minecraft.block.BlockDoublePlantEnumPlantType.ROSE,
                    net.minecraft.block.BlockDoublePlantEnumPlantType.PAEONIA
                };
        for (BlockDoublePlantEnumPlantType class_007c06c4a0d0904a53cc : values()) {
            net.minecraft.block.BlockDoublePlantEnumPlantType.META_LOOKUP[class_007c06c4a0d0904a53cc.getMeta()] =
                    class_007c06c4a0d0904a53cc;
        }
    }

    // region String decoding support

    // endregion

    BlockDoublePlantEnumPlantType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    public int getMeta() {
        return this.meta;
    }
}
