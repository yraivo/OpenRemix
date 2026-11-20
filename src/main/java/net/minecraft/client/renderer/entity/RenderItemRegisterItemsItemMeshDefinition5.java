package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class RenderItemRegisterItemsItemMeshDefinition5 implements ItemMeshDefinition {

    RenderItem aDz;


    public ModelResourceLocation getModelLocation(ItemStack itemStack) {
        return new ModelResourceLocation("banner", "inventory");
    }

    public RenderItemRegisterItemsItemMeshDefinition5(RenderItem aDzValue) {
        this.aDz = aDzValue;
    }
}
