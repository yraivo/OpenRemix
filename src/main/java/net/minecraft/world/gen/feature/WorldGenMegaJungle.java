package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMegaJungle extends WorldGenHugeTrees {
    private boolean isAirOrLeaves(Material material) {
        return material == Material.AIR || material == Material.leaves;
    }

    public WorldGenMegaJungle(
            boolean p171_0, int p171_1, int p171_2, int p171_3, int p171_4) {
        super(p171_0, p171_1, p171_2, p171_3, p171_4);
    }

    private void generateTopLeaves(World world, BlockPos blockPos, int i) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        byte b0 = 2;
        String textValue = text;
        int j = -b0;

        while (j <= 0) {
            this.growLeavesLayer(world, blockPos.offsetUp(j), i + 1 - j);
            j++;
            if (textValue == null) {
                break;
            }
        }
    }

    private void generateTrunkVines(World world, Random random, BlockPos blockPos, int i) {
        if (random.nextInt(3) > 0 && world.isAirBlock(blockPos)) {
            this.func_175905_a(world, blockPos, Blocks.vine, i);
        }
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = this.getTreeHeight(random);
        String textValue = text;
        if (!this.ensureGrowable(world, random, blockPos, i)) {
            return false;
        }

        this.generateTopLeaves(world, blockPos.offsetUp(i), 2);
        int j = blockPos.getY() + i - 2 - random.nextInt(4);

        label71:
        while (j > blockPos.getY() + i / 2) {
            float f = random.nextFloat() * (float) Math.PI * 2.0F;
            int k = blockPos.getX() + (int) (0.5F + MathHelper.cos(f) * 4.0F);
            int l = blockPos.getZ() + (int) (0.5F + MathHelper.sin(f) * 4.0F);
            int i1 = 0;

            while (i1 < 5) {
                k = blockPos.getX() + (int) (1.5F + MathHelper.cos(f) * i1);
                l = blockPos.getZ() + (int) (1.5F + MathHelper.sin(f) * i1);
                this.func_175905_a(world, new BlockPos(k, j - 3 + i1 / 2, l), Blocks.log, this.woodMetadata);
                i1++;
                if (textValue == null) {
                    break;
                }
            }

            i1 = 1 + random.nextInt(2);
            int j1 = j;
            int k1 = j - i1;

            while (true) {
                if (k1 <= j1) {
                    int l1 = k1 - j1;
                    this.growLeavesLayerStrict(world, new BlockPos(k, k1, l), 1 - l1);
                    k1++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j -= 2 + random.nextInt(4);
                if (textValue == null) {
                    break label71;
                }
                break;
            }
        }

        j = 0;

        while (j < i) {
            BlockPos blockPosValue = blockPos.offsetUp(j);
            if (this.isAirOrLeaves(
                    world.getBlockState(blockPosValue).getBlock().getMaterial())) {
                this.func_175905_a(world, blockPosValue, Blocks.log, this.woodMetadata);
                if (j > 0) {
                    this.generateTrunkVines(
                            world, random, blockPosValue.offsetWest(), BlockVine.field_176275_S);
                    this.generateTrunkVines(
                            world, random, blockPosValue.offsetNorth(), BlockVine.field_176272_Q);
                }
            }

            if (j < i - 1) {
                BlockPos blockPosValue2 = blockPosValue.offsetEast();
                if (this.isAirOrLeaves(
                        world.getBlockState(blockPosValue2).getBlock().getMaterial())) {
                    this.func_175905_a(world, blockPosValue2, Blocks.log, this.woodMetadata);
                    if (j > 0) {
                        this.generateTrunkVines(
                                world,
                                random,
                                blockPosValue2.offsetEast(),
                                BlockVine.field_176271_T);
                        this.generateTrunkVines(
                                world,
                                random,
                                blockPosValue2.offsetNorth(),
                                BlockVine.field_176272_Q);
                    }
                }

                BlockPos blockPosValue3 = blockPosValue.offsetSouth().offsetEast();
                if (this.isAirOrLeaves(
                        world.getBlockState(blockPosValue3).getBlock().getMaterial())) {
                    this.func_175905_a(world, blockPosValue3, Blocks.log, this.woodMetadata);
                    if (j > 0) {
                        this.generateTrunkVines(
                                world,
                                random,
                                blockPosValue3.offsetEast(),
                                BlockVine.field_176271_T);
                        this.generateTrunkVines(
                                world,
                                random,
                                blockPosValue3.offsetSouth(),
                                BlockVine.field_176276_R);
                    }
                }

                BlockPos blockPosValue4 = blockPosValue.offsetSouth();
                if (this.isAirOrLeaves(
                        world.getBlockState(blockPosValue4).getBlock().getMaterial())) {
                    this.func_175905_a(world, blockPosValue4, Blocks.log, this.woodMetadata);
                    if (j > 0) {
                        this.generateTrunkVines(
                                world,
                                random,
                                blockPosValue4.offsetWest(),
                                BlockVine.field_176275_S);
                        this.generateTrunkVines(
                                world,
                                random,
                                blockPosValue4.offsetSouth(),
                                BlockVine.field_176276_R);
                    }
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
