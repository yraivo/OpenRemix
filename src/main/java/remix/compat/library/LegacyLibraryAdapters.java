package remix.compat.library;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.RegexReplacement;

import java.nio.charset.Charset;

/**
 * Narrow bridges for two official legacy vendor call sites; no old vulnerable implementation is
 * included.
 */
public final class LegacyLibraryAdapters {
    private LegacyLibraryAdapters() {}

    public static <V> void addCallback(
            ListenableFuture<V> future, FutureCallback<? super V> callback) {
        Futures.addCallback(future, callback, MoreExecutors.directExecutor());
    }

    public static PatternLayout createLayout(
            String pattern,
            Configuration configuration,
            RegexReplacement replacement,
            String charsetName,
            String alwaysWriteExceptions) {
        Charset charset = Charset.defaultCharset();
        if (charsetName != null && Charset.isSupported(charsetName))
            charset = Charset.forName(charsetName);
        // The legacy parseBoolean(value,true) regards every value other than "false" as true.
        return PatternLayout.newBuilder()
                .withPattern(pattern == null ? "%m%n" : pattern)
                .withConfiguration(configuration)
                .withRegexReplacement(replacement)
                .withCharset(charset)
                .withAlwaysWriteExceptions(!"false".equalsIgnoreCase(alwaysWriteExceptions))
                .build();
    }

    public static java.util.List<String> readLines(
            java.io.InputStream in, java.nio.charset.Charset cs) throws java.io.IOException {
        try {
            return org.apache.commons.io.IOUtils.readLines(in, cs);
        } catch (java.io.UncheckedIOException failure) {
            throw failure.getCause();
        }
    }

    /** Copies exactly the requested readable bytes without requiring a heap-backed ByteBuf. */
    public static byte[] readBytes(io.netty.buffer.ByteBuf buffer, int length) {
        if (length < 0 || length > buffer.readableBytes()) {
            throw new IndexOutOfBoundsException("length: " + length);
        }
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return bytes;
    }
}
