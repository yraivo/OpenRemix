package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;

public class RenderGiantZombie extends RenderLiving {
    private float scale;
    private static ResourceLocation zombieTextures;

    protected void preRenderCallback(EntityGiantZombie entityGiantZombie, float f) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    static {
        net.minecraft.client.renderer.entity.RenderGiantZombie.zombieTextures =
                new ResourceLocation("textures/entity/zombie/zombie.png");
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntityGiantZombie) livingEntity, f);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityGiantZombie) entity);
    }

    public RenderGiantZombie(
            RenderManager renderManagerValue, ModelBase modelBase, float f, float scaleValue) {
        super(renderManagerValue, modelBase, f * scaleValue);
        this.scale = scaleValue;
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new GiantZombieArmorLayer(this, this));
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityGiantZombie entityGiantZombie) {
        return net.minecraft.client.renderer.entity.RenderGiantZombie.zombieTextures;
    }
}
