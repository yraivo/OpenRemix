package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBeaconBeamSegment;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import java.util.List;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer {

    private static ResourceLocation beaconBeam;

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer.beaconBeam =
                new ResourceLocation("textures/entity/beacon_beam.png");
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        this.method_0b61601b6c3840937776((TileEntityBeacon) tileEntity, d0, d1, d2, f, i);
    }

    public void method_0b61601b6c3840937776(
            TileEntityBeacon tileEntityBeacon, double d0, double d1, double d2, float f, int l) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        float f1 = tileEntityBeacon.shouldBeamRender();

        GlStateManager.alphaFunc(516, 0.1F);
        if (f1 > 0.0F) {
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            List beamSegments = tileEntityBeacon.getBeamSegments();
            int i = 0;
            int j = 0;

            while (j < beamSegments.size()) {
                TileEntityBeaconBeamSegment tileEntityBeaconBeamSegment =
                        (TileEntityBeaconBeamSegment) beamSegments.get(j);
                int k = i + tileEntityBeaconBeamSegment.getHeight();
                this.bindTexture(net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer.beaconBeam);
                GL11.glTexParameterf(3553, 10242, 10497.0F);
                GL11.glTexParameterf(3553, 10243, 10497.0F);
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                GlStateManager.disableBlend();
                GlStateManager.depthMask(true);
                GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
                float f2 = (float) tileEntityBeacon.getWorld().getTotalWorldTime() + f;
                float f3 = -f2 * 0.2F - MathHelper.floor(-f2 * 0.1F);
                double d3 = f2 * 0.025 * -1.5;
                worldRenderer.startDrawingQuads();
                double d4 = 0.2;
                double d5 = 0.5 + Math.cos(d3 + (Math.PI * 3.0 / 4.0)) * d4;
                double d6 = 0.5 + Math.sin(d3 + (Math.PI * 3.0 / 4.0)) * d4;
                double d7 = 0.5 + Math.cos(d3 + (Math.PI / 4)) * d4;
                double d8 = 0.5 + Math.sin(d3 + (Math.PI / 4)) * d4;
                double d9 = 0.5 + Math.cos(d3 + (Math.PI * 5.0 / 4.0)) * d4;
                double d10 = 0.5 + Math.sin(d3 + (Math.PI * 5.0 / 4.0)) * d4;
                double d11 = 0.5 + Math.cos(d3 + (Math.PI * 7.0 / 4.0)) * d4;
                double d12 = 0.5 + Math.sin(d3 + (Math.PI * 7.0 / 4.0)) * d4;
                double d13 = 0.0;
                double d14 = 1.0;
                double d15 = -1.0F + f3;
                double d16 = tileEntityBeaconBeamSegment.getHeight() * f1 * (0.5 / d4) + d15;
                worldRenderer.setVertexColorFromFloats(
                        tileEntityBeaconBeamSegment.getColors()[0],
                        tileEntityBeaconBeamSegment.getColors()[1],
                        tileEntityBeaconBeamSegment.getColors()[2],
                        0.125F);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + k, d2 + d6, d14, d16);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + i, d2 + d6, d14, d15);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + i, d2 + d8, d13, d15);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + k, d2 + d8, d13, d16);
                worldRenderer.addVertexWithUV(d0 + d11, d1 + k, d2 + d12, d14, d16);
                worldRenderer.addVertexWithUV(d0 + d11, d1 + i, d2 + d12, d14, d15);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + i, d2 + d10, d13, d15);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + k, d2 + d10, d13, d16);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + k, d2 + d8, d14, d16);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + i, d2 + d8, d14, d15);
                worldRenderer.addVertexWithUV(d0 + d11, d1 + i, d2 + d12, d13, d15);
                worldRenderer.addVertexWithUV(d0 + d11, d1 + k, d2 + d12, d13, d16);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + k, d2 + d10, d14, d16);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + i, d2 + d10, d14, d15);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + i, d2 + d6, d13, d15);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + k, d2 + d6, d13, d16);
                instance.draw();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.depthMask(false);
                worldRenderer.startDrawingQuads();
                worldRenderer.setVertexColorFromFloats(
                        tileEntityBeaconBeamSegment.getColors()[0],
                        tileEntityBeaconBeamSegment.getColors()[1],
                        tileEntityBeaconBeamSegment.getColors()[2],
                        0.125F);
                d3 = 0.2;
                d4 = 0.2;
                d5 = 0.8;
                d6 = 0.2;
                d7 = 0.2;
                d8 = 0.8;
                d9 = 0.8;
                d10 = 0.8;
                d11 = 0.0;
                d12 = 1.0;
                d13 = -1.0F + f3;
                d14 = tileEntityBeaconBeamSegment.getHeight() * f1 + d13;
                worldRenderer.addVertexWithUV(d0 + d3, d1 + k, d2 + d4, d12, d14);
                worldRenderer.addVertexWithUV(d0 + d3, d1 + i, d2 + d4, d12, d13);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + i, d2 + d6, d11, d13);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + k, d2 + d6, d11, d14);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + k, d2 + d10, d12, d14);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + i, d2 + d10, d12, d13);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + i, d2 + d8, d11, d13);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + k, d2 + d8, d11, d14);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + k, d2 + d6, d12, d14);
                worldRenderer.addVertexWithUV(d0 + d5, d1 + i, d2 + d6, d12, d13);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + i, d2 + d10, d11, d13);
                worldRenderer.addVertexWithUV(d0 + d9, d1 + k, d2 + d10, d11, d14);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + k, d2 + d8, d12, d14);
                worldRenderer.addVertexWithUV(d0 + d7, d1 + i, d2 + d8, d12, d13);
                worldRenderer.addVertexWithUV(d0 + d3, d1 + i, d2 + d4, d11, d13);
                worldRenderer.addVertexWithUV(d0 + d3, d1 + k, d2 + d4, d11, d14);
                instance.draw();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.depthMask(true);
                i = k;
                j++;
            }
        }
    }

    // region String decoding support

    // endregion
}
