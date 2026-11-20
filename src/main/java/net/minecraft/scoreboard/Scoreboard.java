package net.minecraft.scoreboard;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Scoreboard {
    private Map teams;
    private static String[] packedTextFragments;
    private Map entitiesScoreObjectives;
    private Map scoreObjectiveCriterias;
    private Map teamMemberships;
    private static String[] g;
    private final Map scoreObjectives = Maps.newHashMap();

    private ScoreObjective[] objectiveDisplaySlots;

    public void removeObjective(ScoreObjective scoreObjective) {
        String text = Team.cI();
        this.scoreObjectives.remove(scoreObjective.getName());
        String textValue = text;
        int i = 0;

        while (i < 19) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                this.setObjectiveInDisplaySlot(i, (ScoreObjective) null);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        List list = (List) this.scoreObjectiveCriterias.get(scoreObjective.getCriteria());
        if (list != null) {
            list.remove(scoreObjective);
        }

        for (java.util.Iterator<?> __recoveryIterator171_1852 =
                        (this.entitiesScoreObjectives.values()).iterator();
                (__recoveryIterator171_1852).hasNext(); ) {
            Map map = (Map) (__recoveryIterator171_1852).next();
            map.remove(scoreObjective);
            if (textValue == null) {
                break;
            }
        }

        this.method_496bf13a95b542253a5f(scoreObjective);
    }

    public Collection getObjectivesFromCriteria(IScoreObjectiveCriteria iScoreObjectiveCriteria) {
        Collection collection =
                (Collection) this.scoreObjectiveCriterias.get(iScoreObjectiveCriteria);
        return collection == null ? Lists.newArrayList() : Lists.newArrayList(collection);
    }

    public static String[] getDisplaySlotStrings() {
        String text = Team.cI();
        if (g == null) {
            g = new String[19];
            int i = 0;

            while (i < 19) {
                g[i] = getObjectiveDisplaySlot(i);
                i++;
                if (text == null) {
                    break;
                }
            }
        }

        return g;
    }

    public void broadcastTeamRemoved(ScorePlayerTeam scorePlayerTeam) {}

    public void setObjectiveInDisplaySlot(int i, ScoreObjective scoreObjective) {
        this.objectiveDisplaySlots[i] = scoreObjective;
    }

    public Collection getTeamNames() {
        return this.teams.keySet();
    }

    public Collection getSortedScores(ScoreObjective scoreObjective) {
        String text = Team.cI();
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_3532 =
                        (this.entitiesScoreObjectives.values()).iterator();
                (__recoveryIterator171_3532).hasNext(); ) {
            Map map = (Map) (__recoveryIterator171_3532).next();
            Score score = (Score) map.get(scoreObjective);
            if (score != null) {
                arrayList.add(score);
            }

            if (textValue == null) {
                break;
            }
        }

        Collections.sort(arrayList, Score.scoreComparator);
        return arrayList;
    }

    public Collection getObjectiveNames() {
        return this.entitiesScoreObjectives.keySet();
    }

    public void onScoreObjectiveAdded(ScoreObjective scoreObjective) {}

    static {
        net.minecraft.scoreboard.Scoreboard.packedTextFragments = new String[3];
        net.minecraft.scoreboard.Scoreboard.packedTextFragments[0] = new String("wokqlb");
        g = null;
    }

    public static int getObjectiveDisplaySlotNumber(String text) {
        if (text.equalsIgnoreCase("list")) {
            return 0;
        }

        if (text.equalsIgnoreCase("sidebar")) {
            return 1;
        }

        if (text.equalsIgnoreCase("belowName")) {
            return 2;
        }

        if (text.startsWith("sidebar.team.")) {
            String textValue = text.substring("sidebar.team.".length());
            EnumChatFormatting enumChatFormatting = EnumChatFormatting.getValueByName(textValue);
            if (enumChatFormatting != null && enumChatFormatting.getColorIndex() >= 0) {
                return enumChatFormatting.getColorIndex() + 3;
            }
        }

        return -1;
    }

    public Collection getTeams() {
        return this.teams.values();
    }

    public Map getObjectivesForEntity(String text) {
        Map map = (Map) this.entitiesScoreObjectives.get(text);
        if (map == null) {
            map = Maps.newHashMap();
        }

        return map;
    }

    public void method_496bf13a95b542253a5f(ScoreObjective scoreObjective) {}

    public void v(String text) {}

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public Score getValueFromObjective(String text, ScoreObjective scoreObjective) {
        Map map = (Map) this.entitiesScoreObjectives.get(text);
        if (map == null) {
            map = Maps.newHashMap();
            this.entitiesScoreObjectives.put(text, map);
        }

        Score score = (Score) map.get(scoreObjective);
        if (score == null) {
            score = new Score(this, scoreObjective, text);
            map.put(scoreObjective, score);
        }

        return score;
    }

    public void removeTeam(ScorePlayerTeam scorePlayerTeam) {
        String text = Team.cI();
        this.teams.remove(scorePlayerTeam.getRegisteredName());
        Iterator iterator = scorePlayerTeam.getMembershipCollection().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            String textValue2 = (String) iterator.next();
            this.teamMemberships.remove(textValue2);
            if (textValue == null) {
                break;
            }
        }

        this.method_3c2551ed25f7ff876033(scorePlayerTeam);
    }

    public void method_27a9b2d0b5e68fd3389a(String text, ScoreObjective scoreObjective) {}

    public ScoreObjective getObjective(String text) {
        return (ScoreObjective) this.scoreObjectives.get(text);
    }

    public void func_96536_a(Score score) {}

    public void removePlayerFromTeam(String text, ScorePlayerTeam scorePlayerTeam) {
        if (this.getPlayersTeam(text) != scorePlayerTeam) {
            throw new IllegalStateException(
                    "Player is either on another team or not on any team. Cannot remove from team '"
                            + scorePlayerTeam.getRegisteredName()
                            + "'.");
        }

        this.teamMemberships.remove(text);
        scorePlayerTeam.getMembershipCollection().remove(text);
    }

    public boolean entityHasObjective(String text, ScoreObjective scoreObjective) {
        Map map = (Map) this.entitiesScoreObjectives.get(text);
        if (map == null) {
            return false;
        }

        Score score = (Score) map.get(scoreObjective);
        return score != null;
    }

    public ScoreObjective addScoreObjective(
            String text, IScoreObjectiveCriteria iScoreObjectiveCriteria) {
        Team.cI();
        ScoreObjective objective = this.getObjective(text);
        if (objective != null) {
            throw new IllegalArgumentException(
                    "An objective with the name '" + text + "' already exists!");
        }

        objective = new ScoreObjective(this, text, iScoreObjectiveCriteria);
        List list = (List) this.scoreObjectiveCriterias.get(iScoreObjectiveCriteria);
        if (list == null) {
            list = Lists.newArrayList();
            this.scoreObjectiveCriterias.put(iScoreObjectiveCriteria, list);
        }

        list.add(objective);
        this.scoreObjectives.put(text, objective);
        this.onScoreObjectiveAdded(objective);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Team.b(packedTextFragments[0]);
        }

        return objective;
    }

    public ScoreObjective getObjectiveInDisplaySlot(int i) {
        return this.objectiveDisplaySlots[i];
    }

    public void removeObjectiveFromEntity(String text, ScoreObjective scoreObjective) {
        String textValue = Team.cI();
        if (scoreObjective == null) {
            Map map = (Map) this.entitiesScoreObjectives.remove(text);
            if (map == null) {
                return;
            }

            this.v(text);
            if (textValue != null) {
                return;
            }
        }

        Map map2 = (Map) this.entitiesScoreObjectives.get(text);
        if (map2 != null) {
            Score score = (Score) map2.remove(scoreObjective);
            if (map2.size() < 1) {
                Map map1 = (Map) this.entitiesScoreObjectives.remove(text);
                if (map1 != null) {
                    this.v(text);
                }

                if (textValue != null) {
                    return;
                }
            }

            if (score != null) {
                this.method_27a9b2d0b5e68fd3389a(text, scoreObjective);
            }
        }
    }

    public void func_96532_b(ScoreObjective scoreObjective) {}

    public static String getObjectiveDisplaySlot(int i) {
        switch (i) {
            case 0:
                return "list";
            case 1:
                return "sidebar";
            case 2:
                return "belowName";
            default:
                if (i >= 3 && i <= 18) {
                    EnumChatFormatting enumChatFormatting = EnumChatFormatting.fromColorIndex(i - 3);
                    if (enumChatFormatting != null
                            && enumChatFormatting != EnumChatFormatting.RESET) {
                        return "sidebar.team." + enumChatFormatting.getFriendlyName();
                    }
                }

                return null;
        }
    }

    public Collection getScoreObjectives() {
        return this.scoreObjectives.values();
    }

    public ScorePlayerTeam getPlayersTeam(String text) {
        return (ScorePlayerTeam) this.teamMemberships.get(text);
    }

    public Collection getScores() {
        String text = Team.cI();
        Collection collection = this.entitiesScoreObjectives.values();
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_18380 = (collection).iterator();
                (__recoveryIterator171_18380).hasNext(); ) {
            Map map = (Map) (__recoveryIterator171_18380).next();
            arrayList.addAll(map.values());
            if (textValue == null) {
                break;
            }
        }

        return arrayList;
    }

    public boolean addPlayerToTeam(String text, String textValue) {
        if (!this.teams.containsKey(textValue)) {
            return false;
        }

        ScorePlayerTeam team = this.getTeam(textValue);
        if (this.getPlayersTeam(text) != null) {
            this.removePlayerFromTeams(text);
        }

        this.teamMemberships.put(text, team);
        team.getMembershipCollection().add(text);
        return true;
    }

    public ScorePlayerTeam createTeam(String text) {
        ScorePlayerTeam team = this.getTeam(text);
        if (team != null) {
            throw new IllegalArgumentException(
                    "A team with the name '" + text + "' already exists!");
        }

        team = new ScorePlayerTeam(this, text);
        this.teams.put(text, team);
        this.broadcastTeamCreated(team);
        return team;
    }

    public void broadcastTeamCreated(ScorePlayerTeam scorePlayerTeam) {}

    public boolean removePlayerFromTeams(String text) {
        ScorePlayerTeam playersTeam = this.getPlayersTeam(text);
        if (playersTeam != null) {
            this.removePlayerFromTeam(text, playersTeam);
            return true;
        } else {
            return false;
        }
    }

    public void method_3c2551ed25f7ff876033(ScorePlayerTeam scorePlayerTeam) {}

    public Scoreboard() {
        this.scoreObjectiveCriterias = Maps.newHashMap();
        this.entitiesScoreObjectives = Maps.newHashMap();
        this.objectiveDisplaySlots = new ScoreObjective[19];
        this.teams = Maps.newHashMap();
        this.teamMemberships = Maps.newHashMap();
    }

    public ScorePlayerTeam getTeam(String text) {
        return (ScorePlayerTeam) this.teams.get(text);
    }
}
