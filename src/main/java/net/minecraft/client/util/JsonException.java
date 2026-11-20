package net.minecraft.client.util;

import com.google.common.collect.Lists;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class JsonException extends IOException {
    private String b;

    private final List dB = Lists.newArrayList();

    private static String[] c;

    public JsonException(String text) {
        this.dB.add(new JsonExceptionEntry(null));
        this.b = text;
    }

    public static String[] b() {
        return c;
    }

    public void b(String text) {
        JsonExceptionEntry.access$202((JsonExceptionEntry) this.dB.get(0), text);
        this.dB.add(0, new JsonExceptionEntry(null));
    }

    @Override
    public String getMessage() {
        return "Invalid "
                + ((JsonExceptionEntry) this.dB.get(this.dB.size() - 1)).toString()
                + ": "
                + this.b;
    }

    static {
        b((String[]) null);
    }

    public static JsonException method_4b749345a5b256762054(Exception exception) {
        if (exception instanceof JsonException) {
            return (JsonException) exception;
        }

        String message = exception.getMessage();
        if (exception instanceof FileNotFoundException) {
            message = "File not found";
        }

        return new JsonException(message, exception);
    }

    public JsonException(String text, Throwable throwable) {
        super(throwable);
        this.dB.add(new JsonExceptionEntry(null));
        this.b = text;
    }

    // region String decoding support

    // endregion

    public static void b(String[] astring) {
        c = astring;
    }

    // region String decoding support

    // endregion

    public void method_be01d1eb5e587c583ed6(String text) {
        JsonExceptionEntry.access$100((JsonExceptionEntry) this.dB.get(0), text);
    }
}
