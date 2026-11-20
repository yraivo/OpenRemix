package net.minecraft.network.play.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S35PacketUpdateTileEntity implements Packet {
    private BlockPos blockPos;
    private int metadata;
    private CompoundTag nbt;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        this.metadata = packetBuffer.readUnsignedByte();
        this.nbt = packetBuffer.readNBTTagCompoundFromBuffer();
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPos);
        packetBuffer.writeByte((byte) this.metadata);
        packetBuffer.writeNBTTagCompoundToBuffer(this.nbt);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180725_a((INetHandlerPlayClient) packetHandler);
    }

    public CompoundTag getNbtCompound() {
        return this.nbt;
    }

    public int getTileEntityType() {
        return this.metadata;
    }

    public S35PacketUpdateTileEntity() {}

    public S35PacketUpdateTileEntity(BlockPos blockPos, int metadataValue, CompoundTag aYWValue) {
        this.blockPos = blockPos;
        this.metadata = metadataValue;
        this.nbt = aYWValue;
    }

    public BlockPos getPos() {
        return this.blockPos;
    }

    public void func_180725_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleUpdateTileEntity(this);
    }
}
