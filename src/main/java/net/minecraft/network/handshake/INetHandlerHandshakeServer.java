package net.minecraft.network.handshake;

import net.minecraft.network.PacketHandler;
import net.minecraft.network.handshake.client.C00Handshake;

public interface INetHandlerHandshakeServer extends PacketHandler {
    void processHandshake(C00Handshake c00Handshake);
}
