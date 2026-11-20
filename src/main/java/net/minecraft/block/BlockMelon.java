package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockMelon extends Block {
    public int quantityDroppedWithBonus(int p171_0, Random random) {
        return java.lang.Math.min(9, this.quantityDropped(random) + random.nextInt(1 + p171_0));
    }

    protected BlockMelon() {
        super(Material.gourd);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.melon;
    }

    public int quantityDropped(Random random) {
        return 3 + random.nextInt(5);
    }
}
