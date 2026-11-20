package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBanIp extends CommandBase {

    public static Pattern Ag;

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length >= 1 && astring[0].length() > 1) {
            label44:
            {
                ChatComponent chatComponent =
                        astring.length >= 2
                                ? getChatComponentFromNthArg(commandSender, astring, 1)
                                : null;
                Matcher matcher = Ag.matcher(astring[0]);
                if (matcher.matches()) {
                    this.method_7862b1d0c309e2235cbf(
                            commandSender,
                            astring[0],
                            chatComponent == null ? null : chatComponent.getUnformattedText());
                    if (entityArray == null) {
                        break label44;
                    }
                }

                EntityPlayerMP playerByUsername =
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getPlayerByUsername(astring[0]);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }

                this.method_7862b1d0c309e2235cbf(
                        commandSender,
                        playerByUsername.getPlayerIP(),
                        chatComponent == null ? null : chatComponent.getUnformattedText());
            }

            if (entityArray == null) {
                return;
            }
        }

        throw new WrongUsageException("commands.banip.usage", new Object[0]);
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }

    protected void method_7862b1d0c309e2235cbf(
            CommandSender commandSender, String text, String textValue) {
        Entity[] entityArray = CommandBlockLogic.GU();
        IPBanEntry ipBanEntry =
                new IPBanEntry(text, (Date) null, commandSender.getName(), (Date) null, textValue);
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry(ipBanEntry);
        List list = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(text);
        String[] astring = new String[list.size()];
        int i = 0;
        Entity[] entityArrayValue = entityArray;

        for (java.util.Iterator<?> __recoveryIterator171_3631 = (list).iterator();
                (__recoveryIterator171_3631).hasNext(); ) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) (__recoveryIterator171_3631).next();
            entityPlayerMP.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            astring[i++] = entityPlayerMP.getName();
            if (entityArrayValue != null) {
                break;
            }
        }

        if (list.isEmpty()) {
            notifyOperators(
                    commandSender, this, "commands.banip.success", new Object[] {text});
            if (entityArrayValue == null) {
                return;
            }
        }

        notifyOperators(
                commandSender,
                this,
                "commands.banip.success.players",
                new Object[] {text, joinNiceString(astring)});
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.banip.usage";
    }

    static {
        Ag =
                Pattern.compile(
                        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()
                && super.canCommandSenderUseCommand(commandSender);
    }

    public String getCommandName() {
        return "ban-ip";
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
