package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerDataServerResourceMode;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiScreenAddServer extends GuiScreen {

    private GuiButton serverResourcePacks;
    private ServerData serverData;
    private GuiTextField serverIPField;
    private GuiTextField serverNameField;
    private GuiScreen parentScreen;
    private static String[] packedTextFragments;

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 100,
                        this.height / 4 + 96 + 18,
                        I18n.format("addServer.add", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 100,
                        this.height / 4 + 120 + 18,
                        I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(
                this.serverResourcePacks =
                        new GuiButton(
                                2,
                                this.width / 2 - 100,
                                this.height / 4 + 72,
                                I18n.format("addServer.resourcePack", new Object[0])
                                        + ": "
                                        + this.serverData
                                                .getResourceMode()
                                                .getMotd()
                                                .getFormattedText()));
        this.serverNameField =
                new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        this.serverNameField.setFocused(true);
        this.serverNameField.setText(this.serverData.serverName);
        this.serverIPField =
                new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
        this.serverIPField.setMaxStringLength(128);
        this.serverIPField.setText(this.serverData.serverIP);
        ((GuiButton) this.buttonList.get(0)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.serverIPField.getText().length() > 0
                                        && this.serverIPField
                                                        .getText()
                                                        .split(packedTextFragments[0])
                                                        .length
                                                > 0
                                        && this.serverNameField.getText().length() > 0
                                ? 1
                                : 0));
    }

    public GuiScreenAddServer(GuiScreen guiScreen, ServerData aohValue) {
        this.parentScreen = guiScreen;
        this.serverData = aohValue;
    }

    protected void keyTyped(char c0, int i) throws IOException {
        this.serverNameField.textboxKeyTyped(c0, i);
        this.serverIPField.textboxKeyTyped(c0, i);
        if (i == 15) {
            this.serverNameField.setFocused(!this.serverNameField.isFocused());
            this.serverIPField.setFocused(!this.serverIPField.isFocused());
        }

        if (i == 28 || i == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }

        ((GuiButton) this.buttonList.get(0)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.serverIPField.getText().length() > 0
                                        && this.serverIPField
                                                        .getText()
                                                        .split(packedTextFragments[1])
                                                        .length
                                                > 0
                                        && this.serverNameField.getText().length() > 0
                                ? 1
                                : 0));
    }

    static {
        net.minecraft.client.gui.GuiScreenAddServer.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiScreenAddServer.packedTextFragments[0] = new String(":");
        net.minecraft.client.gui.GuiScreenAddServer.packedTextFragments[1] = new String(":");
    }

    public void updateScreen() {
        this.serverNameField.updateCursorCounter();
        this.serverIPField.updateCursorCounter();
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                this.serverData.setResourceMode(
                        ServerDataServerResourceMode.values()[
                                (this.serverData.getResourceMode().ordinal() + 1)
                                        % ServerDataServerResourceMode.values().length]);
                this.serverResourcePacks.displayString =
                        I18n.format("addServer.resourcePack", new Object[0])
                                + ": "
                                + this.serverData.getResourceMode().getMotd().getFormattedText();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 1) {
                this.parentScreen.confirmClicked(false, 0);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 0) {
                this.serverData.serverName = this.serverNameField.getText();
                this.serverData.serverIP = this.serverIPField.getText();
                this.parentScreen.confirmClicked(true, 0);
            }
        }
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.serverIPField.mouseClicked(i, j, k);
        this.serverNameField.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("addServer.title", new Object[0]),
                this.width / 2,
                17,
                16777215);
        this.drawString(
                this.fontRendererObj,
                I18n.format("addServer.enterName", new Object[0]),
                this.width / 2 - 100,
                53,
                10526880);
        this.drawString(
                this.fontRendererObj,
                I18n.format("addServer.enterIp", new Object[0]),
                this.width / 2 - 100,
                94,
                10526880);
        this.serverNameField.drawTextBox();
        this.serverIPField.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
