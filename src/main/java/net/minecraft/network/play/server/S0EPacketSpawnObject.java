package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S0EPacketSpawnObject implements Packet {
    private int objectData;
    private int pitch;
    private int speedY;
    private int entityId;
    private int z;
    private int speedX;
    private int y;
    private int yaw;
    private int type;
    private int speedZ;
    private int x;

    public int getPitch() {
        return this.pitch;
    }

    public int getSpeedZ() {
        return this.speedZ;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Checked exception carried by the original JVM handler.
            packetBuffer.writeVarIntToBuffer(this.entityId);
            packetBuffer.writeByte(this.type);
            packetBuffer.writeInt(this.x);
            packetBuffer.writeInt(this.y);
            packetBuffer.writeInt(this.z);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeInt(this.objectData);
            if (this.objectData > 0) {
                packetBuffer.writeShort(this.speedX);
                packetBuffer.writeShort(this.speedY);
                packetBuffer.writeShort(this.speedZ);
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
        }
    }

    public void setObjectData(int i) {
        this.objectData = i;
    }

    public int getSpeedY() {
        return this.speedY;
    }

    public int getObjectData() {
        return this.objectData;
    }

    public void setSpeedX(int speedXValue) {
        this.speedX = speedXValue;
    }

    public int getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public S0EPacketSpawnObject() {}

    public int getSpeedX() {
        return this.speedX;
    }

    public void setZ(int i) {
        this.z = i;
    }

    public int getY() {
        return this.y;
    }

    public int getEntityID() {
        return this.entityId;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public int getZ() {
        return this.z;
    }

    public void setSpeedY(int speedYValue) {
        this.speedY = speedYValue;
    }

    public int getYaw() {
        return this.yaw;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Checked exception carried by the original JVM handler.
            this.entityId = packetBuffer.readVarIntFromBuffer();
            this.type = packetBuffer.readByte();
            this.x = packetBuffer.readInt();
            this.y = packetBuffer.readInt();
            this.z = packetBuffer.readInt();
            this.pitch = packetBuffer.readByte();
            this.yaw = packetBuffer.readByte();
            this.objectData = packetBuffer.readInt();
            if (this.objectData > 0) {
                this.speedX = packetBuffer.readShort();
                this.speedY = packetBuffer.readShort();
                this.speedZ = packetBuffer.readShort();
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
        }
    }

    public void setY(int i) {
        this.y = i;
    }

    public void setSpeedZ(int speedZValue) {
        this.speedZ = speedZValue;
    }

    public S0EPacketSpawnObject(Entity entity, int p171_1) {
        this(entity, p171_1, 0);
    }

    public void setX(int i) {
        this.x = i;
    }

    public S0EPacketSpawnObject(Entity entity, int typeValue, int j) {
        this.entityId = entity.getEntityId();
        this.x = MathHelper.floor(entity.posX * 32.0);
        this.y = MathHelper.floor(entity.posY * 32.0);
        this.z = MathHelper.floor(entity.posZ * 32.0);
        this.pitch = MathHelper.floor(entity.rotationPitch * 256.0F / 360.0F);
        this.yaw = MathHelper.floor(entity.rotationYaw * 256.0F / 360.0F);
        this.type = typeValue;
        this.objectData = j;
        if (j > 0) {
            double motionX = entity.motionX;
            double motionY = entity.motionY;
            double motionZ = entity.motionZ;
            double d3 = 3.9;
            if (motionX < -d3) {
                motionX = -d3;
            }

            if (motionY < -d3) {
                motionY = -d3;
            }

            if (motionZ < -d3) {
                motionZ = -d3;
            }

            if (motionX > d3) {
                motionX = d3;
            }

            if (motionY > d3) {
                motionY = d3;
            }

            if (motionZ > d3) {
                motionZ = d3;
            }

            this.speedX = (int) (motionX * 8000.0);
            this.speedY = (int) (motionY * 8000.0);
            this.speedZ = (int) (motionZ * 8000.0);
        }
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnObject(this);
    }
}
