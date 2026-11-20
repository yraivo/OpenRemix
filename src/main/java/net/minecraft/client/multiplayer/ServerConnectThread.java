package net.minecraft.client.multiplayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;
import remix.ui.screen.OfflineUuidResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerConnectThread extends Thread {
    GuiConnecting screen;
    private static String[] packedTextFragments;

    String host;

    int port;

    public ServerConnectThread(GuiConnecting aOxValue, String text, String textValue, int i) {
        super(text);
        this.screen = aOxValue;
        this.host = textValue;
        this.port = i;
    }

    private static UnknownHostException method_9b1198ee8d9161954b70(
            UnknownHostException unknownhostexception) {
        return unknownhostexception;
    }

    static {
        net.minecraft.client.multiplayer.ServerConnectThread.packedTextFragments = new String[4];
        net.minecraft.client.multiplayer.ServerConnectThread.packedTextFragments[0] = new String(":");
        net.minecraft.client.multiplayer.ServerConnectThread.packedTextFragments[1] = new String("");
    }

    @Override
    public void run() {
        InetAddress resolvedAddress = null;
        try {
            if (GuiConnecting.isConnectionCancelled(this.screen)) {
                return;
            }
            resolvedAddress = InetAddress.getByName(this.host);
            NetworkManager connection = NetworkManager.createNetworkManagerAndConnect(resolvedAddress, this.port, true);
            GuiConnecting.setConnection(this.screen, connection);
            this.sendLoginHandshake();
        } catch (UnknownHostException failure) {
            if (!GuiConnecting.isConnectionCancelled(this.screen)) {
                GuiConnecting.getConnectionLogger().error("Couldn't connect to server", failure);
                this.showConnectionFailure(GuiConnecting.getClientForUnknownHost(this.screen), "Unknown host");
            }
        } catch (Exception failure) {
            if (!GuiConnecting.isConnectionCancelled(this.screen)) {
                GuiConnecting.getConnectionLogger().error("Couldn't connect to server", failure);
                String reason = failure.toString();
                if (resolvedAddress != null) {
                    reason = reason.replaceAll(resolvedAddress.toString() + ":" + this.port, "");
                }
                this.showConnectionFailure(GuiConnecting.getClientForConnectionFailure(this.screen), reason);
            }
        }
    }

    private void sendLoginHandshake() {
        NetworkManager network = GuiConnecting.getConnection(this.screen);
        network.setPacketHandler(new NetHandlerLoginClient(network,
                GuiConnecting.getClientForLoginHandler(this.screen), GuiConnecting.getPreviousScreen(this.screen)));
        C00Handshake handshake = new C00Handshake(47, this.host, this.port, ConnectionState.LOGIN);
        OfflineUuidResolver.applyBungeeSpoof(handshake);
        GuiConnecting.getConnection(this.screen).sendPacket(handshake);
        GuiConnecting.getConnection(this.screen).sendPacket(new C00PacketLoginStart(
                GuiConnecting.getClientForLoginStart(this.screen).getSession().getProfile()));
    }

    private void showConnectionFailure(MinecraftClient client, String reason) {
        client.displayGuiScreen(new GuiDisconnected(GuiConnecting.getPreviousScreen(this.screen), "connect.failed",
                new ChatComponentTranslation("disconnect.genericReason", reason)));
    }
}
