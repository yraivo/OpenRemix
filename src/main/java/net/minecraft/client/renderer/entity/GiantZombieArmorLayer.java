package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

class GiantZombieArmorLayer extends LayerBipedArmor {
    RenderGiantZombie aDS;

    protected void initArmor() {
        this.field_177189_c = new ModelZombie(0.5F, true);
        this.field_177186_d = new ModelZombie(1.0F, true);
    }

    GiantZombieArmorLayer(RenderGiantZombie aDSValue, RendererLivingEntity rendererLivingEntity) {
        super(rendererLivingEntity);
        this.aDS = aDSValue;
    }
}
