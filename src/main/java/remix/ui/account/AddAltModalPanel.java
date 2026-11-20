package remix.ui.account;

import remix.ui.proxy.ProxyModalPanel;
import remix.ui.widget.CustomGuiTextField;
import remix.ui.widget.ModalOverlayPanel;
import remix.ui.widget.OverlayButton;
import remix.ui.widget.OverlayIconButton;
import remix.ui.widget.PasswordGuiTextField;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.hud.OverlayWidget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddAltModalPanel extends ModalOverlayPanel {
    private CustomGuiTextField passwordField;
    private CustomGuiTextField usernameField;
    private AltManagerOverlayScreen altManagerOverlayScreen;

    private List<OverlayWidget> buttons;
    private CustomGuiTextField combinedCredentialsField;
    private static String[] packedTextFragments;

    public void init() {
        float f = (this.right + this.left) / 2.0F;
        this.buttons = new ArrayList<>();
        this.buttons.add(
                new OverlayButton(
                        1,
                        "Add",
                        f - 100.0F,
                        this.bottom - 23.0F,
                        f + 100.0F,
                        this.bottom - 8.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        this.buttons.add(
                new OverlayButton(
                        2,
                        "Login",
                        f - 100.0F,
                        this.bottom - 41.0F,
                        f + 100.0F,
                        this.bottom - 26.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        this.buttons.add(
                new OverlayIconButton(
                        3,
                        this.left + 25.0F,
                        this.bottom - 15.0F,
                        -1,
                        new ResourceLocation("remix/gui/altmanager/Back.png"),
                        30,
                        30));
        this.combinedCredentialsField =
                new CustomGuiTextField(
                        "Email:Password",
                        (int) f - 100,
                        (int) this.top + 50,
                        200,
                        15,
                        0,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
        this.usernameField =
                new CustomGuiTextField(
                        "Username",
                        (int) f - 100,
                        (int) this.top + 50 + 18,
                        200,
                        15,
                        0,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
        this.passwordField =
                new PasswordGuiTextField(
                        "Password",
                        (int) f - 100,
                        (int) this.top + 50 + 18 + 18,
                        200,
                        15,
                        0,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
        this.usernameField.setMaxStringLength(999);
        this.passwordField.setMaxStringLength(999);
    }

    public void mouseClicked(int i, int j, int k) {
        ProxyModalPanel.getLegacyProxyPanelState();

        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            OverlayWidget overlayWidget =
                    (OverlayWidget) iterator.next();
            if (overlayWidget.isHovered()) {
                this.handleButtonAction(overlayWidget.getActionId());
            }
        }

        this.combinedCredentialsField.mouseClicked(i, j, k);
        this.usernameField.mouseClicked(i, j, k);
        this.passwordField.mouseClicked(i, j, k);
    }

    static {
        remix.ui.account.AddAltModalPanel.packedTextFragments = new String[6];
        remix.ui.account.AddAltModalPanel.packedTextFragments[0] = new String(":");
        remix.ui.account.AddAltModalPanel.packedTextFragments[1] = new String("");
        remix.ui.account.AddAltModalPanel.packedTextFragments[2] = new String(":");
        remix.ui.account.AddAltModalPanel.packedTextFragments[3] = new String("");
    }

    public void draw(int i, int j) {
        ProxyModalPanel.getLegacyProxyPanelState();

        Render2D3DUtils.drawHollowRect(this.left, this.top, this.right, this.bottom);

        Gui.drawRect(this.left, this.top + 1.0F, this.right, this.bottom, this.backgroundColor);
        Gui.drawRect(
                this.left,
                this.top,
                this.right,
                this.top + 1.0F,
                ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());

        for (OverlayWidget overlayWidget : this.buttons) {
            overlayWidget.updateHoverState(i, j);
        }

        this.combinedCredentialsField.drawTextBox();
        this.usernameField.drawTextBox();
        this.passwordField.drawTextBox();
        super.draw(i, j);
    }

    public AddAltModalPanel(
            AltManagerOverlayScreen altManagerOverlayScreenValue,
            float f,
            float f1,
            float f2,
            float f3,
            int i) {
        super(f, f1, f2, f3, i);
        this.altManagerOverlayScreen = altManagerOverlayScreenValue;
    }

    public void handleButtonAction(int actionId) {
        ProxyModalPanel.getLegacyProxyPanelState();
        AltLoginManager login = this.altManagerOverlayScreen.altLoginManager;
        if (actionId == 3) {
            this.altManagerOverlayScreen.returnToPreviousPanel();
            return;
        }
        if (actionId != 1 && actionId != 2) {
            return;
        }

        String credentials = this.combinedCredentialsField.getText();
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        boolean combined = !credentials.isEmpty();
        if (combined) {
            String[] fields = credentials.split(":");
            if (fields.length == 0 || fields.length > 2 || fields[0].isEmpty()) {
                login.setStatusMessage("Please enter a Username or Email:Password");
                return;
            }
            username = fields[0];
            password = fields.length == 2 ? fields[1] : "";
        }

        if (actionId == 2) {
            login.loginWithCredentials(username, password);
            return;
        }
        if (username.isEmpty()) {
            login.setStatusMessage("Please enter an Username/Email");
            return;
        }
        login.addAccountAndSave(username, password);
        login.setStatusMessage(String.format("Successfully added the alt \"%s\"!", username));
        this.altManagerOverlayScreen.returnToPreviousPanel();
        if (!combined) {
            RemixClient.INSTANCE.getClientServices();
            ClientServices.configFileManager.saveConfigs();
        }
    }

    public void keyTyped(char c0, int i) {
        this.combinedCredentialsField.textboxKeyTyped(c0, i);
        this.usernameField.textboxKeyTyped(c0, i);
        this.passwordField.textboxKeyTyped(c0, i);
    }
}
