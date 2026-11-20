package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S2CPacketSpawnGlobalEntity implements Packet {
    private int type;
    private int x;
    private int z;
    private int entityId;
    private int y;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readByte();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
    }

    public S2CPacketSpawnGlobalEntity(Entity entity) {
        this.entityId = entity.getEntityId();
        this.x = MathHelper.floor(entity.posX * 32.0);
        this.y = MathHelper.floor(entity.posY * 32.0);
        this.z = MathHelper.floor(entity.posZ * 32.0);
        if (entity instanceof EntityLightningBolt) {
            this.type = 1;
        }
    }

    public int getX() {
        return this.x;
    }

    public void func_180720_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnGlobalEntity(this);
    }

    public int getZ() {
        return this.z;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public S2CPacketSpawnGlobalEntity() {}

    public int getY() {
        return this.y;
    }

    public int getType() {
        return this.type;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180720_a((INetHandlerPlayClient) packetHandler);
    }
}
