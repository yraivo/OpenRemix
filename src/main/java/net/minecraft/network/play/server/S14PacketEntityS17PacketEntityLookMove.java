package net.minecraft.network.play.server;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S14PacketEntityS17PacketEntityLookMove extends S14PacketEntity {
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        super.readPacketData(packetBuffer);
        this.posX = packetBuffer.readByte();
        this.posY = packetBuffer.readByte();
        this.posZ = packetBuffer.readByte();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        super.writePacketData(packetBuffer);
        packetBuffer.writeByte(this.posX);
        packetBuffer.writeByte(this.posY);
        packetBuffer.writeByte(this.posZ);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeBoolean(this.onGround);
    }

    public S14PacketEntityS17PacketEntityLookMove() {
        this.includesRotation = true;
    }

    public void processPacket(PacketHandler packetHandler) {
        super.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S14PacketEntityS17PacketEntityLookMove(
            int i, byte b0, byte b1, byte b2, byte b3, byte b4, boolean flag) {
        super(i);
        this.posX = b0;
        this.posY = b1;
        this.posZ = b2;
        this.yaw = b3;
        this.pitch = b4;
        this.onGround = flag;
        this.includesRotation = true;
    }
}
