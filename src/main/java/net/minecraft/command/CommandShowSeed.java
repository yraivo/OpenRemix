package net.minecraft.command;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Object object1;
        label16:
        {
            try {
                if (false)
                    throw (net.minecraft.command.CommandException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (commandSender instanceof PlayerEntity) {
                    object1 = ((PlayerEntity) commandSender).world;
                    break label16;
                }
            } catch (CommandException commandException) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_94a99a47e223766d1a34(commandException));
            }

            object1 = MinecraftServer.getServer().worldServerForDimension(0);
        }

        Object object = object1;
        commandSender.addChatMessage(
                new ChatComponentTranslation(
                        "commands.seed.success", new Object[] {((World) object).getSeed()}));
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.seed.usage";
    }


    public String getCommandName() {
        return "seed";
    }

    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return MinecraftServer.getServer().isSinglePlayer()
                || super.canCommandSenderUseCommand(commandSender);
    }
}
