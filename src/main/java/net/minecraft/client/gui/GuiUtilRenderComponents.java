package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.Lists;

import java.util.List;

public class GuiUtilRenderComponents {
    private static String[] packedTextFragments;

    public static List<ChatComponent> splitText(
            ChatComponent message, int maxWidth, FontRenderer font, boolean trimWrapSpace, boolean forceFormatting) {
        boolean legacyWrapFlow = Gui.getLegacyGuiRenderMarker() != null;
        int lineWidth = 0;
        ChatComponentText line = new ChatComponentText("");
        List<ChatComponent> lines = Lists.newArrayList();
        List<ChatComponent> pending = Lists.newArrayList(message);

        for (int index = 0; index < pending.size(); index++) {
            ChatComponent component = pending.get(index);
            String text = component.getUnformattedTextForChat();
            int newline = text.indexOf('\n');
            boolean finishLine = newline >= 0;
            if (finishLine) {
                pending.add(index + 1, withStyle(text.substring(newline + 1), component));
                text = text.substring(0, newline + 1);
            }

            String formatted = applyChatColorPreference(
                    component.getChatStyle().getFormattingCode() + text, forceFormatting);
            String displayed = formatted.endsWith("\n") ? formatted.substring(0, formatted.length() - 1) : formatted;
            int displayedWidth = font.getStringWidth(displayed);
            if (lineWidth + displayedWidth > maxWidth) {
                displayed = font.trimStringToWidth(formatted, maxWidth - lineWidth, false);
                if (displayed.length() < formatted.length()) {
                    String remainder = formatted.substring(displayed.length());
                    int lastSpace = displayed.lastIndexOf(' ');
                    boolean canWrapAtSpace = lastSpace >= 0 && font.getStringWidth(formatted.substring(0, lastSpace)) > 0;
                    if (canWrapAtSpace) {
                        displayed = formatted.substring(0, lastSpace);
                        remainder = formatted.substring(lastSpace + (trimWrapSpace ? 1 : 0));
                    }
                    if ((!canWrapAtSpace || legacyWrapFlow) && lineWidth > 0 && formatted.indexOf(' ') == -1) {
                        displayed = "";
                        remainder = formatted;
                    }
                    pending.add(index + 1, withStyle(remainder, component));
                }
                displayedWidth = font.getStringWidth(displayed);
                finishLine = true;
            }

            if (lineWidth + displayedWidth <= maxWidth) {
                lineWidth += displayedWidth;
                line.appendSibling(withStyle(displayed, component));
                finishLine |= legacyWrapFlow;
            } else {
                finishLine = true;
            }
            if (finishLine) {
                lines.add(line);
                lineWidth = 0;
                line = new ChatComponentText("");
            }
            if (legacyWrapFlow) {
                break;
            }
        }
        lines.add(line);
        return lines;
    }

    private static ChatComponentText withStyle(String text, ChatComponent source) {
        ChatComponentText result = new ChatComponentText(text);
        result.setChatStyle(source.getChatStyle().createShallowCopy());
        return result;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[7];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[1] = new String("\n");
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[2] = new String("\n");
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[3] = new String(" ");
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[4] = new String(" ");
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiUtilRenderComponents.packedTextFragments[6] = new String("");
    }

    // endregion

    public static String applyChatColorPreference(String text, boolean flag) {
        return !flag && !MinecraftClient.getMinecraft().gameSettings.chatColours
                ? EnumChatFormatting.getTextWithoutFormattingCodes(text)
                : text;
    }

    // region String decoding support

    // endregion
}
