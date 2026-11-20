package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S0DPacketCollectItem implements Packet {
    private int entityId;
    private int collectedItemEntityId;

    public int getEntityID() {
        return this.entityId;
    }

    public S0DPacketCollectItem(int collectedItemEntityIdValue, int entityIdValue) {
        this.collectedItemEntityId = collectedItemEntityIdValue;
        this.entityId = entityIdValue;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.collectedItemEntityId = packetBuffer.readVarIntFromBuffer();
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }

    public S0DPacketCollectItem() {}

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleCollectItem(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.collectedItemEntityId);
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public int getCollectedItemEntityID() {
        return this.collectedItemEntityId;
    }
}
