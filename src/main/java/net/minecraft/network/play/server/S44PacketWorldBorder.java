package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

import java.io.IOException;

public class S44PacketWorldBorder implements Packet {
    private double diameter;
    private int size;
    private S44PacketWorldBorderAction action;
    private int warningTime;
    private int warningDistance;
    private double centerX;
    private double targetSize;
    private long timeUntilTarget;
    private double centerZ;

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179787_a((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.action =
                (S44PacketWorldBorderAction)
                        packetBuffer.readEnumValue(S44PacketWorldBorderAction.class);
        int[] aint = aint1;
        switch (this.action) {
            case SET_SIZE:
                this.targetSize = packetBuffer.readDouble();
                if (aint == null) {
                    break;
                }
            case LERP_SIZE:
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                if (aint == null) {
                    break;
                }
            case SET_CENTER:
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                if (aint == null) {
                    break;
                }
            case SET_WARNING_BLOCKS:
                this.warningDistance = packetBuffer.readVarIntFromBuffer();
                if (aint == null) {
                    break;
                }
            case SET_WARNING_TIME:
                this.warningTime = packetBuffer.readVarIntFromBuffer();
                if (aint == null) {
                    break;
                }
            case INITIALIZE:
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                this.size = packetBuffer.readVarIntFromBuffer();
                this.warningDistance = packetBuffer.readVarIntFromBuffer();
                this.warningTime = packetBuffer.readVarIntFromBuffer();
        }
    }

    public void func_179787_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleWorldBorder(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeEnumValue(this.action);
        int[] aint = aint1;
        switch (this.action) {
            case SET_SIZE:
                packetBuffer.writeDouble(this.targetSize);
                if (aint == null) {
                    break;
                }
            case LERP_SIZE:
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                if (aint == null) {
                    break;
                }
            case SET_CENTER:
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                if (aint == null) {
                    break;
                }
            case SET_WARNING_BLOCKS:
                packetBuffer.writeVarIntToBuffer(this.warningDistance);
                if (aint == null) {
                    break;
                }
            case SET_WARNING_TIME:
                packetBuffer.writeVarIntToBuffer(this.warningTime);
                if (aint == null) {
                    break;
                }
            case INITIALIZE:
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                packetBuffer.writeVarIntToBuffer(this.size);
                packetBuffer.writeVarIntToBuffer(this.warningDistance);
                packetBuffer.writeVarIntToBuffer(this.warningTime);
        }
    }

    public S44PacketWorldBorder(WorldBorder worldBorder, S44PacketWorldBorderAction bxCValue) {
        this.action = bxCValue;
        this.centerX = worldBorder.getCenterX();
        this.centerZ = worldBorder.getCenterZ();
        this.diameter = worldBorder.getDiameter();
        this.targetSize = worldBorder.getTargetSize();
        this.timeUntilTarget = worldBorder.getTimeUntilTarget();
        this.size = worldBorder.getSize();
        this.warningDistance = worldBorder.getWarningDistance();
        this.warningTime = worldBorder.getWarningTime();
    }

    public S44PacketWorldBorder() {}

    public void applyToBorder(WorldBorder worldBorder) {
        int[] aint = S14PacketEntity.getLegacyPacketFlowMarker();
        switch (this.action) {
            case SET_SIZE:
                worldBorder.setTransition(this.targetSize);
                if (aint == null) {
                    break;
                }
            case LERP_SIZE:
                worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                if (aint == null) {
                    break;
                }
            case SET_CENTER:
                worldBorder.setCenter(this.centerX, this.centerZ);
                if (aint == null) {
                    break;
                }
            case SET_WARNING_BLOCKS:
                worldBorder.setWarningDistance(this.warningDistance);
                if (aint == null) {
                    break;
                }
            case SET_WARNING_TIME:
                worldBorder.setWarningTime(this.warningTime);
                if (aint == null) {
                    break;
                }
            case INITIALIZE:
                {
                    worldBorder.setCenter(this.centerX, this.centerZ);

                    if (this.timeUntilTarget > 0L) {
                        worldBorder.setTransition(
                                this.diameter, this.targetSize, this.timeUntilTarget);
                        if (!(aint == null)) {

                            worldBorder.setTransition(this.targetSize);
                        }
                    } else {

                        worldBorder.setTransition(this.targetSize);
                    }
                }

                worldBorder.setSize(this.size);
                worldBorder.setWarningDistance(this.warningDistance);
                worldBorder.setWarningTime(this.warningTime);
        }
    }
}
