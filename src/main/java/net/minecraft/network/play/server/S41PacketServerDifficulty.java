package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

import java.io.IOException;

public class S41PacketServerDifficulty implements Packet {
    private boolean difficultyLocked;
    private EnumDifficulty difficulty;

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
    }

    public void func_179829_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleServerDifficulty(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
    }

    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_179829_a((INetHandlerPlayClient) packetHandler);
    }

    public S41PacketServerDifficulty() {}

    public S41PacketServerDifficulty(EnumDifficulty bxAValue, boolean difficultyLockedValue) {
        this.difficulty = bxAValue;
        this.difficultyLocked = difficultyLockedValue;
    }

    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
}
