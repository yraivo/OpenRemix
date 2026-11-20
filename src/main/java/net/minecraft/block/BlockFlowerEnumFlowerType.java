package net.minecraft.block;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.util.StringSerializable;

import java.util.Collection;

public enum BlockFlowerEnumFlowerType implements StringSerializable {
    DANDELION("DANDELION", 0, BlockFlowerEnumFlowerColor.YELLOW, 0, "dandelion"),
    POPPY("POPPY", 1, BlockFlowerEnumFlowerColor.RED, 0, "poppy"),
    BLUE_ORCHID("BLUE_ORCHID", 2, BlockFlowerEnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"),
    ALLIUM("ALLIUM", 3, BlockFlowerEnumFlowerColor.RED, 2, "allium"),
    HOUSTONIA("HOUSTONIA", 4, BlockFlowerEnumFlowerColor.RED, 3, "houstonia"),
    RED_TULIP("RED_TULIP", 5, BlockFlowerEnumFlowerColor.RED, 4, "red_tulip", "tulipRed"),
    ORANGE_TULIP(
            "ORANGE_TULIP", 6, BlockFlowerEnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"),
    WHITE_TULIP("WHITE_TULIP", 7, BlockFlowerEnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"),
    PINK_TULIP("PINK_TULIP", 8, BlockFlowerEnumFlowerColor.RED, 7, "pink_tulip", "tulipPink"),
    OXEYE_DAISY("OXEYE_DAISY", 9, BlockFlowerEnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");
    private static BlockFlowerEnumFlowerType[] Tj;

    private String unlocalizedName;
    private BlockFlowerEnumFlowerColor blockType;

    private static BlockFlowerEnumFlowerType[][] TYPES_FOR_BLOCK;
    private String name;
    private int meta;

    BlockFlowerEnumFlowerType(
            String text,
            int k,
            BlockFlowerEnumFlowerColor blockFlowerEnumFlowerColor,
            int j,
            String textValue,
            String textValue2) {
        this.blockType = blockFlowerEnumFlowerColor;
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    public static BlockFlowerEnumFlowerType getType(
            BlockFlowerEnumFlowerColor blockFlowerEnumFlowerColor, int i) {
        BlockFlowerEnumFlowerType[] blockFlowerEnumFlowerTypeArray =
                net.minecraft.block.BlockFlowerEnumFlowerType.TYPES_FOR_BLOCK[
                        blockFlowerEnumFlowerColor.ordinal()];
        if (i < 0 || i >= blockFlowerEnumFlowerTypeArray.length) {
            i = 0;
        }

        return blockFlowerEnumFlowerTypeArray[i];
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public BlockFlowerEnumFlowerColor getBlockType() {
        return this.blockType;
    }

    static {
        Tj =
                new BlockFlowerEnumFlowerType[] {
                    net.minecraft.block.BlockFlowerEnumFlowerType.DANDELION,
                    net.minecraft.block.BlockFlowerEnumFlowerType.POPPY,
                    net.minecraft.block.BlockFlowerEnumFlowerType.BLUE_ORCHID,
                    net.minecraft.block.BlockFlowerEnumFlowerType.ALLIUM,
                    net.minecraft.block.BlockFlowerEnumFlowerType.HOUSTONIA,
                    net.minecraft.block.BlockFlowerEnumFlowerType.RED_TULIP,
                    net.minecraft.block.BlockFlowerEnumFlowerType.ORANGE_TULIP,
                    net.minecraft.block.BlockFlowerEnumFlowerType.WHITE_TULIP,
                    net.minecraft.block.BlockFlowerEnumFlowerType.PINK_TULIP,
                    net.minecraft.block.BlockFlowerEnumFlowerType.OXEYE_DAISY
                };
        net.minecraft.block.BlockFlowerEnumFlowerType.TYPES_FOR_BLOCK =
                new BlockFlowerEnumFlowerType[BlockFlowerEnumFlowerColor.values().length][];
        for (BlockFlowerEnumFlowerColor class_a677ea555d1d88426ce1 :
                BlockFlowerEnumFlowerColor.values()) {
            Collection collection =
                    Collections2.filter(
                            Lists.newArrayList(values()),
                            new BlockFlowerEnumFlowerTypePredicate(class_a677ea555d1d88426ce1));
            net.minecraft.block.BlockFlowerEnumFlowerType.TYPES_FOR_BLOCK[
                            class_a677ea555d1d88426ce1.ordinal()] =
                    (BlockFlowerEnumFlowerType[])
                            collection.toArray(new BlockFlowerEnumFlowerType[collection.size()]);
        }
    }

    // region String decoding support

    // endregion

    public int getMeta() {
        return this.meta;
    }

    BlockFlowerEnumFlowerType(
            String text,
            int j,
            BlockFlowerEnumFlowerColor blockFlowerEnumFlowerColor,
            int k,
            String textValue) {
        this(text, j, blockFlowerEnumFlowerColor, k, textValue, textValue);
    }

    public static BlockFlowerEnumFlowerType[] getTypes(
            BlockFlowerEnumFlowerColor blockFlowerEnumFlowerColor) {
        return net.minecraft.block.BlockFlowerEnumFlowerType.TYPES_FOR_BLOCK[
                blockFlowerEnumFlowerColor.ordinal()];
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    // Helper used only by this class.
    public static final class BlockFlowerEnumFlowerTypePredicate
            implements com.google.common.base.Predicate {
        net.minecraft.block.BlockFlowerEnumFlowerColor aUu;

        public boolean apply(java.lang.Object object) {
            return this.method_d11a6eeda37adcb59377(
                    (net.minecraft.block.BlockFlowerEnumFlowerType) object);
        }

        public boolean method_d11a6eeda37adcb59377(
                net.minecraft.block.BlockFlowerEnumFlowerType blockFlowerEnumFlowerType) {
            return blockFlowerEnumFlowerType.getBlockType() == this.aUu;
        }

        public BlockFlowerEnumFlowerTypePredicate(
                net.minecraft.block.BlockFlowerEnumFlowerColor aUuValue) {
            this.aUu = aUuValue;
        }
    }
}
