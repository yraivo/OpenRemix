package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class WorldGenBlockBlob extends WorldGenerator {
    private int dD;
    private Block aUV;

    public WorldGenBlockBlob(Block aUVValue, int i) {
        super(false);
        this.aUV = aUVValue;
        this.dD = i;
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        while (blockPos.getY() > 3) {
            if (!world.isAirBlock(blockPos.offsetDown())) {
                Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
                if (block == Blocks.grass
                        || block == Blocks.dirt
                        || block == Blocks.stone && text != null) {
                    break;
                }
            }

            blockPos = blockPos.offsetDown();
            if (text == null) {
                break;
            }
        }

        if (blockPos.getY() <= 3) {
            return false;
        }

        int i1 = this.dD;
        int i = 0;

        while (i1 >= 0 && i < 3) {
            int j = i1 + random.nextInt(2);
            int k = i1 + random.nextInt(2);
            int l = i1 + random.nextInt(2);
            float f = (j + k + l) * 0.333F + 0.5F;
            Iterator iterator =
                    BlockPos.getAllInBox(blockPos.add(-j, -k, -l), blockPos.add(j, k, l))
                            .iterator();

            while (true) {
                if (iterator.hasNext()) {
                    BlockPos blockPosValue = (BlockPos) iterator.next();
                    if (blockPosValue.distanceSq(blockPos) <= f * f) {
                        world.setBlockState(blockPosValue, this.aUV.getDefaultState(), 4);
                    }

                    if (text != null) {
                        continue;
                    }
                }

                blockPos =
                        blockPos.add(
                                -(i1 + 1) + random.nextInt(2 + i1 * 2),
                                0 - random.nextInt(2),
                                -(i1 + 1) + random.nextInt(2 + i1 * 2));
                i++;
                if (text == null) {
                    return true;
                }
                break;
            }
        }

        return true;
    }
}
