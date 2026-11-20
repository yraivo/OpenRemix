package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandListBans extends CommandBase {

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, new String[] {"players", "ips"})
                : null;
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.banlist.usage";
    }

    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()
                        || MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getBannedPlayers()
                                .isLanServer())
                && super.canCommandSenderUseCommand(commandSender);
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public String getCommandName() {
        return "banlist";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length >= 1 && astring[0].equalsIgnoreCase("ips")) {
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.banlist.ips",
                            new Object[] {
                                MinecraftServer.getServer()
                                        .getConfigurationManager()
                                        .getBannedIPs()
                                        .getKeys()
                                        .length
                            }));
            commandSender.addChatMessage(
                    new ChatComponentText(
                            joinNiceString(
                                    MinecraftServer.getServer()
                                            .getConfigurationManager()
                                            .getBannedIPs()
                                            .getKeys())));
            if (entityArray == null) {
                return;
            }
        }

        commandSender.addChatMessage(
                new ChatComponentTranslation(
                        "commands.banlist.players",
                        new Object[] {
                            MinecraftServer.getServer()
                                    .getConfigurationManager()
                                    .getBannedPlayers()
                                    .getKeys()
                                    .length
                        }));
        commandSender.addChatMessage(
                new ChatComponentText(
                        joinNiceString(
                                MinecraftServer.getServer()
                                        .getConfigurationManager()
                                        .getBannedPlayers()
                                        .getKeys())));
    }


}
