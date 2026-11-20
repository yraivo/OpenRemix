package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenBigMushroom extends WorldGenerator {
    private int mushroomType = -1;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = random.nextInt(2);
        String textValue = text;
        if (this.mushroomType >= 0) {
            i = this.mushroomType;
        }

        int j = random.nextInt(3) + 4;
        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + j + 1 < 256) {
            int y = blockPos.getY();

            label232:
            while (y <= blockPos.getY() + 1 + j) {
                byte b1 = 3;
                if (y <= blockPos.getY() + 3) {
                    b1 = 0;
                }

                int k = blockPos.getX() - b1;

                while (true) {
                    if (k <= blockPos.getX() + b1 && b0 != 0) {
                        int l = blockPos.getZ() - b1;

                        while (l <= blockPos.getZ() + b1 && b0 != 0) {
                            {
                                if (y >= 0 && y < 256) {
                                    Block block =
                                            world.getBlockState(new BlockPos(k, y, l)).getBlock();
                                    if (block.getMaterial() != Material.AIR
                                            && block.getMaterial() != Material.leaves) {
                                        b0 = 0;
                                    }

                                    if (!(textValue != null)) {

                                        b0 = 0;
                                    }
                                } else {

                                    b0 = 0;
                                }
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

                    y++;
                    if (textValue == null) {
                        break label232;
                    }
                    break;
                }
            }

            if (b0 == 0) {
                return false;
            }

            Block blockValue = world.getBlockState(blockPos.offsetDown()).getBlock();
            if (blockValue != Blocks.dirt
                    && blockValue != Blocks.grass
                    && blockValue != Blocks.mycelium) {
                return false;
            }

            int j2 = blockPos.getY() + j;
            if (i == 1) {
                j2 = blockPos.getY() + j - 3;
            }

            int l1 = j2;

            label201:
            while (l1 <= blockPos.getY() + j) {
                int i2 = 1;
                if (l1 < blockPos.getY() + j) {
                    i2++;
                }

                if (i == 0) {
                    i2 = 3;
                }

                int k2 = blockPos.getX() - i2;

                while (true) {
                    if (k2 <= blockPos.getX() + i2) {
                        int j1 = blockPos.getZ() - i2;

                        while (j1 <= blockPos.getZ() + i2) {
                            int k1 = 5;
                            if (k2 == blockPos.getX() - i2) {
                                k1--;
                            }

                            if (k2 == blockPos.getX() + i2) {
                                k1++;
                            }

                            if (j1 == blockPos.getZ() - i2) {
                                k1 -= 3;
                            }

                            if (j1 == blockPos.getZ() + i2) {
                                k1 += 3;
                            }

                            label255:
                            {
                                if (i == 0 || l1 < blockPos.getY() + j) {
                                    if ((k2 == blockPos.getX() - i2 || k2 == blockPos.getX() + i2)
                                            && (j1 == blockPos.getZ() - i2
                                                    || j1 == blockPos.getZ() + i2
                                                            && textValue != null)) {
                                        break label255;
                                    }

                                    if (k2 == blockPos.getX() - (i2 - 1)
                                            && j1 == blockPos.getZ() - i2) {
                                        k1 = 1;
                                    }

                                    if (k2 == blockPos.getX() - i2
                                            && j1 == blockPos.getZ() - (i2 - 1)) {
                                        k1 = 1;
                                    }

                                    if (k2 == blockPos.getX() + (i2 - 1)
                                            && j1 == blockPos.getZ() - i2) {
                                        k1 = 3;
                                    }

                                    if (k2 == blockPos.getX() + i2
                                            && j1 == blockPos.getZ() - (i2 - 1)) {
                                        k1 = 3;
                                    }

                                    if (k2 == blockPos.getX() - (i2 - 1)
                                            && j1 == blockPos.getZ() + i2) {
                                        k1 = 7;
                                    }

                                    if (k2 == blockPos.getX() - i2
                                            && j1 == blockPos.getZ() + (i2 - 1)) {
                                        k1 = 7;
                                    }

                                    if (k2 == blockPos.getX() + (i2 - 1)
                                            && j1 == blockPos.getZ() + i2) {
                                        k1 = 9;
                                    }

                                    if (k2 == blockPos.getX() + i2
                                            && j1 == blockPos.getZ() + (i2 - 1)) {
                                        k1 = 9;
                                    }
                                }

                                if (k1 == 5 && l1 < blockPos.getY() + j) {
                                    k1 = 0;
                                }

                                if (k1 != 0 || blockPos.getY() >= blockPos.getY() + j - 1) {
                                    BlockPos blockPosValue = new BlockPos(k2, l1, j1);
                                    if (!world.getBlockState(blockPosValue)
                                            .getBlock()
                                            .isFullBlock()) {
                                        this.func_175905_a(
                                                world,
                                                blockPosValue,
                                                Block.getBlockById(
                                                        Block.getIdFromBlock(
                                                                        Blocks.brown_mushroom_block)
                                                                + i),
                                                k1);
                                    }
                                }
                            }

                            j1++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        k2++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    l1++;
                    if (textValue == null) {
                        break label201;
                    }
                    break;
                }
            }

            l1 = 0;

            while (l1 < j) {
                Block blockValue2 = world.getBlockState(blockPos.offsetUp(l1)).getBlock();
                if (!blockValue2.isFullBlock()) {
                    this.func_175905_a(
                            world,
                            blockPos.offsetUp(l1),
                            Block.getBlockById(
                                    Block.getIdFromBlock(Blocks.brown_mushroom_block) + i),
                            10);
                }

                l1++;
                if (textValue == null) {
                    break;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public WorldGenBigMushroom(int mushroomTypeValue) {
        super(true);
        this.mushroomType = mushroomTypeValue;
    }

    public WorldGenBigMushroom() {
        super(false);
    }
}
