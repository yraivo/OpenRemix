package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBoat extends Render {

    private static ResourceLocation boatTextures;
    protected ModelBase modelBoat = new ModelBoat();

    protected ResourceLocation method_6541ceb4a3f260d28dd6(EntityBoat entityBoat) {
        return net.minecraft.client.renderer.entity.RenderBoat.boatTextures;
    }

    public void method_0f850b7290e64b87710b(
            EntityBoat entityBoat, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1 + 0.25F, (float) d2);
        GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
        float f2 = entityBoat.getTimeSinceHit() - f1;
        float f3 = entityBoat.getDamageTaken() - f1;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        if (f2 > 0.0F) {
            GlStateManager.rotate(
                    MathHelper.sin(f2) * f2 * f3 / 10.0F * entityBoat.getForwardDirection(),
                    1.0F,
                    0.0F,
                    0.0F);
        }

        float f4 = 0.75F;
        GlStateManager.scale(f4, f4, f4);
        GlStateManager.scale(1.0F / f4, 1.0F / f4, 1.0F / f4);
        this.bindEntityTexture(entityBoat);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.modelBoat.render(entityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entityBoat, d0, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_0f850b7290e64b87710b((EntityBoat) entity, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.RenderBoat.boatTextures = new ResourceLocation("textures/entity/boat.png");
    }

    public RenderBoat(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize = 0.5F;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_6541ceb4a3f260d28dd6((EntityBoat) entity);
    }
}
