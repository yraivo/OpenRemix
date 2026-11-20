package net.minecraft.network.status;

import net.minecraft.network.PacketHandler;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;

public interface INetHandlerStatusServer extends PacketHandler {
    void processPing(C01PacketPing c01PacketPing);

    void processServerQuery(C00PacketServerQuery c00PacketServerQuery);
}
