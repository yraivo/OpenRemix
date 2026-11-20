package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSpider extends RenderLiving {

    private static ResourceLocation spiderTextures;

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntitySpider entitySpider) {
        return net.minecraft.client.renderer.entity.RenderSpider.spiderTextures;
    }

    protected float getDeathMaxRotation(EntitySpider entitySpider) {
        return 180.0F;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntitySpider) entity);
    }

    public RenderSpider(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelSpider(), 1.0F);
        this.addLayer(new LayerSpiderEyes(this));
    }

    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return this.getDeathMaxRotation((EntitySpider) livingEntity);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.RenderSpider.spiderTextures =
                new ResourceLocation("textures/entity/spider/spider.png");
    }
}
