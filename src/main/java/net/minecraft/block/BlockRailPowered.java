package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
    public static PropertyEnum SHAPE;
    public static PropertyBool POWERED;

    protected BlockRailPowered() {
        super(true);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockRailPowered.SHAPE,
                                BlockRailBaseEnumRailDirection.NORTH_SOUTH)
                        .withProperty(net.minecraft.block.BlockRailPowered.POWERED, false));
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockRailPowered.SHAPE,
                        BlockRailBaseEnumRailDirection.byMetadata(i & 7))
                .withProperty(net.minecraft.block.BlockRailPowered.POWERED, (i & 8) > 0);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockRailPowered.SHAPE =
                PropertyEnum.create(
                        "shape",
                        BlockRailBaseEnumRailDirection.class,
                        new BlockRailBaseEnumRailDirectionPredicate());
        net.minecraft.block.BlockRailPowered.POWERED = PropertyBool.create("powered");
    }

    protected void onNeighborChangedInternal(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        byte b0 =
                remix.compat.compiler.JvmBooleanSlots.toByte(
                        (Boolean)
                                blockState.getValue(net.minecraft.block.BlockRailPowered.POWERED));
        int i =
                !world.isBlockPowered(blockPos)
                                && !this.method_aa91d7c3e1fdd149f42d(
                                        world, blockPos, blockState, true, 0)
                                && !this.method_aa91d7c3e1fdd149f42d(
                                        world, blockPos, blockState, false, 0)
                        ? 0
                        : 1;
        if (i != b0) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(
                            net.minecraft.block.BlockRailPowered.POWERED,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(i))),
                    3);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
            if (((BlockRailBaseEnumRailDirection)
                            blockState.getValue(net.minecraft.block.BlockRailPowered.SHAPE))
                    .isAscending()) {
                world.notifyNeighborsOfStateChange(blockPos.offsetUp(), this);
            }
        }
    }

    protected boolean method_aa91d7c3e1fdd149f42d(
            World world, BlockPos blockPos, BlockState blockState, boolean flag, int i) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (i >= 8) {
            return false;
        }

        int k;
        int l;
        int i1;
        byte b0;
        label87:
        {
            k = blockPos.getX();
            l = blockPos.getY();
            i1 = blockPos.getZ();
            b0 = 1;
            BlockRailBaseEnumRailDirection value =
                    (BlockRailBaseEnumRailDirection)
                            blockState.getValue(net.minecraft.block.BlockRailPowered.SHAPE);
            switch (value) {
                case NORTH_SOUTH:
                    if (flag) {
                        i1++;
                        if (j == 0) {
                            return this.method_e740a3fb9e7fbd3415fd(
                                            world, new BlockPos(k, l, i1), flag, i, value)
                                    ? true
                                    : b0 != 0
                                            && this.method_e740a3fb9e7fbd3415fd(
                                                    world,
                                                    new BlockPos(k, l - 1, i1),
                                                    flag,
                                                    i,
                                                    value);
                        }
                    }

                    i1--;
                    if (j == 0) {
                        return this.method_e740a3fb9e7fbd3415fd(
                                        world, new BlockPos(k, l, i1), flag, i, value)
                                ? true
                                : b0 != 0
                                        && this.method_e740a3fb9e7fbd3415fd(
                                                world, new BlockPos(k, l - 1, i1), flag, i, value);
                    }
                case EAST_WEST:
                    if (flag) {
                        k--;
                        if (j == 0) {
                            return this.method_e740a3fb9e7fbd3415fd(
                                            world, new BlockPos(k, l, i1), flag, i, value)
                                    ? true
                                    : b0 != 0
                                            && this.method_e740a3fb9e7fbd3415fd(
                                                    world,
                                                    new BlockPos(k, l - 1, i1),
                                                    flag,
                                                    i,
                                                    value);
                        }
                    }

                    k++;
                    if (j == 0) {
                        return this.method_e740a3fb9e7fbd3415fd(
                                        world, new BlockPos(k, l, i1), flag, i, value)
                                ? true
                                : b0 != 0
                                        && this.method_e740a3fb9e7fbd3415fd(
                                                world, new BlockPos(k, l - 1, i1), flag, i, value);
                    }
                case ASCENDING_EAST:
                    {
                        if (flag) {
                            k--;
                            if (!(j == 0)) {

                                k++;
                                l++;
                                b0 = 0;
                            }
                        } else {

                            k++;
                            l++;
                            b0 = 0;
                        }
                    }

                    value = BlockRailBaseEnumRailDirection.EAST_WEST;
                    if (j == 0) {
                        return this.method_e740a3fb9e7fbd3415fd(
                                        world, new BlockPos(k, l, i1), flag, i, value)
                                ? true
                                : b0 != 0
                                        && this.method_e740a3fb9e7fbd3415fd(
                                                world, new BlockPos(k, l - 1, i1), flag, i, value);
                    }
                case ASCENDING_WEST:
                    {
                        if (flag) {
                            k--;
                            l++;
                            b0 = 0;
                            if (!(j == 0)) {

                                k++;
                            }
                        } else {

                            k++;
                        }
                    }

                    value = BlockRailBaseEnumRailDirection.EAST_WEST;
                    if (j == 0) {
                        return this.method_e740a3fb9e7fbd3415fd(
                                        world, new BlockPos(k, l, i1), flag, i, value)
                                ? true
                                : b0 != 0
                                        && this.method_e740a3fb9e7fbd3415fd(
                                                world, new BlockPos(k, l - 1, i1), flag, i, value);
                    }
                case ASCENDING_NORTH:
                    break;
                case ASCENDING_SOUTH:
                    break label87;
                default:
                    return this.method_e740a3fb9e7fbd3415fd(
                                    world, new BlockPos(k, l, i1), flag, i, value)
                            ? true
                            : b0 != 0
                                    && this.method_e740a3fb9e7fbd3415fd(
                                            world, new BlockPos(k, l - 1, i1), flag, i, value);
            }

            {
                if (flag) {
                    i1++;
                    if (!(j == 0)) {

                        i1--;
                        l++;
                        b0 = 0;
                    }
                } else {

                    i1--;
                    l++;
                    b0 = 0;
                }
            }

            value = BlockRailBaseEnumRailDirection.NORTH_SOUTH;
            if (j == 0) {
                return this.method_e740a3fb9e7fbd3415fd(
                                world, new BlockPos(k, l, i1), flag, i, value)
                        ? true
                        : b0 != 0
                                && this.method_e740a3fb9e7fbd3415fd(
                                        world, new BlockPos(k, l - 1, i1), flag, i, value);
            }
        }

        {
            if (flag) {
                i1++;
                l++;
                b0 = 0;
                if (!(j == 0)) {

                    i1--;
                }
            } else {

                i1--;
            }
        }

        BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection =
                BlockRailBaseEnumRailDirection.NORTH_SOUTH;
        return this.method_e740a3fb9e7fbd3415fd(
                        world, new BlockPos(k, l, i1), flag, i, blockRailBaseEnumRailDirection)
                ? true
                : b0 != 0
                        && this.method_e740a3fb9e7fbd3415fd(
                                world,
                                new BlockPos(k, l - 1, i1),
                                flag,
                                i,
                                blockRailBaseEnumRailDirection);
    }

    protected boolean method_e740a3fb9e7fbd3415fd(
            World world,
            BlockPos blockPos,
            boolean flag,
            int i,
            BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return false;
        }

        BlockRailBaseEnumRailDirection value =
                (BlockRailBaseEnumRailDirection)
                        blockState.getValue(net.minecraft.block.BlockRailPowered.SHAPE);
        return blockRailBaseEnumRailDirection != BlockRailBaseEnumRailDirection.EAST_WEST
                        || value != BlockRailBaseEnumRailDirection.NORTH_SOUTH
                                && value != BlockRailBaseEnumRailDirection.ASCENDING_NORTH
                                && value != BlockRailBaseEnumRailDirection.ASCENDING_SOUTH
                ? (blockRailBaseEnumRailDirection != BlockRailBaseEnumRailDirection.NORTH_SOUTH
                                || value != BlockRailBaseEnumRailDirection.EAST_WEST
                                        && value != BlockRailBaseEnumRailDirection.ASCENDING_EAST
                                        && value != BlockRailBaseEnumRailDirection.ASCENDING_WEST
                        ? (((java.lang.Boolean)
                                        blockState.getValue(
                                                net.minecraft.block.BlockRailPowered.POWERED))
                                ? (world.isBlockPowered(blockPos)
                                        ? true
                                        : this.method_aa91d7c3e1fdd149f42d(
                                                world, blockPos, blockState, flag, i + 1))
                                : false)
                        : false)
                : false;
    }

    public IProperty getShapeProperty() {
        return net.minecraft.block.BlockRailPowered.SHAPE;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockRailPowered.SHAPE,
                    net.minecraft.block.BlockRailPowered.POWERED
                });
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockRailBaseEnumRailDirection)
                                        blockState.getValue(
                                                net.minecraft.block.BlockRailPowered.SHAPE))
                                .getMetadata();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockRailPowered.POWERED)) {
            i |= 8;
        }

        return i;
    }

    // region String decoding support

    // endregion

    // Helper used only by this class.
    public static final class BlockRailBaseEnumRailDirectionPredicate
            implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object object) {
            return this.method_cecee33b099e1df0da40(
                    (net.minecraft.block.BlockRailBaseEnumRailDirection) object);
        }

        public boolean method_cecee33b099e1df0da40(
                net.minecraft.block.BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection) {
            return blockRailBaseEnumRailDirection
                            != net.minecraft.block.BlockRailBaseEnumRailDirection.NORTH_EAST
                    && blockRailBaseEnumRailDirection
                            != net.minecraft.block.BlockRailBaseEnumRailDirection.NORTH_WEST
                    && blockRailBaseEnumRailDirection
                            != net.minecraft.block.BlockRailBaseEnumRailDirection.SOUTH_EAST
                    && blockRailBaseEnumRailDirection
                            != net.minecraft.block.BlockRailBaseEnumRailDirection.SOUTH_WEST;
        }
    }
}
