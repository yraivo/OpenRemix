package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;
import optifine.OptiFineConfig;

public class RenderXPOrb extends Render {
    private static ResourceLocation experienceOrbTextures;

    static {
        net.minecraft.client.renderer.entity.RenderXPOrb.experienceOrbTextures =
                new ResourceLocation("textures/entity/experience_orb.png");
    }

    public void doRender(
            EntityXPOrb entityXPOrb, double d0, double d1, double d2, float f, float f1) {
        Render.getLegacyEntityRenderState();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        this.bindEntityTexture(entityXPOrb);
        int i = entityXPOrb.getTextureByXP();
        float f2 = (i % 4 * 16 + 0) / 64.0F;
        float f3 = (i % 4 * 16 + 16) / 64.0F;
        float f4 = (i / 4 * 16 + 0) / 64.0F;
        float f5 = (i / 4 * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = entityXPOrb.getBrightnessForRender(f1);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f9 = 255.0F;
        float f10 = (entityXPOrb.xpColor + f1) / 2.0F;
        l = (int) ((MathHelper.sin(f10 + 0.0F) + 1.0F) * 0.5F * f9);
        int i1 = (int) f9;
        int j1 = (int) ((MathHelper.sin(f10 + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * f9);
        int k1 = l << 16 | i1 << 8 | j1;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f11 = 0.3F;
        GlStateManager.scale(f11, f11, f11);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        if (OptiFineConfig.isCustomColors()) {
            int l1 = CustomColors.getXpOrbColor(f9);
            if (l1 >= 0) {
                k1 = l1;
            }
        }

        worldRenderer.setVertexColorFromRgbAndAlpha(k1, 128);
        worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
        worldRenderer.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0, f2, f5);
        worldRenderer.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0, f3, f5);
        worldRenderer.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0, f3, f4);
        worldRenderer.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0, f2, f4);
        instance.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entityXPOrb, d0, d1, d2, f, f1);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Render.setLegacyEntityRenderState(new String[5]);
        }
    }

    protected ResourceLocation getTexture(EntityXPOrb entityXPOrb) {
        return net.minecraft.client.renderer.entity.RenderXPOrb.experienceOrbTextures;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public RenderXPOrb(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityXPOrb) entity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getTexture((EntityXPOrb) entity);
    }
}
