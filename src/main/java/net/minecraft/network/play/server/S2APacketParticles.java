package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

import java.io.IOException;

public class S2APacketParticles implements Packet {
    private EnumParticleTypes particleType;
    private float xOffset;
    private float particleSpeed;
    private float zCoord;
    private float zOffset;
    private float xCoord;
    private float yOffset;
    private int[] particleArguments;
    private boolean longDistance;
    private int particleCount;
    private float yCoord;

    public float getYOffset() {
        return this.yOffset;
    }

    public S2APacketParticles(
            EnumParticleTypes bxUValue,
            boolean longDistanceValue,
            float xCoordValue,
            float yCoordValue,
            float zCoordValue,
            float xOffsetValue,
            float yOffsetValue,
            float zOffsetValue,
            float particleSpeedValue,
            int particleCountValue,
            int... aint) {
        this.particleType = bxUValue;
        this.longDistance = longDistanceValue;
        this.xCoord = xCoordValue;
        this.yCoord = yCoordValue;
        this.zCoord = zCoordValue;
        this.xOffset = xOffsetValue;
        this.yOffset = yOffsetValue;
        this.zOffset = zOffsetValue;
        this.particleSpeed = particleSpeedValue;
        this.particleCount = particleCountValue;
        this.particleArguments = aint;
    }

    public float getParticleSpeed() {
        return this.particleSpeed;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.particleType = EnumParticleTypes.getParticleFromId(packetBuffer.readInt());
        int[] aint = aint1;

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (this.particleType == null) {
                this.particleType = EnumParticleTypes.BARRIER;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        this.longDistance = packetBuffer.readBoolean();
        this.xCoord = packetBuffer.readFloat();
        this.yCoord = packetBuffer.readFloat();
        this.zCoord = packetBuffer.readFloat();
        this.xOffset = packetBuffer.readFloat();
        this.yOffset = packetBuffer.readFloat();
        this.zOffset = packetBuffer.readFloat();
        this.particleSpeed = packetBuffer.readFloat();
        this.particleCount = packetBuffer.readInt();
        int argumentCount = this.particleType.getArgumentCount();
        this.particleArguments = new int[argumentCount];
        int j = 0;

        while (j < argumentCount) {
            this.particleArguments[j] = packetBuffer.readVarIntFromBuffer();
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public double getYCoordinate() {
        return this.yCoord;
    }

    public S2APacketParticles() {}

    public boolean isLongDistance() {
        return this.longDistance;
    }

    public int[] getParticleArgs() {
        return this.particleArguments;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180740_a((INetHandlerPlayClient) packetHandler);
    }

    public double getZCoordinate() {
        return this.zCoord;
    }

    public int getParticleCount() {
        return this.particleCount;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.particleType.getParticleID());
        packetBuffer.writeBoolean(this.longDistance);
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeFloat(this.xCoord);
        packetBuffer.writeFloat(this.yCoord);
        packetBuffer.writeFloat(this.zCoord);
        packetBuffer.writeFloat(this.xOffset);
        packetBuffer.writeFloat(this.yOffset);
        packetBuffer.writeFloat(this.zOffset);
        int[] aint = aint1;
        packetBuffer.writeFloat(this.particleSpeed);
        packetBuffer.writeInt(this.particleCount);
        int argumentCount = this.particleType.getArgumentCount();
        int j = 0;

        while (j < argumentCount) {
            packetBuffer.writeVarIntToBuffer(this.particleArguments[j]);
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public double getXCoordinate() {
        return this.xCoord;
    }

    public void func_180740_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleParticles(this);
    }

    public EnumParticleTypes getParticleType() {
        return this.particleType;
    }

    public float getZOffset() {
        return this.zOffset;
    }

    public float getXOffset() {
        return this.xOffset;
    }
}
