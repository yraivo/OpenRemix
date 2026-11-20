package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S36PacketSignEditorOpen implements Packet {
    private BlockPos signPosition;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.signPosition = packetBuffer.readBlockPos();
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public BlockPos getSignPosition() {
        return this.signPosition;
    }

    public S36PacketSignEditorOpen() {}

    public S36PacketSignEditorOpen(BlockPos blockPos) {
        this.signPosition = blockPos;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleSignEditorOpen(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.signPosition);
    }
}
