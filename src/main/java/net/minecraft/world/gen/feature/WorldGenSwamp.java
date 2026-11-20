package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSwamp extends WorldGenAbstractTree {
    public WorldGenSwamp() {
        super(false);
    }

    private void method_485d7cb176bd7b61c9cf(World world, BlockPos blockPos, int i) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        this.func_175905_a(world, blockPos, Blocks.vine, i);
        String textValue = text;
        int j = 4;
        blockPos = blockPos.offsetDown();

        while (world.getBlockState(blockPos).getBlock().getMaterial() == Material.AIR && j > 0) {
            this.func_175905_a(world, blockPos, Blocks.vine, i);
            blockPos = blockPos.offsetDown();
            j--;
            if (textValue == null) {
                break;
            }
        }
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = random.nextInt(4) + 5;
        String textValue = text;

        while (world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial()
                == Material.water) {
            blockPos = blockPos.offsetDown();
            if (textValue == null) {
                break;
            }
        }

        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int l = blockPos.getY();

            label215:
            while (l <= blockPos.getY() + 1 + i) {
                byte b1 = 1;
                if (l == blockPos.getY()) {
                    b1 = 0;
                }

                if (l >= blockPos.getY() + 1 + i - 2) {
                    b1 = 3;
                }

                int j = blockPos.getX() - b1;

                while (true) {
                    if (j <= blockPos.getX() + b1 && b0 != 0) {
                        int k = blockPos.getZ() - b1;

                        while (k <= blockPos.getZ() + b1 && b0 != 0) {
                            label204:
                            {
                                if (l >= 0 && l < 256) {
                                    Block block =
                                            world.getBlockState(new BlockPos(j, l, k)).getBlock();
                                    if (block.getMaterial() != Material.AIR
                                            && block.getMaterial() != Material.leaves) {
                                        if (block != Blocks.water
                                                && block != Blocks.flowing_water) {
                                            b0 = 0;
                                            if (!(textValue != null)) {

                                                if (l > blockPos.getY()) {
                                                    b0 = 0;
                                                }
                                            }
                                        } else {

                                            if (l > blockPos.getY()) {
                                                b0 = 0;
                                            }
                                        }
                                    }

                                    if (textValue != null) {
                                        break label204;
                                    }
                                }

                                b0 = 0;
                            }

                            k++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        j++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    l++;
                    if (textValue == null) {
                        break label215;
                    }
                    break;
                }
            }

            if (b0 == 0) {
                return false;
            }

            Block blockValue = world.getBlockState(blockPos.offsetDown()).getBlock();
            if ((blockValue == Blocks.grass || blockValue == Blocks.dirt)
                    && blockPos.getY() < 256 - i - 1) {
                this.func_175921_a(world, blockPos.offsetDown());
                int i1 = blockPos.getY() - 3 + i;

                label168:
                while (i1 <= blockPos.getY() + i) {
                    int i2 = i1 - (blockPos.getY() + i);
                    int k2 = 2 - i2 / 2;
                    int j1 = blockPos.getX() - k2;

                    while (true) {
                        if (j1 <= blockPos.getX() + k2) {
                            int i3 = j1 - blockPos.getX();
                            int k1 = blockPos.getZ() - k2;

                            while (k1 <= blockPos.getZ() + k2) {
                                int l1 = k1 - blockPos.getZ();
                                if (Math.abs(i3) != k2
                                        || Math.abs(l1) != k2
                                        || random.nextInt(2) != 0 && i2 != 0) {
                                    BlockPos blockPosValue = new BlockPos(j1, i1, k1);
                                    if (!world.getBlockState(blockPosValue)
                                            .getBlock()
                                            .isFullBlock()) {
                                        this.func_175906_a(world, blockPosValue, Blocks.leaves);
                                    }
                                }

                                k1++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            j1++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        i1++;
                        if (textValue == null) {
                            break label168;
                        }
                        break;
                    }
                }

                i1 = 0;

                while (i1 < i) {
                    Block blockValue2 = world.getBlockState(blockPos.offsetUp(i1)).getBlock();
                    if (blockValue2.getMaterial() == Material.AIR
                            || blockValue2.getMaterial() == Material.leaves
                            || blockValue2 == Blocks.flowing_water
                            || blockValue2 == Blocks.water) {
                        this.func_175906_a(world, blockPos.offsetUp(i1), Blocks.log);
                    }

                    i1++;
                    if (textValue == null) {
                        break;
                    }
                }

                i1 = blockPos.getY() - 3 + i;

                while (i1 <= blockPos.getY() + i) {
                    int j2 = i1 - (blockPos.getY() + i);
                    int l2 = 2 - j2 / 2;
                    int k3 = blockPos.getX() - l2;

                    while (true) {
                        if (k3 <= blockPos.getX() + l2) {
                            int j3 = blockPos.getZ() - l2;

                            while (j3 <= blockPos.getZ() + l2) {
                                BlockPos blockPosValue2 = new BlockPos(k3, i1, j3);
                                if (world.getBlockState(blockPosValue2).getBlock().getMaterial()
                                        == Material.leaves) {
                                    BlockPos blockPosValue3 = blockPosValue2.offsetWest();
                                    BlockPos blockPosValue4 = blockPosValue2.offsetEast();
                                    BlockPos blockPosValue5 = blockPosValue2.offsetNorth();
                                    BlockPos blockPosValue6 = blockPosValue2.offsetSouth();
                                    if (random.nextInt(4) == 0
                                            && world.getBlockState(blockPosValue3)
                                                            .getBlock()
                                                            .getMaterial()
                                                    == Material.AIR) {
                                        this.method_485d7cb176bd7b61c9cf(
                                                world, blockPosValue3, BlockVine.field_176275_S);
                                    }

                                    if (random.nextInt(4) == 0
                                            && world.getBlockState(blockPosValue4)
                                                            .getBlock()
                                                            .getMaterial()
                                                    == Material.AIR) {
                                        this.method_485d7cb176bd7b61c9cf(
                                                world, blockPosValue4, BlockVine.field_176271_T);
                                    }

                                    if (random.nextInt(4) == 0
                                            && world.getBlockState(blockPosValue5)
                                                            .getBlock()
                                                            .getMaterial()
                                                    == Material.AIR) {
                                        this.method_485d7cb176bd7b61c9cf(
                                                world, blockPosValue5, BlockVine.field_176272_Q);
                                    }

                                    if (random.nextInt(4) == 0
                                            && world.getBlockState(blockPosValue6)
                                                            .getBlock()
                                                            .getMaterial()
                                                    == Material.AIR) {
                                        this.method_485d7cb176bd7b61c9cf(
                                                world, blockPosValue6, BlockVine.field_176276_R);
                                    }
                                }

                                j3++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            k3++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        i1++;
                        if (textValue == null) {
                            return true;
                        }
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
