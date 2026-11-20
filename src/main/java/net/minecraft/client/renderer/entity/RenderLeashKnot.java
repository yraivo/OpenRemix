package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render {
    private ModelLeashKnot leashKnotModel = new ModelLeashKnot();

    private static ResourceLocation leashKnotTextures;

    protected ResourceLocation getEntityTexture(EntityLeashKnot entityLeashKnot) {
        return net.minecraft.client.renderer.entity.RenderLeashKnot.leashKnotTextures;
    }

    public RenderLeashKnot(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_4829183b457d777877bb((EntityLeashKnot) entity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityLeashKnot) entity);
    }

    public void method_4829183b457d777877bb(
            EntityLeashKnot entityLeashKnot, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        float f2 = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entityLeashKnot);
        this.leashKnotModel.render(entityLeashKnot, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f2);
        GlStateManager.popMatrix();
        super.doRender(entityLeashKnot, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.entity.RenderLeashKnot.leashKnotTextures =
                new ResourceLocation("textures/entity/lead_knot.png");
    }

    // region String decoding support

    // endregion
}
