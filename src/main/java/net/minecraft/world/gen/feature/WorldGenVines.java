package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenVines extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        while (blockPos.getY() < 128) {
            {
                if (world.isAirBlock(blockPos)) {
                    for (Facing facing : FacingPlane.HORIZONTAL.facings()) {
                        if (Blocks.vine.canPlaceBlockOnSide(world, blockPos, facing)) {
                            BlockState blockState =
                                    Blocks.vine
                                            .getDefaultState()
                                            .withProperty(BlockVine.NORTH, facing == Facing.NORTH)
                                            .withProperty(BlockVine.EAST, facing == Facing.EAST)
                                            .withProperty(BlockVine.SOUTH, facing == Facing.SOUTH)
                                            .withProperty(BlockVine.WEST, facing == Facing.WEST);
                            world.setBlockState(blockPos, blockState, 2);
                            if (text != null) {
                                break;
                            }
                        }

                        if (text == null) {
                            break;
                        }
                    }

                    if (!(text != null)) {

                        blockPos =
                                blockPos.add(
                                        random.nextInt(4) - random.nextInt(4),
                                        0,
                                        random.nextInt(4) - random.nextInt(4));
                    }
                } else {

                    blockPos =
                            blockPos.add(
                                    random.nextInt(4) - random.nextInt(4),
                                    0,
                                    random.nextInt(4) - random.nextInt(4));
                }
            }

            blockPos = blockPos.offsetUp();
            if (text == null) {
                break;
            }
        }

        return true;
    }
}
