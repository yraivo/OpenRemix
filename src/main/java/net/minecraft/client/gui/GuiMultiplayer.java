package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetectorLanServer;
import net.minecraft.client.network.LanServerDetectorLanServerList;
import net.minecraft.client.network.LanServerDetectorThreadLanServerFind;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import remix.ui.account.AltManagerScreen;
import net.minecraft.client.gui.GuiScreen;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.hidden.BanCheckerModule;
import remix.ui.screen.NickSpoofScreen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback {
    private boolean serverListInitialized;
    private GuiScreen parentScreen;
    private boolean deletingServer;
    private final OldServerPinger oldServerPinger = new OldServerPinger();
    private static Logger logger;
    private static String[] packedTextFragments;
    private ServerSelectionList serverListSelector;

    private String hoveringText;
    private boolean directConnectPending;
    private GuiButton btnEditServer;
    private boolean addingServer;

    private GuiButton btnDeleteServer;
    private LanServerDetectorLanServerList lanServerList;
    private LanServerDetectorThreadLanServerFind lanServerDetector;
    private boolean editingServer;
    private ServerData selectedServer;
    private ServerList savedServerList;
    private GuiButton btnSelectServer;

    protected void keyTyped(char c0, int i) throws IOException {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int j = this.serverListSelector.getSelectedSlotIndex();
        String[] astring = astring1;
        GuiListExtendedIGuiListEntry guiListExtendedIGuiListEntry =
                j < 0 ? null : this.serverListSelector.getListEntry(j);
        if (i == 63) {
            this.refreshServerList();
            if (astring == null) {
                return;
            }
        }

        if (j >= 0) {
            if (i == 200) {
                if (isShiftKeyDown()) {
                    if (j <= 0
                            || !(guiListExtendedIGuiListEntry instanceof ServerListEntryNormal)) {
                        return;
                    }

                    this.savedServerList.swapServers(j, j - 1);
                    this.selectServer(this.serverListSelector.getSelectedSlotIndex() - 1);
                    this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                    this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
                    if (astring == null) {
                        return;
                    }
                }

                if (j > 0) {
                    this.selectServer(this.serverListSelector.getSelectedSlotIndex() - 1);
                    this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                    if (!(this.serverListSelector.getListEntry(
                                    this.serverListSelector.getSelectedSlotIndex())
                            instanceof ServerListEntryLanScan)) {
                        return;
                    }

                    if (this.serverListSelector.getSelectedSlotIndex() > 0) {
                        this.selectServer(this.serverListSelector.getSize() - 1);
                        this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        if (astring == null) {
                            return;
                        }
                    }

                    this.selectServer(-1);
                    if (astring == null) {
                        return;
                    }
                }

                this.selectServer(-1);
                if (astring == null) {
                    return;
                }
            }

            if (i == 208) {
                if (isShiftKeyDown()) {
                    if (j >= this.savedServerList.countServers() - 1) {
                        return;
                    }

                    this.savedServerList.swapServers(j, j + 1);
                    this.selectServer(j + 1);
                    this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                    this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
                    if (astring == null) {
                        return;
                    }
                }

                if (j < this.serverListSelector.getSize()) {
                    this.selectServer(this.serverListSelector.getSelectedSlotIndex() + 1);
                    this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                    if (!(this.serverListSelector.getListEntry(
                                    this.serverListSelector.getSelectedSlotIndex())
                            instanceof ServerListEntryLanScan)) {
                        return;
                    }

                    if (this.serverListSelector.getSelectedSlotIndex()
                            < this.serverListSelector.getSize() - 1) {
                        this.selectServer(this.serverListSelector.getSize() + 1);
                        this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        if (astring == null) {
                            return;
                        }
                    }

                    this.selectServer(-1);
                    if (astring == null) {
                        return;
                    }
                }

                this.selectServer(-1);
                if (astring == null) {
                    return;
                }
            }

            if (i != 28 && i != 156) {
                super.keyTyped(c0, i);
                if (astring == null) {
                    return;
                }
            }

            this.actionPerformed((GuiButton) this.buttonList.get(2));
            if (astring == null) {
                return;
            }
        }

        super.keyTyped(c0, i);
    }

    public ServerList getServerList() {
        return this.savedServerList;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.serverListSelector.mouseClicked(i, j, k);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        Gui.getLegacyGuiRenderMarker();
        if (!button.enabled) {
            return;
        }

        int selectedIndex = this.serverListSelector.getSelectedSlotIndex();
        GuiListExtendedIGuiListEntry entry = selectedIndex < 0 ? null : this.serverListSelector.getListEntry(selectedIndex);
        switch (button.id) {
            case 2:
                if (entry instanceof ServerListEntryNormal) {
                    this.confirmDeleteServer(((ServerListEntryNormal) entry).getServerData(), selectedIndex);
                }
                return;
            case 1:
                this.connectToSelected();
                return;
            case 4:
                this.directConnectPending = true;
                this.selectedServer = new ServerData(I18n.format("selectServer.defaultName"), packedTextFragments[1]);
                this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer));
                return;
            case 3:
                this.addingServer = true;
                this.selectedServer = new ServerData(I18n.format("selectServer.defaultName"), packedTextFragments[2]);
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
                return;
            case 7:
                if (entry instanceof ServerListEntryNormal) {
                    ServerData server = ((ServerListEntryNormal) entry).getServerData();
                    this.editingServer = true;
                    this.selectedServer = new ServerData(server.serverName, server.serverIP);
                    this.selectedServer.copyFrom(server);
                    this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
                }
                return;
            case 0:
                this.mc.displayGuiScreen(this.parentScreen);
                return;
            case 8:
                this.refreshServerList();
                return;
            case 5:
                this.mc.displayGuiScreen(new NickSpoofScreen(this));
                return;
            case 69:
                this.mc.displayGuiScreen(new AltManagerScreen(this));
                return;
            case 420:
                RemixClient.INSTANCE.getClientServices();
                BanCheckerModule banChecker = ClientServices.moduleManager.banChecker;
                banChecker.setBanCheckEnabled(!banChecker.isBanCheckEnabled());
                button.displayString = banChecker.getBanCheckButtonLabel();
                return;
            default:
                return;
        }
    }

    private void confirmDeleteServer(ServerData server, int selectedIndex) {
        if (server.serverName == null) {
            return;
        }
        this.deletingServer = true;
        String question = I18n.format("selectServer.deleteQuestion");
        String warning = packedTextFragments[0] + server.serverName + "' " + I18n.format("selectServer.deleteWarning");
        this.mc.displayGuiScreen(new GuiYesNo(this, question, warning,
                I18n.format("selectServer.deleteButton"), I18n.format("gui.cancel"), selectedIndex));
    }

    private void refreshServerList() {
        this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
    }

    private void connectToServer(ServerData serverData) {
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, serverData));
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        if (this.lanServerDetector != null) {
            this.lanServerDetector.interrupt();
            this.lanServerDetector = null;
        }

        this.oldServerPinger.clearPendingNetworks();
    }

    public void moveServerUp(
            ServerListEntryNormal serverListEntryNormal, int i, boolean flag) {
        int j = flag ? 0 : i - 1;
        this.savedServerList.swapServers(i, j);
        if (this.serverListSelector.getSelectedSlotIndex() == i) {
            this.selectServer(j);
        }

        this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
    }

    public void setHoveringText(String text) {
        this.hoveringText = text;
    }

    public void selectServer(int i) {
        this.serverListSelector.setSelectedSlotIndex(i);
        GuiListExtendedIGuiListEntry guiListExtendedIGuiListEntry =
                i < 0 ? null : this.serverListSelector.getListEntry(i);
        this.btnSelectServer.enabled = false;
        this.btnEditServer.enabled = false;
        this.btnDeleteServer.enabled = false;
        if (guiListExtendedIGuiListEntry != null
                && !(guiListExtendedIGuiListEntry instanceof ServerListEntryLanScan)) {
            this.btnSelectServer.enabled = true;
            if (guiListExtendedIGuiListEntry instanceof ServerListEntryNormal) {
                this.btnEditServer.enabled = true;
                this.btnDeleteServer.enabled = true;
            }
        }
    }

    public void updateScreen() {
        super.updateScreen();
        if (this.lanServerList.getWasUpdated()) {
            List lanServers = this.lanServerList.getLanServers();
            this.lanServerList.setWasNotUpdated();
            this.serverListSelector.refreshLanServerEntries(lanServers);
        }

        this.oldServerPinger.pingPendingNetworks();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        boolean resizeNewList = Gui.getLegacyGuiRenderMarker() != null;
        this.buttonList.clear();
        if (!this.serverListInitialized) {
            this.initializeServerList();
            if (resizeNewList) {
                this.resizeServerList();
            }
        } else {
            this.resizeServerList();
        }
        this.createServerListButtons();
    }

    private void initializeServerList() {
        this.serverListInitialized = true;
        this.savedServerList = new ServerList(this.mc);
        this.savedServerList.loadServerList();
        this.lanServerList = new LanServerDetectorLanServerList();
        try {
            this.lanServerDetector = new LanServerDetectorThreadLanServerFind(this.lanServerList);
            this.lanServerDetector.start();
        } catch (Exception failure) {
            logger.warn("Unable to start LAN server detection: " + failure.getMessage());
        }
        this.serverListSelector = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
        this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
    }

    private void resizeServerList() {
        this.serverListSelector.setDimensions(this.width, this.height, 32, this.height - 64);
    }

    public void moveServerDown(
            ServerListEntryNormal serverListEntryNormal, int i, boolean flag) {
        int j = flag ? this.savedServerList.countServers() - 1 : i + 1;
        this.savedServerList.swapServers(i, j);
        if (this.serverListSelector.getSelectedSlotIndex() == i) {
            this.selectServer(j);
        }

        this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
    }

    public OldServerPinger getOldServerPinger() {
        return this.oldServerPinger;
    }

    public void drawScreen(int i, int j, float f) {
        this.hoveringText = null;
        this.drawDefaultBackground();
        this.serverListSelector.drawScreen(i, j, f);
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("multiplayer.title", new Object[0]),
                this.width / 2,
                20,
                16777215);
        super.drawScreen(i, j, f);
        if (this.hoveringText != null) {
            this.drawHoveringText(
                    Lists.newArrayList(Splitter.on(packedTextFragments[3]).split(this.hoveringText)), i, j);
        }
    }

    public void connectToSelected() {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        GuiListExtendedIGuiListEntry guiListExtendedIGuiListEntry =
                this.serverListSelector.getSelectedSlotIndex() < 0
                        ? null
                        : this.serverListSelector.getListEntry(
                                this.serverListSelector.getSelectedSlotIndex());
        if (guiListExtendedIGuiListEntry instanceof ServerListEntryNormal) {
            this.connectToServer(
                    ((ServerListEntryNormal) guiListExtendedIGuiListEntry).getServerData());
            if (astring == null) {
                return;
            }
        }

        if (guiListExtendedIGuiListEntry instanceof ServerListEntryLanDetected) {
            LanServerDetectorLanServer lanServer =
                    ((ServerListEntryLanDetected) guiListExtendedIGuiListEntry).getLanServer();
            this.connectToServer(
                    new ServerData(
                            lanServer.getServerMotd(),
                            lanServer.getServerIpPort()));
        }
    }

    public boolean canMoveServerDown(ServerListEntryNormal serverListEntryNormal, int i) {
        return i < this.savedServerList.countServers() - 1;
    }

    public void confirmClicked(boolean flag, int i) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        GuiListExtendedIGuiListEntry guiListExtendedIGuiListEntry =
                this.serverListSelector.getSelectedSlotIndex() < 0
                        ? null
                        : this.serverListSelector.getListEntry(
                                this.serverListSelector.getSelectedSlotIndex());
        if (this.deletingServer) {
            this.deletingServer = false;
            if (flag && guiListExtendedIGuiListEntry instanceof ServerListEntryNormal) {
                this.savedServerList.removeServerData(
                        this.serverListSelector.getSelectedSlotIndex());
                this.savedServerList.saveServerList();
                this.serverListSelector.setSelectedSlotIndex(-1);
                this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
            }

            this.mc.displayGuiScreen(this);
            if (astring == null) {
                return;
            }
        }

        if (this.directConnectPending) {
            this.directConnectPending = false;
            if (flag) {
                this.connectToServer(this.selectedServer);
                if (astring == null) {
                    return;
                }
            }

            this.mc.displayGuiScreen(this);
            if (astring == null) {
                return;
            }
        }

        if (this.addingServer) {
            this.addingServer = false;
            if (flag) {
                this.savedServerList.addServerData(this.selectedServer);
                this.savedServerList.saveServerList();
                this.serverListSelector.setSelectedSlotIndex(-1);
                this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
            }

            this.mc.displayGuiScreen(this);
            if (astring == null) {
                return;
            }
        }

        if (this.editingServer) {
            this.editingServer = false;
            if (flag && guiListExtendedIGuiListEntry instanceof ServerListEntryNormal) {
                ServerData serverData =
                        ((ServerListEntryNormal) guiListExtendedIGuiListEntry).getServerData();
                serverData.serverName = this.selectedServer.serverName;
                serverData.serverIP = this.selectedServer.serverIP;
                serverData.copyFrom(this.selectedServer);
                this.savedServerList.saveServerList();
                this.serverListSelector.refreshSavedServerEntries(this.savedServerList);
            }

            this.mc.displayGuiScreen(this);
        }
    }

    public boolean canMoveServerUp(ServerListEntryNormal serverListEntryNormal, int i) {
        return i > 0;
    }

    public GuiMultiplayer(GuiScreen anPValue) {
        this.parentScreen = anPValue;
    }

    protected void mouseReleased(int i, int j, int k) {
        super.mouseReleased(i, j, k);
        this.serverListSelector.releaseMouseOnEntries(i, j, k);
    }

    static {
        net.minecraft.client.gui.GuiMultiplayer.packedTextFragments = new String[6];
        net.minecraft.client.gui.GuiMultiplayer.packedTextFragments[0] = new String("'");
        net.minecraft.client.gui.GuiMultiplayer.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiMultiplayer.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiMultiplayer.packedTextFragments[3] = new String("\n");
        net.minecraft.client.gui.GuiMultiplayer.logger = LogManager.getLogger();
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.serverListSelector.handleMouseInput();
    }

    public void createServerListButtons() {
        this.buttonList.add(
                this.btnEditServer =
                        new GuiButton(
                                7,
                                this.width / 2 - 154,
                                this.height - 28,
                                70,
                                20,
                                I18n.format("selectServer.edit", new Object[0])));
        this.buttonList.add(
                this.btnDeleteServer =
                        new GuiButton(
                                2,
                                this.width / 2 - 74,
                                this.height - 28,
                                70,
                                20,
                                I18n.format("selectServer.delete", new Object[0])));
        this.buttonList.add(
                this.btnSelectServer =
                        new GuiButton(
                                1,
                                this.width / 2 - 154,
                                this.height - 52,
                                100,
                                20,
                                I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        4,
                        this.width / 2 - 50,
                        this.height - 52,
                        100,
                        20,
                        I18n.format("selectServer.direct", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        3,
                        this.width / 2 + 4 + 50,
                        this.height - 52,
                        100,
                        20,
                        I18n.format("selectServer.add", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        8,
                        this.width / 2 + 4,
                        this.height - 28,
                        70,
                        20,
                        I18n.format("selectServer.refresh", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 + 4 + 76,
                        this.height - 28,
                        75,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width - 80 - 75, 5, 75, 20, "Set Nick/UUID"));
        this.buttonList.add(new GuiButton(69, this.width - 80, 5, 75, 20, "Accounts"));
        this.selectServer(this.serverListSelector.getSelectedSlotIndex());
    }
}
