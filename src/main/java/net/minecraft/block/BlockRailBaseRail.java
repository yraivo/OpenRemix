package net.minecraft.block;

import com.google.common.collect.Lists;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.List;

public class BlockRailBaseRail {
    private BlockState state;
    private List field_150657_g;
    private World world;
    BlockRailBase field_150662_a;
    private BlockRailBase block;
    private boolean isPowered;
    private BlockPos pos;

    private BlockRailBaseRail findRailAt(BlockPos blockPos) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (BlockRailBase.isRailBlock(blockState)) {
            BlockRailBase aWIValue = this.field_150662_a;
            this.field_150662_a.getClass();
            return new BlockRailBaseRail(aWIValue, this.world, blockPos, blockState);
        }

        BlockPos blockPosValue = blockPos.offsetUp();
        blockState = this.world.getBlockState(blockPosValue);
        if (BlockRailBase.isRailBlock(blockState)) {
            BlockRailBase aWIValue2 = this.field_150662_a;
            this.field_150662_a.getClass();
            return new BlockRailBaseRail(aWIValue2, this.world, blockPosValue, blockState);
        }

        blockPosValue = blockPos.offsetDown();
        blockState = this.world.getBlockState(blockPosValue);
        BlockRailBaseRail blockRailBaseRail;
        if (BlockRailBase.isRailBlock(blockState)) {
            BlockRailBase aWIValue3 = this.field_150662_a;
            this.field_150662_a.getClass();
            blockRailBaseRail =
                    new BlockRailBaseRail(aWIValue3, this.world, blockPosValue, blockState);
        } else {
            blockRailBaseRail = null;
        }

        return blockRailBaseRail;
    }

    public BlockRailBaseRail(
            BlockRailBase aWIValue, World world, BlockPos blockPos, BlockState aWHValue) {
        this.field_150662_a = aWIValue;
        this.field_150657_g = Lists.newArrayList();
        this.world = world;
        this.pos = blockPos;
        this.state = aWHValue;
        this.block = (BlockRailBase) aWHValue.getBlock();
        BlockRailBaseEnumRailDirection value =
                (BlockRailBaseEnumRailDirection) aWHValue.getValue(aWIValue.getShapeProperty());
        this.isPowered = this.block.isPowered;
        this.method_17ab15f27456aff48b83(value);
    }

    private boolean func_180363_c(BlockPos blockPos) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = 0;
        int i = k;

        while (j < this.field_150657_g.size()) {
            BlockPos blockPosValue = (BlockPos) this.field_150657_g.get(j);
            if (blockPosValue.getX() == blockPos.getX()
                    && blockPosValue.getZ() == blockPos.getZ()) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public BlockRailBaseRail method_c806dab0a04fb5ea83bb(boolean flag, boolean flag1) {
        BlockPos blockPos = this.pos.offsetNorth();
        BlockPos blockPosValue = this.pos.offsetSouth();
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockPos blockPosValue2 = this.pos.offsetWest();
        BlockPos blockPosValue3 = this.pos.offsetEast();
        boolean flag2 = this.func_180361_d(blockPos);
        boolean flag3 = this.func_180361_d(blockPosValue);
        int i = k;
        boolean flag4 = this.func_180361_d(blockPosValue2);
        boolean flag5 = this.func_180361_d(blockPosValue3);
        BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection = null;
        if ((flag2 || flag3) && !flag4 && !flag5) {
            blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_SOUTH;
        }

        if ((flag4 || flag5) && !flag2 && !flag3) {
            blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.EAST_WEST;
        }

        if (!this.isPowered) {
            if (flag3 && flag5 && !flag2 && !flag4) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_EAST;
            }

            if (flag3 && flag4 && !flag2 && !flag5) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_WEST;
            }

            if (flag2 && flag4 && !flag3 && !flag5) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_WEST;
            }

            if (flag2 && flag5 && !flag3 && !flag4) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_EAST;
            }
        }

        if (blockRailBaseEnumRailDirection == null) {
            if (flag2 || flag3) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_SOUTH;
            }

            if (flag4 || flag5) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.EAST_WEST;
            }

            label167:
            if (!this.isPowered) {
                if (flag) {
                    if (flag3 && flag5) {
                        blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_EAST;
                    }

                    if (flag4 && flag3) {
                        blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_WEST;
                    }

                    if (flag5 && flag2) {
                        blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_EAST;
                    }

                    if (!flag2 || !flag4) {
                        break label167;
                    }

                    blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_WEST;
                    if (i == 0) {
                        break label167;
                    }
                }

                if (flag2 && flag4) {
                    blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_WEST;
                }

                if (flag5 && flag2) {
                    blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_EAST;
                }

                if (flag4 && flag3) {
                    blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_WEST;
                }

                if (flag3 && flag5) {
                    blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_EAST;
                }
            }
        }

        if (blockRailBaseEnumRailDirection == BlockRailBaseEnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.isRailBlock(this.world, blockPos.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_NORTH;
            }

            if (BlockRailBase.isRailBlock(this.world, blockPosValue.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_SOUTH;
            }
        }

        if (blockRailBaseEnumRailDirection == BlockRailBaseEnumRailDirection.EAST_WEST) {
            if (BlockRailBase.isRailBlock(this.world, blockPosValue3.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_EAST;
            }

            if (BlockRailBase.isRailBlock(this.world, blockPosValue2.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_WEST;
            }
        }

        if (blockRailBaseEnumRailDirection == null) {
            blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_SOUTH;
        }

        this.method_17ab15f27456aff48b83(blockRailBaseEnumRailDirection);
        this.state =
                this.state.withProperty(
                        this.block.getShapeProperty(), blockRailBaseEnumRailDirection);
        if (flag1 || this.world.getBlockState(this.pos) != this.state) {
            this.world.setBlockState(this.pos, this.state, 3);
            int j = 0;

            while (j < this.field_150657_g.size()) {
                BlockRailBaseRail blockRailBaseRail =
                        this.findRailAt((BlockPos) this.field_150657_g.get(j));
                if (blockRailBaseRail != null) {
                    blockRailBaseRail.func_150651_b();
                    if (blockRailBaseRail.func_150649_b(this)) {
                        blockRailBaseRail.func_150645_c(this);
                    }
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        return this;
    }

    protected int countAdjacentRails() {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = 0;
        int i = k;

        for (java.util.Iterator<?> __recoveryIterator171_9927 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_9927).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_9927).next();
            if (this.hasRailAt(this.pos.offset(facing))) {
                j++;
            }

            if (i == 0) {
                break;
            }
        }

        return j;
    }

    private void func_150651_b() {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = 0;
        int i = k;

        while (j < this.field_150657_g.size()) {
            {
                BlockRailBaseRail blockRailBaseRail =
                        this.findRailAt((BlockPos) this.field_150657_g.get(j));
                if (blockRailBaseRail != null && blockRailBaseRail.func_150653_a(this)) {
                    this.field_150657_g.set(j, blockRailBaseRail.pos);
                    if (!(i == 0)) {

                        this.field_150657_g.remove(j--);
                    }
                } else {

                    this.field_150657_g.remove(j--);
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }
    }

    private boolean func_180361_d(BlockPos blockPos) {
        BlockRailBaseRail blockRailBaseRail = this.findRailAt(blockPos);
        if (blockRailBaseRail == null) {
            return false;
        }

        blockRailBaseRail.func_150651_b();
        return blockRailBaseRail.func_150649_b(this);
    }

    private void method_17ab15f27456aff48b83(
            BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        this.field_150657_g.clear();
        int i = j;
        switch (blockRailBaseEnumRailDirection) {
            case NORTH_SOUTH:
                this.field_150657_g.add(this.pos.offsetNorth());
                this.field_150657_g.add(this.pos.offsetSouth());
                if (i == 0) {
                    break;
                }
            case EAST_WEST:
                this.field_150657_g.add(this.pos.offsetWest());
                this.field_150657_g.add(this.pos.offsetEast());
                if (i == 0) {
                    break;
                }
            case ASCENDING_EAST:
                this.field_150657_g.add(this.pos.offsetWest());
                this.field_150657_g.add(this.pos.offsetEast().offsetUp());
                if (i == 0) {
                    break;
                }
            case ASCENDING_WEST:
                this.field_150657_g.add(this.pos.offsetWest().offsetUp());
                this.field_150657_g.add(this.pos.offsetEast());
                if (i == 0) {
                    break;
                }
            case ASCENDING_NORTH:
                this.field_150657_g.add(this.pos.offsetNorth().offsetUp());
                this.field_150657_g.add(this.pos.offsetSouth());
                if (i == 0) {
                    break;
                }
            case ASCENDING_SOUTH:
                this.field_150657_g.add(this.pos.offsetNorth());
                this.field_150657_g.add(this.pos.offsetSouth().offsetUp());
                if (i == 0) {
                    break;
                }
            case SOUTH_EAST:
                this.field_150657_g.add(this.pos.offsetEast());
                this.field_150657_g.add(this.pos.offsetSouth());
                if (i == 0) {
                    break;
                }
            case SOUTH_WEST:
                this.field_150657_g.add(this.pos.offsetWest());
                this.field_150657_g.add(this.pos.offsetSouth());
                if (i == 0) {
                    break;
                }
            case NORTH_WEST:
                this.field_150657_g.add(this.pos.offsetWest());
                this.field_150657_g.add(this.pos.offsetNorth());
                if (i == 0) {
                    break;
                }
            case NORTH_EAST:
                this.field_150657_g.add(this.pos.offsetEast());
                this.field_150657_g.add(this.pos.offsetNorth());
        }
    }

    private boolean func_150649_b(BlockRailBaseRail blockRailBaseRail) {
        return this.func_150653_a(blockRailBaseRail) || this.field_150657_g.size() != 2;
    }

    public BlockState getBlockState() {
        return this.state;
    }

    private void func_150645_c(BlockRailBaseRail blockRailBaseRail) {
        this.field_150657_g.add(blockRailBaseRail.pos);
        BlockPos blockPos = this.pos.offsetNorth();
        BlockPos blockPosValue = this.pos.offsetSouth();
        BlockPos blockPosValue2 = this.pos.offsetWest();
        BlockPos blockPosValue3 = this.pos.offsetEast();
        boolean flag = this.func_180363_c(blockPos);
        boolean flag1 = this.func_180363_c(blockPosValue);
        boolean flag2 = this.func_180363_c(blockPosValue2);
        boolean flag3 = this.func_180363_c(blockPosValue3);
        BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection = null;
        if (flag || flag1) {
            blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_SOUTH;
        }

        if (flag2 || flag3) {
            blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.EAST_WEST;
        }

        if (!this.isPowered) {
            if (flag1 && flag3 && !flag && !flag2) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_EAST;
            }

            if (flag1 && flag2 && !flag && !flag3) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.SOUTH_WEST;
            }

            if (flag && flag2 && !flag1 && !flag3) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_WEST;
            }

            if (flag && flag3 && !flag1 && !flag2) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_EAST;
            }
        }

        if (blockRailBaseEnumRailDirection == BlockRailBaseEnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.isRailBlock(this.world, blockPos.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_NORTH;
            }

            if (BlockRailBase.isRailBlock(this.world, blockPosValue.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_SOUTH;
            }
        }

        if (blockRailBaseEnumRailDirection == BlockRailBaseEnumRailDirection.EAST_WEST) {
            if (BlockRailBase.isRailBlock(this.world, blockPosValue3.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_EAST;
            }

            if (BlockRailBase.isRailBlock(this.world, blockPosValue2.offsetUp())) {
                blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.ASCENDING_WEST;
            }
        }

        if (blockRailBaseEnumRailDirection == null) {
            blockRailBaseEnumRailDirection = BlockRailBaseEnumRailDirection.NORTH_SOUTH;
        }

        this.state =
                this.state.withProperty(
                        this.block.getShapeProperty(), blockRailBaseEnumRailDirection);
        this.world.setBlockState(this.pos, this.state, 3);
    }

    private boolean func_150653_a(BlockRailBaseRail blockRailBaseRail) {
        return this.func_180363_c(blockRailBaseRail.pos);
    }

    private boolean hasRailAt(BlockPos blockPos) {
        return BlockRailBase.isRailBlock(this.world, blockPos)
                || BlockRailBase.isRailBlock(this.world, blockPos.offsetUp())
                || BlockRailBase.isRailBlock(this.world, blockPos.offsetDown());
    }
}
