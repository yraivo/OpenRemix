package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

import java.io.IOException;

public class S1EPacketRemoveEntityEffect implements Packet {
    private int effectId;
    private int entityId;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.effectId);
    }

    public S1EPacketRemoveEntityEffect() {}

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public int getEffectId() {
        return this.effectId;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleRemoveEntityEffect(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public S1EPacketRemoveEntityEffect(int entityIdValue, PotionEffect potionEffect) {
        this.entityId = entityIdValue;
        this.effectId = potionEffect.getPotionID();
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.effectId = packetBuffer.readUnsignedByte();
    }
}
