package com.thealtening.authlibapi;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class TrustAllTrustManager implements X509TrustManager {
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return remix.security.SystemTls.trustManager().getAcceptedIssuers();
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
}
