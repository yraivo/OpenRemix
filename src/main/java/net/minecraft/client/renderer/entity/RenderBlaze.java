package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;

public class RenderBlaze extends RenderLiving {

    private static ResourceLocation blazeTextures;

    static {
        net.minecraft.client.renderer.entity.RenderBlaze.blazeTextures = new ResourceLocation("textures/entity/blaze.png");
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityBlaze entityBlaze) {
        return net.minecraft.client.renderer.entity.RenderBlaze.blazeTextures;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityBlaze) entity);
    }

    public RenderBlaze(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelBlaze(), 0.5F);
    }

    // region String decoding support

    // endregion
}
