package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class RenderItemRegisterItemsItemMeshDefinition4 implements ItemMeshDefinition {

    RenderItem aDz;

    public RenderItemRegisterItemsItemMeshDefinition4(RenderItem aDzValue) {
        this.aDz = aDzValue;
    }

    public ModelResourceLocation getModelLocation(ItemStack itemStack) {
        return new ModelResourceLocation("filled_map", "inventory");
    }


}
