package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class ToolsCreativeTab extends CreativeTabs {
    public ToolsCreativeTab(int i, String text) {
        super(i, text);
    }

    @Override
    public Item getTabIconItem() {
        return Items.iron_axe;
    }
}
