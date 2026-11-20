package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManagerTexGen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer {
    private static ResourceLocation END_SKY_TEXTURE;
    private static Random awd;

    FloatBuffer uT = GLAllocation.createDirectFloatBuffer(16);
    private static ResourceLocation END_PORTAL_TEXTURE;

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        this.method_f0b5bfb744fa17543d63((TileEntityEndPortal) tileEntity, d0, d1, d2, f, i);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer.END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
        net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer.END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
        awd = new Random(31100L);
    }

    private FloatBuffer method_3e22920ebeb4c305388f(float f, float f1, float f2, float f3) {
        ((Buffer) this.uT).clear();
        this.uT.put(f).put(f1).put(f2).put(f3);
        ((Buffer) this.uT).flip();
        return this.uT;
    }

    public void method_f0b5bfb744fa17543d63(
            TileEntityEndPortal tileEntityEndPortal,
            double d0,
            double d1,
            double d2,
            float f14,
            int i1) {
        float entityX = (float) this.rendererDispatcher.entityX;
        float entityY = (float) this.rendererDispatcher.entityY;
        float entityZ = (float) this.rendererDispatcher.entityZ;
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        GlStateManager.disableLighting();

        awd.setSeed(31100L);
        float f3 = 0.75F;
        int i = 0;

        while (i < 16) {
            GlStateManager.pushMatrix();
            float f4 = 16 - i;
            float f5 = 0.0625F;
            float f6 = 1.0F / (f4 + 1.0F);
            if (i == 0) {
                this.bindTexture(net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer.END_SKY_TEXTURE);
                f6 = 0.1F;
                f4 = 65.0F;
                f5 = 0.125F;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
            }

            if (i >= 1) {
                this.bindTexture(net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer.END_PORTAL_TEXTURE);
            }

            if (i == 1) {
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(1, 1);
                f5 = 0.5F;
            }

            float f7 = (float) (-(d1 + f3));
            float f8 = f7 + (float) ActiveRenderInfo.getPosition().y;
            float f9 = f7 + f4 + (float) ActiveRenderInfo.getPosition().y;
            float f10 = f8 / f9;
            f10 += (float) (d1 + f3);
            GlStateManager.translate(entityX, f10, entityZ);
            GlStateManager.texGen(GlStateManagerTexGen.aQa, 9217);
            GlStateManager.texGen(GlStateManagerTexGen.aQg, 9217);
            GlStateManager.texGen(GlStateManagerTexGen.aQf, 9217);
            GlStateManager.texGen(GlStateManagerTexGen.aQb, 9216);
            GlStateManager.func_179105_a(
                    GlStateManagerTexGen.aQa,
                    9473,
                    this.method_3e22920ebeb4c305388f(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.func_179105_a(
                    GlStateManagerTexGen.aQg,
                    9473,
                    this.method_3e22920ebeb4c305388f(0.0F, 0.0F, 1.0F, 0.0F));
            GlStateManager.func_179105_a(
                    GlStateManagerTexGen.aQf,
                    9473,
                    this.method_3e22920ebeb4c305388f(0.0F, 0.0F, 0.0F, 1.0F));
            GlStateManager.func_179105_a(
                    GlStateManagerTexGen.aQb,
                    9474,
                    this.method_3e22920ebeb4c305388f(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.enableTexGenCoord(GlStateManagerTexGen.aQa);
            GlStateManager.enableTexGenCoord(GlStateManagerTexGen.aQg);
            GlStateManager.enableTexGenCoord(GlStateManagerTexGen.aQf);
            GlStateManager.enableTexGenCoord(GlStateManagerTexGen.aQb);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            long j = MinecraftClient.getSystemTime();

            GlStateManager.translate(0.0F, (float) (j % 700000L) / 700000.0F, 0.0F);
            GlStateManager.scale(f5, f5, f5);
            GlStateManager.translate(0.5F, 0.5F, 0.0F);
            GlStateManager.rotate((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.0F);
            GlStateManager.translate(-entityX, -entityZ, -entityY);
            f8 = f7 + (float) ActiveRenderInfo.getPosition().y;
            GlStateManager.translate(
                    (float) ActiveRenderInfo.getPosition().x * f4 / f8,
                    (float) ActiveRenderInfo.getPosition().z * f4 / f8,
                    -entityY);
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawingQuads();
            float f11 = awd.nextFloat() * 0.5F + 0.1F;
            float f12 = awd.nextFloat() * 0.5F + 0.4F;
            float f13 = awd.nextFloat() * 0.5F + 0.5F;
            if (i == 0) {
                f13 = 1.0F;
                f12 = 1.0F;
                f11 = 1.0F;
            }

            worldRenderer.setVertexColorFromFloats(f11 * f6, f12 * f6, f13 * f6, 1.0F);
            worldRenderer.addVertex(d0, d1 + f3, d2);
            worldRenderer.addVertex(d0, d1 + f3, d2 + 1.0);
            worldRenderer.addVertex(d0 + 1.0, d1 + f3, d2 + 1.0);
            worldRenderer.addVertex(d0 + 1.0, d1 + f3, d2);
            instance.draw();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            i++;
        }

        GlStateManager.disableBlend();
        GlStateManager.disableTexGenCoord(GlStateManagerTexGen.aQa);
        GlStateManager.disableTexGenCoord(GlStateManagerTexGen.aQg);
        GlStateManager.disableTexGenCoord(GlStateManagerTexGen.aQf);
        GlStateManager.disableTexGenCoord(GlStateManagerTexGen.aQb);
        GlStateManager.enableLighting();
    }

    // region String decoding support

    // endregion
}
