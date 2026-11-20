package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

import java.util.Random;

public class BlockMushroom extends BlockBush implements IGrowable {
    protected BlockMushroom() {
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setTickRandomly(true);
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return true;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (random.nextInt(25) == 0) {
            int j = 5;

            for (java.util.Iterator<?> __recoveryIterator171_1175 =
                            (BlockPos.getAllInBoxMutable(
                                            blockPos.add(-4, -1, -4), blockPos.add(4, 1, 4)))
                                    .iterator();
                    (__recoveryIterator171_1175).hasNext(); ) {
                BlockPos blockPosValue = (BlockPos) (__recoveryIterator171_1175).next();
                if (world.getBlockState(blockPosValue).getBlock() == this) {
                    if (--j <= 0) {
                        return;
                    }
                }

                if (i != 0) {
                    break;
                }
            }

            BlockPos blockPosValue2 =
                    blockPos.add(
                            random.nextInt(3) - 1,
                            random.nextInt(2) - random.nextInt(2),
                            random.nextInt(3) - 1);
            int k = 0;

            while (k < 4) {
                if (world.isAirBlock(blockPosValue2)
                        && this.canBlockStay(world, blockPosValue2, this.getDefaultState())) {
                    blockPos = blockPosValue2;
                }

                blockPosValue2 =
                        blockPos.add(
                                random.nextInt(3) - 1,
                                random.nextInt(2) - random.nextInt(2),
                                random.nextInt(3) - 1);
                k++;
                if (i != 0) {
                    break;
                }
            }

            if (world.isAirBlock(blockPosValue2)
                    && this.canBlockStay(world, blockPosValue2, this.getDefaultState())) {
                world.setBlockState(blockPosValue2, this.getDefaultState(), 2);
            }
        }
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block.isFullBlock();
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return random.nextFloat() < 0.4;
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        this.generateBigMushroom(world, blockPos, blockState, random);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos)
                && this.canBlockStay(world, blockPos, this.getDefaultState());
    }

    public boolean generateBigMushroom(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        world.setBlockToAir(blockPos);
        WorldGenBigMushroom worldGenBigMushroom = null;
        if (this == Blocks.brown_mushroom) {
            worldGenBigMushroom = new WorldGenBigMushroom(0);
        } else if (this == Blocks.red_mushroom) {
            worldGenBigMushroom = new WorldGenBigMushroom(1);
        }

        if (worldGenBigMushroom != null && worldGenBigMushroom.generate(world, random, blockPos)) {
            return true;
        }

        world.setBlockState(blockPos, blockState, 3);
        return false;
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            BlockState blockStateValue = world.getBlockState(blockPos.offsetDown());
            return blockStateValue.getBlock() == Blocks.mycelium
                    ? true
                    : (blockStateValue.getBlock() == Blocks.dirt
                                    && blockStateValue.getValue(BlockDirt.VARIANT)
                                            == BlockDirtDirtType.PODZOL
                            ? true
                            : world.getLight(blockPos) < 13
                                    && this.canPlaceBlockOn(blockStateValue.getBlock()));
        } else {
            return false;
        }
    }
}
