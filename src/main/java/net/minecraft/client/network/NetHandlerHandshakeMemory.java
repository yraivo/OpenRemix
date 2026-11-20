package net.minecraft.client.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.ChatComponent;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer {
    private MinecraftServer mcServer;
    private NetworkManager networkManager;

    public void processHandshake(C00Handshake c00Handshake) {
        this.networkManager.setConnectionState(c00Handshake.getRequestedState());
        this.networkManager.setPacketHandler(
                new NetHandlerLoginServer(this.mcServer, this.networkManager));
    }

    public void onDisconnect(ChatComponent chatComponent) {}

    public NetHandlerHandshakeMemory(MinecraftServer awJValue, NetworkManager awKValue) {
        this.mcServer = awJValue;
        this.networkManager = awKValue;
    }
}
