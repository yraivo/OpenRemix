package net.minecraft.scoreboard;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.server.MinecraftServer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ServerScoreboard extends Scoreboard {
    private MinecraftServer scoreboardMCServer;
    private final Set AR = Sets.newHashSet();
    private ScoreboardSaveData scoreboardSaveData;

    public void broadcastTeamCreated(ScorePlayerTeam scorePlayerTeam) {
        super.broadcastTeamCreated(scorePlayerTeam);
        this.scoreboardMCServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 0));
        this.method_8d9b536bcdc9991c48fb();
    }

    public List method_28eca892baac1edc65ff(ScoreObjective scoreObjective) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new S3BPacketScoreboardObjective(scoreObjective, 0));
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < 19) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                arrayList.add(new S3DPacketDisplayScoreboard(i, scoreObjective));
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_1556 =
                        (this.getSortedScores(scoreObjective)).iterator();
                (__recoveryIterator171_1556).hasNext(); ) {
            Score score = (Score) (__recoveryIterator171_1556).next();
            arrayList.add(new S3CPacketUpdateScore(score));
            if (textValue == null) {
                break;
            }
        }

        return arrayList;
    }

    public void removePlayerFromTeam(String text, ScorePlayerTeam scorePlayerTeam) {
        super.removePlayerFromTeam(text, scorePlayerTeam);
        this.scoreboardMCServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(
                        new S3EPacketTeams(scorePlayerTeam, Arrays.asList(text), 4));
        this.method_8d9b536bcdc9991c48fb();
    }

    protected void method_8d9b536bcdc9991c48fb() {
        if (this.scoreboardSaveData != null) {
            this.scoreboardSaveData.markDirty();
        }
    }

    public int method_e569b1e541c34b4fa770(ScoreObjective scoreObjective) {
        String text = Team.cI();
        int i = 0;
        String textValue = text;
        int j = 0;

        while (j < 19) {
            if (this.getObjectiveInDisplaySlot(j) == scoreObjective) {
                i++;
            }

            j++;
            if (textValue == null) {
                break;
            }
        }

        return i;
    }

    public void method_3c2551ed25f7ff876033(ScorePlayerTeam scorePlayerTeam) {
        super.method_3c2551ed25f7ff876033(scorePlayerTeam);
        this.scoreboardMCServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 1));
        this.method_8d9b536bcdc9991c48fb();
    }

    public void setScoreboardSaveData(ScoreboardSaveData bAVValue) {
        this.scoreboardSaveData = bAVValue;
    }

    public ServerScoreboard(MinecraftServer bAUValue) {
        this.scoreboardMCServer = bAUValue;
    }

    public void getPlayerIterator(ScoreObjective scoreObjective) {
        String text = Team.cI();
        List list = this.method_901cc3a82d6d66db6c05(scoreObjective);
        String textValue = text;

        label21:
        for (java.util.Iterator<?> __recoveryIterator171_3995 =
                        (this.scoreboardMCServer.getConfigurationManager().playerEntityList).iterator();
                (__recoveryIterator171_3995).hasNext(); ) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) (__recoveryIterator171_3995).next();
            Iterator iterator = list.iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Packet packet = (Packet) iterator.next();
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                    if (textValue != null) {
                        continue;
                    }
                }

                if (textValue == null) {
                    break label21;
                }
                break;
            }
        }

        this.AR.remove(scoreObjective);
    }

    public void method_878477af6144de894710(ScoreObjective scoreObjective) {
        String text = Team.cI();
        List list = this.method_28eca892baac1edc65ff(scoreObjective);
        String textValue = text;

        label21:
        for (java.util.Iterator<?> __recoveryIterator171_4995 =
                        (this.scoreboardMCServer.getConfigurationManager().playerEntityList).iterator();
                (__recoveryIterator171_4995).hasNext(); ) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) (__recoveryIterator171_4995).next();
            Iterator iterator = list.iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Packet packet = (Packet) iterator.next();
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                    if (textValue != null) {
                        continue;
                    }
                }

                if (textValue == null) {
                    break label21;
                }
                break;
            }
        }

        this.AR.add(scoreObjective);
    }

    public boolean addPlayerToTeam(String text, String textValue) {
        if (super.addPlayerToTeam(text, textValue)) {
            ScorePlayerTeam team = this.getTeam(textValue);
            this.scoreboardMCServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayers(new S3EPacketTeams(team, Arrays.asList(text), 3));
            this.method_8d9b536bcdc9991c48fb();
            return true;
        } else {
            return false;
        }
    }

    public void setObjectiveInDisplaySlot(int i, ScoreObjective scoreObjective) {
        String text;
        String textValue = Team.cI();
        ScoreObjective objectiveInDisplaySlot = this.getObjectiveInDisplaySlot(i);
        text = textValue;
        super.setObjectiveInDisplaySlot(i, scoreObjective);
        if (objectiveInDisplaySlot != scoreObjective && objectiveInDisplaySlot != null) {
            if (this.method_e569b1e541c34b4fa770(objectiveInDisplaySlot) > 0) {
                this.scoreboardMCServer
                        .getConfigurationManager()
                        .sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(i, scoreObjective));
                if (!(text != null)) {

                    this.getPlayerIterator(objectiveInDisplaySlot);
                }
            } else {

                this.getPlayerIterator(objectiveInDisplaySlot);
            }
        }

        if (scoreObjective != null) {
            if (this.AR.contains(scoreObjective)) {
                this.scoreboardMCServer
                        .getConfigurationManager()
                        .sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(i, scoreObjective));
                if (!(text != null)) {

                    this.method_878477af6144de894710(scoreObjective);
                }
            } else {

                this.method_878477af6144de894710(scoreObjective);
            }
        }

        this.method_8d9b536bcdc9991c48fb();
    }

    public void onScoreObjectiveAdded(ScoreObjective scoreObjective) {
        super.onScoreObjectiveAdded(scoreObjective);
        this.method_8d9b536bcdc9991c48fb();
    }

    public void func_96536_a(Score score) {
        super.func_96536_a(score);
        if (this.AR.contains(score.getObjective())) {
            this.scoreboardMCServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayers(new S3CPacketUpdateScore(score));
        }

        this.method_8d9b536bcdc9991c48fb();
    }

    public void method_27a9b2d0b5e68fd3389a(String text, ScoreObjective scoreObjective) {
        super.method_27a9b2d0b5e68fd3389a(text, scoreObjective);
        this.scoreboardMCServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(new S3CPacketUpdateScore(text, scoreObjective));
        this.method_8d9b536bcdc9991c48fb();
    }

    public void broadcastTeamRemoved(ScorePlayerTeam scorePlayerTeam) {
        super.broadcastTeamRemoved(scorePlayerTeam);
        this.scoreboardMCServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 2));
        this.method_8d9b536bcdc9991c48fb();
    }

    public void v(String text) {
        super.v(text);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(text));
        this.method_8d9b536bcdc9991c48fb();
    }

    public void func_96532_b(ScoreObjective scoreObjective) {
        super.func_96532_b(scoreObjective);
        if (this.AR.contains(scoreObjective)) {
            this.scoreboardMCServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayers(new S3BPacketScoreboardObjective(scoreObjective, 2));
        }

        this.method_8d9b536bcdc9991c48fb();
    }

    public void method_496bf13a95b542253a5f(ScoreObjective scoreObjective) {
        super.method_496bf13a95b542253a5f(scoreObjective);
        if (this.AR.contains(scoreObjective)) {
            this.getPlayerIterator(scoreObjective);
        }

        this.method_8d9b536bcdc9991c48fb();
    }

    public List method_901cc3a82d6d66db6c05(ScoreObjective scoreObjective) {
        String text = Team.cI();
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;
        arrayList.add(new S3BPacketScoreboardObjective(scoreObjective, 1));
        int i = 0;

        while (i < 19) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                arrayList.add(new S3DPacketDisplayScoreboard(i, scoreObjective));
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return arrayList;
    }
}
