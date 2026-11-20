package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.util.ResourceLocation;

public class RenderEndermite extends RenderLiving {
    private static ResourceLocation ENDERMITE_TEXTURES;

    static {
        net.minecraft.client.renderer.entity.RenderEndermite.ENDERMITE_TEXTURES =
                new ResourceLocation("textures/entity/endermite.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_6da461725495397d6cc1((EntityEndermite) entity);
    }

    protected float method_c256180d3d9342fddec3(EntityEndermite entityEndermite) {
        return 180.0F;
    }

    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return this.method_c256180d3d9342fddec3((EntityEndermite) livingEntity);
    }

    protected ResourceLocation method_6da461725495397d6cc1(EntityEndermite entityEndermite) {
        return net.minecraft.client.renderer.entity.RenderEndermite.ENDERMITE_TEXTURES;
    }

    public RenderEndermite(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelEnderMite(), 0.3F);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
