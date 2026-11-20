package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S43PacketCamera implements Packet {
    public int entityId;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179779_a((INetHandlerPlayClient) packetHandler);
    }

    public S43PacketCamera() {}

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public void func_179779_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleCamera(this);
    }

    public S43PacketCamera(Entity entity) {
        this.entityId = entity.getEntityId();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
    }
}
