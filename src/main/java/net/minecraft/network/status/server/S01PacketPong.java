package net.minecraft.network.status.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.status.INetHandlerStatusClient;

import java.io.IOException;

public class S01PacketPong implements Packet {
    private long clientTime;
    private static String[] packedTextFragments;
    private static String b;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerStatusClient) packetHandler);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_c60693f457aa70be7160() != null) {
            b(packedTextFragments[0]);
        }
    }

    public S01PacketPong() {}

    public static void b(String text) {
        b = text;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.network.status.server.S01PacketPong.packedTextFragments[0] = new String("gIE1vc");
    }

    // endregion

    public S01PacketPong(long clientTimeValue) {
        this.clientTime = clientTimeValue;
    }

    // region String decoding support

    // endregion

    public static String method_c60693f457aa70be7160() {
        return b;
    }

    public void processPacket(INetHandlerStatusClient iNetHandlerStatusClient) {
        iNetHandlerStatusClient.handlePong(this);
    }
}
