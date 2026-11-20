package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderChicken extends RenderLiving {

    private static ResourceLocation chickenTextures;

    protected float method_b17072dc0ce7b0480e20(EntityChicken entityChicken, float f) {
        float f1 = entityChicken.bfx + (entityChicken.wingRotation - entityChicken.bfx) * f;
        float f2 = entityChicken.bfw + (entityChicken.destPos - entityChicken.bfw) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    // region String decoding support

    // endregion

    public RenderChicken(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
    }

    static {
        net.minecraft.client.renderer.entity.RenderChicken.chickenTextures = new ResourceLocation("textures/entity/chicken.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_790de5b5b00a3912ff5e((EntityChicken) entity);
    }

    // region String decoding support

    // endregion

    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.method_b17072dc0ce7b0480e20((EntityChicken) livingEntity, f);
    }

    protected ResourceLocation method_790de5b5b00a3912ff5e(EntityChicken entityChicken) {
        return net.minecraft.client.renderer.entity.RenderChicken.chickenTextures;
    }
}
