package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;

import java.io.IOException;

public class S01PacketJoinGame implements Packet {
    private int maxPlayers;
    private EnumDifficulty difficulty;
    private WorldType worldType;
    private boolean reducedDebugInfo;
    private WorldSettingsGameType gameType;
    private int entityId;
    private boolean hardcoreMode;
    private int dimension;

    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }

    public S01PacketJoinGame() {}

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleJoinGame(this);
    }

    public S01PacketJoinGame(
            int entityIdValue,
            WorldSettingsGameType bwKValue,
            boolean hardcoreModeValue,
            int j,
            EnumDifficulty bwIValue,
            int k,
            WorldType bwJValue,
            boolean reducedDebugInfoValue) {
        this.entityId = entityIdValue;
        this.dimension = j;
        this.difficulty = bwIValue;
        this.gameType = bwKValue;
        this.maxPlayers = k;
        this.hardcoreMode = hardcoreModeValue;
        this.worldType = bwJValue;
        this.reducedDebugInfo = reducedDebugInfoValue;
    }

    public boolean isHardcoreMode() {
        return this.hardcoreMode;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readInt();
        short short1 = packetBuffer.readUnsignedByte();

        S01PacketJoinGame s01PacketJoinGame;
        byte b0;
        label29:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                s01PacketJoinGame = this;
                if ((short1 & 8) == 8) {
                    b0 = 1;
                    break label29;
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
            }

            b0 = 0;
        }

        s01PacketJoinGame.hardcoreMode = remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
        int i = short1 & -9;

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.gameType = WorldSettingsGameType.getByID(i);
            this.dimension = packetBuffer.readByte();
            this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
            this.maxPlayers = packetBuffer.readUnsignedByte();
            this.worldType = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(16));
            if (this.worldType == null) {
                this.worldType = WorldType.DEFAULT;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        this.reducedDebugInfo = packetBuffer.readBoolean();
    }

    public int getDimension() {
        return this.dimension;
    }

    public boolean isReducedDebugInfo() {
        return this.reducedDebugInfo;
    }

    public WorldSettingsGameType getGameType() {
        return this.gameType;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.entityId);
        int id = this.gameType.getID();
        if (this.hardcoreMode) {
            id |= 8;
        }

        packetBuffer.writeByte(id);
        packetBuffer.writeByte(this.dimension);
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
        packetBuffer.writeByte(this.maxPlayers);
        packetBuffer.writeString(this.worldType.getWorldTypeName());
        packetBuffer.writeBoolean(this.reducedDebugInfo);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public WorldType getWorldType() {
        return this.worldType;
    }
}
