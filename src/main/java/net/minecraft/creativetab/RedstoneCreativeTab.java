package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class RedstoneCreativeTab extends CreativeTabs {
    public RedstoneCreativeTab(int i, String text) {
        super(i, text);
    }

    public Item getTabIconItem() {
        return Items.redstone;
    }
}
