package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCompressed extends Block {
    private MapColor mapColor;

    public MapColor getMapColor(BlockState blockState) {
        return this.mapColor;
    }

    public BlockCompressed(MapColor ahdValue) {
        super(Material.iron);
        this.mapColor = ahdValue;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
