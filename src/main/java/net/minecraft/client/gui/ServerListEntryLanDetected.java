package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.LanServerDetectorLanServer;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected implements GuiListExtendedIGuiListEntry {

    private GuiMultiplayer avF;

    private long vj;
    protected LanServerDetectorLanServer avH;
    protected MinecraftClient mc;

    public void mouseReleased(int i, int j, int k, int l, int i1, int j1) {}

    public void drawEntry(int k, int i, int j, int l, int i1, int j1, int k1, boolean flag) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.mc.fontRendererObj.drawString(
                I18n.format("lanServer.title", new Object[0]), i + 32 + 3, j + 1, 16777215);
        this.mc.fontRendererObj.drawString(
                this.avH.getServerMotd(), i + 32 + 3, j + 12, 8421504);
        String[] astring = astring1;
        if (this.mc.gameSettings.hideServerAddress) {
            this.mc.fontRendererObj.drawString(
                    I18n.format("selectServer.hiddenAddress", new Object[0]),
                    i + 32 + 3,
                    j + 12 + 11,
                    3158064);
            if (astring == null) {
                return;
            }
        }

        this.mc.fontRendererObj.drawString(
                this.avH.getServerIpPort(), i + 32 + 3, j + 12 + 11, 3158064);
    }


    public void setSelected(int i, int j, int k) {}

    public LanServerDetectorLanServer getLanServer() {
        return this.avH;
    }

    public boolean mousePressed(int i, int l, int i1, int j1, int k1, int l1) {
        this.avF.selectServer(i);
        long j = MinecraftClient.getSystemTime() - this.vj;

        if (j < 250L) {
            this.avF.connectToSelected();
        }

        this.vj = MinecraftClient.getSystemTime();
        return false;
    }

    protected ServerListEntryLanDetected(
            GuiMultiplayer avFValue, LanServerDetectorLanServer avHValue) {

        this.vj = 0L;
        this.avF = avFValue;
        this.avH = avHValue;
        this.mc = MinecraftClient.getMinecraft();
    }
}
