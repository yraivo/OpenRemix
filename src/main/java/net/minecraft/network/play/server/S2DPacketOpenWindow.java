package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class S2DPacketOpenWindow implements Packet {
    private ChatComponent windowTitle;

    private int entityId;
    private int slotCount;
    private int windowId;
    private String inventoryType;

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S2DPacketOpenWindow() {}

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public S2DPacketOpenWindow(int i, String text, ChatComponent chatComponent, int j, int k) {
        this(i, text, chatComponent, j);
        this.entityId = k;
    }

    public S2DPacketOpenWindow(int windowIdValue, String text, ChatComponent bxWValue, int j) {
        this.windowId = windowIdValue;
        this.inventoryType = text;
        this.windowTitle = bxWValue;
        this.slotCount = j;
    }

    public S2DPacketOpenWindow(int i, String text, ChatComponent chatComponent) {
        this(i, text, chatComponent, 0);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.windowId = packetBuffer.readUnsignedByte();
            this.inventoryType = packetBuffer.readStringFromBuffer(32);
            this.windowTitle = packetBuffer.readChatComponent();
            this.slotCount = packetBuffer.readUnsignedByte();
            if (this.inventoryType.equals("EntityHorse")) {
                this.entityId = packetBuffer.readInt();
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public String getGuiId() {
        return this.inventoryType;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleOpenWindow(this);
    }

    public int getSlotCount() {
        return this.slotCount;
    }

    public boolean hasSlots() {
        return this.slotCount > 0;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeByte(this.windowId);
            packetBuffer.writeString(this.inventoryType);
            packetBuffer.writeChatComponent(this.windowTitle);
            packetBuffer.writeByte(this.slotCount);
            if (this.inventoryType.equals("EntityHorse")) {
                packetBuffer.writeInt(this.entityId);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }


    public ChatComponent getWindowTitle() {
        return this.windowTitle;
    }

    public int getWindowId() {
        return this.windowId;
    }

    public int getEntityId() {
        return this.entityId;
    }
}
