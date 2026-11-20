package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLever extends Block {

    public static PropertyEnum FACING;
    public static PropertyBool POWERED;

    public static int getMetadataForFacing(Facing facing) {
        switch (facing) {
            case DOWN:
                return 0;
            case UP:
                return 5;
            case NORTH:
                return 4;
            case SOUTH:
                return 3;
            case WEST:
                return 2;
            case EAST:
                return 1;
            default:
                return -1;
        }
    }

    public boolean canProvidePower() {
        return true;
    }

    protected boolean func_176358_d(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().isNormalCube();
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
        if (world.isRemote) {
            return true;
        }

        blockState = blockState.cycleProperty(net.minecraft.block.BlockLever.POWERED);
        world.setBlockState(blockPos, blockState, 3);
        world.playSoundEffect(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                "random.click",
                0.3F,
                ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockLever.POWERED))
                        ? 0.6F
                        : 0.5F);
        world.notifyNeighborsOfStateChange(blockPos, this);
        Facing facingValue =
                ((BlockLeverEnumOrientation)
                                blockState.getValue(net.minecraft.block.BlockLever.FACING))
                        .getFacing();
        world.notifyNeighborsOfStateChange(blockPos.offset(facingValue.getOpposite()), this);
        return true;
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockLeverEnumOrientation)
                                        blockState.getValue(net.minecraft.block.BlockLever.FACING))
                                .getMetadata();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockLever.POWERED)) {
            i |= 8;
        }

        return i;
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
                this.getDefaultState().withProperty(net.minecraft.block.BlockLever.POWERED, false);
        if (this.func_176358_d(world, blockPos.offset(facing.getOpposite()))) {
            return blockState.withProperty(
                    net.minecraft.block.BlockLever.FACING,
                    BlockLeverEnumOrientation.forFacings(
                            facing, livingEntity.getHorizontalFacing()));
        }

        for (java.util.Iterator<?> __recoveryIterator171_5025 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_5025).hasNext(); ) {
            Facing facingValue = (Facing) (__recoveryIterator171_5025).next();
            if (facingValue != facing
                    && this.func_176358_d(world, blockPos.offset(facingValue.getOpposite()))) {
                return blockState.withProperty(
                        net.minecraft.block.BlockLever.FACING,
                        BlockLeverEnumOrientation.forFacings(
                                facingValue, livingEntity.getHorizontalFacing()));
            }
        }

        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                ? blockState.withProperty(
                        net.minecraft.block.BlockLever.FACING,
                        BlockLeverEnumOrientation.forFacings(
                                Facing.UP, livingEntity.getHorizontalFacing()))
                : blockState;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected BlockLever() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockLever.FACING,
                                BlockLeverEnumOrientation.NORTH)
                        .withProperty(net.minecraft.block.BlockLever.POWERED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockLever.FACING,
                        BlockLeverEnumOrientation.byMetadata(i & 7))
                .withProperty(net.minecraft.block.BlockLever.POWERED, (i & 8) > 0);
    }

    private boolean func_176356_e(World world, BlockPos blockPos) {
        if (this.canPlaceBlockAt(world, blockPos)) {
            return true;
        }

        this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
        world.setBlockToAir(blockPos);
        return false;
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return !((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockLever.POWERED))
                ? 0
                : (((BlockLeverEnumOrientation)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockLever.FACING))
                                        .getFacing()
                                == facing
                        ? 15
                        : 0);
    }

    static {
        net.minecraft.block.BlockLever.FACING =
                PropertyEnum.create("facing", BlockLeverEnumOrientation.class);
        net.minecraft.block.BlockLever.POWERED = PropertyBool.create("powered");
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockLever.FACING, net.minecraft.block.BlockLever.POWERED
                });
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return this.func_176358_d(world, blockPos.offsetWest())
                ? true
                : (this.func_176358_d(world, blockPos.offsetEast())
                        ? true
                        : (this.func_176358_d(world, blockPos.offsetNorth())
                                ? true
                                : (this.func_176358_d(world, blockPos.offsetSouth())
                                        ? true
                                        : (World.doesBlockHaveSolidTopSurface(
                                                        world, blockPos.offsetDown())
                                                ? true
                                                : this.func_176358_d(
                                                        world, blockPos.offsetUp())))));
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return facing == Facing.UP
                        && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                ? true
                : this.func_176358_d(world, blockPos.offset(facing.getOpposite()));
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (this.func_176356_e(world, blockPos)
                && !this.func_176358_d(
                        world,
                        blockPos.offset(
                                ((BlockLeverEnumOrientation)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockLever.FACING))
                                        .getFacing()
                                        .getOpposite()))) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        float f = 0.1875F;
        int i = j;
        switch (((BlockLeverEnumOrientation)
                iBlockAccess
                        .getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockLever.FACING))) {
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
                if (i == 0) {
                    break;
                }
            case UP_Z:
            case UP_X:
                f = 0.25F;
                this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
                if (i == 0) {
                    break;
                }
            case DOWN_X:
            case DOWN_Z:
                f = 0.25F;
                this.setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        }
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockLever.POWERED)) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            Facing facing =
                    ((BlockLeverEnumOrientation)
                                    blockState.getValue(net.minecraft.block.BlockLever.FACING))
                            .getFacing();
            world.notifyNeighborsOfStateChange(blockPos.offset(facing.getOpposite()), this);
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockLever.POWERED))
                ? 15
                : 0;
    }
}
