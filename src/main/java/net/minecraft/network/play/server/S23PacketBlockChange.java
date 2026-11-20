package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

public class S23PacketBlockChange implements Packet {
    private BlockState blockState;
    private BlockPos blockPosition;

    public S23PacketBlockChange() {}

    public BlockState getBlockState() {
        return this.blockState;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.blockState =
                (BlockState) Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarIntFromBuffer());
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180727_a((INetHandlerPlayClient) packetHandler);
    }

    public S23PacketBlockChange(World world, BlockPos blockPos) {
        this.blockPosition = blockPos;
        this.blockState = world.getBlockState(blockPos);
    }

    public void func_180727_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleBlockChange(this);
    }

    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }
}
