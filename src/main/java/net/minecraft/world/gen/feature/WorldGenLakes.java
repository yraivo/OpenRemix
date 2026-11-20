package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator {
    private Block block;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        blockPos = blockPos.add(-8, 0, -8);
        String textValue = text;

        while (blockPos.getY() > 5 && world.isAirBlock(blockPos)) {
            blockPos = blockPos.offsetDown();
            if (textValue == null) {
                break;
            }
        }

        if (blockPos.getY() <= 4) {
            return false;
        }

        blockPos = blockPos.offsetDown(4);
        boolean[] aboolean = new boolean[2048];
        int i = random.nextInt(4) + 4;
        int j = 0;

        label328:
        while (j < i) {
            double d0 = random.nextDouble() * 6.0 + 3.0;
            double d1 = random.nextDouble() * 4.0 + 2.0;
            double d2 = random.nextDouble() * 6.0 + 3.0;
            double d3 = random.nextDouble() * (16.0 - d0 - 2.0) + 1.0 + d0 / 2.0;
            double d4 = random.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
            double d5 = random.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;
            int l = 1;

            while (true) {
                if (l < 15) {
                    int i1 = 1;

                    while (i1 < 15) {
                        int j1 = 1;

                        while (j1 < 7) {
                            double d6 = (l - d3) / (d0 / 2.0);
                            double d7 = (j1 - d4) / (d1 / 2.0);
                            double d8 = (i1 - d5) / (d2 / 2.0);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0) {
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }

                            j1++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        i1++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    l++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    break label328;
                }
                break;
            }
        }

        j = 0;

        label300:
        while (j < 16) {
            int k = 0;

            while (true) {
                if (k < 16) {
                    int k1 = 0;

                    while (k1 < 8) {
                        int k3 =
                                aboolean[(j * 16 + k) * 8 + k1]
                                                || (j >= 15
                                                                || !aboolean[
                                                                        ((j + 1) * 16 + k) * 8
                                                                                + k1])
                                                        && (j <= 0
                                                                || !aboolean[
                                                                        ((j - 1) * 16 + k) * 8
                                                                                + k1])
                                                        && (k >= 15
                                                                || !aboolean[
                                                                        (j * 16 + k + 1) * 8 + k1])
                                                        && (k <= 0
                                                                || !aboolean[
                                                                        (j * 16 + (k - 1)) * 8
                                                                                + k1])
                                                        && (k1 >= 7
                                                                || !aboolean[
                                                                        (j * 16 + k) * 8 + k1 + 1])
                                                        && (k1 <= 0
                                                                || !aboolean[
                                                                        (j * 16 + k) * 8
                                                                                + (k1 - 1)])
                                        ? 0
                                        : 1;
                        if (k3 != 0) {
                            Material material =
                                    world.getBlockState(blockPos.add(j, k1, k))
                                            .getBlock()
                                            .getMaterial();
                            if (k1 >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (k1 < 4
                                    && !material.isSolid()
                                    && world.getBlockState(blockPos.add(j, k1, k)).getBlock()
                                            != this.block) {
                                return false;
                            }
                        }

                        k1++;
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
                    break label300;
                }
                break;
            }
        }

        j = 0;

        label255:
        while (j < 16) {
            int k2 = 0;

            while (true) {
                if (k2 < 16) {
                    int l1 = 0;

                    while (l1 < 8) {
                        if (aboolean[(j * 16 + k2) * 8 + l1]) {
                            world.setBlockState(
                                    blockPos.add(j, l1, k2),
                                    l1 >= 4
                                            ? Blocks.air.getDefaultState()
                                            : this.block.getDefaultState(),
                                    2);
                        }

                        l1++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    k2++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    break label255;
                }
                break;
            }
        }

        j = 0;

        label234:
        while (j < 16) {
            int l2 = 0;

            while (true) {
                if (l2 < 16) {
                    int i2 = 4;

                    while (i2 < 8) {
                        if (aboolean[(j * 16 + l2) * 8 + i2]) {
                            BlockPos blockPosValue = blockPos.add(j, i2 - 1, l2);
                            if (world.getBlockState(blockPosValue).getBlock() == Blocks.dirt
                                    && world.getLightFor(EnumSkyBlock.SKY, blockPos.add(j, i2, l2))
                                            > 0) {
                                BiomeGenBase biomeGenForCoords =
                                        world.getBiomeGenForCoords(blockPosValue);
                                if (biomeGenForCoords.topBlock.getBlock() == Blocks.mycelium) {
                                    world.setBlockState(
                                            blockPosValue, Blocks.mycelium.getDefaultState(), 2);
                                    if (!(textValue != null)) {

                                        world.setBlockState(
                                                blockPosValue, Blocks.grass.getDefaultState(), 2);
                                    }
                                } else {

                                    world.setBlockState(
                                            blockPosValue, Blocks.grass.getDefaultState(), 2);
                                }
                            }
                        }

                        i2++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    l2++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    break label234;
                }
                break;
            }
        }

        if (this.block.getMaterial() == Material.lava) {
            j = 0;

            while (j < 16) {
                int i3 = 0;

                while (i3 < 16) {
                    int j2 = 0;

                    while (j2 < 8) {
                        int l3 =
                                aboolean[(j * 16 + i3) * 8 + j2]
                                                || (j >= 15
                                                                || !aboolean[
                                                                        ((j + 1) * 16 + i3) * 8
                                                                                + j2])
                                                        && (j <= 0
                                                                || !aboolean[
                                                                        ((j - 1) * 16 + i3) * 8
                                                                                + j2])
                                                        && (i3 >= 15
                                                                || !aboolean[
                                                                        (j * 16 + i3 + 1) * 8 + j2])
                                                        && (i3 <= 0
                                                                || !aboolean[
                                                                        (j * 16 + (i3 - 1)) * 8
                                                                                + j2])
                                                        && (j2 >= 7
                                                                || !aboolean[
                                                                        (j * 16 + i3) * 8 + j2 + 1])
                                                        && (j2 <= 0
                                                                || !aboolean[
                                                                        (j * 16 + i3) * 8
                                                                                + (j2 - 1)])
                                        ? 0
                                        : 1;
                        if (l3 != 0
                                && (j2 < 4 || random.nextInt(2) != 0)
                                && world.getBlockState(blockPos.add(j, j2, i3))
                                        .getBlock()
                                        .getMaterial()
                                        .isSolid()) {
                            world.setBlockState(
                                    blockPos.add(j, j2, i3), Blocks.stone.getDefaultState(), 2);
                        }

                        j2++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    i3++;
                    if (textValue == null) {
                        break;
                    }
                }

                j++;
                if (textValue == null) {
                    break;
                }
            }
        }

        if (this.block.getMaterial() == Material.water) {
            j = 0;

            while (j < 16) {
                int j3 = 0;

                while (j3 < 16) {
                    byte b0 = 4;
                    if (world.canBlockFreezeWater(blockPos.add(j, b0, j3))) {
                        world.setBlockState(
                                blockPos.add(j, b0, j3), Blocks.ice.getDefaultState(), 2);
                    }

                    j3++;
                    if (textValue == null) {
                        break;
                    }
                }

                j++;
                if (textValue == null) {
                    break;
                }
            }
        }

        return true;
    }

    public WorldGenLakes(Block aUVValue) {
        this.block = aUVValue;
    }
}
