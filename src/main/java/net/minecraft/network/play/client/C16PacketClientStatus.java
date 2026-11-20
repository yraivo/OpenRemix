package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C16PacketClientStatus implements Packet {
    private C16PacketClientStatusEnumState status;

    public C16PacketClientStatus(C16PacketClientStatusEnumState bwEValue) {
        this.status = bwEValue;
    }

    public void func_180758_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processClientStatus(this);
    }

    public C16PacketClientStatus() {}

    public C16PacketClientStatusEnumState getStatus() {
        return this.status;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.status =
                (C16PacketClientStatusEnumState)
                        packetBuffer.readEnumValue(C16PacketClientStatusEnumState.class);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180758_a((ServerPlayPacketHandler) packetHandler);
    }
}
