package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped {

    private static ResourceLocation ZOMBIE_PIGMAN_TEXTURE;

    public RenderPigZombie(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelZombie(), 0.5F, 1.0F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new PigZombieArmorLayer(this, this));
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
        return this.method_19949b58801094063b49((EntityPigZombie) entityLiving);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation method_19949b58801094063b49(EntityPigZombie entityPigZombie) {
        return net.minecraft.client.renderer.entity.RenderPigZombie.ZOMBIE_PIGMAN_TEXTURE;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_19949b58801094063b49((EntityPigZombie) entity);
    }

    static {
        net.minecraft.client.renderer.entity.RenderPigZombie.ZOMBIE_PIGMAN_TEXTURE =
                new ResourceLocation("textures/entity/zombie_pigman.png");
    }
}
