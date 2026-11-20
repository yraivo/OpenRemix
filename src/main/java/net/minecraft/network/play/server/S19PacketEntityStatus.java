package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S19PacketEntityStatus implements Packet {
    private int entityId;
    private byte logicOpcode;

    public void func_180736_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityStatus(this);
    }

    public S19PacketEntityStatus(Entity entity, byte logicOpcodeValue) {
        this.entityId = entity.getEntityId();
        this.logicOpcode = logicOpcodeValue;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        packetBuffer.writeByte(this.logicOpcode);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        this.logicOpcode = packetBuffer.readByte();
    }

    @Override
    public void processPacket(PacketHandler packetHandler) {
        this.func_180736_a((INetHandlerPlayClient) packetHandler);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public S19PacketEntityStatus() {}

    public byte getOpCode() {
        return this.logicOpcode;
    }
}
