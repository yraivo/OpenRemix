package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3CPacketUpdateScore implements Packet {
    private int value;
    private S3CPacketUpdateScoreAction action;
    private String objective;
    private static String[] packedTextFragments;
    private String name;

    // region String decoding support

    // endregion

    public S3CPacketUpdateScore(String text) {
        this.name = packedTextFragments[4];
        this.objective = packedTextFragments[5];
        this.name = text;
        this.objective = packedTextFragments[6];
        this.value = 0;
        this.action = S3CPacketUpdateScoreAction.REMOVE;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public String getObjectiveName() {
        return this.objective;
    }

    // region String decoding support

    // endregion

    public S3CPacketUpdateScore(String text, ScoreObjective scoreObjective) {
        this.name = packedTextFragments[7];
        this.objective = packedTextFragments[8];
        this.name = text;
        this.objective = scoreObjective.getName();
        this.value = 0;
        this.action = S3CPacketUpdateScoreAction.REMOVE;
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleUpdateScore(this);
    }

    public int getScoreValue() {
        return this.value;
    }

    public S3CPacketUpdateScore() {
        this.name = packedTextFragments[0];
        this.objective = packedTextFragments[1];
    }

    static {
        packedTextFragments = new String[9];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[0] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[1] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[2] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[3] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[4] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[5] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[6] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[7] =
                new String("");
        net.minecraft.network.play.server.S3CPacketUpdateScore.packedTextFragments[8] =
                new String("");
    }

    // endregion

    // region String decoding support

    // endregion

    public String getPlayerName() {
        return this.name;
    }

    public S3CPacketUpdateScoreAction getScoreAction() {
        return this.action;
    }

    public S3CPacketUpdateScore(Score score) {
        this.name = packedTextFragments[2];
        this.objective = packedTextFragments[3];
        this.name = score.getPlayerName();
        this.objective = score.getObjective().getName();
        this.value = score.getScorePoints();
        this.action = S3CPacketUpdateScoreAction.CHANGE;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.name = packetBuffer.readStringFromBuffer(40);
            this.action =
                    (S3CPacketUpdateScoreAction)
                            packetBuffer.readEnumValue(S3CPacketUpdateScoreAction.class);
            this.objective = packetBuffer.readStringFromBuffer(16);
            if (this.action != S3CPacketUpdateScoreAction.REMOVE) {
                this.value = packetBuffer.readVarIntFromBuffer();
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            packetBuffer.writeString(this.name);
            packetBuffer.writeEnumValue(this.action);
            packetBuffer.writeString(this.objective);
            if (this.action != S3CPacketUpdateScoreAction.REMOVE) {
                packetBuffer.writeVarIntToBuffer(this.value);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }
}
