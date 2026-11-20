package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase {


    public String getCommandUsage(CommandSender commandSender) {
        return "commands.downfall.usage";
    }

    public String getCommandName() {
        return "toggledownfall";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        this.toggleDownfall();
        notifyOperators(
                commandSender, this, "commands.downfall.success", new Object[0]);
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    protected void toggleDownfall() {
        WorldInfo worldInfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
        worldInfo.setRaining(!worldInfo.isRaining());
    }
}
