package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandXP extends CommandBase {

    private static String[] packedTextFragments;

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 1;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.xp.usage";
    }

    static {
        net.minecraft.command.CommandXP.packedTextFragments = new String[4];
        net.minecraft.command.CommandXP.packedTextFragments[0] = new String("l");
        net.minecraft.command.CommandXP.packedTextFragments[1] = new String("L");
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 2 ? getListOfStringsMatchingLastWord(astring, this.getAllUsernames()) : null;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length <= 0) {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }

        String text = astring[0];
        int i =
                !text.endsWith(packedTextFragments[0]) && !text.endsWith(packedTextFragments[1])
                        ? 0
                        : 1;
        if (i != 0 && text.length() > 1) {
            text = text.substring(0, text.length() - 1);
        }

        int j = parseInt(text);
        int k = j < 0 ? 1 : 0;
        if (k != 0) {
            j *= -1;
        }

        EntityPlayerMP entityPlayerMP =
                astring.length > 1
                        ? getPlayer(commandSender, astring[1])
                        : getCommandSenderAsPlayer(commandSender);
        if (i != 0) {
            commandSender.setCommandStat(
                    CommandResultStatsType.QUERY_RESULT, entityPlayerMP.experienceLevel);
            if (k != 0) {
                entityPlayerMP.addExperienceLevel(-j);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.xp.success.negative.levels",
                        new Object[] {j, entityPlayerMP.getName()});
                if (flag) {
                    return;
                }
            }

            entityPlayerMP.addExperienceLevel(j);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.xp.success.levels",
                    new Object[] {j, entityPlayerMP.getName()});
            if (flag) {
                return;
            }
        }

        commandSender.setCommandStat(
                CommandResultStatsType.QUERY_RESULT, entityPlayerMP.experienceTotal);
        if (k != 0) {
            throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
        }

        entityPlayerMP.addExperience(j);
        notifyOperators(
                commandSender,
                this,
                "commands.xp.success",
                new Object[] {j, entityPlayerMP.getName()});
    }

    public String getCommandName() {
        return "xp";
    }
}
