package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block {
    public static PropertyBool SOUTH;
    public static PropertyBool EAST;
    public static PropertyBool WEST;

    public static PropertyBool DISARMED;
    public static PropertyBool SUSPENDED;
    public static PropertyBool NORTH;
    public static PropertyBool ATTACHED;
    public static PropertyBool POWERED;

    private void updateState(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        byte b0 =
                remix.compat.compiler.JvmBooleanSlots.toByte(
                        (Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.POWERED));
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        byte b1 = 0;
        int i = j;
        List entitiesWithinAABBExcludingEntity =
                world.getEntitiesWithinAABBExcludingEntity(
                        (Entity) null,
                        new BoundingBox(
                                blockPos.getX() + this.minX,
                                blockPos.getY() + this.minY,
                                blockPos.getZ() + this.minZ,
                                blockPos.getX() + this.maxX,
                                blockPos.getY() + this.maxY,
                                blockPos.getZ() + this.maxZ));
        if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
            for (java.util.Iterator<?> __recoveryIterator171_2250 =
                            (entitiesWithinAABBExcludingEntity).iterator();
                    (__recoveryIterator171_2250).hasNext(); ) {
                Entity entity = (Entity) (__recoveryIterator171_2250).next();
                if (!entity.doesEntityNotTriggerPressurePlate()) {
                    b1 = 1;
                    if (i == 0) {
                        break;
                    }
                }

                if (i != 0) {
                    break;
                }
            }
        }

        if (b1 != b0) {
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockTripWire.POWERED,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(b1)));
            world.setBlockState(blockPos, blockState, 3);
            this.notifyHook(world, blockPos, blockState);
        }

        if (b1 != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this,
                new net.minecraft.block.properties.IProperty[] {
                    net.minecraft.block.BlockTripWire.POWERED,
                    net.minecraft.block.BlockTripWire.SUSPENDED,
                    net.minecraft.block.BlockTripWire.ATTACHED,
                    net.minecraft.block.BlockTripWire.DISARMED,
                    net.minecraft.block.BlockTripWire.NORTH,
                    net.minecraft.block.BlockTripWire.EAST,
                    net.minecraft.block.BlockTripWire.WEST,
                    net.minecraft.block.BlockTripWire.SOUTH
                });
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isRemote
                && playerEntity.getCurrentEquippedItem() != null
                && playerEntity.getCurrentEquippedItem().getItem() == Items.shears) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockTripWire.DISARMED, true),
                    4);
        }
    }

    // region String decoding support

    // endregion

    public boolean isFullCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        boolean value = (Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.ATTACHED);
        boolean valueValue =
                (Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.SUSPENDED);
        int i = j;
        if (!valueValue) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
            if (i != 0) {
                return;
            }
        }

        if (!value) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            if (i != 0) {
                return;
            }
        }

        this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        this.notifyHook(
                world,
                blockPos,
                blockState.withProperty(net.minecraft.block.BlockTripWire.POWERED, true));
    }

    public static boolean isConnectedTo(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        BlockPos blockPosValue = blockPos.offset(facing);
        BlockState blockStateValue = iBlockAccess.getBlockState(blockPosValue);
        Block block = blockStateValue.getBlock();
        if (block == Blocks.tripwire_hook) {
            Facing facingValue = facing.getOpposite();
            return blockStateValue.getValue(BlockTripWireHook.FACING) == facingValue;
        } else if (block == Blocks.tripwire) {
            boolean value =
                    (Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.SUSPENDED);
            boolean valueValue =
                    (Boolean) blockStateValue.getValue(net.minecraft.block.BlockTripWire.SUSPENDED);
            return value == valueValue;
        } else {
            return false;
        }
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote
                && (java.lang.Boolean)
                        world.getBlockState(blockPos)
                                .getValue(net.minecraft.block.BlockTripWire.POWERED)) {
            this.updateState(world, blockPos);
        }
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!world.isRemote
                && !(java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockTripWire.POWERED)) {
            this.updateState(world, blockPos);
        }
    }

    public BlockState getStateFromMeta(int p171_0) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockTripWire.POWERED, (p171_0 & 1) > 0)
                .withProperty(net.minecraft.block.BlockTripWire.SUSPENDED, (p171_0 & 2) > 0)
                .withProperty(net.minecraft.block.BlockTripWire.ATTACHED, (p171_0 & 4) > 0)
                .withProperty(net.minecraft.block.BlockTripWire.DISARMED, (p171_0 & 8) > 0);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        boolean value =
                (java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockTripWire.SUSPENDED);
        boolean flag1 =
                (!net.minecraft.world.World.doesBlockHaveSolidTopSurface(
                        world, blockPos.offsetDown()));
        if (value != flag1) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.string;
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        blockState =
                blockState.withProperty(
                        net.minecraft.block.BlockTripWire.SUSPENDED,
                        !net.minecraft.world.World.doesBlockHaveSolidTopSurface(
                                world, blockPos.offsetDown()));
        world.setBlockState(blockPos, blockState, 3);
        this.notifyHook(world, blockPos, blockState);
    }

    static {
        net.minecraft.block.BlockTripWire.POWERED = PropertyBool.create("powered");
        net.minecraft.block.BlockTripWire.SUSPENDED = PropertyBool.create("suspended");
        net.minecraft.block.BlockTripWire.ATTACHED = PropertyBool.create("attached");
        net.minecraft.block.BlockTripWire.DISARMED = PropertyBool.create("disarmed");
        net.minecraft.block.BlockTripWire.NORTH = PropertyBool.create("north");
        net.minecraft.block.BlockTripWire.EAST = PropertyBool.create("east");
        net.minecraft.block.BlockTripWire.SOUTH = PropertyBool.create("south");
        net.minecraft.block.BlockTripWire.WEST = PropertyBool.create("west");
    }

    public BlockTripWire() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockTripWire.POWERED, false)
                        .withProperty(net.minecraft.block.BlockTripWire.SUSPENDED, false)
                        .withProperty(net.minecraft.block.BlockTripWire.ATTACHED, false)
                        .withProperty(net.minecraft.block.BlockTripWire.DISARMED, false)
                        .withProperty(net.minecraft.block.BlockTripWire.NORTH, false)
                        .withProperty(net.minecraft.block.BlockTripWire.EAST, false)
                        .withProperty(net.minecraft.block.BlockTripWire.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockTripWire.WEST, false));
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        this.setTickRandomly(true);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.string;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    public int getMetaFromState(BlockState blockState) {
        int i = 0;
        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.POWERED)) {
            i |= 1;
        }

        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.SUSPENDED)) {
            i |= 2;
        }

        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.ATTACHED)) {
            i |= 4;
        }

        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockTripWire.DISARMED)) {
            i |= 8;
        }

        return i;
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState
                .withProperty(
                        net.minecraft.block.BlockTripWire.NORTH,
                        net.minecraft.block.BlockTripWire.isConnectedTo(
                                iBlockAccess,
                                blockPos,
                                blockState,
                                net.minecraft.util.Facing.NORTH))
                .withProperty(
                        net.minecraft.block.BlockTripWire.EAST,
                        net.minecraft.block.BlockTripWire.isConnectedTo(
                                iBlockAccess, blockPos, blockState, net.minecraft.util.Facing.EAST))
                .withProperty(
                        net.minecraft.block.BlockTripWire.SOUTH,
                        net.minecraft.block.BlockTripWire.isConnectedTo(
                                iBlockAccess,
                                blockPos,
                                blockState,
                                net.minecraft.util.Facing.SOUTH))
                .withProperty(
                        net.minecraft.block.BlockTripWire.WEST,
                        net.minecraft.block.BlockTripWire.isConnectedTo(
                                iBlockAccess,
                                blockPos,
                                blockState,
                                net.minecraft.util.Facing.WEST));
    }

    private void notifyHook(World world, BlockPos blockPos, BlockState blockState) {
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Facing[] facingArray = new Facing[] {Facing.SOUTH, Facing.WEST};
        int j = facingArray.length;
        int k = 0;
        int i = i1;

        while (k < j) {
            Facing facing = facingArray[k];
            int l = 1;

            while (true) {
                label29:
                if (l < 42) {
                    BlockPos blockPosValue = blockPos.offset(facing, l);
                    BlockState blockStateValue = world.getBlockState(blockPosValue);
                    if (blockStateValue.getBlock() == Blocks.tripwire_hook) {
                        if (blockStateValue.getValue(BlockTripWireHook.FACING)
                                != facing.getOpposite()) {
                            break label29;
                        }

                        Blocks.tripwire_hook.method_287452e54ed46f0c61a9(
                                world, blockPosValue, blockStateValue, false, true, l, blockState);
                        if (i == 0) {
                            break label29;
                        }
                    }

                    if (blockStateValue.getBlock() == Blocks.tripwire) {
                        l++;
                        if (i == 0) {
                            continue;
                        }
                    }
                }

                k++;
                if (i != 0) {
                    return;
                }
                break;
            }
        }
    }
}
