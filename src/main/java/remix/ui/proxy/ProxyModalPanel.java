package remix.ui.proxy;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.account.AltLoginManager;
import remix.ui.account.AltManagerOverlayScreen;
import remix.ui.widget.CustomGuiTextField;
import remix.ui.widget.ModalOverlayPanel;
import remix.ui.widget.OverlayButton;
import remix.ui.widget.OverlayIconButton;
import remix.ui.widget.PasswordGuiTextField;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.ProxyConfigData;
import remix.util.network.ProxyType;
import remix.util.render.Render2D3DUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ProxyModalPanel extends ModalOverlayPanel {

    private AltManagerOverlayScreen altManagerOverlayScreen;
    private static Entity[] legacyProxyPanelState;
    private List<OverlayButton> buttons;
    private ModalOverlayPanel modalOverlayPanel;
    private CustomGuiTextField addressField;
    private PasswordGuiTextField passwordField;
    private OverlayIconButton backButton;

    private CustomGuiTextField usernameField;

    @Override
    public void handleButtonAction(int actionId) {
        getLegacyProxyPanelState();
        String[] address = this.addressField.text.split(":");
        switch (actionId) {
            case 1:
                {
                    RemixClient.INSTANCE.getClientServices();
                    NordVpnProxyManager proxies = ClientServices.nordVpnProxyManager;
                    AltLoginManager loginManager = ClientServices.altLoginManager;
                    ProxyConfigData config = null;
                    if (proxies.getProxyType() != ProxyType.NORDVPN
                            && this.addressField.text.isEmpty()) {
                        loginManager.setStatusMessage("Proxy successfully reset!");
                    } else {
                        config = this.createProxyConfig(proxies, address);
                        loginManager.setStatusMessage(
                                String.format(
                                        "Successfully set '%s' as %s Proxy!",
                                        this.addressField.text,
                                        proxies.getProxyType().getDisplayName()));
                        String configuredAddress = config.getHost() + ":" + config.getPortText();
                        new Thread(() -> reportProxyReachability(address, configuredAddress))
                                .start();
                    }
                    proxies.selectProxy(config);
                    return;
                }
            case 2:
                RemixClient.INSTANCE.getClientServices();
                ClientServices.nordVpnProxyManager.cycleProxyType();
                this.buttons
                        .get(2)
                        .setLabel(
                                "Type: "
                                        + ClientServices.nordVpnProxyManager
                                                .getProxyType()
                                                .getDisplayName());
                return;
            case 3:
            case 4:
                this.altManagerOverlayScreen.returnToPreviousPanel();
                return;
            default:
        }
    }

    private ProxyConfigData createProxyConfig(NordVpnProxyManager proxies, String[] address) {
        switch (proxies.getProxyType()) {
            case AUTH:
                return new ProxyConfigData(
                        address[0],
                        address[1],
                        this.usernameField.getText(),
                        this.passwordField.getText(),
                        ProxyType.AUTH);
            case NORDVPN:
                String host = proxies.findNordVpnSocksHost();
                if (!this.usernameField.getText().isEmpty()
                        && !this.passwordField.getText().isEmpty()) {
                    proxies.proxyUsername = this.usernameField.getText();
                    proxies.proxyPassword = this.passwordField.getText();
                }
                return new ProxyConfigData(
                        host,
                        "1080",
                        proxies.proxyUsername,
                        proxies.proxyPassword,
                        ProxyType.NORDVPN);
            default:
                return new ProxyConfigData(address[0], address[1], proxies.getProxyType());
        }
    }

    private static void reportProxyReachability(String[] address, String configuredAddress) {
        getLegacyProxyPanelState();
        try {
            long startedAt = System.currentTimeMillis();
            // The original probe uses the entered host, while the message shows the selected
            // endpoint.
            InetAddress enteredHost = InetAddress.getByName(address[0]);
            if (enteredHost.isReachable(5000)) {
                reportProxyStatus(
                        "Successfully set '%s' as %s Proxy! (%s ms)",
                        configuredAddress, System.currentTimeMillis() - startedAt);
                return;
            }
            reportProxyStatus("Successfully set '%s' as %s Proxy! (5000 ms <)", configuredAddress);
        } catch (IOException failure) {
            reportProxyStatus(
                    "Successfully set '%s' as %s Proxy! (Failed Pinging)", configuredAddress);
            failure.printStackTrace();
        }
    }

    private static void reportProxyStatus(
            String format, String configuredAddress, Object... details) {
        Object[] fields = new Object[2 + details.length];
        fields[0] = configuredAddress;
        fields[1] = ClientServices.nordVpnProxyManager.getProxyType().getDisplayName();
        System.arraycopy(details, 0, fields, 2, details.length);
        ClientServices.altLoginManager.setStatusMessage(String.format(format, fields));
    }

    public void keyTyped(char character, int keyCode) {
        this.addressField.textboxKeyTyped(character, keyCode);
        if (requiresProxyCredentials(this.currentProxyType())) {
            this.usernameField.textboxKeyTyped(character, keyCode);
            this.passwordField.textboxKeyTyped(character, keyCode);
        }
    }

    private ProxyType currentProxyType() {
        RemixClient.INSTANCE.getClientServices();
        return ClientServices.nordVpnProxyManager.getProxyType();
    }

    private static boolean requiresProxyCredentials(ProxyType type) {
        return type == ProxyType.AUTH || type == ProxyType.NORDVPN;
    }

    public static Entity[] getLegacyProxyPanelState() {
        return remix.ui.proxy.ProxyModalPanel.legacyProxyPanelState;
    }

    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[3];
        setLegacyProxyPanelState(aclass_b6df81f333fa31dc98ea);
    }

    public static void setLegacyProxyPanelState(Entity[] entityArray) {
        remix.ui.proxy.ProxyModalPanel.legacyProxyPanelState = entityArray;
    }

    public ProxyModalPanel(
            AltManagerOverlayScreen altManagerOverlayScreenValue,
            float f,
            float f1,
            float f2,
            float f3,
            int i) {
        super(f, f1, f2, f3, i);
        this.altManagerOverlayScreen = altManagerOverlayScreenValue;
    }

    public void draw(int mouseX, int mouseY) {
        Render2D3DUtils.drawHollowRect(this.left, this.top, this.right, this.bottom);
        getLegacyProxyPanelState();
        Gui.drawRect(this.left, this.top + 1.0F, this.right, this.bottom, this.backgroundColor);
        int themeColor =
                ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        Gui.drawRect(this.left, this.top, this.right, this.top + 1.0F, themeColor);

        for (OverlayButton button : this.buttons) {
            ProxyType type = this.currentProxyType();
            // The Set and Generate buttons share a position and action ID.
            OverlayButton hiddenButton = this.buttons.get(type == ProxyType.NORDVPN ? 0 : 1);
            if (button != hiddenButton) {
                button.updateHoverState(mouseX, mouseY);
            }
        }

        ProxyType type = this.currentProxyType();
        if (type != ProxyType.NORDVPN) {
            this.addressField.drawTextBox();
        }
        if (requiresProxyCredentials(type)) {
            this.usernameField.drawTextBox();
            this.passwordField.drawTextBox();
        }

        this.backButton.updateHoverState(mouseX, mouseY);
        super.draw(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        getLegacyProxyPanelState();
        for (OverlayButton button : this.buttons) {
            if (button.isHovered()) {
                this.handleButtonAction(button.getActionId());
            }
        }
        if (this.backButton.isHovered()) {
            this.handleButtonAction(this.backButton.getActionId());
        }

        this.addressField.mouseClicked(mouseX, mouseY, mouseButton);
        if (requiresProxyCredentials(this.currentProxyType())) {
            this.usernameField.mouseClicked(mouseX, mouseY, mouseButton);
            this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        }
        this.backButton.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void init() {
        float f = (this.right + this.left) / 2.0F;
        this.buttons = new ArrayList<>();
        this.buttons.add(
                new OverlayButton(
                        1,
                        "Set Proxy",
                        f - 100.0F,
                        this.bottom - 59.0F,
                        f + 100.0F,
                        this.bottom - 44.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        this.buttons.add(
                new OverlayButton(
                        1,
                        "Generate Proxy",
                        f - 100.0F,
                        this.bottom - 59.0F,
                        f + 100.0F,
                        this.bottom - 44.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        List list = this.buttons;
        StringBuilder stringbuilder = new StringBuilder().append("Type: ");
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        list.add(
                new OverlayButton(
                        2,
                        stringbuilder
                                .append(
                                        ClientServices.nordVpnProxyManager
                                                .getProxyType()
                                                .getDisplayName())
                                .toString(),
                        f - 100.0F,
                        this.bottom - 41.0F,
                        f + 100.0F,
                        this.bottom - 26.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        this.buttons.add(
                new OverlayButton(
                        3,
                        "Cancel",
                        f - 100.0F,
                        this.bottom - 23.0F,
                        f + 100.0F,
                        this.bottom - 8.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        getLegacyProxyPanelState();

        this.backButton =
                new OverlayIconButton(
                        4,
                        this.left + 25.0F,
                        this.bottom - 15.0F,
                        -1,
                        new ResourceLocation("remix/gui/altmanager/Back.png"),
                        30,
                        30);

        this.addressField =
                new CustomGuiTextField(
                        "IP:Port",
                        (int) f - 100,
                        (int) this.top + 68,
                        200,
                        15,
                        0,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor());
        this.usernameField =
                new CustomGuiTextField(
                        "Username",
                        (int) f - 100,
                        (int) this.top + 86,
                        200,
                        15,
                        0,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor());
        this.passwordField =
                new PasswordGuiTextField(
                        "Password",
                        (int) f - 100,
                        (int) this.top + 104,
                        200,
                        15,
                        0,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor());
    }

    // region String decoding support

    // endregion
}
