package net.minecraft.client.gui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class Gui {
    public static ResourceLocation statIcons;
    protected float zLevel;
    public static ResourceLocation icons;

    private static String[] e;
    public static ResourceLocation optionsBackground;

    public static void setLegacyGuiRenderMarker(String[] astring) {
        e = astring;
    }

    public void drawTexturedModalRect(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                p171_0 + 0, p171_1 + p171_5, this.zLevel, (p171_2 + 0) * f, (p171_3 + p171_5) * f1);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_4,
                p171_1 + p171_5,
                this.zLevel,
                (p171_2 + p171_4) * f,
                (p171_3 + p171_5) * f1);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_4, p171_1 + 0, this.zLevel, (p171_2 + p171_4) * f, (p171_3 + 0) * f1);
        worldRenderer.addVertexWithUV(
                p171_0 + 0, p171_1 + 0, this.zLevel, (p171_2 + 0) * f, (p171_3 + 0) * f1);
        instance.draw();
    }

    protected void method_366505046587ede28c94(int p171_0, int p171_1, int p171_2, int p171_3) {
        if (p171_2 < p171_1) {
            int i1 = p171_1;
            p171_1 = p171_2;
            p171_2 = i1;
        }

        net.minecraft.client.gui.Gui.drawRect(p171_0, p171_1 + 1, p171_0 + 1, p171_2, p171_3);
    }

    public void drawTexturedModalRect(
            float p171_0, float p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        float f2 = 0.00390625F;
        float f3 = 0.00390625F;
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                p171_0 + 0.0F,
                p171_1 + p171_5,
                this.zLevel,
                (p171_2 + 0) * f2,
                (p171_3 + p171_5) * f3);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_4,
                p171_1 + p171_5,
                this.zLevel,
                (p171_2 + p171_4) * f2,
                (p171_3 + p171_5) * f3);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_4,
                p171_1 + 0.0F,
                this.zLevel,
                (p171_2 + p171_4) * f2,
                (p171_3 + 0) * f3);
        worldRenderer.addVertexWithUV(
                p171_0 + 0.0F, p171_1 + 0.0F, this.zLevel, (p171_2 + 0) * f2, (p171_3 + 0) * f3);
        instance.draw();
    }

    public static void drawScaledCustomSizeModalRect(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7,
            float p171_8,
            float p171_9) {
        float f10 = 1.0F / p171_8;
        float f11 = 1.0F / p171_9;
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                p171_0, p171_1 + p171_7, 0.0, p171_2 * f10, (p171_3 + p171_5) * f11);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_6,
                p171_1 + p171_7,
                0.0,
                (p171_2 + p171_4) * f10,
                (p171_3 + p171_5) * f11);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_6, p171_1, 0.0, (p171_2 + p171_4) * f10, p171_3 * f11);
        worldRenderer.addVertexWithUV(p171_0, p171_1, 0.0, p171_2 * f10, p171_3 * f11);
        instance.draw();
    }

    protected void drawGradientRect(
            double p171_0, double p171_1, double p171_2, double p171_3, int p171_4, int p171_5) {
        float f = (p171_4 >> 24 & 0xFF) / 255.0F;
        float f1 = (p171_4 >> 16 & 0xFF) / 255.0F;
        float f2 = (p171_4 >> 8 & 0xFF) / 255.0F;
        float f3 = (p171_4 & 0xFF) / 255.0F;
        float f4 = (p171_5 >> 24 & 0xFF) / 255.0F;
        float f5 = (p171_5 >> 16 & 0xFF) / 255.0F;
        float f6 = (p171_5 >> 8 & 0xFF) / 255.0F;
        float f7 = (p171_5 & 0xFF) / 255.0F;
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        net.minecraft.client.renderer.GlStateManager.enableBlend();
        net.minecraft.client.renderer.GlStateManager.disableAlpha();
        net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        net.minecraft.client.renderer.GlStateManager.shadeModel(7425);
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(f1, f2, f3, f);
        worldRenderer.addVertex(p171_2, p171_1, this.zLevel);
        worldRenderer.addVertex(p171_0, p171_1, this.zLevel);
        worldRenderer.setVertexColorFromFloats(f5, f6, f7, f4);
        worldRenderer.addVertex(p171_0, p171_3, this.zLevel);
        worldRenderer.addVertex(p171_2, p171_3, this.zLevel);
        instance.draw();
        net.minecraft.client.renderer.GlStateManager.shadeModel(7424);
        net.minecraft.client.renderer.GlStateManager.disableBlend();
        net.minecraft.client.renderer.GlStateManager.enableAlpha();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
    }

    public static void drawRect(
            double p171_0, double p171_1, double p171_2, double p171_3, int p171_4) {
        if (p171_0 < p171_2) {
            double d4 = p171_0;
            p171_0 = p171_2;
            p171_2 = d4;
        }

        if (p171_1 < p171_3) {
            double d5 = p171_1;
            p171_1 = p171_3;
            p171_3 = d5;
        }

        float f = (p171_4 >> 24 & 0xFF) / 255.0F;
        float f1 = (p171_4 >> 16 & 0xFF) / 255.0F;
        float f2 = (p171_4 >> 8 & 0xFF) / 255.0F;
        float f3 = (p171_4 & 0xFF) / 255.0F;
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        net.minecraft.client.renderer.GlStateManager.enableBlend();
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        net.minecraft.client.renderer.GlStateManager.color(f1, f2, f3, f);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(p171_0, p171_3, 0.0);
        worldRenderer.addVertex(p171_2, p171_3, 0.0);
        worldRenderer.addVertex(p171_2, p171_1, 0.0);
        worldRenderer.addVertex(p171_0, p171_1, 0.0);
        instance.draw();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
        net.minecraft.client.renderer.GlStateManager.disableBlend();
    }

    public void drawTexturedModalRect(
            int p171_0, int p171_1, TextureAtlasSprite textureAtlasSprite, int p171_3, int p171_4) {
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                p171_0 + 0,
                p171_1 + p171_4,
                this.zLevel,
                textureAtlasSprite.getMinU(),
                textureAtlasSprite.getMaxV());
        worldRenderer.addVertexWithUV(
                p171_0 + p171_3,
                p171_1 + p171_4,
                this.zLevel,
                textureAtlasSprite.getMaxU(),
                textureAtlasSprite.getMaxV());
        worldRenderer.addVertexWithUV(
                p171_0 + p171_3,
                p171_1 + 0,
                this.zLevel,
                textureAtlasSprite.getMaxU(),
                textureAtlasSprite.getMinV());
        worldRenderer.addVertexWithUV(
                p171_0 + 0,
                p171_1 + 0,
                this.zLevel,
                textureAtlasSprite.getMinU(),
                textureAtlasSprite.getMinV());
        instance.draw();
    }

    protected void method_f418b0f77f1897435d21(int p171_0, int p171_1, int p171_2, int p171_3) {
        if (p171_1 < p171_0) {
            int i1 = p171_0;
            p171_0 = p171_1;
            p171_1 = i1;
        }

        net.minecraft.client.gui.Gui.drawRect(p171_0, p171_2, p171_1 + 1, p171_2 + 1, p171_3);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static void drawModalRectWithCustomSizedTexture(
            int p171_0,
            int p171_1,
            float p171_2,
            float p171_3,
            int p171_4,
            int p171_5,
            float p171_6,
            float p171_7) {
        float f4 = 1.0F / p171_6;
        float f5 = 1.0F / p171_7;
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                p171_0, p171_1 + p171_5, 0.0, p171_2 * f4, (p171_3 + p171_5) * f5);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_4,
                p171_1 + p171_5,
                0.0,
                (p171_2 + p171_4) * f4,
                (p171_3 + p171_5) * f5);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_4, p171_1, 0.0, (p171_2 + p171_4) * f4, p171_3 * f5);
        worldRenderer.addVertexWithUV(p171_0, p171_1, 0.0, p171_2 * f4, p171_3 * f5);
        instance.draw();
    }

    public static String[] getLegacyGuiRenderMarker() {
        return e;
    }

    static {
        setLegacyGuiRenderMarker(null);
        net.minecraft.client.gui.Gui.optionsBackground = new ResourceLocation("textures/gui/options_background.png");
        net.minecraft.client.gui.Gui.statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
        net.minecraft.client.gui.Gui.icons = new ResourceLocation("textures/gui/icons.png");
    }

    public void drawCenteredString(
            FontRenderer fontRenderer, String text, int p171_2, int p171_3, int p171_4) {
        fontRenderer.drawStringWithShadow(
                text, p171_2 - fontRenderer.getStringWidth(text) / 2, p171_3, p171_4);
    }

    public void drawString(
            FontRenderer fontRenderer, String text, int p171_2, int p171_3, int p171_4) {
        fontRenderer.drawStringWithShadow(text, p171_2, p171_3, p171_4);
    }
}
