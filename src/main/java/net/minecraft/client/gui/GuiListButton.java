package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton {
    private static String[] packedTextFragments;
    private String localizationStr;

    private boolean lL;
    private GuiPageButtonListGuiResponder guiResponder;

    public void method_5a0449839d28b4a71843(boolean flag) {
        this.lL = flag;
        this.displayString = this.buildDisplayString();
        this.guiResponder.func_175321_a(this.id, flag);
    }

    public boolean mousePressed(MinecraftClient minecraftClient, int i, int j) {
        if (super.mousePressed(minecraftClient, i, j)) {
            this.lL = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.lL ? 1 : 0));
            this.displayString = this.buildDisplayString();
            this.guiResponder.func_175321_a(this.id, this.lL);
            return true;
        } else {
            return false;
        }
    }

    static {
        net.minecraft.client.gui.GuiListButton.packedTextFragments = new String[2];
        net.minecraft.client.gui.GuiListButton.packedTextFragments[0] = new String("");
    }

    public GuiListButton(
            GuiPageButtonListGuiResponder aqYValue,
            int i,
            int j,
            int k,
            String text,
            boolean flag) {
        super(i, j, k, 150, 20, packedTextFragments[0]);
        this.localizationStr = text;
        this.lL = flag;
        this.displayString = this.buildDisplayString();
        this.guiResponder = aqYValue;
    }

    private String buildDisplayString() {
        return I18n.format(this.localizationStr, new Object[0])
                + ": "
                + (this.lL
                        ? I18n.format("gui.yes", new Object[0])
                        : I18n.format("gui.no", new Object[0]));
    }
}
