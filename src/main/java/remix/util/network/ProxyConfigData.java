package remix.util.network;

import net.minecraft.entity.Entity;

import java.net.Authenticator;

public class ProxyConfigData {
    private String username;
    private String portText;
    private String host;
    private String password;
    private ProxyType proxyType;

    public String getPassword() {
        return this.password;
    }

    public ProxyConfigData(String text, String textValue, ProxyType proxyType) {
        this.host = text;
        boolean flag1 = ProxyType.isLegacyProxyConfigFlowEnabled();
        this.portText = textValue;
        this.proxyType = proxyType;
        boolean flag = flag1;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ProxyType.setLegacyProxyConfigFlowEnabled(!flag);
        }
    }

    public String getUsername() {
        return this.username;
    }

    public ProxyType getProxyType() {
        return this.proxyType;
    }

    public String getHost() {
        return this.host;
    }

    public ProxyConfigData(
            String text,
            String textValue,
            String textValue2,
            String textValue3,
            ProxyType proxyType) {
        this.host = text;
        boolean flag1 = ProxyType.isLegacyProxyConfigFlowEnabled();
        this.portText = textValue;
        this.username = textValue2;
        this.password = textValue3;
        boolean flag = flag1;
        Authenticator.setDefault(new SocksProxyAuthenticator(this, textValue2, textValue3));
        this.proxyType = proxyType;
        if (flag) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public String getPortText() {
        return this.portText;
    }
}
