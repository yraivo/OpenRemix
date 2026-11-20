package net.minecraft.client.gui;

public interface GuiListExtendedIGuiListEntry {
    void setSelected(int i, int j, int k);

    void mouseReleased(int i, int j, int k, int l, int i1, int j1);

    void drawEntry(int i, int j, int k, int l, int i1, int j1, int k1, boolean flag);

    boolean mousePressed(int i, int j, int k, int l, int i1, int j1);
}
