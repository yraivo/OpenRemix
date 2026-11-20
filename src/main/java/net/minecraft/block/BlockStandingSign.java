package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockStandingSign extends BlockSign {

    public static PropertyInteger ROTATION_PROP;

    // region String decoding support

    // endregion

    public BlockStandingSign() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockStandingSign.ROTATION_PROP, 0));
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockStandingSign.ROTATION_PROP, i);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }

        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockStandingSign.ROTATION_PROP);
    }

    static {
        net.minecraft.block.BlockStandingSign.ROTATION_PROP =
                PropertyInteger.create("rotation", 0, 15);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockStandingSign.ROTATION_PROP});
    }
}
