package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandSetDefaultSpawnpoint extends CommandBase {

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        BlockPos blockPos;
        {
            Entity[] entityArray = CommandBlockLogic.GU();
            if (astring.length == 0) {
                blockPos = getCommandSenderAsPlayer(commandSender).getPosition();
                if (!(entityArray == null)) {

                    if (astring.length != 3 || commandSender.getEntityWorld() == null) {
                        throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
                    }

                    blockPos = parseBlockPos(commandSender, astring, 0, true);
                }
            } else {

                if (astring.length != 3 || commandSender.getEntityWorld() == null) {
                    throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
                }

                blockPos = parseBlockPos(commandSender, astring, 0, true);
            }
        }

        commandSender.getEntityWorld().setSpawnLocation(blockPos);
        MinecraftServer.getServer()
                .getConfigurationManager()
                .sendPacketToAllPlayers(new S05PacketSpawnPosition(blockPos));
        notifyOperators(
                commandSender,
                this,
                "commands.setworldspawn.success",
                new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.setworldspawn.usage";
    }


    public String getCommandName() {
        return "setworldspawn";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : null;
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
