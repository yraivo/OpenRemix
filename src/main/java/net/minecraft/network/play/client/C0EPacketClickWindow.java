package net.minecraft.network.play.client;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C0EPacketClickWindow implements Packet {
    private int windowId;
    private int mode;
    private int slotId;
    private int usedButton;
    private short actionNumber;
    private ItemStack clickedItem;

    public int getWindowId() {
        return this.windowId;
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processClickWindow(this);
    }

    public C0EPacketClickWindow() {}

    public short getActionNumber() {
        return this.actionNumber;
    }

    public ItemStack getClickedItem() {
        return this.clickedItem;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.windowId);
        packetBuffer.writeShort(this.slotId);
        packetBuffer.writeByte(this.usedButton);
        packetBuffer.writeShort(this.actionNumber);
        packetBuffer.writeByte(this.mode);
        packetBuffer.writeItemStackToBuffer(this.clickedItem);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public C0EPacketClickWindow(
            int windowIdValue,
            int slotIdValue,
            int usedButtonValue,
            int modeValue,
            ItemStack itemStack,
            short bsxValue) {
        this.windowId = windowIdValue;
        this.slotId = slotIdValue;
        this.usedButton = usedButtonValue;
        this.clickedItem = itemStack != null ? itemStack.copy() : null;
        this.actionNumber = bsxValue;
        this.mode = modeValue;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public int getUsedButton() {
        return this.usedButton;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readByte();
        this.slotId = packetBuffer.readShort();
        this.usedButton = packetBuffer.readByte();
        this.actionNumber = packetBuffer.readShort();
        this.mode = packetBuffer.readByte();
        this.clickedItem = packetBuffer.readItemStackFromBuffer();
    }

    public int getMode() {
        return this.mode;
    }
}
