package remix.ui.widget;

import remix.util.render.WorldRenderUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;

import org.lwjgl.opengl.GL11;

import java.awt.Color;

public final class ColoredGuiButton extends GuiButton {
    private Color color;
    private int hoverInset;
    private ClientFontRenderer clientFontRenderer = RemixClient.fonts.roboto21;

    @Override
    public void drawButton(MinecraftClient client, int mouseX, int mouseY) {
        boolean legacyFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        if (!this.visible) {
            return;
        }

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        boolean enabled = this.enabled;
        int outlineColor = enabled ? this.color.getRGB() : -907757;
        if (!this.hovered) {
            this.hoverInset = Math.max(0, this.hoverInset - 1);
            this.drawButtonFill(1073741824);
        }
        if (this.hovered || legacyFlow) {
            this.hoverInset = Math.min(3, this.hoverInset + 1);
            this.drawButtonFill(1056964607);
        }

        Gui.drawRect(
                this.xPosition + 4 + this.hoverInset,
                this.yPosition + 2,
                this.xPosition + this.width - 2 - this.hoverInset,
                this.yPosition + this.height - 2,
                -1895825408);
        WorldRenderUtils.drawRoundedOutline(
                this.xPosition + this.hoverInset,
                this.yPosition,
                this.xPosition - this.hoverInset,
                this.yPosition + this.height,
                1.5F,
                outlineColor + 500,
                outlineColor);
        this.mouseDragged(client, mouseX, mouseY);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.clientFontRenderer.drawCenteredString(
                this.displayString,
                this.xPosition + this.width / 2,
                this.yPosition + (this.height - 8) / 2,
                enabled ? -1052689 : -4210753);
    }

    private void drawButtonFill(int color) {
        Gui.drawRect(this.xPosition + this.hoverInset, this.yPosition,
                this.xPosition + this.width - this.hoverInset, this.yPosition + this.height, color);
    }

    public ColoredGuiButton(int i, int j, int k, String text) {
        super(i, j, k, 200, 20, text);
        this.color =
                new Color(
                        ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
    }

    public ColoredGuiButton(int i, int j, int k, int l, int i1, String text) {
        super(i, j, k, l, i1, text);
        this.color =
                new Color(
                        ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
    }
}
