package net.minecraft.client.gui;

import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.base.Predicate;

public class GuiTextField extends Gui {
    private boolean visible;
    public int cursorCounter;
    public boolean isFocused;
    public FontRenderer fontRendererInstance;
    public boolean isEnabled;
    public int yPosition;
    private boolean canLoseFocus;
    public int lineScrollOffset;
    private GuiPageButtonListGuiResponder field_175210_x;
    public String text;
    public int width;
    private Predicate field_175209_y;
    public int selectionEnd;
    protected int disabledColor;
    private int maxStringLength;
    public int cursorPosition;
    protected int enabledColor;
    public boolean enableBackgroundDrawing;
    private int id;
    public int xPosition;
    private static String[] packedTextFragments;
    public int height;

    public int getNthWordFromCursor(int p171_0) {
        return this.getNthWordFromPos(p171_0, this.getCursorPosition());
    }

    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    public void updateCursorCounter() {
        this.cursorCounter++;
    }

    public void setDisabledTextColour(int disabledColorValue) {
        this.disabledColor = disabledColorValue;
    }

    public void mouseClicked(int p171_0, int p171_1, int p171_2) {
        boolean flag =
                (p171_0 >= this.xPosition
                        && p171_0 < this.xPosition + this.width
                        && p171_1 >= this.yPosition
                        && p171_1 < this.yPosition + this.height);
        if (this.canLoseFocus) {
            this.setFocused(flag);
        }

        if (this.isFocused && (flag) && p171_2 == 0) {
            int l = p171_0 - this.xPosition;
            if (this.enableBackgroundDrawing) {
                l -= 4;
            }

            java.lang.String text =
                    this.fontRendererInstance.trimStringToWidth(
                            this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(
                    this.fontRendererInstance.trimStringToWidth(text, l).length()
                            + this.lineScrollOffset);
        }
    }

    static {
        packedTextFragments = new String[7];
        unpackLiteralFragments();
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public void drawCursorVertical(int p171_0, int p171_1, int p171_2, int p171_3) {
        if (p171_0 < p171_2) {
            int i1 = p171_0;
            p171_0 = p171_2;
            p171_2 = i1;
        }

        if (p171_1 < p171_3) {
            int j1 = p171_1;
            p171_1 = p171_3;
            p171_3 = j1;
        }

        if (p171_2 > this.xPosition + this.width) {
            p171_2 = this.xPosition + this.width;
        }

        if (p171_0 > this.xPosition + this.width) {
            p171_0 = this.xPosition + this.width;
        }

        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        net.minecraft.client.renderer.GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        net.minecraft.client.renderer.GlStateManager.enableColorLogic();
        net.minecraft.client.renderer.GlStateManager.colorLogicOp(5387);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(p171_0, p171_3, 0.0);
        worldRenderer.addVertex(p171_2, p171_3, 0.0);
        worldRenderer.addVertex(p171_2, p171_1, 0.0);
        worldRenderer.addVertex(p171_0, p171_1, 0.0);
        instance.draw();
        net.minecraft.client.renderer.GlStateManager.disableColorLogic();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
    }

    public void setCursorPosition(int cursorPositionValue) {
        this.cursorPosition = cursorPositionValue;
        int j = this.text.length();
        this.cursorPosition = MathHelper.clamp(this.cursorPosition, 0, j);
        this.setSelectionPos(this.cursorPosition);
    }

    public String getText() {
        return this.text;
    }

    public void setEnabled(boolean flag) {
        this.isEnabled = flag;
    }

    public void setText(String text) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.field_175209_y.apply(text)) {
            {
                if (text.length() > this.maxStringLength) {
                    this.text = text.substring(0, this.maxStringLength);
                    if (!(astring == null)) {

                        this.text = text;
                    }
                } else {

                    this.text = text;
                }
            }

            this.setCursorPositionEnd();
        }
    }

    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    public void a(int p171_0) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText(net.minecraft.client.gui.GuiTextField.packedTextFragments[2]);
                if (astring == null) {
                    return;
                }
            }

            this.deleteFromCursor(this.getNthWordFromCursor(p171_0) - this.cursorPosition);
        }
    }

    public void moveCursorBy(int p171_0) {
        this.setCursorPosition(this.selectionEnd + p171_0);
    }

    public void func_175207_a(GuiPageButtonListGuiResponder ashValue) {
        this.field_175210_x = ashValue;
    }

    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    public void setMaxStringLength(int maxStringLengthValue) {
        this.maxStringLength = maxStringLengthValue;
        if (this.text.length() > maxStringLengthValue) {
            this.text = this.text.substring(0, maxStringLengthValue);
        }
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void func_175205_a(Predicate predicate) {
        this.field_175209_y = predicate;
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public boolean canLoseFocus() {
        return this.canLoseFocus;
    }

    // region String decoding support

    // endregion

    public void setSelectionPos(int selectionEndValue) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int j = this.text.length();
        String[] astring = astring1;
        if (selectionEndValue > j) {
            selectionEndValue = j;
        }

        if (selectionEndValue < 0) {
            selectionEndValue = 0;
        }

        this.selectionEnd = selectionEndValue;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > j) {
                this.lineScrollOffset = j;
            }

            int k = this.getWidth();
            String text =
                    this.fontRendererInstance.trimStringToWidth(
                            this.text.substring(this.lineScrollOffset), k);
            int l = text.length() + this.lineScrollOffset;
            if (selectionEndValue == this.lineScrollOffset) {
                this.lineScrollOffset =
                        this.lineScrollOffset
                                - this.fontRendererInstance
                                        .trimStringToWidth(this.text, k, true)
                                        .length();
            }

            {
                if (selectionEndValue > l) {
                    this.lineScrollOffset += selectionEndValue - l;
                    if (!(astring == null)) {

                        if (selectionEndValue <= this.lineScrollOffset) {
                            this.lineScrollOffset =
                                    this.lineScrollOffset - (this.lineScrollOffset - selectionEndValue);
                        }
                    }
                } else {

                    if (selectionEndValue <= this.lineScrollOffset) {
                        this.lineScrollOffset =
                                this.lineScrollOffset - (this.lineScrollOffset - selectionEndValue);
                    }
                }
            }

            this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, j);
        }
    }

    public int getCursorPosition() {
        return this.cursorPosition;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiTextField.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiTextField.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiTextField.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiTextField.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.GuiTextField.packedTextFragments[4] = new String("");
        net.minecraft.client.gui.GuiTextField.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiTextField.packedTextFragments[6] = new String("_");
    }

    // endregion

    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    public void deleteFromCursor(int p171_0) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText(net.minecraft.client.gui.GuiTextField.packedTextFragments[3]);
                if (astring == null) {
                    return;
                }
            }

            boolean flag = (p171_0 < 0);
            int j = (flag) ? this.cursorPosition + p171_0 : this.cursorPosition;
            int k = (flag) ? this.cursorPosition : this.cursorPosition + p171_0;
            java.lang.String text = net.minecraft.client.gui.GuiTextField.packedTextFragments[4];
            if (j >= 0) {
                text = this.text.substring(0, j);
            }

            if (k < this.text.length()) {
                text = text + this.text.substring(k);
            }

            this.text = text;
            if ((flag)) {
                this.moveCursorBy(p171_0);
            }

            if (this.field_175210_x != null) {
                this.field_175210_x.func_175319_a(this.id, this.text);
            }
        }
    }

    public void drawTextBox() {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                net.minecraft.client.gui.Gui.drawRect(
                        this.xPosition - 1,
                        this.yPosition - 1,
                        this.xPosition + this.width + 1,
                        this.yPosition + this.height + 1,
                        -6250336);
                net.minecraft.client.gui.Gui.drawRect(
                        this.xPosition,
                        this.yPosition,
                        this.xPosition + this.width,
                        this.yPosition + this.height,
                        -16777216);
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            java.lang.String text =
                    this.fontRendererInstance.trimStringToWidth(
                            this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = (j >= 0 && j <= text.length());
            boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && (flag));
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 =
                    this.enableBackgroundDrawing
                            ? this.yPosition + (this.height - 8) / 2
                            : this.yPosition;
            int j1 = l;
            if (k > text.length()) {
                k = text.length();
            }

            if (text.length() > 0) {
                java.lang.String textValue = (flag) ? text.substring(0, j) : text;
                j1 = this.fontRendererInstance.drawStringWithShadow(textValue, l, i1, i);
            }

            int k1;
            int flag2;
            {
                flag2 =
                        (this.cursorPosition >= this.text.length()
                                        && this.text.length() < this.getMaxStringLength()
                                ? 0
                                : 1);
                k1 = j1;
                if (!(flag)) {
                    k1 = j > 0 ? l + this.width : l;
                    if (!(astring == null)) {

                        if (flag2 != 0) {
                            k1 = j1 - 1;
                            j1--;
                        }
                    }
                } else {

                    if (flag2 != 0) {
                        k1 = j1 - 1;
                        j1--;
                    }
                }
            }

            if (text.length() > 0 && (flag) && j < text.length()) {
                j1 = this.fontRendererInstance.drawStringWithShadow(text.substring(j), j1, i1, i);
            }

            if ((flag1)) {
                if (flag2 != 0) {
                    net.minecraft.client.gui.Gui.drawRect(
                            k1,
                            i1 - 1,
                            k1 + 1,
                            i1 + 1 + this.fontRendererInstance.FONT_HEIGHT,
                            -3092272);
                    if (!(astring == null)) {

                        this.fontRendererInstance.drawStringWithShadow(
                                net.minecraft.client.gui.GuiTextField.packedTextFragments[6], k1, i1, i);
                    }
                } else {

                    this.fontRendererInstance.drawStringWithShadow(
                            net.minecraft.client.gui.GuiTextField.packedTextFragments[6], k1, i1, i);
                }
            }

            if (k != j) {
                int l1 = l + this.fontRendererInstance.getStringWidth(text.substring(0, k));
                this.drawCursorVertical(
                        k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }

    public void setCanLoseFocus(boolean canLoseFocusValue) {
        this.canLoseFocus = canLoseFocusValue;
    }

    public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingValue) {
        this.enableBackgroundDrawing = enableBackgroundDrawingValue;
    }

    public void setVisible(boolean visibleValue) {
        this.visible = visibleValue;
    }

    // region String decoding support

    // endregion

    public GuiTextField(
            int p171_0,
            FontRenderer apbValue,
            int xPositionValue,
            int yPositionValue,
            int widthValue,
            int heightValue) {
        this.text = net.minecraft.client.gui.GuiTextField.packedTextFragments[0];
        this.maxStringLength = 32;
        this.enableBackgroundDrawing = true;
        this.canLoseFocus = true;
        this.isEnabled = true;
        this.enabledColor = 14737632;
        this.disabledColor = 7368816;
        this.visible = true;
        this.field_175209_y = com.google.common.base.Predicates.alwaysTrue();
        this.id = p171_0;
        this.fontRendererInstance = apbValue;
        this.xPosition = xPositionValue;
        this.yPosition = yPositionValue;
        this.width = widthValue;
        this.height = heightValue;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public boolean textboxKeyTyped(char c0, int i) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (!this.isFocused) {
            return false;
        }

        if (GuiScreen.isKeyComboCtrlA(i)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }

        if (GuiScreen.isKeyComboCtrlC(i)) {
            GuiScreen.copyTextToClipboard(this.getSelectedText());
            return true;
        }

        if (GuiScreen.isKeyComboCtrlV(i)) {
            if (this.isEnabled) {
                this.writeText(GuiScreen.getClipboardString());
            }

            return true;
        } else if (GuiScreen.isKeyComboCtrlX(i)) {
            GuiScreen.copyTextToClipboard(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText(packedTextFragments[5]);
            }

            return true;
        } else {
            switch (i) {
                case 14:
                    if (GuiScreen.isCtrlKeyDown()) {
                        if (!this.isEnabled) {
                            return true;
                        }

                        this.a(-1);
                        if (astring == null) {
                            return true;
                        }
                    }

                    if (this.isEnabled) {
                        this.deleteFromCursor(-1);
                    }

                    return true;
                case 199:
                    if (GuiScreen.isShiftKeyDown()) {
                        this.setSelectionPos(0);
                        if (astring == null) {
                            return true;
                        }
                    }

                    this.setCursorPositionZero();
                    return true;
                case 203:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.setSelectionPos(
                                    this.getNthWordFromPos(-1, this.getSelectionEnd()));
                            if (astring == null) {
                                return true;
                            }
                        }

                        this.setSelectionPos(this.getSelectionEnd() - 1);
                        if (astring == null) {
                            return true;
                        }
                    }

                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(-1));
                        if (astring == null) {
                            return true;
                        }
                    }

                    this.moveCursorBy(-1);
                    return true;
                case 205:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                            if (astring == null) {
                                return true;
                            }
                        }

                        this.setSelectionPos(this.getSelectionEnd() + 1);
                        if (astring == null) {
                            return true;
                        }
                    }

                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                        if (astring == null) {
                            return true;
                        }
                    }

                    this.moveCursorBy(1);
                    return true;
                case 207:
                    if (GuiScreen.isShiftKeyDown()) {
                        this.setSelectionPos(this.text.length());
                        if (astring == null) {
                            return true;
                        }
                    }

                    this.setCursorPositionEnd();
                    return true;
                case 211:
                    if (GuiScreen.isCtrlKeyDown()) {
                        if (!this.isEnabled) {
                            return true;
                        }

                        this.a(1);
                        if (astring == null) {
                            return true;
                        }
                    }

                    if (this.isEnabled) {
                        this.deleteFromCursor(1);
                    }

                    return true;
                default:
                    if (ChatAllowedCharacters.isAllowedCharacter(c0)) {
                        if (this.isEnabled) {
                            this.writeText(Character.toString(c0));
                        }

                        return true;
                    } else {
                        return false;
                    }
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public void setFocused(boolean isFocusedValue) {
        if (isFocusedValue && !this.isFocused) {
            this.cursorCounter = 0;
        }

        this.isFocused = isFocusedValue;
    }

    public String getSelectedText() {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }

    public int getNthWordFromPos(int p171_0, int p171_1) {
        return this.getWordPosition(p171_0, p171_1, true);
    }

    public void writeText(String text) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        String textValue = packedTextFragments[1];
        String[] astring = astring1;
        String textValue2 = ChatAllowedCharacters.filterAllowedCharacters(text);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);

        if (this.text.length() > 0) {
            textValue = textValue + this.text.substring(0, i);
        }

        int l;
        {
            if (k < textValue2.length()) {
                textValue = textValue + textValue2.substring(0, k);
                l = k;
                if (!(astring == null)) {

                    textValue = textValue + textValue2;
                    l = textValue2.length();
                }
            } else {

                textValue = textValue + textValue2;
                l = textValue2.length();
            }
        }

        if (this.text.length() > 0 && j < this.text.length()) {
            textValue = textValue + this.text.substring(j);
        }

        if (this.field_175209_y.apply(textValue)) {
            this.text = textValue;
            this.moveCursorBy(i - this.selectionEnd + l);
            if (this.field_175210_x != null) {
                this.field_175210_x.func_175319_a(this.id, this.text);
            }
        }
    }

    public int getWordPosition(int p171_0, int p171_1, boolean p171_2) {
        java.lang.String[] astring1 = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        int k = p171_1;
        java.lang.String[] astring = astring1;
        boolean flag1 = (p171_0 < 0);
        int l = java.lang.Math.abs(p171_0);
        int i1 = 0;

        while (i1 < l) {
            label72:
            {
                if ((flag1)) {
                    label58:
                    while (true) {
                        if (p171_2 && k > 0 && this.text.charAt(k - 1) == ' ') {
                            k--;
                            if (astring == null) {
                                continue;
                            }
                        }

                        while (true) {
                            if (k <= 0 || this.text.charAt(k - 1) == ' ') {
                                break label72;
                            }

                            k--;
                            if (astring != null) {
                                break label58;
                            }
                        }
                    }
                }

                int j1 = this.text.length();
                k = this.text.indexOf(32, k);
                if (k == -1) {
                    k = j1;
                    if (astring == null) {
                        break label72;
                    }
                }

                while (p171_2 && k < j1 && this.text.charAt(k) == ' ') {
                    k++;
                    if (astring != null) {
                        break;
                    }
                }
            }

            i1++;
            if (astring != null) {
                break;
            }
        }

        return k;
    }

    public void setTextColor(int enabledColorValue) {
        this.enabledColor = enabledColorValue;
    }
}
