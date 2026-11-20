package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBat extends RenderLiving {
    private static ResourceLocation batTextures;

    protected ResourceLocation method_53352696718da8a70114(BatEntity batEntity) {
        return net.minecraft.client.renderer.entity.RenderBat.batTextures;
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.method_36f8f544dd4d3761a7bd((BatEntity) livingEntity, f);
    }

    protected void method_36f8f544dd4d3761a7bd(BatEntity batEntity, float f) {
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
    }

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.rotateCorpse((BatEntity) livingEntity, f, f1, f2);
    }

    static {
        net.minecraft.client.renderer.entity.RenderBat.batTextures = new ResourceLocation("textures/entity/bat.png");
    }

    public RenderBat(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelBat(), 0.25F);
    }

    protected void rotateCorpse(BatEntity batEntity, float f, float f1, float f2) {
        {
            Render.getLegacyEntityRenderState();

            if (!batEntity.getIsBatHanging()) {
                GlStateManager.translate(0.0F, MathHelper.cos(f * 0.3F) * 0.1F, 0.0F);

                
            } else {

                GlStateManager.translate(0.0F, -0.1F, 0.0F);
            }
        }

        super.rotateCorpse(batEntity, f, f1, f2);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_53352696718da8a70114((BatEntity) entity);
    }
}
