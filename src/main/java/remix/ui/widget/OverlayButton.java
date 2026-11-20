package remix.ui.widget;

import net.minecraft.client.gui.Gui;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.OverlayWidget;

public final class OverlayButton extends OverlayWidget {
    private String label;

    public float getHeight() {
        return this.bottom - this.top;
    }

    public void updateHoverState(int i, int j) {
        Gui.drawRect(this.left, this.top, this.right, this.bottom, this.color);
        this.drawCenteredLabel(this.label, 1);
        super.updateHoverState(i, j);
    }

    public OverlayButton(
            int i, String text, float f, float f1, float f2, float f3, int j) {
        super(i, f, f1, f2, f3, j);
        this.label = text;
    }

    public void setLabel(String text) {
        this.label = text;
    }

    public void drawCenteredLabel(String text, int i) {
        ClientFontRenderer clientFontRenderer = RemixClient.fonts.comfortaa18;
        float stringWidth = clientFontRenderer.getStringWidth(text);
        float f1 = this.getWidth() / 2.0F - stringWidth / 2.0F + this.left;
        float f2 = this.getHeight() / 2.0F - clientFontRenderer.getHeight() / 2.0F + this.top;
        clientFontRenderer.drawString(text, f1, f2 + i, -1);
    }

    public float getWidth() {
        return this.right - this.left;
    }
}
