package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class S02PacketChat implements Packet {
    private byte type;
    private ChatComponent chatComponent;

    public S02PacketChat(ChatComponent aHEValue, byte typeValue) {
        this.chatComponent = aHEValue;
        this.type = typeValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.chatComponent);
        packetBuffer.writeByte(this.type);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.chatComponent = packetBuffer.readChatComponent();
        this.type = packetBuffer.readByte();
    }

    public ChatComponent getChatComponent() {
        return this.chatComponent;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleChat(this);
    }

    public S02PacketChat() {}

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public boolean isChat() {
        return this.type == 1 || this.type == 2;
    }

    public byte getType() {
        return this.type;
    }

    public S02PacketChat(ChatComponent chatComponent) {
        this(chatComponent, (byte) 1);
    }
}
