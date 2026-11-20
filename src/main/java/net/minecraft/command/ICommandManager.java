package net.minecraft.command;

import net.minecraft.util.BlockPos;

import java.util.List;
import java.util.Map;

public interface ICommandManager {
    Map getCommands();

    List getTabCompletionOptions(CommandSender commandSender, String text, BlockPos blockPos);

    int executeCommand(CommandSender commandSender, String text);

    List getPossibleCommands(CommandSender commandSender);
}
