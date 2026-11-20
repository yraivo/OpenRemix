package net.minecraft.network.login.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.login.INetHandlerLoginClient;

import java.io.IOException;

public class S03PacketEnableCompression implements Packet {
    private int compressionTreshold;
    private static Entity[] b;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.compressionTreshold);
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179732_a((INetHandlerLoginClient) packetHandler);
    }

    static {
        if (method_da2ac5c94686ee11e9e6() != null) {
            b(new Entity[3]);
        }
    }

    public void func_179732_a(INetHandlerLoginClient iNetHandlerLoginClient) {
        iNetHandlerLoginClient.handleEnableCompression(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.compressionTreshold = packetBuffer.readVarIntFromBuffer();
    }

    public S03PacketEnableCompression() {}

    public int getCompressionTreshold() {
        return this.compressionTreshold;
    }

    public S03PacketEnableCompression(int compressionTresholdValue) {
        this.compressionTreshold = compressionTresholdValue;
    }
}
