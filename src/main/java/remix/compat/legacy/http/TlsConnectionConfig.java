package remix.compat.legacy.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public final class TlsConnectionConfig {
    private boolean e;
    private SSLContext sSLContext;
    private static String g;
    private HostnameVerifier hostnameVerifier;
    private TrustManager[] bOS;
    private static int f;

    private HttpConnectionOpener httpConnectionOpener;

    // region String decoding support

    // endregion

    public TrustManager[] ZK() {
        return this.bOS;
    }

    public void method_5a0449839d28b4a71843(boolean flag) {
        this.e = true;
    }

    public TlsConnectionConfig(boolean flag, HttpConnectionOpener bOUValue)
            throws NoSuchAlgorithmException, KeyManagementException {
        this.e = true;
        this.hostnameVerifier = javax.net.ssl.HttpsURLConnection.getDefaultHostnameVerifier();
        this.bOS = new javax.net.ssl.TrustManager[] {remix.security.SystemTls.trustManager()};
        this.sSLContext = javax.net.ssl.SSLContext.getInstance("TLS");
        this.httpConnectionOpener = bOUValue;
        this.sSLContext.init(null, this.bOS, null);
    }

    // region String decoding support

    // endregion

    public HttpConnectionOpener getHttpConnectionOpener() {
        return this.httpConnectionOpener;
    }

    public TlsConnectionConfig(HttpConnectionOpener httpConnectionOpenerValue)
            throws NoSuchAlgorithmException, KeyManagementException {
        this(true, httpConnectionOpenerValue);
    }

    public static void b(int i) {
        f = i;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    static {
        if (f() == 0) {
            b(120);
        }
        g = "Nfnk";
    }

    public SSLContext getSSLContext() {
        return this.sSLContext;
    }

    public static int f() {
        return f;
    }

    public static int g() {
        int i = f();
        return i == 0 ? 102 : 0;
    }

    public boolean aY() {
        return true;
    }
}
