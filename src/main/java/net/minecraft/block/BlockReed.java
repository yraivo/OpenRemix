package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockReed extends Block {

    public static PropertyInteger AGE;

    public Item getItem(World world, BlockPos blockPos) {
        return Items.reeds;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockReed.AGE);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.checkForDrop(world, blockPos, blockState);
    }

    // region String decoding support

    // endregion

    public boolean isFullCube() {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, net.minecraft.block.BlockReed.AGE);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
        if (block == this) {
            return true;
        }

        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
            return false;
        }

        for (java.util.Iterator<?> __recoveryIterator171_2779 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_2779).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_2779).next();
            if (world.getBlockState(blockPos.offset(facing).offsetDown()).getBlock().getMaterial()
                    == Material.water) {
                return true;
            }
        }

        return false;
    }

    protected final boolean checkForDrop(World world, BlockPos blockPos, BlockState blockState) {
        if (this.canBlockStay(world, blockPos)) {
            return true;
        }

        this.dropBlockAsItem(world, blockPos, blockState, 0);
        world.setBlockToAir(blockPos);
        return false;
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return iBlockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
    }

    protected BlockReed() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty(net.minecraft.block.BlockReed.AGE, 0));
        float f = 0.375F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.setTickRandomly(true);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if ((world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.reeds
                        || this.checkForDrop(world, blockPos, blockState))
                && world.isAirBlock(blockPos.offsetUp())) {
            int j = 1;

            while (world.getBlockState(blockPos.offsetDown(j)).getBlock() == this) {
                j++;
                if (i == 0) {
                    break;
                }
            }

            if (j < 3) {
                int k = (Integer) blockState.getValue(net.minecraft.block.BlockReed.AGE);
                if (k == 15) {
                    world.setBlockState(blockPos.offsetUp(), this.getDefaultState());
                    world.setBlockState(
                            blockPos, blockState.withProperty(net.minecraft.block.BlockReed.AGE, 0), 4);
                    if (i != 0) {
                        return;
                    }
                }

                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockReed.AGE, k + 1), 4);
            }
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean canBlockStay(World world, BlockPos blockPos) {
        return this.canPlaceBlockAt(world, blockPos);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    static {
        net.minecraft.block.BlockReed.AGE = PropertyInteger.create("age", 0, 15);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockReed.AGE, i);
    }

    // region String decoding support

    // endregion

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.reeds;
    }
}
