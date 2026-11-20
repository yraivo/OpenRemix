package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IGrowable {
    boolean isStillGrowing(World world, BlockPos blockPos, BlockState blockState, boolean flag);

    void grow(World world, Random random, BlockPos blockPos, BlockState blockState);

    boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState);
}
