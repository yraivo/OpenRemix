package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class MiscCreativeTab extends CreativeTabs {
    public MiscCreativeTab(int i, String text) {
        super(i, text);
    }

    public Item getTabIconItem() {
        return Items.lava_bucket;
    }
}
