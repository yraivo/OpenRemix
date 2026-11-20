package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetectorLanServer;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class ServerSelectionList extends GuiListExtended {
    private int selectedSlotIndex;
    private GuiMultiplayer owner;
    private final List lh = Lists.newArrayList();
    private GuiListExtendedIGuiListEntry lanScanEntry;
    private final List rp = Lists.newArrayList();

    public void refreshLanServerEntries(List list) {
        this.rp.clear();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        Iterator iterator = list.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            LanServerDetectorLanServer lanServerDetectorLanServer =
                    (LanServerDetectorLanServer) iterator.next();
            this.rp.add(new ServerListEntryLanDetected(this.owner, lanServerDetectorLanServer));
            if (astring != null) {
                break;
            }
        }
    }

    protected int getSize() {
        return this.lh.size() + 1 + this.rp.size();
    }

    public ServerSelectionList(
            GuiMultiplayer avhValue,
            MinecraftClient minecraftClient,
            int i,
            int j,
            int k,
            int l,
            int i1) {
        super(minecraftClient, i, j, k, l, i1);
        this.lanScanEntry = new ServerListEntryLanScan();
        this.selectedSlotIndex = -1;
        this.owner = avhValue;
    }

    public void setSelectedSlotIndex(int selectedSlotIndexValue) {
        this.selectedSlotIndex = selectedSlotIndexValue;
    }

    @Override
    public GuiListExtendedIGuiListEntry getListEntry(int i) {
        if (i < this.lh.size()) {
            return (GuiListExtendedIGuiListEntry) this.lh.get(i);
        }

        i -= this.lh.size();
        return i == 0 ? this.lanScanEntry : (GuiListExtendedIGuiListEntry) this.rp.get(--i);
    }

    protected int getScrollBarX() {
        return super.getScrollBarX() + 30;
    }

    public int cM() {
        return super.cM() + 85;
    }

    public void refreshSavedServerEntries(ServerList serverList) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.lh.clear();
        String[] astring = astring1;
        int i = 0;

        while (i < serverList.countServers()) {
            this.lh.add(new ServerListEntryNormal(this.owner, serverList.getServerData(i)));
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public int getSelectedSlotIndex() {
        return this.selectedSlotIndex;
    }

    @Override
    protected boolean isSelected(int i) {
        return i == this.selectedSlotIndex;
    }
}
