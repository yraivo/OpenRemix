package net.minecraft.command;

import net.minecraft.util.BlockPos;

import java.util.List;

public interface ICommand extends Comparable {
    boolean canCommandSenderUseCommand(CommandSender commandSender);

    List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos);

    String getCommandUsage(CommandSender commandSender);

    String getCommandName();

    void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException;

    boolean isUsernameIndex(String[] astring, int i);

    List getCommandAliases();
}
