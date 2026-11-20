package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException {

    public ChatComponentTranslationFormatException(
            ChatComponentTranslation chatComponentTranslation, String text) {
        super(String.format("Error parsing: %s: %s", chatComponentTranslation, text));
    }

    public ChatComponentTranslationFormatException(
            ChatComponentTranslation chatComponentTranslation, Throwable throwable) {
        super(String.format("Error while parsing: %s", chatComponentTranslation), throwable);
    }


    public ChatComponentTranslationFormatException(
            ChatComponentTranslation chatComponentTranslation, int i) {
        super(String.format("Invalid index %d requested for %s", i, chatComponentTranslation));
    }
}
