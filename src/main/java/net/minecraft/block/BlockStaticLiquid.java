package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockStaticLiquid extends BlockLiquid {

    private static String ET;

    protected boolean isSurroundingBlockFlammable(World world, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing[] facingArray = Facing.values();
        int i = j;

        for (Facing facing : facingArray) {
            if (this.getCanBlockBurn(world, blockPos.offset(facing))) {
                return true;
            }

            if (i == 0) {
                break;
            }
        }

        return false;
    }

    private void updateLiquid(World world, BlockPos blockPos, BlockState blockState) {
        BlockDynamicLiquid blockDynamicLiquid = getDynamicLiquidForMaterial(this.blockMaterial);
        world.setBlockState(
                blockPos,
                blockDynamicLiquid
                        .getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockLiquid.LEVEL,
                                blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL)),
                2);
        world.scheduleUpdate(blockPos, blockDynamicLiquid, this.tickRate(world));
    }

    private boolean getCanBlockBurn(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().getMaterial().getCanBurn();
    }

    // region String decoding support

    // endregion

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (this.blockMaterial == Material.lava
                && world.getGameRules().getGameRuleBooleanValue(ET)) {
            int j = random.nextInt(3);
            if (j > 0) {
                BlockPos blockPosValue = blockPos;
                int k = 0;

                while (k < j) {
                    blockPosValue =
                            blockPosValue.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
                    Block block = world.getBlockState(blockPosValue).getBlock();
                    if (block.blockMaterial == Material.AIR) {
                        if (this.isSurroundingBlockFlammable(world, blockPosValue)) {
                            world.setBlockState(blockPosValue, Blocks.fire.getDefaultState());
                            return;
                        }
                    } else if (block.blockMaterial.blocksMovement()) {
                        return;
                    }

                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                if (i == 0) {
                    return;
                }
            }

            int l = 0;

            while (l < 3) {
                BlockPos blockPosValue2 =
                        blockPos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.isAirBlock(blockPosValue2.offsetUp())
                        && this.getCanBlockBurn(world, blockPosValue2)) {
                    world.setBlockState(blockPosValue2.offsetUp(), Blocks.fire.getDefaultState());
                }

                l++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    protected BlockStaticLiquid(Material material) {
        super(material);
        this.setTickRandomly(false);
        if (material == Material.lava) {
            this.setTickRandomly(true);
        }
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!this.checkForMixing(world, blockPos, blockState)) {
            this.updateLiquid(world, blockPos, blockState);
        }
    }

    static {
        ET = "doFireTick";
    }
}
