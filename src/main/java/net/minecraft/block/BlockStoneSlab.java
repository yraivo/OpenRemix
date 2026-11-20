package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockStoneSlab extends BlockSlab {
    public static PropertyBool SEAMLESS;
    private static String[] packedTextFragments;
    public static PropertyEnum VARIANT;

    public String getFullSlabName(int i) {
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + BlockStoneSlabEnumType.byMetadata(i).getUnlocalizedName();
    }

    public Object getVariant(ItemStack itemStack) {
        return BlockStoneSlabEnumType.byMetadata(itemStack.getItemDamage() & 7);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }

    protected BlockStateContainer createBlockState() {
        return this.isDouble()
                ? new BlockStateContainer(
                        this,
                        new IProperty[] {
                            net.minecraft.block.BlockStoneSlab.SEAMLESS, net.minecraft.block.BlockStoneSlab.VARIANT
                        })
                : new BlockStateContainer(
                        this,
                        new IProperty[] {net.minecraft.block.BlockSlab.HALF_PROP, net.minecraft.block.BlockStoneSlab.VARIANT});
    }

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        return ((BlockStoneSlabEnumType) blockState.getValue(net.minecraft.block.BlockStoneSlab.VARIANT))
                .getMetadata();
    }

    // region String decoding support

    // endregion

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }

    public BlockStoneSlab() {
        super(Material.rock);

        BlockState blockState;
        {
            int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
            blockState = this.blockState.getBaseState();
            int i = j;
            if (this.isDouble()) {
                blockState = blockState.withProperty(net.minecraft.block.BlockStoneSlab.SEAMLESS, false);
                if (!(i != 0)) {

                    blockState =
                            blockState.withProperty(
                                    net.minecraft.block.BlockSlab.HALF_PROP, BlockSlabEnumBlockHalf.BOTTOM);
                }
            } else {

                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockSlab.HALF_PROP, BlockSlabEnumBlockHalf.BOTTOM);
            }
        }

        this.setDefaultState(
                blockState.withProperty(
                        net.minecraft.block.BlockStoneSlab.VARIANT, BlockStoneSlabEnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab)) {
            for (BlockStoneSlabEnumType blockStoneSlabEnumType : BlockStoneSlabEnumType.values()) {
                if (blockStoneSlabEnumType != BlockStoneSlabEnumType.WOOD) {
                    list.add(new ItemStack(item, 1, blockStoneSlabEnumType.getMetadata()));
                }

                if (i != 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        byte b0 = 0;
        int i = k;
        int j =
                b0
                        | ((BlockStoneSlabEnumType)
                                        blockState.getValue(net.minecraft.block.BlockStoneSlab.VARIANT))
                                .getMetadata();
        if (this.isDouble()) {
            if (!(Boolean) blockState.getValue(net.minecraft.block.BlockStoneSlab.SEAMLESS)) {
                return j;
            }

            j |= 8;
            if (i != 0) {
                return j;
            }
        }

        if (blockState.getValue(net.minecraft.block.BlockSlab.HALF_PROP) == BlockSlabEnumBlockHalf.TOP) {
            j |= 8;
        }

        return j;
    }

    public BlockState getStateFromMeta(int i) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockStoneSlab.VARIANT,
                                BlockStoneSlabEnumType.byMetadata(i & 7));
        int j = k;
        if (this.isDouble()) {
            blockState = blockState.withProperty(net.minecraft.block.BlockStoneSlab.SEAMLESS, (i & 8) != 0);
            if (j != 0) {
                return blockState;
            }
        }

        return blockState.withProperty(
                net.minecraft.block.BlockSlab.HALF_PROP,
                (i & 8) == 0 ? BlockSlabEnumBlockHalf.BOTTOM : BlockSlabEnumBlockHalf.TOP);
    }

    static {
        net.minecraft.block.BlockStoneSlab.packedTextFragments = new String[2];
        net.minecraft.block.BlockStoneSlab.packedTextFragments[0] = new String(".");
        net.minecraft.block.BlockStoneSlab.packedTextFragments[1] =
                new String("/\u0011\u0003T^l$\b*\u0015\u0010PSg#*");
        net.minecraft.block.BlockStoneSlab.SEAMLESS = PropertyBool.create("seamless");
        net.minecraft.block.BlockStoneSlab.VARIANT = PropertyEnum.create("variant", BlockStoneSlabEnumType.class);
    }

    public IProperty getVariantProperty() {
        return net.minecraft.block.BlockStoneSlab.VARIANT;
    }
}
