package remix.util.render;

import remix.util.time.ElapsedTimer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class Render2D3DUtils {
    private static Random randomColorSource;
    private static MinecraftClient minecraft;
    private static FloatBuffer projectedWindowCoordinates;
    private static FloatBuffer projectionMatrixBuffer;
    private static FloatBuffer modelViewMatrixBuffer;

    private static IntBuffer viewportBuffer;

    private static Map<String, ResourceLocation> legacyTextureLocations;

    public static int getTeamColor(LivingEntity livingEntity) {
        int i = 16777215;
        ScorePlayerTeam team = (ScorePlayerTeam) livingEntity.getTeam();
        String text;
        if (team != null
                && (text = FontRenderer.getFormatFromString(team.getColorPrefix())).length() >= 2) {
            i = remix.util.render.Render2D3DUtils.minecraft.fontRendererObj.getColorCode(text.charAt(1));
        }

        return i;
    }

    public static void drawCross(double d0, double d1, int i) {
        beginUntexturedOverlay();
        glColor(i);
        enablePrimitiveSmoothing();
        GL11.glLineWidth(2.0F);
        GL11.glBegin(3);
        GL11.glVertex2d(d0 + 1.0, d1 + 1.0);
        GL11.glVertex2d(d0 + 6.5, d1 + 6.5);
        GL11.glEnd();
        GL11.glLineWidth(2.0F);
        GL11.glBegin(3);
        GL11.glVertex2d(d0 + 6.5, d1 + 1.0);
        GL11.glVertex2d(d0 + 1.0, d1 + 6.5);
        GL11.glEnd();
        disablePrimitiveSmoothing();
        endUntexturedOverlay();
    }

    public static void drawRoundedRect(
            float f, float f1, float f2, float f3, float f4, int i) {
        byte b0 = 18;
        ElapsedTimer.fM();

        float f5 = 90.0F / b0;
        float f6 = (i >> 24 & 0xFF) / 255.0F;
        float f7 = (i >> 16 & 0xFF) / 255.0F;
        float f8 = (i >> 8 & 0xFF) / 255.0F;
        float f9 = (i & 0xFF) / 255.0F;
        GL11.glDisable(2884);
        GL11.glDisable(3553);

        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.color(f7, f8, f9, f6);
        GL11.glBegin(5);
        GL11.glVertex2f(f + f4, f3);
        GL11.glVertex2f(f + f4, f3 - f4);
        GL11.glVertex2f(f2 - f4, f3);
        GL11.glVertex2f(f2 - f4, f3 - f4);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(f, f3 - f4);
        GL11.glVertex2f(f, f1);
        GL11.glVertex2f(f2, f3 - f4);
        GL11.glVertex2f(f2, f1);
        GL11.glEnd();
        float f10 = f + f4;
        float f11 = f3 - f4;
        GL11.glBegin(6);
        GL11.glVertex2f(f10, f11);
        int j = 0;

        while (j <= b0) {
            float f12 = j * f5;
            GL11.glVertex2f(
                    (float) (f10 - f4 * Math.cos(Math.toRadians(f12))),
                    (float) (f11 + f4 * Math.sin(Math.toRadians(f12))));
            j++;
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f10 = f2 - f4;
        f11 = f3 - f4;
        GL11.glVertex2f(f10, f11);
        j = 0;

        while (j <= b0) {
            float f13 = j * f5;
            GL11.glVertex2f(
                    (float) (f10 + f4 * Math.cos(Math.toRadians(f13))),
                    (float) (f11 + f4 * Math.sin(Math.toRadians(f13))));
            j++;
        }

        GL11.glEnd();
        GlStateManager.disableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
    }

    public static void drawIcon(
            ResourceLocation resourceLocation, float f, float f1, float f2, float f3) {
        drawIconWithAlpha(resourceLocation, f, f1, f2, f3, 1.0F);
    }

    public static void beginUntexturedOverlay() {
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glDisable(2929);
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
    }

    public static void drawBorder(
            double d0, double d1, double d2, double d3, double d4, int i) {
        Gui.drawRect(d0 + d4, d1, d2 - d4, d1 + d4, i);
        Gui.drawRect(d0, d1, d0 + d4, d3, i);
        Gui.drawRect(d2 - d4, d1, d2, d3, i);
        Gui.drawRect(d0 + d4, d3 - d4, d2 - d4, d3, i);
    }

    public static void drawBorderedRect(
            double d0, double d1, double d2, double d3, double d4, int i, int j) {
        Gui.drawRect(d0 + d4, d1 + d4, d2 - d4, d3 - d4, i);
        drawBorder(d0, d1, d2, d3, d4, j);
    }

    public static void drawOutline(
            double d0, double d1, double d2, double d3, double d4, int i) {
        ElapsedTimer.fM();

        float f = (i >> 24 & 0xFF) / 255.0F;
        float f1 = (i >> 16 & 0xFF) / 255.0F;

        float f2 = (i >> 8 & 0xFF) / 255.0F;
        float f3 = (i & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glDisable(3553);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        int b0 = 0;

        while (b0 <= 90) {
            GL11.glVertex2d(
                    d0 + d4 + MathHelper.sin((float) (b0 * Math.PI / 180.0)) * (d4 * -1.0),
                    d1 + d4 + MathHelper.cos((float) (b0 * Math.PI / 180.0)) * (d4 * -1.0));
            b0 += 3;
        }

        b0 = 90;

        while (b0 <= 180) {
            GL11.glVertex2d(
                    d0 + d4 + MathHelper.sin((float) (b0 * Math.PI / 180.0)) * (d4 * -1.0),
                    d3 - d4 + MathHelper.cos((float) (b0 * Math.PI / 180.0)) * (d4 * -1.0));
            b0 += 3;
        }

        b0 = 0;

        while (b0 <= 90) {
            GL11.glVertex2d(
                    d2 - d4 + MathHelper.sin((float) (b0 * Math.PI / 180.0)) * d4,
                    d3 - d4 + MathHelper.cos((float) (b0 * Math.PI / 180.0)) * d4);
            b0 += 3;
        }

        b0 = 90;

        while (b0 <= 180) {
            GL11.glVertex2d(
                    d2 - d4 + Math.sin(b0 * Math.PI / 180.0) * d4,
                    d1 + d4 + Math.cos(b0 * Math.PI / 180.0) * d4);
            b0 += 3;
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void endUnoccludedWorldOverlay() {
        net.minecraft.client.renderer.GlStateManager.depthMask(true);
        net.minecraft.client.renderer.GlStateManager.enableDepth();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
        remix.util.render.Render2D3DUtils.minecraft.entityRenderer.enableLightmap();
        net.minecraft.client.renderer.GlStateManager.popMatrix();
    }

    public static void drawArc(
            float f, float f1, float f2, float f3, float f4, int i) {
        ElapsedTimer.fM();

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);

        if (f2 > f3) {
            float f5 = f3;
            f3 = f2;
            f2 = f5;
        }

        float f6 = (i >> 24 & 0xFF) / 255.0F;
        float f7 = (i >> 16 & 0xFF) / 255.0F;
        float f8 = (i >> 8 & 0xFF) / 255.0F;
        float f9 = (i & 0xFF) / 255.0F;
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f7, f8, f9, f6);
        if (f6 > 0.5F) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            float f10 = f3;

            while (f10 >= f2) {
                float f11 = (float) Math.cos(f10 * Math.PI / 180.0) * f4;
                float f12 = (float) Math.sin(f10 * Math.PI / 180.0) * f4;
                GL11.glVertex2f(f + f11, f1 + f12);
                f10 -= 4.0F;
            }

            GL11.glEnd();
            GL11.glDisable(2848);
        }

        GL11.glBegin(6);
        float f13 = f3;

        while (f13 >= f2) {
            float f14 = (float) Math.cos(f13 * Math.PI / 180.0) * f4;
            float f15 = (float) Math.sin(f13 * Math.PI / 180.0) * f4;
            GL11.glVertex2f(f + f14, f1 + f15);
            f13 -= 4.0F;
        }

        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCircle(
            float p171_0, float p171_1, float p171_2, int p171_3) {
        remix.util.render.Render2D3DUtils.drawArc(
                p171_0, p171_1, 0.0F, 360.0F, p171_2, p171_3);
    }

    public static void drawTextureModalWithPalette(
            ResourceLocation resourceLocation,
            double d0,
            double d1,
            float f,
            float f1,
            int[] aint) {
        GL11.glPushMatrix();
        remix.util.render.Render2D3DUtils.minecraft.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.color(
                aint[0] / 255.0F, aint[1] / 255.0F, aint[2] / 255.0F, aint[3] / 255.0F);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GL11.glEnable(3042);
        drawCurvedGradient(
                d0, d1, 0.0F, 0.0F, f / 2.0F, f1 / 2.0F, f / 2.0F, f1 / 2.0F, f / 2.0F, f1 / 2.0F);
        GlStateManager.disableAlpha();
        GL11.glPopMatrix();
    }

    public static void drawOutlinedRoundedRect(
            float f, float f1, float f2, float f3, float f4, int i) {
        byte b0 = 18;
        float f5 = 90.0F / b0;
        float f6 = (i >> 24 & 0xFF) / 255.0F;
        float f7 = (i >> 16 & 0xFF) / 255.0F;
        float f8 = (i >> 8 & 0xFF) / 255.0F;
        float f9 = (i & 0xFF) / 255.0F;
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        ElapsedTimer.fM();

        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.color(f7, f8, f9, f6);
        GL11.glBegin(5);
        GL11.glVertex2f(f + f4, f1);

        GL11.glVertex2f(f + f4, f1 + f4);
        GL11.glVertex2f(f2 - f4, f1);
        GL11.glVertex2f(f2 - f4, f1 + f4);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(f, f1 + f4);
        GL11.glVertex2f(f, f3);
        GL11.glVertex2f(f2, f1 + f4);
        GL11.glVertex2f(f2, f3);
        GL11.glEnd();
        GL11.glBegin(6);
        float f10 = f2 - f4;
        float f11 = f1 + f4;
        GL11.glVertex2f(f10, f11);
        int j = 0;

        while (j <= b0) {
            float f12 = j * f5;
            GL11.glVertex2f(
                    (float) (f10 + f4 * Math.cos(Math.toRadians(f12))),
                    (float) (f11 - f4 * Math.sin(Math.toRadians(f12))));
            j++;
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f10 = f + f4;
        f11 = f1 + f4;
        GL11.glVertex2f(f10, f11);
        j = 0;

        while (j <= b0) {
            float f13 = j * f5;
            GL11.glVertex2f(
                    (float) (f10 - f4 * Math.cos(Math.toRadians(f13))),
                    (float) (f11 - f4 * Math.sin(Math.toRadians(f13))));
            j++;
        }

        GL11.glEnd();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
    }

    public static float[] getRGBA(int p171_0) {
        float f = (p171_0 >> 24 & 0xFF) / 255.0F;
        float f1 = (p171_0 >> 16 & 0xFF) / 255.0F;
        float f2 = (p171_0 >> 8 & 0xFF) / 255.0F;
        float f3 = (p171_0 & 0xFF) / 255.0F;
        return new float[] {f1, f2, f3, f};
    }

    public static void drawPolygon(double[] adouble, double[] adouble1, int i) {
        float f = (i >> 16 & 0xFF) / 255.0F;
        float f1 = (i >> 8 & 0xFF) / 255.0F;
        float f2 = (i & 0xFF) / 255.0F;
        float f3 = (i >> 24 & 0xFF) / 255.0F;
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.enableBlend();
        ElapsedTimer.fM();

        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        GL11.glLineWidth(1.0F);
        worldRenderer.startDrawing(6);
        int j = adouble.length - 1;

        while (j >= 0) {
            worldRenderer.addVertex(adouble[j], adouble1[j], 0.0);
            j--;
        }

        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GL11.glLineWidth(1.0F);
    }

    public static void drawTriangle(double d0, double d1, double d2, boolean flag) {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        d0 *= 2.0;
        d1 *= 2.0;
        d2 *= 2.0;
        GL11.glBegin(4);
        GL11.glVertex2d(d0, d1 - d2);
        GL11.glVertex2d(d0 - d2 * 2.0, d1 - d2 / 2.0);
        GL11.glVertex2d(d0, d1);
        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }

    public static void disablePrimitiveSmoothing() {
        org.lwjgl.opengl.GL11.glDisable(2848);
        org.lwjgl.opengl.GL11.glDisable(2881);
        org.lwjgl.opengl.GL11.glDisable(2832);
    }

    public static void drawGradientBorder(
            double p171_0, double p171_1, double p171_2, double p171_3, double p171_4, int p171_5) {
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glEnable(2848);
        org.lwjgl.opengl.GL11.glDisable(2929);
        org.lwjgl.opengl.GL11.glDepthMask(false);
        float[] afloat = remix.util.render.Render2D3DUtils.getRGBA(p171_5);
        org.lwjgl.opengl.GL11.glColor4f(afloat[0], afloat[1], afloat[2], afloat[3]);
        remix.util.render.Render2D3DUtils.drawFilledBoundingBox(
                new net.minecraft.util.BoundingBox(
                        p171_0 - p171_3,
                        p171_1,
                        p171_2 - p171_3,
                        p171_0 + p171_3,
                        p171_1 + p171_4,
                        p171_2 + p171_3));
        org.lwjgl.opengl.GL11.glDisable(2848);
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glEnable(2929);
        org.lwjgl.opengl.GL11.glDepthMask(true);
        org.lwjgl.opengl.GL11.glDisable(3042);
        org.lwjgl.opengl.GL11.glPopMatrix();
    }

    public static void drawArrow(double d0, double d1, double d2, boolean flag) {
        {
            ElapsedTimer.fM();

            GL11.glScalef(0.5F, 0.5F, 0.5F);
            d0 *= 2.0;

            d1 *= 2.0;
            d2 *= 2.0;
            GL11.glBegin(4);
            if (flag) {
                GL11.glVertex2d(d0 + d2 / 2.0, d1 - d2);
                GL11.glVertex2d(d0, d1);
                GL11.glVertex2d(d0 + d2, d1);

                
            } else {

                GL11.glVertex2d(d0 + d2, d1 - d2);
                GL11.glVertex2d(d0, d1 - d2);
                GL11.glVertex2d(d0 + d2 / 2.0, d1);
            }
        }

        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }

    public static void drawCheckmark(double d0, double d1, int i) {
        beginUntexturedOverlay();
        glColor(i);
        enablePrimitiveSmoothing();
        GL11.glLineWidth(2.0F);
        GL11.glBegin(3);
        GL11.glVertex2d(d0 + 1.0, d1 + 4.0);
        GL11.glVertex2d(d0 + 3.0, d1 + 6.5);
        GL11.glVertex2d(d0 + 7.0, d1 + 2.0);
        GL11.glEnd();
        disablePrimitiveSmoothing();
        endUntexturedOverlay();
    }

    public static void drawArrowColored(
            double d0, double d1, double d2, boolean flag, int i) {
        beginUntexturedOverlay();
        glColor(i);
        enablePrimitiveSmoothing();
        drawArrow(d0, d1, d2, flag);
        disablePrimitiveSmoothing();
        endUntexturedOverlay();
    }

    public static int interpolateColor(int i, int j, float f) {
        Color color = new Color(i);
        Color color1 = new Color(j);
        f = Math.min(Math.max(0.0F, f), 1.0F);
        float f1 = 1.0F - f;
        float f2 = color.getRed() * f + color1.getRed() * f1;
        float f3 = color.getGreen() * f + color1.getGreen() * f1;
        float f4 = color.getBlue() * f + color1.getBlue() * f1;
        float f5 = color.getAlpha() * f + color1.getAlpha() * f1;
        f2 = Math.min(Math.max(0.0F, f2), 255.0F);
        f3 = Math.min(Math.max(0.0F, f3), 255.0F);
        f4 = Math.min(Math.max(0.0F, f4), 255.0F);
        f5 = Math.min(Math.max(0.0F, f5), 255.0F);

        Color color2;
        try {
            color2 = new Color(f2 / 255.0F, f3 / 255.0F, f4 / 255.0F, f5 / 255.0F);
        } catch (Exception exception) {
            color2 = new Color(-1);
        }

        return color2.getRGB();
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public static Vec3 getInterpolatedPos(Entity entity) {
        float renderPartialTicks = remix.util.render.Render2D3DUtils.minecraft.timer.renderPartialTicks;
        RenderManager renderManager = remix.util.render.Render2D3DUtils.minecraft.getRenderManager();
        double d0 =
                entity.prevPosX
                        + (entity.posX - entity.prevPosX) * renderPartialTicks
                        - renderManager.viewerPosX;
        double d1 =
                entity.prevPosY
                        + (entity.posY - entity.prevPosY) * renderPartialTicks
                        - renderManager.viewerPosY;
        double d2 =
                entity.prevPosZ
                        + (entity.posZ - entity.prevPosZ) * renderPartialTicks
                        - renderManager.viewerPosZ;
        return new Vec3(d0, d1, d2);
    }

    public static void drawBorderedRectSimple(
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
        worldRenderer.addVertex(p171_2, p171_1, 0.0);
        worldRenderer.setVertexColorFromFloats(f1, f2, f3, f);
        worldRenderer.addVertex(p171_0, p171_1, 0.0);
        worldRenderer.setVertexColorFromFloats(f5, f6, f7, f4);
        worldRenderer.addVertex(p171_0, p171_3, 0.0);
        worldRenderer.setVertexColorFromFloats(f5, f6, f7, f4);
        worldRenderer.addVertex(p171_2, p171_3, 0.0);
        instance.draw();
        net.minecraft.client.renderer.GlStateManager.shadeModel(7424);
        net.minecraft.client.renderer.GlStateManager.disableBlend();
        net.minecraft.client.renderer.GlStateManager.enableAlpha();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
    }

    public static double[] worldToScreen(
            double p171_0, double p171_1, double p171_2) {
        org.lwjgl.opengl.GL11.glGetFloat(2982, remix.util.render.Render2D3DUtils.modelViewMatrixBuffer);
        org.lwjgl.opengl.GL11.glGetFloat(2983, remix.util.render.Render2D3DUtils.projectionMatrixBuffer);
        org.lwjgl.opengl.GL11.glGetInteger(2978, remix.util.render.Render2D3DUtils.viewportBuffer);
        org.lwjgl.util.glu.GLU.gluProject(
                (float) p171_0,
                (float) p171_1,
                (float) p171_2,
                remix.util.render.Render2D3DUtils.modelViewMatrixBuffer,
                remix.util.render.Render2D3DUtils.projectionMatrixBuffer,
                remix.util.render.Render2D3DUtils.viewportBuffer,
                remix.util.render.Render2D3DUtils.projectedWindowCoordinates);
        return new double[] {
            remix.util.render.Render2D3DUtils.projectedWindowCoordinates.get(0),
            org.lwjgl.opengl.Display.getHeight() - remix.util.render.Render2D3DUtils.projectedWindowCoordinates.get(1),
            remix.util.render.Render2D3DUtils.projectedWindowCoordinates.get(2)
        };
    }

    public static void drawCircle3D(double d0, double d1, double d2, Color color) {
        byte b0 = 70;
        double d3 = (Math.PI * 2) / b0;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        ElapsedTimer.fM();

        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);

        GL11.glBegin(2);
        int i = 0;

        while (i < b0) {
            float f = (float) (d2 * Math.cos(i * d3));
            float f1 = (float) (d2 * Math.sin(i * d3));
            GL11.glColor4f(
                    color.getRed() / 255.0F,
                    color.getGreen() / 255.0F,
                    color.getBlue() / 255.0F,
                    color.getAlpha() / 255.0F);
            GL11.glVertex2f((float) d0 + f, (float) d1 + f1);
            i++;
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static Color randomOverlayColor() {
        return java.awt.Color.getHSBColor(
                remix.util.render.Render2D3DUtils.randomColorSource.nextFloat(), 0.6F, 0.75F);
    }

    public static int hsvToRgb(float f, float f1, float f2) {
        int i = (int) (f * 6.0F) % 6;
        float f3 = f * 6.0F - i;
        float f4 = f2 * (1.0F - f1);
        ElapsedTimer.fM();

        float f5 = f2 * (1.0F - f3 * f1);

        float f6 = f2 * (1.0F - (1.0F - f3) * f1);
        float f7;
        float f8;
        float f9;
        switch (i) {
            case 0:
                f7 = f2;
                f8 = f6;
                f9 = f4;

                break;

            case 1:
                f7 = f5;
                f8 = f2;
                f9 = f4;

                break;

            case 2:
                f7 = f4;
                f8 = f2;
                f9 = f6;

                break;

            case 3:
                f7 = f4;
                f8 = f5;
                f9 = f2;

                break;

            case 4:
                f7 = f6;
                f8 = f4;
                f9 = f2;

                break;

            case 5:
                f7 = f2;
                f8 = f4;
                f9 = f5;

                break;
            default:
                throw new RuntimeException(
                        "Something went wrong when converting from HSV to RGB. Input was "
                                + f
                                + ", "
                                + f1
                                + ", "
                                + f2);
        }

        int j = MathHelper.clamp((int) (f7 * 255.0F), 0, 255);
        int k = MathHelper.clamp((int) (f8 * 255.0F), 0, 255);
        int l = MathHelper.clamp((int) (f9 * 255.0F), 0, 255);
        return j << 16 | k << 8 | l;
    }

    public static void scissor(
            double d0, double d1, double d2, double d3, int i) {
        GL11.glScissor(
                (int) (d0 * i),
                (int) ((Display.getHeight() / i - d3) * i),
                (int) ((d2 - d0) * i),
                (int) ((d3 - d1) * i));
    }

    public static void drawShadow(
            float p171_0, float p171_1, float p171_2, float p171_3, float p171_4) {
        remix.util.render.Render2D3DUtils.drawGradientRect(
                p171_0, p171_1 - 3.0F, p171_2, p171_1, new float[] {p171_4, p171_4, 0.0F, 0.0F});
        remix.util.render.Render2D3DUtils.drawGradientRect(
                p171_0, p171_3, p171_2, p171_3 + 3.0F, new float[] {0.0F, 0.0F, p171_4, p171_4});
        remix.util.render.Render2D3DUtils.drawGradientRect(
                p171_0 - 3.0F, p171_1, p171_0, p171_3, new float[] {0.0F, p171_4, p171_4, 0.0F});
        remix.util.render.Render2D3DUtils.drawGradientRect(
                p171_2, p171_1, p171_2 + 3.0F, p171_3, new float[] {p171_4, 0.0F, 0.0F, p171_4});
    }

    public static void endUntexturedOverlay() {
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glEnable(2929);
        org.lwjgl.opengl.GL11.glPopMatrix();
    }

    public static void drawIconColored(
            ResourceLocation resourceLocation, float f, float f1, float f2, float f3, int[] aint) {
        drawTextureModalWithPalette(
                resourceLocation,
                (int) f - (int) (f2 / 4.0F),
                f1 - (int) (f3 / 4.0F),
                (int) f2,
                (int) f3,
                aint);
    }

    public static void beginUnoccludedWorldOverlay() {
        net.minecraft.client.renderer.GlStateManager.pushMatrix();
        remix.util.render.Render2D3DUtils.minecraft.entityRenderer.disableLightmap();
        net.minecraft.client.renderer.GlStateManager.enableBlend();
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        net.minecraft.client.renderer.GlStateManager.disableDepth();
        net.minecraft.client.renderer.GlStateManager.depthMask(false);
        net.minecraft.client.renderer.GlStateManager.blendFunc(770, 771);
    }

    public static float[] getRGB(int p171_0) {
        float f = (p171_0 >> 16 & 0xFF) / 255.0F;
        float f1 = (p171_0 >> 8 & 0xFF) / 255.0F;
        float f2 = (p171_0 & 0xFF) / 255.0F;
        return new float[] {f, f1, f2};
    }

    public static void drawDottedLine(double d0, double d1, double d2, double d3) {
        drawLine2D((float) d0, (float) d1, (float) d2, (float) d3);
    }

    public static void drawGradientBetween(int i, int j) {
        float f = (i >> 24 & 0xFF) / 255.0F;
        float f1 = (i >> 16 & 0xFF) / 255.0F;
        float f2 = (i >> 8 & 0xFF) / 255.0F;
        float f3 = (i & 0xFF) / 255.0F;
        GlStateManager.color(f1, f2, f3, j);
    }

    public static void drawBoundingBoxOutline(
            BoundingBox boundingBox, float f, float f1, float f2, float f3) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(f, f1, f2, f3);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(f, f1, f2, f3);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        ElapsedTimer.fM();

        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(f, f1, f2, f3);
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
        worldRenderer.setVertexColorFromFloats(f, f1, f2, f3);
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
        worldRenderer.setVertexColorFromFloats(f, f1, f2, f3);
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
        worldRenderer.setVertexColorFromFloats(f, f1, f2, f3);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        instance.draw();
    }

    public static void enablePrimitiveSmoothing() {
        org.lwjgl.opengl.GL11.glEnable(2848);
        org.lwjgl.opengl.GL11.glEnable(2881);
        org.lwjgl.opengl.GL11.glEnable(2832);
        org.lwjgl.opengl.GL11.glEnable(3042);
        org.lwjgl.opengl.GL11.glBlendFunc(770, 771);
        org.lwjgl.opengl.GL11.glHint(3154, 4354);
        org.lwjgl.opengl.GL11.glHint(3155, 4354);
        org.lwjgl.opengl.GL11.glHint(3153, 4354);
    }

    public static void drawFilledPolygon(
            double[] p171_0, double[] p171_1, int p171_2, int p171_3, float p171_4) {
        remix.util.render.Render2D3DUtils.drawThickPolygon(
                p171_0, p171_1, p171_3, p171_4);
        remix.util.render.Render2D3DUtils.drawPolygon(p171_0, p171_1, p171_2);
    }

    public static void glColor(int p171_0) {
        float f = (p171_0 >> 24 & 0xFF) / 255.0F;
        float f1 = (p171_0 >> 16 & 0xFF) / 255.0F;
        float f2 = (p171_0 >> 8 & 0xFF) / 255.0F;
        float f3 = (p171_0 & 0xFF) / 255.0F;
        net.minecraft.client.renderer.GlStateManager.color(f1, f2, f3, f);
    }

    public static void drawLine2D(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        net.minecraft.client.gui.ScaledResolution scaledResolution =
                new net.minecraft.client.gui.ScaledResolution(
                        remix.util.render.Render2D3DUtils.minecraft,
                        remix.util.render.Render2D3DUtils.minecraft.displayWidth,
                        remix.util.render.Render2D3DUtils.minecraft.displayHeight);
        int scaleFactor = scaledResolution.getScaleFactor();
        org.lwjgl.opengl.GL11.glScissor(
                (int) (p171_0 * scaleFactor),
                (int) ((scaledResolution.getHeight() - p171_3) * scaleFactor),
                (int) ((p171_2 - p171_0) * scaleFactor),
                (int) ((p171_3 - p171_1) * scaleFactor));
    }

    public static void drawThickPolygon(
            double[] adouble, double[] adouble1, int i, float f) {
        ElapsedTimer.fM();

        float f1 = (i >> 16 & 0xFF) / 255.0F;
        float f2 = (i >> 8 & 0xFF) / 255.0F;
        float f3 = (i & 0xFF) / 255.0F;
        float f4 = (i >> 24 & 0xFF) / 255.0F;

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f4);
        GL11.glLineWidth(f);
        worldRenderer.startDrawing(2);
        int j = adouble.length - 1;

        while (j >= 0) {
            worldRenderer.addVertex(adouble[j], adouble1[j], 0.0);
            j--;
        }

        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
    }

    public static void drawCurvedGradient(
            double p171_0,
            double p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7,
            float p171_8,
            float p171_9) {
        float f8 = 1.0F / p171_8;
        float f9 = 1.0F / p171_9;
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                p171_0, p171_1 + p171_7, 0.0, p171_2 * f8, (p171_3 + p171_5) * f9);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_6,
                p171_1 + p171_7,
                0.0,
                (p171_2 + p171_4) * f8,
                (p171_3 + p171_5) * f9);
        worldRenderer.addVertexWithUV(
                p171_0 + p171_6, p171_1, 0.0, (p171_2 + p171_4) * f8, p171_3 * f9);
        worldRenderer.addVertexWithUV(p171_0, p171_1, 0.0, p171_2 * f8, p171_3 * f9);
        instance.draw();
    }

    static {
        remix.util.render.Render2D3DUtils.legacyTextureLocations = new HashMap<>();
        remix.util.render.Render2D3DUtils.minecraft = MinecraftClient.getMinecraft();
        remix.util.render.Render2D3DUtils.randomColorSource = new Random();
        remix.util.render.Render2D3DUtils.projectedWindowCoordinates = BufferUtils.createFloatBuffer(3);
        remix.util.render.Render2D3DUtils.viewportBuffer = BufferUtils.createIntBuffer(16);
        remix.util.render.Render2D3DUtils.modelViewMatrixBuffer = BufferUtils.createFloatBuffer(16);
        remix.util.render.Render2D3DUtils.projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
    }

    public static void drawIconWithAlpha(
            ResourceLocation resourceLocation, float f, float f1, float f2, float f3, float f4) {
        GL11.glPushMatrix();
        remix.util.render.Render2D3DUtils.minecraft.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.color(1.0F, 1.0F, 1.0F, f4);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect(
                f, f1, 0.0F, 0.0F, f2 / 2.0F, f3 / 2.0F, f2 / 2.0F, f3 / 2.0F, f2 / 2.0F,
                f3 / 2.0F);
        GL11.glPopMatrix();
    }

    public static void drawFilledBoundingBox(BoundingBox boundingBox) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        instance.draw();
        worldRenderer.startDrawingQuads();
        ElapsedTimer.fM();
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
            ElapsedTimer.b(new int[3]);
        }
    }

    public static void drawGradientRect(
            double p171_0, double p171_1, double p171_2, double p171_3, float[] p171_4) {
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        net.minecraft.client.renderer.GlStateManager.disableDepth();
        net.minecraft.client.renderer.GlStateManager.enableBlend();
        net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        net.minecraft.client.renderer.GlStateManager.disableAlpha();
        net.minecraft.client.renderer.GlStateManager.shadeModel(7425);
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, p171_4[0]);
        worldRenderer.addVertexWithUV(p171_0, p171_3, 0.0, 0.0, 1.0);
        worldRenderer.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, p171_4[1]);
        worldRenderer.addVertexWithUV(p171_2, p171_3, 0.0, 1.0, 1.0);
        worldRenderer.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, p171_4[2]);
        worldRenderer.addVertexWithUV(p171_2, p171_1, 0.0, 1.0, 0.0);
        worldRenderer.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, p171_4[3]);
        worldRenderer.addVertexWithUV(p171_0, p171_1, 0.0, 0.0, 0.0);
        instance.draw();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
        net.minecraft.client.renderer.GlStateManager.shadeModel(7424);
        net.minecraft.client.renderer.GlStateManager.enableAlpha();
        net.minecraft.client.renderer.GlStateManager.disableBlend();
    }

    public static void drawTriangleColored(
            double d0, double d1, double d2, boolean flag, int i) {
        beginUntexturedOverlay();
        glColor(i);
        enablePrimitiveSmoothing();
        drawTriangle(d0, d1, d2, flag);
        disablePrimitiveSmoothing();
        endUntexturedOverlay();
    }

    public static void drawHollowRect(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        remix.util.render.Render2D3DUtils.drawShadow(
                p171_0, p171_1, p171_2, p171_3, 0.15F);
    }

    public static void drawCenteredIcon(
            ResourceLocation resourceLocation, float f, float f1, float f2, float f3) {
        drawIcon(resourceLocation, f - f2 / 4.0F, f1 - f3 / 4.0F, f2, f3);
    }

    public static void resetGlState() {
        org.lwjgl.opengl.GL11.glEnable(3553);
        org.lwjgl.opengl.GL11.glDisable(3042);
        org.lwjgl.opengl.GL11.glEnable(2929);
        org.lwjgl.opengl.GL11.glDisable(2848);
        org.lwjgl.opengl.GL11.glHint(3154, 4352);
        org.lwjgl.opengl.GL11.glHint(3155, 4352);
    }

    public static void drawCircleOutline(
            float p171_0, float p171_1, float p171_2, int p171_3) {
        remix.util.render.Render2D3DUtils.beginUntexturedOverlay();
        remix.util.render.Render2D3DUtils.enablePrimitiveSmoothing();
        remix.util.render.Render2D3DUtils.glColor(p171_3);
        org.lwjgl.opengl.GL11.glPointSize(p171_2);
        org.lwjgl.opengl.GL11.glBegin(0);
        org.lwjgl.opengl.GL11.glVertex2f(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glEnd();
        remix.util.render.Render2D3DUtils.disablePrimitiveSmoothing();
        remix.util.render.Render2D3DUtils.endUntexturedOverlay();
    }
}
