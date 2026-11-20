package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSlime extends RenderLiving {
    private static ResourceLocation slimeTextures;

    protected ResourceLocation getEntityTexture(SlimeEntity slimeEntity) {
        return net.minecraft.client.renderer.entity.RenderSlime.slimeTextures;
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((SlimeEntity) livingEntity, f);
    }

    public void doRender(
            SlimeEntity slimeEntity, double d0, double d1, double d2, float f, float f1) {
        this.shadowSize = 0.25F * slimeEntity.getSlimeSize();
        super.doRender(slimeEntity, d0, d1, d2, f, f1);
    }

    static {
        net.minecraft.client.renderer.entity.RenderSlime.slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
    }

    public RenderSlime(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
        this.addLayer(new LayerSlimeGel(this));
    }

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.doRender((SlimeEntity) entityLiving, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((SlimeEntity) entity);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((SlimeEntity) entity, d0, d1, d2, f, f1);
    }

    protected void preRenderCallback(SlimeEntity slimeEntity, float f) {
        float slimeSize = slimeEntity.getSlimeSize();
        float f2 =
                (slimeEntity.prevSquishFactor
                                + (slimeEntity.squishFactor - slimeEntity.prevSquishFactor) * f)
                        / (slimeSize * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * slimeSize, 1.0F / f3 * slimeSize, f3 * slimeSize);
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((SlimeEntity) livingEntity, d0, d1, d2, f, f1);
    }
}
