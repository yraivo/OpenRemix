package remix.ui.widget;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.Entity;

import remix.client.RemixClient;
import remix.util.network.NordVpnProxyManager;
import remix.util.render.Render2D3DUtils;

public class CustomGuiTextField extends GuiTextField {
    public String jT;
    public int B;
    public int C;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public void mouseClicked(int i, int j, int l) {
        int k =
                i >= this.xPosition
                                && i < this.xPosition + this.width
                                && j >= this.yPosition
                                && j < this.yPosition + this.height
                        ? 1
                        : 0;
        if (this.canLoseFocus()) {
            this.setFocused(remix.compat.compiler.JvmBooleanSlots.fromInt(k));
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.widget.CustomGuiTextField.packedTextFragments[0] = new String("_");
        remix.ui.widget.CustomGuiTextField.packedTextFragments[1] = new String("_");
    }

    // endregion

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public void drawTextBox() {
        Entity[] entityArray = NordVpnProxyManager.getLegacyProxyFlowState();
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                Render2D3DUtils.drawBorderedRect(
                        this.xPosition,
                        this.yPosition,
                        this.xPosition + this.width,
                        this.yPosition + this.height,
                        0.5,
                        this.B,
                        this.C);
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String text = this.text;
            if (!this.isFocused() && this.getText().length() == 0) {
                text = this.jT;
            }

            String textValue = text;
            int l = j >= 0 && j <= textValue.length() ? 1 : 0;
            int i1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && l != 0 ? 1 : 0;
            int j1 = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int k1 =
                    this.enableBackgroundDrawing
                            ? this.yPosition + (this.height - 8) / 2
                            : this.yPosition;
            int l1 = j1;
            if (k > textValue.length()) {
                k = textValue.length();
            }

            if (textValue.length() > 0) {
                String textValue2 = l != 0 ? textValue.substring(0, j) : textValue;
                l1 =
                        (int)
                                RemixClient.fonts.roboto18.drawStringWithShadow(
                                        textValue2, j1, k1 + 1, i);
            }

            int i2;
            int j2;
            {
                j2 =
                        this.cursorPosition >= this.text.length()
                                        && this.text.length() < this.getMaxStringLength()
                                ? 0
                                : 1;
                i2 = l1;
                if (l == 0) {
                    i2 = j > 0 ? j1 + this.width : j1;
                    if (!(entityArray == null)) {

                        if (j2 != 0) {
                            i2 = l1 - 1;
                            l1--;
                        }
                    }
                } else {

                    if (j2 != 0) {
                        i2 = l1 - 1;
                        l1--;
                    }
                }
            }

            if (textValue.length() > 0 && l != 0 && j < textValue.length()) {
                RemixClient.fonts.roboto18.drawStringWithShadow(
                        textValue.substring(j), l1, k1 + 1, i);
            }

            if (i1 != 0) {
                if (j2 != 0) {
                    Gui.drawRect(
                            i2,
                            k1 - 1,
                            i2 + 1,
                            k1
                                    + 1
                                    + RemixClient.fonts.roboto18.getTextHeightLegacy(
                                            packedTextFragments[0]),
                            -3092272);
                    if (!(entityArray == null)) {

                        RemixClient.fonts.roboto18.drawStringWithShadow(
                                packedTextFragments[1], i2, k1 + 1, i);
                    }
                } else {

                    RemixClient.fonts.roboto18.drawStringWithShadow(
                            packedTextFragments[1], i2, k1 + 1, i);
                }
            }

            if (k != j) {
                float f = j1 + RemixClient.fonts.roboto18.getStringWidth(textValue.substring(0, k));
                this.drawCursorVertical(
                        i2,
                        k1 - 1,
                        (int) f - 1,
                        k1 + 1 + (int) RemixClient.fonts.roboto18.getTextHeightLegacy(textValue));
            }
        }
    }

    public CustomGuiTextField(String text, int i, int j, int k, int l, int i1, int j1) {
        super(-1, null, i, j, k, l);
        this.jT = text;
        this.B = i1;
        this.C = j1;
        this.setMaxStringLength(255);
    }
}
