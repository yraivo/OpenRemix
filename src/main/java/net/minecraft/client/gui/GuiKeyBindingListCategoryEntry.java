package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiKeyBindingListCategoryEntry implements GuiListExtendedIGuiListEntry {
    private String labelText;
    private int labelWidth;
    GuiKeyBindingList asr;

    public boolean mousePressed(int i, int j, int k, int l, int i1, int j1) {
        return false;
    }

    public void mouseReleased(int i, int j, int k, int l, int i1, int j1) {}

    public void setSelected(int i, int j, int k) {}

    public GuiKeyBindingListCategoryEntry(GuiKeyBindingList asrValue, String text) {
        this.asr = asrValue;
        this.labelText = I18n.format(text, new Object[0]);
        this.labelWidth =
                GuiKeyBindingList.method_b082c7fed71fca36020d(asrValue)
                        .fontRendererObj
                        .getStringWidth(this.labelText);
    }

    public void drawEntry(int k, int l, int i, int i1, int j, int j1, int k1, boolean flag) {
        GuiKeyBindingList.method_b082c7fed71fca36020d(this.asr)
                .fontRendererObj
                .drawString(
                        this.labelText,
                        GuiKeyBindingList.method_b082c7fed71fca36020d(this.asr).currentScreen.width
                                        / 2
                                - this.labelWidth / 2,
                        i
                                + j
                                - GuiKeyBindingList.method_b082c7fed71fca36020d(this.asr)
                                        .fontRendererObj
                                        .FONT_HEIGHT
                                - 1,
                        16777215);
    }
}
