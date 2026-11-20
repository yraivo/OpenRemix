package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CommandHandler implements ICommandManager {
    private Set commandSet;

    private final Map commandMap = Maps.newHashMap();

    private static String[] packedTextFragments;
    private static Logger logger;

    public ICommand registerCommand(
            ICommand iCommand) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        this.commandMap.put(
                iCommand.getCommandName(),
                iCommand);
        this.commandSet.add(iCommand);

        for (java.util.Iterator<?> __recoveryIterator171_24898 =
                        (iCommand.getCommandAliases()).iterator();
                (__recoveryIterator171_24898).hasNext(); ) {
            String text = (String) (__recoveryIterator171_24898).next();
            ICommand iCommandValue =
                    (ICommand) this.commandMap.get(text);
            if (iCommandValue == null
                    || !iCommandValue
                            .getCommandName()
                            .equals(text)) {
                this.commandMap.put(text, iCommand);
            }

            if (flag) {
                break;
            }
        }
    

        return iCommand;
    }

    public List getTabCompletionOptions(
            CommandSender commandSender, String text, BlockPos blockPos) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        String[] astring = text.split(packedTextFragments[3], -1);
        boolean flag = flag1;
        String textValue = astring[0];
        if (astring.length != 1) {
            if (astring.length > 1) {
                ICommand iCommand =
                        (ICommand) this.commandMap.get(textValue);
                if (iCommand != null
                        && iCommand.canCommandSenderUseCommand(commandSender)) {
                    return iCommand.addTabCompletionOptions(
                            commandSender, dropFirstString(astring), blockPos);
                }
            }

            return null;
        } else {
            ArrayList arrayList = Lists.newArrayList();

            for (java.util.Iterator<?> __recoveryIterator171_26469 =
                            (this.commandMap.entrySet()).iterator();
                    (__recoveryIterator171_26469).hasNext(); ) {
                Entry entry = (Entry) (__recoveryIterator171_26469).next();
                if (CommandBase.doesStringStartWith(textValue, (String) entry.getKey())
                        && ((ICommand) entry.getValue())
                                .canCommandSenderUseCommand(commandSender)) {
                    arrayList.add(entry.getKey());
                }

                if (flag) {
                    break;
                }
            }

            return arrayList;
        }
    }

    private static String[] dropFirstString(String[] astring) {
        String[] astring1 = new String[astring.length - 1];
        System.arraycopy(astring, 1, astring1, 0, astring.length - 1);
        return astring1;
    }

    public List getPossibleCommands(CommandSender commandSender) {
        ArrayList arrayList = Lists.newArrayList();
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        Iterator iterator = this.commandSet.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            ICommand iCommand =
                    (ICommand) iterator.next();
            if (iCommand.canCommandSenderUseCommand(commandSender)) {
                arrayList.add(iCommand);
            }

            if (flag) {
                break;
            }
        }

        return arrayList;
    }

    static {
        net.minecraft.command.CommandHandler.packedTextFragments = new String[6];
        net.minecraft.command.CommandHandler.packedTextFragments[0] = new String("/");
        net.minecraft.command.CommandHandler.packedTextFragments[1] = new String(" ");
        net.minecraft.command.CommandHandler.packedTextFragments[2] = new String("'");
        net.minecraft.command.CommandHandler.packedTextFragments[3] = new String(" ");
        net.minecraft.command.CommandHandler.logger = LogManager.getLogger();
    }

    public Map getCommands() {
        return this.commandMap;
    }

    public CommandHandler() {
        this.commandSet = Sets.newHashSet();
    }

    protected boolean tryExecute(
            CommandSender commandSender,
            String[] astring,
            ICommand iCommand,
            String text) {
        try {
            iCommand.processCommand(commandSender, astring);
            return true;
        } catch (WrongUsageException wrongUsageException) {
            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation(
                            "commands.generic.usage",
                            new Object[] {
                                new ChatComponentTranslation(
                                        wrongUsageException.getMessage(),
                                        wrongUsageException.getErrorObjects())
                            });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
        } catch (CommandException commandException) {
            ChatComponentTranslation chatComponentTranslationValue =
                    new ChatComponentTranslation(
                            commandException.getMessage(),
                            commandException.getErrorObjects());
            chatComponentTranslationValue.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslationValue);
        } catch (Throwable throwable) {
            ChatComponentTranslation chatComponentTranslationValue2 =
                    new ChatComponentTranslation("commands.generic.exception", new Object[0]);
            chatComponentTranslationValue2.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslationValue2);
            net.minecraft.command.CommandHandler.logger.error(
                    "Couldn't process command: '" + text + packedTextFragments[2], throwable);
        }

        return false;
    }

    public int executeCommand(CommandSender commandSender, String text) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        text = text.trim();
        boolean flag = flag1;
        if (text.startsWith(packedTextFragments[0])) {
            text = text.substring(1);
        }

        int j;
        label48:
        {
            String[] astring = text.split(packedTextFragments[1]);
            String textValue = astring[0];
            astring = dropFirstString(astring);
            ICommand iCommand =
                    (ICommand) this.commandMap.get(textValue);
            int i = this.getUsernameIndex(iCommand, astring);
            j = 0;
            if (iCommand == null) {
                ChatComponentTranslation chatComponentTranslation =
                        new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                commandSender.addChatMessage(chatComponentTranslation);
                if (!flag) {
                    break label48;
                }
            }

            if (iCommand.canCommandSenderUseCommand(commandSender)) {
                if (i > -1) {
                    List list =
                            PlayerSelector.matchEntities(
                                    commandSender, astring[i], Entity.class);
                    String textValue2 = astring[i];
                    commandSender.setCommandStat(
                            CommandResultStatsType.AFFECTED_ENTITIES, list.size());

                    for (java.util.Iterator<?> __recoveryIterator171_35993 = (list).iterator();
                            (__recoveryIterator171_35993).hasNext(); ) {
                        Entity entity = (Entity) (__recoveryIterator171_35993).next();
                        astring[i] = entity.getUniqueId().toString();
                        if (this.tryExecute(
                                commandSender, astring, iCommand, text)) {
                            j++;
                        }

                        if (flag) {
                            break;
                        }
                    }

                    astring[i] = textValue2;
                    if (!flag) {
                        break label48;
                    }
                }

                commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ENTITIES, 1);
                if (!this.tryExecute(
                        commandSender, astring, iCommand, text)) {
                    break label48;
                }

                j++;
                if (!flag) {
                    break label48;
                }
            }

            ChatComponentTranslation chatComponentTranslationValue =
                    new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            chatComponentTranslationValue.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslationValue);
        }

        commandSender.setCommandStat(CommandResultStatsType.SUCCESS_COUNT, j);
        return j;
    }

    private int getUsernameIndex(
            ICommand iCommand, String[] astring) {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (iCommand == null) {
            return -1;
        }

        int i = 0;

        while (i < astring.length) {
            if (iCommand.isUsernameIndex(astring, i)
                    && PlayerSelector.matchesMultiplePlayers(astring[i])) {
                return i;
            }

            i++;
            if (flag) {
                break;
            }
        }

        return -1;
    }
}
