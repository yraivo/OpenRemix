package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTaiga2 extends WorldGenAbstractTree {
    public boolean generate(World world, Random random, BlockPos blockPos) {
        int i = random.nextInt(4) + 6;
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int j = 1 + random.nextInt(2);
        String textValue = text;
        int k = i - j;
        int l = 2 + random.nextInt(2);
        byte b0 = 1;
        if (blockPos.getY() >= 1 && blockPos.getY() + i + 1 <= 256) {
            int k1 = blockPos.getY();

            label153:
            while (k1 <= blockPos.getY() + 1 + i && b0 != 0) {
                int j1;
                {
                    if (k1 - blockPos.getY() < j) {
                        j1 = 0;
                        if (!(textValue != null)) {

                            j1 = l;
                        }
                    } else {

                        j1 = l;
                    }
                }

                int i1 = blockPos.getX() - j1;

                while (true) {
                    if (i1 <= blockPos.getX() + j1 && b0 != 0) {
                        int l1 = blockPos.getZ() - j1;

                        while (l1 <= blockPos.getZ() + j1 && b0 != 0) {
                            {
                                if (k1 >= 0 && k1 < 256) {
                                    Block block =
                                            world.getBlockState(new BlockPos(i1, k1, l1))
                                                    .getBlock();
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

                            l1++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        i1++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    k1++;
                    if (textValue == null) {
                        break label153;
                    }
                    break;
                }
            }

            if (b0 == 0) {
                return false;
            }

            Block blockValue = world.getBlockState(blockPos.offsetDown()).getBlock();
            if ((blockValue == Blocks.grass
                            || blockValue == Blocks.dirt
                            || blockValue == Blocks.farmland)
                    && blockPos.getY() < 256 - i - 1) {
                this.func_175921_a(world, blockPos.offsetDown());
                int j3 = random.nextInt(2);
                int i3 = 1;
                byte b2 = 0;
                int i4 = 0;

                while (i4 <= k) {
                    int k3 = blockPos.getY() + i - i4;
                    int i2 = blockPos.getX() - j3;

                    label104:
                    while (i2 <= blockPos.getX() + j3) {
                        int j2 = i2 - blockPos.getX();
                        int k2 = blockPos.getZ() - j3;

                        while (true) {
                            if (k2 <= blockPos.getZ() + j3) {
                                int l2 = k2 - blockPos.getZ();
                                if (Math.abs(j2) != j3 || Math.abs(l2) != j3 || j3 <= 0) {
                                    BlockPos blockPosValue = new BlockPos(i2, k3, k2);
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

                                k2++;
                                if (textValue != null) {
                                    continue;
                                }
                            }

                            i2++;
                            if (textValue == null) {
                                break label104;
                            }
                            break;
                        }
                    }

                    label86:
                    {
                        if (j3 >= i3) {
                            j3 = b2;
                            b2 = 1;
                            if (++i3 <= l) {
                                break label86;
                            }

                            i3 = l;
                            if (textValue != null) {
                                break label86;
                            }
                        }

                        j3++;
                    }

                    i4++;
                    if (textValue == null) {
                        break;
                    }
                }

                i4 = random.nextInt(3);
                int l3 = 0;

                while (l3 < i - i4) {
                    Block blockValue2 = world.getBlockState(blockPos.offsetUp(l3)).getBlock();
                    if (blockValue2.getMaterial() == Material.AIR
                            || blockValue2.getMaterial() == Material.leaves) {
                        this.func_175905_a(
                                world,
                                blockPos.offsetUp(l3),
                                Blocks.log,
                                BlockPlanksEnumType.SPRUCE.getMetadata());
                    }

                    l3++;
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

    public WorldGenTaiga2(boolean flag) {
        super(flag);
    }
}
