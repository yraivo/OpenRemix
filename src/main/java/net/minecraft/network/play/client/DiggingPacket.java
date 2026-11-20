package net.minecraft.network.play.client;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;

import java.io.IOException;

public class DiggingPacket implements Packet {
    private DiggingAction status;
    private Facing facing;
    private BlockPos position;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.status = (DiggingAction) packetBuffer.readEnumValue(DiggingAction.class);
        this.position = packetBuffer.readBlockPos();
        this.facing = Facing.getFront(packetBuffer.readUnsignedByte());
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180763_a((ServerPlayPacketHandler) packetHandler);
    }

    public Facing getFacing() {
        return this.facing;
    }

    public DiggingPacket(DiggingAction bxaValue, BlockPos blockPos, Facing aAcValue) {
        this.status = bxaValue;
        this.position = blockPos;
        this.facing = aAcValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.status);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.facing.getIndex());
    }

    public DiggingPacket() {}

    public BlockPos getPosition() {
        return this.position;
    }

    public DiggingAction getStatus() {
        return this.status;
    }

    public void func_180763_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processPlayerDigging(this);
    }
}
