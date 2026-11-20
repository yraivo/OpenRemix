package remix.util.network;

import net.minecraft.client.gui.GuiScreen;

import net.minecraft.entity.Entity;
import optifine.MathUtils;
import remix.client.ClientServices;
import remix.client.RemixClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public final class NordVpnProxyManager {
    private ProxyType proxyType = ProxyType.SOCKS4;
    public String proxyPassword;

    private static String[] packedTextFragments;
    private static Entity[] legacyProxyFlowState;
    public String proxyUsername;
    private ProxyConfigData proxyConfigData;
    private List<ProxyConfigData> availableProxies = new ArrayList<>();

    private int proxyTypeIndex;

    private NordVpnProxyManager() {}

    public static NordVpnProxyManager create() {
        return new NordVpnProxyManager();
    }

    public String findNordVpnSocksHost() {
        Entity[] entityArray = getLegacyProxyFlowState();
        ArrayList arrayList = new ArrayList();
        Entity[] entityArrayValue = entityArray;

        try {
            URL url = new URL("https://api.nordvpn.com/server");
            URLConnection urlconnection = url.openConnection();
            urlconnection.connect();
            JsonArray jsonarray =
                    new JsonParser()
                            .parse(new InputStreamReader((InputStream) urlconnection.getContent()))
                            .getAsJsonArray();
            int i = 0;

            while (i < jsonarray.size()) {
                JsonObject jsonobject = jsonarray.get(i).getAsJsonObject();
                JsonObject jsonobject1 = jsonobject.get("features").getAsJsonObject();
                if (jsonobject.get("flag").getAsString().equalsIgnoreCase("DE")
                        && jsonobject1.get("socks").getAsBoolean()) {
                    arrayList.add(jsonobject.get("domain").getAsString());
                }

                i++;
                if (entityArrayValue != null) {
                    break;
                }
            }

            return (String) arrayList.get(MathUtils.random.nextInt(arrayList.size() - 1));
        } catch (Exception exception) {
            return null;
        }
    }

    public void selectProxy(ProxyConfigData proxyConfigDataValue) {
        this.proxyConfigData = proxyConfigDataValue;
    }

    // region String decoding support

    // endregion

    public String getSelectedProxyEndpoint() {
        return this.proxyConfigData.getHost() + packedTextFragments[0] + this.proxyConfigData.getPortText();
    }

    public static Entity[] getLegacyProxyFlowState() {
        return remix.util.network.NordVpnProxyManager.legacyProxyFlowState;
    }

    public ProxyType getProxyType() {
        return this.proxyType;
    }

    public void importProxyList(ProxyType proxyType) {
        if (!this.availableProxies.isEmpty()) {
            this.availableProxies.clear();
        }

        JFileChooser jfilechooser = new JFileChooser();
        jfilechooser.setMultiSelectionEnabled(false);
        int i = jfilechooser.showOpenDialog(null);
        jfilechooser.requestFocus();
        if (i == 0) {
            File file1 = jfilechooser.getSelectedFile();

            try {
                Files.lines(file1.toPath())
                        .forEach(text -> {
                            String[] astring = text.split(packedTextFragments[1]);
                            ProxyConfigData proxyConfigDataValue = null;
                            switch (astring.length) {
                                case 2:
                                    proxyConfigDataValue =
                                            new ProxyConfigData(
                                                    astring[0], astring[1], proxyType);
                                    break;
                                case 4:
                                    proxyConfigDataValue =
                                            new ProxyConfigData(
                                                    astring[0],
                                                    astring[1],
                                                    astring[2],
                                                    astring[3],
                                                    ProxyType.AUTH);
                            }

                            if (proxyConfigDataValue != null) {
                                this.availableProxies.add(proxyConfigDataValue);
                            }
                        });
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void cycleProxyType() {
        this.proxyTypeIndex++;
        if (this.proxyTypeIndex >= ProxyType.values().length) {
            this.proxyTypeIndex = 0;
        }

        this.setProxyType(ProxyType.values()[this.proxyTypeIndex]);
    }

    // region String decoding support

    // endregion

    public List<ProxyConfigData> getList() {
        return this.availableProxies;
    }

    static {
        remix.util.network.NordVpnProxyManager.packedTextFragments = new String[4];
        remix.util.network.NordVpnProxyManager.packedTextFragments[0] = new String(":");
        remix.util.network.NordVpnProxyManager.packedTextFragments[1] = new String(":");
        setLegacyProxyFlowState(null);
    }

    // region String decoding support

    // endregion

    public static void setLegacyProxyFlowState(Entity[] entityArray) {
        remix.util.network.NordVpnProxyManager.legacyProxyFlowState = entityArray;
    }

    public void addProxy(ProxyConfigData proxyConfigDataValue) {
        this.availableProxies.add(proxyConfigDataValue);
    }

    public ProxyConfigData getProxyConfigData() {
        return this.proxyConfigData;
    }

    public ProxyType findProxyTypeByName(String text) {
        Entity[] entityArray = getLegacyProxyFlowState();
        ProxyType[] proxyTypeArray = ProxyType.values();
        int length = proxyTypeArray.length;
        int j = 0;
        Entity[] entityArrayValue = entityArray;

        while (j < length) {
            ProxyType proxyType = proxyTypeArray[j];
            if (proxyType.getDisplayName().equals(text)) {
                return proxyType;
            }

            j++;
            if (entityArrayValue != null) {
                break;
            }
        }

        return null;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public void chooseProxyOrImport() {
        boolean legacyProxyFlow = getLegacyProxyFlowState() != null;
        if (this.proxyType != ProxyType.NORDVPN) {
            RemixClient.INSTANCE.getClientServices();
            NordVpnProxyManager proxies = ClientServices.nordVpnProxyManager;
            if (GuiScreen.isShiftKeyDown() || proxies.getList().isEmpty()) {
                proxies.importProxyList(proxies.getProxyType());
                if (!legacyProxyFlow) {
                    return;
                }
            }

            if (!this.availableProxies.isEmpty()) {
                this.selectProxy(this.availableProxies.get(MathUtils.random.nextInt(this.availableProxies.size())));
            }
            if (!legacyProxyFlow) {
                return;
            }
        }

        String host = this.findNordVpnSocksHost();
        this.selectProxy(new ProxyConfigData(host, "1080", ProxyType.NORDVPN));
    }
}
