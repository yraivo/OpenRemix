package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S3APacketTabComplete implements Packet {
    private String[] matches;

    public String[] getMatches() {
        return this.matches;
    }

    public S3APacketTabComplete(String[] textArray) {
        this.matches = textArray;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.matches.length);
        String[] astring = this.matches;
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        int length = astring.length;
        int j = 0;
        int[] aint = aint1;

        while (j < length) {
            String text = astring[j];
            packetBuffer.writeString(text);
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public S3APacketTabComplete() {}

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.matches = new String[packetBuffer.readVarIntFromBuffer()];
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        int i = 0;
        int[] aint = aint1;

        while (i < this.matches.length) {
            this.matches[i] = packetBuffer.readStringFromBuffer(32767);
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleTabComplete(this);
    }
}
