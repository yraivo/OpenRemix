package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockClay extends Block {
    public int quantityDropped(Random random) {
        return 4;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.clay_ball;
    }

    public BlockClay() {
        super(Material.clay);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
