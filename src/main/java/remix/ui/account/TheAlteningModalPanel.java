package remix.ui.account;

import remix.ui.proxy.ProxyModalPanel;
import remix.ui.widget.ModalOverlayPanel;
import remix.ui.widget.OverlayButton;
import remix.ui.widget.OverlayIconButton;
import remix.ui.widget.PasswordGuiTextField;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.OverlayWidget;

import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;

import java.util.ArrayList;
import java.util.List;

public class TheAlteningModalPanel extends ModalOverlayPanel {

    private PasswordGuiTextField apiTokenField;
    private static ResourceLocation ALTENING_LOGO;

    private AltManagerOverlayScreen altManagerOverlayScreen;
    private OverlayButton serviceButton;
    private List<OverlayWidget> actionButtons;
    private int accentColor = -13782665;

    @Override
    public void handleButtonAction(int actionId) {
        ProxyModalPanel.getLegacyProxyPanelState();
        RemixClient.INSTANCE.getClientServices();

        AltServiceSwitcher service = ClientServices.altServiceSwitcher;
        switch (actionId) {
            case 1:
                this.generateAccount(service);
                return;
            case 2:
                service.cycleAuthService();
                this.serviceButton.setLabel("Service: " + service.getServiceLabel());
                return;
            case 3:
                String token = this.apiTokenField.text;
                new Thread(() -> this.storeApiToken(service, token)).start();
                return;
            case 4:
                this.altManagerOverlayScreen.returnToPreviousPanel();
                return;
            default:
        }
    }

    private void generateAccount(AltServiceSwitcher service) {
        AltLoginManager loginManager = this.altManagerOverlayScreen.altLoginManager;
        if (service.getApiToken().isEmpty()) {
            loginManager.setStatusMessage("Please set your token!");
            return;
        }
        AccountData account = service.getTheAltening().generateAccount();
        if (account == null) {
            loginManager.setStatusMessage("Failed getting alt token!");
            return;
        }
        loginManager.loginWithCredentials(account.getToken(), "RemixOP");
    }

    private void storeApiToken(AltServiceSwitcher service, String token) {
        ProxyModalPanel.getLegacyProxyPanelState();

        AltLoginManager loginManager = this.altManagerOverlayScreen.altLoginManager;
        loginManager.setStatusMessage("Trying to set token...");
        if (service.getTheAltening() == null) {
            ClientServices.altServiceSwitcher.setTheAltening(new TheAltening(token));
        } else {
            service.getTheAltening().apiToken = token;
        }
        loginManager.setStatusMessage("Successfully set the token!");
        service.setApiToken(token);
    }

    public void draw(int i, int j) {
        ProxyModalPanel.getLegacyProxyPanelState();

        Render2D3DUtils.drawHollowRect(this.left, this.top, this.right, this.bottom);

        Gui.drawRect(this.left, this.top + 1.0F, this.right, this.bottom, this.backgroundColor);
        Gui.drawRect(this.left, this.top, this.right, this.top + 1.0F, this.accentColor);

        for (OverlayWidget overlayWidget : this.actionButtons) {
            overlayWidget.updateHoverState(i, j);
        }

        ClientFontRenderer clientFontRenderer = RemixClient.fonts.roboto32;
        float f1 = (this.right + this.left) / 2.0F;
        float f = clientFontRenderer.getStringWidth("The Altening") / 2;
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.account.TheAlteningModalPanel.ALTENING_LOGO, f1 - 17.0F, this.top + 20.0F, 74.0F, 84.0F, new int[] {0, 0, 0, 30});
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.account.TheAlteningModalPanel.ALTENING_LOGO, f1 - 18.5F, this.top + 19.0F, 74.0F, 84.0F, new int[] {255, 255, 255, 255});
        clientFontRenderer.drawStringWithShadow("The ", f1 - f, this.top + 65.0F, -1);
        clientFontRenderer.drawStringWithShadow(
                "Altening",
                f1 - f + clientFontRenderer.getStringWidth("The "),
                this.top + 65.0F,
                this.accentColor);
        Gui.drawRect(
                f1 - 30.0F, this.top + 91.0F, f1 + 30.0F, this.top + 92.0F, this.accentColor);
        this.serviceButton.updateHoverState(i, j);
        this.apiTokenField.drawTextBox();
        super.draw(i, j);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ProxyModalPanel.setLegacyProxyPanelState(new Entity[1]);
        }
    }

    static {
        remix.ui.account.TheAlteningModalPanel.ALTENING_LOGO = new ResourceLocation("remix/gui/altmanager/TheAlteningColored.png");
    }

    public void keyTyped(char c0, int i) {
        this.apiTokenField.textboxKeyTyped(c0, i);
    }

    public TheAlteningModalPanel(
            AltManagerOverlayScreen altManagerOverlayScreenValue,
            float f,
            float f1,
            float f2,
            float f3,
            int i) {
        super(f, f1, f2, f3, i);
        this.altManagerOverlayScreen = altManagerOverlayScreenValue;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ProxyModalPanel.getLegacyProxyPanelState();

        for (OverlayWidget button : this.actionButtons) {
            if (button.isHovered()) {
                this.handleButtonAction(button.getActionId());
            }
        }
        if (this.serviceButton.isHovered()) {
            this.handleButtonAction(this.serviceButton.actionId);
        }
        this.apiTokenField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void init() {
        float f = (this.right + this.left) / 2.0F;
        this.actionButtons = new ArrayList<>();
        this.actionButtons.add(
                new OverlayButton(
                        1,
                        "Generate",
                        f - 100.0F,
                        this.bottom - 59.0F,
                        f + 100.0F,
                        this.bottom - 44.0F,
                        this.accentColor));
        StringBuilder stringbuilder = new StringBuilder().append("Service: ");
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        this.serviceButton =
                new OverlayButton(
                        2,
                        stringbuilder
                                .append(ClientServices.altServiceSwitcher.getServiceLabel())
                                .toString(),
                        f - 100.0F,
                        this.bottom - 41.0F,
                        f + 100.0F,
                        this.bottom - 26.0F,
                        this.accentColor);
        this.actionButtons.add(
                new OverlayButton(
                        3,
                        "Set API Key",
                        f - 100.0F,
                        this.bottom - 23.0F,
                        f + 100.0F,
                        this.bottom - 8.0F,
                        this.accentColor));
        this.actionButtons.add(
                new OverlayIconButton(
                        4,
                        this.left + 25.0F,
                        this.bottom - 15.0F,
                        -1,
                        new ResourceLocation("remix/gui/altmanager/Back.png"),
                        30,
                        30));
        this.apiTokenField =
                new PasswordGuiTextField(
                        "API Key",
                        (int) f - 100,
                        (int) this.top + 50 + 18 + 18 + 25,
                        200,
                        15,
                        0,
                        this.accentColor);
    }
}
