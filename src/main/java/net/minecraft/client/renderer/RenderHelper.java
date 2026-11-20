package net.minecraft.client.renderer;

import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class RenderHelper {
    private static final Vec3 LIGHT0_POS = new Vec3(0.2F, 1.0, -0.7F).normalize();
    private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
    private static final Vec3 LIGHT1_POS = new Vec3(-0.2F, 1.0, 0.7F).normalize();

    private static FloatBuffer setColorBuffer(double d0, double d1, double d2, double d3) {
        return setColorBuffer((float) d0, (float) d1, (float) d2, (float) d3);
    }

    public static void enableGUIStandardItemLighting() {
        net.minecraft.client.renderer.GlStateManager.pushMatrix();
        net.minecraft.client.renderer.GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
        net.minecraft.client.renderer.GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        net.minecraft.client.renderer.GlStateManager.popMatrix();
    }

    private static FloatBuffer setColorBuffer(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        ((java.nio.Buffer) net.minecraft.client.renderer.RenderHelper.colorBuffer).clear();
        net.minecraft.client.renderer.RenderHelper.colorBuffer.put(p171_0).put(p171_1).put(p171_2).put(p171_3);
        ((java.nio.Buffer) net.minecraft.client.renderer.RenderHelper.colorBuffer).flip();
        return net.minecraft.client.renderer.RenderHelper.colorBuffer;
    }

    public static void disableStandardItemLighting() {
        net.minecraft.client.renderer.GlStateManager.disableLighting();
        net.minecraft.client.renderer.GlStateManager.disableBooleanStateAt(0);
        net.minecraft.client.renderer.GlStateManager.disableBooleanStateAt(1);
        net.minecraft.client.renderer.GlStateManager.disableColorMaterial();
    }

    public static void method_348997132916d5e25a01(int i, int j, float f, int k) {
        float f1 = (k >> 24 & 0xFF) / 255.0F;
        int j1 = WorldRenderer.getLegacyRenderFlowToken();
        float f2 = (k >> 16 & 0xFF) / 255.0F;
        float f3 = (k >> 8 & 0xFF) / 255.0F;
        float f4 = (k & 0xFF) / 255.0F;
        boolean flag = GL11.glIsEnabled(3042);
        int l = j1;
        boolean flag1 = GL11.glIsEnabled(2848);
        boolean flag2 = GL11.glIsEnabled(3553);
        if (!flag) {
            GL11.glEnable(3042);
        }

        if (!flag1) {
            GL11.glEnable(2848);
        }

        if (flag2) {
            GL11.glDisable(3553);
        }

        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glBegin(9);
        int i1 = 0;

        while (i1 <= 360) {
            GL11.glVertex2d(
                    i + Math.sin(i1 * 3.141526 / 180.0) * f,
                    j + Math.cos(i1 * 3.141526 / 180.0) * f);
            i1++;
            if (l != 0) {
                break;
            }
        }

        GL11.glEnd();
        if (flag2) {
            GL11.glEnable(3553);
        }

        if (!flag1) {
            GL11.glDisable(2848);
        }

        if (!flag) {
            GL11.glDisable(3042);
        }
    }

    public static void enableStandardItemLighting() {
        net.minecraft.client.renderer.GlStateManager.enableLighting();
        net.minecraft.client.renderer.GlStateManager.enableBooleanStateAt(0);
        net.minecraft.client.renderer.GlStateManager.enableBooleanStateAt(1);
        net.minecraft.client.renderer.GlStateManager.enableColorMaterial();
        net.minecraft.client.renderer.GlStateManager.colorMaterial(1032, 5634);
        float f = 0.4F;
        float f1 = 0.6F;
        float f2 = 0.0F;
        org.lwjgl.opengl.GL11.glLight(
                16384,
                4611,
                net.minecraft.client.renderer.RenderHelper.setColorBuffer(
                        net.minecraft.client.renderer.RenderHelper.LIGHT0_POS.x,
                        net.minecraft.client.renderer.RenderHelper.LIGHT0_POS.y,
                        net.minecraft.client.renderer.RenderHelper.LIGHT0_POS.z,
                        0.0));
        org.lwjgl.opengl.GL11.glLight(
                16384, 4609, net.minecraft.client.renderer.RenderHelper.setColorBuffer(f1, f1, f1, 1.0F));
        org.lwjgl.opengl.GL11.glLight(
                16384, 4608, net.minecraft.client.renderer.RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        org.lwjgl.opengl.GL11.glLight(
                16384, 4610, net.minecraft.client.renderer.RenderHelper.setColorBuffer(f2, f2, f2, 1.0F));
        org.lwjgl.opengl.GL11.glLight(
                16385,
                4611,
                net.minecraft.client.renderer.RenderHelper.setColorBuffer(
                        net.minecraft.client.renderer.RenderHelper.LIGHT1_POS.x,
                        net.minecraft.client.renderer.RenderHelper.LIGHT1_POS.y,
                        net.minecraft.client.renderer.RenderHelper.LIGHT1_POS.z,
                        0.0));
        org.lwjgl.opengl.GL11.glLight(
                16385, 4609, net.minecraft.client.renderer.RenderHelper.setColorBuffer(f1, f1, f1, 1.0F));
        org.lwjgl.opengl.GL11.glLight(
                16385, 4608, net.minecraft.client.renderer.RenderHelper.setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        org.lwjgl.opengl.GL11.glLight(
                16385, 4610, net.minecraft.client.renderer.RenderHelper.setColorBuffer(f2, f2, f2, 1.0F));
        net.minecraft.client.renderer.GlStateManager.shadeModel(7424);
        org.lwjgl.opengl.GL11.glLightModel(2899, net.minecraft.client.renderer.RenderHelper.setColorBuffer(f, f, f, 1.0F));
    }
}
