package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends Block {
    public static PropertyDirection FACING;

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        Facing facing = Facing.getFront(i);
        if (facing.getAxis() == FacingAxis.Y) {
            facing = Facing.NORTH;
        }

        return this.getDefaultState().withProperty(net.minecraft.block.BlockLadder.FACING, facing);
    }

    public boolean isFullCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        int i = j;
        if (blockState.getBlock() == this) {
            float f = 0.125F;
            switch (((Facing) blockState.getValue(net.minecraft.block.BlockLadder.FACING))) {
                case NORTH:
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                    if (i == 0) {
                        break;
                    }
                case SOUTH:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                    if (i == 0) {
                        break;
                    }
                case WEST:
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    if (i == 0) {
                        break;
                    }
                case EAST:
                default:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
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
        if (facing.getAxis().isHorizontal() && this.canBlockStay(world, blockPos, facing)) {
            return this.getDefaultState()
                    .withProperty(net.minecraft.block.BlockLadder.FACING, facing);
        }

        for (java.util.Iterator<?> __recoveryIterator171_4256 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_4256).hasNext(); ) {
            Facing facingValue = (Facing) (__recoveryIterator171_4256).next();
            if (this.canBlockStay(world, blockPos, facingValue)) {
                return this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockLadder.FACING, facingValue);
            }
        }

        return this.getDefaultState();
    }

    protected boolean canBlockStay(World world, BlockPos blockPos, Facing facing) {
        return world.getBlockState(blockPos.offset(facing.getOpposite())).getBlock().isNormalCube();
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos.offsetWest()).getBlock().isNormalCube()
                ? true
                : (world.getBlockState(blockPos.offsetEast()).getBlock().isNormalCube()
                        ? true
                        : (world.getBlockState(blockPos.offsetNorth()).getBlock().isNormalCube()
                                ? true
                                : world.getBlockState(blockPos.offsetSouth())
                                        .getBlock()
                                        .isNormalCube()));
    }

    static {
        net.minecraft.block.BlockLadder.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected BlockLadder() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockLadder.FACING, Facing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        return ((Facing) blockState.getValue(net.minecraft.block.BlockLadder.FACING)).getIndex();
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockLadder.FACING);
        if (!this.canBlockStay(world, blockPos, facing)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }

        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockLadder.FACING});
    }
}
