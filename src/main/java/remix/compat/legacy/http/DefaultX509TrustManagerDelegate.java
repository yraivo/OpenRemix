package remix.compat.legacy.http;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

final class DefaultX509TrustManagerDelegate implements X509TrustManager {
    DefaultX509TrustManagerDelegate(TlsDelegateAccessMarker accessMarker4) {
        this();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] ax509certificate, String text)
            throws java.security.cert.CertificateException {
        remix.security.SystemTls.trustManager().checkServerTrusted(ax509certificate, text);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] ax509certificate, String text)
            throws java.security.cert.CertificateException {
        remix.security.SystemTls.trustManager().checkClientTrusted(ax509certificate, text);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return remix.security.SystemTls.trustManager().getAcceptedIssuers();
    }

    private DefaultX509TrustManagerDelegate() {}
}
