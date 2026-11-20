package net.minecraft.network.play.server;

import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityPaintingEnumArt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;

import java.io.IOException;

public class S10PacketSpawnPainting implements Packet {
    private int entityID;
    private Facing facing;
    private BlockPos position;
    private String title;

    public int getEntityID() {
        return this.entityID;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public S10PacketSpawnPainting() {}

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityID = packetBuffer.readVarIntFromBuffer();
        this.title = packetBuffer.readStringFromBuffer(EntityPaintingEnumArt.field_180001_A);
        this.position = packetBuffer.readBlockPos();
        this.facing = Facing.getHorizontal(packetBuffer.readUnsignedByte());
    }

    public S10PacketSpawnPainting(EntityPainting entityPainting) {
        this.entityID = entityPainting.getEntityId();
        this.position = entityPainting.getHangingPosition();
        this.facing = entityPainting.facingDirection;
        this.title = entityPainting.art.title;
    }

    public Facing getFacing() {
        return this.facing;
    }

    public String getTitle() {
        return this.title;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityID);
        packetBuffer.writeString(this.title);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getHorizontalIndex());
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180722_a((INetHandlerPlayClient) packetHandler);
    }

    public void func_180722_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnPainting(this);
    }
}
