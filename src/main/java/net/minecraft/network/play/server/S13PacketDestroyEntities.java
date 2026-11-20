package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S13PacketDestroyEntities implements Packet {
    private int[] entityIDs;

    public int[] getEntityIDs() {
        return this.entityIDs;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeVarIntToBuffer(this.entityIDs.length);
        int[] aint = aint1;
        int i = 0;

        while (i < this.entityIDs.length) {
            packetBuffer.writeVarIntToBuffer(this.entityIDs[i]);
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleDestroyEntities(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.entityIDs = new int[packetBuffer.readVarIntFromBuffer()];
        int[] aint = aint1;
        int i = 0;

        while (i < this.entityIDs.length) {
            this.entityIDs[i] = packetBuffer.readVarIntFromBuffer();
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S13PacketDestroyEntities(int... aint) {
        this.entityIDs = aint;
    }

    public S13PacketDestroyEntities() {}
}
