package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockPlanksEnumType implements StringSerializable {
    OAK(initializeConstantState(), 0, 0, "oak"),
    SPRUCE("SPRUCE", 1, 1, "spruce"),
    BIRCH("BIRCH", 2, 2, "birch"),
    JUNGLE("JUNGLE", 3, 3, "jungle"),
    ACACIA("ACACIA", 4, 4, "acacia"),
    DARK_OAK("DARK_OAK", 5, 5, "dark_oak", "big_oak");
    private static BlockPlanksEnumType[] UK;

    private static BlockPlanksEnumType[] UG;

    private static BlockPlanksEnumType[] META_LOOKUP;

    private int meta;
    private static int legacyBlockFlowToken;
    private String name;
    private String unlocalizedName;

    public static BlockPlanksEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockPlanksEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockPlanksEnumType.META_LOOKUP[i];
    }

    public static void setLegacyBlockFlowToken(int i) {
        net.minecraft.block.BlockPlanksEnumType.legacyBlockFlowToken = i;
    }

    static {
        UK =
                new BlockPlanksEnumType[] {
                    net.minecraft.block.BlockPlanksEnumType.OAK,
                    net.minecraft.block.BlockPlanksEnumType.SPRUCE,
                    net.minecraft.block.BlockPlanksEnumType.BIRCH,
                    net.minecraft.block.BlockPlanksEnumType.JUNGLE,
                    net.minecraft.block.BlockPlanksEnumType.ACACIA,
                    net.minecraft.block.BlockPlanksEnumType.DARK_OAK
                };
        net.minecraft.block.BlockPlanksEnumType.META_LOOKUP =
                new BlockPlanksEnumType[values().length];
        UG =
                new BlockPlanksEnumType[] {
                    net.minecraft.block.BlockPlanksEnumType.OAK,
                    net.minecraft.block.BlockPlanksEnumType.SPRUCE,
                    net.minecraft.block.BlockPlanksEnumType.BIRCH,
                    net.minecraft.block.BlockPlanksEnumType.JUNGLE,
                    net.minecraft.block.BlockPlanksEnumType.ACACIA,
                    net.minecraft.block.BlockPlanksEnumType.DARK_OAK
                };
        for (BlockPlanksEnumType class_fefbb747b86a62a2e0b4 : values()) {
            net.minecraft.block.BlockPlanksEnumType.META_LOOKUP[
                            class_fefbb747b86a62a2e0b4.getMetadata()] =
                    class_fefbb747b86a62a2e0b4;
        }
    }

    public static int getLegacyBlockFlowToken() {
        return net.minecraft.block.BlockPlanksEnumType.legacyBlockFlowToken;
    }

    public static int getInvertedBlockFlowToken() {
        int i = getLegacyBlockFlowToken();
        return i == 0 ? 14 : 0;
    }

    // region String decoding support

    // endregion

    BlockPlanksEnumType(String text, int j, int k, String textValue) {
        this(text, j, k, textValue, textValue);
    }

    public int getMetadata() {
        return this.meta;
    }

    // region String decoding support

    // endregion

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public String getName() {
        return this.name;
    }

    BlockPlanksEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static BlockPlanksEnumType[] __recovery_values171() {
        return UK.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        setLegacyBlockFlowToken(87);
        return "OAK";
    }
}
