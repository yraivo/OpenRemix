package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish extends RenderLiving {

    private static ResourceLocation silverfishTextures;

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntitySilverfish) entity);
    }

    protected ResourceLocation getEntityTexture(EntitySilverfish entitySilverfish) {
        return net.minecraft.client.renderer.entity.RenderSilverfish.silverfishTextures;
    }

    public RenderSilverfish(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelSilverfish(), 0.3F);
    }

    static {
        net.minecraft.client.renderer.entity.RenderSilverfish.silverfishTextures =
                new ResourceLocation("textures/entity/silverfish.png");
    }

    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return this.method_d098237860a5be852747((EntitySilverfish) livingEntity);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected float method_d098237860a5be852747(EntitySilverfish entitySilverfish) {
        return 180.0F;
    }
}
