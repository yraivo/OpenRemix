package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase {

    public int getRequiredPermissionLevel() {
        return 3;
    }


    public String getCommandName() {
        return "setidletimeout";
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.setidletimeout.usage";
    }

    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (astring.length != 1) {
                throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
            }
        } catch (CommandException commandException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandException));
        }

        int i = parseInt(astring[0], 0);
        MinecraftServer.getServer().setPlayerIdleTimeout(i);
        notifyOperators(commandSender, this, "commands.setidletimeout.success", new Object[] {i});
    }
}
