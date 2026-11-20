package net.minecraft.network.status.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.status.INetHandlerStatusServer;

import java.io.IOException;

public class C00PacketServerQuery implements Packet {
    public void processPacket(PacketHandler packetHandler) {
        this.func_180775_a((INetHandlerStatusServer) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {}

    public void func_180775_a(INetHandlerStatusServer iNetHandlerStatusServer) {
        iNetHandlerStatusServer.processServerQuery(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {}
}
