package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenShrub extends WorldGenTrees {
    private int woodMetadata;
    private int leavesMetadata;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.AIR
                        || block.getMaterial() == Material.leaves)
                && blockPos.getY() > 0) {
            blockPos = blockPos.offsetDown();
            if (text == null) {
                break;
            }
        }

        Block blockValue = world.getBlockState(blockPos).getBlock();
        if (blockValue == Blocks.dirt || blockValue == Blocks.grass) {
            blockPos = blockPos.offsetUp();
            this.func_175905_a(world, blockPos, Blocks.log, this.woodMetadata);
            int y = blockPos.getY();

            while (y <= blockPos.getY() + 2) {
                int j = y - blockPos.getY();
                int k = 2 - j;
                int l = blockPos.getX() - k;

                while (l <= blockPos.getX() + k) {
                    int i1 = l - blockPos.getX();
                    int j1 = blockPos.getZ() - k;

                    while (j1 <= blockPos.getZ() + k) {
                        int k1 = j1 - blockPos.getZ();
                        if (Math.abs(i1) != k || Math.abs(k1) != k || random.nextInt(2) != 0) {
                            BlockPos blockPosValue = new BlockPos(l, y, j1);
                            if (!world.getBlockState(blockPosValue).getBlock().isFullBlock()) {
                                this.func_175905_a(world, blockPosValue, Blocks.leaves, this.leavesMetadata);
                            }
                        }

                        j1++;
                        if (text == null) {
                            break;
                        }
                    }

                    l++;
                    if (text == null) {
                        break;
                    }
                }

                y++;
                if (text == null) {
                    break;
                }
            }
        }

        return true;
    }

    public WorldGenShrub(int p171_0, int p171_1) {
        super(false);
        this.woodMetadata = p171_0;
        this.leavesMetadata = p171_1;
    }
}
