package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.TeamEnumVisible;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandScoreboard extends CommandBase {
    private static String[] packedTextFragments;

    private boolean func_175780_b(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        int i = -1;
        Entity[] entityArrayValue = entityArray;
        int j = 0;

        while (j < astring.length) {
            if (this.isUsernameIndex(astring, j)
                    && packedTextFragments[0].equals(astring[j])) {
                if (i >= 0) {
                    throw new CommandException(
                            "commands.scoreboard.noMultiWildcard", new Object[0]);
                }

                i = j;
            }

            j++;
            if (entityArrayValue != null) {
                break;
            }
        }

        if (i < 0) {
            return false;
        }

        ArrayList arrayList = Lists.newArrayList(this.getScoreboard().getObjectiveNames());
        String text = astring[i];
        ArrayList arrayListValue = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_1828 = (arrayList).iterator();
                (__recoveryIterator171_1828).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_1828).next();
            astring[i] = textValue;

            try {
                this.processCommand(commandSender, astring);
                arrayListValue.add(textValue);
            } catch (CommandException commandException) {
                ChatComponentTranslation chatComponentTranslation =
                        new ChatComponentTranslation(
                                commandException.getMessage(),
                                commandException.getErrorObjects());
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                commandSender.addChatMessage(chatComponentTranslation);
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        astring[i] = text;
        commandSender.setCommandStat(
                CommandResultStatsType.AFFECTED_ENTITIES, arrayListValue.size());
        if (arrayListValue.size() == 0) {
            throw new WrongUsageException(
                    "commands.scoreboard.allMatchesFailed", new Object[0]);
        } else {
            return true;
        }
    }

    protected void listObjectives(CommandSender commandSender)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArrayValue = entityArray;
        Collection scoreObjectives = scoreboard.getScoreObjectives();
        if (scoreObjectives.size() <= 0) {
            throw new CommandException(
                    "commands.scoreboard.objectives.list.empty", new Object[0]);
        }

        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation(
                        "commands.scoreboard.objectives.list.count",
                        new Object[] {scoreObjectives.size()});
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);

        for (java.util.Iterator<?> __recoveryIterator171_3994 = (scoreObjectives).iterator();
                (__recoveryIterator171_3994).hasNext(); ) {
            ScoreObjective scoreObjective = (ScoreObjective) (__recoveryIterator171_3994).next();
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.scoreboard.objectives.list.entry",
                            new Object[] {
                                scoreObjective.getName(),
                                scoreObjective.getDisplayName(),
                                scoreObjective.getCriteria().getName()
                            }));
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    protected List eN() {
        Entity[] entityArray = CommandBlockLogic.GU();
        Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        Entity[] entityArrayValue = entityArray;
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_4985 = (scoreObjectives).iterator();
                (__recoveryIterator171_4985).hasNext(); ) {
            ScoreObjective scoreObjective = (ScoreObjective) (__recoveryIterator171_4985).next();
            if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
                arrayList.add(scoreObjective.getName());
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        return arrayList;
    }

    protected void enablePlayer(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Scoreboard scoreboard = this.getScoreboard();
        String text = getPlayerName(commandSender, astring[i++]);
        ScoreObjective scoreObjective = this.getObjective(astring[i], false);
        if (scoreObjective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            throw new CommandException(
                    "commands.scoreboard.players.enable.noTrigger",
                    new Object[] {scoreObjective.getName()});
        }

        Score valueFromObjective = scoreboard.getValueFromObjective(text, scoreObjective);
        valueFromObjective.setLocked(false);
        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.players.enable.success",
                new Object[] {scoreObjective.getName(), text});
    }

    protected void setObjectiveDisplay(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        String text = astring[i++];
        int j = Scoreboard.getObjectiveDisplaySlotNumber(text);
        Entity[] entityArrayValue = entityArray;
        ScoreObjective scoreObjective = null;
        if (astring.length == 4) {
            scoreObjective = this.getObjective(astring[i], false);
        }

        if (j < 0) {
            throw new CommandException(
                    "commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] {text});
        }

        scoreboard.setObjectiveInDisplaySlot(j, scoreObjective);
        if (scoreObjective != null) {
            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.objectives.setdisplay.successSet",
                    new Object[] {Scoreboard.getObjectiveDisplaySlot(j), scoreObjective.getName()});
            if (entityArrayValue == null) {
                return;
            }
        }

        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.objectives.setdisplay.successCleared",
                new Object[] {Scoreboard.getObjectiveDisplaySlot(j)});
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        if (astring.length == 1) {
            return getListOfStringsMatchingLastWord(
                    astring, new String[] {"objectives", "players", "teams"});
        }

        if (astring[0].equalsIgnoreCase("objectives")) {
            if (astring.length == 2) {
                return getListOfStringsMatchingLastWord(
                        astring, new String[] {"list", "add", "remove", "setdisplay"});
            }

            if (astring[1].equalsIgnoreCase("add")) {
                if (astring.length == 4) {
                    Set set = IScoreObjectiveCriteria.INSTANCES.keySet();
                    return getListOfStringsMatchingLastWord(astring, set);
                }
            } else if (astring[1].equalsIgnoreCase("remove")) {
                if (astring.length == 3) {
                    return getListOfStringsMatchingLastWord(
                            astring, this.func_147184_a(false));
                }
            } else if (astring[1].equalsIgnoreCase("setdisplay")) {
                if (astring.length == 3) {
                    return getListOfStringsMatchingLastWord(astring, Scoreboard.getDisplaySlotStrings());
                }

                if (astring.length == 4) {
                    return getListOfStringsMatchingLastWord(
                            astring, this.func_147184_a(false));
                }
            }
        } else if (astring[0].equalsIgnoreCase("players")) {
            if (astring.length == 2) {
                return getListOfStringsMatchingLastWord(
                        astring,
                        new String[] {
                            "set", "add", "remove", "reset", "list", "enable", "test", "operation"
                        });
            }

            if (!astring[1].equalsIgnoreCase("set")
                    && !astring[1].equalsIgnoreCase("add")
                    && !astring[1].equalsIgnoreCase("remove")
                    && !astring[1].equalsIgnoreCase("reset")) {
                if (astring[1].equalsIgnoreCase("enable")) {
                    if (astring.length == 3) {
                        return getListOfStringsMatchingLastWord(
                                astring, MinecraftServer.getServer().getAllUsernames());
                    }

                    if (astring.length == 4) {
                        return getListOfStringsMatchingLastWord(astring, this.eN());
                    }
                } else if (!astring[1].equalsIgnoreCase("list")
                        && !astring[1].equalsIgnoreCase("test")) {
                    if (astring[1].equalsIgnoreCase("operation")) {
                        if (astring.length == 3) {
                            return getListOfStringsMatchingLastWord(
                                    astring, this.getScoreboard().getObjectiveNames());
                        }

                        if (astring.length == 4) {
                            return getListOfStringsMatchingLastWord(
                                    astring, this.func_147184_a(true));
                        }

                        if (astring.length == 5) {
                            return getListOfStringsMatchingLastWord(
                                    astring,
                                    new String[] {
                                        "+=",
                                        "-=",
                                        "*=",
                                        "/=",
                                        "%=",
                                        packedTextFragments[8],
                                        packedTextFragments[9],
                                        packedTextFragments[10],
                                        "><"
                                    });
                        }

                        if (astring.length == 6) {
                            return getListOfStringsMatchingLastWord(
                                    astring, MinecraftServer.getServer().getAllUsernames());
                        }

                        if (astring.length == 7) {
                            return getListOfStringsMatchingLastWord(
                                    astring, this.func_147184_a(false));
                        }
                    }
                } else {
                    if (astring.length == 3) {
                        return getListOfStringsMatchingLastWord(astring, this.getScoreboard().getObjectiveNames());
                    }

                    if (astring.length == 4 && astring[1].equalsIgnoreCase("test")) {
                        return getListOfStringsMatchingLastWord(
                                astring, this.func_147184_a(false));
                    }
                }
            } else {
                if (astring.length == 3) {
                    return getListOfStringsMatchingLastWord(
                            astring, MinecraftServer.getServer().getAllUsernames());
                }

                if (astring.length == 4) {
                    return getListOfStringsMatchingLastWord(
                            astring, this.func_147184_a(true));
                }
            }
        } else if (astring[0].equalsIgnoreCase("teams")) {
            if (astring.length == 2) {
                return getListOfStringsMatchingLastWord(
                        astring,
                        new String[] {"add", "remove", "join", "leave", "empty", "list", "option"});
            }

            if (astring[1].equalsIgnoreCase("join")) {
                if (astring.length == 3) {
                    return getListOfStringsMatchingLastWord(astring, this.getScoreboard().getTeamNames());
                }

                if (astring.length >= 4) {
                    return getListOfStringsMatchingLastWord(
                            astring, MinecraftServer.getServer().getAllUsernames());
                }
            } else {
                if (astring[1].equalsIgnoreCase("leave")) {
                    return getListOfStringsMatchingLastWord(
                            astring, MinecraftServer.getServer().getAllUsernames());
                }

                if (!astring[1].equalsIgnoreCase("empty")
                        && !astring[1].equalsIgnoreCase("list")
                        && !astring[1].equalsIgnoreCase("remove")) {
                    if (astring[1].equalsIgnoreCase("option")) {
                        if (astring.length == 3) {
                            return getListOfStringsMatchingLastWord(astring, this.getScoreboard().getTeamNames());
                        }

                        if (astring.length == 4) {
                            return getListOfStringsMatchingLastWord(
                                    astring,
                                    new String[] {
                                        "color",
                                        "friendlyfire",
                                        "seeFriendlyInvisibles",
                                        "nametagVisibility",
                                        "deathMessageVisibility"
                                    });
                        }

                        if (astring.length == 5) {
                            if (astring[3].equalsIgnoreCase("color")) {
                                return getListOfStringsMatchingLastWord(
                                        astring,
                                        EnumChatFormatting.getValidValues(
                                                true, false));
                            }

                            if (astring[3].equalsIgnoreCase("nametagVisibility")
                                    || astring[3].equalsIgnoreCase("deathMessageVisibility")) {
                                return getListOfStringsMatchingLastWord(astring, TeamEnumVisible.y());
                            }

                            if (astring[3].equalsIgnoreCase("friendlyfire")
                                    || astring[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return getListOfStringsMatchingLastWord(
                                        astring, new String[] {"true", "false"});
                            }
                        }
                    }
                } else if (astring.length == 3) {
                    return getListOfStringsMatchingLastWord(astring, this.getScoreboard().getTeamNames());
                }
            }
        }

        return null;
    }

    protected void testPlayer(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArray = CommandBlockLogic.GU();
        String text = getEntityName(commandSender, astring[i++]);
        ScoreObjective scoreObjective = this.getObjective(astring[i++], false);
        Entity[] entityArrayValue = entityArray;
        if (!scoreboard.entityHasObjective(text, scoreObjective)) {
            throw new CommandException(
                    "commands.scoreboard.players.test.notFound",
                    new Object[] {scoreObjective.getName(), text});
        }

        int j =
                astring[i].equals(packedTextFragments[3])
                        ? Integer.MIN_VALUE
                        : parseInt(astring[i]);
        i++;
        int k =
                i < astring.length && !astring[i].equals(packedTextFragments[4])
                        ? parseInt(astring[i], j)
                        : Integer.MAX_VALUE;
        Score valueFromObjective = scoreboard.getValueFromObjective(text, scoreObjective);
        if (valueFromObjective.getScorePoints() >= j && valueFromObjective.getScorePoints() <= k) {
            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.players.test.success",
                    new Object[] {valueFromObjective.getScorePoints(), j, k});
            if (entityArrayValue == null) {
                return;
            }
        }

        throw new CommandException(
                "commands.scoreboard.players.test.failed",
                new Object[] {valueFromObjective.getScorePoints(), j, k});
    }

    protected List func_147184_a(boolean flag) {
        Entity[] entityArray = CommandBlockLogic.GU();
        Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        Entity[] entityArrayValue = entityArray;
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_24225 = (scoreObjectives).iterator();
                (__recoveryIterator171_24225).hasNext(); ) {
            ScoreObjective scoreObjective = (ScoreObjective) (__recoveryIterator171_24225).next();
            if (!flag || !scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        return arrayList;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return !astring[0].equalsIgnoreCase("players")
                ? (astring[0].equalsIgnoreCase("teams") ? i == 2 : false)
                : (astring.length > 1 && astring[1].equalsIgnoreCase("operation")
                        ? i == 2 || i == 5
                        : i == 2);
    }

    protected void removeTeam(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Scoreboard scoreboard = this.getScoreboard();
        ScorePlayerTeam scorePlayerTeam = this.getTeam(astring[i]);
        if (scorePlayerTeam != null) {
            scoreboard.removeTeam(scorePlayerTeam);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.teams.remove.success",
                    new Object[] {scorePlayerTeam.getRegisteredName()});
        }
    }

    protected void listPlayers(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArrayValue = entityArray;
        if (astring.length > i) {
            String text = getEntityName(commandSender, astring[i]);
            Map map = scoreboard.getObjectivesForEntity(text);
            commandSender.setCommandStat(CommandResultStatsType.QUERY_RESULT, map.size());
            if (map.size() <= 0) {
                throw new CommandException(
                        "commands.scoreboard.players.list.player.empty", new Object[] {text});
            }

            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation(
                            "commands.scoreboard.players.list.player.count",
                            new Object[] {map.size(), text});
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);

            for (java.util.Iterator<?> __recoveryIterator171_27087 = (map.values()).iterator();
                    (__recoveryIterator171_27087).hasNext(); ) {
                Score score = (Score) (__recoveryIterator171_27087).next();
                commandSender.addChatMessage(
                        new ChatComponentTranslation(
                                "commands.scoreboard.players.list.player.entry",
                                new Object[] {
                                    score.getScorePoints(),
                                    score.getObjective().getDisplayName(),
                                    score.getObjective().getName()
                                }));
                if (entityArrayValue != null) {
                    break;
                }
            }

            if (entityArrayValue == null) {
                return;
            }
        }

        Collection objectiveNames = scoreboard.getObjectiveNames();
        commandSender.setCommandStat(CommandResultStatsType.QUERY_RESULT, objectiveNames.size());
        if (objectiveNames.size() <= 0) {
            throw new CommandException(
                    "commands.scoreboard.players.list.empty", new Object[0]);
        }

        ChatComponentTranslation chatComponentTranslationValue =
                new ChatComponentTranslation(
                        "commands.scoreboard.players.list.count",
                        new Object[] {objectiveNames.size()});
        chatComponentTranslationValue.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslationValue);
        commandSender.addChatMessage(
                new ChatComponentText(joinNiceString(objectiveNames.toArray())));
    }

    protected void removeObjective(CommandSender commandSender, String text)
            throws CommandException {
        Scoreboard scoreboard = this.getScoreboard();
        ScoreObjective scoreObjective = this.getObjective(text, false);
        scoreboard.removeObjective(scoreObjective);
        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.objectives.remove.success",
                new Object[] {text});
    }

    protected void setTeamOption(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        ScorePlayerTeam scorePlayerTeam = this.getTeam(astring[i++]);
        Entity[] entityArrayValue = entityArray;
        if (scorePlayerTeam != null) {
            String text = astring[i++].toLowerCase();
            if (!text.equalsIgnoreCase("color")
                    && !text.equalsIgnoreCase("friendlyfire")
                    && !text.equalsIgnoreCase("seeFriendlyInvisibles")
                    && !text.equalsIgnoreCase("nametagVisibility")
                    && !text.equalsIgnoreCase("deathMessageVisibility")) {
                throw new WrongUsageException(
                        "commands.scoreboard.teams.option.usage", new Object[0]);
            }

            if (astring.length == 4) {
                if (text.equalsIgnoreCase("color")) {
                    throw new WrongUsageException(
                            "commands.scoreboard.teams.option.noValue",
                            new Object[] {
                                text,
                                joinNiceStringFromCollection(
                                        EnumChatFormatting.getValidValues(true, false))
                            });
                }

                if (!text.equalsIgnoreCase("friendlyfire")
                        && !text.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!text.equalsIgnoreCase("nametagVisibility")
                            && !text.equalsIgnoreCase("deathMessageVisibility")) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.option.usage", new Object[0]);
                    }

                    throw new WrongUsageException(
                            "commands.scoreboard.teams.option.noValue",
                            new Object[] {text, joinNiceString(TeamEnumVisible.y())});
                }

                throw new WrongUsageException(
                        "commands.scoreboard.teams.option.noValue",
                        new Object[] {
                            text, joinNiceStringFromCollection(Arrays.asList("true", "false"))
                        });
            }

            String textValue;
            label120:
            {
                textValue = astring[i];
                if (text.equalsIgnoreCase("color")) {
                    EnumChatFormatting enumChatFormatting =
                            EnumChatFormatting.getValueByName(textValue);
                    if (enumChatFormatting == null || enumChatFormatting.isFancyStyling()) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.option.noValue",
                                new Object[] {
                                    text,
                                    joinNiceStringFromCollection(
                                            EnumChatFormatting.getValidValues(
                                                    true, false))
                                });
                    }

                    scorePlayerTeam.setChatFormat(enumChatFormatting);
                    scorePlayerTeam.setNamePrefix(enumChatFormatting.toString());
                    scorePlayerTeam.setNameSuffix(EnumChatFormatting.RESET.toString());
                    if (entityArrayValue == null) {
                        break label120;
                    }
                }

                if (text.equalsIgnoreCase("friendlyfire")) {
                    if (!textValue.equalsIgnoreCase("true")
                            && !textValue.equalsIgnoreCase("false")) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.option.noValue",
                                new Object[] {
                                    text,
                                    joinNiceStringFromCollection(Arrays.asList("true", "false"))
                                });
                    }

                    scorePlayerTeam.setAllowFriendlyFire(textValue.equalsIgnoreCase("true"));
                    if (entityArrayValue == null) {
                        break label120;
                    }
                }

                if (text.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!textValue.equalsIgnoreCase("true")
                            && !textValue.equalsIgnoreCase("false")) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.option.noValue",
                                new Object[] {
                                    text,
                                    joinNiceStringFromCollection(Arrays.asList("true", "false"))
                                });
                    }

                    scorePlayerTeam.setSeeFriendlyInvisiblesEnabled(textValue.equalsIgnoreCase("true"));
                    if (entityArrayValue == null) {
                        break label120;
                    }
                }

                if (text.equalsIgnoreCase("nametagVisibility")) {
                    TeamEnumVisible teamEnumVisible = TeamEnumVisible.fromProtocolKey(textValue);
                    if (teamEnumVisible == null) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.option.noValue",
                                new Object[] {
                                    text, joinNiceString(TeamEnumVisible.y())
                                });
                    }

                    scorePlayerTeam.setNameTagVisibility(teamEnumVisible);
                    if (entityArrayValue == null) {
                        break label120;
                    }
                }

                if (text.equalsIgnoreCase("deathMessageVisibility")) {
                    TeamEnumVisible teamEnumVisibleValue = TeamEnumVisible.fromProtocolKey(textValue);
                    if (teamEnumVisibleValue == null) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.option.noValue",
                                new Object[] {
                                    text, joinNiceString(TeamEnumVisible.y())
                                });
                    }

                    scorePlayerTeam.setDeathMessageVisibility(teamEnumVisibleValue);
                }
            }

            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.teams.option.success",
                    new Object[] {text, scorePlayerTeam.getRegisteredName(), textValue});
        }
    }

    protected void addObjective(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        String text = astring[i++];
        String textValue = astring[i++];
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArray = CommandBlockLogic.GU();
        IScoreObjectiveCriteria iScoreObjectiveCriteria =
                (IScoreObjectiveCriteria) IScoreObjectiveCriteria.INSTANCES.get(textValue);
        Entity[] entityArrayValue = entityArray;
        if (iScoreObjectiveCriteria == null) {
            throw new WrongUsageException(
                    "commands.scoreboard.objectives.add.wrongType", new Object[] {textValue});
        }

        if (scoreboard.getObjective(text) != null) {
            throw new CommandException(
                    "commands.scoreboard.objectives.add.alreadyExists", new Object[] {text});
        }

        if (text.length() > 16) {
            throw new SyntaxErrorException(
                    "commands.scoreboard.objectives.add.tooLong", new Object[] {text, 16});
        }

        if (text.length() == 0) {
            throw new WrongUsageException(
                    "commands.scoreboard.objectives.add.usage", new Object[0]);
        }

        label34:
        {
            if (astring.length > i) {
                String textValue2 =
                        getChatComponentFromNthArg(commandSender, astring, i).getUnformattedText();
                if (textValue2.length() > 32) {
                    throw new SyntaxErrorException(
                            "commands.scoreboard.objectives.add.displayTooLong",
                            new Object[] {textValue2, 32});
                }

                {
                    if (textValue2.length() > 0) {
                        scoreboard
                                .addScoreObjective(text, iScoreObjectiveCriteria)
                                .setDisplayName(textValue2);
                        if (!(entityArrayValue == null)) {

                            scoreboard.addScoreObjective(text, iScoreObjectiveCriteria);
                        }
                    } else {

                        scoreboard.addScoreObjective(text, iScoreObjectiveCriteria);
                    }
                }

                if (entityArrayValue == null) {
                    break label34;
                }
            }

            scoreboard.addScoreObjective(text, iScoreObjectiveCriteria);
        }

        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.objectives.add.success",
                new Object[] {text});
    }

    protected ScoreObjective getObjective(String text, boolean flag)
            throws CommandException {
        Scoreboard scoreboard = this.getScoreboard();
        ScoreObjective objective = scoreboard.getObjective(text);
        if (objective == null) {
            throw new CommandException(
                    "commands.scoreboard.objectiveNotFound", new Object[] {text});
        } else if (flag && objective.getCriteria().isReadOnly()) {
            throw new CommandException(
                    "commands.scoreboard.objectiveReadOnly", new Object[] {text});
        } else {
            return objective;
        }
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (!this.func_175780_b(commandSender, astring)) {
            if (astring.length < 1) {
                throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            }

            if (astring[0].equalsIgnoreCase("objectives")) {
                if (astring.length == 1) {
                    throw new WrongUsageException(
                            "commands.scoreboard.objectives.usage", new Object[0]);
                }

                if (astring[1].equalsIgnoreCase("list")) {
                    this.listObjectives(commandSender);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("add")) {
                    if (astring.length < 4) {
                        throw new WrongUsageException(
                                "commands.scoreboard.objectives.add.usage", new Object[0]);
                    }

                    this.addObjective(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("remove")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException(
                                "commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }

                    this.removeObjective(commandSender, astring[2]);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (!astring[1].equalsIgnoreCase("setdisplay")) {
                    throw new WrongUsageException(
                            "commands.scoreboard.objectives.usage", new Object[0]);
                }

                if (astring.length != 3 && astring.length != 4) {
                    throw new WrongUsageException(
                            "commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                }

                this.setObjectiveDisplay(commandSender, astring, 2);
                if (entityArray == null) {
                    return;
                }
            }

            if (astring[0].equalsIgnoreCase("players")) {
                if (astring.length == 1) {
                    throw new WrongUsageException(
                            "commands.scoreboard.players.usage", new Object[0]);
                }

                if (astring[1].equalsIgnoreCase("list")) {
                    if (astring.length > 3) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.list.usage", new Object[0]);
                    }

                    this.listPlayers(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("add")) {
                    if (astring.length < 5) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.add.usage", new Object[0]);
                    }

                    this.setPlayer(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("remove")) {
                    if (astring.length < 5) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.remove.usage", new Object[0]);
                    }

                    this.setPlayer(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("set")) {
                    if (astring.length < 5) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.set.usage", new Object[0]);
                    }

                    this.setPlayer(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("reset")) {
                    if (astring.length != 3 && astring.length != 4) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.reset.usage", new Object[0]);
                    }

                    this.resetPlayers(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("enable")) {
                    if (astring.length != 4) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.enable.usage", new Object[0]);
                    }

                    this.enablePlayer(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("test")) {
                    if (astring.length != 5 && astring.length != 6) {
                        throw new WrongUsageException(
                                "commands.scoreboard.players.test.usage", new Object[0]);
                    }

                    this.testPlayer(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (!astring[1].equalsIgnoreCase("operation")) {
                    throw new WrongUsageException(
                            "commands.scoreboard.players.usage", new Object[0]);
                }

                if (astring.length != 7) {
                    throw new WrongUsageException(
                            "commands.scoreboard.players.operation.usage", new Object[0]);
                }

                this.operatePlayers(commandSender, astring, 2);
                if (entityArray == null) {
                    return;
                }
            }

            if (astring[0].equalsIgnoreCase("teams")) {
                if (astring.length == 1) {
                    throw new WrongUsageException(
                            "commands.scoreboard.teams.usage", new Object[0]);
                }

                if (astring[1].equalsIgnoreCase("list")) {
                    if (astring.length > 3) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.list.usage", new Object[0]);
                    }

                    this.listTeams(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("add")) {
                    if (astring.length < 3) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.add.usage", new Object[0]);
                    }

                    this.addTeam(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("remove")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.remove.usage", new Object[0]);
                    }

                    this.removeTeam(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("empty")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.empty.usage", new Object[0]);
                    }

                    this.emptyTeam(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("join")) {
                    if (astring.length < 4
                            && (astring.length != 3 || !(commandSender instanceof PlayerEntity))) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.join.usage", new Object[0]);
                    }

                    this.joinTeam(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (astring[1].equalsIgnoreCase("leave")) {
                    if (astring.length < 3 && !(commandSender instanceof PlayerEntity)) {
                        throw new WrongUsageException(
                                "commands.scoreboard.teams.leave.usage", new Object[0]);
                    }

                    this.leaveTeam(commandSender, astring, 2);
                    if (entityArray == null) {
                        return;
                    }
                }

                if (!astring[1].equalsIgnoreCase("option")) {
                    throw new WrongUsageException(
                            "commands.scoreboard.teams.usage", new Object[0]);
                }

                if (astring.length != 4 && astring.length != 5) {
                    throw new WrongUsageException(
                            "commands.scoreboard.teams.option.usage", new Object[0]);
                }

                this.setTeamOption(commandSender, astring, 2);
            }
        }
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.scoreboard.usage";
    }

    protected void leaveTeam(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        HashSet hashset;
        HashSet hashset1;
        label71:
        {
            Entity[] entityArray = CommandBlockLogic.GU();
            Scoreboard scoreboard = this.getScoreboard();
            Entity[] entityArrayValue = entityArray;
            hashset = Sets.newHashSet();
            hashset1 = Sets.newHashSet();
            if (commandSender instanceof PlayerEntity && i == astring.length) {
                String name = getCommandSenderAsPlayer(commandSender).getName();
                if (scoreboard.removePlayerFromTeams(name)) {
                    hashset.add(name);
                    if (entityArrayValue == null) {
                        break label71;
                    }
                }

                hashset1.add(name);
                if (entityArrayValue == null) {
                    break label71;
                }
            }

            label70:
            while (i < astring.length) {
                String text = astring[i++];
                if (text.startsWith(packedTextFragments[2])) {
                    List list = method_6c2108a61cf6b9b04dde(commandSender, text);
                    Iterator iterator = list.iterator();

                    while (true) {
                        if (iterator.hasNext()) {
                            {
                                Entity entity = (Entity) iterator.next();
                                String textValue =
                                        getEntityName(
                                                commandSender, entity.getUniqueId().toString());
                                if (scoreboard.removePlayerFromTeams(textValue)) {
                                    hashset.add(textValue);
                                    if (!(entityArrayValue == null)) {

                                        hashset1.add(textValue);
                                    }
                                } else {

                                    hashset1.add(textValue);
                                }
                            }

                            if (entityArrayValue == null) {
                                continue;
                            }
                        }

                        if (entityArrayValue != null) {
                            break;
                        }
                        continue label70;
                    }
                }

                {
                    String textValue2 = getEntityName(commandSender, text);
                    if (scoreboard.removePlayerFromTeams(textValue2)) {
                        hashset.add(textValue2);
                        if (!(entityArrayValue == null)) {

                            hashset1.add(textValue2);
                        }
                    } else {

                        hashset1.add(textValue2);
                    }
                }

                if (entityArrayValue != null) {
                    break;
                }
            }
        }

        if (!hashset.isEmpty()) {
            commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ENTITIES, hashset.size());
            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.teams.leave.success",
                    new Object[] {
                        hashset.size(), joinNiceString(hashset.toArray(new String[0]))
                    });
        }

        if (!hashset1.isEmpty()) {
            throw new CommandException(
                    "commands.scoreboard.teams.leave.failure",
                    new Object[] {
                        hashset1.size(),
                        joinNiceString(hashset1.toArray(new String[0]))
                    });
        }
    }

    protected void addTeam(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        String text = astring[i++];
        Entity[] entityArrayValue = entityArray;
        Scoreboard scoreboard = this.getScoreboard();
        if (scoreboard.getTeam(text) != null) {
            throw new CommandException(
                    "commands.scoreboard.teams.add.alreadyExists", new Object[] {text});
        }

        if (text.length() > 16) {
            throw new SyntaxErrorException(
                    "commands.scoreboard.teams.add.tooLong", new Object[] {text, 16});
        }

        if (text.length() == 0) {
            throw new WrongUsageException(
                    "commands.scoreboard.teams.add.usage", new Object[0]);
        }

        label32:
        {
            if (astring.length > i) {
                String textValue =
                        getChatComponentFromNthArg(commandSender, astring, i).getUnformattedText();
                if (textValue.length() > 32) {
                    throw new SyntaxErrorException(
                            "commands.scoreboard.teams.add.displayTooLong",
                            new Object[] {textValue, 32});
                }

                {
                    if (textValue.length() > 0) {
                        scoreboard.createTeam(text).setTeamName(textValue);
                        if (!(entityArrayValue == null)) {

                            scoreboard.createTeam(text);
                        }
                    } else {

                        scoreboard.createTeam(text);
                    }
                }

                if (entityArrayValue == null) {
                    break label32;
                }
            }

            scoreboard.createTeam(text);
        }

        notifyOperators(
                commandSender, this, "commands.scoreboard.teams.add.success", new Object[] {text});
    }

    protected Scoreboard getScoreboard() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }

    protected void listTeams(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArrayValue = entityArray;
        if (astring.length > i) {
            ScorePlayerTeam scorePlayerTeam = this.getTeam(astring[i]);
            if (scorePlayerTeam == null) {
                return;
            }

            Collection membershipCollection = scorePlayerTeam.getMembershipCollection();
            commandSender.setCommandStat(
                    CommandResultStatsType.QUERY_RESULT, membershipCollection.size());
            if (membershipCollection.size() <= 0) {
                throw new CommandException(
                        "commands.scoreboard.teams.list.player.empty",
                        new Object[] {scorePlayerTeam.getRegisteredName()});
            }

            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation(
                            "commands.scoreboard.teams.list.player.count",
                            new Object[] {
                                membershipCollection.size(), scorePlayerTeam.getRegisteredName()
                            });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            commandSender.addChatMessage(
                    new ChatComponentText(
                            joinNiceString(membershipCollection.toArray())));
            if (entityArrayValue == null) {
                return;
            }
        }

        Collection collectionValue = scoreboard.getTeams();
        commandSender.setCommandStat(CommandResultStatsType.QUERY_RESULT, collectionValue.size());
        if (collectionValue.size() <= 0) {
            throw new CommandException(
                    "commands.scoreboard.teams.list.empty", new Object[0]);
        }

        ChatComponentTranslation chatComponentTranslationValue =
                new ChatComponentTranslation(
                        "commands.scoreboard.teams.list.count",
                        new Object[] {collectionValue.size()});
        chatComponentTranslationValue.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslationValue);

        for (java.util.Iterator<?> __recoveryIterator171_58982 = (collectionValue).iterator();
                (__recoveryIterator171_58982).hasNext(); ) {
            ScorePlayerTeam scorePlayerTeamValue =
                    (ScorePlayerTeam) (__recoveryIterator171_58982).next();
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.scoreboard.teams.list.entry",
                            new Object[] {
                                scorePlayerTeamValue.getRegisteredName(),
                                scorePlayerTeamValue.getTeamName(),
                                scorePlayerTeamValue.getMembershipCollection().size()
                            }));
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    protected void emptyTeam(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArrayValue = entityArray;
        ScorePlayerTeam scorePlayerTeam = this.getTeam(astring[i]);
        if (scorePlayerTeam != null) {
            ArrayList arrayList = Lists.newArrayList(scorePlayerTeam.getMembershipCollection());
            commandSender.setCommandStat(
                    CommandResultStatsType.AFFECTED_ENTITIES, arrayList.size());
            if (arrayList.isEmpty()) {
                throw new CommandException(
                        "commands.scoreboard.teams.empty.alreadyEmpty",
                        new Object[] {scorePlayerTeam.getRegisteredName()});
            }

            for (java.util.Iterator<?> __recoveryIterator171_60676 = (arrayList).iterator();
                    (__recoveryIterator171_60676).hasNext(); ) {
                String text = (String) (__recoveryIterator171_60676).next();
                scoreboard.removePlayerFromTeam(text, scorePlayerTeam);
                if (entityArrayValue != null) {
                    break;
                }
            }

            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.teams.empty.success",
                    new Object[] {arrayList.size(), scorePlayerTeam.getRegisteredName()});
        }
    }

    protected void resetPlayers(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        String text = getEntityName(commandSender, astring[i++]);
        Entity[] entityArrayValue = entityArray;
        if (astring.length > i) {
            ScoreObjective scoreObjective = this.getObjective(astring[i++], false);
            scoreboard.removeObjectiveFromEntity(text, scoreObjective);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.players.resetscore.success",
                    new Object[] {scoreObjective.getName(), text});
            if (entityArrayValue == null) {
                return;
            }
        }

        scoreboard.removeObjectiveFromEntity(text, (ScoreObjective) null);
        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.players.reset.success",
                new Object[] {text});
    }

    private static Exception method_bbe44e843daabb729dea(Exception exception) {
        return exception;
    }

    protected ScorePlayerTeam getTeam(String text) throws CommandException {
        Scoreboard scoreboard = this.getScoreboard();
        ScorePlayerTeam team = scoreboard.getTeam(text);
        if (team == null) {
            throw new CommandException(
                    "commands.scoreboard.teamNotFound", new Object[] {text});
        } else {
            return team;
        }
    }

    protected void joinTeam(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        String text;
        HashSet hashset;
        HashSet hashset1;
        label71:
        {
            Entity[] entityArray = CommandBlockLogic.GU();
            Scoreboard scoreboard = this.getScoreboard();
            text = astring[i++];
            Entity[] entityArrayValue = entityArray;
            hashset = Sets.newHashSet();
            hashset1 = Sets.newHashSet();
            if (commandSender instanceof PlayerEntity && i == astring.length) {
                String name = getCommandSenderAsPlayer(commandSender).getName();
                if (scoreboard.addPlayerToTeam(name, text)) {
                    hashset.add(name);
                    if (entityArrayValue == null) {
                        break label71;
                    }
                }

                hashset1.add(name);
                if (entityArrayValue == null) {
                    break label71;
                }
            }

            label70:
            while (i < astring.length) {
                String textValue = astring[i++];
                if (textValue.startsWith(packedTextFragments[1])) {
                    List list = method_6c2108a61cf6b9b04dde(commandSender, textValue);
                    Iterator iterator = list.iterator();

                    while (true) {
                        if (iterator.hasNext()) {
                            {
                                Entity entity = (Entity) iterator.next();
                                String textValue2 =
                                        getEntityName(
                                                commandSender, entity.getUniqueId().toString());
                                if (scoreboard.addPlayerToTeam(textValue2, text)) {
                                    hashset.add(textValue2);
                                    if (!(entityArrayValue == null)) {

                                        hashset1.add(textValue2);
                                    }
                                } else {

                                    hashset1.add(textValue2);
                                }
                            }

                            if (entityArrayValue == null) {
                                continue;
                            }
                        }

                        if (entityArrayValue != null) {
                            break;
                        }
                        continue label70;
                    }
                }

                {
                    String textValue3 = getEntityName(commandSender, textValue);
                    if (scoreboard.addPlayerToTeam(textValue3, text)) {
                        hashset.add(textValue3);
                        if (!(entityArrayValue == null)) {

                            hashset1.add(textValue3);
                        }
                    } else {

                        hashset1.add(textValue3);
                    }
                }

                if (entityArrayValue != null) {
                    break;
                }
            }
        }

        if (!hashset.isEmpty()) {
            commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ENTITIES, hashset.size());
            notifyOperators(
                    commandSender,
                    this,
                    "commands.scoreboard.teams.join.success",
                    new Object[] {
                        hashset.size(),
                        text,
                        joinNiceString(hashset.toArray(new String[0]))
                    });
        }

        if (!hashset1.isEmpty()) {
            throw new CommandException(
                    "commands.scoreboard.teams.join.failure",
                    new Object[] {
                        hashset1.size(),
                        text,
                        joinNiceString(hashset1.toArray(new String[0]))
                    });
        }
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    static {
        net.minecraft.command.server.CommandScoreboard.packedTextFragments = new String[12];
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[0] = new String("*");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[1] = new String("@");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[2] = new String("@");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[3] = new String("*");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[4] = new String("*");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[5] = new String("=");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[6] = new String("<");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[7] = new String(">");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[8] = new String("=");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[9] = new String("<");
        net.minecraft.command.server.CommandScoreboard.packedTextFragments[10] = new String(">");
    }

    protected void setPlayer(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        String text = astring[i - 1];
        int j = i;
        Entity[] entityArray = CommandBlockLogic.GU();
        String textValue = getEntityName(commandSender, astring[i++]);
        Entity[] entityArrayValue = entityArray;
        ScoreObjective scoreObjective = this.getObjective(astring[i++], true);
        int k =
                text.equalsIgnoreCase("set")
                        ? parseInt(astring[i++])
                        : parseInt(astring[i++], 0);
        if (astring.length > i) {
            Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[j]);

            try {
                CompoundTag compoundTag =
                        JsonToNBT.getTagFromJson(buildString(astring, i));
                CompoundTag compoundTagValue = new CompoundTag();
                entity.writeToNBT(compoundTagValue);
                if (!CommandTestForBlock.method_b1e94d9ac0e51bb728b9(
                        compoundTag, compoundTagValue, true)) {
                    throw new CommandException(
                            "commands.scoreboard.players.set.tagMismatch",
                            new Object[] {textValue});
                }
            } catch (NBTException nbtException) {
                throw new CommandException(
                        "commands.scoreboard.players.set.tagError",
                        new Object[] {nbtException.getMessage()});
            }
        }

        Score score;
        label41:
        {
            Scoreboard scoreboard = this.getScoreboard();
            score = scoreboard.getValueFromObjective(textValue, scoreObjective);
            if (text.equalsIgnoreCase("set")) {
                score.setScorePoints(k);
                if (entityArrayValue == null) {
                    break label41;
                }
            }

            if (text.equalsIgnoreCase("add")) {
                score.increseScore(k);
                if (entityArrayValue == null) {
                    break label41;
                }
            }

            score.decreaseScore(k);
        }

        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.players.set.success",
                new Object[] {scoreObjective.getName(), textValue, score.getScorePoints()});
    }

    protected void operatePlayers(CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        Scoreboard scoreboard = this.getScoreboard();
        Entity[] entityArrayValue = entityArray;
        String text = getEntityName(commandSender, astring[i++]);
        ScoreObjective scoreObjective = this.getObjective(astring[i++], true);
        String textValue = astring[i++];
        String textValue2 = getEntityName(commandSender, astring[i++]);
        ScoreObjective scoreObjectiveValue = this.getObjective(astring[i], false);
        Score valueFromObjective = scoreboard.getValueFromObjective(text, scoreObjective);
        if (!scoreboard.entityHasObjective(textValue2, scoreObjectiveValue)) {
            throw new CommandException(
                    "commands.scoreboard.players.operation.notFound",
                    new Object[] {scoreObjectiveValue.getName(), textValue2});
        }

        label88:
        {
            Score valueFromObjectiveValue =
                    scoreboard.getValueFromObjective(textValue2, scoreObjectiveValue);
            if (textValue.equals("+=")) {
                valueFromObjective.setScorePoints(
                        valueFromObjective.getScorePoints()
                                + valueFromObjectiveValue.getScorePoints());
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals("-=")) {
                valueFromObjective.setScorePoints(
                        valueFromObjective.getScorePoints()
                                - valueFromObjectiveValue.getScorePoints());
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals("*=")) {
                valueFromObjective.setScorePoints(
                        valueFromObjective.getScorePoints()
                                * valueFromObjectiveValue.getScorePoints());
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals("/=")) {
                if (valueFromObjectiveValue.getScorePoints() == 0) {
                    break label88;
                }

                valueFromObjective.setScorePoints(
                        valueFromObjective.getScorePoints()
                                / valueFromObjectiveValue.getScorePoints());
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals("%=")) {
                if (valueFromObjectiveValue.getScorePoints() == 0) {
                    break label88;
                }

                valueFromObjective.setScorePoints(
                        valueFromObjective.getScorePoints()
                                % valueFromObjectiveValue.getScorePoints());
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals(packedTextFragments[5])) {
                valueFromObjective.setScorePoints(valueFromObjectiveValue.getScorePoints());
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals(packedTextFragments[6])) {
                valueFromObjective.setScorePoints(
                        Math.min(
                                valueFromObjective.getScorePoints(),
                                valueFromObjectiveValue.getScorePoints()));
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (textValue.equals(packedTextFragments[7])) {
                valueFromObjective.setScorePoints(
                        Math.max(
                                valueFromObjective.getScorePoints(),
                                valueFromObjectiveValue.getScorePoints()));
                if (entityArrayValue == null) {
                    break label88;
                }
            }

            if (!textValue.equals("><")) {
                throw new CommandException(
                        "commands.scoreboard.players.operation.invalidOperation",
                        new Object[] {textValue});
            }

            int j = valueFromObjective.getScorePoints();
            valueFromObjective.setScorePoints(valueFromObjectiveValue.getScorePoints());
            valueFromObjectiveValue.setScorePoints(j);
        }

        notifyOperators(
                commandSender,
                this,
                "commands.scoreboard.players.operation.success",
                new Object[0]);
    }

    public String getCommandName() {
        return "scoreboard";
    }
}
