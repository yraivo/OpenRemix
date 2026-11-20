package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.AIR
                        || block.getMaterial() == Material.leaves)
                && blockPos.getY() > 0) {
            blockPos = blockPos.offsetDown();
            if (text == null) {
                break;
            }
        }

        int i = 0;

        while (i < 4) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(blockPosValue)
                    && Blocks.deadbush.canBlockStay(
                            world, blockPosValue, Blocks.deadbush.getDefaultState())) {
                world.setBlockState(blockPosValue, Blocks.deadbush.getDefaultState(), 2);
            }

            i++;
            if (text == null) {
                break;
            }
        }

        return true;
    }
}
