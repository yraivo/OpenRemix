package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderWolf extends RenderLiving {
    private static ResourceLocation wolfTextures;

    private static ResourceLocation anrgyWolfTextures;
    private static ResourceLocation tamedWolfTextures;

    public void method_fa3a806be9d1d855907c(
            EntityWolf entityWolf, double d0, double d1, double d2, float f, float f1) {
        if (entityWolf.isWolfWet()) {
            float f2 = entityWolf.getBrightness(f1) * entityWolf.getShadingWhileWet(f1);
            GlStateManager.color(f2, f2, f2);
        }

        super.doRender(entityWolf, d0, d1, d2, f, f1);
    }

    protected float method_10ceaddbed86458cab73(EntityWolf entityWolf, float f) {
        return entityWolf.getTailRotation();
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.method_fa3a806be9d1d855907c((EntityWolf) livingEntity, d0, d1, d2, f, f1);
    }

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.method_fa3a806be9d1d855907c((EntityWolf) entityLiving, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.method_10ceaddbed86458cab73((EntityWolf) livingEntity, f);
    }

    protected ResourceLocation getEntityTexture(EntityWolf entityWolf) {
        return entityWolf.isTamed() ? net.minecraft.client.renderer.entity.RenderWolf.tamedWolfTextures : (entityWolf.isAngry() ? net.minecraft.client.renderer.entity.RenderWolf.anrgyWolfTextures : net.minecraft.client.renderer.entity.RenderWolf.wolfTextures);
    }

    public RenderWolf(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
        this.addLayer(new LayerWolfCollar(this));
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_fa3a806be9d1d855907c((EntityWolf) entity, d0, d1, d2, f, f1);
    }

    static {
        net.minecraft.client.renderer.entity.RenderWolf.wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
        net.minecraft.client.renderer.entity.RenderWolf.tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
        net.minecraft.client.renderer.entity.RenderWolf.anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityWolf) entity);
    }

    // region String decoding support

    // endregion
}
