package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenIceSpike extends WorldGenerator {
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

        blockPos = blockPos.offsetUp(random.nextInt(4));
        int i = random.nextInt(4) + 7;
        int j = i / 4 + random.nextInt(2);
        if (j > 1 && random.nextInt(60) == 0) {
            blockPos = blockPos.offsetUp(10 + random.nextInt(30));
        }

        int k = 0;

        label155:
        while (k < i) {
            float f = (1.0F - (float) k / i) * j;
            int l = MathHelper.ceil(f);
            int i1 = -l;

            while (true) {
                if (i1 <= l) {
                    float f1 = MathHelper.abs(i1) - 0.25F;
                    int j1 = -l;

                    while (j1 <= l) {
                        float f2 = MathHelper.abs(j1) - 0.25F;
                        if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f)
                                && (i1 != -l && i1 != l && j1 != -l && j1 != l
                                        || random.nextFloat() <= 0.75F)) {
                            Block block = world.getBlockState(blockPos.add(i1, k, j1)).getBlock();
                            if (block.getMaterial() == Material.AIR
                                    || block == Blocks.dirt
                                    || block == Blocks.snow
                                    || block == Blocks.ice) {
                                this.func_175906_a(
                                        world, blockPos.add(i1, k, j1), Blocks.packed_ice);
                            }

                            if (k != 0 && l > 1) {
                                block = world.getBlockState(blockPos.add(i1, -k, j1)).getBlock();
                                if (block.getMaterial() == Material.AIR
                                        || block == Blocks.dirt
                                        || block == Blocks.snow
                                        || block == Blocks.ice) {
                                    this.func_175906_a(
                                            world, blockPos.add(i1, -k, j1), Blocks.packed_ice);
                                }
                            }
                        }

                        j1++;
                        if (text == null) {
                            break;
                        }
                    }

                    i1++;
                    if (text != null) {
                        continue;
                    }
                }

                k++;
                if (text == null) {
                    break label155;
                }
                break;
            }
        }

        {
            k = j - 1;
            if (k < 0) {
                k = 0;
                if (!(text != null)) {

                    if (k > 1) {
                        k = 1;
                    }
                }
            } else {

                if (k > 1) {
                    k = 1;
                }
            }
        }

        int l1 = -k;

        while (l1 <= k) {
            int k1 = -k;

            while (true) {
                if (k1 <= k) {
                    BlockPos blockPosValue = blockPos.add(l1, -1, k1);
                    int i2 = 50;
                    if (Math.abs(l1) == 1 && Math.abs(k1) == 1) {
                        i2 = random.nextInt(5);
                    }

                    while (blockPosValue.getY() > 50) {
                        Block blockValue = world.getBlockState(blockPosValue).getBlock();
                        if (blockValue.getMaterial() != Material.AIR
                                && blockValue != Blocks.dirt
                                && blockValue != Blocks.snow
                                && blockValue != Blocks.ice
                                && blockValue != Blocks.packed_ice) {
                            break;
                        }

                        this.func_175906_a(world, blockPosValue, Blocks.packed_ice);
                        blockPosValue = blockPosValue.offsetDown();
                        if (--i2 <= 0) {
                            blockPosValue = blockPosValue.offsetDown(random.nextInt(5) + 1);
                            i2 = random.nextInt(5);
                            if (text == null) {
                                break;
                            }
                        }
                    }

                    k1++;
                    if (text != null) {
                        continue;
                    }
                }

                l1++;
                if (text == null) {
                    return true;
                }
                break;
            }
        }

        return true;
    }
}
