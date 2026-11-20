package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S03PacketTimeUpdate implements Packet {
    private long totalWorldTime;
    private long worldTime;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.totalWorldTime = packetBuffer.readLong();
        this.worldTime = packetBuffer.readLong();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public long getTotalWorldTime() {
        return this.totalWorldTime;
    }

    public S03PacketTimeUpdate() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.totalWorldTime);
        packetBuffer.writeLong(this.worldTime);
    }

    public long getWorldTime() {
        return this.worldTime;
    }

    public S03PacketTimeUpdate(long totalWorldTimeValue, long j, boolean flag) {
        this.totalWorldTime = totalWorldTimeValue;
        this.worldTime = j;
        if (!flag) {
            this.worldTime = -this.worldTime;

            if (this.worldTime == 0L) {

                this.worldTime = -1L;
            }
        }
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleTimeUpdate(this);
    }
}
