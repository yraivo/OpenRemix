package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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

public abstract class BlockWoodSlab extends BlockSlab {
    public static PropertyEnum VARIANT;
    private static String[] packedTextFragments;

    protected BlockStateContainer createBlockState() {
        return this.isDouble()
                ? new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockWoodSlab.VARIANT})
                : new BlockStateContainer(
                        this,
                        new IProperty[] {net.minecraft.block.BlockSlab.HALF_PROP, net.minecraft.block.BlockWoodSlab.VARIANT});
    }

    public String getFullSlabName(int i) {
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + BlockPlanksEnumType.byMetadata(i).getUnlocalizedName();
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockPlanksEnumType) blockState.getValue(net.minecraft.block.BlockWoodSlab.VARIANT))
                                .getMetadata();
        if (!this.isDouble()
                && blockState.getValue(net.minecraft.block.BlockSlab.HALF_PROP) == BlockSlabEnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    static {
        net.minecraft.block.BlockWoodSlab.packedTextFragments = new String[2];
        net.minecraft.block.BlockWoodSlab.packedTextFragments[0] = new String(".");
        net.minecraft.block.BlockWoodSlab.packedTextFragments[1] = new String("D\u0007<'\u001d\\5");
        net.minecraft.block.BlockWoodSlab.VARIANT = PropertyEnum.create("variant", BlockPlanksEnumType.class);
    }

    public BlockState getStateFromMeta(int i) {
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockWoodSlab.VARIANT, BlockPlanksEnumType.byMetadata(i & 7));
        if (!this.isDouble()) {
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockSlab.HALF_PROP,
                            (i & 8) == 0
                                    ? BlockSlabEnumBlockHalf.BOTTOM
                                    : BlockSlabEnumBlockHalf.TOP);
        }

        return blockState;
    }

    public IProperty getVariantProperty() {
        return net.minecraft.block.BlockWoodSlab.VARIANT;
    }

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        return ((BlockPlanksEnumType) blockState.getValue(net.minecraft.block.BlockWoodSlab.VARIANT))
                .getMetadata();
    }

    public BlockWoodSlab() {
        super(Material.wood);
        BlockState blockState = this.blockState.getBaseState();
        if (!this.isDouble()) {
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockSlab.HALF_PROP, BlockSlabEnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(
                blockState.withProperty(net.minecraft.block.BlockWoodSlab.VARIANT, BlockPlanksEnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (item != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
            for (BlockPlanksEnumType blockPlanksEnumType : BlockPlanksEnumType.values()) {
                list.add(new ItemStack(item, 1, blockPlanksEnumType.getMetadata()));
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public Object getVariant(ItemStack itemStack) {
        return BlockPlanksEnumType.byMetadata(itemStack.getItemDamage() & 7);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
