package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S25PacketBlockBreakAnim implements Packet {
    private BlockPos position;
    private int progress;
    private int breakerId;

    public int getProgress() {
        return this.progress;
    }

    public S25PacketBlockBreakAnim(int breakerIdValue, BlockPos blockPos, int progressValue) {
        this.breakerId = breakerIdValue;
        this.position = blockPos;
        this.progress = progressValue;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.breakerId = packetBuffer.readVarIntFromBuffer();
        this.position = packetBuffer.readBlockPos();
        this.progress = packetBuffer.readUnsignedByte();
    }

    public S25PacketBlockBreakAnim() {}

    public BlockPos getPosition() {
        return this.position;
    }

    public int getBreakerId() {
        return this.breakerId;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.breakerId);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.progress);
    }

    public void handle(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleBlockBreakAnim(this);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.handle((INetHandlerPlayClient) packetHandler);
    }
}
