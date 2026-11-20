package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class FoodCreativeTab extends CreativeTabs {
    public Item getTabIconItem() {
        return Items.apple;
    }

    public FoodCreativeTab(int i, String text) {
        super(i, text);
    }
}
