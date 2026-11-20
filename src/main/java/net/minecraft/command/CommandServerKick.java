package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandServerKick extends CommandBase {

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.kick.usage";
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }


    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length >= 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }

    public String getCommandName() {
        return "kick";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length > 0 && astring[0].length() > 1) {
            EntityPlayerMP playerByUsername =
                    MinecraftServer.getServer()
                            .getConfigurationManager()
                            .getPlayerByUsername(astring[0]);
            String text = "Kicked by an operator.";
            byte b0 = 0;
            if (playerByUsername == null) {
                throw new PlayerNotFoundException();
            }

            if (astring.length >= 2) {
                text = getChatComponentFromNthArg(commandSender, astring, 1).getUnformattedText();
                b0 = 1;
            }

            {
                playerByUsername.playerNetServerHandler.kickPlayerFromServer(text);
                if (b0 != 0) {
                    notifyOperators(
                            commandSender,
                            this,
                            "commands.kick.success.reason",
                            new Object[] {playerByUsername.getName(), text});
                    if (!(flag)) {

                        notifyOperators(
                                commandSender,
                                this,
                                "commands.kick.success",
                                new Object[] {playerByUsername.getName()});
                    }
                } else {

                    notifyOperators(
                            commandSender,
                            this,
                            "commands.kick.success",
                            new Object[] {playerByUsername.getName()});
                }
            }

            if (flag) {
                return;
            }
        }

        throw new WrongUsageException("commands.kick.usage", new Object[0]);
    }
}
