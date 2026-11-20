package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockDirt extends Block {
    public static PropertyBool SNOWY;

    public static PropertyEnum VARIANT;

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(this, 1, BlockDirtDirtType.DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, BlockDirtDirtType.COARSE_DIRT.getMetadata()));
        list.add(new ItemStack(this, 1, BlockDirtDirtType.PODZOL.getMetadata()));
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockDirtDirtType) blockState.getValue(net.minecraft.block.BlockDirt.VARIANT)).getMetadata();
    }

    // region String decoding support

    // endregion

    public int getDamageValue(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock() != this
                ? 0
                : ((BlockDirtDirtType) blockState.getValue(net.minecraft.block.BlockDirt.VARIANT)).getMetadata();
    }

    public int damageDropped(BlockState blockState) {
        BlockDirtDirtType value = (BlockDirtDirtType) blockState.getValue(net.minecraft.block.BlockDirt.VARIANT);
        if (value == BlockDirtDirtType.PODZOL) {
            value = BlockDirtDirtType.DIRT;
        }

        return value.getMetadata();
    }

    protected BlockDirt() {
        super(Material.ground);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDirt.VARIANT, BlockDirtDirtType.DIRT)
                        .withProperty(net.minecraft.block.BlockDirt.SNOWY, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirt.VARIANT, BlockDirtDirtType.byMetadata(i));
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockDirt.VARIANT, net.minecraft.block.BlockDirt.SNOWY});
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        if (blockState.getValue(net.minecraft.block.BlockDirt.VARIANT) == BlockDirtDirtType.PODZOL) {
            Block block = iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock();
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockDirt.SNOWY,
                            block == Blocks.snow || block == Blocks.snow_layer);
        }

        return blockState;
    }

    static {
        net.minecraft.block.BlockDirt.VARIANT = PropertyEnum.create("variant", BlockDirtDirtType.class);
        net.minecraft.block.BlockDirt.SNOWY = PropertyBool.create("snowy");
    }
}
