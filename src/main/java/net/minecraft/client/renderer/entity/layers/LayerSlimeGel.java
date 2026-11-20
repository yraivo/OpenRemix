package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;

public class LayerSlimeGel implements LayerRenderer {
    private RenderSlime slimeRenderer;
    private final ModelBase slimeModel = new ModelSlime(0);

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.doRenderLayer((SlimeEntity) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public LayerSlimeGel(RenderSlime aGkValue) {
        this.slimeRenderer = aGkValue;
    }

    public boolean shouldCombineTextures() {
        return true;
    }

    public void doRenderLayer(
            SlimeEntity slimeEntity,
            float f,
            float f1,
            float f6,
            float f2,
            float f3,
            float f4,
            float f5) {
        if (!slimeEntity.isInvisible()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(slimeEntity, f, f1, f2, f3, f4, f5);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }
}
