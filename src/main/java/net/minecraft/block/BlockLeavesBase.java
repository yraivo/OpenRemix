package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import java.util.IdentityHashMap;
import java.util.Map;

public class BlockLeavesBase extends Block {
    private static Map mapOriginalOpacity = new IdentityHashMap();
    protected boolean fancyGraphics;

    public static void restoreLightOpacity(Block block) {
        if (net.minecraft.block.BlockLeavesBase.mapOriginalOpacity.containsKey(block)) {
            int i = (java.lang.Integer) net.minecraft.block.BlockLeavesBase.mapOriginalOpacity.get(block);
            net.minecraft.block.BlockLeavesBase.setLightOpacity(block, i);
        }
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return optifine.OptiFineConfig.isCullFacesLeaves()
                        && iBlockAccess.getBlockState(blockPos).getBlock() == this
                ? false
                : super.shouldSideBeRendered(iBlockAccess, blockPos, facing);
    }

    protected BlockLeavesBase(Material material, boolean fancyGraphicsValue) {
        super(material);
        this.fancyGraphics = fancyGraphicsValue;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public static void setLightOpacity(Block block, int p171_1) {
        if (!net.minecraft.block.BlockLeavesBase.mapOriginalOpacity.containsKey(block)) {
            net.minecraft.block.BlockLeavesBase.mapOriginalOpacity.put(block, block.getLightOpacity());
        }

        block.setLightOpacity(p171_1);
    }
}
