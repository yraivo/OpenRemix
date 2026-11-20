package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S31PacketWindowProperty implements Packet {
    private int windowId;
    private int varIndex;
    private int varValue;

    public S31PacketWindowProperty() {}

    public int getVarValue() {
        return this.varValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.varIndex);
        packetBuffer.writeShort(this.varValue);
    }

    public S31PacketWindowProperty(int windowIdValue, int varIndexValue, int varValueValue) {
        this.windowId = windowIdValue;
        this.varIndex = varIndexValue;
        this.varValue = varValueValue;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void func_180733_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleWindowProperty(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180733_a((INetHandlerPlayClient) packetHandler);
    }

    public int getVarIndex() {
        return this.varIndex;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.varIndex = packetBuffer.readShort();
        this.varValue = packetBuffer.readShort();
    }
}
