package net.minecraft.network.status;

import net.minecraft.network.PacketHandler;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;

public interface INetHandlerStatusClient extends PacketHandler {
    void handlePong(S01PacketPong s01PacketPong);

    void handleServerInfo(S00PacketServerInfo s00PacketServerInfo);
}
