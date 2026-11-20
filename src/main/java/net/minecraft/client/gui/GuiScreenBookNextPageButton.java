package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;

class GuiScreenBookNextPageButton extends GuiButton {
    private static String[] packedTextFragments;
    private boolean lL;

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        if (this.visible) {
            int k =
                    i >= this.xPosition
                                    && j >= this.yPosition
                                    && i < this.xPosition + this.width
                                    && j < this.yPosition + this.height
                            ? 1
                            : 0;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            minecraftClient.getTextureManager().bindTexture(GuiScreenBook.getBookTexture());
            byte b0 = 0;
            short short1 = 192;
            if (k != 0) {
                b0 += 23;
            }

            if (!this.lL) {
                short1 += 13;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, b0, short1, 23, 13);
        }
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiScreenBookNextPageButton.packedTextFragments[0] = new String("");
    }

    // endregion

    public GuiScreenBookNextPageButton(int i, int j, int k, boolean flag) {
        super(i, j, k, 23, 13, packedTextFragments[0]);
        this.lL = flag;
    }
}
