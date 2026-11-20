package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class RenderOcelot extends RenderLiving {
    private static ResourceLocation redOcelotTextures;
    private static ResourceLocation ocelotTextures;

    private static ResourceLocation blackOcelotTextures;
    private static ResourceLocation siameseOcelotTextures;

    // region String decoding support

    // endregion

    protected void preRenderCallback(EntityOcelot entityOcelot, float f) {
        super.preRenderCallback(entityOcelot, f);
        if (entityOcelot.isTamed()) {
            GlStateManager.scale(0.8F, 0.8F, 0.8F);
        }
    }

    static {
        net.minecraft.client.renderer.entity.RenderOcelot.blackOcelotTextures =
                new ResourceLocation("textures/entity/cat/black.png");
        net.minecraft.client.renderer.entity.RenderOcelot.ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
        net.minecraft.client.renderer.entity.RenderOcelot.redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
        net.minecraft.client.renderer.entity.RenderOcelot.siameseOcelotTextures =
                new ResourceLocation("textures/entity/cat/siamese.png");
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntityOcelot) livingEntity, f);
    }

    protected ResourceLocation getEntityTexture(EntityOcelot entityOcelot) {
        switch (entityOcelot.getTameSkin()) {
            case 0:
            default:
                return net.minecraft.client.renderer.entity.RenderOcelot.ocelotTextures;
            case 1:
                return net.minecraft.client.renderer.entity.RenderOcelot.blackOcelotTextures;
            case 2:
                return net.minecraft.client.renderer.entity.RenderOcelot.redOcelotTextures;
            case 3:
                return net.minecraft.client.renderer.entity.RenderOcelot.siameseOcelotTextures;
        }
    }

    public RenderOcelot(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityOcelot) entity);
    }
}
