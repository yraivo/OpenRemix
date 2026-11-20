package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandSetSpawnpoint extends CommandBase {

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        if (astring.length > 0 && astring.length < 4) {
            throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
        }

        EntityPlayerMP entityPlayerMP =
                astring.length > 0
                        ? getPlayer(commandSender, astring[0])
                        : getCommandSenderAsPlayer(commandSender);
        BlockPos blockPos =
                astring.length > 3
                        ? parseBlockPos(commandSender, astring, 1, true)
                        : entityPlayerMP.getPosition();
        if (entityPlayerMP.world != null) {
            entityPlayerMP.setSpawnPoint(blockPos, true);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.spawnpoint.success",
                    new Object[] {
                        entityPlayerMP.getName(), blockPos.getX(), blockPos.getY(), blockPos.getZ()
                    });
        }
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }


    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : (astring.length > 1 && astring.length <= 4
                        ? method_cb1cf55a8a3937d053ae(astring, 1, blockPos)
                        : null);
    }

    public String getCommandName() {
        return "spawnpoint";
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.spawnpoint.usage";
    }
}
