package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends RenderLiving {

    private static ResourceLocation shearedSheepTextures;

    protected ResourceLocation getEntityTexture(EntitySheep entitySheep) {
        return net.minecraft.client.renderer.entity.RenderSheep.shearedSheepTextures;
    }

    static {
        net.minecraft.client.renderer.entity.RenderSheep.shearedSheepTextures =
                new ResourceLocation("textures/entity/sheep/sheep.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntitySheep) entity);
    }

    public RenderSheep(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
        this.addLayer(new LayerSheepWool(this));
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
