package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockColored extends Block {
    public static PropertyEnum COLOR;

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockColored.COLOR, EnumDyeColor.byMetadata(i));
    }

    static {
        net.minecraft.block.BlockColored.COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        EnumDyeColor[] enumDyeColorArray = EnumDyeColor.values();
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = enumDyeColorArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            EnumDyeColor enumDyeColor = enumDyeColorArray[k];
            list.add(new ItemStack(item, 1, enumDyeColor.getMetadata()));
            k++;
            if (i == 0) {
                break;
            }
        }
    }

    public int damageDropped(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockColored.COLOR)).getMetadata();
    }

    // region String decoding support

    // endregion

    public BlockColored(Material material) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockColored.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockColored.COLOR});
    }

    public int getMetaFromState(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockColored.COLOR)).getMetadata();
    }

    public MapColor getMapColor(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockColored.COLOR)).getMapColor();
    }
}
