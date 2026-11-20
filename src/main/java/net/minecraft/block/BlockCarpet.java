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
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCarpet extends Block {

    public static PropertyEnum COLOR;

    protected BlockCarpet() {
        super(Material.carpet);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockCarpet.COLOR, EnumDyeColor.WHITE));
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsFromMeta(0);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockCarpet.COLOR, EnumDyeColor.byMetadata(i));
    }

    public int getMetaFromState(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockCarpet.COLOR)).getMetadata();
    }

    private boolean checkAndDropBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.canBlockStay(world, blockPos)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
            return false;
        } else {
            return true;
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBlockBoundsFromMeta(0);
    }

    public int damageDropped(BlockState blockState) {
        return ((EnumDyeColor) blockState.getValue(net.minecraft.block.BlockCarpet.COLOR)).getMetadata();
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return facing == Facing.UP
                ? true
                : super.shouldSideBeRendered(iBlockAccess, blockPos, facing);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockCarpet.COLOR});
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = 0;
        int i = k;

        while (j < 16) {
            list.add(new ItemStack(item, 1, j));
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    private boolean canBlockStay(World world, BlockPos blockPos) {
        return !world.isAirBlock(blockPos.offsetDown());
    }

    protected void setBlockBoundsFromMeta(int i) {
        byte b0 = 0;
        float f = 1 * (1 + b0) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    static {
        net.minecraft.block.BlockCarpet.COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos);
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsFromMeta(0);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean isFullCube() {
        return false;
    }
}
