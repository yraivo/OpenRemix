package net.minecraft.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.io.Charsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;

public class StringTranslate {
    private long lastUpdateTimeInMilliseconds;

    private static StringTranslate instance;
    private static Pattern numericVariablePattern;
    private static Splitter equalSignSplitter;

    private final Map languageList = Maps.newHashMap();

    public static synchronized void replaceWith(Map map) {
        net.minecraft.util.StringTranslate.instance.languageList.clear();
        net.minecraft.util.StringTranslate.instance.languageList.putAll(map);
        net.minecraft.util.StringTranslate.instance.lastUpdateTimeInMilliseconds =
                System.currentTimeMillis();
    }

    private String tryTranslateKey(String text) {
        String textValue = (String) this.languageList.get(text);

        try {
            if (textValue == null) {
                return text;
            }
        } catch (IllegalFormatException illegalformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(illegalformatexception));
        }

        return textValue;
    }

    static StringTranslate getInstance() {
        return net.minecraft.util.StringTranslate.instance;
    }

    public synchronized String translateKey(String text) {
        return this.tryTranslateKey(text);
    }

    static {
        net.minecraft.util.StringTranslate.numericVariablePattern =
                Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
        net.minecraft.util.StringTranslate.equalSignSplitter = Splitter.on('=').limit(2);
        net.minecraft.util.StringTranslate.instance = new StringTranslate();
    }

    public synchronized boolean isKeyTranslated(String text) {
        return this.languageList.containsKey(text);
    }

    public long getLastUpdateTimeInMilliseconds() {
        return this.lastUpdateTimeInMilliseconds;
    }

    public synchronized String translateKeyFormat(String text, Object... aobject) {
        String textValue = this.tryTranslateKey(text);

        try {
            return String.format(textValue, aobject);
        } catch (IllegalFormatException illegalformatexception) {
            return "Format error: " + textValue;
        }
    }

    public StringTranslate() {
        try {
            InputStream inputstream =
                    StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");

            for (String text :
                    remix.compat.library.LegacyLibraryAdapters.readLines(
                            inputstream, Charsets.UTF_8)) {
                try {
                    if (false)
                        throw (java.io.IOException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (text.isEmpty() || text.charAt(0) == '#') {
                        continue;
                    }
                } catch (IOException ioexception1) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
                }

                String[] astring =
                        (String[])
                                Iterables.toArray(
                                        net.minecraft.util.StringTranslate.equalSignSplitter.split(
                                                text),
                                        String.class);

                try {
                    if (false)
                        throw (java.io.IOException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (astring == null || astring.length != 2) {
                        continue;
                    }
                } catch (IOException ioexception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
                }

                String textValue = astring[0];
                String textValue2 =
                        net.minecraft.util.StringTranslate.numericVariablePattern
                                .matcher(astring[1])
                                .replaceAll("%$1s");
                this.languageList.put(textValue, textValue2);
            }

            this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
        } catch (IOException ioexception2) {
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
