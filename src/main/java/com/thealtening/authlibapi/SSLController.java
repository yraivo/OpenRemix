package com.thealtening.authlibapi;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public final class SSLController {
    private static HostnameVerifier b;
    private SSLSocketFactory c;
    private static TrustManager[] a;

    private static String f;
    private SSLSocketFactory d;
    private HostnameVerifier e;

    static {
        f = "SSL";
        a = new TrustManager[] {new TrustAllTrustManager()};
        b = new CustomHostVerifier();
    }

    private void a(SSLSocketFactory sslsocketfactory, HostnameVerifier hostnameverifier) {
        throw new UnsupportedOperationException("Changing TLS verification is disabled");
    }

    private static NoSuchAlgorithmException a(NoSuchAlgorithmException nosuchalgorithmexception) {
        return nosuchalgorithmexception;
    }

    public void method_8d9b536bcdc9991c48fb() {
        // JVM TLS defaults are never changed.
    }

    public void method_784e95624d6d70fe7f50() {
        throw new UnsupportedOperationException("Third-party TLS downgrade is disabled");
    }

    public SSLController() {
        super();
        EnumAltService.b();

        this.c = javax.net.ssl.HttpsURLConnection.getDefaultSSLSocketFactory();
        this.d = this.c;
        this.e = javax.net.ssl.HttpsURLConnection.getDefaultHostnameVerifier();
    }
}
