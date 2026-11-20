package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;

public class RenderWither extends RenderLiving {

    private static ResourceLocation invulnerableWitherTextures;
    private static ResourceLocation witherTextures;

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityWither) entity);
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.method_de8deb42696878dea854((EntityWither) livingEntity, f);
    }

    static {
        net.minecraft.client.renderer.entity.RenderWither.invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
        net.minecraft.client.renderer.entity.RenderWither.witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    }

    // region String decoding support

    // endregion

    public void method_a84ea2a457c3ec68f839(
            EntityWither entityWither, double d0, double d1, double d2, float f, float f1) {
        BossStatus.setBossStatus(entityWither, true);
        super.doRender(entityWither, d0, d1, d2, f, f1);
    }

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.method_a84ea2a457c3ec68f839((EntityWither) entityLiving, d0, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_a84ea2a457c3ec68f839((EntityWither) entity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(EntityWither entityWither) {
        int invulTime = entityWither.getInvulTime();
        return invulTime <= 0 || invulTime <= 80 && invulTime / 5 % 2 == 1 ? net.minecraft.client.renderer.entity.RenderWither.witherTextures : net.minecraft.client.renderer.entity.RenderWither.invulnerableWitherTextures;
    }

    protected void method_de8deb42696878dea854(EntityWither entityWither, float f) {
        float f1 = 2.0F;
        int invulTime = entityWither.getInvulTime();
        if (invulTime > 0) {
            f1 -= (invulTime - f) / 220.0F * 0.5F;
        }

        GlStateManager.scale(f1, f1, f1);
    }

    // region String decoding support

    // endregion

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.method_a84ea2a457c3ec68f839((EntityWither) livingEntity, d0, d1, d2, f, f1);
    }

    public RenderWither(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelWither(0.0F), 1.0F);
        this.addLayer(new LayerWitherAura(this));
    }
}
