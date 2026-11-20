package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockEndPortal extends BlockContainer {
    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return facing == Facing.DOWN
                ? super.shouldSideBeRendered(iBlockAccess, blockPos, facing)
                : false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityEndPortal();
    }

    protected BlockEndPortal(Material material) {
        super(material);
        this.setLightLevel(1.0F);
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        double d0 = blockPos.getX() + random.nextFloat();
        double d1 = blockPos.getY() + 0.8F;
        double d2 = blockPos.getZ() + random.nextFloat();
        double d3 = 0.0;
        double d4 = 0.0;
        double d5 = 0.0;
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return null;
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (entity.ridingEntity == null && entity.riddenByEntity == null && !world.isRemote) {
            entity.travelToDimension(1);
        }
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {}

    public int quantityDropped(Random random) {
        return 0;
    }

    public MapColor getMapColor(BlockState blockState) {
        return MapColor.obsidianColor;
    }

    public boolean isFullCube() {
        return false;
    }
}
