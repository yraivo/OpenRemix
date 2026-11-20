package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public final class InventoryCreativeTab extends CreativeTabs {
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.chest);
    }

    public InventoryCreativeTab(int i, String text) {
        super(i, text);
    }
}
