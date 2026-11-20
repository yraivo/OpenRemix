package remix.compat.legacy.http;

import java.net.URISyntaxException;

public class EmptyUriSyntaxException extends URISyntaxException {
    private static String[] b;
    private static String[] packedTextFragments;

    public static String[] b() {
        return b;
    }

    public EmptyUriSyntaxException() {
        super(packedTextFragments[0], packedTextFragments[1], 0);
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();

        b();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.http.EmptyUriSyntaxException.packedTextFragments[0] = new String("");
        remix.compat.legacy.http.EmptyUriSyntaxException.packedTextFragments[1] = new String("");
    }

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static void b(String[] astring) {
        b = astring;
    }
}
