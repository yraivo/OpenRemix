package remix.compat.legacy.http;

import java.io.IOException;

public final class DefaultHttpConnectionOpener1 implements HttpConnectionOpener {
    private static int[] k;

    public UrlConnectionWrapper method_17f82745b501361129cb(
            HttpEndpointUrl httpEndpointUrl,
            TlsConnectionConfig tlsConnectionConfig)
            throws IOException {
        return httpEndpointUrl.Zj();
    }

    public static int[] method_650532ba484532b3efb0() {
        return k;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static void b(int[] aint) {
        k = aint;
    }

    static {
        method_650532ba484532b3efb0();
    }
}
