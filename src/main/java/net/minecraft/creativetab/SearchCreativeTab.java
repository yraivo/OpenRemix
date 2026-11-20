package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class SearchCreativeTab extends CreativeTabs {
    public Item getTabIconItem() {
        return Items.compass;
    }

    public SearchCreativeTab(int i, String text) {
        super(i, text);
    }
}
