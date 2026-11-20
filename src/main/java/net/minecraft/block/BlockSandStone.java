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

public class BlockSandStone extends Block {

    public static PropertyEnum TYPE;

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockSandStone.TYPE = PropertyEnum.create("type", BlockSandStoneEnumType.class);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockSandStoneEnumType) blockState.getValue(net.minecraft.block.BlockSandStone.TYPE))
                .getMetadata();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockSandStone.TYPE});
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockSandStoneEnumType) blockState.getValue(net.minecraft.block.BlockSandStone.TYPE))
                .getMetadata();
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockSandStoneEnumType[] blockSandStoneEnumTypeArray = BlockSandStoneEnumType.values();
        int i = j;

        for (BlockSandStoneEnumType blockSandStoneEnumType : blockSandStoneEnumTypeArray) {
            list.add(new ItemStack(item, 1, blockSandStoneEnumType.getMetadata()));
            if (i != 0) {
                break;
            }
        }
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockSandStone.TYPE, BlockSandStoneEnumType.byMetadata(i));
    }

    public BlockSandStone() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockSandStone.TYPE, BlockSandStoneEnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    // region String decoding support

    // endregion
}
