package remix.ui.proxy;

import net.minecraft.client.gui.GuiScreen;
import remix.ui.widget.ColoredGuiButton;
import remix.ui.widget.PlaceholderGuiTextField;
import remix.ui.widget.ScrollableGuiSlot;
import remix.util.network.NordVpnProxyManager;
import remix.util.network.ProxyConfigData;
import remix.util.network.ProxyType;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;

import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class ProxyConfigScreen extends GuiScreen {
    public static int x;
    public HashMap<Integer, String> hashMap;
    public static final int y = -14145496;
    private String statusMessage;

    private GuiTextField usernameField;
    private static ResourceLocation connectionIcon;
    public List<GuiTextField> list;
    private static String[] packedTextFragments;
    private GuiTextField addressField;

    protected static ClientFontRenderer statusFont;
    private final String headingText = "Proxy " + EnumChatFormatting.RESET + "Manager";
    public GuiScreen guiScreen;
    protected static ClientFontRenderer headingFont;
    private GuiTextField passwordField;

    static {
        remix.ui.proxy.ProxyConfigScreen.packedTextFragments = new String[4];
        remix.ui.proxy.ProxyConfigScreen.packedTextFragments[0] = new String(":");
        remix.ui.proxy.ProxyConfigScreen.packedTextFragments[1] = new String(":");
        remix.ui.proxy.ProxyConfigScreen.connectionIcon = new ResourceLocation("remix/gui/altmanager/Connection.png");
        x = new Color(-14935012).brighter().getRGB();
        remix.ui.proxy.ProxyConfigScreen.statusFont = RemixClient.fonts.roboto19;
        remix.ui.proxy.ProxyConfigScreen.headingFont = RemixClient.fonts.roboto32;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        Gui.drawRect(0.0, 0.0, this.width, this.height, -14145496);
        int k = this.width / 2;
        int l = this.height / 2 - 100;
        int rgb = new Color(-14671840).darker().getRGB();
        Gui.drawRect(
                this.width / 2 - 110 - 10,
                l + 15,
                this.width / 2 + 108 + 10,
                this.height / 2 + 82 - 2,
                rgb);
        Render2D3DUtils.drawShadow(
                this.width / 2 - 110 - 10,
                l + 15 - 2,
                this.width / 2 + 108 + 10,
                this.height / 2 + 82,
                0.2F);
        Render2D3DUtils.drawBorder(
                this.width / 2 - 110 - 12,
                l + 15 - 2,
                this.width / 2 + 108 + 12,
                this.height / 2 + 82,
                1.0,
                -14671840);
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.proxy.ProxyConfigScreen.connectionIcon, k - 17, l + 20, 64.0F, 64.0F, new int[] {0, 0, 0, 30});
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.proxy.ProxyConfigScreen.connectionIcon, k - 18.5F, l + 19, 64.0F, 64.0F, new int[] {255, 255, 255, 255});
        int j1 = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        remix.ui.proxy.ProxyConfigScreen.headingFont.drawCenteredString(this.headingText, this.width / 2, l + 55, -12465804);
        remix.ui.proxy.ProxyConfigScreen.statusFont.drawCenteredString(this.getStatusMessage(), this.width / 2, l + 74, j1);
        String textValue = text;

        for (GuiTextField guiTextField : this.list) {
            guiTextField.drawTextBox();
            if (guiTextField.getText().length() == 0 && !guiTextField.isFocused()) {
                remix.ui.proxy.ProxyConfigScreen.statusFont.drawString(
                        this.hashMap.get(guiTextField.getId()),
                        guiTextField.xPosition + 4,
                        guiTextField.yPosition + guiTextField.height / 3.4F,
                        Render2D3DUtils.interpolateColor(
                                16777215, -16777216, 0.5F));
            }

            if (textValue != null) {
                break;
            }
        }

        super.drawScreen(i, j, f);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        boolean legacyButtonFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        if (!button.enabled) {
            return;
        }

        switch (button.id) {
            case 1:
                RemixClient.INSTANCE.getClientServices();
                NordVpnProxyManager manager = ClientServices.nordVpnProxyManager;
                ProxyType type = manager.getProxyType();
                String enteredAddress = this.addressField.getText();
                ProxyConfigData proxy = null;
                if (type != ProxyType.NORDVPN && enteredAddress.isEmpty()) {
                    this.setStatusMessage("Proxy successfully reset!");
                } else {
                    String[] addressParts = enteredAddress.split(packedTextFragments[0]);
                    if (type != ProxyType.NORDVPN
                            && (addressParts.length < 2 || addressParts[0].isEmpty() || addressParts[1].isEmpty())) {
                        this.setStatusMessage("Enter the proxy as host:port.");
                        return;
                    }

                    proxy = this.createProxy(manager, type, addressParts);
                    this.setStatusMessage(this.proxyStatus(enteredAddress, ""));
                    String[] probeEndpoint = {proxy.getHost(), proxy.getPortText()};
                    String configuredAddress = probeEndpoint[0] + packedTextFragments[1] + probeEndpoint[1];
                    new Thread(() -> this.probeProxyLatency(probeEndpoint, configuredAddress)).start();
                }
                manager.selectProxy(proxy);
                if (!legacyButtonFlow) {
                    return;
                }
                // The old flow cycles the type and then closes the screen.
            case 2:
                RemixClient.INSTANCE.getClientServices();
                NordVpnProxyManager current = ClientServices.nordVpnProxyManager;
                current.cycleProxyType();
                this.setStatusMessage("Type\u00a7f set to " + EnumChatFormatting.RESET
                        + current.getProxyType().getDisplayName() + "\u00a7f!");
                if (!legacyButtonFlow) {
                    return;
                }
            case 3:
                this.mc.displayGuiScreen(this.guiScreen);
                return;
            default:
                return;
        }
    }

    private ProxyConfigData createProxy(NordVpnProxyManager manager, ProxyType type, String[] address) {
        if (type == ProxyType.AUTH) {
            return new ProxyConfigData(address[0], address[1], this.usernameField.getText(), this.passwordField.getText(), type);
        }
        if (type == ProxyType.NORDVPN) {
            String host = manager.findNordVpnSocksHost();
            if (!this.usernameField.getText().isEmpty() && !this.passwordField.getText().isEmpty()) {
                manager.proxyUsername = this.usernameField.getText();
                manager.proxyPassword = this.passwordField.getText();
            }
            return new ProxyConfigData(host, "1080", manager.proxyUsername, manager.proxyPassword, type);
        }
        return new ProxyConfigData(address[0], address[1], type);
    }

    private String proxyStatus(String address, String result) {
        RemixClient.INSTANCE.getClientServices();
        String type = ClientServices.nordVpnProxyManager.getProxyType().getDisplayName();
        return "\u00a7fSet " + EnumChatFormatting.RESET + "'" + address + "' \u00a7fas " + type
                + " " + EnumChatFormatting.RESET + "Proxy\u00a7f!" + result;
    }

    private void probeProxyLatency(String[] endpoint, String address) {
        boolean legacyStatusFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        try {
            long started = System.currentTimeMillis();
            InetAddress host = InetAddress.getByName(endpoint[0]);
            if (host.isReachable(5000)) {
                this.setStatusMessage(this.proxyStatus(
                        address, " (" + (System.currentTimeMillis() - started) + " ms)"));
                if (!legacyStatusFlow) {
                    return;
                }
            }
            this.setStatusMessage(this.proxyStatus(address, " (\u00a745000\u00a7f ms <)"));
        } catch (IOException failure) {
            this.setStatusMessage(this.proxyStatus(address, " (\u00a74Failed\u00a7f Pinging)"));
            failure.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char c0, int i) throws IOException {
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        Iterator iterator = this.list.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            GuiTextField guiTextField = (GuiTextField) iterator.next();
            guiTextField.textboxKeyTyped(c0, i);
            if (textValue != null) {
                break;
            }
        }

        super.keyTyped(c0, i);
    }

    public void setStatusMessage(String text) {
        this.statusMessage = text;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.list.clear();
        int i = this.height / 2 - 13;
        this.buttonList.add(
                new ColoredGuiButton(1, this.width / 2 + 10, i, 90, 20, "Set proxy"));
        this.list.add(
                this.addressField =
                        new PlaceholderGuiTextField(
                                0, this.fontRendererObj, this.width / 2 - 100, i, 100, 16));
        i += 23;
        this.list.add(
                this.usernameField =
                        new PlaceholderGuiTextField(
                                1, this.fontRendererObj, this.width / 2 - 102, i, 100, 16));
        this.list.add(
                this.passwordField =
                        new PlaceholderGuiTextField(
                                2, this.fontRendererObj, this.width / 2 + 2, i, 100, 16));
        i += 23;
        this.buttonList.add(
                new ColoredGuiButton(2, this.width / 2 - 100, i, "Change type"));
        i += 23;
        this.buttonList.add(new ColoredGuiButton(3, this.width / 2 - 100, i, "Cancel"));
        this.list.get(0).setFocused(true);
        this.hashMap.put(0, "IP:Port");
        this.hashMap.put(1, "Username");
        this.hashMap.put(2, "Password");
    }

    @Override
    public void updateScreen() {
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        Iterator iterator = this.list.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            GuiTextField guiTextField = (GuiTextField) iterator.next();
            guiTextField.updateCursorCounter();
            if (textValue != null) {
                break;
            }
        }

        super.updateScreen();
    }

    @Override
    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        Iterator iterator = this.list.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            GuiTextField guiTextField = (GuiTextField) iterator.next();
            guiTextField.mouseClicked(i, j, k);
            if (textValue != null) {
                break;
            }
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public ProxyConfigScreen(GuiScreen guiScreen) {
        super();
        this.statusMessage = "\u00a7eWaiting...";
        this.list = new java.util.ArrayList();
        this.hashMap = new java.util.HashMap();
        this.guiScreen = guiScreen;
    }
}
