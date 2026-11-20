package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenForest extends WorldGenAbstractTree {
    private boolean useExtraRandomHeight;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = random.nextInt(3) + 5;
        String textValue = text;
        if (this.useExtraRandomHeight) {
            i += random.nextInt(7);
        }

        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int l = blockPos.getY();

            label147:
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
                            label136:
                            {
                                if (l >= 0 && l < 256) {
                                    if (this.func_150523_a(
                                            world.getBlockState(new BlockPos(j, l, k))
                                                    .getBlock())) {
                                        break label136;
                                    }

                                    b0 = 0;
                                    if (textValue != null) {
                                        break label136;
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
                        break label147;
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
                int k2 = blockPos.getY() - 3 + i;

                label107:
                while (k2 <= blockPos.getY() + i) {
                    int i2 = k2 - (blockPos.getY() + i);
                    int j2 = 1 - i2 / 2;
                    int i1 = blockPos.getX() - j2;

                    while (true) {
                        if (i1 <= blockPos.getX() + j2) {
                            int j1 = i1 - blockPos.getX();
                            int k1 = blockPos.getZ() - j2;

                            while (k1 <= blockPos.getZ() + j2) {
                                int l1 = k1 - blockPos.getZ();
                                if (Math.abs(j1) != j2
                                        || Math.abs(l1) != j2
                                        || random.nextInt(2) != 0 && i2 != 0) {
                                    BlockPos blockPosValue = new BlockPos(i1, k2, k1);
                                    Block blockValue =
                                            world.getBlockState(blockPosValue).getBlock();
                                    if (blockValue.getMaterial() == Material.AIR
                                            || blockValue.getMaterial() == Material.leaves) {
                                        this.func_175905_a(
                                                world,
                                                blockPosValue,
                                                Blocks.leaves,
                                                BlockPlanksEnumType.BIRCH.getMetadata());
                                    }
                                }

                                k1++;
                                if (textValue == null) {
                                    break;
                                }
                            }

                            i1++;
                            if (textValue != null) {
                                continue;
                            }
                        }

                        k2++;
                        if (textValue == null) {
                            break label107;
                        }
                        break;
                    }
                }

                k2 = 0;

                while (k2 < i) {
                    Block blockValue2 = world.getBlockState(blockPos.offsetUp(k2)).getBlock();
                    if (blockValue2.getMaterial() == Material.AIR
                            || blockValue2.getMaterial() == Material.leaves) {
                        this.func_175905_a(
                                world,
                                blockPos.offsetUp(k2),
                                Blocks.log,
                                BlockPlanksEnumType.BIRCH.getMetadata());
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

    public WorldGenForest(boolean p171_0, boolean useExtraRandomHeightValue) {
        super(p171_0);
        this.useExtraRandomHeight = useExtraRandomHeightValue;
    }
}
