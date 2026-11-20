package net.minecraft.network;

import java.io.IOException;

public interface Packet {
    void processPacket(PacketHandler packetHandler);

    void writePacketData(PacketBuffer packetBuffer) throws IOException;

    void readPacketData(PacketBuffer packetBuffer) throws IOException;
}
