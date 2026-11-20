package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBush extends Block {
    protected BlockBush() {
        this(Material.plants);
    }

    protected void checkAndDropBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
        }
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    protected BlockBush(Material material) {
        super(material);
        this.setTickRandomly(true);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        this.checkAndDropBlock(world, blockPos, blockState);
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos)
                && this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }
}
