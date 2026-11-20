package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class S40PacketDisconnect implements Packet {
    private ChatComponent reason;

    public ChatComponent getReason() {
        return this.reason;
    }

    public S40PacketDisconnect(ChatComponent aHEValue) {
        this.reason = aHEValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.reason);
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleDisconnect(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.reason = packetBuffer.readChatComponent();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public S40PacketDisconnect() {}
}
