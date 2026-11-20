package net.minecraft.server.network;

import net.minecraft.network.ConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer {
    private MinecraftServer server;

    private NetworkManager networkManager;

    private static UnsupportedOperationException method_1524377ab05941cf1076(
            UnsupportedOperationException unsupportedoperationexception) {
        return unsupportedoperationexception;
    }

    public void processHandshake(C00Handshake c00Handshake) {
        int i = NetHandlerLoginServer.f();
        switch (c00Handshake.getRequestedState()) {
            case LOGIN:
                this.networkManager.setConnectionState(ConnectionState.LOGIN);
                if (c00Handshake.getProtocolVersion() > 47) {
                    ChatComponentText chatComponentText =
                            new ChatComponentText("Outdated server! I'm still on 1.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
                    this.networkManager.closeChannel(chatComponentText);
                    if (i != 0) {
                        break;
                    }
                }

                if (c00Handshake.getProtocolVersion() < 47) {
                    ChatComponentText chatComponentTextValue =
                            new ChatComponentText("Outdated client! Please use 1.8");
                    this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentTextValue));
                    this.networkManager.closeChannel(chatComponentTextValue);
                    if (i != 0) {
                        break;
                    }
                }

                this.networkManager.setPacketHandler(
                        new NetHandlerLoginServer(this.server, this.networkManager));
                if (i != 0) {
                    break;
                }
            case STATUS:
                this.networkManager.setConnectionState(ConnectionState.STATUS);
                this.networkManager.setPacketHandler(
                        new NetHandlerStatusServer(this.server, this.networkManager));
                if (i == 0) {
                    throw new UnsupportedOperationException(
                            "Invalid intention " + c00Handshake.getRequestedState());
                }
                break;
            default:
                throw new UnsupportedOperationException(
                        "Invalid intention " + c00Handshake.getRequestedState());
        }
    }


    public void onDisconnect(ChatComponent chatComponent) {}

    public NetHandlerHandshakeTCP(MinecraftServer awJValue, NetworkManager awKValue) {
        this.server = awJValue;
        this.networkManager = awKValue;
    }
}
