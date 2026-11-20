package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving {

    private static ResourceLocation squidTextures;

    protected ResourceLocation getEntityTexture(SquidEntity squidEntity) {
        return net.minecraft.client.renderer.entity.RenderSquid.squidTextures;
    }

    protected void rotateCorpse(SquidEntity squidEntity, float f4, float f, float f1) {
        float f2 =
                squidEntity.prevSquidPitch
                        + (squidEntity.squidPitch - squidEntity.prevSquidPitch) * f1;
        float f3 =
                squidEntity.prevSquidYaw + (squidEntity.squidYaw - squidEntity.prevSquidYaw) * f1;
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
        GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, -1.2F, 0.0F);
    }

    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return this.handleRotationFloat((SquidEntity) livingEntity, f);
    }

    protected float handleRotationFloat(SquidEntity squidEntity, float f) {
        return squidEntity.lastTentacleAngle
                + (squidEntity.tentacleAngle - squidEntity.lastTentacleAngle) * f;
    }

    public RenderSquid(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
    }

    // region String decoding support

    // endregion

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.rotateCorpse((SquidEntity) livingEntity, f, f1, f2);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((SquidEntity) entity);
    }

    static {
        net.minecraft.client.renderer.entity.RenderSquid.squidTextures = new ResourceLocation("textures/entity/squid.png");
    }
}
