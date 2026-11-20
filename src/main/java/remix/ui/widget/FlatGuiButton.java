package remix.ui.widget;

import remix.util.network.NordVpnProxyManager;
import remix.util.render.WorldRenderUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;

import java.awt.Color;

public final class FlatGuiButton extends GuiButton {
    protected int hoverInset;
    protected ClientFontRenderer clientFontRenderer = RemixClient.fonts.roboto19;
    protected static final Color themeAccent =
            new Color(((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
    protected static final Color outlineColor = new Color(-14244261);

    @Override
    public void drawButton(MinecraftClient client, int mouseX, int mouseY) {
        boolean legacyFlow = NordVpnProxyManager.getLegacyProxyFlowState() != null;
        if (!this.visible) {
            return;
        }

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        this.updateHoverInset(this.getHoverState(this.hovered) == 2, legacyFlow);
        Gui.drawRect(
                this.xPosition + this.hoverInset,
                this.yPosition,
                this.xPosition + this.width - this.hoverInset,
                this.yPosition + this.height,
                1073741824);
        Gui.drawRect(
                this.xPosition + 4 + this.hoverInset,
                this.yPosition + 2,
                this.xPosition + this.width - 2 - this.hoverInset,
                this.yPosition + this.height - 2,
                -1895825408);
        WorldRenderUtils.drawRoundedOutline(
                this.xPosition + this.hoverInset,
                this.yPosition,
                this.xPosition,
                this.yPosition + this.height,
                1.5F,
                remix.ui.widget.FlatGuiButton.outlineColor.getRGB(),
                remix.ui.widget.FlatGuiButton.themeAccent.getRGB());
        Gui.drawRect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.hoverInset + 1.5,
                this.yPosition + this.height,
                remix.ui.widget.FlatGuiButton.themeAccent.darker().getRGB());
        this.clientFontRenderer.drawCenteredString(
                this.displayString,
                this.xPosition + this.width / 2,
                this.yPosition + (this.height - 10) / 2,
                -1);
    }

    private void updateHoverInset(boolean activeHover, boolean legacyFlow) {
        if (!activeHover) {
            if (--this.hoverInset > 0) {
                return;
            }
            this.hoverInset = 0;
            if (!legacyFlow) {
                return;
            }
        }
        this.hoverInset = Math.min(4, this.hoverInset + 1);
    }

    public FlatGuiButton(int i, int j, int k, String text) {
        super(i, j, k, text);
    }
}
