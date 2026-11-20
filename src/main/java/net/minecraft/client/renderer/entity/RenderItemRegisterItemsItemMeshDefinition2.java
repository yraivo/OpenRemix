package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class RenderItemRegisterItemsItemMeshDefinition2 implements ItemMeshDefinition {

    RenderItem aDz;

    public ModelResourceLocation getModelLocation(ItemStack itemStack) {
        return new ModelResourceLocation("spawn_egg", "inventory");
    }


    public RenderItemRegisterItemsItemMeshDefinition2(RenderItem aDzValue) {
        this.aDz = aDzValue;
    }
}
