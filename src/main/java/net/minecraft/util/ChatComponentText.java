package net.minecraft.util;

public class ChatComponentText extends ChatComponentStyle {

    private String text;

    public String getChatComponentText_TextValue() {
        return this.text;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChatComponentText)) {
            return false;
        }

        ChatComponentText chatComponentText = (ChatComponentText) object;
        return this.text.equals(chatComponentText.getChatComponentText_TextValue())
                && super.equals(object);
    }

    public String getUnformattedTextForChat() {
        return this.text;
    }

    public ChatComponentText TJ() {
        ChatComponentText chatComponentText = new ChatComponentText(this.text);
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        chatComponentText.setChatStyle(this.getChatStyle().createShallowCopy());
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_21715 = (this.getSiblings()).iterator();
                (__recoveryIterator171_21715).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_21715).next();
            chatComponentText.appendSibling(chatComponent.createCopy());
            if (textValue == null) {
                break;
            }
        }

        return chatComponentText;
    }


    public ChatComponent createCopy() {
        return this.TJ();
    }

    public ChatComponentText(String text) {
        this.text = text;
    }

    public String toString() {
        return "TextComponent{text='"
                + this.text
                + '\''
                + ", siblings="
                + this.siblings
                + ", style="
                + this.getChatStyle()
                + '}';
    }
}
