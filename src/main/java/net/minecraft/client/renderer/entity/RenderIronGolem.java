package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerIronGolemFlower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;

public class RenderIronGolem extends RenderLiving {

    private static ResourceLocation ironGolemTextures;

    public RenderIronGolem(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelIronGolem(), 0.5F);
        this.addLayer(new LayerIronGolemFlower(this));
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityIronGolem) entity);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.RenderIronGolem.ironGolemTextures =
                new ResourceLocation("textures/entity/iron_golem.png");
    }

    // region String decoding support

    // endregion

    protected void rotateCorpse(EntityIronGolem entityIronGolem, float f, float f1, float f2) {
        super.rotateCorpse(entityIronGolem, f, f1, f2);
        if (entityIronGolem.limbSwingAmount >= 0.01) {
            float f3 = 13.0F;
            float f4 = entityIronGolem.limbSwing - entityIronGolem.limbSwingAmount * (1.0F - f2) + 6.0F;
            float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
            GlStateManager.rotate(6.5F * f5, 0.0F, 0.0F, 1.0F);
        }
    }

    protected ResourceLocation getEntityTexture(EntityIronGolem entityIronGolem) {
        return net.minecraft.client.renderer.entity.RenderIronGolem.ironGolemTextures;
    }

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.rotateCorpse((EntityIronGolem) livingEntity, f, f1, f2);
    }
}
