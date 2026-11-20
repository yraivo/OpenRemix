package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockObsidian extends Block {
    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }

    public BlockObsidian() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public MapColor getMapColor(BlockState blockState) {
        return MapColor.obsidianColor;
    }
}
