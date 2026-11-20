package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S2EPacketCloseWindow implements Packet {
    private int windowId;

    public S2EPacketCloseWindow() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
    }

    public S2EPacketCloseWindow(int windowIdValue) {
        this.windowId = windowIdValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180731_a((INetHandlerPlayClient) packetHandler);
    }

    public void func_180731_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleCloseWindow(this);
    }
}
