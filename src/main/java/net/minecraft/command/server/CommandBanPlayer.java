package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.BlockPos;

import com.mojang.authlib.GameProfile;

import java.util.Date;
import java.util.List;

public class CommandBanPlayer extends CommandBase {

    public String getCommandName() {
        return "ban";
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
                runGuardedCode = !(astring.length < 1 || astring[0].length() <= 0);
            } catch (CommandException commandException) {
                throw method_94a99a47e223766d1a34(commandException);
            }
            if (runGuardedCode) {

                MinecraftServer server = MinecraftServer.getServer();
                GameProfile gameprofile =
                        server.getPlayerProfileCache().getGameProfileForUsername(astring[0]);

                try {
                    if (gameprofile == null) {
                        throw new CommandException(
                                "commands.ban.failed", new Object[] {astring[0]});
                    }
                } catch (CommandException commandExceptionValue) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue);
                }

                String text = null;
                if (astring.length >= 2) {
                    text = getChatComponentFromNthArg(commandSender, astring, 1).getUnformattedText();
                }

                UserListBansEntry userListBansEntry =
                        new UserListBansEntry(
                                gameprofile, (Date) null, commandSender.getName(), (Date) null, text);
                server.getConfigurationManager().getBannedPlayers().addEntry(userListBansEntry);
                EntityPlayerMP playerByUsername =
                        server.getConfigurationManager().getPlayerByUsername(astring[0]);

                try {
                    if (false)
                        throw (net.minecraft.command.CommandException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (playerByUsername != null) {
                        playerByUsername.playerNetServerHandler.kickPlayerFromServer(
                                "You are banned from this server.");
                    }
                } catch (CommandException commandExceptionValue2) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue2);
                }

                try {
                    if (false)
                        throw (net.minecraft.command.CommandException)
                                null; // Original JVM checked handler; no emitted instructions.
                    notifyOperators(
                            commandSender, this, "commands.ban.success", new Object[] {astring[0]});
                    if (entityArray == null) {
                        return;
                    }
                } catch (CommandException commandExceptionValue3) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue3);
                }
            }
        }

        try {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        } catch (CommandException commandExceptionValue4) {
            throw method_94a99a47e223766d1a34(commandExceptionValue4);
        }
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return MinecraftServer.getServer()
                        .getConfigurationManager()
                        .getBannedPlayers()
                        .isLanServer()
                && super.canCommandSenderUseCommand(commandSender);
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.ban.usage";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length >= 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }


    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
