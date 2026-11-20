package net.minecraft.network.play.server;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S14PacketEntityS16PacketEntityLook extends S14PacketEntity {
    public S14PacketEntityS16PacketEntityLook() {
        this.includesRotation = true;
    }

    public void processPacket(PacketHandler packetHandler) {
        super.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        super.readPacketData(packetBuffer);
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }

    public S14PacketEntityS16PacketEntityLook(int i, byte b0, byte b1, boolean flag) {
        super(i);
        this.yaw = b0;
        this.pitch = b1;
        this.includesRotation = true;
        this.onGround = flag;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        super.writePacketData(packetBuffer);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeBoolean(this.onGround);
    }
}
