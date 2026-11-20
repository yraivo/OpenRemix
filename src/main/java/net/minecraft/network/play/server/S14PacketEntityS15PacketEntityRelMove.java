package net.minecraft.network.play.server;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S14PacketEntityS15PacketEntityRelMove extends S14PacketEntity {
    public S14PacketEntityS15PacketEntityRelMove(int i, byte b0, byte b1, byte b2, boolean flag) {
        super(i);
        this.posX = b0;
        this.posY = b1;
        this.posZ = b2;
        this.onGround = flag;
    }

    public void processPacket(PacketHandler packetHandler) {
        super.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        super.readPacketData(packetBuffer);
        this.posX = packetBuffer.readByte();
        this.posY = packetBuffer.readByte();
        this.posZ = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }

    public S14PacketEntityS15PacketEntityRelMove() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        super.writePacketData(packetBuffer);
        packetBuffer.writeByte(this.posX);
        packetBuffer.writeByte(this.posY);
        packetBuffer.writeByte(this.posZ);
        packetBuffer.writeBoolean(this.onGround);
    }
}
