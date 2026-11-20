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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockStainedGlass extends BlockBreakable {

    public static PropertyEnum COLOR;

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }

    public boolean isFullCube() {
        return false;
    }

    protected boolean canSilkHarvest() {
        return true;
    }

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockStainedGlass.COLOR)).getMetadata();
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        EnumDyeColor[] enumDyeColorArray = EnumDyeColor.values();
        int i = j;

        for (EnumDyeColor enumDyeColor : enumDyeColorArray) {
            list.add(new ItemStack(item, 1, enumDyeColor.getMetadata()));
            if (i == 0) {
                break;
            }
        }
    }

    static {
        net.minecraft.block.BlockStainedGlass.COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockStainedGlass.COLOR)).getMetadata();
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockStainedGlass.COLOR, EnumDyeColor.byMetadata(i));
    }

    // region String decoding support

    // endregion

    public MapColor getMapColor(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockStainedGlass.COLOR)).getMapColor();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockStainedGlass.COLOR});
    }

    public BlockStainedGlass(Material material) {
        super(material, false);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockStainedGlass.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
}
