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

public class BlockPrismarine extends Block {
    public static int BRICKSMETA;
    public static int ROUGHMETA;
    public static PropertyEnum VARIANTS;

    public static int DARKMETA;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public BlockPrismarine() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockPrismarine.VARIANTS, BlockPrismarineEnumType.ROUGH));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    static {
        net.minecraft.block.BlockPrismarine.VARIANTS =
                PropertyEnum.create("variant", BlockPrismarineEnumType.class);
        net.minecraft.block.BlockPrismarine.ROUGHMETA = BlockPrismarineEnumType.ROUGH.getMetadata();
        net.minecraft.block.BlockPrismarine.BRICKSMETA = BlockPrismarineEnumType.BRICKS.getMetadata();
        net.minecraft.block.BlockPrismarine.DARKMETA = BlockPrismarineEnumType.DARK.getMetadata();
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockPrismarineEnumType) blockState.getValue(net.minecraft.block.BlockPrismarine.VARIANTS))
                .getMetadata();
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, net.minecraft.block.BlockPrismarine.ROUGHMETA));
        list.add(new ItemStack(item, 1, net.minecraft.block.BlockPrismarine.BRICKSMETA));
        list.add(new ItemStack(item, 1, net.minecraft.block.BlockPrismarine.DARKMETA));
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockPrismarine.VARIANTS, BlockPrismarineEnumType.byMetadata(i));
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockPrismarineEnumType) blockState.getValue(net.minecraft.block.BlockPrismarine.VARIANTS))
                .getMetadata();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockPrismarine.VARIANTS});
    }
}
