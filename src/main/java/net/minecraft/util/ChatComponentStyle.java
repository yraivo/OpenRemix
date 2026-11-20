package net.minecraft.util;

import net.minecraft.util.ChatComponentStyleCreateDeepCopyIteratorFunction;
import net.minecraft.util.ChatComponentStyleCreateDeepCopyIteratorFunction2;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public abstract class ChatComponentStyle implements ChatComponent {
    private ChatStyle style;

    protected List siblings = Lists.newArrayList();

    private static int c;

    public static Iterator createDeepCopyIterator(Iterable iterable) {
        Iterator iterator =
                Iterators.concat(
                        Iterators.transform(iterable.iterator(), new ChatComponentStyleCreateDeepCopyIteratorFunction()));
        return Iterators.transform(iterator, new ChatComponentStyleCreateDeepCopyIteratorFunction2());
    }

    static {
        b(29);
    }

    // region String decoding support

    // endregion

    public static void b(int i) {
        c = i;
    }

    public ChatComponent appendSibling(ChatComponent chatComponent) {
        chatComponent.getChatStyle().setParentStyle(this.getChatStyle());
        this.siblings.add(chatComponent);
        return this;
    }

    // region String decoding support

    // endregion

    public static int method_7eec03da329a017ec143() {
        return c;
    }

    @Override
    public int hashCode() {
        return 31 * this.style.hashCode() + this.siblings.hashCode();
    }

    public Iterator iterator() {
        return Iterators.concat(
                Iterators.forArray(new ChatComponentStyle[] {this}),
                createDeepCopyIterator(this.siblings));
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 91 : 0;
    }

    @Override
    public String toString() {
        return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
    }

    public final String getFormattedText() {
        StringBuilder stringbuilder = new StringBuilder();
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = this.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            ChatComponent chatComponent = (ChatComponent) iterator.next();
            stringbuilder.append(chatComponent.getChatStyle().getFormattingCode());
            stringbuilder.append(chatComponent.getUnformattedTextForChat());
            stringbuilder.append(EnumChatFormatting.RESET);
            if (textValue == null) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    public List getSiblings() {
        return this.siblings;
    }

    public final String getUnformattedText() {
        StringBuilder stringbuilder = new StringBuilder();
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = this.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            ChatComponent chatComponent = (ChatComponent) iterator.next();
            stringbuilder.append(chatComponent.getUnformattedTextForChat());
            if (textValue == null) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChatComponentStyle)) {
            return false;
        }

        ChatComponentStyle chatComponentStyle = (ChatComponentStyle) object;
        return this.siblings.equals(chatComponentStyle.siblings)
                && this.getChatStyle().equals(chatComponentStyle.getChatStyle());
    }

    public ChatComponent appendText(String text) {
        return this.appendSibling(new ChatComponentText(text));
    }

    public ChatStyle getChatStyle() {
        if (this.style == null) {
            this.style = new ChatStyle();

            for (java.util.Iterator<?> __recoveryIterator171_29166 = (this.siblings).iterator();
                    (__recoveryIterator171_29166).hasNext(); ) {
                ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_29166).next();
                chatComponent.getChatStyle().setParentStyle(this.style);
            }
        }

        return this.style;
    }

    public ChatComponent setChatStyle(ChatStyle bEwValue) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        this.style = bEwValue;
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_29657 = (this.siblings).iterator();
                (__recoveryIterator171_29657).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_29657).next();
            chatComponent.getChatStyle().setParentStyle(this.getChatStyle());
            if (textValue == null) {
                break;
            }
        }

        return this;
    }
}
