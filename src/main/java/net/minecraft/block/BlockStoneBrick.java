package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockStoneBrick extends Block {
    public static int CHISELED_META;
    public static int DEFAULT_META;
    public static PropertyEnum VARIANT_PROP;
    public static int CRACKED_META;
    public static int MOSSY_META;

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockStoneBrick.VARIANT_PROP,
                        BlockStoneBrickEnumType.getStateFromMeta(i));
    }

    static {
        net.minecraft.block.BlockStoneBrick.VARIANT_PROP =
                PropertyEnum.create("variant", BlockStoneBrickEnumType.class);
        net.minecraft.block.BlockStoneBrick.DEFAULT_META = BlockStoneBrickEnumType.DEFAULT.getMetaFromState();
        net.minecraft.block.BlockStoneBrick.MOSSY_META = BlockStoneBrickEnumType.MOSSY.getMetaFromState();
        net.minecraft.block.BlockStoneBrick.CRACKED_META = BlockStoneBrickEnumType.CRACKED.getMetaFromState();
        net.minecraft.block.BlockStoneBrick.CHISELED_META = BlockStoneBrickEnumType.CHISELED.getMetaFromState();
    }

    // region String decoding support

    // endregion

    public BlockStoneBrick() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockStoneBrick.VARIANT_PROP,
                                BlockStoneBrickEnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockStoneBrickEnumType) blockState.getValue(net.minecraft.block.BlockStoneBrick.VARIANT_PROP))
                .getMetaFromState();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockStoneBrick.VARIANT_PROP});
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockStoneBrickEnumType) blockState.getValue(net.minecraft.block.BlockStoneBrick.VARIANT_PROP))
                .getMetaFromState();
    }

    // region String decoding support

    // endregion

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockStoneBrickEnumType[] blockStoneBrickEnumTypeArray = BlockStoneBrickEnumType.values();
        int j = blockStoneBrickEnumTypeArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            BlockStoneBrickEnumType blockStoneBrickEnumType = blockStoneBrickEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockStoneBrickEnumType.getMetaFromState()));
            k++;
            if (i == 0) {
                break;
            }
        }
    }
}
