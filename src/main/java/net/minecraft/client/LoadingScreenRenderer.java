package net.minecraft.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;

public class LoadingScreenRenderer implements IProgressUpdate {
    private static String[] packedTextFragments;
    private ScaledResolution scaledResolution;
    private Framebuffer framebuffer;
    private boolean field_73724_e;
    private String message;
    private long systemTime;
    private String currentlyDisplayedText;
    private MinecraftClient mc;

    private void displayString(String text) {
        String textValue = MinecraftClient.getLegacyClientFlowMarker();
        this.currentlyDisplayedText = text;
        String textValue2 = textValue;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        } else {
            label16:
            {
                GlStateManager.clear(256);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    int scaleFactor = this.scaledResolution.getScaleFactor();
                    GlStateManager.ortho(
                            0.0,
                            this.scaledResolution.getWidth() * scaleFactor,
                            this.scaledResolution.getHeight() * scaleFactor,
                            0.0,
                            100.0,
                            300.0);
                    if (textValue2 == null) {
                        break label16;
                    }
                }

                ScaledResolution scaledResolution =
                        new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                GlStateManager.ortho(
                        0.0,
                        scaledResolution.getWidthDouble(),
                        scaledResolution.getHeightDouble(),
                        0.0,
                        100.0,
                        300.0);
            }

            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -200.0F);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.LoadingScreenRenderer.packedTextFragments[0] = new String("");
        net.minecraft.client.LoadingScreenRenderer.packedTextFragments[1] = new String("");
    }

    // endregion

    public void setDoneWorking() {}

    public void displaySavingString(String text) {
        this.field_73724_e = true;
        this.displayString(text);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public void setLoadingProgress(int i) {
        String text = MinecraftClient.getLegacyClientFlowMarker();
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        } else {
            long j = MinecraftClient.getSystemTime();
            long l1 = j - this.systemTime;

            if (l1 >= 100L) {
                ScaledResolution scaledResolution;
                int k;
                int l;
                int i1;
                {
                    this.systemTime = j;
                    scaledResolution =
                            new ScaledResolution(
                                    this.mc, this.mc.displayWidth, this.mc.displayHeight);
                    k = scaledResolution.getScaleFactor();
                    l = scaledResolution.getWidth();
                    i1 = scaledResolution.getHeight();
                    if (OpenGlHelper.isFramebufferEnabled()) {
                        this.framebuffer.framebufferClear();
                        if (!(text == null)) {

                            GlStateManager.clear(256);
                        }
                    } else {

                        GlStateManager.clear(256);
                    }
                }

                this.framebuffer.bindFramebuffer(false);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.ortho(
                        0.0,
                        scaledResolution.getWidthDouble(),
                        scaledResolution.getHeightDouble(),
                        0.0,
                        100.0,
                        300.0);
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -200.0F);
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GlStateManager.clear(16640);
                }

                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                float f = 32.0F;
                worldRenderer.startDrawingQuads();
                worldRenderer.setOpaqueVertexColorFromRgb(4210752);
                worldRenderer.addVertexWithUV(0.0, i1, 0.0, 0.0, i1 / f);
                worldRenderer.addVertexWithUV(l, i1, 0.0, l / f, i1 / f);
                worldRenderer.addVertexWithUV(l, 0.0, 0.0, l / f, 0.0);
                worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                instance.draw();
                if (i >= 0) {
                    byte b0 = 100;
                    byte b1 = 2;
                    int j1 = l / 2 - b0 / 2;
                    int k1 = i1 / 2 + 16;
                    GlStateManager.disableTexture2D();
                    worldRenderer.startDrawingQuads();
                    worldRenderer.setOpaqueVertexColorFromRgb(8421504);
                    worldRenderer.addVertex(j1, k1, 0.0);
                    worldRenderer.addVertex(j1, k1 + b1, 0.0);
                    worldRenderer.addVertex(j1 + b0, k1 + b1, 0.0);
                    worldRenderer.addVertex(j1 + b0, k1, 0.0);
                    worldRenderer.setOpaqueVertexColorFromRgb(8454016);
                    worldRenderer.addVertex(j1, k1, 0.0);
                    worldRenderer.addVertex(j1, k1 + b1, 0.0);
                    worldRenderer.addVertex(j1 + i, k1 + b1, 0.0);
                    worldRenderer.addVertex(j1 + i, k1, 0.0);
                    instance.draw();
                    GlStateManager.enableTexture2D();
                }

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.mc.fontRendererObj.drawStringWithShadow(
                        this.currentlyDisplayedText,
                        (l - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2,
                        i1 / 2 - 4 - 16,
                        16777215);
                this.mc.fontRendererObj.drawStringWithShadow(
                        this.message,
                        (l - this.mc.fontRendererObj.getStringWidth(this.message)) / 2,
                        i1 / 2 - 4 + 8,
                        16777215);
                this.framebuffer.unbindFramebuffer();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferRender(l * k, i1 * k);
                }

                this.mc.updateDisplay();

                try {
                    Thread.yield();
                } catch (Exception exception) {
                }
            }
        }
    }

    public void resetProgressAndMessage(String text) {
        this.field_73724_e = false;
        this.displayString(text);
    }

    public LoadingScreenRenderer(MinecraftClient minecraftClient) {
        this.message = packedTextFragments[0];
        this.currentlyDisplayedText = packedTextFragments[1];
        this.systemTime = MinecraftClient.getSystemTime();
        this.mc = minecraftClient;
        this.scaledResolution =
                new ScaledResolution(
                        minecraftClient,
                        minecraftClient.displayWidth,
                        minecraftClient.displayHeight);
        this.framebuffer =
                new Framebuffer(minecraftClient.displayWidth, minecraftClient.displayHeight, false);
        this.framebuffer.setFramebufferFilter(9728);
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public void displayLoadingString(String text) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        } else {

            this.systemTime = 0L;
            this.message = text;
            this.setLoadingProgress(-1);

            this.systemTime = 0L;
        }
    }

    // region String decoding support

    // endregion
}
