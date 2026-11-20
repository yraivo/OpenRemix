package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenAbstractTree extends WorldGenerator {
    protected boolean func_150523_a(Block block) {
        return block.getMaterial() == Material.AIR
                || block.getMaterial() == Material.leaves
                || block == Blocks.grass
                || block == Blocks.dirt
                || block == Blocks.log
                || block == Blocks.log2
                || block == Blocks.sapling
                || block == Blocks.vine;
    }

    protected void func_175921_a(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() != Blocks.dirt) {
            this.setBlockAndNotifyAdequately(world, blockPos, Blocks.dirt.getDefaultState());
        }
    }

    public WorldGenAbstractTree(boolean flag) {
        super(flag);
    }

    public void generatePodzol(World world, Random random, BlockPos blockPos) {}
}
