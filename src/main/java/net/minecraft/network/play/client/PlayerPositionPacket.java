package net.minecraft.network.play.client;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class PlayerPositionPacket extends PlayerMovementPacket {
    public PlayerPositionPacket(double x, double y, double z, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
        this.hasPosition = true;
    }

    public void processPacket(PacketHandler packetHandler) {
        super.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        super.readPacketData(packetBuffer);
    }

    public PlayerPositionPacket() {
        this.hasPosition = true;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        super.writePacketData(packetBuffer);
    }
}
