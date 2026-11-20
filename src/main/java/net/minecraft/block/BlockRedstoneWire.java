package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends Block {
    private boolean canProvidePower = true;
    public static PropertyEnum WEST;

    public static PropertyInteger POWER;
    public static PropertyEnum NORTH;
    public static PropertyEnum SOUTH;
    public static PropertyEnum EAST;
    private final Set blocksNeedingUpdate = Sets.newHashSet();

    static {
        net.minecraft.block.BlockRedstoneWire.NORTH =
                PropertyEnum.create("north", BlockRedstoneWireEnumAttachPosition.class);
        net.minecraft.block.BlockRedstoneWire.EAST =
                PropertyEnum.create("east", BlockRedstoneWireEnumAttachPosition.class);
        net.minecraft.block.BlockRedstoneWire.SOUTH =
                PropertyEnum.create("south", BlockRedstoneWireEnumAttachPosition.class);
        net.minecraft.block.BlockRedstoneWire.WEST =
                PropertyEnum.create("west", BlockRedstoneWireEnumAttachPosition.class);
        net.minecraft.block.BlockRedstoneWire.POWER = PropertyInteger.create("power", 0, 15);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                || world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.glowstone;
    }

    private void notifyWireNeighborsOfStateChange(World world, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (world.getBlockState(blockPos).getBlock() == this) {
            world.notifyNeighborsOfStateChange(blockPos, this);

            for (Facing facing : Facing.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
                if (i != 0) {
                    break;
                }
            }
        }
    }

    protected static boolean canConnectTo(BlockState blockState, Facing facing) {
        Block block = blockState.getBlock();
        if (block == Blocks.redstone_wire) {
            return true;
        } else if (Blocks.unpowered_repeater.isAssociated(block)) {
            Facing facingValue = (Facing) blockState.getValue(BlockRedstoneRepeater.AGE);
            return facingValue == facing || facingValue.getOpposite() == facing;
        } else {
            return block.canProvidePower() && facing != null;
        }
    }

    private BlockState calculateCurrentChanges(
            World world, BlockPos blockPos, BlockPos blockPosValue, BlockState blockState) {
        BlockState blockStateValue = blockState;
        int j = (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneWire.POWER);
        byte b0 = 0;
        int k1 = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int k = this.getMaxCurrentStrength(world, blockPosValue, b0);
        this.canProvidePower = false;
        int l = world.isBlockIndirectlyGettingPowered(blockPos);
        int i = k1;
        this.canProvidePower = true;
        if (l > 0 && l > k - 1) {
            k = l;
        }

        int i1 = 0;

        for (java.util.Iterator<?> __recoveryIterator171_8376 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_8376).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_8376).next();
            BlockPos blockPosValue2 = blockPos.offset(facing);
            int j1 =
                    blockPosValue2.getX() == blockPosValue.getX()
                                    && blockPosValue2.getZ() == blockPosValue.getZ()
                            ? 0
                            : 1;
            if (j1 != 0) {
                i1 = this.getMaxCurrentStrength(world, blockPosValue2, i1);
            }

            label78:
            {
                if (world.getBlockState(blockPosValue2).getBlock().isNormalCube()
                        && !world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube()) {
                    if (j1 == 0 || blockPos.getY() < blockPosValue.getY()) {
                        break label78;
                    }

                    i1 = this.getMaxCurrentStrength(world, blockPosValue2.offsetUp(), i1);
                    if (i != 0) {
                        break label78;
                    }
                }

                if (!world.getBlockState(blockPosValue2).getBlock().isNormalCube()
                        && j1 != 0
                        && blockPos.getY() <= blockPosValue.getY()) {
                    i1 = this.getMaxCurrentStrength(world, blockPosValue2.offsetDown(), i1);
                }
            }

            if (i == 0) {
                break;
            }
        }

        label93:
        {
            if (i1 > k) {
                k = i1 - 1;
                if (i != 0) {
                    break label93;
                }
            }

            if (k > 0) {
                k--;
                if (i != 0) {
                    break label93;
                }
            }

            k = 0;
        }

        if (l > k - 1) {
            k = l;
        }

        if (j != k) {
            blockState = blockState.withProperty(net.minecraft.block.BlockRedstoneWire.POWER, k);
            if (world.getBlockState(blockPos) == blockStateValue) {
                world.setBlockState(blockPos, blockState, 2);
            }

            this.blocksNeedingUpdate.add(blockPos);

            for (Facing facingValue : Facing.values()) {
                this.blocksNeedingUpdate.add(blockPos.offset(facingValue));
                if (i == 0) {
                    break;
                }
            }
        }

        return blockState;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.redstone;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.redstone;
    }

    protected static boolean canConnectUpwardsTo(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return canConnectUpwardsTo(iBlockAccess.getBlockState(blockPos));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            if (this.canPlaceBlockAt(world, blockPos)) {
                this.updateSurroundingRedstone(world, blockPos, blockState);
                if (i == 0) {
                    return;
                }
            }

            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }
    }

    public BlockRedstoneWire() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockRedstoneWire.NORTH,
                                BlockRedstoneWireEnumAttachPosition.NONE)
                        .withProperty(
                                net.minecraft.block.BlockRedstoneWire.EAST,
                                BlockRedstoneWireEnumAttachPosition.NONE)
                        .withProperty(
                                net.minecraft.block.BlockRedstoneWire.SOUTH,
                                BlockRedstoneWireEnumAttachPosition.NONE)
                        .withProperty(
                                net.minecraft.block.BlockRedstoneWire.WEST,
                                BlockRedstoneWireEnumAttachPosition.NONE)
                        .withProperty(net.minecraft.block.BlockRedstoneWire.POWER, 0));
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    private int colorMultiplier(int i) {
        float f = i / 15.0F;
        float f1 = f * 0.6F + 0.4F;
        if (i == 0) {
            f1 = 0.3F;
        }

        float f2 = f * f * 0.7F - 0.5F;
        float f3 = f * f * 0.6F - 0.7F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        int j = MathHelper.clamp((int) (f1 * 255.0F), 0, 255);
        int k = MathHelper.clamp((int) (f2 * 255.0F), 0, 255);
        int l = MathHelper.clamp((int) (f3 * 255.0F), 0, 255);
        return 0xFF000000 | j << 16 | k << 8 | l;
    }

    private int getMaxCurrentStrength(World world, BlockPos blockPos, int i) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return i;
        }

        int j = (Integer) world.getBlockState(blockPos).getValue(net.minecraft.block.BlockRedstoneWire.POWER);
        return j > i ? j : i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockRedstoneWire.NORTH,
                    net.minecraft.block.BlockRedstoneWire.EAST,
                    net.minecraft.block.BlockRedstoneWire.SOUTH,
                    net.minecraft.block.BlockRedstoneWire.WEST,
                    net.minecraft.block.BlockRedstoneWire.POWER
                });
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return !this.canProvidePower
                ? 0
                : this.isProvidingWeakPower(iBlockAccess, blockPos, blockState, facing);
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!this.canProvidePower) {
            return 0;
        }

        int j = (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneWire.POWER);
        if (j == 0) {
            return 0;
        }

        if (facing == Facing.UP) {
            return j;
        }

        EnumSet enumset = EnumSet.noneOf(Facing.class);

        for (java.util.Iterator<?> __recoveryIterator171_16817 =
                        (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_16817).hasNext(); ) {
            Facing facingValue = (Facing) (__recoveryIterator171_16817).next();
            if (this.method_3f8955335a629b484a12(iBlockAccess, blockPos, facingValue)) {
                enumset.add(facingValue);
            }

            if (i != 0) {
                break;
            }
        }

        if (facing.getAxis().isHorizontal() && enumset.isEmpty()) {
            return j;
        } else {
            return enumset.contains(facing)
                            && !enumset.contains(facing.rotateYCCW())
                            && !enumset.contains(facing.rotateY())
                    ? j
                    : 0;
        }
    }

    private BlockState updateSurroundingRedstone(
            World world, BlockPos blockPos, BlockState blockState) {
        blockState = this.calculateCurrentChanges(world, blockPos, blockPos, blockState);
        ArrayList arrayList = Lists.newArrayList(this.blocksNeedingUpdate);
        this.blocksNeedingUpdate.clear();
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Iterator iterator = arrayList.iterator();
        int i = j;

        while (iterator.hasNext()) {
            BlockPos blockPosValue = (BlockPos) iterator.next();
            world.notifyNeighborsOfStateChange(blockPosValue, this);
            if (i != 0) {
                break;
            }
        }

        return blockState;
    }

    protected static boolean canConnectUpwardsTo(BlockState blockState) {
        return canConnectTo(blockState, (Facing) null);
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockRedstoneWire.POWER, i);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote) {
            this.updateSurroundingRedstone(world, blockPos, blockState);

            for (java.util.Iterator<?> __recoveryIterator171_20365 =
                            (FacingPlane.VERTICAL).iterator();
                    (__recoveryIterator171_20365).hasNext(); ) {
                Facing facing = (Facing) (__recoveryIterator171_20365).next();
                world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
                if (i == 0) {
                    break;
                }
            }

            for (java.util.Iterator<?> __recoveryIterator171_20707 =
                            (FacingPlane.HORIZONTAL).iterator();
                    (__recoveryIterator171_20707).hasNext(); ) {
                Facing facingValue = (Facing) (__recoveryIterator171_20707).next();
                this.notifyWireNeighborsOfStateChange(world, blockPos.offset(facingValue));
                if (i == 0) {
                    break;
                }
            }

            for (java.util.Iterator<?> __recoveryIterator171_21051 =
                            (FacingPlane.HORIZONTAL).iterator();
                    (__recoveryIterator171_21051).hasNext(); ) {
                Facing facingValue2 = (Facing) (__recoveryIterator171_21051).next();
                {
                    BlockPos blockPosValue = blockPos.offset(facingValue2);
                    if (world.getBlockState(blockPosValue).getBlock().isNormalCube()) {
                        this.notifyWireNeighborsOfStateChange(world, blockPosValue.offsetUp());
                        if (!(i != 0)) {

                            this.notifyWireNeighborsOfStateChange(world, blockPosValue.offsetDown());
                        }
                    } else {

                        this.notifyWireNeighborsOfStateChange(world, blockPosValue.offsetDown());
                    }
                }

                if (i == 0) {
                    break;
                }
            }
        }
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        blockState =
                blockState.withProperty(
                        net.minecraft.block.BlockRedstoneWire.WEST,
                        this.getAttachPosition(iBlockAccess, blockPos, Facing.WEST));
        blockState =
                blockState.withProperty(
                        net.minecraft.block.BlockRedstoneWire.EAST,
                        this.getAttachPosition(iBlockAccess, blockPos, Facing.EAST));
        blockState =
                blockState.withProperty(
                        net.minecraft.block.BlockRedstoneWire.NORTH,
                        this.getAttachPosition(iBlockAccess, blockPos, Facing.NORTH));
        return blockState.withProperty(
                net.minecraft.block.BlockRedstoneWire.SOUTH,
                this.getAttachPosition(iBlockAccess, blockPos, Facing.SOUTH));
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        return blockState.getBlock() != this
                ? super.colorMultiplier(iBlockAccess, blockPos, i)
                : this.colorMultiplier(
                        (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneWire.POWER));
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneWire.POWER);
    }

    public boolean canProvidePower() {
        return this.canProvidePower;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    private BlockRedstoneWireEnumAttachPosition getAttachPosition(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        BlockPos blockPosValue = blockPos.offset(facing);
        Block block = iBlockAccess.getBlockState(blockPos.offset(facing)).getBlock();
        if (!canConnectTo(iBlockAccess.getBlockState(blockPosValue), facing)
                && (block.isSolidFullCube()
                        || !canConnectUpwardsTo(
                                iBlockAccess.getBlockState(blockPosValue.offsetDown())))) {
            Block blockValue = iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock();
            return !blockValue.isSolidFullCube()
                            && block.isSolidFullCube()
                            && canConnectUpwardsTo(
                                    iBlockAccess.getBlockState(blockPosValue.offsetUp()))
                    ? BlockRedstoneWireEnumAttachPosition.UP
                    : BlockRedstoneWireEnumAttachPosition.NONE;
        } else {
            return BlockRedstoneWireEnumAttachPosition.SIDE;
        }
    }

    public boolean isFullCube() {
        return false;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        super.breakBlock(world, blockPos, blockState);
        int i = j;
        if (!world.isRemote) {
            for (Facing facing : Facing.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
                if (i != 0) {
                    break;
                }
            }

            this.updateSurroundingRedstone(world, blockPos, blockState);

            for (java.util.Iterator<?> __recoveryIterator171_28481 =
                            (FacingPlane.HORIZONTAL).iterator();
                    (__recoveryIterator171_28481).hasNext(); ) {
                Facing facingValue = (Facing) (__recoveryIterator171_28481).next();
                this.notifyWireNeighborsOfStateChange(world, blockPos.offset(facingValue));
                if (i != 0) {
                    break;
                }
            }

            for (java.util.Iterator<?> __recoveryIterator171_28825 =
                            (FacingPlane.HORIZONTAL).iterator();
                    (__recoveryIterator171_28825).hasNext(); ) {
                Facing facingValue2 = (Facing) (__recoveryIterator171_28825).next();
                {
                    BlockPos blockPosValue = blockPos.offset(facingValue2);
                    if (world.getBlockState(blockPosValue).getBlock().isNormalCube()) {
                        this.notifyWireNeighborsOfStateChange(world, blockPosValue.offsetUp());
                        if (!(i == 0)) {

                            this.notifyWireNeighborsOfStateChange(world, blockPosValue.offsetDown());
                        }
                    } else {

                        this.notifyWireNeighborsOfStateChange(world, blockPosValue.offsetDown());
                    }
                }

                if (i != 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    private boolean method_3f8955335a629b484a12(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        BlockPos blockPosValue = blockPos.offset(facing);
        BlockState blockState = iBlockAccess.getBlockState(blockPosValue);
        Block block = blockState.getBlock();
        boolean normalCube = block.isNormalCube();
        boolean normalCubeValue =
                iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
        return !normalCubeValue
                        && normalCube
                        && canConnectUpwardsTo(iBlockAccess, blockPosValue.offsetUp())
                ? true
                : (canConnectTo(blockState, facing)
                        ? true
                        : (block == Blocks.powered_repeater
                                        && blockState.getValue(BlockRedstoneDiode.AGE) == facing
                                ? true
                                : !normalCube
                                        && canConnectUpwardsTo(
                                                iBlockAccess, blockPosValue.offsetDown())));
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int value = (Integer) blockState.getValue(net.minecraft.block.BlockRedstoneWire.POWER);
        if (value != 0) {
            double d0 = blockPos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            double d1 = blockPos.getY() + 0.0625F;
            double d2 = blockPos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            float f = value / 15.0F;
            float f1 = f * 0.6F + 0.4F;
            float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
            float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
            world.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, f1, f2, f3, new int[0]);
        }
    }
}
