package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;

import java.io.IOException;

public class S07PacketRespawn implements Packet {
    private EnumDifficulty difficulty;
    private WorldSettingsGameType gameType;
    private int dimensionID;
    private WorldType worldType;

    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public int getDimensionID() {
        return this.dimensionID;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeInt(this.dimensionID);
        packetBuffer.writeByte(this.difficulty.getDifficultyId());
        packetBuffer.writeByte(this.gameType.getID());
        packetBuffer.writeString(this.worldType.getWorldTypeName());
    }

    public S07PacketRespawn() {}

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public S07PacketRespawn(
            int dimensionIDValue,
            EnumDifficulty bwLValue,
            WorldType bwMValue,
            WorldSettingsGameType bwKValue) {
        this.dimensionID = dimensionIDValue;
        this.difficulty = bwLValue;
        this.gameType = bwKValue;
        this.worldType = bwMValue;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.dimensionID = packetBuffer.readInt();
            this.difficulty = EnumDifficulty.getDifficultyEnum(packetBuffer.readUnsignedByte());
            this.gameType = WorldSettingsGameType.getByID(packetBuffer.readUnsignedByte());
            this.worldType = WorldType.parseWorldType(packetBuffer.readStringFromBuffer(16));
            if (this.worldType == null) {
                this.worldType = WorldType.DEFAULT;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public WorldType getWorldType() {
        return this.worldType;
    }

    public WorldSettingsGameType getGameType() {
        return this.gameType;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleRespawn(this);
    }
}
