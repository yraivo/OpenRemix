package remix.compat.legacy.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

final class DefaultHostnameVerifierDelegate implements HostnameVerifier {
    @Override
    public boolean verify(String text, SSLSession sslsession) {
        return javax.net.ssl.HttpsURLConnection.getDefaultHostnameVerifier()
                .verify(text, sslsession);
    }

    DefaultHostnameVerifierDelegate(TlsDelegateAccessMarker accessMarker4) {
        this();
    }

    private DefaultHostnameVerifierDelegate() {}
}
