package remix.security;

import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/** Platform certificate validation only. No network access and no global TLS changes. */
public final class SystemTls {
    private static final X509TrustManager SYSTEM = systemTrust();

    private SystemTls() {}

    public static X509TrustManager trustManager() {
        return SYSTEM;
    }

    private static X509TrustManager systemTrust() {
        try {
            TrustManagerFactory instance =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore) null);
            for (TrustManager m : instance.getTrustManagers())
                if (m instanceof X509TrustManager) return (X509TrustManager) m;
            throw new GeneralSecurityException("No platform X.509 trust manager");
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
