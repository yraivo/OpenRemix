package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;

public class RenderGhast extends RenderLiving {

    private static ResourceLocation ghastTextures;
    private static ResourceLocation ghastShootingTextures;

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntityGhast) livingEntity, f);
    }

    protected void preRenderCallback(EntityGhast entityGhast, float f3) {
        float f = 1.0F;
        float f1 = (8.0F + f) / 2.0F;
        float f2 = (8.0F + 1.0F / f) / 2.0F;
        GlStateManager.scale(f2, f1, f2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    static {
        net.minecraft.client.renderer.entity.RenderGhast.ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
        net.minecraft.client.renderer.entity.RenderGhast.ghastShootingTextures =
                new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityGhast) entity);
    }

    public RenderGhast(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelGhast(), 0.5F);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityGhast entityGhast) {
        return entityGhast.isAttacking()
                ? net.minecraft.client.renderer.entity.RenderGhast.ghastShootingTextures
                : net.minecraft.client.renderer.entity.RenderGhast.ghastTextures;
    }

    // region String decoding support

    // endregion
}
