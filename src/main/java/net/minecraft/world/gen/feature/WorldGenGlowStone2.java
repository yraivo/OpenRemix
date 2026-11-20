package net.minecraft.world.gen.feature;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenGlowStone2 extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        if (!world.isAirBlock(blockPos)) {
            return false;
        }

        if (world.getBlockState(blockPos.offsetUp()).getBlock() != Blocks.netherrack) {
            return false;
        }

        world.setBlockState(blockPos, Blocks.glowstone.getDefaultState(), 2);
        int i = 0;

        while (i < 1500) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(12),
                            random.nextInt(8) - random.nextInt(8));
            if (world.getBlockState(blockPosValue).getBlock().getMaterial() == Material.AIR) {
                int j = 0;

                for (Facing facing : Facing.values()) {
                    if (world.getBlockState(blockPosValue.offset(facing)).getBlock()
                            == Blocks.glowstone) {
                        j++;
                    }

                    if (j > 1 && text != null || text == null) {
                        break;
                    }
                }

                if (j == 1) {
                    world.setBlockState(blockPosValue, Blocks.glowstone.getDefaultState(), 2);
                }
            }

            i++;
            if (text == null) {
                break;
            }
        }

        return true;
    }
}
