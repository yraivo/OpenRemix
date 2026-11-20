package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandName() {
        return "save-off";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        MinecraftServer server = MinecraftServer.getServer();
        byte b0 = 0;
        Entity[] entityArray = CommandBlockLogic.GU();
        int i = 0;
        Entity[] entityArrayValue = entityArray;

        while (i < server.worldServers.length) {
            if (server.worldServers[i] != null) {
                WorldServer worldServer = server.worldServers[i];
                if (!worldServer.disableLevelSaving) {
                    worldServer.disableLevelSaving = true;
                    b0 = 1;
                }
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        if (b0 != 0) {
            notifyOperators(
                    commandSender, this, "commands.save.disabled", new Object[0]);
            if (entityArrayValue == null) {
                return;
            }
        }

        throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
    }


    public String getCommandUsage(CommandSender commandSender) {
        return "commands.save-off.usage";
    }
}
