package net.minecraft.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

public class LoggingPrintStream extends PrintStream {
    private String domain;

    private static Logger LOGGER;
    private static String c;

    private void logString(String text) {
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
        StackTraceElement stacktraceelement =
                astacktraceelement[Math.min(3, astacktraceelement.length)];
        net.minecraft.util.LoggingPrintStream.LOGGER.info(
                c,
                new Object[] {
                    this.domain, stacktraceelement.getFileName(), stacktraceelement.getLineNumber(), text
                });
    }

    @Override
    public void println(String text) {
        this.logString(text);
    }

    public LoggingPrintStream(String text, OutputStream outputstream) {
        super(outputstream);
        this.domain = text;
    }

    // region String decoding support

    // endregion

    @Override
    public void println(Object object) {
        this.logString(String.valueOf(object));
    }

    static {
        c = "[{}]@.({}:{}): {}";
        net.minecraft.util.LoggingPrintStream.LOGGER = LogManager.getLogger();
    }

    // region String decoding support

    // endregion
}
