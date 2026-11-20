package net.minecraft.dispenser;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public interface IBlockSource extends ILocatableSource {
    Block getBlock();

    double getY();

    TileEntity getBlockTileEntity();

    BlockPos getBlockPos();

    int getBlockMetadata();

    double getZ();

    double getX();
}
