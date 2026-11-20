package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig extends RenderLiving {

    private static ResourceLocation pigTextures;

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_c66b22a16e954e3ac39c((EntityPig) entity);
    }

    // region String decoding support

    // endregion

    public RenderPig(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
        this.addLayer(new LayerSaddle(this));
    }

    protected ResourceLocation method_c66b22a16e954e3ac39c(EntityPig entityPig) {
        return net.minecraft.client.renderer.entity.RenderPig.pigTextures;
    }

    static {
        net.minecraft.client.renderer.entity.RenderPig.pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
    }

    // region String decoding support

    // endregion
}
