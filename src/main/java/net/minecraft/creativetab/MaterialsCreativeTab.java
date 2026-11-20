package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class MaterialsCreativeTab extends CreativeTabs {
    public Item getTabIconItem() {
        return Items.stick;
    }

    public MaterialsCreativeTab(int i, String text) {
        super(i, text);
    }
}
