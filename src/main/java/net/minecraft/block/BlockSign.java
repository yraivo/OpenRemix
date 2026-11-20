package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSign extends BlockContainer {
    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.sign;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.sign;
    }

    public boolean isFullCube() {
        return false;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntitySign();
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return true;
    }

    protected BlockSign() {
        super(Material.wood);
        float f = 0.25F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (world.isRemote) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntitySign
                ? ((TileEntitySign) tileEntity).executeCommand(playerEntity)
                : false;
    }
}
