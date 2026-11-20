package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockRedstoneDiode extends BlockDirectional {
    protected boolean isRepeaterPowered;

    public boolean isFacingTowardsRepeater(
            World world, BlockPos blockPos, BlockState blockState) {
        Facing facing = ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE)).getOpposite();
        BlockPos blockPosValue = blockPos.offset(facing);
        return isRedstoneRepeaterBlockID(world.getBlockState(blockPosValue).getBlock())
                ? world.getBlockState(blockPosValue).getValue(net.minecraft.block.BlockDirectional.AGE) != facing
                : false;
    }

    public boolean isAssociatedBlock(Block block) {
        return this.isAssociated(block);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                ? super.canPlaceBlockAt(world, blockPos)
                : false;
    }

    public boolean canProvidePower() {
        return true;
    }

    protected abstract int getDelay(BlockState blockState);

    protected abstract BlockState getUnpoweredState(BlockState blockState);

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return this.isProvidingWeakPower(iBlockAccess, blockPos, blockState, facing);
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return facing.getAxis() != FacingAxis.Y;
    }

    protected void notifyNeighbors(World world, BlockPos blockPos, BlockState blockState) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        BlockPos blockPosValue = blockPos.offset(facing.getOpposite());
        world.notifyBlockOfStateChange(blockPosValue, this);
        world.notifyNeighborsOfStateExcept(blockPosValue, this, facing);
    }

    protected int getActiveSignal(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState) {
        return 15;
    }

    public static boolean isRedstoneRepeaterBlockID(Block block) {
        return Blocks.unpowered_repeater.isAssociated(block)
                || Blocks.unpowered_comparator.isAssociated(block);
    }

    public boolean isAssociated(Block block) {
        return block == this.getPoweredState(this.getDefaultState()).getBlock()
                || block == this.getUnpoweredState(this.getDefaultState()).getBlock();
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!this.isLocked(world, blockPos, blockState)) {
            boolean flag = this.shouldBePowered(world, blockPos, blockState);
            if (this.isRepeaterPowered && !flag) {
                world.setBlockState(blockPos, this.getUnpoweredState(blockState), 2);
                if (i == 0) {
                    return;
                }
            }

            if (!this.isRepeaterPowered) {
                world.setBlockState(blockPos, this.getPoweredState(blockState), 2);
                if (!flag) {
                    world.updateBlockTick(
                            blockPos,
                            this.getPoweredState(blockState).getBlock(),
                            this.getTickDelay(blockState),
                            -1);
                }
            }
        }
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.canBlockStay(world, blockPos)) {
            this.updateState(world, blockPos, blockState);
            if (i != 0) {
                return;
            }
        }

        this.dropBlockAsItem(world, blockPos, blockState, 0);
        world.setBlockToAir(blockPos);

        for (Facing facing : Facing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
            if (i == 0) {
                break;
            }
        }
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.notifyNeighbors(world, blockPos, blockState);
    }

    public boolean isFullCube() {
        return false;
    }

    protected abstract BlockState getPoweredState(BlockState blockState);

    protected BlockRedstoneDiode(boolean isRepeaterPoweredValue) {
        super(Material.circuits);
        this.isRepeaterPowered = isRepeaterPoweredValue;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockDirectional.AGE,
                        livingEntity.getHorizontalFacing().getOpposite());
    }

    protected int getPowerOnSides(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        Facing facingValue = facing.rotateY();
        Facing facingValue2 = facing.rotateYCCW();
        return Math.max(
                this.getPowerOnSide(iBlockAccess, blockPos.offset(facingValue), facingValue),
                this.getPowerOnSide(iBlockAccess, blockPos.offset(facingValue2), facingValue2));
    }

    protected int getPowerOnSide(IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return this.canPowerSide(block)
                ? (block == Blocks.redstone_wire
                        ? (Integer) blockState.getValue(BlockRedstoneWire.POWER)
                        : iBlockAccess.getStrongPower(blockPos, facing))
                : 0;
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        if (this.shouldBePowered(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, 1);
        }
    }

    protected boolean isPowered(BlockState blockState) {
        return this.isRepeaterPowered;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean canBlockStay(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }

    protected void updateState(
            World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!this.isLocked(world, blockPos, blockState)) {
            boolean flag = this.shouldBePowered(world, blockPos, blockState);
            if ((this.isRepeaterPowered && !flag || !this.isRepeaterPowered && flag)
                    && !world.isBlockTickPending(blockPos, this)) {
                byte b0;
                {
                    b0 = -1;
                    if (this.isFacingTowardsRepeater(world, blockPos, blockState)) {
                        b0 = -3;
                        if (!(i == 0)) {

                            if (this.isRepeaterPowered) {
                                b0 = -2;
                            }
                        }
                    } else {

                        if (this.isRepeaterPowered) {
                            b0 = -2;
                        }
                    }
                }

                world.updateBlockTick(blockPos, this, this.getDelay(blockState), b0);
            }
        }
    }

    protected int getTickDelay(BlockState blockState) {
        return this.getDelay(blockState);
    }

    public boolean isLocked(IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (this.isRepeaterPowered) {
            for (Facing facing : Facing.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
                if (i != 0) {
                    break;
                }
            }
        }

        super.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }

    protected int calculateInputStrength(World world, BlockPos blockPos, BlockState blockState) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        BlockPos blockPosValue = blockPos.offset(facing);
        int redstonePower = world.getRedstonePower(blockPosValue, facing);
        if (redstonePower >= 15) {
            return redstonePower;
        }

        BlockState blockStateValue = world.getBlockState(blockPosValue);
        return Math.max(
                redstonePower,
                blockStateValue.getBlock() == Blocks.redstone_wire
                        ? (Integer) blockStateValue.getValue(BlockRedstoneWire.POWER)
                        : 0);
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return !this.isPowered(blockState)
                ? 0
                : (blockState.getValue(net.minecraft.block.BlockDirectional.AGE) == facing
                        ? this.getActiveSignal(iBlockAccess, blockPos, blockState)
                        : 0);
    }

    protected boolean shouldBePowered(World world, BlockPos blockPos, BlockState blockState) {
        return this.calculateInputStrength(world, blockPos, blockState) > 0;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected boolean canPowerSide(Block block) {
        return block.canProvidePower();
    }
}
