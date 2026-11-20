package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S1BPacketEntityAttach implements Packet {
    private int vehicleEntityId;
    private int leash;
    private int entityId;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.vehicleEntityId = packetBuffer.readInt();
        this.leash = packetBuffer.readUnsignedByte();
    }

    public int getLeash() {
        return this.leash;
    }

    public int getVehicleEntityId() {
        return this.vehicleEntityId;
    }

    public S1BPacketEntityAttach() {}

    public S1BPacketEntityAttach(int leashValue, Entity entity, Entity entityValue) {
        this.leash = leashValue;
        this.entityId = entity.getEntityId();
        this.vehicleEntityId = entityValue != null ? entityValue.getEntityId() : -1;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityAttach(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeInt(this.vehicleEntityId);
        packetBuffer.writeByte(this.leash);
    }
}
