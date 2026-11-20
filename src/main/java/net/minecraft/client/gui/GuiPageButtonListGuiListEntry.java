package net.minecraft.client.gui;

public class GuiPageButtonListGuiListEntry {
    private String b;
    private int a;
    private boolean enabled;

    public boolean isEnabled() {
        return this.enabled;
    }

    public String method_c60693f457aa70be7160() {
        return this.b;
    }

    public GuiPageButtonListGuiListEntry(int i, String text, boolean flag) {
        this.a = i;
        this.b = text;
        this.enabled = flag;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.a;
    }
}
