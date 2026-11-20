package net.minecraft.scoreboard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSavedData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;

public class ScoreboardSaveData extends WorldSavedData {

    private static Logger logger;
    private Scoreboard theScoreboard;
    private CompoundTag delayedInitNbt;

    protected void method_15dc1ad093980e995423(
            ScorePlayerTeam scorePlayerTeam, NBTTagList nbtTagList) {
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < nbtTagList.tagCount()) {
            this.theScoreboard.addPlayerToTeam(
                    nbtTagList.getStringTagAt(i), scorePlayerTeam.getRegisteredName());
            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    protected void readObjectives(NBTTagList nbtTagList) {
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTag = nbtTagList.getCompoundTagAt(i);
            IScoreObjectiveCriteria iScoreObjectiveCriteria =
                    (IScoreObjectiveCriteria)
                            IScoreObjectiveCriteria.INSTANCES.get(
                                    compoundTag.getString("CriteriaName"));
            if (iScoreObjectiveCriteria != null) {
                ScoreObjective scoreObjective =
                        this.theScoreboard.addScoreObjective(
                                compoundTag.getString("Name"), iScoreObjectiveCriteria);
                scoreObjective.setDisplayName(compoundTag.getString("DisplayName"));
                scoreObjective.setRenderType(
                        IScoreObjectiveCriteriaEnumRenderType.func_178795_a(
                                compoundTag.getString("RenderType")));
            }

            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    public void writeToNBT(CompoundTag compoundTag) {
        String text = Team.cI();
        if (this.theScoreboard == null) {
            net.minecraft.scoreboard.ScoreboardSaveData.logger.warn(
                    "Tried to save scoreboard without having a scoreboard...");
            if (text != null) {
                return;
            }
        }

        compoundTag.setTag("Objectives", this.UE());
        compoundTag.setTag("PlayerScores", this.UD());
        compoundTag.setTag("Teams", this.Nv());
        this.method_c135d64953bbb242d2fb(compoundTag);
    }

    public ScoreboardSaveData(String text) {
        super(text);
    }

    public void readFromNBT(CompoundTag bumValue) {
        String text = Team.cI();
        if (this.theScoreboard == null) {
            this.delayedInitNbt = bumValue;
            if (text != null) {
                return;
            }
        }

        this.readObjectives(bumValue.getTagList("Objectives", 10));
        this.readScores(bumValue.getTagList("PlayerScores", 10));
        if (bumValue.hasKey("DisplaySlots", 10)) {
            this.readDisplayConfig(bumValue.getCompoundTag("DisplaySlots"));
        }

        if (bumValue.hasKey("Teams", 9)) {
            this.readTeams(bumValue.getTagList("Teams", 10));
        }
    }

    protected void readDisplayConfig(CompoundTag compoundTag) {
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < 19) {
            if (compoundTag.hasKey("slot_" + i, 8)) {
                String textValue2 = compoundTag.getString("slot_" + i);
                ScoreObjective objective = this.theScoreboard.getObjective(textValue2);
                this.theScoreboard.setObjectiveInDisplaySlot(i, objective);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    public void setScoreboard(Scoreboard bFzValue) {
        this.theScoreboard = bFzValue;
        if (this.delayedInitNbt != null) {
            this.readFromNBT(this.delayedInitNbt);
        }
    }

    protected void readTeams(NBTTagList nbtTagList) {
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTag = nbtTagList.getCompoundTagAt(i);
            ScorePlayerTeam scorePlayerTeam =
                    this.theScoreboard.createTeam(compoundTag.getString("Name"));
            scorePlayerTeam.setTeamName(compoundTag.getString("DisplayName"));
            if (compoundTag.hasKey("TeamColor", 8)) {
                scorePlayerTeam.setChatFormat(
                        EnumChatFormatting.getValueByName(compoundTag.getString("TeamColor")));
            }

            scorePlayerTeam.setNamePrefix(compoundTag.getString("Prefix"));
            scorePlayerTeam.setNameSuffix(compoundTag.getString("Suffix"));
            if (compoundTag.hasKey("AllowFriendlyFire", 99)) {
                scorePlayerTeam.setAllowFriendlyFire(
                        compoundTag.getBoolean("AllowFriendlyFire"));
            }

            if (compoundTag.hasKey("SeeFriendlyInvisibles", 99)) {
                scorePlayerTeam.setSeeFriendlyInvisiblesEnabled(
                        compoundTag.getBoolean("SeeFriendlyInvisibles"));
            }

            if (compoundTag.hasKey("NameTagVisibility", 8)) {
                TeamEnumVisible teamEnumVisible =
                        TeamEnumVisible.fromProtocolKey(compoundTag.getString("NameTagVisibility"));
                if (teamEnumVisible != null) {
                    scorePlayerTeam.setNameTagVisibility(teamEnumVisible);
                }
            }

            if (compoundTag.hasKey("DeathMessageVisibility", 8)) {
                TeamEnumVisible teamEnumVisibleValue =
                        TeamEnumVisible.fromProtocolKey(
                                compoundTag.getString("DeathMessageVisibility"));
                if (teamEnumVisibleValue != null) {
                    scorePlayerTeam.setDeathMessageVisibility(teamEnumVisibleValue);
                }
            }

            this.method_15dc1ad093980e995423(scorePlayerTeam, compoundTag.getTagList("Players", 8));
            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    static {
        net.minecraft.scoreboard.ScoreboardSaveData.logger = LogManager.getLogger();
    }

    public ScoreboardSaveData() {
        this("scoreboard");
    }

    protected NBTTagList UD() {
        NBTTagList nbtTagList = new NBTTagList();
        Collection collection = this.theScoreboard.getScores();
        String text = Team.cI();
        Iterator iterator = collection.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Score score = (Score) iterator.next();
            if (score.getObjective() != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.setString("Name", score.getPlayerName());
                compoundTag.setString("Objective", score.getObjective().getName());
                compoundTag.setInteger("Score", score.getScorePoints());
                compoundTag.setBoolean("Locked", score.isLocked());
                nbtTagList.appendTag(compoundTag);
            }

            if (textValue == null) {
                break;
            }
        }

        return nbtTagList;
    }

    protected NBTTagList Nv() {
        NBTTagList nbtTagList = new NBTTagList();
        Collection teams = this.theScoreboard.getTeams();
        String text = Team.cI();
        Iterator iterator = teams.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam) iterator.next();
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.setString("Name", scorePlayerTeam.getRegisteredName());
            compoundTag.setString("DisplayName", scorePlayerTeam.getTeamName());
            if (scorePlayerTeam.getChatFormat().getColorIndex() >= 0) {
                compoundTag.setString(
                        "TeamColor", scorePlayerTeam.getChatFormat().getFriendlyName());
            }

            compoundTag.setString("Prefix", scorePlayerTeam.getColorPrefix());
            compoundTag.setString("Suffix", scorePlayerTeam.getColorSuffix());
            compoundTag.setBoolean(
                    "AllowFriendlyFire", scorePlayerTeam.getAllowFriendlyFire());
            compoundTag.setBoolean(
                    "SeeFriendlyInvisibles", scorePlayerTeam.getSeeFriendlyInvisiblesEnabled());
            compoundTag.setString(
                    "NameTagVisibility", scorePlayerTeam.getNameTagVisibility().protocolKey);
            compoundTag.setString(
                    "DeathMessageVisibility",
                    scorePlayerTeam.getDeathMessageVisibility().protocolKey);
            NBTTagList nbtTagListValue = new NBTTagList();
            Iterator iteratorValue = scorePlayerTeam.getMembershipCollection().iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    String textValue2 = (String) iteratorValue.next();
                    nbtTagListValue.appendTag(new NBTTagString(textValue2));
                    if (textValue != null) {
                        continue;
                    }
                }

                compoundTag.setTag("Players", nbtTagListValue);
                nbtTagList.appendTag(compoundTag);
                if (textValue == null) {
                    return nbtTagList;
                }
                break;
            }
        }

        return nbtTagList;
    }

    protected void method_c135d64953bbb242d2fb(CompoundTag compoundTag) {
        CompoundTag compoundTagValue = new CompoundTag();
        byte b0 = 0;
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < 19) {
            ScoreObjective objectiveInDisplaySlot = this.theScoreboard.getObjectiveInDisplaySlot(i);
            if (objectiveInDisplaySlot != null) {
                compoundTagValue.setString("slot_" + i, objectiveInDisplaySlot.getName());
                b0 = 1;
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        if (b0 != 0) {
            compoundTag.setTag("DisplaySlots", compoundTagValue);
        }
    }

    protected void readScores(NBTTagList nbtTagList) {
        String text = Team.cI();
        int i = 0;
        String textValue = text;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTag = nbtTagList.getCompoundTagAt(i);
            ScoreObjective objective =
                    this.theScoreboard.getObjective(compoundTag.getString("Objective"));
            Score valueFromObjective =
                    this.theScoreboard.getValueFromObjective(
                            compoundTag.getString("Name"), objective);
            valueFromObjective.setScorePoints(compoundTag.getInteger("Score"));
            if (compoundTag.hasKey("Locked")) {
                valueFromObjective.setLocked(compoundTag.getBoolean("Locked"));
            }

            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    protected NBTTagList UE() {
        String text = Team.cI();
        NBTTagList nbtTagList = new NBTTagList();
        Collection scoreObjectives = this.theScoreboard.getScoreObjectives();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_39562 = (scoreObjectives).iterator();
                (__recoveryIterator171_39562).hasNext(); ) {
            ScoreObjective scoreObjective = (ScoreObjective) (__recoveryIterator171_39562).next();
            if (scoreObjective.getCriteria() != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.setString("Name", scoreObjective.getName());
                compoundTag.setString("CriteriaName", scoreObjective.getCriteria().getName());
                compoundTag.setString("DisplayName", scoreObjective.getDisplayName());
                compoundTag.setString("RenderType", scoreObjective.getRenderType().func_178796_a());
                nbtTagList.appendTag(compoundTag);
            }

            if (textValue == null) {
                break;
            }
        }

        return nbtTagList;
    }
}
