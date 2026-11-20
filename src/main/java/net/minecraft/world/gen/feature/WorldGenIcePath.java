package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenIcePath extends WorldGenerator {
    private int basePathWidth;
    private Block block = Blocks.packed_ice;

    public WorldGenIcePath(int basePathWidthValue) {
        this.basePathWidth = basePathWidthValue;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        while (world.isAirBlock(blockPos) && blockPos.getY() > 2) {
            blockPos = blockPos.offsetDown();
            if (text == null) {
                break;
            }
        }

        if (world.getBlockState(blockPos).getBlock() != Blocks.snow) {
            return false;
        }

        int i = random.nextInt(this.basePathWidth - 2) + 2;
        byte b0 = 1;
        int j = blockPos.getX() - i;

        while (j <= blockPos.getX() + i) {
            int k = blockPos.getZ() - i;

            while (true) {
                if (k <= blockPos.getZ() + i) {
                    int l = j - blockPos.getX();
                    int i1 = k - blockPos.getZ();
                    if (l * l + i1 * i1 <= i * i) {
                        int j1 = blockPos.getY() - b0;

                        while (j1 <= blockPos.getY() + b0) {
                            BlockPos blockPosValue = new BlockPos(j, j1, k);
                            Block block = world.getBlockState(blockPosValue).getBlock();
                            if (block == Blocks.dirt
                                    || block == Blocks.snow
                                    || block == Blocks.ice) {
                                world.setBlockState(blockPosValue, this.block.getDefaultState(), 2);
                            }

                            j1++;
                            if (text == null) {
                                break;
                            }
                        }
                    }

                    k++;
                    if (text != null) {
                        continue;
                    }
                }

                j++;
                if (text == null) {
                    return true;
                }
                break;
            }
        }

        return true;
    }
}
