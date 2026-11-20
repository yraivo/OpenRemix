package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.List;

public class CommandMessage extends CommandBase {
    private static String[] packedTextFragments;

    public List getCommandAliases() {
        return Arrays.asList(packedTextFragments[0], "msg");
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();

        try {
            if (astring.length < 2) {
                throw new WrongUsageException("commands.message.usage", new Object[0]);
            }
        } catch (CommandException commandException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandException));
        }

        EntityPlayerMP entityPlayerMP = getPlayer(commandSender, astring[0]);

        try {
            if (entityPlayerMP == commandSender) {
                throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
            }
        } catch (CommandException commandExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandExceptionValue));
        }

        CommandSender commandSenderValue;
        String[] astring1;
        byte b0;
        byte b1;
        label39:
        {
            try {
                if (false)
                    throw (net.minecraft.command.CommandException)
                            null; // Original JVM checked handler; no emitted instructions.
                commandSenderValue = commandSender;
                astring1 = astring;
                b0 = 1;
                if (!(commandSender instanceof PlayerEntity)) {
                    b1 = 1;
                    break label39;
                }
            } catch (CommandException commandExceptionValue2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_94a99a47e223766d1a34(commandExceptionValue2));
            }

            b1 = 0;
        }

        ChatComponent chatComponent =
                getChatComponentFromNthArg(
                        commandSenderValue,
                        astring1,
                        b0,
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation(
                        "commands.message.display.incoming",
                        new Object[] {commandSender.getDisplayName(), chatComponent.createCopy()});
        ChatComponentTranslation chatComponentTranslationValue =
                new ChatComponentTranslation(
                        "commands.message.display.outgoing",
                        new Object[] {entityPlayerMP.getDisplayName(), chatComponent.createCopy()});

        try {
            if (false)
                throw (net.minecraft.command.CommandException)
                        null; // Original JVM checked handler; no emitted instructions.
            chatComponentTranslation
                    .getChatStyle()
                    .setColor(EnumChatFormatting.GRAY)
                    .setItalic(true);
            chatComponentTranslationValue
                    .getChatStyle()
                    .setColor(EnumChatFormatting.GRAY)
                    .setItalic(true);
            entityPlayerMP.addChatMessage(chatComponentTranslation);
            commandSender.addChatMessage(chatComponentTranslationValue);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                CommandBlockLogic.b(new Entity[4]);
            }
        } catch (CommandException commandExceptionValue3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_94a99a47e223766d1a34(commandExceptionValue3));
        }
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.message.usage";
    }

    private static CommandException method_94a99a47e223766d1a34(CommandException commandException) {
        return commandException;
    }

    public String getCommandName() {
        return "tell";
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    static {
        net.minecraft.command.server.CommandMessage.packedTextFragments = new String[3];
        net.minecraft.command.server.CommandMessage.packedTextFragments[0] = new String("w");
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return getListOfStringsMatchingLastWord(
                astring, MinecraftServer.getServer().getAllUsernames());
    }
}
