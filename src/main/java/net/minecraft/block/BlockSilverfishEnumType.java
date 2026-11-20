package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.StringSerializable;

public enum BlockSilverfishEnumType implements StringSerializable {
    STONE("STONE", 0, 0, "stone") {
        public BlockState getModelBlock() {
            return Blocks.stone
                    .getDefaultState()
                    .withProperty(BlockStone.VARIANT_PROP, BlockStoneEnumType.STONE);
        }
    },
    COBBLESTONE("COBBLESTONE", 1, 1, "cobblestone", "cobble") {
        public BlockState getModelBlock() {
            return Blocks.cobblestone.getDefaultState();
        }
    },
    STONEBRICK("STONEBRICK", 2, 2, "stone_brick", "brick") {
        public BlockState getModelBlock() {
            return Blocks.stonebrick
                    .getDefaultState()
                    .withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrickEnumType.DEFAULT);
        }
    },
    MOSSY_STONEBRICK("MOSSY_STONEBRICK", 3, 3, "mossy_brick", "mossybrick") {
        public BlockState getModelBlock() {
            return Blocks.stonebrick
                    .getDefaultState()
                    .withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrickEnumType.MOSSY);
        }
    },
    CRACKED_STONEBRICK("CRACKED_STONEBRICK", 4, 4, "cracked_brick", "crackedbrick") {
        public BlockState getModelBlock() {
            return Blocks.stonebrick
                    .getDefaultState()
                    .withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrickEnumType.CRACKED);
        }
    },
    CHISELED_STONEBRICK("CHISELED_STONEBRICK", 5, 5, "chiseled_brick", "chiseledbrick") {
        public BlockState getModelBlock() {
            return Blocks.stonebrick
                    .getDefaultState()
                    .withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrickEnumType.CHISELED);
        }
    };
    private static BlockSilverfishEnumType[] Wj;

    private static BlockSilverfishEnumType[] Wg;

    private static BlockSilverfishEnumType[] META_LOOKUP;

    private String name;
    private int meta;

    private String unlocalizedName;

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    BlockSilverfishEnumType(String text, int k, int j, String textValue, String textValue2) {
        this.meta = j;
        this.name = textValue;
        this.unlocalizedName = textValue2;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static BlockSilverfishEnumType forModelBlock(BlockState blockState) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockSilverfishEnumType[] blockSilverfishEnumTypeArray = values();
        int j = blockSilverfishEnumTypeArray.length;
        int k = 0;
        int i = l;

        while (k < j) {
            BlockSilverfishEnumType blockSilverfishEnumType = blockSilverfishEnumTypeArray[k];
            if (blockState == blockSilverfishEnumType.getModelBlock()) {
                return blockSilverfishEnumType;
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return net.minecraft.block.BlockSilverfishEnumType.STONE;
    }

    // region String decoding support

    // endregion

    public BlockState getModelBlock() {
        throw new UnsupportedOperationException();
    }

    // region String decoding support

    // endregion

    public int getMetadata() {
        return this.meta;
    }

    // region String decoding support

    // endregion

    public static BlockSilverfishEnumType byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockSilverfishEnumType.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.block.BlockSilverfishEnumType.META_LOOKUP[i];
    }

    BlockSilverfishEnumType(String text, int j, int k, String textValue) {
        this(text, j, k, textValue, textValue);
    }

    public String getName() {
        return this.name;
    }

    static {
        Wg =
                new BlockSilverfishEnumType[] {
                    net.minecraft.block.BlockSilverfishEnumType.STONE,
                    net.minecraft.block.BlockSilverfishEnumType.COBBLESTONE,
                    net.minecraft.block.BlockSilverfishEnumType.STONEBRICK,
                    net.minecraft.block.BlockSilverfishEnumType.MOSSY_STONEBRICK,
                    net.minecraft.block.BlockSilverfishEnumType.CRACKED_STONEBRICK,
                    net.minecraft.block.BlockSilverfishEnumType.CHISELED_STONEBRICK
                };
        net.minecraft.block.BlockSilverfishEnumType.META_LOOKUP =
                new BlockSilverfishEnumType[values().length];
        Wj =
                new BlockSilverfishEnumType[] {
                    net.minecraft.block.BlockSilverfishEnumType.STONE,
                    net.minecraft.block.BlockSilverfishEnumType.COBBLESTONE,
                    net.minecraft.block.BlockSilverfishEnumType.STONEBRICK,
                    net.minecraft.block.BlockSilverfishEnumType.MOSSY_STONEBRICK,
                    net.minecraft.block.BlockSilverfishEnumType.CRACKED_STONEBRICK,
                    net.minecraft.block.BlockSilverfishEnumType.CHISELED_STONEBRICK
                };
        for (BlockSilverfishEnumType class_9c5ff0763031487a78a8 : values()) {
            net.minecraft.block.BlockSilverfishEnumType.META_LOOKUP[
                            class_9c5ff0763031487a78a8.getMetadata()] =
                    class_9c5ff0763031487a78a8;
        }
    }

    private static BlockSilverfishEnumType[] __recovery_values171() {
        return Wg.clone();
    }
}
