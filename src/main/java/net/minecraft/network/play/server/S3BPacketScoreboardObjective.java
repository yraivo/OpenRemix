package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3BPacketScoreboardObjective implements Packet {
    private int field_149342_c;
    private String objectiveValue;
    private String objectiveName;
    private IScoreObjectiveCriteriaEnumRenderType type;

    public String getObjectiveId() {
        return this.objectiveName;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleScoreboardObjective(this);
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.objectiveName = packetBuffer.readStringFromBuffer(16);
        this.field_149342_c = packetBuffer.readByte();
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            this.objectiveValue = packetBuffer.readStringFromBuffer(32);
            this.type =
                    IScoreObjectiveCriteriaEnumRenderType.func_178795_a(
                            packetBuffer.readStringFromBuffer(16));
        }
    }

    public S3BPacketScoreboardObjective(ScoreObjective scoreObjective, int i) {
        this.objectiveName = scoreObjective.getName();
        this.objectiveValue = scoreObjective.getDisplayName();
        this.type = scoreObjective.getCriteria().getRenderType();
        this.field_149342_c = i;
    }

    public String getDisplayName() {
        return this.objectiveValue;
    }

    public S3BPacketScoreboardObjective() {}

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public IScoreObjectiveCriteriaEnumRenderType getRenderType() {
        return this.type;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.objectiveName);
        packetBuffer.writeByte(this.field_149342_c);
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            packetBuffer.writeString(this.objectiveValue);
            packetBuffer.writeString(this.type.func_178796_a());
        }
    }

    public int getAction() {
        return this.field_149342_c;
    }
}
