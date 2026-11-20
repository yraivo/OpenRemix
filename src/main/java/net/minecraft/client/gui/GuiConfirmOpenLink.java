package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiConfirmOpenLink extends GuiYesNo {

    private String openLinkWarning;
    private String linkText;

    private boolean showSecurityWarning = true;
    private String copyLinkButtonText;


    public void initGui() {
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 50 - 105,
                        this.height / 6 + 96,
                        100,
                        20,
                        this.confirmButtonText));
        this.buttonList.add(
                new GuiButton(
                        2,
                        this.width / 2 - 50,
                        this.height / 6 + 96,
                        100,
                        20,
                        this.copyLinkButtonText));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 50 + 105,
                        this.height / 6 + 96,
                        100,
                        20,
                        this.cancelButtonText));
    }

    public GuiConfirmOpenLink(GuiYesNoCallback guiYesNoCallback, String text, int i, boolean flag) {
        super(
                guiYesNoCallback,
                I18n.format(flag ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]),
                text,
                i);
        this.confirmButtonText = I18n.format(flag ? "chat.link.open" : "gui.yes", new Object[0]);
        this.cancelButtonText = I18n.format(flag ? "gui.cancel" : "gui.no", new Object[0]);
        this.copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
        this.openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
        this.linkText = text;
    }

    public void disableSecurityWarning() {
        this.showSecurityWarning = false;
    }

    public void copyLinkToClipboard() {
        copyTextToClipboard(this.linkText);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        if (this.showSecurityWarning) {
            this.drawCenteredString(
                    this.fontRendererObj, this.openLinkWarning, this.width / 2, 110, 16764108);
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (guiButton.id == 2) {
                this.copyLinkToClipboard();
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        GuiYesNoCallback guiYesNoCallback;
        byte b0;
        label23:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                guiYesNoCallback = this.parentScreen;
                if (guiButton.id == 0) {
                    b0 = 1;
                    break label23;
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
            }

            b0 = 0;
        }

        guiYesNoCallback.confirmClicked(
                remix.compat.compiler.JvmBooleanSlots.fromInt(b0), this.parentButtonClickedId);
    }
}
