package net.minecraft.world.gen.feature;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenReed extends WorldGenerator {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < 20) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(4) - random.nextInt(4),
                            0,
                            random.nextInt(4) - random.nextInt(4));
            if (world.isAirBlock(blockPosValue)) {
                BlockPos blockPosValue2 = blockPosValue.offsetDown();
                if (world.getBlockState(blockPosValue2.offsetWest()).getBlock().getMaterial()
                                == Material.water
                        || world.getBlockState(blockPosValue2.offsetEast()).getBlock().getMaterial()
                                == Material.water
                        || world.getBlockState(blockPosValue2.offsetNorth())
                                        .getBlock()
                                        .getMaterial()
                                == Material.water
                        || world.getBlockState(blockPosValue2.offsetSouth())
                                        .getBlock()
                                        .getMaterial()
                                == Material.water) {
                    int j = 2 + random.nextInt(random.nextInt(3) + 1);
                    int k = 0;

                    while (k < j) {
                        if (Blocks.reeds.canBlockStay(world, blockPosValue)) {
                            world.setBlockState(
                                    blockPosValue.offsetUp(k), Blocks.reeds.getDefaultState(), 2);
                        }

                        k++;
                        if (textValue == null) {
                            break;
                        }
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
