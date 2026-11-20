package net.minecraft.command.server;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandAchievement extends CommandBase {

    private static String[] packedTextFragments;

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.achievement.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length < 2) {
            throw new WrongUsageException("commands.achievement.usage", new Object[0]);
        }

        StatBase oneShotStat = StatList.getOneShotStat(astring[1]);
        if (oneShotStat == null && !astring[1].equals(packedTextFragments[0])) {
            throw new CommandException(
                    "commands.achievement.unknownAchievement", new Object[] {astring[1]});
        }

        EntityPlayerMP entityPlayerMP =
                astring.length >= 3
                        ? getPlayer(commandSender, astring[2])
                        : getCommandSenderAsPlayer(commandSender);
        boolean flag = astring[0].equalsIgnoreCase("give");
        boolean flag1 = astring[0].equalsIgnoreCase("take");
        if (flag || flag1) {
            if (oneShotStat == null) {
                label147:
                {
                    if (flag) {
                        for (java.util.Iterator<?> __recoveryIterator171_5499 =
                                        (AchievementList.achievementList).iterator();
                                (__recoveryIterator171_5499).hasNext(); ) {
                            Achievement achievement =
                                    (Achievement) (__recoveryIterator171_5499).next();
                            entityPlayerMP.triggerAchievement(achievement);
                            if (entityArray != null) {
                                break;
                            }
                        }

                        notifyOperators(
                                commandSender,
                                this,
                                "commands.achievement.give.success.all",
                                new Object[] {entityPlayerMP.getName()});
                        if (entityArray == null) {
                            break label147;
                        }
                    }

                    if (flag1) {
                        for (java.util.Iterator<?> __recoveryIterator171_6339 =
                                        (Lists.reverse(AchievementList.achievementList)).iterator();
                                (__recoveryIterator171_6339).hasNext(); ) {
                            Achievement achievementValue =
                                    (Achievement) (__recoveryIterator171_6339).next();
                            entityPlayerMP.resetStat(achievementValue);
                            if (entityArray != null) {
                                break;
                            }
                        }

                        notifyOperators(
                                commandSender,
                                this,
                                "commands.achievement.take.success.all",
                                new Object[] {entityPlayerMP.getName()});
                    }
                }

                if (entityArray == null) {
                    return;
                }
            }

            label83:
            if (oneShotStat instanceof Achievement) {
                Achievement achievementValue2 = (Achievement) oneShotStat;
                if (flag) {
                    if (entityPlayerMP.getStatFile().hasAchievementUnlocked(achievementValue2)) {
                        throw new CommandException(
                                "commands.achievement.alreadyHave",
                                new Object[] {
                                    entityPlayerMP.getName(), oneShotStat.func_150955_j()
                                });
                    }

                    ArrayList arrayList = Lists.newArrayList();

                    while (achievementValue2.parentAchievement != null
                            && !entityPlayerMP
                                    .getStatFile()
                                    .hasAchievementUnlocked(achievementValue2.parentAchievement)) {
                        arrayList.add(achievementValue2.parentAchievement);
                        achievementValue2 = achievementValue2.parentAchievement;
                        if (entityArray != null) {
                            break;
                        }
                    }

                    Iterator iterator = Lists.reverse(arrayList).iterator();

                    do {
                        if (!iterator.hasNext()) {
                            break label83;
                        }

                        Achievement achievementValue3 = (Achievement) iterator.next();
                        entityPlayerMP.triggerAchievement(achievementValue3);
                    } while (entityArray == null);
                }

                if (flag1) {
                    if (!entityPlayerMP.getStatFile().hasAchievementUnlocked(achievementValue2)) {
                        throw new CommandException(
                                "commands.achievement.dontHave",
                                new Object[] {
                                    entityPlayerMP.getName(), oneShotStat.func_150955_j()
                                });
                    }

                    ArrayList arrayListValue =
                            Lists.newArrayList(
                                    Iterators.filter(
                                            AchievementList.achievementList.iterator(),
                                            new ProcessCommandPredicate(
                                                    this, entityPlayerMP, oneShotStat)));

                    while (achievementValue2.parentAchievement != null
                            && entityPlayerMP
                                    .getStatFile()
                                    .hasAchievementUnlocked(achievementValue2.parentAchievement)) {
                        arrayListValue.remove(achievementValue2.parentAchievement);
                        achievementValue2 = achievementValue2.parentAchievement;
                        if (entityArray != null) {
                            break;
                        }
                    }

                    for (java.util.Iterator<?> __recoveryIterator171_10007 =
                                    (arrayListValue).iterator();
                            (__recoveryIterator171_10007).hasNext(); ) {
                        Achievement achievementValue4 =
                                (Achievement) (__recoveryIterator171_10007).next();
                        entityPlayerMP.resetStat(achievementValue4);
                        if (entityArray != null) {
                            break;
                        }
                    }
                }
            }

            if (flag) {
                entityPlayerMP.triggerAchievement(oneShotStat);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.achievement.give.success.one",
                        new Object[] {entityPlayerMP.getName(), oneShotStat.func_150955_j()});
                if (entityArray == null) {
                    return;
                }
            }

            if (flag1) {
                entityPlayerMP.resetStat(oneShotStat);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.achievement.take.success.one",
                        new Object[] {oneShotStat.func_150955_j(), entityPlayerMP.getName()});
            }
        }
    }

    static {
        net.minecraft.command.server.CommandAchievement.packedTextFragments = new String[3];
        net.minecraft.command.server.CommandAchievement.packedTextFragments[0] = new String("*");
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 2;
    }

    public String getCommandName() {
        return "achievement";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length == 1) {
            return getListOfStringsMatchingLastWord(astring, new String[] {"give", "take"});
        }

        if (astring.length != 2) {
            return astring.length == 3
                    ? getListOfStringsMatchingLastWord(
                            astring, MinecraftServer.getServer().getAllUsernames())
                    : null;
        }

        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_36070 = (StatList.allStats).iterator();
                (__recoveryIterator171_36070).hasNext(); ) {
            StatBase statBase = (StatBase) (__recoveryIterator171_36070).next();
            arrayList.add(statBase.statId);
            if (entityArray != null) {
                break;
            }
        }

        return getListOfStringsMatchingLastWord(astring, arrayList);
    }

    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }

    // Helper used only by this class.
    public static class ProcessCommandPredicate implements com.google.common.base.Predicate {
        net.minecraft.command.server.CommandAchievement aYv;
        net.minecraft.stats.StatBase aYw;
        net.minecraft.entity.player.EntityPlayerMP aYx;

        public boolean apply(java.lang.Object object) {
            return this.method_500f2b52b77ba57f68bb((net.minecraft.stats.Achievement) object);
        }

        public boolean method_500f2b52b77ba57f68bb(net.minecraft.stats.Achievement achievement) {
            return this.aYx.getStatFile().hasAchievementUnlocked(achievement)
                    && achievement != this.aYw;
        }

        public ProcessCommandPredicate(
                net.minecraft.command.server.CommandAchievement aYvValue,
                net.minecraft.entity.player.EntityPlayerMP aYxValue,
                net.minecraft.stats.StatBase aYwValue) {
            this.aYv = aYvValue;
            this.aYx = aYxValue;
            this.aYw = aYwValue;
        }
    }
}
