package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class LayerSaddle implements LayerRenderer {
    private final ModelPig pigModel = new ModelPig(0.5F);
    private RenderPig pigRenderer;
    private static ResourceLocation TEXTURE;

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.doRenderLayer((EntityPig) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    // region String decoding support

    // endregion

    public LayerSaddle(RenderPig aGfValue) {
        this.pigRenderer = aGfValue;
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    static {
        net.minecraft.client.renderer.entity.layers.LayerSaddle.TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    }

    public void doRenderLayer(
            EntityPig entityPig,
            float f,
            float f1,
            float f6,
            float f2,
            float f3,
            float f4,
            float f5) {
        if (entityPig.getSaddled()) {
            this.pigRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerSaddle.TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(entityPig, f, f1, f2, f3, f4, f5);
        }
    }

    // region String decoding support

    // endregion
}
