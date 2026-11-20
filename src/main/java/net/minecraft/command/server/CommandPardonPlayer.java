package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import com.mojang.authlib.GameProfile;

import java.util.List;

public class CommandPardonPlayer extends CommandBase {


    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return MinecraftServer.getServer()
                        .getConfigurationManager()
                        .getBannedPlayers()
                        .isLanServer()
                && super.canCommandSenderUseCommand(commandSender);
    }

    public String getCommandName() {
        return "pardon";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();

        {
            boolean runGuardedCode;
            try {
                if (false)
                    throw (net.minecraft.command.CommandException)
                            null; // Original JVM checked handler; no emitted instructions.
                runGuardedCode = !(astring.length != 1 || astring[0].length() <= 0);
            } catch (CommandException commandException) {
                throw method_94a99a47e223766d1a34(commandException);
            }
            if (runGuardedCode) {

                MinecraftServer server = MinecraftServer.getServer();
                GameProfile gameprofile =
                        server.getConfigurationManager()
                                .getBannedPlayers()
                                .isUsernameBanned(astring[0]);

                try {
                    if (gameprofile == null) {
                        throw new CommandException(
                                "commands.unban.failed", new Object[] {astring[0]});
                    }
                } catch (CommandException commandExceptionValue) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue);
                }

                try {
                    if (false)
                        throw (net.minecraft.command.CommandException)
                                null; // Original JVM checked handler; no emitted instructions.
                    server.getConfigurationManager().getBannedPlayers().removeEntry(gameprofile);
                    notifyOperators(
                            commandSender, this, "commands.unban.success", new Object[] {astring[0]});
                    if (entityArray == null) {
                        return;
                    }
                } catch (CommandException commandExceptionValue2) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue2);
                }
            }
        }

        try {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        } catch (CommandException commandExceptionValue3) {
            throw method_94a99a47e223766d1a34(commandExceptionValue3);
        }
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.unban.usage";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring,
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getBannedPlayers()
                                .getKeys())
                : null;
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
