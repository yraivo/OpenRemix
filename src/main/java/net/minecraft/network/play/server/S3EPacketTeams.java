package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.TeamEnumVisible;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Collection;

public class S3EPacketTeams implements Packet {
    private Collection playerNames;

    private int colorIndex;
    private int friendlyFlags;
    private String displayName;
    private String prefix;
    private int action;
    private String teamId;
    private String nameTagVisibilityKey;

    private String suffix;
    private static String[] packedTextFragments;

    public S3EPacketTeams(ScorePlayerTeam scorePlayerTeam, int i) {
        this.teamId = packedTextFragments[4];
        this.displayName = packedTextFragments[5];
        this.prefix = packedTextFragments[6];
        this.suffix = packedTextFragments[7];
        this.nameTagVisibilityKey = TeamEnumVisible.ALWAYS.protocolKey;
        this.colorIndex = -1;
        this.playerNames = Lists.newArrayList();
        this.teamId = scorePlayerTeam.getRegisteredName();
        this.action = i;
        if (i == 0 || i == 2) {
            this.displayName = scorePlayerTeam.getTeamName();
            this.prefix = scorePlayerTeam.getColorPrefix();
            this.suffix = scorePlayerTeam.getColorSuffix();
            this.friendlyFlags = scorePlayerTeam.packFriendlyFlags();
            this.nameTagVisibilityKey = scorePlayerTeam.getNameTagVisibility().protocolKey;
            this.colorIndex = scorePlayerTeam.getChatFormat().getColorIndex();
        }

        if (i == 0) {
            this.playerNames.addAll(scorePlayerTeam.getMembershipCollection());
        }
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public S3EPacketTeams() {
        this.teamId = packedTextFragments[0];
        this.displayName = packedTextFragments[1];
        this.prefix = packedTextFragments[2];
        this.suffix = packedTextFragments[3];
        this.nameTagVisibilityKey = TeamEnumVisible.ALWAYS.protocolKey;
        this.colorIndex = -1;
        this.playerNames = Lists.newArrayList();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    public void writePacketData(PacketBuffer buffer) throws IOException {
        boolean legacyFirstPlayerOnly = S14PacketEntity.getLegacyPacketFlowMarker() != null;
        buffer.writeString(this.teamId);
        buffer.writeByte(this.action);
        if (this.hasTeamProperties()) {
            buffer.writeString(this.displayName);
            buffer.writeString(this.prefix);
            buffer.writeString(this.suffix);
            buffer.writeByte(this.friendlyFlags);
            buffer.writeString(this.nameTagVisibilityKey);
            buffer.writeByte(this.colorIndex);
        }
        if (this.hasPlayerNames()) {
            buffer.writeVarIntToBuffer(this.playerNames.size());
            for (Object playerName : this.playerNames) {
                buffer.writeString((String) playerName);
                if (legacyFirstPlayerOnly) {
                    break;
                }
            }
        }
    }

    public Collection getPlayerNames() {
        return this.playerNames;
    }

    static {
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments = new String[13];
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[0] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[1] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[2] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[3] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[4] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[5] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[6] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[7] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[8] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[9] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[10] = new String("");
        net.minecraft.network.play.server.S3EPacketTeams.packedTextFragments[11] = new String("");
    }

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleTeams(this);
    }

    public void readPacketData(PacketBuffer buffer) throws IOException {
        boolean legacyFirstPlayerOnly = S14PacketEntity.getLegacyPacketFlowMarker() != null;
        this.teamId = buffer.readStringFromBuffer(16);
        this.action = buffer.readByte();
        if (this.hasTeamProperties()) {
            this.displayName = buffer.readStringFromBuffer(32);
            this.prefix = buffer.readStringFromBuffer(16);
            this.suffix = buffer.readStringFromBuffer(16);
            this.friendlyFlags = buffer.readByte();
            this.nameTagVisibilityKey = buffer.readStringFromBuffer(32);
            this.colorIndex = buffer.readByte();
        }
        if (this.hasPlayerNames()) {
            int count = buffer.readVarIntFromBuffer();
            for (int index = 0; index < count; index++) {
                this.playerNames.add(buffer.readStringFromBuffer(40));
                if (legacyFirstPlayerOnly) {
                    break;
                }
            }
        }
    }

    private boolean hasTeamProperties() {
        return this.action == 0 || this.action == 2;
    }

    private boolean hasPlayerNames() {
        return this.action == 0 || this.action == 3 || this.action == 4;
    }

    public String getTeamId() {
        return this.teamId;
    }

    public int getAction() {
        return this.action;
    }

    public String getNameTagVisibilityKey() {
        return this.nameTagVisibilityKey;
    }

    public int getFriendlyFlags() {
        return this.friendlyFlags;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public S3EPacketTeams(ScorePlayerTeam scorePlayerTeam, Collection collection, int i) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.teamId = packedTextFragments[8];
        this.displayName = packedTextFragments[9];
        this.prefix = packedTextFragments[10];
        int[] aint = aint1;
        this.suffix = packedTextFragments[11];
        this.nameTagVisibilityKey = TeamEnumVisible.ALWAYS.protocolKey;
        this.colorIndex = -1;
        this.playerNames = Lists.newArrayList();
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(
                    "Method must be join or leave for player constructor");
        }

        if (collection != null && !collection.isEmpty()) {
            this.action = i;
            this.teamId = scorePlayerTeam.getRegisteredName();
            this.playerNames.addAll(collection);
            if (aint == null) {
                return;
            }
        }

        throw new IllegalArgumentException("Players cannot be null/empty");
    }
}
