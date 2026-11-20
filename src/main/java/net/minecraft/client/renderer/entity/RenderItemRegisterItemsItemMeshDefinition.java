package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class RenderItemRegisterItemsItemMeshDefinition implements ItemMeshDefinition {

    RenderItem aDz;

    public ModelResourceLocation getModelLocation(ItemStack itemStack) {
        return new ModelResourceLocation("enchanted_book", "inventory");
    }

    public RenderItemRegisterItemsItemMeshDefinition(RenderItem aDzValue) {
        this.aDz = aDzValue;
    }


}
