package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;

public class RenderMagmaCube extends RenderLiving {
    private static ResourceLocation magmaCubeTextures;

    public RenderMagmaCube(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelMagmaCube(), 0.25F);
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntityMagmaCube) livingEntity, f);
    }

    static {
        net.minecraft.client.renderer.entity.RenderMagmaCube.magmaCubeTextures =
                new ResourceLocation("textures/entity/slime/magmacube.png");
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityMagmaCube) entity);
    }

    protected void preRenderCallback(EntityMagmaCube entityMagmaCube, float f) {
        int slimeSize = entityMagmaCube.getSlimeSize();
        float f1 =
                (entityMagmaCube.prevSquishFactor
                                + (entityMagmaCube.squishFactor - entityMagmaCube.prevSquishFactor)
                                        * f)
                        / (slimeSize * 0.5F + 1.0F);
        float f2 = 1.0F / (f1 + 1.0F);
        float f3 = slimeSize;
        GlStateManager.scale(f2 * f3, 1.0F / f2 * f3, f2 * f3);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityMagmaCube entityMagmaCube) {
        return net.minecraft.client.renderer.entity.RenderMagmaCube.magmaCubeTextures;
    }
}
