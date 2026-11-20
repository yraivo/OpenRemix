package net.minecraft.network.login.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class S00PacketDisconnect implements Packet {
    private ChatComponent reason;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180772_a((INetHandlerLoginClient) packetHandler);
    }

    public ChatComponent getReason() {
        return this.reason;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.reason = packetBuffer.readChatComponent();
    }

    public void func_180772_a(INetHandlerLoginClient iNetHandlerLoginClient) {
        iNetHandlerLoginClient.handleDisconnect(this);
    }

    public S00PacketDisconnect() {}

    public S00PacketDisconnect(ChatComponent aHEValue) {
        this.reason = aHEValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.reason);
    }
}
