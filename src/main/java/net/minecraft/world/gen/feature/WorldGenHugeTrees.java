package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree {
    protected int leavesMetadata;
    protected int woodMetadata;
    protected int baseHeight;
    protected int extraRandomHeight;

    public WorldGenHugeTrees(boolean flag, int i, int j, int k, int l) {
        super(flag);
        this.baseHeight = i;
        this.extraRandomHeight = j;
        this.woodMetadata = k;
        this.leavesMetadata = l;
    }

    private boolean method_d54e49ced33b7f0da655(World world, BlockPos blockPos, int i) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        byte b0 = 1;
        String textValue = text;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int j = 0;

            while (j <= 1 + i) {
                byte b1;
                {
                    b1 = 2;
                    if (j == 0) {
                        b1 = 1;
                        if (!(textValue != null)) {

                            if (j >= 1 + i - 2) {
                                b1 = 2;
                            }
                        }
                    } else {

                        if (j >= 1 + i - 2) {
                            b1 = 2;
                        }
                    }
                }

                int k = -b1;

                while (true) {
                    if (k <= b1 && b0 != 0) {
                        int l = -b1;

                        while (l <= b1 && b0 != 0) {
                            if (blockPos.getY() + j < 0
                                    || blockPos.getY() + j >= 256
                                    || !this.func_150523_a(
                                            world.getBlockState(blockPos.add(k, j, l))
                                                    .getBlock())) {
                                b0 = 0;
                            }

                            l++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        k++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    j++;
                    if (textValue == null) {
                        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
                    }
                    break;
                }
            }

            return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
        } else {
            return false;
        }
    }

    protected void growLeavesLayer(World world, BlockPos blockPos, int i) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int j = i * i;
        String textValue = text;
        int k = -i;

        while (k <= i + 1) {
            int l = -i;

            while (true) {
                if (l <= i + 1) {
                    int i1 = k - 1;
                    int j1 = l - 1;
                    if (k * k + l * l <= j
                            || i1 * i1 + j1 * j1 <= j
                            || k * k + j1 * j1 <= j
                            || i1 * i1 + l * l <= j) {
                        BlockPos blockPosValue = blockPos.add(k, 0, l);
                        Material material =
                                world.getBlockState(blockPosValue).getBlock().getMaterial();
                        if (material == Material.AIR || material == Material.leaves) {
                            this.func_175905_a(
                                    world, blockPosValue, Blocks.leaves, this.leavesMetadata);
                        }
                    }

                    l++;
                    if (textValue != null) {
                        continue;
                    }
                }

                k++;
                if (textValue == null) {
                    return;
                }
                break;
            }
        }
    }

    protected void growLeavesLayerStrict(World world, BlockPos blockPos, int i) {
        int j = i * i;
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int k = -i;
        String textValue = text;

        while (k <= i) {
            int l = -i;

            while (true) {
                if (l <= i) {
                    if (k * k + l * l <= j) {
                        BlockPos blockPosValue = blockPos.add(k, 0, l);
                        Material material =
                                world.getBlockState(blockPosValue).getBlock().getMaterial();
                        if (material == Material.AIR || material == Material.leaves) {
                            this.func_175905_a(
                                    world, blockPosValue, Blocks.leaves, this.leavesMetadata);
                        }
                    }

                    l++;
                    if (textValue != null) {
                        continue;
                    }
                }

                k++;
                if (textValue == null) {
                    return;
                }
                break;
            }
        }
    }

    protected int getTreeHeight(Random random) {
        int i = random.nextInt(3) + this.baseHeight;
        if (this.extraRandomHeight > 1) {
            i += random.nextInt(this.extraRandomHeight);
        }

        return i;
    }

    private boolean method_f1aa7d7a3276bd75ac5c(BlockPos blockPos, World world) {
        BlockPos blockPosValue = blockPos.offsetDown();
        Block block = world.getBlockState(blockPosValue).getBlock();
        if ((block == Blocks.grass || block == Blocks.dirt) && blockPos.getY() >= 2) {
            this.func_175921_a(world, blockPosValue);
            this.func_175921_a(world, blockPosValue.offsetEast());
            this.func_175921_a(world, blockPosValue.offsetSouth());
            this.func_175921_a(world, blockPosValue.offsetSouth().offsetEast());
            return true;
        } else {
            return false;
        }
    }

    protected boolean ensureGrowable(World world, Random random, BlockPos blockPos, int i) {
        return this.method_d54e49ced33b7f0da655(world, blockPos, i)
                && this.method_f1aa7d7a3276bd75ac5c(blockPos, world);
    }
}
