package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;

public abstract class GuiListExtended extends GuiSlot {
    public boolean releaseMouseOnEntries(int p171_0, int p171_1, int p171_2) {
        java.lang.String[] astring1 = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        int l = 0;
        java.lang.String[] astring = astring1;

        while (l < this.getSize()) {
            int i1 = this.left + this.width / 2 - this.cM() / 2 + 2;
            int j1 =
                    this.top
                            + 4
                            - this.getAmountScrolled()
                            + l * this.slotHeight
                            + this.headerPadding;
            int k1 = p171_0 - i1;
            int l1 = p171_1 - j1;
            this.getListEntry(l).mouseReleased(l, p171_0, p171_1, p171_2, k1, l1);
            l++;
            if (astring != null) {
                break;
            }
        }

        this.setEnabled(true);
        return false;
    }

    protected void method_a8908bd0f12e21ee1ad3(int p171_0, int p171_1, int p171_2) {
        this.getListEntry(p171_0).setSelected(p171_0, p171_1, p171_2);
    }

    protected void drawBackground() {}

    public GuiListExtended(
            MinecraftClient minecraftClient,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5) {
        super(minecraftClient, p171_1, p171_2, p171_3, p171_4, p171_5);
    }

    protected boolean isSelected(int i) {
        return false;
    }

    public abstract GuiListExtendedIGuiListEntry getListEntry(int i);

    protected void elementClicked(int i, boolean flag, int j, int k) {}

    protected void drawSlot(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        this.getListEntry(p171_0)
                .drawEntry(
                        p171_0,
                        p171_1,
                        p171_2,
                        this.cM(),
                        p171_3,
                        p171_4,
                        p171_5,
                        this.getSlotIndexFromScreenCoords(p171_4, p171_5) == p171_0);
    }

    public boolean mouseClicked(int p171_0, int p171_1, int p171_2) {
        if (this.isMouseYWithinSlotBounds(p171_1)) {
            int l = this.getSlotIndexFromScreenCoords(p171_0, p171_1);
            if (l >= 0) {
                int i1 = this.left + this.width / 2 - this.cM() / 2 + 2;
                int j1 =
                        this.top
                                + 4
                                - this.getAmountScrolled()
                                + l * this.slotHeight
                                + this.headerPadding;
                int k1 = p171_0 - i1;
                int l1 = p171_1 - j1;
                if (this.getListEntry(l).mousePressed(l, p171_0, p171_1, p171_2, k1, l1)) {
                    this.setEnabled(false);
                    return true;
                }
            }
        }

        return false;
    }
}
