package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLanguage extends GuiButton {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiButtonLanguage.packedTextFragments[0] = new String("");
    }

    // endregion

    public GuiButtonLanguage(int i, int j, int k) {
        super(i, j, k, 20, 20, packedTextFragments[0]);
    }

    // region String decoding support

    // endregion

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        if (this.visible) {
            minecraftClient.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int k =
                    i >= this.xPosition
                                    && j >= this.yPosition
                                    && i < this.xPosition + this.width
                                    && j < this.yPosition + this.height
                            ? 1
                            : 0;
            int l = 106;
            if (k != 0) {
                l += this.height;
            }

            this.drawTexturedModalRect(
                    this.xPosition, this.yPosition, 0, l, this.width, this.height);
        }
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }
}
