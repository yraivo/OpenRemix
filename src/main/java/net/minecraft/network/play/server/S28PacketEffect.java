package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S28PacketEffect implements Packet {
    private int soundType;
    private int soundData;
    private boolean serverWide;
    private BlockPos soundPos;

    public S28PacketEffect(
            int soundTypeValue, BlockPos blockPos, int soundDataValue, boolean serverWideValue) {
        this.soundType = soundTypeValue;
        this.soundPos = blockPos;
        this.soundData = soundDataValue;
        this.serverWide = serverWideValue;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180739_a((INetHandlerPlayClient) packetHandler);
    }

    public BlockPos getSoundPos() {
        return this.soundPos;
    }

    public void func_180739_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEffect(this);
    }

    public S28PacketEffect() {}

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.soundType);
        packetBuffer.writeBlockPos(this.soundPos);
        packetBuffer.writeInt(this.soundData);
        packetBuffer.writeBoolean(this.serverWide);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.soundType = packetBuffer.readInt();
        this.soundPos = packetBuffer.readBlockPos();
        this.soundData = packetBuffer.readInt();
        this.serverWide = packetBuffer.readBoolean();
    }

    public int getSoundData() {
        return this.soundData;
    }

    public int getSoundType() {
        return this.soundType;
    }

    public boolean isSoundServerwide() {
        return this.serverWide;
    }
}
