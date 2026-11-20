package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S24PacketBlockAction implements Packet {
    private Block block;
    private int pitch;
    private int instrument;
    private BlockPos blockPosition;

    public S24PacketBlockAction() {}

    public S24PacketBlockAction(
            BlockPos blockPos, Block bmhValue, int instrumentValue, int pitchValue) {
        this.blockPosition = blockPos;
        this.instrument = instrumentValue;
        this.pitch = pitchValue;
        this.block = bmhValue;
    }

    public void func_180726_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleBlockAction(this);
    }

    public int getData2() {
        return this.pitch;
    }

    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeByte(this.instrument);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeVarIntToBuffer(net.minecraft.block.Block.getIdFromBlock(this.block) & 4095);
    }

    public Block getBlockType() {
        return this.block;
    }

    public int getData1() {
        return this.instrument;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.instrument = packetBuffer.readUnsignedByte();
        this.pitch = packetBuffer.readUnsignedByte();
        this.block = net.minecraft.block.Block.getBlockById(packetBuffer.readVarIntFromBuffer() & 4095);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180726_a((INetHandlerPlayClient) packetHandler);
    }
}
