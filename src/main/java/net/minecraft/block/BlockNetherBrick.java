package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockNetherBrick extends Block {
    public MapColor getMapColor(BlockState blockState) {
        return MapColor.netherrackColor;
    }

    public BlockNetherBrick() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
