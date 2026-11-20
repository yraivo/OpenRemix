package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMegaPineTree extends WorldGenHugeTrees {
    private boolean useBaseHeight;

    public void generatePodzol(World world, Random random, BlockPos blockPos) {
        this.surroundWithPodzol(world, blockPos.offsetWest().offsetNorth());
        String text = WorldGenerator.method_c60693f457aa70be7160();
        this.surroundWithPodzol(world, blockPos.offsetEast(2).offsetNorth());
        String textValue = text;
        this.surroundWithPodzol(world, blockPos.offsetWest().offsetSouth(2));
        this.surroundWithPodzol(world, blockPos.offsetEast(2).offsetSouth(2));
        int i = 0;

        while (i < 5) {
            int j = random.nextInt(64);
            int k = j % 8;
            int l = j / 8;
            if (k == 0 || k == 7 || l == 0 || l == 7) {
                this.surroundWithPodzol(world, blockPos.add(-3 + k, 0, -3 + l));
            }

            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    private void setPodzol(World world, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 2;
        String textValue = text;

        while (i >= -3) {
            BlockPos blockPosValue = blockPos.offsetUp(i);
            Block block = world.getBlockState(blockPosValue).getBlock();
            if (block == Blocks.grass || block == Blocks.dirt) {
                this.func_175905_a(
                        world, blockPosValue, Blocks.dirt, BlockDirtDirtType.PODZOL.getMetadata());
                if (textValue != null) {
                    break;
                }
            }

            if (block.getMaterial() != Material.AIR && i < 0 && textValue != null) {
                break;
            }

            i--;
            if (textValue == null) {
                break;
            }
        }
    }

    private void surroundWithPodzol(World world, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = -2;
        String textValue = text;

        while (i <= 2) {
            int j = -2;

            while (true) {
                if (j <= 2) {
                    if (Math.abs(i) != 2 || Math.abs(j) != 2) {
                        this.setPodzol(world, blockPos.add(i, 0, j));
                    }

                    j++;
                    if (textValue != null) {
                        continue;
                    }
                }

                i++;
                if (textValue == null) {
                    return;
                }
                break;
            }
        }
    }

    public WorldGenMegaPineTree(boolean flag, boolean flag1) {
        super(
                flag,
                13,
                15,
                BlockPlanksEnumType.SPRUCE.getMetadata(),
                BlockPlanksEnumType.SPRUCE.getMetadata());
        this.useBaseHeight = flag1;
    }

    private void generateLeaves(
            World world, int i, int j, int k, int l, Random random) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i1 = random.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
        int j1 = 0;
        int k1 = k - i1;

        while (k1 <= k) {
            int l1 = k - k1;
            int i2 = l + MathHelper.floor((float) l1 / i1 * 3.5F);
            this.growLeavesLayer(
                    world,
                    new BlockPos(i, k1, j),
                    i2 + (l1 > 0 && i2 == j1 && (k1 & 1) == 0 ? 1 : 0));
            j1 = i2;
            k1++;
            if (text == null) {
                break;
            }
        }
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = this.getTreeHeight(random);
        String textValue = text;
        if (!this.ensureGrowable(world, random, blockPos, i)) {
            return false;
        }

        this.generateLeaves(
                world, blockPos.getX(), blockPos.getZ(), blockPos.getY() + i, 0, random);
        int j = 0;

        while (j < i) {
            Block block = world.getBlockState(blockPos.offsetUp(j)).getBlock();
            if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.leaves) {
                this.func_175905_a(world, blockPos.offsetUp(j), Blocks.log, this.woodMetadata);
            }

            if (j < i - 1) {
                block = world.getBlockState(blockPos.add(1, j, 0)).getBlock();
                if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.leaves) {
                    this.func_175905_a(world, blockPos.add(1, j, 0), Blocks.log, this.woodMetadata);
                }

                block = world.getBlockState(blockPos.add(1, j, 1)).getBlock();
                if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.leaves) {
                    this.func_175905_a(world, blockPos.add(1, j, 1), Blocks.log, this.woodMetadata);
                }

                block = world.getBlockState(blockPos.add(0, j, 1)).getBlock();
                if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.leaves) {
                    this.func_175905_a(world, blockPos.add(0, j, 1), Blocks.log, this.woodMetadata);
                }
            }

            j++;
            if (textValue == null) {
                break;
            }
        }

        return true;
    }
}
