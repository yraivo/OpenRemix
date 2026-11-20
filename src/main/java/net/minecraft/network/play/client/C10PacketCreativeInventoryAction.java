package net.minecraft.network.play.client;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C10PacketCreativeInventoryAction implements Packet {
    private int slotId;
    private ItemStack stack;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeItemStackToBuffer(this.stack);
    }

    public C10PacketCreativeInventoryAction(int slotIdValue, ItemStack itemStack) {
        this.slotId = slotIdValue;
        this.stack = itemStack != null ? itemStack.copy() : null;
    }

    public void func_180767_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processCreativeInventoryAction(this);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public C10PacketCreativeInventoryAction() {}

    public void processPacket(PacketHandler packetHandler) {
        this.func_180767_a((ServerPlayPacketHandler) packetHandler);
    }

    public int getSlotId() {
        return this.slotId;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.slotId = packetBuffer.readShort();
        this.stack = packetBuffer.readItemStackFromBuffer();
    }
}
