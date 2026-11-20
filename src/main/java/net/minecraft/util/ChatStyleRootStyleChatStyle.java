package net.minecraft.util;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public final class ChatStyleRootStyleChatStyle extends ChatStyle {
    private static String kE;
    private static String[] packedTextFragments;

    public ChatStyle setChatHoverEvent(HoverEvent hoverEvent) {
        throw new UnsupportedOperationException();
    }

    public ChatStyle setStrikethrough(Boolean obool) {
        throw new UnsupportedOperationException();
    }

    // region String decoding support

    // endregion

    public String getInsertion() {
        return null;
    }

    public ChatStyle setObfuscated(Boolean obool) {
        throw new UnsupportedOperationException();
    }

    static {
        net.minecraft.util.ChatStyleRootStyleChatStyle.packedTextFragments = new String[2];
        net.minecraft.util.ChatStyleRootStyleChatStyle.packedTextFragments[0] = new String("");
        net.minecraft.util.ChatStyleRootStyleChatStyle.packedTextFragments[1] =
                new String("0\u001c9;suc,'\u0014");
        kE = "Style.ROOT";
    }

    public ClickEvent getChatClickEvent() {
        return null;
    }

    public ChatStyle setParentStyle(ChatStyle chatStyle) {
        throw new UnsupportedOperationException();
    }

    public ChatStyle createShallowCopy() {
        return this;
    }

    public boolean getObfuscated() {
        return false;
    }

    public EnumChatFormatting getColor() {
        return null;
    }

    public ChatStyle setItalic(Boolean obool) {
        throw new UnsupportedOperationException();
    }

    public ChatStyle setColor(EnumChatFormatting enumChatFormatting) {
        throw new UnsupportedOperationException();
    }

    public ChatStyle setUnderlined(Boolean obool) {
        throw new UnsupportedOperationException();
    }

    public boolean getItalic() {
        return false;
    }

    public String getFormattingCode() {
        return packedTextFragments[0];
    }

    public ChatStyle createDeepCopy() {
        return this;
    }

    public boolean getStrikethrough() {
        return false;
    }

    public boolean getUnderlined() {
        return false;
    }

    public ChatStyle setChatClickEvent(ClickEvent clickEvent) {
        throw new UnsupportedOperationException();
    }

    // region String decoding support

    // endregion

    public HoverEvent getChatHoverEvent() {
        return null;
    }

    public String toString() {
        return kE;
    }

    public boolean getBold() {
        return false;
    }

    public ChatStyle setBold(Boolean obool) {
        throw new UnsupportedOperationException();
    }
}
