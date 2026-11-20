package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTrees extends WorldGenAbstractTree {
    private boolean vinesGrow;
    private int metaWood;
    private int minTreeHeight;
    private int metaLeaves;

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

    public WorldGenTrees(boolean flag) {
        this(flag, 4, 0, 0, false);
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = random.nextInt(3) + this.minTreeHeight;
        String textValue = text;
        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int k = blockPos.getY();

            label260:
            while (k <= blockPos.getY() + 1 + i) {
                byte b1 = 1;
                if (k == blockPos.getY()) {
                    b1 = 0;
                }

                if (k >= blockPos.getY() + 1 + i - 2) {
                    b1 = 2;
                }

                int l = blockPos.getX() - b1;

                while (true) {
                    if (l <= blockPos.getX() + b1 && b0 != 0) {
                        int j = blockPos.getZ() - b1;

                        while (j <= blockPos.getZ() + b1 && b0 != 0) {
                            label249:
                            {
                                if (k >= 0 && k < 256) {
                                    if (this.func_150523_a(
                                            world.getBlockState(new BlockPos(l, k, j))
                                                    .getBlock())) {
                                        break label249;
                                    }

                                    b0 = 0;
                                    if (textValue != null) {
                                        break label249;
                                    }
                                }

                                b0 = 0;
                            }

                            j++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        l++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    k++;
                    if (textValue == null) {
                        break label260;
                    }
                    break;
                }
            }

            if (b0 == 0) {
                return false;
            }

            Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
            if ((block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland)
                    && blockPos.getY() < 256 - i - 1) {
                this.func_175921_a(world, blockPos.offsetDown());
                byte b2 = 3;
                byte b3 = 0;
                int k2 = blockPos.getY() - b2 + i;

                label220:
                while (k2 <= blockPos.getY() + i) {
                    int i1 = k2 - (blockPos.getY() + i);
                    int j1 = b3 + 1 - i1 / 2;
                    int k1 = blockPos.getX() - j1;

                    while (true) {
                        if (k1 <= blockPos.getX() + j1) {
                            int l1 = k1 - blockPos.getX();
                            int i2 = blockPos.getZ() - j1;

                            while (i2 <= blockPos.getZ() + j1) {
                                int j2 = i2 - blockPos.getZ();
                                if (Math.abs(l1) != j1
                                        || Math.abs(j2) != j1
                                        || random.nextInt(2) != 0 && i1 != 0) {
                                    BlockPos blockPosValue = new BlockPos(k1, k2, i2);
                                    Block blockValue =
                                            world.getBlockState(blockPosValue).getBlock();
                                    if (blockValue.getMaterial() == Material.AIR
                                            || blockValue.getMaterial() == Material.leaves
                                            || blockValue.getMaterial() == Material.vine) {
                                        this.func_175905_a(
                                                world,
                                                blockPosValue,
                                                Blocks.leaves,
                                                this.metaLeaves);
                                    }
                                }

                                i2++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            k1++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        k2++;
                        if (textValue == null) {
                            break label220;
                        }
                        break;
                    }
                }

                k2 = 0;

                while (k2 < i) {
                    Block blockValue2 = world.getBlockState(blockPos.offsetUp(k2)).getBlock();
                    if (blockValue2.getMaterial() == Material.AIR
                            || blockValue2.getMaterial() == Material.leaves
                            || blockValue2.getMaterial() == Material.vine) {
                        this.func_175905_a(world, blockPos.offsetUp(k2), Blocks.log, this.metaWood);
                        if (this.vinesGrow && k2 > 0) {
                            if (random.nextInt(3) > 0
                                    && world.isAirBlock(blockPos.add(-1, k2, 0))) {
                                this.func_175905_a(
                                        world,
                                        blockPos.add(-1, k2, 0),
                                        Blocks.vine,
                                        BlockVine.field_176275_S);
                            }

                            if (random.nextInt(3) > 0 && world.isAirBlock(blockPos.add(1, k2, 0))) {
                                this.func_175905_a(
                                        world,
                                        blockPos.add(1, k2, 0),
                                        Blocks.vine,
                                        BlockVine.field_176271_T);
                            }

                            if (random.nextInt(3) > 0
                                    && world.isAirBlock(blockPos.add(0, k2, -1))) {
                                this.func_175905_a(
                                        world,
                                        blockPos.add(0, k2, -1),
                                        Blocks.vine,
                                        BlockVine.field_176272_Q);
                            }

                            if (random.nextInt(3) > 0 && world.isAirBlock(blockPos.add(0, k2, 1))) {
                                this.func_175905_a(
                                        world,
                                        blockPos.add(0, k2, 1),
                                        Blocks.vine,
                                        BlockVine.field_176276_R);
                            }
                        }
                    }

                    k2++;
                    if (textValue == null) {
                        break;
                    }
                }

                if (this.vinesGrow) {
                    k2 = blockPos.getY() - 3 + i;

                    while (k2 <= blockPos.getY() + i) {
                        int l2 = k2 - (blockPos.getY() + i);
                        int j3 = 2 - l2 / 2;
                        int l3 = blockPos.getX() - j3;

                        while (l3 <= blockPos.getX() + j3) {
                            int i4 = blockPos.getZ() - j3;

                            while (i4 <= blockPos.getZ() + j3) {
                                BlockPos blockPosValue2 = new BlockPos(l3, k2, i4);
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

                                i4++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            l3++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        k2++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    if (random.nextInt(5) == 0 && i > 5) {
                        k2 = 0;

                        while (k2 < 2) {
                            int i3 = 0;

                            while (i3 < 4) {
                                if (random.nextInt(4 - k2) == 0) {
                                    int k3 = random.nextInt(3);
                                    Facing facing = Facing.getHorizontal(i3).getOpposite();
                                    this.func_175905_a(
                                            world,
                                            blockPos.add(
                                                    facing.getFrontOffsetX(),
                                                    i - 5 + k2,
                                                    facing.getFrontOffsetZ()),
                                            Blocks.cocoa,
                                            k3 << 2
                                                    | Facing.getHorizontal(i3)
                                                            .getHorizontalIndex());
                                }

                                i3++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            k2++;
                            if (textValue == null) {
                                break;
                            }
                        }
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

    public WorldGenTrees(
            boolean flag, int minTreeHeightValue, int j, int k, boolean vinesGrowValue) {
        super(flag);
        this.minTreeHeight = minTreeHeightValue;
        this.metaWood = j;
        this.metaLeaves = k;
        this.vinesGrow = vinesGrowValue;
    }
}
