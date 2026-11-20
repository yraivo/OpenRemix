package remix.util.render;

import remix.util.network.NordVpnProxyManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public final class WorldRenderUtils {
    private static String a;

    public static String formatPosition(float p171_0, float p171_1, float p171_2) {
        int i = (int) (p171_0 * 6.0F);
        float f3 = p171_0 * 6.0F - i;
        float f4 = p171_2 * (1.0F - p171_1);
        float f5 = p171_2 * (1.0F - f3 * p171_1);
        float f6 = p171_2 * (1.0F - (1.0F - f3) * p171_1);

        try {
            switch (i) {
                case 0:
                    return remix.util.render.WorldRenderUtils.formatCompactCoords(
                            p171_2, f6, f4);
                case 1:
                    return remix.util.render.WorldRenderUtils.formatCompactCoords(
                            f5, p171_2, f4);
                case 2:
                    return remix.util.render.WorldRenderUtils.formatCompactCoords(
                            f4, p171_2, f6);
                case 3:
                    return remix.util.render.WorldRenderUtils.formatCompactCoords(
                            f4, f5, p171_2);
                case 4:
                    return remix.util.render.WorldRenderUtils.formatCompactCoords(
                            f6, f4, p171_2);
                case 5:
                    return remix.util.render.WorldRenderUtils.formatCompactCoords(
                            p171_2, f4, f5);
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.util.render.WorldRenderUtils.wrapException(runtimeexception);
        }

        throw new java.lang.RuntimeException(remix.util.render.WorldRenderUtils.a);
    }

    public static void drawBorderedRect(
            double d0, double d1, double d2, double d3, int i, int j, double d4) {
        drawBorder(d0, d1, d2, d3, j, d4);
        drawRect(d0 + d4, d1 + d4, d2 - d4, d3 - d4, i);
    }

    public static void drawTracerLine(
            double d0, double d1, double d2, double d3, double d4, double d5) {
        d0 -= MinecraftClient.getMinecraft().getRenderManager().getRenderOriginX();
        d3 -= MinecraftClient.getMinecraft().getRenderManager().getRenderOriginX();
        d1 -= MinecraftClient.getMinecraft().getRenderManager().getRenderOriginY();
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        d4 -= MinecraftClient.getMinecraft().getRenderManager().getRenderOriginY();
        d2 -= MinecraftClient.getMinecraft().getRenderManager().getRenderOriginZ();
        Entity[] entityArrayValue = entityArray;
        d5 -= MinecraftClient.getMinecraft().getRenderManager().getRenderOriginZ();

        try {
            GL11.glBegin(7);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glVertex3d(d0, d4, d5);
            GL11.glVertex3d(d0, d1, d5);
            GL11.glVertex3d(d0, d4, d2);
            GL11.glVertex3d(d0, d1, d2);
            GL11.glVertex3d(d3, d4, d2);
            GL11.glVertex3d(d3, d1, d2);
            GL11.glVertex3d(d3, d4, d5);
            GL11.glVertex3d(d3, d1, d5);
            GL11.glEnd();
            if (entityArrayValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }
        } catch (RuntimeException runtimeexception) {
            throw wrapException(runtimeexception);
        }
    }

    public static void drawBorderedRectSimple(
            double p171_0, double p171_1, double p171_2, double p171_3, int p171_4, int p171_5) {
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glEnable(2848);
        org.lwjgl.opengl.GL11.glShadeModel(7425);
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glBegin(7);
        remix.util.render.WorldRenderUtils.a(p171_4);
        org.lwjgl.opengl.GL11.glVertex2d(p171_2, p171_1);
        org.lwjgl.opengl.GL11.glVertex2d(p171_0, p171_1);
        remix.util.render.WorldRenderUtils.a(p171_5);
        org.lwjgl.opengl.GL11.glVertex2d(p171_0, p171_3);
        org.lwjgl.opengl.GL11.glVertex2d(p171_2, p171_3);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glPopMatrix();
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glDisable(3042);
        org.lwjgl.opengl.GL11.glDisable(2848);
        org.lwjgl.opengl.GL11.glShadeModel(7424);
    }

    public static void drawGradientLine(float f, float f1, float f2, int i, int j) {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        f2 *= 2.0F;
        Entity[] entityArrayValue = entityArray;
        f *= 2.0F;
        f1 *= 2.0F;
        float f3 = (j >> 24 & 0xFF) / 255.0F;
        float f4 = (j >> 16 & 0xFF) / 255.0F;
        float f5 = (j >> 8 & 0xFF) / 255.0F;
        float f6 = (j & 0xFF) / 255.0F;
        float f7 = (float) (6.2831852 / i);
        float f8 = (float) Math.cos(f7);
        float f9 = (float) Math.sin(f7);
        float f10 = f2;
        float f11 = 0.0F;
        setupRender();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glColor4f(f4, f5, f6, f3);
        GL11.glBegin(2);
        int k = 0;

        while (k < i) {
            GL11.glVertex2f(f10 + f, f11 + f1);
            float f12 = f10;
            f10 = f8 * f10 - f9 * f11;
            f11 = f9 * f12 + f8 * f11;
            k++;
            if (entityArrayValue != null) {
                break;
            }
        }

        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        finishRender();
    }

    public static void drawRect(
            double p171_0, double p171_1, double p171_2, double p171_3, int p171_4) {
        remix.util.render.WorldRenderUtils.setupRender();
        remix.util.render.WorldRenderUtils.a(p171_4);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0, p171_1, p171_2, p171_3);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    // region String decoding support

    // endregion

    public static void drawBillboardQuad(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7) {
        remix.util.render.WorldRenderUtils.setupRender();
        org.lwjgl.opengl.GL11.glColor4f(p171_4, p171_5, p171_6, p171_7);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0, p171_1, p171_2, p171_3);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static String formatCompactCoords(float p171_0, float p171_1, float p171_2) {
        java.lang.String text = java.lang.Integer.toHexString((int) (p171_0 * 256.0F));
        java.lang.String textValue = java.lang.Integer.toHexString((int) (p171_1 * 256.0F));
        java.lang.String textValue2 = java.lang.Integer.toHexString((int) (p171_2 * 256.0F));
        return text + textValue + textValue2;
    }

    public static void drawArcSegment(int i, int j, double d0, int k) {
        d0 *= 2.0;
        i *= 2;
        j *= 2;
        float f = (k >> 24 & 0xFF) / 255.0F;
        float f1 = (k >> 16 & 0xFF) / 255.0F;
        float f2 = (k >> 8 & 0xFF) / 255.0F;
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        float f3 = (k & 0xFF) / 255.0F;
        setupRender();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Entity[] entityArrayValue = entityArray;
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(6);
        int l = 0;

        while (l <= 360) {
            double d1 = Math.sin(l * Math.PI / 180.0) * d0;
            double d2 = Math.cos(l * Math.PI / 180.0) * d0;
            GL11.glVertex2d(i + d1, j + d2);
            l++;
            if (entityArrayValue != null) {
                break;
            }
        }

        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        finishRender();
    }

    public static void drawBorderedBox(
            double p171_0, double p171_1, double p171_2, double p171_3, int p171_4, int p171_5) {
        remix.util.render.WorldRenderUtils.setupRender();
        p171_0 *= 2.0;
        p171_2 *= 2.0;
        p171_1 *= 2.0;
        p171_3 *= 2.0;
        org.lwjgl.opengl.GL11.glScalef(0.5F, 0.5F, 0.5F);
        remix.util.render.WorldRenderUtils.drawVerticalLine(
                p171_0, p171_1, p171_3 - 1.0, p171_5);
        remix.util.render.WorldRenderUtils.drawVerticalLine(
                p171_2 - 1.0, p171_1, p171_3, p171_5);
        remix.util.render.WorldRenderUtils.drawHorizontalLine(
                p171_0, p171_2 - 1.0, p171_1, p171_5);
        remix.util.render.WorldRenderUtils.drawHorizontalLine(
                p171_0, p171_2 - 2.0, p171_3 - 1.0, p171_5);
        remix.util.render.WorldRenderUtils.drawRect(
                p171_0 + 1.0, p171_1 + 1.0, p171_2 - 1.0, p171_3 - 1.0, p171_4);
        org.lwjgl.opengl.GL11.glScalef(2.0F, 2.0F, 2.0F);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void drawDottedLine(
            double p171_0, double p171_1, double p171_2, double p171_3) {
        org.lwjgl.opengl.GL11.glBegin(7);
        org.lwjgl.opengl.GL11.glVertex2d(p171_0, p171_3);
        org.lwjgl.opengl.GL11.glVertex2d(p171_2, p171_3);
        org.lwjgl.opengl.GL11.glVertex2d(p171_2, p171_1);
        org.lwjgl.opengl.GL11.glVertex2d(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glEnd();
    }

    public static void drawComplexGradientRect(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            int p171_5,
            int p171_6,
            int p171_7) {
        remix.util.render.WorldRenderUtils.setupRender();
        remix.util.render.WorldRenderUtils.drawHollowGradientBox(
                p171_0, p171_1, p171_2, p171_3, p171_7, p171_6);
        remix.util.render.WorldRenderUtils.a(p171_5);
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glLineWidth(p171_4);
        org.lwjgl.opengl.GL11.glBegin(3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_2, p171_3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_2, p171_1);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glDisable(3042);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void drawVerticalLine(
            double p171_0, double p171_1, double p171_2, int p171_3) {
        if (p171_2 < p171_1) {
            float f = (float) p171_1;
            p171_1 = p171_2;
            p171_2 = f;
        }

        remix.util.render.WorldRenderUtils.drawRect(
                p171_0, p171_1 + 1.0, p171_0 + 1.0, p171_2, p171_3);
    }

    public static void drawHorizontalLine(
            double p171_0, double p171_1, double p171_2, int p171_3) {
        if (p171_1 < p171_0) {
            double d3 = p171_0;
            p171_0 = p171_1;
            p171_1 = d3;
        }

        remix.util.render.WorldRenderUtils.drawRect(
                p171_0, p171_2, p171_1 + 1.0, p171_2 + 1.0, p171_3);
    }

    public static void drawRoundedRect(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            int p171_5,
            int p171_6) {
        remix.util.render.WorldRenderUtils.setupRender();
        remix.util.render.WorldRenderUtils.drawFilledBox(
                p171_0, p171_1, p171_2, p171_3, p171_5);
        remix.util.render.WorldRenderUtils.a(p171_6);
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glLineWidth(p171_4);
        org.lwjgl.opengl.GL11.glBegin(3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_2, p171_3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_2, p171_1);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glDisable(3042);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void setupRender() {
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glDisable(2929);
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
    }

    public static void drawGradientBox(
            float p171_0, float p171_1, float p171_2, float p171_3, int p171_4, int p171_5) {
        remix.util.render.WorldRenderUtils.setupRender();
        p171_0 *= 2.0F;
        p171_2 *= 2.0F;
        p171_1 *= 2.0F;
        p171_3 *= 2.0F;
        org.lwjgl.opengl.GL11.glScalef(0.5F, 0.5F, 0.5F);
        remix.util.render.WorldRenderUtils.drawCircleOutline(
                p171_0, p171_1, p171_3 - 1.0F, p171_5);
        remix.util.render.WorldRenderUtils.drawCircleOutline(
                p171_2 - 1.0F, p171_1, p171_3, p171_5);
        remix.util.render.WorldRenderUtils.drawCircle(
                p171_0, p171_2 - 1.0F, p171_1, p171_5);
        remix.util.render.WorldRenderUtils.drawCircle(
                p171_0, p171_2 - 2.0F, p171_3 - 1.0F, p171_5);
        remix.util.render.WorldRenderUtils.drawFilledBox(
                p171_0 + 1.0F, p171_1 + 1.0F, p171_2 - 1.0F, p171_3 - 1.0F, p171_4);
        org.lwjgl.opengl.GL11.glScalef(2.0F, 2.0F, 2.0F);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void finishRender() {
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glDisable(3042);
        org.lwjgl.opengl.GL11.glEnable(2929);
        org.lwjgl.opengl.GL11.glPopMatrix();
    }

    public static void drawRoundedOutline(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            int p171_5,
            int p171_6) {
        remix.util.render.WorldRenderUtils.setupRender();
        remix.util.render.WorldRenderUtils.a(p171_5);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0 + p171_4, p171_1 + p171_4, p171_2 - p171_4, p171_3 - p171_4);
        remix.util.render.WorldRenderUtils.a(p171_6);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0 + p171_4, p171_1, p171_2 - p171_4, p171_1 + p171_4);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0, p171_1, p171_0 + p171_4, p171_3);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_2 - p171_4, p171_1, p171_2, p171_3);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0 + p171_4, p171_3 - p171_4, p171_2 - p171_4, p171_3);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void drawESPBoundingBox(BoundingBox boundingBox, int i, int j) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();

        try {
            worldRenderer.startDrawing(3);
            if (i != -1) {
                worldRenderer.setOpaqueVertexColorFromRgb(i);
            }
        } catch (RuntimeException runtimeexception2) {
            throw wrapException(runtimeexception2);
        }

        try {
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            a(j);
            instance.draw();
            worldRenderer.startDrawing(3);
            if (i != -1) {
                worldRenderer.setOpaqueVertexColorFromRgb(i);
            }
        } catch (RuntimeException runtimeexception) {
            throw wrapException(runtimeexception);
        }

        try {
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            a(j);
            instance.draw();
            worldRenderer.startDrawing(1);
            if (i != -1) {
                worldRenderer.setOpaqueVertexColorFromRgb(i);
            }
        } catch (RuntimeException runtimeexception1) {
            throw wrapException(runtimeexception1);
        }

        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        a(j);
        instance.draw();
    }

    public static void drawCircle(
            float p171_0, float p171_1, float p171_2, int p171_3) {
        if (p171_1 < p171_0) {
            float f3 = p171_0;
            p171_0 = p171_1;
            p171_1 = f3;
        }

        remix.util.render.WorldRenderUtils.drawFilledBox(
                p171_0, p171_2, p171_1 + 1.0F, p171_2 + 1.0F, p171_3);
    }

    public static void drawHollowGradientBox(
            float p171_0, float p171_1, float p171_2, float p171_3, int p171_4, int p171_5) {
        remix.util.render.WorldRenderUtils.setupRender();
        org.lwjgl.opengl.GL11.glShadeModel(7425);
        org.lwjgl.opengl.GL11.glBegin(7);
        remix.util.render.WorldRenderUtils.a(p171_4);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_3);
        org.lwjgl.opengl.GL11.glVertex2f(p171_2, p171_3);
        remix.util.render.WorldRenderUtils.a(p171_5);
        org.lwjgl.opengl.GL11.glVertex2f(p171_2, p171_1);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glShadeModel(7424);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void drawFilledBox(
            float p171_0, float p171_1, float p171_2, float p171_3, int p171_4) {
        remix.util.render.WorldRenderUtils.setupRender();
        remix.util.render.WorldRenderUtils.a(p171_4);
        remix.util.render.WorldRenderUtils.drawDottedLine(
                p171_0, p171_1, p171_2, p171_3);
        remix.util.render.WorldRenderUtils.finishRender();
    }

    public static void drawOutlinedBox(BoundingBox boundingBox) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        NordVpnProxyManager.getLegacyProxyFlowState();
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);

        try {
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            instance.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            instance.draw();
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                NordVpnProxyManager.setLegacyProxyFlowState(new Entity[3]);
            }
        } catch (RuntimeException runtimeexception) {
            throw wrapException(runtimeexception);
        }
    }

    public static void drawCustomTexture(
            float f, float f1, ResourceLocation resourceLocation, int i, int j) {
        GL11.glPushMatrix();
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        drawTexturedModalRect((int) f, (int) f1, 0.0F, 0.0F, i, j, i, j, i, j);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    // region String decoding support

    // endregion

    public static void drawTexturedModalRect(
            int p171_0,
            int p171_1,
            float p171_2,
            float p171_3,
            int p171_4,
            int p171_5,
            int p171_6,
            int p171_7,
            float p171_8,
            float p171_9) {
        net.minecraft.client.gui.Gui.drawScaledCustomSizeModalRect(
                p171_0, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6, p171_7, p171_8, p171_9);
    }

    public static void drawBorder(
            double d0, double d1, double d2, double d3, int i, double d4) {
        drawRect(d0, d1, d2, d1 + d4, i);
        drawRect(d0, d3 - d4, d2, d3, i);
        drawRect(d0, d1 + d4, d0 + d4, d3 - d4, i);
        drawRect(d2 - d4, d1 + d4, d2, d3 - d4, i);
    }

    static {
        a = "Error";
    }

    public static void a(int p171_0) {
        float f = (p171_0 >> 24 & 0xFF) / 255.0F;
        float f1 = (p171_0 >> 16 & 0xFF) / 255.0F;
        float f2 = (p171_0 >> 8 & 0xFF) / 255.0F;
        float f3 = (p171_0 & 0xFF) / 255.0F;
        org.lwjgl.opengl.GL11.glColor4f(f1, f2, f3, f);
    }

    public static void drawCircleOutline(
            float p171_0, float p171_1, float p171_2, int p171_3) {
        if (p171_2 < p171_1) {
            float f3 = p171_1;
            p171_1 = p171_2;
            p171_2 = f3;
        }

        remix.util.render.WorldRenderUtils.drawFilledBox(
                p171_0, p171_1 + 1.0F, p171_0 + 1.0F, p171_2, p171_3);
    }

    public static void drawGradientSegment(float f, float f1, float f2, int i, int j) {
        if (f1 < f) {
            float f3 = f;
            f = f1;
            f1 = f3;
        }

        drawHollowGradientBox(f, f2, f1 + 1.0F, f2 + 1.0F, i, j);
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }
}
