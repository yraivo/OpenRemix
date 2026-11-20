package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSpikes extends WorldGenerator {
    private Block baseBlockRequired;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        if (world.isAirBlock(blockPos)
                && world.getBlockState(blockPos.offsetDown()).getBlock()
                        == this.baseBlockRequired) {
            int i = random.nextInt(32) + 6;
            int j = random.nextInt(4) + 1;
            int k = blockPos.getX() - j;

            label66:
            while (k <= blockPos.getX() + j) {
                int l = blockPos.getZ() - j;

                while (true) {
                    if (l <= blockPos.getZ() + j) {
                        int i1 = k - blockPos.getX();
                        int j1 = l - blockPos.getZ();
                        if (i1 * i1 + j1 * j1 <= j * j + 1
                                && world.getBlockState(new BlockPos(k, blockPos.getY() - 1, l))
                                                .getBlock()
                                        != this.baseBlockRequired) {
                            return false;
                        }

                        l++;
                        if (text != null) {
                            continue;
                        }
                    }

                    k++;
                    if (text == null) {
                        break label66;
                    }
                    break;
                }
            }

            k = blockPos.getY();

            label52:
            while (k < blockPos.getY() + i && k < 256) {
                int l1 = blockPos.getX() - j;

                while (true) {
                    if (l1 <= blockPos.getX() + j) {
                        int i2 = blockPos.getZ() - j;

                        while (i2 <= blockPos.getZ() + j) {
                            int j2 = l1 - blockPos.getX();
                            int k1 = i2 - blockPos.getZ();
                            if (j2 * j2 + k1 * k1 <= j * j + 1) {
                                world.setBlockState(
                                        new BlockPos(l1, k, i2),
                                        Blocks.obsidian.getDefaultState(),
                                        2);
                            }

                            i2++;
                            if (text == null) {
                                break;
                            }
                        }

                        l1++;
                        if (text != null) {
                            continue;
                        }
                    }

                    k++;
                    if (text == null) {
                        break label52;
                    }
                    break;
                }
            }

            EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal(world);
            entityEnderCrystal.setLocationAndAngles(
                    blockPos.getX() + 0.5F,
                    blockPos.getY() + i,
                    blockPos.getZ() + 0.5F,
                    random.nextFloat() * 360.0F,
                    0.0F);
            world.spawnEntityInWorld(entityEnderCrystal);
            world.setBlockState(blockPos.offsetUp(i), Blocks.bedrock.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

    public WorldGenSpikes(Block aUVValue) {
        this.baseBlockRequired = aUVValue;
    }
}
