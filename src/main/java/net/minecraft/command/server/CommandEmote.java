package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandEmote extends CommandBase {

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (astring.length <= 0) {
                throw new WrongUsageException("commands.me.usage", new Object[0]);
            }
        } catch (CommandException commandException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandException));
        }

        CommandSender commandSenderValue;
        String[] astring1;
        byte b0;
        byte b1;
        label22:
        {
            try {
                if (false)
                    throw (net.minecraft.command.CommandException)
                            null; // Original JVM checked handler; no emitted instructions.
                commandSenderValue = commandSender;
                astring1 = astring;
                b0 = 0;
                if (!(commandSender instanceof PlayerEntity)) {
                    b1 = 1;
                    break label22;
                }
            } catch (CommandException commandExceptionValue) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_94a99a47e223766d1a34(commandExceptionValue));
            }

            b1 = 0;
        }

        ChatComponent chatComponent =
                getChatComponentFromNthArg(
                        commandSenderValue,
                        astring1,
                        b0,
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
        MinecraftServer.getServer()
                .getConfigurationManager()
                .sendChatMsg(
                        new ChatComponentTranslation(
                                "chat.type.emote",
                                new Object[] {commandSender.getDisplayName(), chatComponent}));
    }

    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }


    public String getCommandUsage(CommandSender commandSender) {
        return "commands.me.usage";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return getListOfStringsMatchingLastWord(
                astring, MinecraftServer.getServer().getAllUsernames());
    }

    public String getCommandName() {
        return "me";
    }
}
