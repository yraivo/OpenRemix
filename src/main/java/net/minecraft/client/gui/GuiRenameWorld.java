package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiRenameWorld extends GuiScreen {

    private GuiTextField xO;

    private GuiScreen parentScreen;
    private String saveName;

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.xO.mouseClicked(i, j, k);
    }

    public void updateScreen() {
        this.xO.updateCursorCounter();
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("selectWorld.renameTitle", new Object[0]),
                this.width / 2,
                20,
                16777215);
        this.drawString(
                this.fontRendererObj,
                I18n.format("selectWorld.enterName", new Object[0]),
                this.width / 2 - 100,
                47,
                10526880);
        this.xO.drawTextBox();
        super.drawScreen(i, j, f);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 0) {
                ISaveFormat saveLoader = this.mc.getSaveLoader();
                saveLoader.renameWorld(this.saveName, this.xO.getText().trim());
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 100,
                        this.height / 4 + 96 + 12,
                        I18n.format("selectWorld.renameButton", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 - 100,
                        this.height / 4 + 120 + 12,
                        I18n.format("gui.cancel", new Object[0])));
        ISaveFormat saveLoader = this.mc.getSaveLoader();
        WorldInfo worldInfo = saveLoader.getWorldInfo(this.saveName);
        String text = worldInfo.getWorldName();
        this.xO = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.xO.setFocused(true);
        this.xO.setText(text);
    }


    protected void keyTyped(char c0, int i) throws IOException {
        this.xO.textboxKeyTyped(c0, i);
        ((GuiButton) this.buttonList.get(0)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.xO.getText().trim().length() > 0 ? 1 : 0));
        if (i == 28 || i == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public GuiRenameWorld(GuiScreen guiScreen, String text) {
        this.parentScreen = guiScreen;
        this.saveName = text;
    }
}
