package net.minecraft.network.play.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

public class S0APacketUseBed implements Packet {
    private int playerID;
    private BlockPos bedPos;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180744_a((INetHandlerPlayClient) packetHandler);
    }

    public S0APacketUseBed(PlayerEntity playerEntity, BlockPos blockPos) {
        this.playerID = playerEntity.getEntityId();
        this.bedPos = blockPos;
    }

    public BlockPos getBedPosition() {
        return this.bedPos;
    }

    public void func_180744_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleUseBed(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.playerID = packetBuffer.readVarIntFromBuffer();
        this.bedPos = packetBuffer.readBlockPos();
    }

    public S0APacketUseBed() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.playerID);
        packetBuffer.writeBlockPos(this.bedPos);
    }

    public PlayerEntity getPlayer(World world) {
        return (net.minecraft.entity.player.PlayerEntity) world.getEntityByID(this.playerID);
    }
}
