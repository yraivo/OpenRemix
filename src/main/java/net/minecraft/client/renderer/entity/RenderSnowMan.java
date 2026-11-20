package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerSnowmanHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;

public class RenderSnowMan extends RenderLiving {
    private static ResourceLocation snowManTextures;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_9a1fb8cba72d6e5e3f81((EntitySnowman) entity);
    }

    public RenderSnowMan(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelSnowMan(), 0.5F);
        this.addLayer(new LayerSnowmanHead(this));
    }

    public ModelBase getMainModel() {
        return this.Ad();
    }

    static {
        net.minecraft.client.renderer.entity.RenderSnowMan.snowManTextures = new ResourceLocation("textures/entity/snowman.png");
    }

    public ModelSnowMan Ad() {
        return (ModelSnowMan) super.getMainModel();
    }

    protected ResourceLocation method_9a1fb8cba72d6e5e3f81(EntitySnowman entitySnowman) {
        return net.minecraft.client.renderer.entity.RenderSnowMan.snowManTextures;
    }
}
