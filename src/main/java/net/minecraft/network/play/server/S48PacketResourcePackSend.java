package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S48PacketResourcePackSend implements Packet {
    private String hash;
    private static String c;
    private String url;
    private static String[] packedTextFragments;

    public S48PacketResourcePackSend(String text, String textValue) {
        this.url = text;
        this.hash = textValue;
        if (textValue.length() > 40) {
            throw new IllegalArgumentException(c + textValue.length() + packedTextFragments[0]);
        }
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleResourcePack(this);
    }

    public String getURL() {
        return this.url;
    }

    // region String decoding support

    // endregion

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public String getHash() {
        return this.hash;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.url);
        packetBuffer.writeString(this.hash);
    }

    public S48PacketResourcePackSend() {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.url = packetBuffer.readStringFromBuffer(32767);
        this.hash = packetBuffer.readStringFromBuffer(40);
    }

    static {
        net.minecraft.network.play.server.S48PacketResourcePackSend.packedTextFragments = new String[2];
        net.minecraft.network.play.server.S48PacketResourcePackSend.packedTextFragments[0] = new String(")");
        net.minecraft.network.play.server.S48PacketResourcePackSend.packedTextFragments[1] =
                new String("\u001b\u0013}.C(;s\u0006a)C-'=\u0015.n\u000e 0sF>jC6) R");
        c = "Hash is too long (max 40, was ";
    }
}
