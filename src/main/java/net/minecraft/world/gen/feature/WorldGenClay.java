package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenClay extends WorldGenerator {
    private Block field_150546_a = Blocks.clay;
    private int numberOfBlocks;

    public WorldGenClay(int numberOfBlocksValue) {
        this.numberOfBlocks = numberOfBlocksValue;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.water) {
            return false;
        }

        int i = random.nextInt(this.numberOfBlocks - 2) + 2;
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
                            if (block == Blocks.dirt || block == Blocks.clay) {
                                world.setBlockState(
                                        blockPosValue, this.field_150546_a.getDefaultState(), 2);
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
