package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C0APacketAnimation implements Packet {
    public void processPacket(PacketHandler packetHandler) {
        this.func_179721_a((ServerPlayPacketHandler) packetHandler);
    }

    public void func_179721_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.handleAnimation(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {}
}
