package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;

public class RenderWitherSkull extends Render {

    private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
    private static ResourceLocation invulnerableWitherTextures;
    private static ResourceLocation witherTextures;

    public void doRender(
            EntityWitherSkull entityWitherSkull,
            double d0,
            double d1,
            double d2,
            float f,
            float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        float f2 =
                this.method_4e008049330cc7e3f715(
                        entityWitherSkull.prevRotationYaw, entityWitherSkull.rotationYaw, f1);
        float f3 =
                entityWitherSkull.prevRotationPitch
                        + (entityWitherSkull.rotationPitch - entityWitherSkull.prevRotationPitch)
                                * f1;
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        float f4 = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entityWitherSkull);
        this.skeletonHeadModel.render(entityWitherSkull, 0.0F, 0.0F, 0.0F, f2, f3, f4);
        GlStateManager.popMatrix();
        super.doRender(entityWitherSkull, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityWitherSkull) entity, d0, d1, d2, f, f1);
    }

    private float method_4e008049330cc7e3f715(float f, float f1, float f2) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        float f3 = f1 - f;

        while (f3 < -180.0F) {
            f3 += 360.0F;
        }

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        return f + f2 * f3;
    }

    static {
        net.minecraft.client.renderer.tileentity.RenderWitherSkull.invulnerableWitherTextures =
                new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
        net.minecraft.client.renderer.tileentity.RenderWitherSkull.witherTextures =
                new ResourceLocation("textures/entity/wither/wither.png");
    }

    public RenderWitherSkull(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    protected ResourceLocation method_b43254c9b8c54f2586e4(EntityWitherSkull entityWitherSkull) {
        return entityWitherSkull.isInvulnerable()
                ? net.minecraft.client.renderer.tileentity.RenderWitherSkull.invulnerableWitherTextures
                : net.minecraft.client.renderer.tileentity.RenderWitherSkull.witherTextures;
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_b43254c9b8c54f2586e4((EntityWitherSkull) entity);
    }
}
