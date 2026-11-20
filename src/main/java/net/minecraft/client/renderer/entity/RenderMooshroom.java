package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.util.ResourceLocation;

public class RenderMooshroom extends RenderLiving {
    private static ResourceLocation mooshroomTextures;

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.RenderMooshroom.mooshroomTextures =
                new ResourceLocation("textures/entity/cow/mooshroom.png");
    }

    protected ResourceLocation method_f85e399b19c5fab382e7(EntityMooshroom entityMooshroom) {
        return net.minecraft.client.renderer.entity.RenderMooshroom.mooshroomTextures;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_f85e399b19c5fab382e7((EntityMooshroom) entity);
    }

    // region String decoding support

    // endregion

    public RenderMooshroom(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
        this.addLayer(new LayerMooshroomMushroom(this));
    }
}
