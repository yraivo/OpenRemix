package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C01PacketChatMessage implements Packet {
    private String message;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.message);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180757_a((ServerPlayPacketHandler) packetHandler);
    }

    public C01PacketChatMessage() {}

    public void func_180757_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processChatMessage(this);
    }

    public C01PacketChatMessage(String text) {
        if (text.length() > 100) {
            text = text.substring(0, 100);
        }

        this.message = text;
    }

    public String getMessage() {
        return this.message;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.message = packetBuffer.readStringFromBuffer(100);
    }
}
