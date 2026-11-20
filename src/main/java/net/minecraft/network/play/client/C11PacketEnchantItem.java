package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C11PacketEnchantItem implements Packet {
    private int button;
    private int id;

    public C11PacketEnchantItem(int p171_0, int buttonValue) {
        this.id = p171_0;
        this.button = buttonValue;
    }

    public C11PacketEnchantItem() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.id);
        packetBuffer.writeByte(this.button);
    }

    public int getButton() {
        return this.button;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((ServerPlayPacketHandler) packetHandler);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readByte();
        this.button = packetBuffer.readByte();
    }

    public void processPacket(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processEnchantItem(this);
    }

    public int getId() {
        return this.id;
    }
}
