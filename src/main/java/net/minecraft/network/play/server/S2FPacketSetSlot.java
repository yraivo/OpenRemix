package net.minecraft.network.play.server;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S2FPacketSetSlot implements Packet {
    private int slot;
    private int windowId;
    private ItemStack item;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slot);
        packetBuffer.writeItemStackToBuffer(this.item);
    }

    public int getSlotIndex() {
        return this.slot;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slot = packetBuffer.readShort();
        this.item = packetBuffer.readItemStackFromBuffer();
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public S2FPacketSetSlot() {}

    public S2FPacketSetSlot(int windowIdValue, int slotValue, ItemStack itemStack) {
        this.windowId = windowIdValue;
        this.slot = slotValue;
        this.item = itemStack == null ? null : itemStack.copy();
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSetSlot(this);
    }

    public int getWindowId() {
        return this.windowId;
    }
}
