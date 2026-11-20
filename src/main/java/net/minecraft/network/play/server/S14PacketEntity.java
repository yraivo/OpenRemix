package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S14PacketEntity implements Packet {
    protected boolean onGround;
    private static int[] legacyPacketFlowMarker;
    protected byte posZ;
    public byte yaw;
    protected byte posY;
    private static String sT;
    protected boolean includesRotation;

    protected int entityId;
    public byte pitch;
    protected byte posX;

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    @Override
    public String toString() {
        return sT + super.toString();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }

    public byte getPitchByte() {
        return this.pitch;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityMovement(this);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    static {
        if (getLegacyPacketFlowMarker() != null) {
            setLegacyPacketFlowMarker(new int[4]);
        }
        sT = "Entity_";
    }

    public byte getYawByte() {
        return this.yaw;
    }

    public byte getDeltaZ() {
        return this.posZ;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public byte getDeltaX() {
        return this.posX;
    }

    public S14PacketEntity(int entityIdValue) {
        this.entityId = entityIdValue;
    }

    // region String decoding support

    // endregion

    public byte getDeltaY() {
        return this.posY;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }

    public static void setLegacyPacketFlowMarker(int[] aint) {
        net.minecraft.network.play.server.S14PacketEntity.legacyPacketFlowMarker = aint;
    }

    public S14PacketEntity() {}

    public boolean getOnGround() {
        return this.onGround;
    }

    public static int[] getLegacyPacketFlowMarker() {
        return net.minecraft.network.play.server.S14PacketEntity.legacyPacketFlowMarker;
    }

    // region String decoding support

    // endregion

    public boolean includesRotation() {
        return this.includesRotation;
    }
}
