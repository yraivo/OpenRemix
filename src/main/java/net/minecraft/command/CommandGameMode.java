package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;

import java.util.List;

public class CommandGameMode extends CommandBase {
    private static String[] packedTextFragments;

    static {
        net.minecraft.command.CommandGameMode.packedTextFragments = new String[5];
        net.minecraft.command.CommandGameMode.packedTextFragments[0] = new String("s");
        net.minecraft.command.CommandGameMode.packedTextFragments[1] = new String("c");
        net.minecraft.command.CommandGameMode.packedTextFragments[2] = new String("a");
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.gamemode.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    protected WorldSettingsGameType getGameModeFromCommand(
            CommandSender commandSender, String text) throws CommandException {
        return text.equalsIgnoreCase(WorldSettingsGameType.SURVIVAL.getName())
                        || text.equalsIgnoreCase(packedTextFragments[0])
                ? WorldSettingsGameType.SURVIVAL
                : (text.equalsIgnoreCase(WorldSettingsGameType.CREATIVE.getName())
                                || text.equalsIgnoreCase(packedTextFragments[1])
                        ? WorldSettingsGameType.CREATIVE
                        : (text.equalsIgnoreCase(WorldSettingsGameType.ADVENTURE.getName())
                                        || text.equalsIgnoreCase(packedTextFragments[2])
                                ? WorldSettingsGameType.ADVENTURE
                                : (!text.equalsIgnoreCase(WorldSettingsGameType.SPECTATOR.getName())
                                                && !text.equalsIgnoreCase("sp")
                                        ? WorldSettings.getGameTypeById(
                                                parseInt(
                                                        text,
                                                        0,
                                                        WorldSettingsGameType.values().length - 2))
                                        : WorldSettingsGameType.SPECTATOR)));
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, new String[] {"survival", "creative", "adventure", "spectator"})
                : (astring.length == 2 ? getListOfStringsMatchingLastWord(astring, this.getListOfPlayerUsernames()) : null);
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length <= 0) {
            throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
        }

        WorldSettingsGameType worldSettingsGameType =
                this.getGameModeFromCommand(commandSender, astring[0]);
        EntityPlayerMP entityPlayerMP =
                astring.length >= 2
                        ? getPlayer(commandSender, astring[1])
                        : getCommandSenderAsPlayer(commandSender);
        entityPlayerMP.setGameType(worldSettingsGameType);
        entityPlayerMP.fallDistance = 0.0F;
        if (commandSender
                .getEntityWorld()
                .getGameRules()
                .getGameRuleBooleanValue("sendCommandFeedback")) {
            entityPlayerMP.addChatMessage(
                    new ChatComponentTranslation("gameMode.changed", new Object[0]));
        }

        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation(
                        "gameMode." + worldSettingsGameType.getName(), new Object[0]);
        if (entityPlayerMP != commandSender) {
            notifyOperators(
                    commandSender,
                    this,
                    1,
                    "commands.gamemode.success.other",
                    new Object[] {entityPlayerMP.getName(), chatComponentTranslation});
            if (flag) {
                return;
            }
        }

        notifyOperators(
                commandSender,
                this,
                1,
                "commands.gamemode.success.self",
                new Object[] {chatComponentTranslation});
    }

    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    private static CommandException method_dbec021beb6e924429e6(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandName() {
        return "gamemode";
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 1;
    }
}
