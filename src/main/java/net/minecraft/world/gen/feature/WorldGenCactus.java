package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenCactus extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < 10) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(8) - random.nextInt(8),
                            random.nextInt(4) - random.nextInt(4),
                            random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(blockPosValue)) {
                int j = 1 + random.nextInt(random.nextInt(3) + 1);
                int k = 0;

                while (k < j) {
                    if (Blocks.cactus.canBlockStay(world, blockPosValue)) {
                        world.setBlockState(
                                blockPosValue.offsetUp(k), Blocks.cactus.getDefaultState(), 2);
                    }

                    k++;
                    if (textValue == null) {
                        break;
                    }
                }
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return true;
    }
}
