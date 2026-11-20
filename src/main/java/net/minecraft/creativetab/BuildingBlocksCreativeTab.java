package net.minecraft.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public final class BuildingBlocksCreativeTab extends CreativeTabs {
    public BuildingBlocksCreativeTab(int i, String text) {
        super(i, text);
    }

    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.brick_block);
    }
}
