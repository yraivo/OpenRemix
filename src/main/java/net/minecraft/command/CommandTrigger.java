package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class CommandTrigger extends CommandBase {


    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.trigger.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length < 3) {
            throw new WrongUsageException("commands.trigger.usage", new Object[0]);
        }

        EntityPlayerMP entityPlayerMP;
        {
            if (commandSender instanceof EntityPlayerMP) {
                entityPlayerMP = (EntityPlayerMP) commandSender;
                if (!(!flag)) {

                    Entity commandSenderEntity = commandSender.getCommandSenderEntity();
                    if (!(commandSenderEntity instanceof EntityPlayerMP)) {
                        throw new CommandException(
                                "commands.trigger.invalidPlayer", new Object[0]);
                    }

                    entityPlayerMP = (EntityPlayerMP) commandSenderEntity;
                }
            } else {

                Entity commandSenderEntity = commandSender.getCommandSenderEntity();
                if (!(commandSenderEntity instanceof EntityPlayerMP)) {
                    throw new CommandException(
                            "commands.trigger.invalidPlayer", new Object[0]);
                }

                entityPlayerMP = (EntityPlayerMP) commandSenderEntity;
            }
        }

        Scoreboard scoreboard =
                MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        ScoreObjective objective = scoreboard.getObjective(astring[0]);
        if (objective != null && objective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
            int i = parseInt(astring[2]);
            if (!scoreboard.entityHasObjective(entityPlayerMP.getName(), objective)) {
                throw new CommandException(
                        "commands.trigger.invalidObjective", new Object[] {astring[0]});
            }

            Score valueFromObjective =
                    scoreboard.getValueFromObjective(entityPlayerMP.getName(), objective);
            if (valueFromObjective.isLocked()) {
                throw new CommandException(
                        "commands.trigger.disabled", new Object[] {astring[0]});
            }

            {
                if ("set".equals(astring[1])) {
                    valueFromObjective.setScorePoints(i);
                    if (!(!flag)) {

                        if (!"add".equals(astring[1])) {
                            throw new CommandException(
                                    "commands.trigger.invalidMode", new Object[] {astring[1]});
                        }

                        valueFromObjective.increseScore(i);
                    }
                } else {

                    if (!"add".equals(astring[1])) {
                        throw new CommandException(
                                "commands.trigger.invalidMode", new Object[] {astring[1]});
                    }

                    valueFromObjective.increseScore(i);
                }
            }

            valueFromObjective.setLocked(true);
            if (entityPlayerMP.theItemInWorldManager.isCreative()) {
                notifyOperators(
                        commandSender,
                        this,
                        "commands.trigger.success",
                        astring[0],
                        astring[1],
                        astring[2]);
            }

            if (!flag) {
                return;
            }
        }

        throw new CommandException(
                "commands.trigger.invalidObjective", new Object[] {astring[0]});
    }

    @Override
    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length != 1) {
            return astring.length == 2 ? getListOfStringsMatchingLastWord(astring, "add", "set") : null;
        }

        Scoreboard scoreboard =
                MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_30797 =
                        (scoreboard.getScoreObjectives()).iterator();
                (__recoveryIterator171_30797).hasNext(); ) {
            ScoreObjective scoreObjective = (ScoreObjective) (__recoveryIterator171_30797).next();
            if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
                arrayList.add(scoreObjective.getName());
            }

            if (flag) {
                break;
            }
        }

        return getListOfStringsMatchingLastWord(
                astring, ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()])));
    }

    public String getCommandName() {
        return "trigger";
    }
}
