package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandBroadcast extends CommandBase {

    public int getRequiredPermissionLevel() {
        return 1;
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
                runGuardedCode = !(astring.length <= 0 || astring[0].length() <= 0);
            } catch (CommandException commandException) {
                throw method_94a99a47e223766d1a34(commandException);
            }
            if (runGuardedCode) {

                ChatComponent chatComponent =
                        getChatComponentFromNthArg(commandSender, astring, 0, true);

                try {
                    if (false)
                        throw (net.minecraft.command.CommandException)
                                null; // Original JVM checked handler; no emitted instructions.
                    MinecraftServer.getServer()
                            .getConfigurationManager()
                            .sendChatMsg(
                                    new ChatComponentTranslation(
                                            "chat.type.announcement",
                                            new Object[] {
                                                commandSender.getDisplayName(), chatComponent
                                            }));
                    if (entityArray == null) {
                        return;
                    }
                } catch (CommandException commandExceptionValue) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue);
                }
            }
        }

        try {
            throw new WrongUsageException("commands.say.usage", new Object[0]);
        } catch (CommandException commandExceptionValue2) {
            throw method_94a99a47e223766d1a34(commandExceptionValue2);
        }
    }

    public String getCommandName() {
        return "say";
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.say.usage";
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length >= 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }
}
