package remix.util.network;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class SocksProxyAuthenticator extends Authenticator {
    String a;
    String b;
    ProxyConfigData proxyConfigData;

    SocksProxyAuthenticator(
            ProxyConfigData proxyConfigDataValue, String text, String textValue) {
        this.proxyConfigData = proxyConfigDataValue;
        this.a = text;
        this.b = textValue;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return null;
    }
}
