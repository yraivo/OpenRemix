package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityListEntityEggInfo;
import net.minecraft.util.ResourceLocation;

import java.util.List;

class GuiStatsStatsMobsList extends GuiSlot {

    GuiStats avv;

    private List xU;

    protected boolean isSelected(int i) {
        return false;
    }

    public GuiStatsStatsMobsList(GuiStats avvValue, MinecraftClient minecraftClient) {
        super(
                minecraftClient,
                avvValue.width,
                avvValue.height,
                32,
                avvValue.height - 64,
                GuiStats.method_fbaa1a1dbafd33505ab4(avvValue).FONT_HEIGHT * 4);
        ResourceLocation.cB();

        this.avv = avvValue;

        this.xU = Lists.newArrayList();
        this.setShowSelectionBox(false);

        for (java.util.Iterator<?> __recoveryIterator171_22780 =
                        (EntityList.entityEggs.values()).iterator();
                (__recoveryIterator171_22780).hasNext(); ) {
            EntityListEntityEggInfo entityListEntityEggInfo =
                    (EntityListEntityEggInfo) (__recoveryIterator171_22780).next();
            if (GuiStats.access$100(avvValue).writeStat(entityListEntityEggInfo.field_151512_d) > 0
                    || GuiStats.access$100(avvValue)
                                    .writeStat(entityListEntityEggInfo.field_151513_e)
                            > 0) {
                this.xU.add(entityListEntityEggInfo);
            }
        }
    }

    protected int getSize() {
        return this.xU.size();
    }

    protected void drawSlot(int i, int j, int k, int j1, int k1, int l1) {
        EntityListEntityEggInfo entityListEntityEggInfo = (EntityListEntityEggInfo) this.xU.get(i);
        String text =
                I18n.format(
                        "entity."
                                + EntityList.getStringFromID(entityListEntityEggInfo.spawnedID)
                                + ".name",
                        new Object[0]);
        int l = GuiStats.access$100(this.avv).writeStat(entityListEntityEggInfo.field_151512_d);
        int i1 = GuiStats.access$100(this.avv).writeStat(entityListEntityEggInfo.field_151513_e);
        String textValue = I18n.format("stat.entityKills", new Object[] {l, text});
        String textValue2 = I18n.format("stat.entityKilledBy", new Object[] {text, i1});
        if (l == 0) {
            textValue = I18n.format("stat.entityKills.none", new Object[] {text});
        }

        if (i1 == 0) {
            textValue2 = I18n.format("stat.entityKilledBy.none", new Object[] {text});
        }

        this.avv.drawString(GuiStats.access$1800(this.avv), text, j + 2 - 10, k + 1, 16777215);
        this.avv.drawString(
                GuiStats.access$1900(this.avv),
                textValue,
                j + 2,
                k + 1 + GuiStats.access$2000(this.avv).FONT_HEIGHT,
                l == 0 ? 6316128 : 9474192);
        this.avv.drawString(
                GuiStats.access$2100(this.avv),
                textValue2,
                j + 2,
                k + 1 + GuiStats.access$2200(this.avv).FONT_HEIGHT * 2,
                i1 == 0 ? 6316128 : 9474192);
    }

    protected void elementClicked(int i, boolean flag, int j, int k) {}


    protected int getContentHeight() {
        return this.getSize() * GuiStats.access$1700(this.avv).FONT_HEIGHT * 4;
    }

    protected void drawBackground() {
        this.avv.drawDefaultBackground();
    }
}
