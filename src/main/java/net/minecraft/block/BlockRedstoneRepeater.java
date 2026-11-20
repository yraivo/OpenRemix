package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
    public static PropertyInteger DELAY;
    public static PropertyBool LOCKED;

    protected BlockState getPoweredState(BlockState blockState) {
        Integer integer = (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.DELAY);
        Boolean obool = (Boolean) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.LOCKED);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        return Blocks.powered_repeater
                .getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, facing)
                .withProperty(net.minecraft.block.BlockRedstoneRepeater.DELAY, integer)
                .withProperty(net.minecraft.block.BlockRedstoneRepeater.LOCKED, obool);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockDirectional.AGE,
                    net.minecraft.block.BlockRedstoneRepeater.DELAY,
                    net.minecraft.block.BlockRedstoneRepeater.LOCKED
                });
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (this.isRepeaterPowered) {
            Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
            double d0 = blockPos.getX() + 0.5F + (random.nextFloat() - 0.5F) * 0.2;
            double d1 = blockPos.getY() + 0.4F + (random.nextFloat() - 0.5F) * 0.2;
            double d2 = blockPos.getZ() + 0.5F + (random.nextFloat() - 0.5F) * 0.2;
            float f = -5.0F;
            if (random.nextBoolean()) {
                f = (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.DELAY) * 2 - 1;
            }

            f /= 16.0F;
            double d3 = f * facing.getFrontOffsetX();
            double d4 = f * facing.getFrontOffsetZ();
            world.spawnParticle(
                    EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0, 0.0, 0.0, new int[0]);
        }
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (!playerEntity.capabilities.allowEdit) {
            return false;
        }

        world.setBlockState(
                blockPos, blockState.cycleProperty(net.minecraft.block.BlockRedstoneRepeater.DELAY), 3);
        return true;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.getHorizontal(i))
                .withProperty(net.minecraft.block.BlockRedstoneRepeater.LOCKED, false)
                .withProperty(net.minecraft.block.BlockRedstoneRepeater.DELAY, 1 + (i >> 2));
    }

    // region String decoding support

    // endregion

    public boolean isLocked(IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState) {
        return this.getPowerOnSides(iBlockAccess, blockPos, blockState) > 0;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.repeater;
    }

    protected BlockState getUnpoweredState(BlockState blockState) {
        Integer integer = (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.DELAY);
        Boolean obool = (Boolean) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.LOCKED);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        return Blocks.unpowered_repeater
                .getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, facing)
                .withProperty(net.minecraft.block.BlockRedstoneRepeater.DELAY, integer)
                .withProperty(net.minecraft.block.BlockRedstoneRepeater.LOCKED, obool);
    }

    protected int getDelay(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.DELAY) * 2;
    }

    static {
        net.minecraft.block.BlockRedstoneRepeater.LOCKED = PropertyBool.create("locked");
        net.minecraft.block.BlockRedstoneRepeater.DELAY =
                PropertyInteger.create("delay", 1, 4);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.repeater;
    }

    protected BlockRedstoneRepeater(boolean flag) {
        super(flag);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockRedstoneRepeater.DELAY, 1)
                        .withProperty(net.minecraft.block.BlockRedstoneRepeater.LOCKED, false));
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                .getHorizontalIndex();
        return i | (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneRepeater.DELAY) - 1 << 2;
    }

    // region String decoding support

    // endregion

    protected boolean canPowerSide(Block block) {
        return isRedstoneRepeaterBlockID(block);
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState.withProperty(
                net.minecraft.block.BlockRedstoneRepeater.LOCKED,
                this.isLocked(iBlockAccess, blockPos, blockState));
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        this.notifyNeighbors(world, blockPos, blockState);
    }
}
