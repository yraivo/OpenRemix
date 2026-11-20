package remix.compat.legacy.crypto;

import remix.compat.legacy.codec.FormatPredicate;
import remix.compat.legacy.codec.TokenArraySplitter;

public abstract class AbstractHashListParser implements remix.compat.legacy.crypto.HashListParser {
    private remix.compat.legacy.codec.TokenArraySplitter tokenArraySplitter;
    private FormatPredicate formatPredicate;
    private static int c;

    public remix.compat.legacy.codec.TokenArraySplitter getTokenArraySplitter() {
        return this.tokenArraySplitter;
    }

    public static int method_beea3e16fe3ec0995eb9() {
        method_7eec03da329a017ec143();

        return (0);
    }

    public static void b(int i) {
        c = i;
    }

    public FormatPredicate getFormatPredicate() {
        return this.formatPredicate;
    }

    public static int method_7eec03da329a017ec143() {
        return c;
    }

    public AbstractHashListParser(
            FormatPredicate bVxValue, TokenArraySplitter bVwValue) {
        this.formatPredicate = bVxValue;
        this.tokenArraySplitter = bVwValue;
    }

    static {
        if (method_7eec03da329a017ec143() == 0) {
            b(85);
        }
    }
}
