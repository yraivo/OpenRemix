package net.minecraft.creativetab;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public final class DecorationsCreativeTab extends CreativeTabs {
    public DecorationsCreativeTab(int i, String text) {
        super(i, text);
    }

    public int getIconItemDamage() {
        return BlockDoublePlantEnumPlantType.PAEONIA.getMeta();
    }

    public Item getTabIconItem() {
        return Item.getItemFromBlock(Blocks.double_plant);
    }
}
