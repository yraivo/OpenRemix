package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderSkeleton extends RenderBiped {
    private static ResourceLocation witherSkeletonTextures;
    private static ResourceLocation skeletonTextures;

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
        return this.getEntityTexture((EntitySkeleton) entityLiving);
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntitySkeleton) livingEntity, f);
    }

    static {
        net.minecraft.client.renderer.entity.RenderSkeleton.skeletonTextures =
                new ResourceLocation("textures/entity/skeleton/skeleton.png");
        net.minecraft.client.renderer.entity.RenderSkeleton.witherSkeletonTextures =
                new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }

    public RenderSkeleton(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelSkeleton(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new SkeletonArmorLayer(this, this));
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntitySkeleton) entity);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntitySkeleton entitySkeleton) {
        return entitySkeleton.getSkeletonType() == 1
                ? net.minecraft.client.renderer.entity.RenderSkeleton.witherSkeletonTextures
                : net.minecraft.client.renderer.entity.RenderSkeleton.skeletonTextures;
    }

    protected void preRenderCallback(EntitySkeleton entitySkeleton, float f) {
        if (entitySkeleton.getSkeletonType() == 1) {
            GlStateManager.scale(1.2F, 1.2F, 1.2F);
        }
    }

    // region String decoding support

    // endregion
}
