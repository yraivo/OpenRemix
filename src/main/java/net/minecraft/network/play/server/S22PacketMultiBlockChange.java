package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import java.io.IOException;

public class S22PacketMultiBlockChange implements Packet {
    private S22PacketMultiBlockChangeBlockUpdateData[] changedBlocks;
    private ChunkCoordIntPair chunkPosCoord;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.chunkPosCoord = new ChunkCoordIntPair(packetBuffer.readInt(), packetBuffer.readInt());
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.changedBlocks =
                new S22PacketMultiBlockChangeBlockUpdateData[packetBuffer.readVarIntFromBuffer()];
        int[] aint = aint1;
        int i = 0;

        while (i < this.changedBlocks.length) {
            this.changedBlocks[i] =
                    new S22PacketMultiBlockChangeBlockUpdateData(
                            this,
                            packetBuffer.readShort(),
                            (BlockState)
                                    Block.BLOCK_STATE_IDS.getByValue(
                                            packetBuffer.readVarIntFromBuffer()));
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180729_a((INetHandlerPlayClient) packetHandler);
    }

    public S22PacketMultiBlockChange(int i, short[] ashort, Chunk chunk) {
        this.chunkPosCoord = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.changedBlocks = new S22PacketMultiBlockChangeBlockUpdateData[i];
        int j = 0;
        int[] aint = aint1;

        while (j < this.changedBlocks.length) {
            this.changedBlocks[j] =
                    new S22PacketMultiBlockChangeBlockUpdateData(this, ashort[j], chunk);
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public void func_180729_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleMultiBlockChange(this);
    }

    static ChunkCoordIntPair access$000(S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        return s22PacketMultiBlockChange.chunkPosCoord;
    }

    public S22PacketMultiBlockChange() {}

    public S22PacketMultiBlockChangeBlockUpdateData[] getChangedBlocks() {
        return this.changedBlocks;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeInt(this.chunkPosCoord.chunkXPos);
        packetBuffer.writeInt(this.chunkPosCoord.chunkZPos);
        packetBuffer.writeVarIntToBuffer(this.changedBlocks.length);
        S22PacketMultiBlockChangeBlockUpdateData[] bxOValue = this.changedBlocks;
        int length = bxOValue.length;
        int j = 0;
        int[] aint = aint1;

        while (j < length) {
            S22PacketMultiBlockChangeBlockUpdateData s22PacketMultiBlockChangeBlockUpdateData =
                    bxOValue[j];
            packetBuffer.writeShort(s22PacketMultiBlockChangeBlockUpdateData.getChunkPosCrammed());
            packetBuffer.writeVarIntToBuffer(
                    Block.BLOCK_STATE_IDS.get(
                            s22PacketMultiBlockChangeBlockUpdateData.getBlockState()));
            j++;
            if (aint != null) {
                break;
            }
        }
    }
}
