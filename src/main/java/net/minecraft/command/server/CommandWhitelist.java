package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.mojang.authlib.GameProfile;

import java.util.List;

public class CommandWhitelist extends CommandBase {

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length < 1) {
            throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
        }

        MinecraftServer server = MinecraftServer.getServer();
        if (astring[0].equals("on")) {
            server.getConfigurationManager().setWhiteListEnabled(true);
            notifyOperators(
                    commandSender, this, "commands.whitelist.enabled", new Object[0]);
            if (entityArray == null) {
                return;
            }
        }

        if (astring[0].equals("off")) {
            server.getConfigurationManager().setWhiteListEnabled(false);
            notifyOperators(
                    commandSender, this, "commands.whitelist.disabled", new Object[0]);
            if (entityArray == null) {
                return;
            }
        }

        if (astring[0].equals("list")) {
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.whitelist.list",
                            new Object[] {
                                server.getConfigurationManager().getWhitelistedPlayerNames().length,
                                server.getConfigurationManager().getAvailablePlayerDat().length
                            }));
            String[] astring1 = server.getConfigurationManager().getWhitelistedPlayerNames();
            commandSender.addChatMessage(
                    new ChatComponentText(joinNiceString(astring1)));
            if (entityArray == null) {
                return;
            }
        }

        if (astring[0].equals("add")) {
            if (astring.length < 2) {
                throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
            }

            GameProfile gameprofile =
                    server.getPlayerProfileCache().getGameProfileForUsername(astring[1]);
            if (gameprofile == null) {
                throw new CommandException(
                        "commands.whitelist.add.failed", new Object[] {astring[1]});
            }

            server.getConfigurationManager().addWhitelistedPlayer(gameprofile);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.whitelist.add.success",
                    new Object[] {astring[1]});
            if (entityArray == null) {
                return;
            }
        }

        if (astring[0].equals("remove")) {
            if (astring.length < 2) {
                throw new WrongUsageException(
                        "commands.whitelist.remove.usage", new Object[0]);
            }

            GameProfile gameprofile1 =
                    server.getConfigurationManager()
                            .getWhitelistedPlayers()
                            .func_152706_a(astring[1]);
            if (gameprofile1 == null) {
                throw new CommandException(
                        "commands.whitelist.remove.failed", new Object[] {astring[1]});
            }

            server.getConfigurationManager().removePlayerFromWhitelist(gameprofile1);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.whitelist.remove.success",
                    new Object[] {astring[1]});
            if (entityArray == null) {
                return;
            }
        }

        if (astring[0].equals("reload")) {
            server.getConfigurationManager().iJ();
            notifyOperators(
                    commandSender, this, "commands.whitelist.reloaded", new Object[0]);
        }
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        if (astring.length == 1) {
            return getListOfStringsMatchingLastWord(
                    astring, new String[] {"on", "off", "list", "add", "remove", "reload"});
        }

        if (astring.length == 2) {
            if (astring[0].equals("remove")) {
                return getListOfStringsMatchingLastWord(
                        astring,
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getWhitelistedPlayerNames());
            }

            if (astring[0].equals("add")) {
                return getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getPlayerProfileCache().getUsernames());
            }
        }

        return null;
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.whitelist.usage";
    }


    public String getCommandName() {
        return "whitelist";
    }
}
