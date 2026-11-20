package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

public class BlockCompressedPowered extends BlockCompressed {
    public boolean canProvidePower() {
        return true;
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return 15;
    }

    public BlockCompressedPowered(MapColor mapColor) {
        super(mapColor);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
