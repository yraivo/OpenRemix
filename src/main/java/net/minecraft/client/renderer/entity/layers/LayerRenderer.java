package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.LivingEntity;

public interface LayerRenderer {
    boolean shouldCombineTextures();

    void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6);
}
