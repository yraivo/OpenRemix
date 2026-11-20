package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S12PacketEntityVelocity implements Packet {
    private int motionX;
    private int entityID;
    private int motionY;
    private int motionZ;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.motionX = packetBuffer.readShort();
        this.motionY = packetBuffer.readShort();
        this.motionZ = packetBuffer.readShort();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void setMotionY(int motionYValue) {
        this.motionY = motionYValue;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityVelocity(this);
    }

    public void setMotionZ(int motionZValue) {
        this.motionZ = motionZValue;
    }

    public S12PacketEntityVelocity(Entity entity) {
        this(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ);
    }

    public int getEntityID() {
        return this.entityID;
    }

    public void setMotionX(int motionXValue) {
        this.motionX = motionXValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeShort(this.motionX);
        packetBuffer.writeShort(this.motionY);
        packetBuffer.writeShort(this.motionZ);
    }

    public int getMotionZ() {
        return this.motionZ;
    }

    public int getMotionY() {
        return this.motionY;
    }

    public S12PacketEntityVelocity() {}

    public int getMotionX() {
        return this.motionX;
    }

    public S12PacketEntityVelocity(int entityIDValue, double p171_1, double p171_2, double p171_3) {
        this.entityID = entityIDValue;
        double d3 = 3.9;
        if (p171_1 < -d3) {
            p171_1 = -d3;
        }

        if (p171_2 < -d3) {
            p171_2 = -d3;
        }

        if (p171_3 < -d3) {
            p171_3 = -d3;
        }

        if (p171_1 > d3) {
            p171_1 = d3;
        }

        if (p171_2 > d3) {
            p171_2 = d3;
        }

        if (p171_3 > d3) {
            p171_3 = d3;
        }

        this.motionX = (int) (p171_1 * 8000.0);
        this.motionY = (int) (p171_2 * 8000.0);
        this.motionZ = (int) (p171_3 * 8000.0);
    }
}
