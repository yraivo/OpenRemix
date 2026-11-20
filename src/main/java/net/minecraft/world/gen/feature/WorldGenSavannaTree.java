package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSavannaTree extends WorldGenAbstractTree {
    public WorldGenSavannaTree(boolean flag) {
        super(flag);
    }

    private void func_175924_b(World world, BlockPos blockPos) {
        Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        if (material == Material.AIR || material == Material.leaves) {
            this.func_175905_a(world, blockPos, Blocks.leaves2, 0);
        }
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = random.nextInt(3) + random.nextInt(3) + 5;
        String textValue = text;
        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int l = blockPos.getY();

            label222:
            while (l <= blockPos.getY() + 1 + i) {
                byte b1 = 1;
                if (l == blockPos.getY()) {
                    b1 = 0;
                }

                if (l >= blockPos.getY() + 1 + i - 2) {
                    b1 = 2;
                }

                int j = blockPos.getX() - b1;

                while (true) {
                    if (j <= blockPos.getX() + b1 && b0 != 0) {
                        int k = blockPos.getZ() - b1;

                        while (k <= blockPos.getZ() + b1 && b0 != 0) {
                            label211:
                            {
                                if (l >= 0 && l < 256) {
                                    if (this.func_150523_a(
                                            world.getBlockState(new BlockPos(j, l, k))
                                                    .getBlock())) {
                                        break label211;
                                    }

                                    b0 = 0;
                                    if (textValue != null) {
                                        break label211;
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
                        break label222;
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
                Facing facing = FacingPlane.HORIZONTAL.random(random);
                int l2 = i - random.nextInt(4) - 1;
                int i3 = 3 - random.nextInt(3);
                int x = blockPos.getX();
                int j1 = blockPos.getZ();
                int k1 = 0;
                int i2 = 0;

                while (i2 < i) {
                    int l1 = blockPos.getY() + i2;
                    if (i2 >= l2 && i3 > 0) {
                        x += facing.getFrontOffsetX();
                        j1 += facing.getFrontOffsetZ();
                        i3--;
                    }

                    BlockPos blockPosValue = new BlockPos(x, l1, j1);
                    Material material = world.getBlockState(blockPosValue).getBlock().getMaterial();
                    if (material == Material.AIR || material == Material.leaves) {
                        this.func_175905_a(
                                world,
                                blockPosValue,
                                Blocks.log2,
                                BlockPlanksEnumType.ACACIA.getMetadata() - 4);
                        k1 = l1;
                    }

                    i2++;
                    if (textValue == null) {
                        break;
                    }
                }

                BlockPos blockPosValue2 = new BlockPos(x, k1, j1);
                int j3 = -3;

                label170:
                while (j3 <= 3) {
                    int k3 = -3;

                    while (true) {
                        if (k3 <= 3) {
                            if (Math.abs(j3) != 3 || Math.abs(k3) != 3) {
                                this.func_175924_b(world, blockPosValue2.add(j3, 0, k3));
                            }

                            k3++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        j3++;
                        if (textValue == null) {
                            break label170;
                        }
                        break;
                    }
                }

                BlockPos blockPosValue3 = blockPosValue2.offsetUp();
                j3 = -1;

                label152:
                while (j3 <= 1) {
                    int l3 = -1;

                    while (true) {
                        if (l3 <= 1) {
                            this.func_175924_b(world, blockPosValue3.add(j3, 0, l3));
                            l3++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        j3++;
                        if (textValue == null) {
                            break label152;
                        }
                        break;
                    }
                }

                this.func_175924_b(world, blockPosValue3.offsetEast(2));
                this.func_175924_b(world, blockPosValue3.offsetWest(2));
                this.func_175924_b(world, blockPosValue3.offsetSouth(2));
                this.func_175924_b(world, blockPosValue3.offsetNorth(2));
                x = blockPos.getX();
                j1 = blockPos.getZ();
                Facing facingValue = FacingPlane.HORIZONTAL.random(random);
                if (facingValue != facing) {
                    j3 = l2 - random.nextInt(2) - 1;
                    int i4 = 1 + random.nextInt(3);
                    k1 = 0;
                    int k2 = j3;

                    while (k2 < i && i4 > 0) {
                        if (k2 >= 1) {
                            int j2 = blockPos.getY() + k2;
                            x += facingValue.getFrontOffsetX();
                            j1 += facingValue.getFrontOffsetZ();
                            BlockPos blockPosValue4 = new BlockPos(x, j2, j1);
                            Material materialValue =
                                    world.getBlockState(blockPosValue4).getBlock().getMaterial();
                            if (materialValue == Material.AIR || materialValue == Material.leaves) {
                                this.func_175905_a(
                                        world,
                                        blockPosValue4,
                                        Blocks.log2,
                                        BlockPlanksEnumType.ACACIA.getMetadata() - 4);
                                k1 = j2;
                            }
                        }

                        k2++;
                        i4--;
                        if (textValue == null) {
                            break;
                        }
                    }

                    if (k1 > 0) {
                        BlockPos blockPosValue5 = new BlockPos(x, k1, j1);
                        int j4 = -2;

                        while (j4 <= 2) {
                            int k4 = -2;

                            while (k4 <= 2) {
                                if (Math.abs(j4) != 2 || Math.abs(k4) != 2) {
                                    this.func_175924_b(world, blockPosValue5.add(j4, 0, k4));
                                }

                                k4++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            j4++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        BlockPos blockPosValue6 = blockPosValue5.offsetUp();
                        j4 = -1;

                        while (j4 <= 1) {
                            int l4 = -1;

                            while (l4 <= 1) {
                                this.func_175924_b(world, blockPosValue6.add(j4, 0, l4));
                                l4++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            j4++;
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
}
