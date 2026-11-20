package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

import java.util.Random;

public class BlockGlass extends BlockBreakable {
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean isFullCube() {
        return false;
    }

    public BlockGlass(Material material, boolean flag) {
        super(material, flag);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    protected boolean canSilkHarvest() {
        return true;
    }

    public int quantityDropped(Random random) {
        return 0;
    }
}
