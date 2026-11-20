package net.minecraft.network.play.client;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class PlayerPositionLookPacket extends PlayerMovementPacket {
    public void processPacket(PacketHandler packetHandler) {
        super.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public PlayerPositionLookPacket(
            double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.hasRotation = true;
        this.hasPosition = true;
    }

    public PlayerPositionLookPacket() {
        this.hasPosition = true;
        this.hasRotation = true;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
        super.writePacketData(packetBuffer);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
        super.readPacketData(packetBuffer);
    }
}
