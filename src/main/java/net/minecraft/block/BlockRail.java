package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
    public static PropertyEnum SHAPE;

    public int getMetaFromState(BlockState blockState) {
        return ((BlockRailBaseEnumRailDirection) blockState.getValue(net.minecraft.block.BlockRail.SHAPE))
                .getMetadata();
    }

    static {
        net.minecraft.block.BlockRail.SHAPE = PropertyEnum.create("shape", BlockRailBaseEnumRailDirection.class);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockRail.SHAPE, BlockRailBaseEnumRailDirection.byMetadata(i));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockRail.SHAPE});
    }

    // region String decoding support

    // endregion

    public IProperty getShapeProperty() {
        return net.minecraft.block.BlockRail.SHAPE;
    }

    // region String decoding support

    // endregion

    protected void onNeighborChangedInternal(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (block.canProvidePower()
                && new BlockRailBaseRail(this, world, blockPos, blockState).countAdjacentRails()
                        == 3) {
            this.func_176564_a(world, blockPos, blockState, false);
        }
    }

    protected BlockRail() {
        super(false);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockRail.SHAPE, BlockRailBaseEnumRailDirection.NORTH_SOUTH));
    }
}
