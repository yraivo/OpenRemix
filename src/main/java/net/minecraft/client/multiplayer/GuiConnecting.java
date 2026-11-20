package net.minecraft.client.multiplayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerConnectThread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiConnecting extends GuiScreen {
    private boolean cancel;
    private static Logger logger;

    private static AtomicInteger CONNECTION_ID;
    private GuiScreen previousGuiScreen;
    private NetworkManager networkManager;
    public static ServerData lastServerData;

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }

            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    @Override
    public void updateScreen() {
        boolean legacyConnectionFlow = ClientWorld.getLegacyClientWorldFlowState() != null;
        if (this.networkManager == null) {
            return;
        }
        if (this.networkManager.isChannelOpen()) {
            this.networkManager.processReceivedPackets();
            if (!legacyConnectionFlow) {
                return;
            }
        }
        this.networkManager.checkDisconnected();
    }

    private void connect(String host, int port) {
        logger.info("Connecting to " + host + ", " + port);
        String threadName = "Server Connector #" + CONNECTION_ID.incrementAndGet();
        new ServerConnectThread(this, threadName, host, port).start();
    }

    @Override
    protected void keyTyped(char c0, int i) throws IOException {}

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 100,
                        this.height / 4 + 120 + 12,
                        I18n.format("gui.cancel", new Object[0])));
    }

    public static boolean isConnectionCancelled(GuiConnecting guiConnecting) {
        return guiConnecting.cancel;
    }

    public static GuiScreen getPreviousScreen(GuiConnecting guiConnecting) {
        return guiConnecting.previousGuiScreen;
    }

    public GuiConnecting(GuiScreen asbValue, MinecraftClient apiValue, ServerData asdValue) {
        this.mc = apiValue;
        this.previousGuiScreen = asbValue;
        ServerAddress serverAddress = ServerAddress.fromString(asdValue.serverIP);
        apiValue.loadWorld(null);
        apiValue.setServerData(asdValue);
        net.minecraft.client.multiplayer.GuiConnecting.lastServerData = asdValue;
        this.connect(serverAddress.getIP(), serverAddress.getPort());
    }

    public static MinecraftClient getClientForConnectionFailure(GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }

    public static MinecraftClient getClientForLoginStart(GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }

    public static NetworkManager setConnection(GuiConnecting guiConnecting, NetworkManager ascValue) {
        return guiConnecting.networkManager = ascValue;
    }

    public static MinecraftClient getClientForLoginHandler(GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }

    public static NetworkManager getConnection(GuiConnecting guiConnecting) {
        return guiConnecting.networkManager;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean legacyConnectionFlow = ClientWorld.getLegacyClientWorldFlowState() != null;
        this.drawDefaultBackground();
        boolean connecting = this.networkManager == null;
        if (connecting) {
            this.drawConnectionStatus("connect.connecting");
        }
        if (!connecting || legacyConnectionFlow) {
            this.drawConnectionStatus("connect.authorizing");
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawConnectionStatus(String statusKey) {
        this.drawCenteredString(this.fontRendererObj, I18n.format(statusKey), this.width / 2, this.height / 2 - 50, 16777215);
    }

    public static MinecraftClient getClientForUnknownHost(GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }

    static {
        net.minecraft.client.multiplayer.GuiConnecting.CONNECTION_ID = new AtomicInteger(0);
        net.minecraft.client.multiplayer.GuiConnecting.logger = LogManager.getLogger();
    }

    public GuiConnecting(GuiScreen asbValue, MinecraftClient apiValue, String text, int i) {
        this.mc = apiValue;
        this.previousGuiScreen = asbValue;
        apiValue.loadWorld(null);
        this.connect(text, i);
    }

    public static Logger getConnectionLogger() {
        return net.minecraft.client.multiplayer.GuiConnecting.logger;
    }
}
