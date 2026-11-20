package net.minecraft.client.gui;

import net.minecraft.client.settings.GameSettingsOptions;

public class GuiOptionButton extends GuiButton {
    private GameSettingsOptions enumOptions;

    public GuiOptionButton(int i, int j, int k, int l, int i1, String text) {
        super(i, j, k, l, i1, text);
        this.enumOptions = null;
    }

    public GameSettingsOptions returnEnumOptions() {
        return this.enumOptions;
    }

    public GuiOptionButton(int i, int j, int k, String text) {
        this(i, j, k, (GameSettingsOptions) null, text);
    }

    public GuiOptionButton(int i, int j, int k, GameSettingsOptions arbValue, String text) {
        super(i, j, k, 150, 20, text);
        this.enumOptions = arbValue;
    }
}
