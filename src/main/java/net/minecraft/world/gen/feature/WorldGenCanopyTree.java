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

public class WorldGenCanopyTree extends WorldGenAbstractTree {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = random.nextInt(3) + random.nextInt(2) + 6;
        byte b0 = 1;
        String textValue = text;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int l = blockPos.getY();

            label271:
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
                            label260:
                            {
                                if (l >= 0 && l < 256) {
                                    if (this.func_150523_a(
                                            world.getBlockState(new BlockPos(j, l, k))
                                                    .getBlock())) {
                                        break label260;
                                    }

                                    b0 = 0;
                                    if (textValue != null) {
                                        break label260;
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
                        break label271;
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
                this.func_175921_a(world, blockPos.add(1, -1, 0));
                this.func_175921_a(world, blockPos.add(1, -1, 1));
                this.func_175921_a(world, blockPos.add(0, -1, 1));
                Facing facing = FacingPlane.HORIZONTAL.random(random);
                int k2 = i - random.nextInt(4);
                int l2 = 2 - random.nextInt(3);
                int x = blockPos.getX();
                int j1 = blockPos.getZ();
                int k1 = 0;
                int l1 = 0;

                while (l1 < i) {
                    int i2 = blockPos.getY() + l1;
                    if (l1 >= k2 && l2 > 0) {
                        x += facing.getFrontOffsetX();
                        j1 += facing.getFrontOffsetZ();
                        l2--;
                    }

                    BlockPos blockPosValue = new BlockPos(x, i2, j1);
                    Material material = world.getBlockState(blockPosValue).getBlock().getMaterial();
                    if (material == Material.AIR || material == Material.leaves) {
                        this.func_175905_a(
                                world,
                                blockPosValue,
                                Blocks.log2,
                                BlockPlanksEnumType.DARK_OAK.getMetadata() - 4);
                        this.func_175905_a(
                                world,
                                blockPosValue.offsetEast(),
                                Blocks.log2,
                                BlockPlanksEnumType.DARK_OAK.getMetadata() - 4);
                        this.func_175905_a(
                                world,
                                blockPosValue.offsetSouth(),
                                Blocks.log2,
                                BlockPlanksEnumType.DARK_OAK.getMetadata() - 4);
                        this.func_175905_a(
                                world,
                                blockPosValue.offsetEast().offsetSouth(),
                                Blocks.log2,
                                BlockPlanksEnumType.DARK_OAK.getMetadata() - 4);
                        k1 = i2;
                    }

                    l1++;
                    if (textValue == null) {
                        break;
                    }
                }

                l1 = -2;

                label219:
                while (l1 <= 0) {
                    int i3 = -2;

                    while (true) {
                        if (i3 <= 0) {
                            byte b2 = -1;
                            this.method_4b9e247c644cab8a4cc8(world, x + l1, k1 + b2, j1 + i3);
                            this.method_4b9e247c644cab8a4cc8(world, 1 + x - l1, k1 + b2, j1 + i3);
                            this.method_4b9e247c644cab8a4cc8(world, x + l1, k1 + b2, 1 + j1 - i3);
                            this.method_4b9e247c644cab8a4cc8(
                                    world, 1 + x - l1, k1 + b2, 1 + j1 - i3);
                            if ((l1 > -2 || i3 > -1) && (l1 != -1 || i3 != -2)) {
                                byte b3 = 1;
                                this.method_4b9e247c644cab8a4cc8(world, x + l1, k1 + b3, j1 + i3);
                                this.method_4b9e247c644cab8a4cc8(
                                        world, 1 + x - l1, k1 + b3, j1 + i3);
                                this.method_4b9e247c644cab8a4cc8(
                                        world, x + l1, k1 + b3, 1 + j1 - i3);
                                this.method_4b9e247c644cab8a4cc8(
                                        world, 1 + x - l1, k1 + b3, 1 + j1 - i3);
                            }

                            i3++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        l1++;
                        if (textValue == null) {
                            break label219;
                        }
                        break;
                    }
                }

                if (random.nextBoolean()) {
                    this.method_4b9e247c644cab8a4cc8(world, x, k1 + 2, j1);
                    this.method_4b9e247c644cab8a4cc8(world, x + 1, k1 + 2, j1);
                    this.method_4b9e247c644cab8a4cc8(world, x + 1, k1 + 2, j1 + 1);
                    this.method_4b9e247c644cab8a4cc8(world, x, k1 + 2, j1 + 1);
                }

                l1 = -3;

                label198:
                while (l1 <= 4) {
                    int j3 = -3;

                    while (true) {
                        if (j3 <= 4) {
                            if ((l1 != -3 || j3 != -3)
                                    && (l1 != -3 || j3 != 4)
                                    && (l1 != 4 || j3 != -3)
                                    && (l1 != 4 || j3 != 4)
                                    && (Math.abs(l1) < 3 || Math.abs(j3) < 3)) {
                                this.method_4b9e247c644cab8a4cc8(world, x + l1, k1, j1 + j3);
                            }

                            j3++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        l1++;
                        if (textValue == null) {
                            break label198;
                        }
                        break;
                    }
                }

                l1 = -1;

                while (l1 <= 2) {
                    int k3 = -1;

                    while (true) {
                        if (k3 <= 2) {
                            if ((l1 < 0 || l1 > 1 || k3 < 0 || k3 > 1) && random.nextInt(3) <= 0) {
                                int l3 = random.nextInt(3) + 2;
                                int i4 = 0;

                                while (i4 < l3) {
                                    this.func_175905_a(
                                            world,
                                            new BlockPos(
                                                    blockPos.getX() + l1,
                                                    k1 - i4 - 1,
                                                    blockPos.getZ() + k3),
                                            Blocks.log2,
                                            BlockPlanksEnumType.DARK_OAK.getMetadata() - 4);
                                    i4++;
                                    if (textValue == null) {
                                        break;
                                    }
                                }

                                i4 = -1;

                                label153:
                                while (i4 <= 1) {
                                    int j2 = -1;

                                    while (true) {
                                        if (j2 <= 1) {
                                            this.method_4b9e247c644cab8a4cc8(
                                                    world, x + l1 + i4, k1 - 0, j1 + k3 + j2);
                                            j2++;
                                            if (textValue != null) {
                                                continue;
                                            }
                                        }

                                        i4++;
                                        if (textValue == null) {
                                            break label153;
                                        }
                                        break;
                                    }
                                }

                                i4 = -2;

                                while (i4 <= 2) {
                                    int j4 = -2;

                                    while (j4 <= 2) {
                                        if (Math.abs(i4) != 2 || Math.abs(j4) != 2) {
                                            this.method_4b9e247c644cab8a4cc8(
                                                    world, x + l1 + i4, k1 - 1, j1 + k3 + j4);
                                        }

                                        j4++;
                                        if (textValue == null) {
                                            break;
                                        }
                                    }

                                    i4++;
                                    if (textValue == null) {
                                        break;
                                    }
                                }
                            }

                            k3++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        l1++;
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

    public WorldGenCanopyTree(boolean flag) {
        super(flag);
    }

    private void method_4b9e247c644cab8a4cc8(World world, int i, int j, int k) {
        Block block = world.getBlockState(new BlockPos(i, j, k)).getBlock();
        if (block.getMaterial() == Material.AIR) {
            this.func_175905_a(world, new BlockPos(i, j, k), Blocks.leaves2, 1);
        }
    }
}
