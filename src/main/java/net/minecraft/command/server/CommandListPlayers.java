package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListPlayers extends CommandBase {

    public String getCommandName() {
        return "list";
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.players.usage";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }


    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        int currentPlayerCount = MinecraftServer.getServer().getCurrentPlayerCount();
        commandSender.addChatMessage(
                new ChatComponentTranslation(
                        "commands.players.list",
                        new Object[] {
                            currentPlayerCount, MinecraftServer.getServer().getMaxPlayers()
                        }));
        commandSender.addChatMessage(
                new ChatComponentText(
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .method_cee993844cce4b97ed62()));
        commandSender.setCommandStat(CommandResultStatsType.QUERY_RESULT, currentPlayerCount);
    }
}
