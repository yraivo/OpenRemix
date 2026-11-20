package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {

    public String getCommandName() {
        return "save-all";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        MinecraftServer server = MinecraftServer.getServer();
        Entity[] entityArray = CommandBlockLogic.GU();
        commandSender.addChatMessage(
                new ChatComponentTranslation("commands.save.start", new Object[0]));
        Entity[] entityArrayValue = entityArray;

        try {
            if (false)
                throw (net.minecraft.world.MinecraftException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (server.getConfigurationManager() != null) {
                server.getConfigurationManager().saveAllPlayerData();
            }
        } catch (MinecraftException minecraftException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_11f45e6fdfaec9360f1d(minecraftException));
        }

        try {
            {
                int i = 0;

                while (i < server.worldServers.length) {
                    if (server.worldServers[i] != null) {
                        WorldServer worldServer = server.worldServers[i];
                        boolean aAY = worldServer.disableLevelSaving;
                        worldServer.disableLevelSaving = false;
                        worldServer.saveAllChunks(true, (IProgressUpdate) null);
                        worldServer.disableLevelSaving = aAY;
                    }

                    i++;
                    if (entityArrayValue != null) {
                        break;
                    }
                }

                boolean runGuardedCode;
                try {
                    if (false)
                        throw (net.minecraft.world.MinecraftException)
                                null; // Original JVM checked handler; no emitted instructions.
                    runGuardedCode = !(astring.length <= 0 || !"flush".equals(astring[0]));
                } catch (MinecraftException minecraftExceptionValue) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            method_11f45e6fdfaec9360f1d(minecraftExceptionValue));
                }
                if (runGuardedCode) {

                    commandSender.addChatMessage(
                            new ChatComponentTranslation(
                                    "commands.save.flushStart", new Object[0]));
                    i = 0;

                    while (i < server.worldServers.length) {
                        if (server.worldServers[i] != null) {
                            WorldServer worldServerValue = server.worldServers[i];
                            boolean aAYValue = worldServerValue.disableLevelSaving;
                            worldServerValue.disableLevelSaving = false;
                            worldServerValue.saveExtraChunkData();
                            worldServerValue.disableLevelSaving = aAYValue;
                        }

                        i++;
                        if (entityArrayValue != null) {
                            break;
                        }
                    }

                    commandSender.addChatMessage(
                            new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
                }
            }
        } catch (MinecraftException minecraftExceptionValue2) {
            notifyOperators(
                    commandSender,
                    this,
                    "commands.save.failed",
                    new Object[] {minecraftExceptionValue2.getMessage()});
            return;
        }

        notifyOperators(commandSender, this, "commands.save.success", new Object[0]);
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.save.usage";
    }


    private static MinecraftException method_11f45e6fdfaec9360f1d(
            MinecraftException minecraftException) {
        return minecraftException;
    }
}
