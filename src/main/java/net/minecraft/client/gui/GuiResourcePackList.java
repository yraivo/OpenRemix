package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public abstract class GuiResourcePackList extends GuiListExtended {
    protected List lh;
    protected MinecraftClient mc;
    private static String[] packedTextFragments;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public List getList() {
        return this.lh;
    }

    public GuiListExtendedIGuiListEntry U(int i) {
        return this.V(i);
    }

    protected void drawListHeader(int i, int j, Tessellator tessellator) {
        String text =
                EnumChatFormatting.UNDERLINE
                        + packedTextFragments[0]
                        + EnumChatFormatting.BOLD
                        + this.getListHeader();
        this.mc.fontRendererObj.drawString(
                text,
                i + this.width / 2 - this.mc.fontRendererObj.getStringWidth(text) / 2,
                Math.min(this.top + 3, j),
                16777215);
    }

    // region String decoding support

    // endregion

    public GuiResourcePackList(MinecraftClient avdValue, int i, int j, List list) {
        super(avdValue, i, j, 32, j - 55 + 4, 36);
        this.mc = avdValue;
        this.lh = list;
        this.kS = false;
        this.setHasListHeader(true, (int) (avdValue.fontRendererObj.FONT_HEIGHT * 1.5F));
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiResourcePackList.packedTextFragments[0] = new String("");
    }

    // endregion

    public GuiListExtendedIGuiListEntry getListEntry(int i) {
        return this.V(i);
    }

    public int cM() {
        return this.width;
    }

    protected abstract String getListHeader();

    protected int getSize() {
        return this.getList().size();
    }

    public ResourcePackListEntry V(int i) {
        return (ResourcePackListEntry) this.getList().get(i);
    }

    protected int getScrollBarX() {
        return this.right - 6;
    }
}
