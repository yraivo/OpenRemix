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

import java.util.List;
import java.util.Random;

public class BlockStone extends Block {

    public static PropertyEnum VARIANT_PROP;

    public int damageDropped(BlockState blockState) {
        return ((BlockStoneEnumType) blockState.getValue(net.minecraft.block.BlockStone.VARIANT_PROP))
                .getMetaFromState();
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockStone.VARIANT_PROP});
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        BlockStoneEnumType[] blockStoneEnumTypeArray = BlockStoneEnumType.values();
        int j = blockStoneEnumTypeArray.length;
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int k = 0;
        int i = l;

        while (k < j) {
            BlockStoneEnumType blockStoneEnumType = blockStoneEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockStoneEnumType.getMetaFromState()));
            k++;
            if (i != 0) {
                break;
            }
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockStoneEnumType) blockState.getValue(net.minecraft.block.BlockStone.VARIANT_PROP))
                .getMetaFromState();
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockStone.VARIANT_PROP, BlockStoneEnumType.getStateFromMeta(i));
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return blockState.getValue(net.minecraft.block.BlockStone.VARIANT_PROP) == BlockStoneEnumType.STONE
                ? Item.getItemFromBlock(Blocks.cobblestone)
                : Item.getItemFromBlock(Blocks.stone);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockStone.VARIANT_PROP = PropertyEnum.create("variant", BlockStoneEnumType.class);
    }

    public BlockStone() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockStone.VARIANT_PROP, BlockStoneEnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
