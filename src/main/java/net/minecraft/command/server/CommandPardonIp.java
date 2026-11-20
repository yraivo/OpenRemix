package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;
import java.util.regex.Matcher;

public class CommandPardonIp extends CommandBase {

    public String getCommandName() {
        return "pardon-ip";
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length == 1 && astring[0].length() > 1) {
            Matcher matcher = CommandBanIp.Ag.matcher(astring[0]);
            if (!matcher.matches()) {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }

            MinecraftServer.getServer()
                    .getConfigurationManager()
                    .getBannedIPs()
                    .removeEntry(astring[0]);
            notifyOperators(
                    commandSender, this, "commands.unbanip.success", new Object[] {astring[0]});
            if (entityArray != null) {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }

            if (entityArray == null) {
                return;
            }
        }

        throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring,
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getBannedIPs()
                                .getKeys())
                : null;
    }


    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()
                && super.canCommandSenderUseCommand(commandSender);
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.unbanip.usage";
    }
}
