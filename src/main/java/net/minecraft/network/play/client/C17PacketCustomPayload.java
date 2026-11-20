package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C17PacketCustomPayload implements Packet {

    private String channel;
    private static String[] packedTextFragments;
    private PacketBuffer data;

    public C17PacketCustomPayload() {}

    public String getChannelName() {
        return this.channel;
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processVanilla250Packet(this);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public C17PacketCustomPayload(String text, PacketBuffer bwGValue) {
        this.channel = text;
        this.data = bwGValue;
        if (bwGValue.writerIndex() > 32767) {
            throw new IllegalArgumentException(
                    "Payload may not be larger than 32767 bytes ("
                            + bwGValue.writerIndex()
                            + packedTextFragments[0]);
        }
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }

    static {
        net.minecraft.network.play.client.C17PacketCustomPayload.packedTextFragments = new String[2];
        net.minecraft.network.play.client.C17PacketCustomPayload.packedTextFragments[0] = new String(")");
    }

    public PacketBuffer getBufferData() {
        return this.data;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.channel = packetBuffer.readStringFromBuffer(20);
        int k = PlayerMovementPacket.cM();
        int j = packetBuffer.readableBytes();
        int i = k;
        if (j >= 0 && j <= 32767) {
            this.data = new PacketBuffer(packetBuffer.readBytes(j));
            if (i == 0) {
                return;
            }
        }

        throw new IOException("Payload may not be larger than 32767 bytes");
    }
}
