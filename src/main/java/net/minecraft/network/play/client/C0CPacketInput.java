package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C0CPacketInput implements Packet {
    private float forwardSpeed;
    private boolean sneaking;
    private boolean jumping;
    private float strafeSpeed;

    public boolean isJumping() {
        return this.jumping;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.strafeSpeed);
        packetBuffer.writeFloat(this.forwardSpeed);
        byte b0 = 0;
        if (this.jumping) {
            b0 = (byte) (b0 | 1);
        }

        if (this.sneaking) {
            b0 = (byte) (b0 | 2);
        }

        packetBuffer.writeByte(b0);
    }

    public C0CPacketInput(
            float strafeSpeedValue,
            float forwardSpeedValue,
            boolean jumpingValue,
            boolean sneakingValue) {
        this.strafeSpeed = strafeSpeedValue;
        this.forwardSpeed = forwardSpeedValue;
        this.jumping = jumpingValue;
        this.sneaking = sneakingValue;
    }

    public float getForwardSpeed() {
        return this.forwardSpeed;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public C0CPacketInput() {}

    public void func_180766_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processInput(this);
    }

    public float getStrafeSpeed() {
        return this.strafeSpeed;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.strafeSpeed = packetBuffer.readFloat();
        this.forwardSpeed = packetBuffer.readFloat();
        byte b0 = packetBuffer.readByte();

        net.minecraft.network.play.client.C0CPacketInput c0CPacketInput;
        byte b1;
        label29:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                c0CPacketInput = this;
                if ((b0 & 1) > 0) {
                    b1 = 1;
                    break label29;
                }
            } catch (java.io.IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.C0CPacketInput.a(ioexception1));
            }

            b1 = 0;
        }

        label22:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                c0CPacketInput.jumping = (b1 != 0);
                c0CPacketInput = this;
                if ((b0 & 2) > 0) {
                    b1 = 1;
                    break label22;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.client.C0CPacketInput.a(ioexception));
            }

            b1 = 0;
        }

        c0CPacketInput.sneaking = (b1 != 0);
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180766_a((ServerPlayPacketHandler) packetHandler);
    }
}
