package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenWaterlily extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < 10) {
            int j = blockPos.getX() + random.nextInt(8) - random.nextInt(8);
            int k = blockPos.getY() + random.nextInt(4) - random.nextInt(4);
            int l = blockPos.getZ() + random.nextInt(8) - random.nextInt(8);
            if (world.isAirBlock(new BlockPos(j, k, l))
                    && Blocks.waterlily.canPlaceBlockAt(world, new BlockPos(j, k, l))) {
                world.setBlockState(new BlockPos(j, k, l), Blocks.waterlily.getDefaultState(), 2);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return true;
    }
}
