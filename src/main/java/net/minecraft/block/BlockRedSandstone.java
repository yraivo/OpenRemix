package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockRedSandstone extends Block {

    public static PropertyEnum TYPE;

    public int getMetaFromState(BlockState blockState) {
        return ((BlockRedSandstoneEnumType) blockState.getValue(net.minecraft.block.BlockRedSandstone.TYPE))
                .getMetaFromState();
    }

    public BlockRedSandstone() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockRedSandstone.TYPE, BlockRedSandstoneEnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockRedSandstone.TYPE, BlockRedSandstoneEnumType.byMetadata(i));
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockRedSandstoneEnumType) blockState.getValue(net.minecraft.block.BlockRedSandstone.TYPE))
                .getMetaFromState();
    }

    static {
        net.minecraft.block.BlockRedSandstone.TYPE = PropertyEnum.create("type", BlockRedSandstoneEnumType.class);
    }

    // region String decoding support

    // endregion

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockRedSandstoneEnumType[] blockRedSandstoneEnumTypeArray =
                BlockRedSandstoneEnumType.values();
        int j = blockRedSandstoneEnumTypeArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            BlockRedSandstoneEnumType blockRedSandstoneEnumType = blockRedSandstoneEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockRedSandstoneEnumType.getMetaFromState()));
            k++;
            if (i != 0) {
                break;
            }
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, net.minecraft.block.BlockRedSandstone.TYPE);
    }
}
