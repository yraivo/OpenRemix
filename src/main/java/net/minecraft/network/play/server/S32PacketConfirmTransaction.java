package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S32PacketConfirmTransaction implements Packet {
    private boolean field_148893_c;
    private int windowId;
    private short actionNumber;

    public void func_180730_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleConfirmTransaction(this);
    }

    public S32PacketConfirmTransaction() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeBoolean(this.field_148893_c);
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        this.actionNumber = packetBuffer.readShort();
        this.field_148893_c = packetBuffer.readBoolean();
    }

    public boolean isAccepted() {
        return this.field_148893_c;
    }

    public S32PacketConfirmTransaction(int windowIdValue, short bxcValue, boolean p171_2) {
        this.windowId = windowIdValue;
        this.actionNumber = bxcValue;
        this.field_148893_c = p171_2;
    }

    public short getActionNumber() {
        return this.actionNumber;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180730_a((INetHandlerPlayClient) packetHandler);
    }
}
