package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider {

    private static ResourceLocation caveSpiderTextures;

    // region String decoding support

    // endregion

    public RenderCaveSpider(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize *= 0.7F;
    }

    protected ResourceLocation method_ec6d1a99b0951813f27e(EntityCaveSpider entityCaveSpider) {
        return net.minecraft.client.renderer.entity.RenderCaveSpider.caveSpiderTextures;
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_ec6d1a99b0951813f27e((EntityCaveSpider) entity);
    }

    protected ResourceLocation getEntityTexture(EntitySpider entitySpider) {
        return this.method_ec6d1a99b0951813f27e((EntityCaveSpider) entitySpider);
    }

    static {
        net.minecraft.client.renderer.entity.RenderCaveSpider.caveSpiderTextures =
                new ResourceLocation("textures/entity/spider/cave_spider.png");
    }

    protected void method_80ebbce8a314fb7da153(EntityCaveSpider entityCaveSpider, float f) {
        GlStateManager.scale(0.7F, 0.7F, 0.7F);
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.method_80ebbce8a314fb7da153((EntityCaveSpider) livingEntity, f);
    }
}
