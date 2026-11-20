package net.minecraft.client.gui.achievement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

class GuiStatsStatsGeneral extends GuiSlot {
    GuiStats avt;

    protected void elementClicked(int i, boolean flag, int j, int k) {}

    protected int getContentHeight() {
        return this.getSize() * 10;
    }

    public GuiStatsStatsGeneral(GuiStats avtValue, MinecraftClient minecraftClient) {
        super(minecraftClient, avtValue.width, avtValue.height, 32, avtValue.height - 64, 10);
        this.avt = avtValue;
        this.setShowSelectionBox(false);
    }

    protected boolean isSelected(int i) {
        return false;
    }

    protected int getSize() {
        return StatList.generalStats.size();
    }

    protected void drawBackground() {
        this.avt.drawDefaultBackground();
    }

    protected void drawSlot(int i, int j, int k, int l, int i1, int j1) {
        StatBase statBase = (StatBase) StatList.generalStats.get(i);
        this.avt.drawString(
                GuiStats.access$000(this.avt),
                statBase.getStatName().getUnformattedText(),
                j + 2,
                k + 1,
                i % 2 == 0 ? 16777215 : 9474192);
        String text = statBase.format(GuiStats.access$100(this.avt).writeStat(statBase));
        this.avt.drawString(
                GuiStats.access$200(this.avt),
                text,
                j + 2 + 213 - GuiStats.access$300(this.avt).getStringWidth(text),
                k + 1,
                i % 2 == 0 ? 16777215 : 9474192);
    }
}
