package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator {
    private Block block;

    public WorldGenLiquids(Block aUVValue) {
        this.block = aUVValue;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        if (world.getBlockState(blockPos.offsetUp()).getBlock() != Blocks.stone) {
            return false;
        }

        if (world.getBlockState(blockPos.offsetDown()).getBlock() != Blocks.stone) {
            return false;
        }

        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.AIR
                && world.getBlockState(blockPos).getBlock() != Blocks.stone) {
            return false;
        }

        int i = 0;
        if (world.getBlockState(blockPos.offsetWest()).getBlock() == Blocks.stone) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetEast()).getBlock() == Blocks.stone) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetNorth()).getBlock() == Blocks.stone) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetSouth()).getBlock() == Blocks.stone) {
            i++;
        }

        int j = 0;
        if (world.isAirBlock(blockPos.offsetWest())) {
            j++;
        }

        if (world.isAirBlock(blockPos.offsetEast())) {
            j++;
        }

        if (world.isAirBlock(blockPos.offsetNorth())) {
            j++;
        }

        if (world.isAirBlock(blockPos.offsetSouth())) {
            j++;
        }

        if (i == 3 && j == 1) {
            world.setBlockState(blockPos, this.block.getDefaultState(), 2);
            world.forceBlockUpdateTick(this.block, blockPos, random);
        }

        return true;
    }
}
