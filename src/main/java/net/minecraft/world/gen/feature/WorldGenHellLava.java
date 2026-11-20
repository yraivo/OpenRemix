package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator {
    private boolean d;
    private Block aUV;

    public WorldGenHellLava(Block aUVValue, boolean flag) {
        this.aUV = aUVValue;
        this.d = flag;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        if (world.getBlockState(blockPos.offsetUp()).getBlock() != Blocks.netherrack) {
            return false;
        }

        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.AIR
                && world.getBlockState(blockPos).getBlock() != Blocks.netherrack) {
            return false;
        }

        int i = 0;
        if (world.getBlockState(blockPos.offsetWest()).getBlock() == Blocks.netherrack) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetEast()).getBlock() == Blocks.netherrack) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetNorth()).getBlock() == Blocks.netherrack) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetSouth()).getBlock() == Blocks.netherrack) {
            i++;
        }

        if (world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.netherrack) {
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

        if (world.isAirBlock(blockPos.offsetDown())) {
            j++;
        }

        if (!this.d && i == 4 && j == 1 || i == 5) {
            world.setBlockState(blockPos, this.aUV.getDefaultState(), 2);
            world.forceBlockUpdateTick(this.aUV, blockPos, random);
        }

        return true;
    }
}
