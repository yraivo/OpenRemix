package net.minecraft.world;

import net.minecraft.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBlockAccess {
    TileEntity getTileEntity(BlockPos blockPos);

    boolean isAirBlock(BlockPos blockPos);

    boolean extendedLevelsInChunkCache();

    BlockState getBlockState(BlockPos blockPos);

    int getCombinedLight(BlockPos blockPos, int i);

    WorldType getWorldType();

    BiomeGenBase getBiomeGenForCoords(BlockPos blockPos);

    int getStrongPower(BlockPos blockPos, Facing facing);
}
