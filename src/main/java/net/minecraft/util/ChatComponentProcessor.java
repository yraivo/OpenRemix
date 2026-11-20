package net.minecraft.util;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

import java.util.List;

public class ChatComponentProcessor {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.util.ChatComponentProcessor.packedTextFragments[0] = new String("*");
        net.minecraft.util.ChatComponentProcessor.packedTextFragments[1] = new String("");
    }

    // endregion

    // region String decoding support

    // endregion

    public static ChatComponent processComponent(
            CommandSender commandSender, ChatComponent chatComponent, Entity entity)
            throws CommandException {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Object object = null;
        String textValue = text;
        if (chatComponent instanceof ChatComponentScore) {
            ChatComponentScore chatComponentScore = (ChatComponentScore) chatComponent;
            String textValue2 = chatComponentScore.getName();
            if (PlayerSelector.hasArguments(textValue2)) {
                List list =
                        PlayerSelector.matchEntities(
                                commandSender, textValue2, Entity.class);
                if (list.size() != 1) {
                    throw new EntityNotFoundException();
                }

                textValue2 = ((Entity) list.get(0)).getName();
            }

            object =
                    entity != null && textValue2.equals(packedTextFragments[0])
                            ? new ChatComponentScore(
                                    entity.getName(), chatComponentScore.getObjective())
                            : new ChatComponentScore(textValue2, chatComponentScore.getObjective());
            ((ChatComponentScore) object).setValue(chatComponentScore.getUnformattedTextForChat());
        } else if (chatComponent instanceof ChatComponentSelector) {
            String textValue3 =
                    ((ChatComponentSelector) chatComponent).getSelector();
            object =
                    PlayerSelector.matchEntitiesToChatComponent(
                            commandSender, textValue3);
            if (object == null) {
                object = new ChatComponentText(packedTextFragments[1]);
            }
        } else if (chatComponent instanceof ChatComponentText) {
            object =
                    new ChatComponentText(
                            ((ChatComponentText) chatComponent).getChatComponentText_TextValue());
        } else {
            if (!(chatComponent instanceof ChatComponentTranslation)) {
                return chatComponent;
            }

            Object[] aobject = ((ChatComponentTranslation) chatComponent).getFormatArgs();
            int i = 0;

            while (i < aobject.length) {
                Object object1 = aobject[i];
                if (object1 instanceof ChatComponent) {
                    aobject[i] =
                            processComponent(
                                    commandSender, (ChatComponent) object1, entity);
                }

                i++;
                if (textValue == null) {
                    break;
                }
            }

            object =
                    new ChatComponentTranslation(
                            ((ChatComponentTranslation) chatComponent).getKey(), aobject);
        }

        ChatStyle chatStyle = chatComponent.getChatStyle();
        if (chatStyle != null) {
            ((ChatComponent) object).setChatStyle(chatStyle.createShallowCopy());
        }

        for (java.util.Iterator<?> __recoveryIterator171_5022 =
                        (chatComponent.getSiblings()).iterator();
                (__recoveryIterator171_5022).hasNext(); ) {
            ChatComponent chatComponentValue = (ChatComponent) (__recoveryIterator171_5022).next();
            ((ChatComponent) object)
                    .appendSibling(
                            processComponent(commandSender, chatComponentValue, entity));
            if (textValue == null) {
                break;
            }
        }

        return (ChatComponent) object;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
