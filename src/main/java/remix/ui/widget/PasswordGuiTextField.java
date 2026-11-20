package remix.ui.widget;

import remix.util.network.NordVpnProxyManager;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import remix.client.RemixClient;

public class PasswordGuiTextField extends CustomGuiTextField {
    private static String[] packedTextFragments;

    static {
        packedTextFragments = new String[4];
        unpackLiteralFragments();
    }

    public PasswordGuiTextField(String text, int i, int j, int k, int l, int i1, int j1) {
        super(text, i, j, k, l, i1, j1);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.widget.PasswordGuiTextField.packedTextFragments[0] = new String("*");
        remix.ui.widget.PasswordGuiTextField.packedTextFragments[1] = new String("A");
        remix.ui.widget.PasswordGuiTextField.packedTextFragments[2] = new String("_");
        remix.ui.widget.PasswordGuiTextField.packedTextFragments[3] = new String("A");
    }

    // endregion

    // region String decoding support

    // endregion

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
            StringBuilder stringbuilder = new StringBuilder();
            int l = 0;

            while (l < text.length()) {
                stringbuilder.append(packedTextFragments[0]);
                l++;
                if (entityArray != null) {
                    break;
                }
            }

            text = stringbuilder.toString();
            if (!this.isFocused() && text.isEmpty()) {
                text = this.jT;
            }

            l = j >= 0 && j <= text.length() ? 1 : 0;
            int i1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && l != 0 ? 1 : 0;
            int j1 = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int k1 =
                    this.enableBackgroundDrawing
                            ? this.yPosition + (this.height - 8) / 2
                            : this.yPosition;
            int l1 = j1;
            if (k > text.length()) {
                k = text.length();
            }

            if (text.length() > 0) {
                String textValue = l != 0 ? text.substring(0, j) : text;
                l1 = (int) RemixClient.fonts.roboto18.drawStringWithShadow(textValue, j1, k1 + 1, i);
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

            if (text.length() > 0 && l != 0 && j < text.length()) {
                RemixClient.fonts.roboto18.drawStringWithShadow(text.substring(j), l1, k1 + 1, i);
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
                                            packedTextFragments[1]),
                            -3092272);
                    if (!(entityArray == null)) {

                        RemixClient.fonts.roboto18.drawStringWithShadow(packedTextFragments[2], i2, k1 + 1, i);
                    }
                } else {

                    RemixClient.fonts.roboto18.drawStringWithShadow(packedTextFragments[2], i2, k1 + 1, i);
                }
            }

            if (k != j) {
                float f = j1 + RemixClient.fonts.roboto18.getStringWidth(text.substring(0, k));
                this.drawCursorVertical(
                        i2,
                        k1 - 1,
                        (int) f - 1,
                        k1
                                + 1
                                + (int)
                                        RemixClient.fonts.roboto18.getTextHeightLegacy(
                                                packedTextFragments[3]));
            }
        }
    }
}
