package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C00PacketKeepAlive implements Packet {
    private int key;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.key = packetBuffer.readVarIntFromBuffer();
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processKeepAlive(this);
    }

    public C00PacketKeepAlive() {}

    public int getKey() {
        return this.key;
    }

    public C00PacketKeepAlive(int keyValue) {
        this.key = keyValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.key);
    }
}
