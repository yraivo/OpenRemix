package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.settings.GameSettingsOptions;

public class GuiOptionsRowListRow implements GuiListExtendedIGuiListEntry {
    private GuiButton asw;
    private final MinecraftClient ec = MinecraftClient.getMinecraft();
    private GuiButton asx;

    public boolean mousePressed(int k, int i, int j, int l, int i1, int j1) {
        if (this.asx.mousePressed(this.ec, i, j)) {
            if (this.asx instanceof GuiOptionButton) {
                this.ec.gameSettings.setOptionValue(
                        ((GuiOptionButton) this.asx).returnEnumOptions(), 1);
                this.asx.displayString =
                        this.ec.gameSettings.getKeyBinding(GameSettingsOptions.getEnumOptions(this.asx.id));
            }

            return true;
        } else if (this.asw != null && this.asw.mousePressed(this.ec, i, j)) {
            if (this.asw instanceof GuiOptionButton) {
                this.ec.gameSettings.setOptionValue(
                        ((GuiOptionButton) this.asw).returnEnumOptions(), 1);
                this.asw.displayString =
                        this.ec.gameSettings.getKeyBinding(GameSettingsOptions.getEnumOptions(this.asw.id));
            }

            return true;
        } else {
            return false;
        }
    }

    public void mouseReleased(int k, int i, int j, int l, int i1, int j1) {
        if (this.asx != null) {
            this.asx.mouseReleased(i, j);
        }

        if (this.asw != null) {
            this.asw.mouseReleased(i, j);
        }
    }

    public GuiOptionsRowListRow(GuiButton asxValue, GuiButton aswValue) {
        this.asx = asxValue;
        this.asw = aswValue;
    }

    public void drawEntry(
            int l, int i1, int yPosition, int j1, int k1, int j, int k, boolean flag) {
        if (this.asx != null) {
            this.asx.yPosition = yPosition;
            this.asx.drawButton(this.ec, j, k);
        }

        if (this.asw != null) {
            this.asw.yPosition = yPosition;
            this.asw.drawButton(this.ec, j, k);
        }
    }

    public void setSelected(int i, int j, int k) {}
}
