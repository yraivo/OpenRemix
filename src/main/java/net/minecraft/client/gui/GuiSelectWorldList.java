package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.storage.SaveFormatComparator;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

class GuiSelectWorldList extends GuiSlot {

    GuiSelectWorld avk;
    private static String[] packedTextFragments;

    protected int getSize() {
        return GuiSelectWorld.access$000(this.avk).size();
    }

    protected void elementClicked(int i, boolean flag, int k, int l) {
        GuiSelectWorld.access$102(this.avk, i);
        int j =
                GuiSelectWorld.access$100(this.avk) >= 0
                                && GuiSelectWorld.access$100(this.avk) < this.getSize()
                        ? 1
                        : 0;
        GuiSelectWorld.access$200(this.avk).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(j);
        GuiSelectWorld.access$300(this.avk).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(j);
        GuiSelectWorld.access$400(this.avk).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(j);
        GuiSelectWorld.access$500(this.avk).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(j);
        if (flag && j != 0) {
            this.avk.method_30be6ce7816ed46ef891(i);
        }
    }

    protected void drawSlot(int i, int j, int k, int l, int i1, int j1) {
        SaveFormatComparator saveFormatComparator =
                (SaveFormatComparator) GuiSelectWorld.access$000(this.avk).get(i);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        String text = saveFormatComparator.getDisplayName();
        String[] astring = astring1;
        if (StringUtils.isEmpty(text)) {
            text = GuiSelectWorld.access$600(this.avk) + packedTextFragments[0] + (i + 1);
        }

        String textValue;
        String textValue2;
        label24:
        {
            textValue = saveFormatComparator.getFileName();
            textValue =
                    textValue
                            + " ("
                            + GuiSelectWorld.access$700(this.avk)
                                    .format(new Date(saveFormatComparator.getLastTimePlayed()));
            textValue = textValue + packedTextFragments[1];
            textValue2 = packedTextFragments[2];
            if (saveFormatComparator.requiresConversion()) {
                textValue2 =
                        GuiSelectWorld.access$800(this.avk) + packedTextFragments[3] + textValue2;
                if (astring == null) {
                    break label24;
                }
            }

            textValue2 =
                    GuiSelectWorld.access$900(this.avk)[
                            saveFormatComparator.getEnumGameType().getID()];
            if (saveFormatComparator.isHardcoreModeEnabled()) {
                textValue2 =
                        EnumChatFormatting.DARK_RED
                                + I18n.format("gameMode.hardcore", new Object[0])
                                + EnumChatFormatting.RESET;
            }

            if (saveFormatComparator.getCheatsEnabled()) {
                textValue2 = textValue2 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
            }
        }

        this.avk.drawString(this.avk.fontRendererObj, text, j + 2, k + 1, 16777215);
        this.avk.drawString(this.avk.fontRendererObj, textValue, j + 2, k + 12, 8421504);
        this.avk.drawString(this.avk.fontRendererObj, textValue2, j + 2, k + 12 + 10, 8421504);
    }

    static {
        net.minecraft.client.gui.GuiSelectWorldList.packedTextFragments = new String[6];
        net.minecraft.client.gui.GuiSelectWorldList.packedTextFragments[0] = new String(" ");
        net.minecraft.client.gui.GuiSelectWorldList.packedTextFragments[1] = new String(")");
        net.minecraft.client.gui.GuiSelectWorldList.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiSelectWorldList.packedTextFragments[3] = new String(" ");
    }

    public GuiSelectWorldList(GuiSelectWorld avkValue, MinecraftClient minecraftClient) {
        super(minecraftClient, avkValue.width, avkValue.height, 32, avkValue.height - 64, 36);
        this.avk = avkValue;
    }

    protected boolean isSelected(int i) {
        return i == GuiSelectWorld.access$100(this.avk);
    }

    protected int getContentHeight() {
        return GuiSelectWorld.access$000(this.avk).size() * 36;
    }

    protected void drawBackground() {
        this.avk.drawDefaultBackground();
    }
}
