package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class RenderCow extends RenderLiving {

    private static ResourceLocation cowTextures;

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_88b05990ebf53502f129((EntityCow) entity);
    }

    static {
        net.minecraft.client.renderer.entity.RenderCow.cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
    }

    public RenderCow(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation method_88b05990ebf53502f129(EntityCow entityCow) {
        return net.minecraft.client.renderer.entity.RenderCow.cowTextures;
    }
}
