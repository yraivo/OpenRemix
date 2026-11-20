package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockBookshelf extends Block {
    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.book;
    }

    public BlockBookshelf() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int quantityDropped(Random random) {
        return 3;
    }
}
