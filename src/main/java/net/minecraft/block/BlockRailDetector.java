package net.minecraft.block;

import com.google.common.base.Predicate;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockRailDetector extends BlockRailBase {
    public static PropertyEnum SHAPE;
    public static PropertyBool POWERED;

    // region String decoding support

    // endregion

    private BoundingBox getDectectionBox(BlockPos blockPos) {

        return new BoundingBox(
                blockPos.getX() + 0.2F,
                blockPos.getY(),
                blockPos.getZ() + 0.2F,
                blockPos.getX() + 1 - 0.2F,
                blockPos.getY() + 1 - 0.2F,
                blockPos.getZ() + 1 - 0.2F);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockRailBaseEnumRailDirection)
                                        blockState.getValue(
                                                net.minecraft.block.BlockRailDetector.SHAPE))
                                .getMetadata();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockRailDetector.POWERED)) {
            i |= 8;
        }

        return i;
    }

    static {
        net.minecraft.block.BlockRailDetector.SHAPE =
                PropertyEnum.create(
                        "shape",
                        BlockRailBaseEnumRailDirection.class,
                        new BlockRailBaseEnumRailDirectionPredicate());
        net.minecraft.block.BlockRailDetector.POWERED = PropertyBool.create("powered");
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return ((java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockRailDetector.POWERED))
                ? 15
                : 0;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockRailDetector.SHAPE,
                        BlockRailBaseEnumRailDirection.byMetadata(i & 7))
                .withProperty(net.minecraft.block.BlockRailDetector.POWERED, (i & 8) > 0);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockRailDetector.SHAPE,
                    net.minecraft.block.BlockRailDetector.POWERED
                });
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return !((java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockRailDetector.POWERED))
                ? 0
                : (facing == Facing.UP ? 15 : 0);
    }

    public int tickRate(World world) {
        return 20;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote
                && (Boolean) blockState.getValue(net.minecraft.block.BlockRailDetector.POWERED)) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!world.isRemote
                && !(Boolean) blockState.getValue(net.minecraft.block.BlockRailDetector.POWERED)) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }

    protected List findMinecarts(
            World world, BlockPos blockPos, Class oclass, Predicate... apredicate) {
        BoundingBox boundingBox = this.getDectectionBox(blockPos);
        return apredicate.length != 1
                ? world.getEntitiesWithinAABB(oclass, boundingBox)
                : world.getEntitiesWithinAABB(oclass, boundingBox, apredicate[0]);
    }

    public BlockRailDetector() {
        super(true);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockRailDetector.POWERED, false)
                        .withProperty(
                                net.minecraft.block.BlockRailDetector.SHAPE,
                                BlockRailBaseEnumRailDirection.NORTH_SOUTH));
        this.setTickRandomly(true);
    }

    public IProperty getShapeProperty() {
        return net.minecraft.block.BlockRailDetector.SHAPE;
    }

    public boolean canProvidePower() {
        return true;
    }

    private void updatePoweredState(World world, BlockPos blockPos, BlockState blockState) {
        boolean value =
                (Boolean) blockState.getValue(net.minecraft.block.BlockRailDetector.POWERED);
        byte b0 = 0;
        List minecarts = this.findMinecarts(world, blockPos, EntityMinecart.class);
        if (!minecarts.isEmpty()) {
            b0 = 1;
        }

        if (b0 != 0 && !value) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockRailDetector.POWERED, true),
                    3);
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }

        if (b0 == 0 && value) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockRailDetector.POWERED, false),
                    3);
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offsetDown(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }

        if (b0 != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }

        world.updateComparatorOutputLevel(blockPos, this);
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        if ((Boolean)
                world.getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockRailDetector.POWERED)) {
            List minecarts = this.findMinecarts(world, blockPos, EntityMinecartCommandBlock.class);
            if (!minecarts.isEmpty()) {
                return ((EntityMinecartCommandBlock) minecarts.get(0))
                        .getCommandBlockLogic()
                        .getSuccessCount();
            }

            List listValue =
                    this.findMinecarts(
                            world,
                            blockPos,
                            EntityMinecart.class,
                            IEntitySelector.selectInventories);
            if (!listValue.isEmpty()) {
                return Container.calcRedstoneFromInventory((IInventory) listValue.get(0));
            }
        }

        return 0;
    }

    // region String decoding support

    // endregion

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.updatePoweredState(world, blockPos, blockState);
    }

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
