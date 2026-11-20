package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block {

    protected BoundingBox getSensitiveAABB(BlockPos blockPos) {

        return new BoundingBox(
                blockPos.getX() + 0.125F,
                blockPos.getY(),
                blockPos.getZ() + 0.125F,
                blockPos.getX() + 1 - 0.125F,
                blockPos.getY() + 0.25,
                blockPos.getZ() + 1 - 0.125F);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (this.getRedstoneStrength(blockState) > 0) {
            this.updateNeighbors(world, blockPos);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    protected BlockBasePressurePlate(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
    }

    protected abstract BlockState setRedstoneStrength(BlockState blockState, int i);

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return this.canBePlacedOn(world, blockPos.offsetDown());
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public int getMobilityFlag() {
        return 1;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!this.canBePlacedOn(world, blockPos.offsetDown())) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }
    }

    protected void setBlockBoundsBasedOnState0(BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = this.getRedstoneStrength(blockState) > 0 ? 1 : 0;

        if (j != 0) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return facing == Facing.UP ? this.getRedstoneStrength(blockState) : 0;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote) {
            int redstoneStrength = this.getRedstoneStrength(blockState);
            if (redstoneStrength > 0) {
                this.updateState(world, blockPos, blockState, redstoneStrength);
            }
        }
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return this.getRedstoneStrength(blockState);
    }

    public void setBlockBoundsForItemRender() {

        this.setBlockBounds(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
    }

    public boolean isFullCube() {
        return false;
    }

    protected abstract int computeRedstoneStrength(World world, BlockPos blockPos);

    public int tickRate(World world) {
        return 20;
    }

    public boolean canProvidePower() {
        return true;
    }

    private boolean canBePlacedOn(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos)
                || world.getBlockState(blockPos).getBlock() instanceof BlockFence;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return true;
    }

    protected void updateNeighbors(World world, BlockPos blockPos) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState0(iBlockAccess.getBlockState(blockPos));
    }

    protected abstract int getRedstoneStrength(BlockState blockState);

    protected void updateState(
            World world, BlockPos blockPos, BlockState blockState, int i) {
        int j1 = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int k = this.computeRedstoneStrength(world, blockPos);
        int j = j1;
        int l = i > 0 ? 1 : 0;
        int i1 = k > 0 ? 1 : 0;
        if (i != k) {
            blockState = this.setRedstoneStrength(blockState, k);
            world.setBlockState(blockPos, blockState, 2);
            this.updateNeighbors(world, blockPos);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }

        {
            if (i1 == 0 && l != 0) {
                world.playSoundEffect(
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 0.1,
                        blockPos.getZ() + 0.5,
                        "random.click",
                        0.3F,
                        0.5F);
                if (!(j != 0)) {

                    if (i1 != 0 && l == 0) {
                        world.playSoundEffect(
                                blockPos.getX() + 0.5,
                                blockPos.getY() + 0.1,
                                blockPos.getZ() + 0.5,
                                "random.click",
                                0.3F,
                                0.6F);
                    }
                }
            } else {

                if (i1 != 0 && l == 0) {
                    world.playSoundEffect(
                            blockPos.getX() + 0.5,
                            blockPos.getY() + 0.1,
                            blockPos.getZ() + 0.5,
                            "random.click",
                            0.3F,
                            0.6F);
                }
            }
        }

        if (i1 != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }


    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!world.isRemote) {
            int redstoneStrength = this.getRedstoneStrength(blockState);
            if (redstoneStrength == 0) {
                this.updateState(world, blockPos, blockState, redstoneStrength);
            }
        }
    }
}
