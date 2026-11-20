package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block {
    private boolean ignoreSimilarity;

    public boolean isOpaqueCube() {
        return false;
    }

    protected BlockBreakable(Material material, boolean ignoreSimilarityValue) {
        super(material);
        this.ignoreSimilarity = ignoreSimilarityValue;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (this == Blocks.glass || this == Blocks.stained_glass) {
            if (iBlockAccess.getBlockState(blockPos.offset(facing.getOpposite())) != blockState) {
                return true;
            }

            if (block == this) {
                return false;
            }
        }

        return !this.ignoreSimilarity && block == this
                ? false
                : super.shouldSideBeRendered(iBlockAccess, blockPos, facing);
    }
}
