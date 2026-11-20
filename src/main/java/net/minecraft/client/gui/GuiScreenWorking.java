package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
    private String jR;
    private static String[] packedTextFragments;
    private int progress;
    private boolean doneWorking;
    private static String jT;
    private String EH;

    public GuiScreenWorking() {
        this.EH = packedTextFragments[0];
        this.jR = packedTextFragments[1];
    }

    public void resetProgressAndMessage(String text) {
        this.EH = text;
        this.displayLoadingString(jT);
    }

    public void displaySavingString(String text) {
        this.resetProgressAndMessage(text);
    }

    public void displayLoadingString(String text) {
        this.jR = text;
        this.setLoadingProgress(0);
    }

    // region String decoding support

    // endregion

    public void setDoneWorking() {
        this.doneWorking = true;
    }

    public void setLoadingProgress(int i) {
        this.progress = i;
    }

    static {
        net.minecraft.client.gui.GuiScreenWorking.packedTextFragments = new String[5];
        net.minecraft.client.gui.GuiScreenWorking.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiScreenWorking.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiScreenWorking.packedTextFragments[2] = new String(" ");
        net.minecraft.client.gui.GuiScreenWorking.packedTextFragments[3] = new String("%");
        net.minecraft.client.gui.GuiScreenWorking.packedTextFragments[4] =
                new String("JZI6[ZZ3\u001b\u0015");
        jT = "Working...";
    }

    // region String decoding support

    // endregion

    public void drawScreen(int i, int j, float f) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.doneWorking) {
            this.mc.displayGuiScreen((GuiScreen) null);
            if (astring == null) {
                return;
            }
        }

        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.EH, this.width / 2, 70, 16777215);
        this.drawCenteredString(
                this.fontRendererObj,
                this.jR + packedTextFragments[2] + this.progress + packedTextFragments[3],
                this.width / 2,
                90,
                16777215);
        super.drawScreen(i, j, f);
    }

    // region String decoding support

    // endregion
}
