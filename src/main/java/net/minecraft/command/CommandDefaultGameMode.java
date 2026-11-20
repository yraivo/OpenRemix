package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettingsGameType;

public class CommandDefaultGameMode extends CommandGameMode {

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.defaultgamemode.usage";
    }

    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }

    protected void setGameType(WorldSettingsGameType worldSettingsGameType) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        MinecraftServer server = MinecraftServer.getServer();
        boolean flag = flag1;
        server.setGameType(worldSettingsGameType);
        if (server.getForceGamemode()) {
            for (java.util.Iterator<?> __recoveryIterator171_3072 =
                            (MinecraftServer.getServer().getConfigurationManager().playerEntityList)
                                    .iterator();
                    (__recoveryIterator171_3072).hasNext(); ) {
                EntityPlayerMP entityPlayerMP =
                        (EntityPlayerMP) (__recoveryIterator171_3072).next();
                entityPlayerMP.setGameType(worldSettingsGameType);
                entityPlayerMP.fallDistance = 0.0F;
                if (!flag) {
                    break;
                }
            }
        }
    }


    public String getCommandName() {
        return "defaultgamemode";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (astring.length <= 0) {
                throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
            }
        } catch (CommandException commandException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandException));
        }

        WorldSettingsGameType worldSettingsGameType =
                this.getGameModeFromCommand(commandSender, astring[0]);
        this.setGameType(worldSettingsGameType);
        notifyOperators(
                commandSender,
                this,
                "commands.defaultgamemode.success",
                new Object[] {
                    new ChatComponentTranslation(
                            "gameMode." + worldSettingsGameType.getName(), new Object[0])
                });
    }
}
