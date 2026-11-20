package remix.util.network;

public enum ProxyType {
    SOCKS4("Socks4"),
    SOCKS5("Socks5"),
    HTTP("HTTP"),
    NORDVPN("Nord VPN"),
    AUTH("Auth");

    private String displayName;
    private static boolean legacyProxyConfigFlowFlag;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static boolean isLegacyProxyConfigFlowDisabled() {
        boolean flag = isLegacyProxyConfigFlowEnabled();
        return !flag;
    }

    ProxyType(String text) {
        this.displayName = text;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static boolean isLegacyProxyConfigFlowEnabled() {
        return remix.util.network.ProxyType.legacyProxyConfigFlowFlag;
    }

    public static void setLegacyProxyConfigFlowEnabled(boolean flag) {
        remix.util.network.ProxyType.legacyProxyConfigFlowFlag = flag;
    }


    // region String decoding support

    // endregion

}
