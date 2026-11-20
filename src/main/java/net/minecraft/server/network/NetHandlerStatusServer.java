package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer {
    private MinecraftServer server;
    private NetworkManager networkManager;

    public void processServerQuery(C00PacketServerQuery c00PacketServerQuery) {
        this.networkManager.sendPacket(new S00PacketServerInfo(this.server.getServerStatusResponse()));
    }

    public void onDisconnect(ChatComponent chatComponent) {}

    public NetHandlerStatusServer(MinecraftServer awJValue, NetworkManager awKValue) {
        this.server = awJValue;
        this.networkManager = awKValue;
    }

    public void processPing(C01PacketPing c01PacketPing) {
        this.networkManager.sendPacket(new S01PacketPong(c01PacketPing.getClientTime()));
    }
}
