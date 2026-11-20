package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public final class TransportationCreativeTab extends CreativeTabs {
    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.golden_rail);
    }

    public TransportationCreativeTab(int i, String text) {
        super(i, text);
    }
}
