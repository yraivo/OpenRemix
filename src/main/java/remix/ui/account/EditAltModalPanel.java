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

public class EditAltModalPanel extends ModalOverlayPanel {
    private ModalOverlayPanel modalOverlayPanel;

    private CustomGuiTextField passwordField;
    private CustomGuiTextField emailField;
    private AltManagerOverlayScreen altManagerOverlayScreen;
    private List<OverlayWidget> buttons;

    private AltAccountEntry altAccountEntry;

    public EditAltModalPanel(
            AltManagerOverlayScreen altManagerOverlayScreenValue,
            ModalOverlayPanel modalOverlayPanelValue,
            AltAccountEntry altAccountEntryValue,
            float f,
            float f1,
            float f2,
            float f3,
            int i) {
        super(f, f1, f2, f3, i);
        this.altManagerOverlayScreen = altManagerOverlayScreenValue;
        this.modalOverlayPanel = modalOverlayPanelValue;
        this.altAccountEntry = altAccountEntryValue;
    }

    public void keyTyped(char c0, int i) {
        this.emailField.textboxKeyTyped(c0, i);
        this.passwordField.textboxKeyTyped(c0, i);
    }

    public void init() {
        float f = (this.right + this.left) / 2.0F;
        this.buttons = new ArrayList<>();
        this.buttons.add(
                new OverlayButton(
                        1,
                        "Edit",
                        f - 100.0F,
                        this.bottom - 41.0F,
                        f + 100.0F,
                        this.bottom - 26.0F,
                        ((ClientServices) RemixClient.INSTANCE.getClientServices())
                                .getThemeColor()));
        this.buttons.add(
                new OverlayButton(
                        2,
                        "Cancel",
                        f - 100.0F,
                        this.bottom - 23.0F,
                        f + 100.0F,
                        this.bottom - 8.0F,
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
        this.emailField =
                new CustomGuiTextField(
                        "Username/Email",
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
        this.emailField.setText(this.altAccountEntry.getEmail());
        this.passwordField.setText(this.altAccountEntry.getPassword());
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

        this.emailField.drawTextBox();
        this.passwordField.drawTextBox();
        super.draw(i, j);
    }

    public void handleButtonAction(int i) {
        ProxyModalPanel.getLegacyProxyPanelState();

        switch (i) {
            case 1:
                this.altAccountEntry.setEmail(this.emailField.getText());
                this.altAccountEntry.setPassword(this.passwordField.getText());
                ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                ClientServices.altLoginManager.setStatusMessage("Successfully edited an account!");
                this.modalOverlayPanel.init();
                this.altManagerOverlayScreen.returnToPreviousPanel();

                break;

            case 2:
            case 3:
                this.altManagerOverlayScreen.returnToPreviousPanel();
        }
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

        this.emailField.mouseClicked(i, j, k);
        this.passwordField.mouseClicked(i, j, k);
    }
}
