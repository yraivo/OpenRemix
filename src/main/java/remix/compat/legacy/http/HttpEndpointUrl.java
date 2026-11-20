package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveInstantiationException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public final class HttpEndpointUrl {
    private static int cz;
    private URL url;

    public boolean a(String text) {
        return this.method_e0e3092a59fee58fae52().equals(text);
    }

    public static int f() {
        int i = cR();
        return i == 0 ? 101 : 0;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public HttpEndpointUrl(String text) {
        super();
        int initialFlowToken = f();

        int j = initialFlowToken;

        int i = j;

        URL url;
        try {
            url =
                    (URL)
                            new ReflectiveClassWrapper(URL.class)
                                    .method_d2c3a5ad661e2c5b1fe4(
                                            new Object[] {text}, new Class[] {String.class});
        } catch (ReflectiveInstantiationException reflectiveInstantiationException) {
            url = null;
        }

        try {
            if (false)
                throw (remix.util.reflection.ReflectiveInstantiationException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.url = url;
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(++i);
            }
        } catch (ReflectiveInstantiationException reflectiveInstantiationExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    a(reflectiveInstantiationExceptionValue));
        }
    }

    @Override
    public String toString() {
        return this.url.toString();
    }

    public UrlConnectionWrapper Zj() throws IOException {
        throw new java.io.IOException("Legacy Remix cloud/config requests are disabled");
    }

    static {
        if (f() != 0) {
            b(51);
        }
    }

    public static void b(int i) {
        cz = i;
    }

    public URL Zk() {
        return this.url;
    }

    public boolean method_3baace372e02b00ebb50(int i) {
        return this.method_7eec03da329a017ec143() == i;
    }

    public HttpEndpointUrl(URL url) {
        this.url = url;
    }

    public HttpEndpointUrl(URI uri) {
        super();
        int initialFlowToken = f();

        int j = initialFlowToken;

        int i = j;

        URL url;
        try {
            url = uri.toURL();
        } catch (MalformedURLException malformedurlexception1) {
            url = null;
        }

        try {
            if (false)
                throw (java.net.MalformedURLException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.url = url;
            if (i != 0) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }
        } catch (MalformedURLException malformedurlexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(malformedurlexception));
        }
    }

    public String method_e0e3092a59fee58fae52() {
        return this.url.getProtocol();
    }

    public static int cR() {
        return cz;
    }

    public int method_7eec03da329a017ec143() {
        return this.url.getPort();
    }
}
