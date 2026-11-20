package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

public class GuiStreamIndicator {
    private int pulseDirection;
    private static String[] packedTextFragments;
    private float pulseBrightness = 1.0F;
    private MinecraftClient mc;
    private static ResourceLocation locationStreamIndicator;

    public void updatePulse() {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.mc.getTwitchStream().isBroadcasting()) {
            this.pulseBrightness = this.pulseBrightness + 0.025F * this.pulseDirection;
            if (this.pulseBrightness < 0.0F) {
                this.pulseDirection *= -1;
                this.pulseBrightness = 0.0F;
                if (astring == null) {
                    return;
                }
            }

            if (!(this.pulseBrightness > 1.0F)) {
                return;
            }

            this.pulseDirection *= -1;
            this.pulseBrightness = 1.0F;
            if (astring == null) {
                return;
            }
        }

        this.pulseBrightness = 1.0F;
        this.pulseDirection = 1;
    }

    public GuiStreamIndicator(MinecraftClient minecraftClient) {
        this.pulseDirection = 1;
        this.mc = minecraftClient;
    }

    // region String decoding support

    // endregion

    public void render(int i, int j) {
        if (this.mc.getTwitchStream().isBroadcasting()) {
            GlStateManager.enableBlend();
            int k = this.mc.getTwitchStream().func_152920_A();
            if (k > 0) {
                String text = packedTextFragments[0] + k;
                int l = this.mc.fontRendererObj.getStringWidth(text);

                int i1 = i - l - 1;
                int j1 = j + 20 - 1;
                int k1 = j + 20 + this.mc.fontRendererObj.FONT_HEIGHT - 1;
                GlStateManager.disableTexture2D();
                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                GlStateManager.color(
                        0.0F, 0.0F, 0.0F, (0.65F + 0.35000002F * this.pulseBrightness) / 2.0F);
                worldRenderer.startDrawingQuads();
                worldRenderer.addVertex(i1, k1, 0.0);
                worldRenderer.addVertex(i, k1, 0.0);
                worldRenderer.addVertex(i, j1, 0.0);
                worldRenderer.addVertex(i1, j1, 0.0);
                instance.draw();
                GlStateManager.enableTexture2D();
                this.mc.fontRendererObj.drawString(text, i - l, j + 20, 16777215);
            }

            this.render(i, j, this.func_152440_b(), 0);
            this.render(i, j, this.func_152438_c(), 17);
        }
    }

    static {
        net.minecraft.client.gui.GuiStreamIndicator.packedTextFragments = new String[2];
        net.minecraft.client.gui.GuiStreamIndicator.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiStreamIndicator.packedTextFragments[1] =
                new String("Tn*MS?MS$5LOb[Ty7XK\u0012ANo;ZG9GR%\"WA");
        net.minecraft.client.gui.GuiStreamIndicator.locationStreamIndicator =
                new ResourceLocation("textures/gui/stream_indicator.png");
    }

    private int func_152438_c() {
        return this.mc.getTwitchStream().func_152929_G() ? 48 : 32;
    }

    // region String decoding support

    // endregion

    private void render(int i, int j, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.65F + 0.35000002F * this.pulseBrightness);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiStreamIndicator.locationStreamIndicator);
        float f = 150.0F;
        float f1 = 0.0F;
        float f2 = k * 0.015625F;
        float f3 = 1.0F;
        float f4 = (k + 16) * 0.015625F;
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(i - 16 - l, j + 16, f, f1, f4);
        worldRenderer.addVertexWithUV(i - l, j + 16, f, f3, f4);
        worldRenderer.addVertexWithUV(i - l, j + 0, f, f3, f2);
        worldRenderer.addVertexWithUV(i - 16 - l, j + 0, f, f1, f2);
        instance.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    // region String decoding support

    // endregion

    private int func_152440_b() {
        return this.mc.getTwitchStream().isPaused() ? 16 : 0;
    }
}
