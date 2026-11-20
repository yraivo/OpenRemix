package net.minecraft.network.play.server;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.List;

public class S30PacketWindowItems implements Packet {
    private int windowId;
    private ItemStack[] itemStacks;

    public ItemStack[] getItemStacks() {
        return this.itemStacks;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.windowId = packetBuffer.readUnsignedByte();
        short short1 = packetBuffer.readShort();
        this.itemStacks = new ItemStack[short1];
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        int i = 0;
        int[] aint = aint1;

        while (i < short1) {
            this.itemStacks[i] = packetBuffer.readItemStackFromBuffer();
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeByte(this.windowId);
        int[] aint = aint1;
        packetBuffer.writeShort(this.itemStacks.length);

        for (ItemStack itemStack : this.itemStacks) {
            packetBuffer.writeItemStackToBuffer(itemStack);
            if (aint != null) {
                break;
            }
        }
    }

    public void func_180732_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleWindowItems(this);
    }

    public S30PacketWindowItems() {}

    public S30PacketWindowItems(int i, List list) {
        super();
        int[] initialFlowMarker = S14PacketEntity.getLegacyPacketFlowMarker();

        int[] aint1 = initialFlowMarker;

        this.windowId = i;
        int[] aint = aint1;
        this.itemStacks = new ItemStack[list.size()];
        int j = 0;

        while (j < this.itemStacks.length) {
            ItemStack itemStack = (ItemStack) list.get(j);
            this.itemStacks[j] = itemStack == null ? null : itemStack.copy();
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180732_a((INetHandlerPlayClient) packetHandler);
    }
}
