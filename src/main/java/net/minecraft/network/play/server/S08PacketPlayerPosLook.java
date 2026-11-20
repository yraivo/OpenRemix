package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.Set;

public class S08PacketPlayerPosLook implements Packet {
    public float yaw;
    private double x;
    public float pitch;
    private double z;
    private double y;
    private Set<S08PacketPlayerPosLookEnumFlags> relativeFlags;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
        this.relativeFlags =
                S08PacketPlayerPosLookEnumFlags.unpackFlags(packetBuffer.readUnsignedByte());
    }

    public double getZ() {
        return this.z;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180718_a((INetHandlerPlayClient) packetHandler);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
        packetBuffer.writeByte(S08PacketPlayerPosLookEnumFlags.packFlags(this.relativeFlags));
    }

    public S08PacketPlayerPosLook(
            double p171_0,
            double p171_1,
            double p171_2,
            float yawValue,
            float pitchValue,
            Set bwNValue) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.yaw = yawValue;
        this.pitch = pitchValue;
        this.relativeFlags = bwNValue;
    }

    public double getX() {
        return this.x;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getY() {
        return this.y;
    }

    public S08PacketPlayerPosLook() {}

    public float getYaw() {
        return this.yaw;
    }

    public void func_180718_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handlePlayerPosLook(this);
    }

    public Set getRelativeFlags() {
        return this.relativeFlags;
    }
}
