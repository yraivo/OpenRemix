package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatComponentTranslation extends ChatComponentStyle {
    public static Pattern stringVariablePattern;
    private final Object syncLock = new Object();

    private Object[] formatArgs;
    List children;
    private String key;
    private long lastTranslationUpdateTimeInMilliseconds;

    private static String[] packedTextFragments;

    public ChatComponent createCopy() {
        return this.TL();
    }

    synchronized void ensureInitialized() {
        Object syncLockValue = this.syncLock;
        synchronized (this.syncLock) {
            long aMNValue = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
            if (aMNValue == this.lastTranslationUpdateTimeInMilliseconds) {
                return;
            }

            this.lastTranslationUpdateTimeInMilliseconds = aMNValue;
            this.children.clear();
        }

        try {
            this.initializeFromFormat(StatCollector.translateToLocal(this.key));
        } catch (ChatComponentTranslationFormatException chatComponentTranslationFormatException) {
            this.children.clear();

            try {
                this.initializeFromFormat(StatCollector.translateToFallback(this.key));
            } catch (
                    ChatComponentTranslationFormatException
                            chatComponentTranslationFormatExceptionValue) {
                throw chatComponentTranslationFormatException;
            }
        }
    }

    static {
        net.minecraft.util.ChatComponentTranslation.packedTextFragments = new String[6];
        net.minecraft.util.ChatComponentTranslation.packedTextFragments[0] = new String("%");
        net.minecraft.util.ChatComponentTranslation.packedTextFragments[1] = new String("%");
        net.minecraft.util.ChatComponentTranslation.packedTextFragments[2] = new String("s");
        net.minecraft.util.ChatComponentTranslation.packedTextFragments[3] = new String("'");
        net.minecraft.util.ChatComponentTranslation.stringVariablePattern =
                Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    }

    public ChatComponentTranslation TL() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Object[] aobject = new Object[this.formatArgs.length];
        String textValue = text;
        int i = 0;

        while (i < this.formatArgs.length) {
            {
                if (this.formatArgs[i] instanceof ChatComponent) {
                    aobject[i] = ((ChatComponent) this.formatArgs[i]).createCopy();
                    if (!(textValue != null)) {

                        aobject[i] = this.formatArgs[i];
                    }
                } else {

                    aobject[i] = this.formatArgs[i];
                }
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation(this.key, aobject);
        chatComponentTranslation.setChatStyle(this.getChatStyle().createShallowCopy());

        for (java.util.Iterator<?> __recoveryIterator171_6400 = (this.getSiblings()).iterator();
                (__recoveryIterator171_6400).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_6400).next();
            chatComponentTranslation.appendSibling(chatComponent.createCopy());
            if (textValue == null) {
                break;
            }
        }

        return chatComponentTranslation;
    }

    public String getUnformattedTextForChat() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        this.ensureInitialized();
        StringBuilder stringbuilder = new StringBuilder();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_6957 = (this.children).iterator();
                (__recoveryIterator171_6957).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_6957).next();
            stringbuilder.append(chatComponent.getUnformattedTextForChat());
            if (textValue == null) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    public String getKey() {
        return this.key;
    }

    public ChatComponentTranslation(String text, Object... aobject) {

        this.lastTranslationUpdateTimeInMilliseconds = -1L;
        this.children = Lists.newArrayList();
        this.key = text;
        this.formatArgs = aobject;
        Object[] aobject1 = aobject;
        int length = aobject.length;

        for (int j = 0; j < length; j++) {
            Object object = aobject1[j];
            if (object instanceof ChatComponent) {
                ((ChatComponent) object).getChatStyle().setParentStyle(this.getChatStyle());
            }
        }
    }

    public Object[] getFormatArgs() {
        return this.formatArgs;
    }

    protected void initializeFromFormat(String text) {

        Matcher matcher = net.minecraft.util.ChatComponentTranslation.stringVariablePattern.matcher(text);
        String textValue = Vec3i.getLegacyCoordinateFlowMarker();
        int i = 0;
        String textValue2 = textValue;
        int j = 0;

        try {
            while (matcher.find(j)) {
                int l = matcher.start();
                int k = matcher.end();
                if (l > j) {
                    ChatComponentText chatComponentText =
                            new ChatComponentText(String.format(text.substring(j, l)));
                    chatComponentText.getChatStyle().setParentStyle(this.getChatStyle());
                    this.children.add(chatComponentText);
                }

                label53:
                {
                    String textValue3 = matcher.group(2);
                    String textValue4 = text.substring(l, k);
                    if (packedTextFragments[0].equals(textValue3) && "%%".equals(textValue4)) {
                        ChatComponentText chatComponentTextValue =
                                new ChatComponentText(packedTextFragments[1]);
                        chatComponentTextValue.getChatStyle().setParentStyle(this.getChatStyle());
                        this.children.add(chatComponentTextValue);
                        if (textValue2 != null) {
                            break label53;
                        }
                    }

                    if (!packedTextFragments[2].equals(textValue3)) {
                        throw new ChatComponentTranslationFormatException(
                                this,
                                "Unsupported format: '" + textValue4 + packedTextFragments[3]);
                    }

                    String textValue5 = matcher.group(1);
                    int i1 = textValue5 != null ? Integer.parseInt(textValue5) - 1 : i++;
                    if (i1 < this.formatArgs.length) {
                        this.children.add(this.getFormatArgumentAsComponent(i1));
                    }
                }

                j = k;
                if (textValue2 == null) {
                    break;
                }
            }

            if (j < text.length()) {
                ChatComponentText chatComponentTextValue2 =
                        new ChatComponentText(String.format(text.substring(j)));
                chatComponentTextValue2.getChatStyle().setParentStyle(this.getChatStyle());
                this.children.add(chatComponentTextValue2);
            }
        } catch (IllegalFormatException illegalformatexception) {
            throw new ChatComponentTranslationFormatException(this, illegalformatexception);
        }
    }

    public ChatComponent setChatStyle(ChatStyle chatStyle) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        super.setChatStyle(chatStyle);
        Object[] aobject = this.formatArgs;
        int length = aobject.length;
        int j = 0;
        String textValue = text;

        while (j < length) {
            Object object = aobject[j];
            if (object instanceof ChatComponent) {
                ((ChatComponent) object).getChatStyle().setParentStyle(this.getChatStyle());
            }

            j++;
            if (textValue == null) {
                break;
            }
        }

        if (this.lastTranslationUpdateTimeInMilliseconds > -1L) {
            for (java.util.Iterator<?> __recoveryIterator171_11207 = (this.children).iterator();
                    (__recoveryIterator171_11207).hasNext(); ) {
                ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_11207).next();
                chatComponent.getChatStyle().setParentStyle(chatStyle);
                if (textValue == null) {
                    break;
                }
            }
        }

        return this;
    }

    public int hashCode() {
        int i = super.hashCode();
        i = 31 * i + this.key.hashCode();
        return 31 * i + Arrays.hashCode(this.formatArgs);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChatComponentTranslation)) {
            return false;
        }

        ChatComponentTranslation chatComponentTranslation = (ChatComponentTranslation) object;
        return Arrays.equals(this.formatArgs, chatComponentTranslation.formatArgs)
                && this.key.equals(chatComponentTranslation.key)
                && super.equals(object);
    }

    private ChatComponent getFormatArgumentAsComponent(int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (i >= this.formatArgs.length) {
            throw new ChatComponentTranslationFormatException(this, i);
        }

        Object object = this.formatArgs[i];
        if (object instanceof ChatComponent) {
            ChatComponent chatComponent = (ChatComponent) object;
            if (text != null) {
                return chatComponent;
            }
        }

        ChatComponentText chatComponentText =
                new ChatComponentText(object == null ? "null" : object.toString());
        ((ChatComponent) chatComponentText).getChatStyle().setParentStyle(this.getChatStyle());
        return (ChatComponent) chatComponentText;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public String toString() {
        return "TranslatableComponent{key='"
                + this.key
                + '\''
                + ", args="
                + Arrays.toString(this.formatArgs)
                + ", siblings="
                + this.siblings
                + ", style="
                + this.getChatStyle()
                + '}';
    }

    public Iterator iterator() {
        this.ensureInitialized();
        return Iterators.concat(
                createDeepCopyIterator(this.children), createDeepCopyIterator(this.siblings));
    }
}
