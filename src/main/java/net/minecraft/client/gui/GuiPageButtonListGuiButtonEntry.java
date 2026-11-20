package net.minecraft.client.gui;

public class GuiPageButtonListGuiButtonEntry extends GuiPageButtonListGuiListEntry {
    private boolean d;

    public GuiPageButtonListGuiButtonEntry(int i, String text, boolean flag, boolean flag1) {
        super(i, text, flag);
        this.d = flag1;
    }

    public boolean aY() {
        return this.d;
    }
}
