package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSourceImpl implements IBlockSource {
    private BlockPos pos;
    private World worldObj;

    public Block getBlock() {
        return this.worldObj.getBlockState(this.pos).getBlock();
    }

    public World getWorld() {
        return this.worldObj;
    }

    public BlockSourceImpl(World world, BlockPos blockPos) {
        this.worldObj = world;
        this.pos = blockPos;
    }

    public double getY() {
        return this.pos.getY() + 0.5;
    }

    public int getBlockMetadata() {
        BlockState blockState = this.worldObj.getBlockState(this.pos);
        return blockState.getBlock().getMetaFromState(blockState);
    }

    public TileEntity getBlockTileEntity() {
        return this.worldObj.getTileEntity(this.pos);
    }

    public double getX() {
        return this.pos.getX() + 0.5;
    }

    public double getZ() {
        return this.pos.getZ() + 0.5;
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }
}
