package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class BlockDynamicLiquid extends BlockLiquid {
    int adjacentSourceBlocks;

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int l1 = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = (Integer) blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL);
        int i = l1;
        byte b0 = 1;
        if (this.blockMaterial == Material.lava && !world.provider.doesWaterVaporize()) {
            b0 = 2;
        }

        label131:
        {
            int k = this.tickRate(world);
            if (j > 0) {
                int i1 = -100;
                this.adjacentSourceBlocks = 0;

                for (java.util.Iterator<?> __recoveryIterator171_1039 =
                                (FacingPlane.HORIZONTAL).iterator();
                        (__recoveryIterator171_1039).hasNext(); ) {
                    Facing facing = (Facing) (__recoveryIterator171_1039).next();
                    i1 = this.checkAdjacentBlock(world, blockPos.offset(facing), i1);
                    if (i == 0) {
                        break;
                    }
                }

                int k1 = i1 + b0;
                if (k1 >= 8 || i1 < 0) {
                    k1 = -1;
                }

                if (this.getLevel(world, blockPos.offsetUp()) >= 0) {
                    int l = this.getLevel(world, blockPos.offsetUp());
                    if (l >= 8) {
                        k1 = l;
                        if (!(i != 0)) {

                            k1 = l + 8;
                        }
                    } else {

                        k1 = l + 8;
                    }
                }

                if (this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.water) {
                    BlockState blockStateValue = world.getBlockState(blockPos.offsetDown());
                    if (blockStateValue.getBlock().getMaterial().isSolid()) {
                        k1 = 0;
                        if (!(i != 0)) {

                            if (blockStateValue.getBlock().getMaterial() == this.blockMaterial
                                    && (Integer) blockStateValue.getValue(net.minecraft.block.BlockLiquid.LEVEL) == 0) {
                                k1 = 0;
                            }
                        }
                    } else {

                        if (blockStateValue.getBlock().getMaterial() == this.blockMaterial
                                && (Integer) blockStateValue.getValue(net.minecraft.block.BlockLiquid.LEVEL) == 0) {
                            k1 = 0;
                        }
                    }
                }

                if (this.blockMaterial == Material.lava
                        && j < 8
                        && k1 < 8
                        && k1 > j
                        && random.nextInt(4) != 0) {
                    k *= 4;
                }

                label138:
                {
                    if (k1 == j) {
                        this.placeStaticBlock(world, blockPos, blockState);
                        if (i != 0) {
                            break label138;
                        }
                    }

                    j = k1;
                    if (k1 < 0) {
                        world.setBlockToAir(blockPos);
                        if (i != 0) {
                            break label138;
                        }
                    }

                    blockState = blockState.withProperty(net.minecraft.block.BlockLiquid.LEVEL, k1);
                    world.setBlockState(blockPos, blockState, 2);
                    world.scheduleUpdate(blockPos, this, k);
                    world.notifyNeighborsOfStateChange(blockPos, this);
                }

                if (i != 0) {
                    break label131;
                }
            }

            this.placeStaticBlock(world, blockPos, blockState);
        }

        BlockState blockStateValue2 = world.getBlockState(blockPos.offsetDown());
        if (this.canFlowInto(world, blockPos.offsetDown(), blockStateValue2)) {
            if (this.blockMaterial == Material.lava
                    && world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial()
                            == Material.water) {
                world.setBlockState(blockPos.offsetDown(), Blocks.stone.getDefaultState());
                this.triggerMixEffects(world, blockPos.offsetDown());
                return;
            }

            if (j >= 8) {
                this.tryFlowInto(world, blockPos.offsetDown(), blockStateValue2, j);
                if (i != 0) {
                    return;
                }
            }

            this.tryFlowInto(world, blockPos.offsetDown(), blockStateValue2, j + 8);
            if (i != 0) {
                return;
            }
        }

        if (j >= 0 && (j == 0 || this.isBlocked(world, blockPos.offsetDown(), blockStateValue2))) {
            Set set = this.getPossibleFlowDirections(world, blockPos);
            int j1 = j + b0;
            if (j >= 8) {
                j1 = 1;
            }

            if (j1 >= 8) {
                return;
            }

            for (java.util.Iterator<?> __recoveryIterator171_5706 = (set).iterator();
                    (__recoveryIterator171_5706).hasNext(); ) {
                Facing facingValue = (Facing) (__recoveryIterator171_5706).next();
                this.tryFlowInto(
                        world,
                        blockPos.offset(facingValue),
                        world.getBlockState(blockPos.offset(facingValue)),
                        j1);
                if (i == 0) {
                    break;
                }
            }
        }
    }

    protected BlockDynamicLiquid(Material material) {
        super(material);
    }

    private void tryFlowInto(
            World world, BlockPos blockPos, BlockState blockState, int i) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.canFlowInto(world, blockPos, blockState)) {
            if (blockState.getBlock() != Blocks.air) {
                if (this.blockMaterial == Material.lava) {
                    this.triggerMixEffects(world, blockPos);
                    if (!(j != 0)) {

                        blockState.getBlock().dropBlockAsItem(world, blockPos, blockState, 0);
                    }
                } else {

                    blockState.getBlock().dropBlockAsItem(world, blockPos, blockState, 0);
                }
            }

            world.setBlockState(
                    blockPos, this.getDefaultState().withProperty(net.minecraft.block.BlockLiquid.LEVEL, i), 3);
        }
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.checkForMixing(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }

    protected int checkAdjacentBlock(World world, BlockPos blockPos, int p171_2) {
        int j = this.getLevel(world, blockPos);
        if (j < 0) {
            return p171_2;
        }

        if (j == 0) {
            this.adjacentSourceBlocks++;
        }

        if (j >= 8) {
            j = 0;
        }

        return p171_2 >= 0 && j >= p171_2 ? p171_2 : j;
    }

    private Set getPossibleFlowDirections(World world, BlockPos blockPos) {
        int j = 1000;
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        EnumSet enumset = EnumSet.noneOf(Facing.class);
        Iterator iterator = FacingPlane.HORIZONTAL.iterator();
        int i = l;

        while (iterator.hasNext()) {
            Facing facing = (Facing) iterator.next();
            BlockPos blockPosValue = blockPos.offset(facing);
            BlockState blockState = world.getBlockState(blockPosValue);
            if (!this.isBlocked(world, blockPosValue, blockState)
                    && (blockState.getBlock().getMaterial() != this.blockMaterial
                            || (Integer) blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL) > 0)) {
                int k;
                {
                    if (this.isBlocked(
                            world,
                            blockPosValue.offsetDown(),
                            world.getBlockState(blockPosValue.offsetDown()))) {
                        k =
                                this.method_e4bb1467c46880eb7677(
                                        world, blockPosValue, 1, facing.getOpposite());
                        if (!(i == 0)) {

                            k = 0;
                        }
                    } else {

                        k = 0;
                    }
                }

                if (k < j) {
                    enumset.clear();
                }

                if (k <= j) {
                    enumset.add(facing);
                    j = k;
                }
            }

            if (i != 0) {
                break;
            }
        }

        return enumset;
    }

    private void placeStaticBlock(World world, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(
                blockPos,
                getStaticLiquidForMaterial(this.blockMaterial)
                        .getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockLiquid.LEVEL,
                                blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL)),
                2);
    }

    private boolean canFlowInto(World world, BlockPos blockPos, BlockState blockState) {
        Material material = blockState.getBlock().getMaterial();
        return material != this.blockMaterial
                && material != Material.lava
                && !this.isBlocked(world, blockPos, blockState);
    }

    private int method_e4bb1467c46880eb7677(World world, BlockPos blockPos, int i, Facing facing) {
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int k = 1000;
        int j = i1;

        for (java.util.Iterator<?> __recoveryIterator171_11968 =
                        (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_11968).hasNext(); ) {
            Facing facingValue = (Facing) (__recoveryIterator171_11968).next();
            if (facingValue != facing) {
                BlockPos blockPosValue = blockPos.offset(facingValue);
                BlockState blockState = world.getBlockState(blockPosValue);
                if (!this.isBlocked(world, blockPosValue, blockState)
                        && (blockState.getBlock().getMaterial() != this.blockMaterial
                                || (Integer) blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL) > 0)) {
                    if (!this.isBlocked(world, blockPosValue.offsetDown(), blockState)) {
                        return i;
                    }

                    if (i < 4) {
                        int l =
                                this.method_e4bb1467c46880eb7677(
                                        world, blockPosValue, i + 1, facingValue.getOpposite());
                        if (l < k) {
                            k = l;
                        }
                    }
                }
            }

            if (j != 0) {
                break;
            }
        }

        return k;
    }

    private boolean isBlocked(World world, BlockPos blockPos, BlockState blockState) {
        Block block = world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockDoor)
                        && block != Blocks.standing_sign
                        && block != Blocks.ladder
                        && block != Blocks.reeds
                ? (block.blockMaterial == Material.portal
                        ? true
                        : block.blockMaterial.blocksMovement())
                : true;
    }
}
