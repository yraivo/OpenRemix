package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;

import java.util.List;

public class ItemCoal extends Item {

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return itemStack.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
    }


    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    public ItemCoal() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}
