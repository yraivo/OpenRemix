package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiScreenServerList extends GuiScreen {
    private GuiScreen yQ;
    private ServerData aoh;
    private GuiTextField xJ;

    private static String[] packedTextFragments;

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.yQ.confirmClicked(false, 0);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 0) {
                this.aoh.serverIP = this.xJ.getText();
                this.yQ.confirmClicked(true, 0);
            }
        }
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("selectServer.direct", new Object[0]),
                this.width / 2,
                20,
                16777215);
        this.drawString(
                this.fontRendererObj,
                I18n.format("addServer.enterIp", new Object[0]),
                this.width / 2 - 100,
                100,
                10526880);
        this.xJ.drawTextBox();
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 100,
                        this.height / 4 + 96 + 12,
                        I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 100,
                        this.height / 4 + 120 + 12,
                        I18n.format("gui.cancel", new Object[0])));
        this.xJ = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
        this.xJ.setMaxStringLength(128);
        this.xJ.setFocused(true);
        this.xJ.setText(this.mc.gameSettings.lastServer);
        ((GuiButton) this.buttonList.get(0)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.xJ.getText().length() > 0
                                        && this.xJ.getText().split(packedTextFragments[0]).length
                                                > 0
                                ? 1
                                : 0));
    }

    static {
        net.minecraft.client.gui.GuiScreenServerList.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiScreenServerList.packedTextFragments[0] = new String(":");
        net.minecraft.client.gui.GuiScreenServerList.packedTextFragments[1] = new String(":");
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.gameSettings.lastServer = this.xJ.getText();
        this.mc.gameSettings.saveOptions();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.xJ.mouseClicked(i, j, k);
    }

    protected void keyTyped(char c0, int i) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.xJ.textboxKeyTyped(c0, i)) {
            ((GuiButton) this.buttonList.get(0)).enabled =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (this.xJ.getText().length() > 0
                                            && this.xJ
                                                            .getText()
                                                            .split(packedTextFragments[1])
                                                            .length
                                                    > 0
                                    ? 1
                                    : 0));
            if (astring == null) {
                return;
            }
        }

        if (i == 28 || i == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }

    public void updateScreen() {
        this.xJ.updateCursorCounter();
    }

    public GuiScreenServerList(GuiScreen guiScreen, ServerData aohValue) {
        this.yQ = guiScreen;
        this.aoh = aohValue;
    }
}
