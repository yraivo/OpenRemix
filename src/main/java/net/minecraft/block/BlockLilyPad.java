package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockLilyPad extends BlockBush {
    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            BlockState blockStateValue = world.getBlockState(blockPos.offsetDown());
            return blockStateValue.getBlock().getMaterial() == Material.water
                    && (Integer) blockStateValue.getValue(BlockLiquid.LEVEL) == 0;
        } else {
            return false;
        }
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        if (entity == null || !(entity instanceof net.minecraft.entity.item.EntityBoat)) {
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        }
    }

    public int getRenderColor(BlockState blockState) {
        return 7455580;
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.water;
    }

    public int getBlockColor() {
        return 7455580;
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return 2129968;
    }

    public int getMetaFromState(BlockState blockState) {
        return 0;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return new BoundingBox(
                blockPos.getX() + this.minX,
                blockPos.getY() + this.minY,
                blockPos.getZ() + this.minZ,
                blockPos.getX() + this.maxX,
                blockPos.getY() + this.maxY,
                blockPos.getZ() + this.maxZ);
    }

    protected BlockLilyPad() {
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
