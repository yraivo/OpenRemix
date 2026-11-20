package remix.ui.widget;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;

public final class PlaceholderGuiTextField extends GuiTextField {
    private static String[] packedTextFragments;
    public int B;
    private ClientFontRenderer clientFontRenderer = RemixClient.fonts.roboto21;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.widget.PlaceholderGuiTextField.packedTextFragments[0] = new String("_");
    }

    // endregion

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    @Override
    public void drawTextBox() {
        boolean legacyFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        if (!this.getVisible()) {
            return;
        }
        this.drawUnderline(legacyFlow);

        int color = this.isEnabled ? this.enabledColor : this.disabledColor;
        int cursor = this.cursorPosition - this.lineScrollOffset;
        int selection = this.selectionEnd - this.lineScrollOffset;
        String visibleText = this.clientFontRenderer.trimStringToWidth(
                this.text.substring(this.lineScrollOffset), this.getWidth());
        boolean cursorVisible = cursor >= 0 && cursor <= visibleText.length();
        boolean drawCaret = this.isFocused && this.cursorCounter / 6 % 2 == 0 && cursorVisible;
        int textX = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
        int textY = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;

        selection = Math.min(selection, visibleText.length());
        int textEndX = textX;
        if (!visibleText.isEmpty()) {
            String beforeCursor = cursorVisible ? visibleText.substring(0, cursor) : visibleText;
            textEndX = (int) this.clientFontRenderer.drawStringWithShadow(beforeCursor, textX, textY, color);
        }

        boolean barCaret = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
        int caretX = textEndX;
        if (!cursorVisible) {
            caretX = cursor > 0 ? textX + this.width : textX;
        }
        if ((cursorVisible || legacyFlow) && barCaret) {
            caretX = --textEndX;
        }
        if (cursorVisible && cursor < visibleText.length()) {
            this.clientFontRenderer.drawStringWithShadow(visibleText.substring(cursor), textEndX, textY, color);
        }

        if (drawCaret) {
            this.drawTextCaret(caretX, textY, color, barCaret, legacyFlow);
        }
        if (selection != cursor) {
            int selectionX = textX + this.clientFontRenderer.getStringWidth(visibleText.substring(0, selection));
            this.drawCursorVertical(caretX, textY - 1, selectionX - 1, textY + 1 + this.fontRendererInstance.FONT_HEIGHT);
        }
    }

    private void drawUnderline(boolean legacyFlow) {
        int left = this.xPosition - 1;
        int right = this.xPosition + this.width + 1;
        int bottom = this.yPosition + this.height + 3;
        if (this.isFocused) {
            drawRect(left, bottom, right, bottom - 1, ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
        }
        if (!this.isFocused || legacyFlow) {
            drawRect(left, bottom, right, bottom - 1, -6250336);
        }
    }

    private void drawTextCaret(int x, int y, int color, boolean barCaret, boolean legacyFlow) {
        if (barCaret) {
            Gui.drawRect(x, y - 1, x + 1, y + 1 + this.clientFontRenderer.getHeight(), -3092272);
        }
        if (!barCaret || legacyFlow) {
            this.clientFontRenderer.drawStringWithShadow(packedTextFragments[0], x, y, color);
        }
    }

    public PlaceholderGuiTextField(
            int i, FontRenderer fontRenderer, int j, int k, int l, int i1) {
        super(i, fontRenderer, j, k, l, i1);
        this.B = 0;
    }
}
