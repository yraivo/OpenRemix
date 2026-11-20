package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S18PacketEntityTeleport implements Packet {
    private int entityId;
    private byte yaw;
    private int posZ;
    private int posY;
    private boolean onGround;
    private byte pitch;
    private int posX;

    public int getY() {
        return this.posY;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityTeleport(this);
    }

    public int getX() {
        return this.posX;
    }

    public S18PacketEntityTeleport(Entity entity) {
        this.entityId = entity.getEntityId();
        this.posX = MathHelper.floor(entity.posX * 32.0);
        this.posY = MathHelper.floor(entity.posY * 32.0);
        this.posZ = MathHelper.floor(entity.posZ * 32.0);
        this.yaw = (byte) (entity.rotationYaw * 256.0F / 360.0F);
        this.pitch = (byte) (entity.rotationPitch * 256.0F / 360.0F);
        this.onGround = entity.onGround;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeInt(this.posX);
        packetBuffer.writeInt(this.posY);
        packetBuffer.writeInt(this.posZ);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeBoolean(this.onGround);
    }

    public boolean getOnGround() {
        return this.onGround;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S18PacketEntityTeleport() {}

    public int getEntityId() {
        return this.entityId;
    }

    public byte getYaw() {
        return this.yaw;
    }

    public int getZ() {
        return this.posZ;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.posX = packetBuffer.readInt();
        this.posY = packetBuffer.readInt();
        this.posZ = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }

    public S18PacketEntityTeleport(
            int entityIdValue,
            int posXValue,
            int posYValue,
            int posZValue,
            byte bvyValue,
            byte bwBValue,
            boolean onGroundValue) {
        this.entityId = entityIdValue;
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        this.yaw = bvyValue;
        this.pitch = bwBValue;
        this.onGround = onGroundValue;
    }
}
