package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3DPacketDisplayScoreboard implements Packet {
    private String scoreName;
    private int position;
    private static String[] packedTextFragments;

    public String getObjectiveId() {
        return this.scoreName;
    }

    public S3DPacketDisplayScoreboard(int i, ScoreObjective scoreObjective) {
        super();
        int[] initialFlowMarker = S14PacketEntity.getLegacyPacketFlowMarker();

        int[] aint1 = initialFlowMarker;

        this.position = i;
        int[] aint = aint1;
        if (scoreObjective == null) {
            this.scoreName = packedTextFragments[0];
            if (aint == null) {
                return;
            }
        }

        this.scoreName = scoreObjective.getName();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.network.play.server.S3DPacketDisplayScoreboard.packedTextFragments[0] =
                new String("");
    }

    // endregion

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.position = packetBuffer.readByte();
        this.scoreName = packetBuffer.readStringFromBuffer(16);
    }

    public int getDisplaySlot() {
        return this.position;
    }

    // region String decoding support

    // endregion

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.position);
        packetBuffer.writeString(this.scoreName);
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180747_a((INetHandlerPlayClient) packetHandler);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public void func_180747_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleDisplayScoreboard(this);
    }

    public S3DPacketDisplayScoreboard() {}
}
