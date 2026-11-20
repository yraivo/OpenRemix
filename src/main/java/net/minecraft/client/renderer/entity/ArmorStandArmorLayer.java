package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

class ArmorStandArmorLayer extends LayerBipedArmor {
    ArmorStandRenderer aDR;

    protected void initArmor() {
        this.field_177189_c = new ModelArmorStandArmor(0.5F);
        this.field_177186_d = new ModelArmorStandArmor(1.0F);
    }

    ArmorStandArmorLayer(ArmorStandRenderer aDRValue, RendererLivingEntity rendererLivingEntity) {
        super(rendererLivingEntity);
        this.aDR = aDRValue;
    }
}
