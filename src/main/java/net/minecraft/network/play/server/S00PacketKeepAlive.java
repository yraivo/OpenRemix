package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S00PacketKeepAlive implements Packet {
    private int id;

    public S00PacketKeepAlive() {}

    public S00PacketKeepAlive(int i) {
        this.id = i;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleKeepAlive(this);
    }

    public int getKeepAliveId() {
        return this.id;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.id);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readVarIntFromBuffer();
    }
}
