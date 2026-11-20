package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S0BPacketAnimation implements Packet {
    private int type;
    private int entityId;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readUnsignedByte();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
    }

    public void func_180723_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleAnimation(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180723_a((INetHandlerPlayClient) packetHandler);
    }

    public int getEntityID() {
        return this.entityId;
    }

    public S0BPacketAnimation(Entity entity, int typeValue) {
        this.entityId = entity.getEntityId();
        this.type = typeValue;
    }

    public int getAnimationType() {
        return this.type;
    }

    public S0BPacketAnimation() {}
}
