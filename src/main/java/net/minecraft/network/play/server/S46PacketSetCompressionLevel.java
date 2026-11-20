package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S46PacketSetCompressionLevel implements Packet {
    private int field_179761_a;

    public int getCompressionThreshold() {
        return this.field_179761_a;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.field_179761_a);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_179761_a = packetBuffer.readVarIntFromBuffer();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179759_a((INetHandlerPlayClient) packetHandler);
    }

    public void func_179759_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSetCompressionLevel(this);
    }
}
