package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtendedIGuiListEntry {

    private final MinecraftClient mc = MinecraftClient.getMinecraft();

    public boolean mousePressed(int i, int j, int k, int l, int i1, int j1) {
        return false;
    }

    public void setSelected(int i, int j, int k) {}

    public void drawEntry(int k1, int l1, int i, int i2, int j, int j2, int k2, boolean flag) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int k = i + j / 2 - this.mc.fontRendererObj.FONT_HEIGHT / 2;
        String[] astring = astring1;
        this.mc.fontRendererObj.drawString(
                I18n.format("lanServer.scanning", new Object[0]),
                this.mc.currentScreen.width / 2
                        - this.mc.fontRendererObj.getStringWidth(
                                        I18n.format("lanServer.scanning", new Object[0]))
                                / 2,
                k,
                16777215);
        long l = MinecraftClient.getSystemTime();

        long i1 = l / 300L;

        String text;
        switch ((int) (i1 % 4L)) {
            case 0:
            default:
                text = "O o o";
                if (astring == null) {
                    break;
                }
            case 1:
            case 3:
                text = "o O o";
                if (astring == null) {
                    break;
                }
            case 2:
                text = "o o O";
        }

        this.mc.fontRendererObj.drawString(
                text,
                this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.getStringWidth(text) / 2,
                k + this.mc.fontRendererObj.FONT_HEIGHT,
                8421504);
    }


    public void mouseReleased(int i, int j, int k, int l, int i1, int j1) {}
}
