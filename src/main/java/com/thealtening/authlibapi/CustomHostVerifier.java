package com.thealtening.authlibapi;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class CustomHostVerifier implements HostnameVerifier {


    @Override
    public boolean verify(String text, SSLSession sslsession) {
        return javax.net.ssl.HttpsURLConnection.getDefaultHostnameVerifier()
                .verify(text, sslsession);
    }
}
