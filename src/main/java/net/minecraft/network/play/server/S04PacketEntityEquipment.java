package net.minecraft.network.play.server;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S04PacketEntityEquipment implements Packet {
    private ItemStack itemStack;
    private int entityID;
    private int equipmentSlot;

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getEquipmentSlot() {
        return this.equipmentSlot;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.equipmentSlot = packetBuffer.readShort();
        this.itemStack = packetBuffer.readItemStackFromBuffer();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeShort(this.equipmentSlot);
        packetBuffer.writeItemStackToBuffer(this.itemStack);
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityEquipment(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S04PacketEntityEquipment() {}

    public int getEntityID() {
        return this.entityID;
    }

    public S04PacketEntityEquipment(
            int entityIDValue, int equipmentSlotValue, ItemStack itemStack) {
        this.entityID = entityIDValue;
        this.equipmentSlot = equipmentSlotValue;
        this.itemStack = itemStack == null ? null : itemStack.copy();
    }
}
