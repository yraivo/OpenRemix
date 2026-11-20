package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class HeldItemChangePacket implements Packet {
    private int slotId;

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processHeldItemChange(this);
    }

    public HeldItemChangePacket(int slotIdValue) {
        this.slotId = slotIdValue;
    }

    public HeldItemChangePacket() {}

    public int getSlotId() {
        return this.slotId;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
    }
}
