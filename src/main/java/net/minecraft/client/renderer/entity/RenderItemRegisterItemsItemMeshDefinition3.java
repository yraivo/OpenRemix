package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class RenderItemRegisterItemsItemMeshDefinition3 implements ItemMeshDefinition {
    RenderItem aDz;

    public ModelResourceLocation getModelLocation(ItemStack itemStack) {
        return ItemPotion.isSplash(itemStack.getItemDamage())
                ? new ModelResourceLocation("bottle_splash", "inventory")
                : new ModelResourceLocation("bottle_drinkable", "inventory");
    }


    public RenderItemRegisterItemsItemMeshDefinition3(RenderItem aDzValue) {
        this.aDz = aDzValue;
    }
}
