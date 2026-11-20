package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderArrow extends Render {
    private static ResourceLocation arrowTextures;

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityArrow) entity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(EntityArrow entityArrow) {
        return net.minecraft.client.renderer.entity.RenderArrow.arrowTextures;
    }

    // region String decoding support

    // endregion

    public RenderArrow(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    public void doRender(
            EntityArrow entityArrow, double d0, double d1, double d2, float f, float f1) {
        this.bindEntityTexture(entityArrow);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        Render.getLegacyEntityRenderState();

        GlStateManager.rotate(
                entityArrow.prevRotationYaw
                        + (entityArrow.rotationYaw - entityArrow.prevRotationYaw) * f1
                        - 90.0F,
                0.0F,
                1.0F,
                0.0F);
        GlStateManager.rotate(
                entityArrow.prevRotationPitch
                        + (entityArrow.rotationPitch - entityArrow.prevRotationPitch) * f1,
                0.0F,
                0.0F,
                1.0F);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        byte b0 = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (0 + b0 * 10) / 32.0F;

        float f5 = (5 + b0 * 10) / 32.0F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (5 + b0 * 10) / 32.0F;
        float f9 = (10 + b0 * 10) / 32.0F;
        float f10 = 0.05625F;
        GlStateManager.enableRescaleNormal();
        float f11 = entityArrow.arrowShake - f1;
        if (f11 > 0.0F) {
            float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
            GlStateManager.rotate(f12, 0.0F, 0.0F, 1.0F);
        }

        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f10, f10, f10);
        GlStateManager.translate(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7.0, -2.0, -2.0, f6, f8);
        worldRenderer.addVertexWithUV(-7.0, -2.0, 2.0, f7, f8);
        worldRenderer.addVertexWithUV(-7.0, 2.0, 2.0, f7, f9);
        worldRenderer.addVertexWithUV(-7.0, 2.0, -2.0, f6, f9);
        instance.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7.0, 2.0, -2.0, f6, f8);
        worldRenderer.addVertexWithUV(-7.0, 2.0, 2.0, f7, f8);
        worldRenderer.addVertexWithUV(-7.0, -2.0, 2.0, f7, f9);
        worldRenderer.addVertexWithUV(-7.0, -2.0, -2.0, f6, f9);
        instance.draw();
        int i = 0;

        while (i < 4) {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertexWithUV(-8.0, -2.0, 0.0, f2, f4);
            worldRenderer.addVertexWithUV(8.0, -2.0, 0.0, f3, f4);
            worldRenderer.addVertexWithUV(8.0, 2.0, 0.0, f3, f5);
            worldRenderer.addVertexWithUV(-8.0, 2.0, 0.0, f2, f5);
            instance.draw();
            i++;
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entityArrow, d0, d1, d2, f, f1);
    }

    static {
        net.minecraft.client.renderer.entity.RenderArrow.arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityArrow) entity);
    }

    // region String decoding support

    // endregion
}
