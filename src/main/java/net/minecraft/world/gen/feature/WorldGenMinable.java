package net.minecraft.world.gen.feature;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

import java.util.Random;

public class WorldGenMinable extends WorldGenerator {
    private int numberOfBlocks;
    private BlockState oreBlock;
    private Predicate predicate;

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        float f = random.nextFloat() * (float) Math.PI;
        double d0 = blockPos.getX() + 8 + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double d1 = blockPos.getX() + 8 - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double d2 = blockPos.getZ() + 8 + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double d3 = blockPos.getZ() + 8 - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        String textValue = text;
        double d4 = blockPos.getY() + random.nextInt(3) - 2;
        double d5 = blockPos.getY() + random.nextInt(3) - 2;
        int i = 0;

        while (i < this.numberOfBlocks) {
            float f1 = (float) i / this.numberOfBlocks;
            double d6 = d0 + (d1 - d0) * f1;
            double d7 = d4 + (d5 - d4) * f1;
            double d8 = d2 + (d3 - d2) * f1;
            double d9 = random.nextDouble() * this.numberOfBlocks / 16.0;
            double d10 = (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0;
            double d11 = (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0;
            int j = MathHelper.floor(d6 - d10 / 2.0);
            int k = MathHelper.floor(d7 - d11 / 2.0);
            int l = MathHelper.floor(d8 - d10 / 2.0);
            int i1 = MathHelper.floor(d6 + d10 / 2.0);
            int j1 = MathHelper.floor(d7 + d11 / 2.0);
            int k1 = MathHelper.floor(d8 + d10 / 2.0);
            int l1 = j;

            while (true) {
                if (l1 <= i1) {
                    double d12 = (l1 + 0.5 - d6) / (d10 / 2.0);
                    if (d12 * d12 < 1.0) {
                        int i2 = k;

                        while (i2 <= j1) {
                            double d13 = (i2 + 0.5 - d7) / (d11 / 2.0);
                            if (d12 * d12 + d13 * d13 < 1.0) {
                                int j2 = l;

                                while (j2 <= k1) {
                                    double d14 = (j2 + 0.5 - d8) / (d10 / 2.0);
                                    if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0) {
                                        BlockPos blockPosValue = new BlockPos(l1, i2, j2);
                                        if (this.predicate.apply(
                                                world.getBlockState(blockPosValue))) {
                                            world.setBlockState(blockPosValue, this.oreBlock, 2);
                                        }
                                    }

                                    j2++;
                                    if (textValue == null) {
                                        Entity.setLegacyGlobalFlowMarker(new String[1]);
                                        break;
                                    }
                                }
                            }

                            i2++;
                            if (textValue == null) {
                                break;
                            }
                        }
                    }

                    l1++;
                    if (textValue != null) {
                        continue;
                    }
                }

                i++;
                if (textValue == null) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    public WorldGenMinable(BlockState blockState, int i) {
        this(blockState, i, BlockHelper.forBlock(Blocks.stone));
    }

    public WorldGenMinable(BlockState aViValue, int numberOfBlocksValue, Predicate predicate) {
        this.oreBlock = aViValue;
        this.numberOfBlocks = numberOfBlocksValue;
        this.predicate = predicate;
    }
}
