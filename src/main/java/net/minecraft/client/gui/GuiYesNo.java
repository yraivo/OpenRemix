package net.minecraft.client.gui;

import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.List;

public class GuiYesNo extends GuiScreen {
    protected String messageLine1;
    private int ticksUntilEnable;

    protected GuiYesNoCallback parentScreen;

    protected int parentButtonClickedId;
    protected String cancelButtonText;
    private String messageLine2;
    private final List rp = Lists.newArrayList();
    protected String confirmButtonText;

    public void initGui() {
        this.buttonList.add(
                new GuiOptionButton(
                        0, this.width / 2 - 155, this.height / 6 + 96, this.confirmButtonText));
        this.buttonList.add(
                new GuiOptionButton(
                        1,
                        this.width / 2 - 155 + 160,
                        this.height / 6 + 96,
                        this.cancelButtonText));
        this.rp.clear();
        this.rp.addAll(
                this.fontRendererObj.listFormattedStringToWidth(
                        this.messageLine2, this.width - 50));
    }

    public void setButtonDelay(int ticksUntilEnableValue) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.ticksUntilEnable = ticksUntilEnableValue;
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_1479 = (this.buttonList).iterator();
                (__recoveryIterator171_1479).hasNext(); ) {
            GuiButton guiButton = (GuiButton) (__recoveryIterator171_1479).next();
            guiButton.enabled = false;
            if (astring != null) {
                break;
            }
        }
    }

    private static IOException method_02836a3a008661c0ef77(IOException ioexception) {
        return ioexception;
    }

    public GuiYesNo(
            GuiYesNoCallback asXValue,
            String text,
            String textValue,
            String textValue2,
            String textValue3,
            int parentButtonClickedIdValue) {
        this.parentScreen = asXValue;
        this.messageLine1 = text;
        this.messageLine2 = textValue;
        this.confirmButtonText = textValue2;
        this.cancelButtonText = textValue3;
        this.parentButtonClickedId = parentButtonClickedIdValue;
    }


    protected void actionPerformed(GuiButton guiButton) throws IOException {
        GuiYesNoCallback guiYesNoCallback;
        byte b0;
        label16:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                guiYesNoCallback = this.parentScreen;
                if (guiButton.id == 0) {
                    b0 = 1;
                    break label16;
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_02836a3a008661c0ef77(ioexception));
            }

            b0 = 0;
        }

        guiYesNoCallback.confirmClicked(
                remix.compat.compiler.JvmBooleanSlots.fromInt(b0), this.parentButtonClickedId);
    }

    public void updateScreen() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        super.updateScreen();
        String[] astring = astring1;
        if (--this.ticksUntilEnable == 0) {
            for (java.util.Iterator<?> __recoveryIterator171_26141 = (this.buttonList).iterator();
                    (__recoveryIterator171_26141).hasNext(); ) {
                GuiButton guiButton = (GuiButton) (__recoveryIterator171_26141).next();
                guiButton.enabled = true;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public GuiYesNo(
            GuiYesNoCallback asXValue,
            String text,
            String textValue,
            int parentButtonClickedIdValue) {
        this.parentScreen = asXValue;
        this.messageLine1 = text;
        this.messageLine2 = textValue;
        this.parentButtonClickedId = parentButtonClickedIdValue;
        this.confirmButtonText = I18n.format("gui.yes", new Object[0]);
        this.cancelButtonText = I18n.format("gui.no", new Object[0]);
    }

    public void drawScreen(int i, int j, float f) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.drawDefaultBackground();
        String[] astring = astring1;
        this.drawCenteredString(
                this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
        int k = 90;

        for (java.util.Iterator<?> __recoveryIterator171_27666 = (this.rp).iterator();
                (__recoveryIterator171_27666).hasNext(); ) {
            String text = (String) (__recoveryIterator171_27666).next();
            this.drawCenteredString(this.fontRendererObj, text, this.width / 2, k, 16777215);
            k += this.fontRendererObj.FONT_HEIGHT;
            if (astring != null) {
                break;
            }
        }

        super.drawScreen(i, j, f);
    }
}
