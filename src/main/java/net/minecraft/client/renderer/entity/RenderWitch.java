package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.ResourceLocation;

public class RenderWitch extends RenderLiving {
    private static ResourceLocation witchTextures;

    public void method_8e7230864846c5e16d5f(
            EntityWitch entityWitch, double d0, double d1, double d2, float f, float f1) {
        ((ModelWitch) this.mainModel).jj =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (entityWitch.getHeldItem() != null ? 1 : 0));
        super.doRender(entityWitch, d0, d1, d2, f, f1);
    }

    protected void preRenderCallback(EntityWitch entityWitch, float f1) {
        float f = 0.9375F;
        GlStateManager.scale(f, f, f);
    }

    // region String decoding support

    // endregion

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_8e7230864846c5e16d5f((EntityWitch) entity, d0, d1, d2, f, f1);
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.method_8e7230864846c5e16d5f((EntityWitch) livingEntity, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    public RenderWitch(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelWitch(0.0F), 0.5F);
        this.addLayer(new LayerHeldItemWitch(this));
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntityWitch) livingEntity, f);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_97f2e373c8d5d9fa6ccf((EntityWitch) entity);
    }

    protected ResourceLocation method_97f2e373c8d5d9fa6ccf(EntityWitch entityWitch) {
        return net.minecraft.client.renderer.entity.RenderWitch.witchTextures;
    }

    static {
        net.minecraft.client.renderer.entity.RenderWitch.witchTextures =
                new ResourceLocation("textures/entity/witch.png");
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.method_8e7230864846c5e16d5f((EntityWitch) entityLiving, d0, d1, d2, f, f1);
    }
}
