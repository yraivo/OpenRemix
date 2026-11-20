package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

class SkeletonArmorLayer extends LayerBipedArmor {
    RenderSkeleton aDU;

    protected void initArmor() {
        this.field_177189_c = new ModelSkeleton(0.5F, true);
        this.field_177186_d = new ModelSkeleton(1.0F, true);
    }

    SkeletonArmorLayer(RenderSkeleton aDUValue, RendererLivingEntity rendererLivingEntity) {
        super(rendererLivingEntity);
        this.aDU = aDUValue;
    }
}
