package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends CommandBase {

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.stop.usage";
    }

    public String getCommandName() {
        return "stop";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (false)
                throw (net.minecraft.command.CommandException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (MinecraftServer.getServer().worldServers != null) {
                notifyOperators(commandSender, this, "commands.stop.start", new Object[0]);
            }
        } catch (CommandException commandException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandException));
        }

        MinecraftServer.getServer().initiateShutdown();
    }


    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }
}
