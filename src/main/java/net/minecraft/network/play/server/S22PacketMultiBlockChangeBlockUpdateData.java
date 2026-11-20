package net.minecraft.network.play.server;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChangeBlockUpdateData {
    S22PacketMultiBlockChange field_180093_a;
    private short chunkPosCrammed;
    private BlockState blockState;

    public short getChunkPosCrammed() {
        return this.chunkPosCrammed;
    }

    public BlockPos getPos() {
        return new BlockPos(
                S22PacketMultiBlockChange.access$000(this.field_180093_a)
                        .getBlock(
                                this.chunkPosCrammed >> 12 & 15,
                                this.chunkPosCrammed & 255,
                                this.chunkPosCrammed >> 8 & 15));
    }

    public S22PacketMultiBlockChangeBlockUpdateData(
            S22PacketMultiBlockChange bxQValue, short short1, BlockState blockState) {
        this.field_180093_a = bxQValue;
        this.chunkPosCrammed = short1;
        this.blockState = blockState;
    }

    public S22PacketMultiBlockChangeBlockUpdateData(
            S22PacketMultiBlockChange bxQValue, short short1, Chunk chunk) {
        this.field_180093_a = bxQValue;
        this.chunkPosCrammed = short1;
        this.blockState = chunk.getBlockState(this.getPos());
    }

    public BlockState getBlockState() {
        return this.blockState;
    }
}
