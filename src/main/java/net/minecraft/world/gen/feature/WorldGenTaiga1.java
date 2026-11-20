package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenAbstractTree {
    public WorldGenTaiga1() {
        super(false);
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        int i = random.nextInt(5) + 7;
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int j = i - random.nextInt(2) - 3;
        int k = i - j;
        int l = 1 + random.nextInt(k + 1);
        String textValue = text;
        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int l1 = blockPos.getY();

            label149:
            while (l1 <= blockPos.getY() + 1 + i && b0 != 0) {
                int k1;
                {
                    if (l1 - blockPos.getY() < j) {
                        k1 = 0;
                        if (!(textValue != null)) {

                            k1 = l;
                        }
                    } else {

                        k1 = l;
                    }
                }

                int i1 = blockPos.getX() - k1;

                while (true) {
                    if (i1 <= blockPos.getX() + k1 && b0 != 0) {
                        int j1 = blockPos.getZ() - k1;

                        while (j1 <= blockPos.getZ() + k1 && b0 != 0) {
                            label132:
                            {
                                if (l1 >= 0 && l1 < 256) {
                                    if (this.func_150523_a(
                                            world.getBlockState(new BlockPos(i1, l1, j1))
                                                    .getBlock())) {
                                        break label132;
                                    }

                                    b0 = 0;
                                    if (textValue != null) {
                                        break label132;
                                    }
                                }

                                b0 = 0;
                            }

                            j1++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        i1++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    l1++;
                    if (textValue == null) {
                        break label149;
                    }
                    break;
                }
            }

            if (b0 == 0) {
                return false;
            }

            Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
            if ((block == Blocks.grass || block == Blocks.dirt) && blockPos.getY() < 256 - i - 1) {
                this.func_175921_a(world, blockPos.offsetDown());
                int i3 = 0;
                int k2 = blockPos.getY() + i;

                while (k2 >= blockPos.getY() + j) {
                    int l2 = blockPos.getX() - i3;

                    label100:
                    while (l2 <= blockPos.getX() + i3) {
                        int j3 = l2 - blockPos.getX();
                        int i2 = blockPos.getZ() - i3;

                        while (true) {
                            if (i2 <= blockPos.getZ() + i3) {
                                int j2 = i2 - blockPos.getZ();
                                if (Math.abs(j3) != i3 || Math.abs(j2) != i3 || i3 <= 0) {
                                    BlockPos blockPosValue = new BlockPos(l2, k2, i2);
                                    if (!world.getBlockState(blockPosValue)
                                            .getBlock()
                                            .isFullBlock()) {
                                        this.func_175905_a(
                                                world,
                                                blockPosValue,
                                                Blocks.leaves,
                                                BlockPlanksEnumType.SPRUCE.getMetadata());
                                    }
                                }

                                i2++;
                                if (textValue != null) {
                                    continue;
                                }
                            }

                            l2++;
                            if (textValue == null) {
                                break label100;
                            }
                            break;
                        }
                    }

                    {
                        if (i3 >= 1 && k2 == blockPos.getY() + j + 1) {
                            i3--;
                            if (!(textValue != null)) {

                                if (i3 < l) {
                                    i3++;
                                }
                            }
                        } else {

                            if (i3 < l) {
                                i3++;
                            }
                        }
                    }

                    k2--;
                    if (textValue == null) {
                        break;
                    }
                }

                k2 = 0;

                while (k2 < i - 1) {
                    Block blockValue = world.getBlockState(blockPos.offsetUp(k2)).getBlock();
                    if (blockValue.getMaterial() == Material.AIR
                            || blockValue.getMaterial() == Material.leaves) {
                        this.func_175905_a(
                                world,
                                blockPos.offsetUp(k2),
                                Blocks.log,
                                BlockPlanksEnumType.SPRUCE.getMetadata());
                    }

                    k2++;
                    if (textValue == null) {
                        break;
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
