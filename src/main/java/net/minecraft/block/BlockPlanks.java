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

public class BlockPlanks extends Block {

    public static PropertyEnum VARIANT_PROP;

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockPlanks.VARIANT_PROP});
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockPlanksEnumType) blockState.getValue(net.minecraft.block.BlockPlanks.VARIANT_PROP))
                .getMetadata();
    }

    // region String decoding support

    // endregion

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockPlanksEnumType[] blockPlanksEnumTypeArray = BlockPlanksEnumType.values();
        int j = blockPlanksEnumTypeArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            BlockPlanksEnumType blockPlanksEnumType = blockPlanksEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockPlanksEnumType.getMetadata()));
            k++;
            if (i != 0) {
                break;
            }
        }
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockPlanks.VARIANT_PROP, BlockPlanksEnumType.byMetadata(i));
    }

    public BlockPlanks() {
        super(Material.wood);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPlanks.VARIANT_PROP, BlockPlanksEnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    static {
        net.minecraft.block.BlockPlanks.VARIANT_PROP = PropertyEnum.create("variant", BlockPlanksEnumType.class);
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockPlanksEnumType) blockState.getValue(net.minecraft.block.BlockPlanks.VARIANT_PROP))
                .getMetadata();
    }
}
