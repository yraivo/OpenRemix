package net.minecraft.scoreboard;

import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collection;
import java.util.Set;

public class ScorePlayerTeam extends Team {
    private EnumChatFormatting chatFormat;
    private TeamEnumVisible nameTagVisibility;
    private String namePrefixSPT;
    private boolean canSeeFriendlyInvisibles;
    private String registeredName;
    private TeamEnumVisible deathMessageVisibility;

    private String teamNameSPT;

    private final Set membershipSet = Sets.newHashSet();
    private String colorSuffix;
    private Scoreboard theScoreboard;
    private boolean allowFriendlyFire;
    private static String[] packedTextFragments;

    public Collection getMembershipCollection() {
        return this.membershipSet;
    }

    public String getTeamName() {
        return this.teamNameSPT;
    }

    public void setNameSuffix(String text) {
        this.colorSuffix = text;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public static String formatPlayerName(Team team, String text) {
        try {
            if (team == null) {
                return text;
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        return team.formatString(text);
    }

    public TeamEnumVisible getDeathMessageVisibility() {
        return this.deathMessageVisibility;
    }

    public boolean getSeeFriendlyInvisiblesEnabled() {
        return this.canSeeFriendlyInvisibles;
    }

    public ScorePlayerTeam(Scoreboard bymValue, String text) {
        this.namePrefixSPT = packedTextFragments[0];
        this.colorSuffix = packedTextFragments[1];
        this.allowFriendlyFire = true;
        this.canSeeFriendlyInvisibles = true;
        this.nameTagVisibility = TeamEnumVisible.ALWAYS;
        this.deathMessageVisibility = TeamEnumVisible.ALWAYS;
        String textValue = Team.cI();
        this.chatFormat = EnumChatFormatting.RESET;
        this.theScoreboard = bymValue;
        String textValue2 = textValue;

        try {
            this.registeredName = text;
            this.teamNameSPT = text;
            if (textValue2 == null) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }
    }

    public void setTeamName(String text) {
        try {
            if (text == null) {
                throw new IllegalArgumentException("Name cannot be null");
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        this.teamNameSPT = text;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public EnumChatFormatting getChatFormat() {
        return this.chatFormat;
    }

    static {
        net.minecraft.scoreboard.ScorePlayerTeam.packedTextFragments = new String[3];
        net.minecraft.scoreboard.ScorePlayerTeam.packedTextFragments[0] = new String("");
        net.minecraft.scoreboard.ScorePlayerTeam.packedTextFragments[1] = new String("");
    }

    public String getColorSuffix() {
        return this.colorSuffix;
    }

    public void setSeeFriendlyInvisiblesEnabled(boolean flag) {
        this.canSeeFriendlyInvisibles = flag;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void setNamePrefix(String text) {
        try {
            if (text == null) {
                throw new IllegalArgumentException("Prefix cannot be null");
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        this.namePrefixSPT = text;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public void setAllowFriendlyFire(boolean flag) {
        this.allowFriendlyFire = flag;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public void setDeathMessageVisibility(TeamEnumVisible bylValue) {
        this.deathMessageVisibility = bylValue;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public int packFriendlyFlags() {
        byte b0 = 0;
        if (this.getAllowFriendlyFire()) {
            b0 |= 1;
        }

        if (this.getSeeFriendlyInvisiblesEnabled()) {
            b0 |= 2;
        }

        return b0;
    }

    public void setNameTagVisibility(TeamEnumVisible bykValue) {
        this.nameTagVisibility = bykValue;
        this.theScoreboard.broadcastTeamRemoved(this);
    }

    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }

    public String getRegisteredName() {
        return this.registeredName;
    }

    public void setChatFormat(EnumChatFormatting byjValue) {
        this.chatFormat = byjValue;
    }

    public void applyFriendlyFlags(int i) {
        ScorePlayerTeam scorePlayerTeam;
        byte b0;
        label28:
        {
            try {
                scorePlayerTeam = this;
                if ((i & 1) > 0) {
                    b0 = 1;
                    break label28;
                }
            } catch (IllegalArgumentException illegalargumentexception1) {
                throw method_9a70918140b0a7642dba(illegalargumentexception1);
            }

            b0 = 0;
        }

        label21:
        {
            try {
                scorePlayerTeam.setAllowFriendlyFire(
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b0));
                scorePlayerTeam = this;
                if ((i & 2) > 0) {
                    b0 = 1;
                    break label21;
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            b0 = 0;
        }

        scorePlayerTeam.setSeeFriendlyInvisiblesEnabled(
                remix.compat.compiler.JvmBooleanSlots.fromInt(b0));
    }

    public TeamEnumVisible getNameTagVisibility() {
        return this.nameTagVisibility;
    }

    public String getColorPrefix() {
        return this.namePrefixSPT;
    }

    public String formatString(String text) {
        return this.getColorPrefix() + text + this.getColorSuffix();
    }
}
