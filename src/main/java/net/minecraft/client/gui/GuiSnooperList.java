package net.minecraft.client.gui;

class GuiSnooperList extends GuiSlot {
    GuiSnooper avm;

    public GuiSnooperList(GuiSnooper avmValue) {
        super(
                avmValue.mc,
                avmValue.width,
                avmValue.height,
                80,
                avmValue.height - 40,
                avmValue.fontRendererObj.FONT_HEIGHT + 1);
        this.avm = avmValue;
    }

    protected boolean isSelected(int i) {
        return false;
    }

    protected void elementClicked(int i, boolean flag, int j, int k) {}

    protected int getScrollBarX() {
        return this.width - 10;
    }

    protected void drawBackground() {}

    protected int getSize() {
        return GuiSnooper.access$000(this.avm).size();
    }

    protected void drawSlot(int i, int k, int j, int l, int i1, int j1) {
        this.avm.fontRendererObj.drawString(
                (String) GuiSnooper.access$000(this.avm).get(i), 10, j, 16777215);
        this.avm.fontRendererObj.drawString(
                (String) GuiSnooper.access$100(this.avm).get(i), 230, j, 16777215);
    }
}
