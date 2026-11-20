package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockBannerBlockBannerStanding extends BlockBanner {
    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this, new net.minecraft.block.properties.IProperty[] {net.minecraft.block.BlockBanner.ROTATION_PROP});
    }

    public int getMetaFromState(BlockState blockState) {
        return (java.lang.Integer) blockState.getValue(net.minecraft.block.BlockBanner.ROTATION_PROP);
    }

    public BlockState getStateFromMeta(int p171_0) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockBanner.ROTATION_PROP, p171_0);
    }

    public BlockBannerBlockBannerStanding() {
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockBanner.ROTATION_PROP, 0));
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }

        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }
}
