package net.minecraft.network.play.client;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class PlayerLookPacket extends PlayerMovementPacket {
    public void processPacket(PacketHandler packetHandler) {
        super.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
        super.writePacketData(packetBuffer);
    }

    public PlayerLookPacket() {
        this.hasRotation = true;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
        super.readPacketData(packetBuffer);
    }

    public PlayerLookPacket(float yaw, float pitch, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.hasRotation = true;
    }
}
