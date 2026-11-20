package net.minecraft.network.play.server;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;
import java.util.List;

public class S0FPacketSpawnMob implements Packet {
    private int x;
    private byte headPitch;
    private int velocityY;
    private int z;
    private List watcher;
    private int y;
    private int entityId;
    private byte pitch;
    private byte yaw;
    private int type;
    private int velocityX;
    private DataWatcher field_149043_l;
    private int velocityZ;

    public int getZ() {
        return this.z;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeByte(this.type & 0xFF);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeByte(this.headPitch);
        packetBuffer.writeShort(this.velocityX);
        packetBuffer.writeShort(this.velocityY);
        packetBuffer.writeShort(this.velocityZ);
        this.field_149043_l.writeTo(packetBuffer);
    }

    public byte getPitch() {
        return this.pitch;
    }

    public int getY() {
        return this.y;
    }

    public void func_180721_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnMob(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180721_a((INetHandlerPlayClient) packetHandler);
    }

    public int getVelocityY() {
        return this.velocityY;
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getHeadPitch() {
        return this.headPitch;
    }

    public S0FPacketSpawnMob() {}

    public int getEntityID() {
        return this.entityId;
    }

    public int getX() {
        return this.x;
    }

    public int getVelocityZ() {
        return this.velocityZ;
    }

    public List getWatchableObjects() {
        if (this.watcher == null) {
            this.watcher = this.field_149043_l.getAllWatched();
        }

        return this.watcher;
    }

    public S0FPacketSpawnMob(LivingEntity livingEntity) {
        super();
        int[] initialFlowMarker = S14PacketEntity.getLegacyPacketFlowMarker();

        int[] aint1 = initialFlowMarker;

        this.entityId = livingEntity.getEntityId();
        int[] aint = aint1;
        this.type = (byte) EntityList.getEntityID(livingEntity);
        this.x = MathHelper.floor(livingEntity.posX * 32.0);
        this.y = MathHelper.floor(livingEntity.posY * 32.0);
        this.z = MathHelper.floor(livingEntity.posZ * 32.0);
        this.yaw = (byte) (livingEntity.rotationYaw * 256.0F / 360.0F);
        this.pitch = (byte) (livingEntity.rotationPitch * 256.0F / 360.0F);
        this.headPitch = (byte) (livingEntity.rotationYawHead * 256.0F / 360.0F);
        double d0 = 3.9;
        double motionX = livingEntity.motionX;
        double motionY = livingEntity.motionY;
        double motionZ = livingEntity.motionZ;
        if (motionX < -d0) {
            motionX = -d0;
        }

        if (motionY < -d0) {
            motionY = -d0;
        }

        if (motionZ < -d0) {
            motionZ = -d0;
        }

        if (motionX > d0) {
            motionX = d0;
        }

        if (motionY > d0) {
            motionY = d0;
        }

        if (motionZ > d0) {
            motionZ = d0;
        }

        this.velocityX = (int) (motionX * 8000.0);
        this.velocityY = (int) (motionY * 8000.0);
        this.velocityZ = (int) (motionZ * 8000.0);
        this.field_149043_l = livingEntity.getDataWatcher();
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.type = packetBuffer.readByte() & 255;
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.headPitch = packetBuffer.readByte();
        this.velocityX = packetBuffer.readShort();
        this.velocityY = packetBuffer.readShort();
        this.velocityZ = packetBuffer.readShort();
        this.watcher =
                net.minecraft.entity.DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }

    public int getVelocityX() {
        return this.velocityX;
    }

    public int getEntityType() {
        return this.type;
    }
}
