package net.minecraft.block;

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

public class BlockStainedGlassPane extends BlockPane {

    public static PropertyEnum COLOR;

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = 0;
        int i = k;

        while (j < EnumDyeColor.values().length) {
            list.add(new ItemStack(item, 1, j));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockStainedGlassPane.COLOR, EnumDyeColor.byMetadata(i));
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockPane.NORTH,
                    net.minecraft.block.BlockPane.EAST,
                    net.minecraft.block.BlockPane.WEST,
                    net.minecraft.block.BlockPane.SOUTH,
                    net.minecraft.block.BlockStainedGlassPane.COLOR
                });
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockStainedGlassPane.COLOR))
                .getMetadata();
    }

    // region String decoding support

    // endregion

    public BlockStainedGlassPane() {
        super(Material.glass, false);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPane.NORTH, false)
                        .withProperty(net.minecraft.block.BlockPane.EAST, false)
                        .withProperty(net.minecraft.block.BlockPane.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockPane.WEST, false)
                        .withProperty(net.minecraft.block.BlockStainedGlassPane.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    public int damageDropped(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockStainedGlassPane.COLOR))
                .getMetadata();
    }

    static {
        net.minecraft.block.BlockStainedGlassPane.COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }

    // region String decoding support

    // endregion
}
