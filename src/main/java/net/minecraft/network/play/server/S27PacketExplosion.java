package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class S27PacketExplosion implements Packet {
    private float strength;
    private float motionX;
    private float motionZ;
    private List<BlockPos> affectedBlockPositions;
    private float motionY;
    private double posY;
    private double posX;
    private double posZ;

    public double getY() {
        return this.posY;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.posX = packetBuffer.readFloat();
        this.posY = packetBuffer.readFloat();
        this.posZ = packetBuffer.readFloat();
        int[] aint = aint1;
        this.strength = packetBuffer.readFloat();
        int i = packetBuffer.readInt();
        this.affectedBlockPositions = Lists.newArrayListWithCapacity(i);
        int j = (int) this.posX;
        int k = (int) this.posY;
        int l = (int) this.posZ;
        int i1 = 0;

        while (i1 < i) {
            int j1 = packetBuffer.readByte() + j;
            int k1 = packetBuffer.readByte() + k;
            int l1 = packetBuffer.readByte() + l;
            this.affectedBlockPositions.add(new BlockPos(j1, k1, l1));
            i1++;
            if (aint != null) {
                break;
            }
        }

        this.motionX = packetBuffer.readFloat();
        this.motionY = packetBuffer.readFloat();
        this.motionZ = packetBuffer.readFloat();
    }

    public S27PacketExplosion() {}

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleExplosion(this);
    }

    public float setMotionZ(float f) {
        return this.motionZ = f;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat((float) this.posX);
        packetBuffer.writeFloat((float) this.posY);
        packetBuffer.writeFloat((float) this.posZ);
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeFloat(this.strength);
        packetBuffer.writeInt(this.affectedBlockPositions.size());
        int posXValue = (int) this.posX;
        int[] aint = aint1;
        int j = (int) this.posY;
        int k = (int) this.posZ;

        for (BlockPos blockPos : this.affectedBlockPositions) {
            int l = blockPos.getX() - posXValue;
            int i1 = blockPos.getY() - j;
            int j1 = blockPos.getZ() - k;
            packetBuffer.writeByte(l);
            packetBuffer.writeByte(i1);
            packetBuffer.writeByte(j1);
            if (aint != null) {
                break;
            }
        }

        packetBuffer.writeFloat(this.motionX);
        packetBuffer.writeFloat(this.motionY);
        packetBuffer.writeFloat(this.motionZ);
    }

    public S27PacketExplosion(
            double posXValue,
            double posYValue,
            double posZValue,
            float strengthValue,
            List list,
            Vec3 vec3) {
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        this.strength = strengthValue;
        this.affectedBlockPositions = com.google.common.collect.Lists.newArrayList(list);
        if (vec3 != null) {
            this.motionX = (float) vec3.x;
            this.motionY = (float) vec3.y;
            this.motionZ = (float) vec3.z;
        }
    }

    public float setMotionY(float f) {
        return this.motionY = f;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public float getStrength() {
        return this.strength;
    }

    public float getMotionZ() {
        return this.motionZ;
    }

    public float getMotionX() {
        return this.motionX;
    }

    public float getMotionY() {
        return this.motionY;
    }

    public double getZ() {
        return this.posZ;
    }

    public double getX() {
        return this.posX;
    }

    public float setMotionX(float f) {
        return this.motionX = f;
    }

    public List<BlockPos> getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }
}
