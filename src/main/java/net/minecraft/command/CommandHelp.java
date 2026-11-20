package net.minecraft.command;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandHelp extends CommandBase {
    private static String[] packedTextFragments;

    protected List getSortedPossibleCommands(CommandSender commandSender) {
        List possibleCommands =
                MinecraftServer.getServer().getCommandManager().getPossibleCommands(commandSender);
        Collections.sort(possibleCommands);
        return possibleCommands;
    }

    private static NumberInvalidException method_9f78e2366144040b606d(
            NumberInvalidException numberInvalidException) {
        return numberInvalidException;
    }

    public List getCommandAliases() {
        return Arrays.asList(packedTextFragments[0]);
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.help.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        int i1 =
                remix.compat.compiler.JvmBooleanSlots.toInt(
                        CommandException.method_b417d7500ec59d69e373());
        List list = this.getSortedPossibleCommands(commandSender);

        int i = (list.size() - 1) / 7;

        boolean flag = remix.compat.compiler.JvmBooleanSlots.fromInt(i1);

        int j;
        try {
            label63:
            {
                try {
                    if (false)
                        throw (net.minecraft.command.NumberInvalidException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (astring.length == 0) {
                        i1 = 0;
                        break label63;
                    }
                } catch (NumberInvalidException numberInvalidException) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            method_9f78e2366144040b606d(numberInvalidException));
                }

                i1 = parseInt(astring[0], 1, i + 1) - 1;
            }

            j = i1;
        } catch (NumberInvalidException numberInvalidExceptionValue) {
            NumberInvalidException numberInvalidExceptionValue2 = numberInvalidExceptionValue;
            Map map = this.getCommands();
            ICommand iCommand = (ICommand) map.get(astring[0]);

            try {
                if (false)
                    throw (net.minecraft.command.NumberInvalidException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (iCommand != null) {
                    throw new WrongUsageException(
                            iCommand.getCommandUsage(commandSender), new Object[0]);
                }
            } catch (NumberInvalidException numberInvalidExceptionValue3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_9f78e2366144040b606d(numberInvalidExceptionValue3));
            }

            try {
                if (MathHelper.parseIntWithDefault(astring[0], -1) != -1) {
                    throw numberInvalidExceptionValue2;
                }
            } catch (NumberInvalidException numberInvalidExceptionValue4) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_9f78e2366144040b606d(numberInvalidExceptionValue4));
            }

            throw new CommandNotFoundException();
        }

        int k = Math.min((j + 1) * 7, list.size());
        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation("commands.help.header", new Object[] {j + 1, i + 1});
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        int l = j * 7;

        while (l < k) {
            ICommand iCommandValue = (ICommand) list.get(l);
            ChatComponentTranslation chatComponentTranslationValue =
                    new ChatComponentTranslation(
                            iCommandValue.getCommandUsage(commandSender), new Object[0]);
            chatComponentTranslationValue
                    .getChatStyle()
                    .setChatClickEvent(
                            new ClickEvent(
                                    ClickEventAction.SUGGEST_COMMAND,
                                    packedTextFragments[1]
                                            + iCommandValue.getCommandName()
                                            + packedTextFragments[2]));
            commandSender.addChatMessage(chatComponentTranslationValue);
            l++;
            if (!flag) {
                break;
            }
        }

        try {
            if (false)
                throw (net.minecraft.command.NumberInvalidException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (j != 0 || !(commandSender instanceof PlayerEntity)) {
                return;
            }
        } catch (NumberInvalidException numberInvalidExceptionValue5) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_9f78e2366144040b606d(numberInvalidExceptionValue5));
        }

        ChatComponentTranslation chatComponentTranslationValue2 =
                new ChatComponentTranslation("commands.help.footer", new Object[0]);
        chatComponentTranslationValue2.getChatStyle().setColor(EnumChatFormatting.GREEN);
        commandSender.addChatMessage(chatComponentTranslationValue2);
    }

    public String getCommandName() {
        return "help";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        if (astring.length == 1) {
            Set set = this.getCommands().keySet();
            return getListOfStringsMatchingLastWord(
                    astring, ((java.lang.String[]) set.toArray(new String[set.size()])));
        } else {
            return null;
        }
    }

    protected Map getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    static {
        net.minecraft.command.CommandHelp.packedTextFragments = new String[5];
        net.minecraft.command.CommandHelp.packedTextFragments[0] = new String("?");
        net.minecraft.command.CommandHelp.packedTextFragments[1] = new String("/");
        net.minecraft.command.CommandHelp.packedTextFragments[2] = new String(" ");
    }
}
