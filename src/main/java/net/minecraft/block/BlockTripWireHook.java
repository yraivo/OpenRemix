package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTripWireHook extends Block {

    public static PropertyBool SUSPENDED;
    public static PropertyBool ATTACHED;
    public static PropertyDirection FACING;

    public static PropertyBool POWERED;

    public boolean isOpaqueCube() {
        return false;
    }

    static {
        net.minecraft.block.BlockTripWireHook.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockTripWireHook.POWERED = PropertyBool.create("powered");
        net.minecraft.block.BlockTripWireHook.ATTACHED = PropertyBool.create("attached");
        net.minecraft.block.BlockTripWireHook.SUSPENDED = PropertyBool.create("suspended");
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        float f = 0.1875F;
        int i = j;
        switch (((Facing)
                iBlockAccess
                        .getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockTripWireHook.FACING))) {
            case EAST:
                this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
                if (i == 0) {
                    break;
                }
            case WEST:
                this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
                if (i == 0) {
                    break;
                }
            case SOUTH:
                this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
                if (i == 0) {
                    break;
                }
            case NORTH:
                this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
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
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockTripWireHook.POWERED, false)
                        .withProperty(net.minecraft.block.BlockTripWireHook.ATTACHED, false)
                        .withProperty(net.minecraft.block.BlockTripWireHook.SUSPENDED, false);
        if (facing.getAxis().isHorizontal()) {
            blockState =
                    blockState.withProperty(net.minecraft.block.BlockTripWireHook.FACING, facing);
        }

        return blockState;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        boolean value =
                (java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockTripWireHook.ATTACHED);
        boolean valueValue =
                (java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockTripWireHook.POWERED);
        if (value || valueValue) {
            this.method_287452e54ed46f0c61a9(
                    world,
                    blockPos,
                    blockState,
                    true,
                    false,
                    -1,
                    (net.minecraft.block.state.BlockState) null);
        }

        if (valueValue) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(
                    blockPos.offset(
                            ((net.minecraft.util.Facing)
                                            blockState.getValue(
                                                    net.minecraft.block.BlockTripWireHook.FACING))
                                    .getOpposite()),
                    this);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    private void func_176262_b(World world, BlockPos blockPos, Facing facing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(facing.getOpposite()), this);
    }

    private void method_3dc34bb1206d0017c483(
            World world,
            BlockPos blockPos,
            boolean flag,
            boolean flag1,
            boolean flag2,
            boolean flag3) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (flag1 && !flag3) {
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.1,
                    blockPos.getZ() + 0.5,
                    "random.click",
                    0.4F,
                    0.6F);
            if (i != 0) {
                return;
            }
        }

        if (!flag1 && flag3) {
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.1,
                    blockPos.getZ() + 0.5,
                    "random.click",
                    0.4F,
                    0.5F);
            if (i != 0) {
                return;
            }
        }

        if (flag && !flag2) {
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.1,
                    blockPos.getZ() + 0.5,
                    "random.click",
                    0.4F,
                    0.7F);
            if (i != 0) {
                return;
            }
        }

        if (!flag && flag2) {
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.1,
                    blockPos.getZ() + 0.5,
                    "random.bowhit",
                    0.4F,
                    1.2F / (world.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return !((boolean)
                        (((java.lang.Boolean)
                                        ((java.lang.Boolean)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockTripWireHook
                                                                .POWERED)))
                                .booleanValue()))
                ? 0
                : (blockState.getValue(net.minecraft.block.BlockTripWireHook.FACING) == facing
                        ? 15
                        : 0);
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        for (java.util.Iterator<?> typedIterator87 =
                        (net.minecraft.util.FacingPlane.HORIZONTAL).iterator();
                ((boolean) (((java.util.Iterator) (typedIterator87)).hasNext())); ) {
            net.minecraft.util.Facing facing =
                    (net.minecraft.util.Facing)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator87)).next()));
            if (world.getBlockState(blockPos.offset(facing)).getBlock().isNormalCube()) {
                return true;
            }
        }

        return false;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (block != this && this.checkForDrop(world, blockPos, blockState)) {
            net.minecraft.util.Facing facing =
                    (net.minecraft.util.Facing)
                            blockState.getValue(net.minecraft.block.BlockTripWireHook.FACING);
            if (!world.getBlockState(blockPos.offset(facing.getOpposite()))
                    .getBlock()
                    .isNormalCube()) {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
                world.setBlockToAir(blockPos);
            }
        }
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return ((boolean)
                        (((java.lang.Boolean)
                                        ((java.lang.Boolean)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockTripWireHook
                                                                .POWERED)))
                                .booleanValue()))
                ? 15
                : 0;
    }

    public void method_287452e54ed46f0c61a9(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            boolean flag,
            boolean flag1,
            int i,
            BlockState blockStateValue) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockTripWireHook.FACING);
        int j2 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        byte b0 =
                remix.compat.compiler.JvmBooleanSlots.toByte(
                        (Boolean)
                                blockState.getValue(
                                        net.minecraft.block.BlockTripWireHook.ATTACHED));
        int j = j2;
        boolean value =
                (Boolean) blockState.getValue(net.minecraft.block.BlockTripWireHook.POWERED);
        int k = !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown()) ? 1 : 0;
        int l = !flag ? 1 : 0;
        int i1 = 0;
        int j1 = 0;
        BlockState[] blockStateArray = new BlockState[42];
        int k1 = 1;

        while (k1 < 42) {
            BlockPos blockPosValue = blockPos.offset(facing, k1);
            BlockState blockStateValue2 = world.getBlockState(blockPosValue);
            if (blockStateValue2.getBlock() == Blocks.tripwire_hook) {
                if (blockStateValue2.getValue(net.minecraft.block.BlockTripWireHook.FACING)
                        != facing.getOpposite()) {
                    break;
                }

                j1 = k1;
                if (j == 0) {
                    break;
                }
            }

            label111:
            {
                if (blockStateValue2.getBlock() != Blocks.tripwire && k1 != i) {
                    blockStateArray[k1] = null;
                    l = 0;
                    if (j == 0) {
                        break label111;
                    }
                }

                if (k1 == i) {
                    blockStateValue2 =
                            (BlockState)
                                    com.google.common.base.MoreObjects.firstNonNull(
                                            blockStateValue, blockStateValue2);
                }

                int l1 =
                        !((java.lang.Boolean) blockStateValue2.getValue(BlockTripWire.DISARMED))
                                ? 1
                                : 0;
                boolean valueValue = (Boolean) blockStateValue2.getValue(BlockTripWire.POWERED);
                byte b1 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                (Boolean) blockStateValue2.getValue(BlockTripWire.SUSPENDED));
                l &= b1 == k ? 1 : 0;
                i1 |= l1 != 0 && valueValue ? 1 : 0;
                blockStateArray[k1] = blockStateValue2;
                if (k1 == i) {
                    world.scheduleUpdate(blockPos, this, this.tickRate(world));
                    l &= l1;
                }
            }

            k1++;
            if (j != 0) {
                break;
            }
        }

        l &= j1 > 1 ? 1 : 0;
        i1 &= l;
        BlockState blockStateValue3 =
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockTripWireHook.ATTACHED,
                                Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(l)))
                        .withProperty(
                                net.minecraft.block.BlockTripWireHook.POWERED,
                                Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(i1)));
        if (j1 > 0) {
            BlockPos blockPosValue2 = blockPos.offset(facing, j1);
            Facing facingValue = facing.getOpposite();
            world.setBlockState(
                    blockPosValue2,
                    blockStateValue3.withProperty(
                            net.minecraft.block.BlockTripWireHook.FACING, facingValue),
                    3);
            this.func_176262_b(world, blockPosValue2, facingValue);
            this.method_3dc34bb1206d0017c483(
                    world,
                    blockPosValue2,
                    remix.compat.compiler.JvmBooleanSlots.fromInt(l),
                    remix.compat.compiler.JvmBooleanSlots.fromInt(i1),
                    remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                    value);
        }

        this.method_3dc34bb1206d0017c483(
                world,
                blockPos,
                remix.compat.compiler.JvmBooleanSlots.fromInt(l),
                remix.compat.compiler.JvmBooleanSlots.fromInt(i1),
                remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                value);
        if (!flag) {
            world.setBlockState(
                    blockPos,
                    blockStateValue3.withProperty(
                            net.minecraft.block.BlockTripWireHook.FACING, facing),
                    3);
            if (flag1) {
                this.func_176262_b(world, blockPos, facing);
            }
        }

        if (b0 != l) {
            int i2 = 1;

            while (i2 < j1) {
                BlockPos blockPosValue3 = blockPos.offset(facing, i2);
                BlockState blockStateValue4 = blockStateArray[i2];
                if (blockStateValue4 != null
                        && world.getBlockState(blockPosValue3).getBlock() != Blocks.air) {
                    world.setBlockState(
                            blockPosValue3,
                            blockStateValue4.withProperty(
                                    net.minecraft.block.BlockTripWireHook.ATTACHED,
                                    Boolean.valueOf(
                                            remix.compat.compiler.JvmBooleanSlots.fromInt(l))),
                            3);
                }

                i2++;
                if (j != 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        this.method_287452e54ed46f0c61a9(
                world,
                blockPos,
                blockState,
                false,
                false,
                -1,
                (net.minecraft.block.state.BlockState) null);
    }

    public BlockTripWireHook() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockTripWireHook.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockTripWireHook.POWERED, false)
                        .withProperty(net.minecraft.block.BlockTripWireHook.ATTACHED, false)
                        .withProperty(net.minecraft.block.BlockTripWireHook.SUSPENDED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public boolean canProvidePower() {
        return true;
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return facing.getAxis().isHorizontal()
                && world.getBlockState(blockPos.offset(facing.getOpposite()))
                        .getBlock()
                        .isNormalCube();
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((net.minecraft.util.Facing)
                                        blockState.getValue(
                                                net.minecraft.block.BlockTripWireHook.FACING))
                                .getHorizontalIndex();
        if ((java.lang.Boolean)
                blockState.getValue(net.minecraft.block.BlockTripWireHook.POWERED)) {
            i |= 8;
        }

        if ((java.lang.Boolean)
                blockState.getValue(net.minecraft.block.BlockTripWireHook.ATTACHED)) {
            i |= 4;
        }

        return i;
    }

    private boolean checkForDrop(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockPos)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
            return false;
        } else {
            return true;
        }
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        this.method_287452e54ed46f0c61a9(
                world,
                blockPos,
                blockState,
                false,
                true,
                -1,
                (net.minecraft.block.state.BlockState) null);
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState.withProperty(
                net.minecraft.block.BlockTripWireHook.SUSPENDED,
                !net.minecraft.world.World.doesBlockHaveSolidTopSurface(
                        iBlockAccess, blockPos.offsetDown()));
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this,
                new net.minecraft.block.properties.IProperty[] {
                    net.minecraft.block.BlockTripWireHook.FACING,
                    net.minecraft.block.BlockTripWireHook.POWERED,
                    net.minecraft.block.BlockTripWireHook.ATTACHED,
                    net.minecraft.block.BlockTripWireHook.SUSPENDED
                });
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public BlockState getStateFromMeta(int p171_0) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockTripWireHook.FACING,
                        net.minecraft.util.Facing.getHorizontal(p171_0 & 3))
                .withProperty(net.minecraft.block.BlockTripWireHook.POWERED, (p171_0 & 8) > 0)
                .withProperty(net.minecraft.block.BlockTripWireHook.ATTACHED, (p171_0 & 4) > 0);
    }
}
