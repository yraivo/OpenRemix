package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S09PacketHeldItemChange implements Packet {
    private int heldItemHotbarIndex;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180746_a((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.heldItemHotbarIndex = packetBuffer.readByte();
    }

    public S09PacketHeldItemChange(int heldItemHotbarIndexValue) {
        this.heldItemHotbarIndex = heldItemHotbarIndexValue;
    }

    public int getHeldItemHotbarIndex() {
        return this.heldItemHotbarIndex;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.heldItemHotbarIndex);
    }

    public void func_180746_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleHeldItemChange(this);
    }

    public S09PacketHeldItemChange() {}
}
