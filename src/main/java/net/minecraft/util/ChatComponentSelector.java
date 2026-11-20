package net.minecraft.util;

import java.util.Iterator;

public class ChatComponentSelector extends ChatComponentStyle {

    private String selector;

    public ChatComponentSelector TI() {
        ChatComponentSelector chatComponentSelector =
                new ChatComponentSelector(this.selector);
        chatComponentSelector.setChatStyle(this.getChatStyle().createShallowCopy());
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = this.getSiblings().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            ChatComponent chatComponent = (ChatComponent) iterator.next();
            chatComponentSelector.appendSibling(chatComponent.createCopy());
            if (textValue == null) {
                break;
            }
        }

        return chatComponentSelector;
    }

    public ChatComponentSelector(String text) {
        this.selector = text;
    }

    public String getUnformattedTextForChat() {
        return this.selector;
    }

    public ChatComponent createCopy() {
        return this.TI();
    }

    @Override
    public String toString() {
        return "SelectorComponent{pattern='"
                + this.selector
                + '\''
                + ", siblings="
                + this.siblings
                + ", style="
                + this.getChatStyle()
                + '}';
    }

    public String getSelector() {
        return this.selector;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChatComponentSelector)) {
            return false;
        }

        ChatComponentSelector chatComponentSelector =
                (ChatComponentSelector) object;
        return this.selector.equals(chatComponentSelector.selector) && super.equals(object);
    }


}
