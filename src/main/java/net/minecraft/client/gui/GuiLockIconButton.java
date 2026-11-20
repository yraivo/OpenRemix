package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {
    private static String[] packedTextFragments;
    private boolean locked = false;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiLockIconButton.packedTextFragments[0] = new String("");
    }

    // endregion

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.visible) {
            GuiLockIconButtonIcon guiLockIconButtonIcon;
            label51:
            {
                minecraftClient.getTextureManager().bindTexture(GuiButton.buttonTextures);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                int k =
                        i >= this.xPosition
                                        && j >= this.yPosition
                                        && i < this.xPosition + this.width
                                        && j < this.yPosition + this.height
                                ? 1
                                : 0;
                if (this.locked) {
                    if (!this.enabled) {
                        guiLockIconButtonIcon = GuiLockIconButtonIcon.LOCKED_DISABLED;
                        if (astring == null) {
                            break label51;
                        }
                    }

                    if (k != 0) {
                        guiLockIconButtonIcon = GuiLockIconButtonIcon.LOCKED_HOVER;
                        if (astring == null) {
                            break label51;
                        }
                    }

                    guiLockIconButtonIcon = GuiLockIconButtonIcon.LOCKED;
                    if (astring == null) {
                        break label51;
                    }
                }

                if (!this.enabled) {
                    guiLockIconButtonIcon = GuiLockIconButtonIcon.UNLOCKED_DISABLED;
                    if (astring == null) {
                        break label51;
                    }
                }

                if (k != 0) {
                    guiLockIconButtonIcon = GuiLockIconButtonIcon.UNLOCKED_HOVER;
                    if (astring == null) {
                        break label51;
                    }
                }

                guiLockIconButtonIcon = GuiLockIconButtonIcon.UNLOCKED;
            }

            this.drawTexturedModalRect(
                    this.xPosition,
                    this.yPosition,
                    guiLockIconButtonIcon.method_9b417d27949cba33a0d0(),
                    guiLockIconButtonIcon.method_7eec03da329a017ec143(),
                    this.width,
                    this.height);
        }
    }

    public void setLocked(boolean flag) {
        this.locked = flag;
    }

    public boolean isLocked() {
        return this.locked;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion

    public GuiLockIconButton(int i, int j, int k) {
        super(i, j, k, 20, 20, packedTextFragments[0]);
    }
}
