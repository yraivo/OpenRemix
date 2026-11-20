package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMelon extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < 64) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(8) - random.nextInt(8));
            if (Blocks.melon_block.canPlaceBlockAt(world, blockPosValue)
                    && world.getBlockState(blockPosValue.offsetDown()).getBlock() == Blocks.grass) {
                world.setBlockState(blockPosValue, Blocks.melon_block.getDefaultState(), 2);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return true;
    }
}
