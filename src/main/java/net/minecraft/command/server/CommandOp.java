package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import java.util.ArrayList;
import java.util.List;

public class CommandOp extends CommandBase {

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length != 1) {
            return null;
        }

        String text = astring[astring.length - 1];
        ArrayList arrayList = Lists.newArrayList();

        for (GameProfile gameprofile : MinecraftServer.getServer().getGameProfiles()) {
            if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameprofile)
                    && doesStringStartWith(text, gameprofile.getName())) {
                arrayList.add(gameprofile.getName());
            }

            if (entityArray != null) {
                break;
            }
        }

        return arrayList;
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public String getCommandName() {
        return "op";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();

        {
            boolean runGuardedCode;
            try {
                if (false)
                    throw (net.minecraft.command.CommandException)
                            null; // Original JVM checked handler; no emitted instructions.
                runGuardedCode = !(astring.length != 1 || astring[0].length() <= 0);
            } catch (CommandException commandException) {
                throw method_94a99a47e223766d1a34(commandException);
            }
            if (runGuardedCode) {

                MinecraftServer server = MinecraftServer.getServer();
                GameProfile gameprofile =
                        server.getPlayerProfileCache().getGameProfileForUsername(astring[0]);

                try {
                    if (gameprofile == null) {
                        throw new CommandException(
                                "commands.op.failed", new Object[] {astring[0]});
                    }
                } catch (CommandException commandExceptionValue) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue);
                }

                try {
                    if (false)
                        throw (net.minecraft.command.CommandException)
                                null; // Original JVM checked handler; no emitted instructions.
                    server.getConfigurationManager().addOp(gameprofile);
                    notifyOperators(
                            commandSender, this, "commands.op.success", new Object[] {astring[0]});
                    if (entityArray == null) {
                        return;
                    }
                } catch (CommandException commandExceptionValue2) {
                    throw method_94a99a47e223766d1a34(commandExceptionValue2);
                }
            }
        }

        try {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        } catch (CommandException commandExceptionValue3) {
            throw method_94a99a47e223766d1a34(commandExceptionValue3);
        }
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.op.usage";
    }


    public int getRequiredPermissionLevel() {
        return 3;
    }
}
