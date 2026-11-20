package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S19PacketEntityHeadLook implements Packet {
    private int entityId;
    private byte yaw;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.yaw = packetBuffer.readByte();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.yaw);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180745_a((INetHandlerPlayClient) packetHandler);
    }

    public byte getYaw() {
        return this.yaw;
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public S19PacketEntityHeadLook(Entity entity, byte yawValue) {
        this.entityId = entity.getEntityId();
        this.yaw = yawValue;
    }

    public S19PacketEntityHeadLook() {}

    public void func_180745_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityHeadLook(this);
    }
}
