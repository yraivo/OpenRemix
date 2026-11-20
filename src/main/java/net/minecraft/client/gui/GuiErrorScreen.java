package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiErrorScreen extends GuiScreen {
    private String jR;
    private static String kg;
    private String EH;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void drawScreen(int i, int j, float f) {
        this.drawGradientRect(0.0, 0.0, this.width, this.height, -12574688, -11530224);
        this.drawCenteredString(this.fontRendererObj, this.EH, this.width / 2, 90, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.jR, this.width / 2, 110, 16777215);
        super.drawScreen(i, j, f);
    }

    protected void keyTyped(char c0, int i) throws IOException {}

    static {
        kg = "gui.cancel";
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        this.mc.displayGuiScreen((GuiScreen) null);
    }

    public GuiErrorScreen(String text, String textValue) {
        this.EH = text;
        this.jR = textValue;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 140, I18n.format(kg)));
    }
}
