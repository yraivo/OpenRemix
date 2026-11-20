package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.world.WorldServer;

import java.io.IOException;
import java.util.UUID;

public class C18PacketSpectate implements Packet {
    private UUID id;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeUuid(this.id);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179728_a((ServerPlayPacketHandler) packetHandler);
    }

    public C18PacketSpectate() {}

    public C18PacketSpectate(UUID uuid) {
        this.id = uuid;
    }

    public void func_179728_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.handleSpectate(this);
    }

    public Entity getEntity(WorldServer worldServer) {
        return worldServer.getEntityFromUuid(this.id);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.id = packetBuffer.readUuid();
    }
}
