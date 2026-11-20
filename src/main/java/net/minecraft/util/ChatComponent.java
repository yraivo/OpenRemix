package net.minecraft.util;

import java.util.List;

public interface ChatComponent extends Iterable {
    ChatComponent appendText(String text);

    String getUnformattedTextForChat();

    List getSiblings();

    ChatComponent createCopy();

    ChatComponent setChatStyle(ChatStyle chatStyle);

    ChatStyle getChatStyle();

    String getFormattedText();

    String getUnformattedText();

    ChatComponent appendSibling(ChatComponent chatComponent);
}
