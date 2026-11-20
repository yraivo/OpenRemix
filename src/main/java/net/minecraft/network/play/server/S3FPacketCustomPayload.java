package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S3FPacketCustomPayload implements Packet {
    private PacketBuffer data;

    private String channel;

    public S3FPacketCustomPayload(String text, PacketBuffer bwGValue) {
        this.channel = text;
        this.data = bwGValue;
        if (bwGValue.writerIndex() > 1048576) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.process((INetHandlerPlayClient) packetHandler);
    }

    public void process(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleCustomPayload(this);
    }


    public PacketBuffer getBufferData() {
        return this.data;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.channel);
        packetBuffer.writeBytes(this.data);
    }

    public S3FPacketCustomPayload() {}

    public String getChannelName() {
        return this.channel;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.channel = packetBuffer.readStringFromBuffer(20);
        int[] aint = aint1;
        int i = packetBuffer.readableBytes();
        if (i >= 0 && i <= 1048576) {
            this.data = new PacketBuffer(packetBuffer.readBytes(i));
            if (aint == null) {
                return;
            }
        }

        throw new IOException("Payload may not be larger than 1048576 bytes");
    }
}
