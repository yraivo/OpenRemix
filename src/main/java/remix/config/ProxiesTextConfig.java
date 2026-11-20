package remix.config;

import remix.util.network.NordVpnProxyManager;
import remix.util.network.ProxyConfigData;
import remix.util.network.ProxyType;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ProxiesTextConfig extends AbstractTextConfigFile {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.config.ProxiesTextConfig.packedTextFragments[0] = new String(":");
    }

    // endregion

    public void saveTo(java.util.List output) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();
        TextConfigLineList records = (TextConfigLineList) output;
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        for (ProxyConfigData proxy : ClientServices.nordVpnProxyManager.getList()) {
            TextConfigLineBuilder record = records.newLine();
            record.appendField(proxy.getHost());
            record.appendField(proxy.getPortText());
            if (proxy.getProxyType() == ProxyType.AUTH) {
                record.appendField(proxy.getProxyType().getDisplayName());
                record.appendField(proxy.getUsername());
                record.appendText(proxy.getPassword());
            } else {
                record.appendText(proxy.getProxyType().getDisplayName());
            }
            record.terminateAndCommitLine();
        }
    }

    public void readLine(TextConfigLineBuilder record) throws IOException {
        String[] fields = record.toString().split(packedTextFragments[0]);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NordVpnProxyManager manager = ClientServices.nordVpnProxyManager;
        if (fields.length == 3 && manager.findProxyTypeByName(fields[2]) != null) {
            manager.addProxy(new ProxyConfigData(fields[0], fields[1], manager.findProxyTypeByName(fields[2])));
        } else if (fields.length == 5 && manager.findProxyTypeByName(fields[3]) != null) {
            // The original five-column importer tests column four before using column three as the type.
            manager.addProxy(new ProxyConfigData(fields[0], fields[1], fields[3], fields[4],
                    manager.findProxyTypeByName(fields[2])));
        }
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public ProxiesTextConfig(File file1) {
        super(file1, StandardCharsets.US_ASCII);
    }

    public void loadFrom(Object object) throws IOException {
        this.readLine((TextConfigLineBuilder) object);
    }

    // region String decoding support

    // endregion
}
