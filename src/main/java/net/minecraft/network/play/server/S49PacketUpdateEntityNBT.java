package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S49PacketUpdateEntityNBT implements Packet {
    private CompoundTag tagCompound;
    private int entityId;

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeNBTTagCompoundToBuffer(this.tagCompound);
    }

    public S49PacketUpdateEntityNBT() {}

    public void func_179762_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityNBT(this);
    }

    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.tagCompound = packetBuffer.readNBTTagCompoundFromBuffer();
    }

    public S49PacketUpdateEntityNBT(int entityIdValue, CompoundTag bvDValue) {
        super();
        this.entityId = entityIdValue;
        this.tagCompound = bvDValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179762_a((INetHandlerPlayClient) packetHandler);
    }

    public CompoundTag getTagCompound() {
        return this.tagCompound;
    }
}
