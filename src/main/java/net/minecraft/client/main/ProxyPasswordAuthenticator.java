package net.minecraft.client.main;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

final class ProxyPasswordAuthenticator extends Authenticator {
    String b;
    String a;

    ProxyPasswordAuthenticator(String text, String textValue) {
        this.a = text;
        this.b = textValue;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return null;
    }
}
