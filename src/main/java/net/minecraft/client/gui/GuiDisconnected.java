package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponent;
import remix.ui.account.AltServiceSwitcher;
import net.minecraft.client.gui.GuiScreen;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.ProxyType;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.screen.RemixMainMenuScreen;

import com.thealtening.api.data.AccountData;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.util.List;

public class GuiDisconnected extends GuiScreen {
    private List<String> multilineMessage;
    private GuiScreen parentScreen;

    private int messageHeight;
    private static String[] packedTextFragments;
    private String reason;
    private ChatComponent message;

    private void selectOrImportProxy() {
        boolean legacyProxyFlow = Gui.getLegacyGuiRenderMarker() != null;
        RemixClient.INSTANCE.getClientServices();
        NordVpnProxyManager proxies = ClientServices.nordVpnProxyManager;
        if ((isShiftKeyDown() || proxies.getList().isEmpty()) && proxies.getProxyType() != ProxyType.NORDVPN) {
            proxies.importProxyList(proxies.getProxyType());
            if (!legacyProxyFlow) {
                return;
            }
        }
        proxies.chooseProxyOrImport();
    }

    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.messageHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        int left = this.width / 2 - 100;
        int right = left + 102;
        int y = this.height / 2 + this.messageHeight / 2 + this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, left, y, I18n.format("gui.toMenu")));
        this.buttonList.add(new GuiButton(1, left, y + 22, 98, 20, "New Alt"));
        RemixClient.INSTANCE.getClientServices();
        this.buttonList.add(new GuiButton(2, right, y + 22, 98, 20,
                "Alt: " + ClientServices.altServiceSwitcher.getServiceLabel()));
        this.buttonList.add(new GuiButton(3, left, y + 44, 98, 20, "New Proxy"));
        this.buttonList.add(new GuiButton(4, right, y + 44, 98, 20,
                "Proxy: " + ClientServices.nordVpnProxyManager.getProxyType().getDisplayName()));
        this.buttonList.add(new GuiButton(5, left, y + 66, 98, 20, "Combined"));
        this.buttonList.add(new GuiButton(6, right, y + 66, 98, 20, "Reconnect"));
    }

    private void selectAlternateAccount() {
        boolean legacyAccountFlow = Gui.getLegacyGuiRenderMarker() != null;
        RemixClient.INSTANCE.getClientServices();
        AltServiceSwitcher service = ClientServices.altServiceSwitcher;
        if (service.getServiceLabel().equalsIgnoreCase("TheAltening")) {
            try {
                AccountData account = service.getTheAltening().generateAccount();
                if (account != null) {
                    ClientServices.altLoginManager.loginWithCredentials(account.getToken(), "a");
                }
                return;
            } catch (Exception unavailable) {
                if (!legacyAccountFlow) {
                    return;
                }
            }
        }

        try {
            String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            if (clipboard.isEmpty()) {
                return;
            }
            String[] fields = clipboard.split(":");
            if (fields.length >= 2) {
                ClientServices.altLoginManager.loginWithCredentials(fields[0], fields[1]);
                if (!legacyAccountFlow) {
                    return;
                }
            }
            ClientServices.altLoginManager.loginWithCredentials(fields[0], "");
        } catch (Exception ignored) {
            // The original screen leaves clipboard/login failures to the account manager.
        }
    }

    public GuiDisconnected(GuiScreen parent, String reasonKey, ChatComponent message) {
        this.parentScreen = parent;
        this.reason = I18n.format(reasonKey);
        this.message = message;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean legacyScreenFlow = Gui.getLegacyGuiRenderMarker() != null;
        this.drawDefaultBackground();
        int messageY = this.height / 2 - this.messageHeight / 2;
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2,
                messageY - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);

        if (this.buttonList.size() > 3) {
            this.updateProxyButtonLabel(legacyScreenFlow);
        }
        if (this.multilineMessage != null) {
            for (String line : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, line, this.width / 2, messageY, 16777215);
                messageY += this.fontRendererObj.FONT_HEIGHT;
                if (legacyScreenFlow) {
                    break;
                }
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateProxyButtonLabel(boolean legacyScreenFlow) {
        boolean importProxies = isShiftKeyDown();
        if (!importProxies) {
            RemixClient.INSTANCE.getClientServices();
            importProxies = ClientServices.nordVpnProxyManager.getList().isEmpty();
        }
        GuiButton proxyButton = this.buttonList.get(3);
        if (importProxies) {
            proxyButton.setDisplayString("Import Proxies...");
            if (!legacyScreenFlow) {
                return;
            }
        }
        proxyButton.setDisplayString("New Proxy");
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        boolean legacyScreenFlow = Gui.getLegacyGuiRenderMarker() != null;
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(this.parentScreen);
                if (!legacyScreenFlow) {
                    return;
                }
            case 1:
                this.selectAlternateAccount();
                this.reconnectThroughServerList();
                if (!legacyScreenFlow) {
                    return;
                }
            case 2:
                RemixClient.INSTANCE.getClientServices();
                AltServiceSwitcher service = ClientServices.altServiceSwitcher;
                service.cycleAuthService();
                this.buttonList.get(2).setDisplayString("Alt: " + service.getServiceLabel());
                if (!legacyScreenFlow) {
                    return;
                }
            case 3:
                this.selectOrImportProxy();
                this.reconnectThroughServerList();
                if (!legacyScreenFlow) {
                    return;
                }
            case 4:
                RemixClient.INSTANCE.getClientServices();
                NordVpnProxyManager proxies = ClientServices.nordVpnProxyManager;
                proxies.cycleProxyType();
                this.buttonList.get(4).setDisplayString("Proxy: " + proxies.getProxyType().getDisplayName());
                if (!legacyScreenFlow) {
                    return;
                }
            case 5:
                this.selectAlternateAccount();
                this.selectOrImportProxy();
                this.reconnectThroughServerList();
                if (!legacyScreenFlow) {
                    return;
                }
            case 6:
                this.reconnectToLastServer(this, this.mc);
            default:
        }
    }

    private void reconnectThroughServerList() {
        GuiScreen serverList = new GuiMultiplayer(new RemixMainMenuScreen(RemixClient.INSTANCE));
        this.reconnectToLastServer(serverList, MinecraftClient.getMinecraft());
    }

    private void reconnectToLastServer(GuiScreen parent, MinecraftClient client) {
        ServerData server = GuiConnecting.lastServerData;
        if (server != null) {
            client.displayGuiScreen(new GuiConnecting(parent, client, server));
        }
    }

    static {
        net.minecraft.client.gui.GuiDisconnected.packedTextFragments = new String[6];
        net.minecraft.client.gui.GuiDisconnected.packedTextFragments[0] = new String("a");
        net.minecraft.client.gui.GuiDisconnected.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiDisconnected.packedTextFragments[2] = new String(":");
        net.minecraft.client.gui.GuiDisconnected.packedTextFragments[3] = new String("");
    }

    protected void keyTyped(char c0, int i) throws IOException {}
}
