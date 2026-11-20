package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSign extends BlockSign {

    public static PropertyDirection FACING;

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockWallSign.FACING});
    }

    public int getMetaFromState(BlockState blockState) {
        return ((Facing) blockState.getValue(net.minecraft.block.BlockWallSign.FACING)).getIndex();
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        Facing facing = Facing.getFront(i);
        if (facing.getAxis() == FacingAxis.Y) {
            facing = Facing.NORTH;
        }

        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockWallSign.FACING, facing);
    }

    public BlockWallSign() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockWallSign.FACING, Facing.NORTH));
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        Facing facing =
                (Facing)
                        iBlockAccess
                                .getBlockState(blockPos)
                                .getValue(net.minecraft.block.BlockWallSign.FACING);
        float f = 0.28125F;
        float f1 = 0.78125F;
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        float f2 = 0.0F;
        int i = j;
        float f3 = 1.0F;
        float f4 = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        switch (facing) {
            case NORTH:
                this.setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
                if (i != 0) {
                    break;
                }
            case SOUTH:
                this.setBlockBounds(f2, f, 0.0F, f3, f1, f4);
                if (i != 0) {
                    break;
                }
            case WEST:
                this.setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
                if (i != 0) {
                    break;
                }
            case EAST:
                this.setBlockBounds(0.0F, f, f2, f4, f1, f3);
        }
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockWallSign.FACING);
        if (!world.getBlockState(blockPos.offset(facing.getOpposite()))
                .getBlock()
                .getMaterial()
                .isSolid()) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }

        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockWallSign.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
    }
}
