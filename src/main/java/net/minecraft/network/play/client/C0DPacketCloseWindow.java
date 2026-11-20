package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C0DPacketCloseWindow implements Packet {
    private int windowId;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
    }

    public void func_180759_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processCloseWindow(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180759_a((ServerPlayPacketHandler) packetHandler);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public C0DPacketCloseWindow() {}

    public C0DPacketCloseWindow(int windowIdValue) {
        this.windowId = windowIdValue;
    }
}
