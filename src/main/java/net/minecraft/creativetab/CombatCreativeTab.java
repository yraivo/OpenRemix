package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class CombatCreativeTab extends CreativeTabs {
    public Item getTabIconItem() {
        return Items.golden_sword;
    }

    public CombatCreativeTab(int i, String text) {
        super(i, text);
    }
}
