package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockSand extends BlockFalling {
    public static PropertyEnum VARIANT_PROP;

    public MapColor getMapColor(BlockState blockState) {
        return ((BlockSandEnumType) blockState.getValue(net.minecraft.block.BlockSand.VARIANT_PROP))
                .getMapColor();
    }

    public BlockSand() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockSand.VARIANT_PROP, BlockSandEnumType.SAND));
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockSandEnumType[] blockSandEnumTypeArray = BlockSandEnumType.values();
        int j = blockSandEnumTypeArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            BlockSandEnumType blockSandEnumType = blockSandEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockSandEnumType.getMetadata()));
            k++;
            if (i != 0) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockSand.VARIANT_PROP, BlockSandEnumType.byMetadata(i));
    }

    static {
        net.minecraft.block.BlockSand.VARIANT_PROP = PropertyEnum.create("variant", BlockSandEnumType.class);
    }

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        return ((BlockSandEnumType) blockState.getValue(net.minecraft.block.BlockSand.VARIANT_PROP))
                .getMetadata();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockSand.VARIANT_PROP});
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockSandEnumType) blockState.getValue(net.minecraft.block.BlockSand.VARIANT_PROP))
                .getMetadata();
    }
}
