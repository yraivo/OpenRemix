package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettingsGameType;

public class CommandPublishLocalServer extends CommandBase {

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        String text = MinecraftServer.getServer().shareToLAN(WorldSettingsGameType.SURVIVAL, false);
        Entity[] entityArrayValue = entityArray;
        if (text != null) {
            notifyOperators(
                    commandSender, this, "commands.publish.started", new Object[] {text});
            if (entityArrayValue == null) {
                return;
            }
        }

        notifyOperators(commandSender, this, "commands.publish.failed", new Object[0]);
    }


    public String getCommandName() {
        return "publish";
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.publish.usage";
    }
}
