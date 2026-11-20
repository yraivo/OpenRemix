package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class PlayerMovementPacket implements Packet {
    protected boolean hasPosition;
    protected double y;
    protected double z;
    protected boolean hasRotation;
    protected boolean onGround;
    private static int mu;
    protected float pitch;
    protected float yaw;
    protected double x;

    public void setX(double x) {
        this.x = x;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        net.minecraft.network.PacketBuffer packetBufferValue;
        byte b0;
        label16:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                packetBufferValue = packetBuffer;
                if (this.onGround) {
                    b0 = 1;
                    break label16;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.PlayerMovementPacket.a(ioexception));
            }

            b0 = 0;
        }

        packetBufferValue.writeByte(b0);
    }

    public static int cM() {
        return mu;
    }

    public boolean hasPosition() {
        return this.hasPosition;
    }

    // region Static initialization and string tables
    static {
        if (cM() != 0) {
            b(36);
        }
    }

    // endregion

    public static int cK() {
        int i = cM();
        return i == 0 ? 119 : 0;
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processPlayer(this);
    }

    public void setZ(double z) {
        this.z = z;
    }

    public PlayerMovementPacket() {}

    public double getX() {
        return this.x;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public boolean hasRotation() {
        return this.hasRotation;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public double getY() {
        return this.y;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        net.minecraft.network.play.client.PlayerMovementPacket playerMovementPacket;
        byte b0;
        label16:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                playerMovementPacket = this;
                if (packetBuffer.readUnsignedByte() != 0) {
                    b0 = 1;
                    break label16;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.PlayerMovementPacket.a(ioexception));
            }

            b0 = 0;
        }

        playerMovementPacket.onGround = (b0 != 0);
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setHasRotation(boolean hasRotation) {
        this.hasRotation = hasRotation;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public static void b(int i) {
        mu = i;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setY(double y) {
        this.y = y;
    }

    public PlayerMovementPacket(boolean onGround) {
        this.onGround = onGround;
    }

    public void setHasPosition(boolean hasPosition) {
        this.hasPosition = hasPosition;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getZ() {
        return this.z;
    }
}
