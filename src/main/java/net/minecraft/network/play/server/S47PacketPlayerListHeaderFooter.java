package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ChatComponent;

import java.io.IOException;

public class S47PacketPlayerListHeaderFooter implements Packet {
    private ChatComponent footer;
    private ChatComponent header;

    public S47PacketPlayerListHeaderFooter(ChatComponent aHEValue) {
        this.header = aHEValue;
    }

    public void func_179699_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handlePlayerListHeaderFooter(this);
    }

    public ChatComponent getHeader() {
        return this.header;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179699_a((INetHandlerPlayClient) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.header = packetBuffer.readChatComponent();
        this.footer = packetBuffer.readChatComponent();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeChatComponent(this.header);
        packetBuffer.writeChatComponent(this.footer);
    }

    public ChatComponent getFooter() {
        return this.footer;
    }

    public S47PacketPlayerListHeaderFooter() {}
}
