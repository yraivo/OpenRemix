package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;

class GuiMerchantMerchantButton extends GuiButton {
    private static String[] packedTextFragments;
    private boolean field_146157_o;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiMerchantMerchantButton.packedTextFragments[0] = new String("");
    }

    // endregion

    public GuiMerchantMerchantButton(int i, int j, int k, boolean flag) {
        super(i, j, k, 12, 19, packedTextFragments[0]);
        this.field_146157_o = flag;
    }

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.visible) {
            int l;
            int i1;
            {
                minecraftClient.getTextureManager().bindTexture(GuiMerchant.access$000());
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                int k =
                        i >= this.xPosition
                                        && j >= this.yPosition
                                        && i < this.xPosition + this.width
                                        && j < this.yPosition + this.height
                                ? 1
                                : 0;
                l = 0;
                i1 = 176;
                if (!this.enabled) {
                    i1 += this.width * 2;
                    if (!(astring == null)) {

                        if (k != 0) {
                            i1 += this.width;
                        }
                    }
                } else {

                    if (k != 0) {
                        i1 += this.width;
                    }
                }
            }

            if (!this.field_146157_o) {
                l += this.height;
            }

            this.drawTexturedModalRect(
                    this.xPosition, this.yPosition, i1, l, this.width, this.height);
        }
    }
}
