package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S05PacketSpawnPosition implements Packet {
    private BlockPos spawnBlockPos;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180752_a((INetHandlerPlayClient) packetHandler);
    }

    public BlockPos getSpawnPos() {
        return this.spawnBlockPos;
    }

    public void func_180752_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSpawnPosition(this);
    }

    public S05PacketSpawnPosition() {}

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.spawnBlockPos = packetBuffer.readBlockPos();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.spawnBlockPos);
    }

    public S05PacketSpawnPosition(BlockPos blockPos) {
        this.spawnBlockPos = blockPos;
    }
}
