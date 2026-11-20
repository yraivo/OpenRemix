package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator {
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
            if (world.isAirBlock(blockPosValue)
                    && world.getBlockState(blockPosValue.offsetDown()).getBlock() == Blocks.grass
                    && Blocks.pumpkin.canPlaceBlockAt(world, blockPosValue)) {
                world.setBlockState(
                        blockPosValue,
                        Blocks.pumpkin
                                .getDefaultState()
                                .withProperty(
                                        BlockPumpkin.AGE, FacingPlane.HORIZONTAL.random(random)),
                        2);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return true;
    }
}
