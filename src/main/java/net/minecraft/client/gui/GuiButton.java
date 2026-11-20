package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
    public int xPosition;
    public boolean visible;

    protected int width = 200;
    protected static ResourceLocation buttonTextures;
    public int yPosition;
    public int id;

    protected int height = 20;
    private static String kK;
    public String displayString;
    public boolean enabled = true;

    protected boolean hovered;

    public GuiButton(int i, int j, int k, int l, int heightValue, String text) {
        this.visible = true;
        this.id = i;
        this.xPosition = j;
        this.yPosition = k;
        this.width = l;
        this.height = heightValue;
        this.displayString = text;
    }

    public GuiButton(int i, int j, int k, String text) {
        this(i, j, k, 200, 20, text);
    }

    public void setDisplayString(String text) {
        this.displayString = text;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    // region String decoding support

    // endregion

    public static String method_cee993844cce4b97ed62() {
        return kK;
    }

    public void mouseReleased(int i, int j) {}

    protected int getHoverState(boolean flag) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        byte b0 = 1;
        String[] astring = astring1;
        if (!this.enabled) {
            b0 = 0;
            if (astring == null) {
                return b0;
            }
        }

        if (flag) {
            b0 = 2;
        }

        return b0;
    }

    public boolean mousePressed(MinecraftClient minecraftClient, int i, int j) {
        return this.enabled
                && this.visible
                && i >= this.xPosition
                && j >= this.yPosition
                && i < this.xPosition + this.width
                && j < this.yPosition + this.height;
    }

    protected void mouseDragged(MinecraftClient minecraftClient, int i, int j) {}

    public void drawButtonForegroundLayer(int i, int j) {}

    public void setWidth(int widthValue) {
        this.width = widthValue;
    }

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.visible) {
            FontRenderer fontRenderer;
            int l;
            {
                fontRenderer = minecraftClient.fontRendererObj;
                minecraftClient
                        .getTextureManager()
                        .bindTexture(net.minecraft.client.gui.GuiButton.buttonTextures);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (i >= this.xPosition
                                                && j >= this.yPosition
                                                && i < this.xPosition + this.width
                                                && j < this.yPosition + this.height
                                        ? 1
                                        : 0));
                int k = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
                this.drawTexturedModalRect(
                        this.xPosition,
                        this.yPosition,
                        0,
                        46 + k * 20,
                        this.width / 2,
                        this.height);
                this.drawTexturedModalRect(
                        this.xPosition + this.width / 2,
                        this.yPosition,
                        200 - this.width / 2,
                        46 + k * 20,
                        this.width / 2,
                        this.height);
                this.mouseDragged(minecraftClient, i, j);
                l = 14737632;
                if (!this.enabled) {
                    l = 10526880;
                    if (!(astring == null)) {

                        if (this.hovered) {
                            l = 16777120;
                        }
                    }
                } else {

                    if (this.hovered) {
                        l = 16777120;
                    }
                }
            }

            this.drawCenteredString(
                    fontRenderer,
                    this.displayString,
                    this.xPosition + this.width / 2,
                    this.yPosition + (this.height - 8) / 2,
                    l);
        }
    }

    public static void b(String text) {
        kK = text;
    }

    // region String decoding support

    // endregion

    public int getButtonWidth() {
        return this.width;
    }

    static {
        b(((java.lang.String) (null)));
        net.minecraft.client.gui.GuiButton.buttonTextures =
                new ResourceLocation("textures/gui/widgets.png");
    }

    public void playPressSound(SoundHandler soundHandler) {
        soundHandler.playSound(
                PositionedSoundRecord.createPositionedSoundRecord(
                        new ResourceLocation("gui.button.press"), 1.0F));
    }
}
