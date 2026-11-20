package remix.ui.account;

import remix.ui.widget.ModalOverlayPanel;
import remix.ui.widget.OverlayIconButton;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.OverlayWidget;

import java.util.ArrayList;
import java.util.List;

public final class OverlayAccountCard extends OverlayWidget {
    private List<OverlayIconButton> actionButtons = new ArrayList<>();
    private static ResourceLocation REMOVE_ICON;
    private AltAccountEntry altAccountEntry;

    private ModalOverlayPanel modalOverlayPanel;
    private static ResourceLocation EDIT_ICON;

    private AltManagerOverlayScreen altManagerOverlayScreen;

    public void mouseClicked(int k, int l, int i1) {
        int i = OverlayIconButton.getLegacyIconFlowMarker();
        int j = this == AltManagerOverlayScreen.selectedAccountCard ? 1 : 0;
        byte b0 = 0;
        if (j != 0) {
            for (OverlayIconButton overlayIconButton : this.actionButtons) {
                if (overlayIconButton.isHovered()) {
                    b0 = 1;
                    this.handleAccountAction(overlayIconButton.getActionId());
                }

                if (i != 0) {
                    break;
                }
            }

            if (b0 == 0 && this.isHovered()) {
                this.handleAccountAction(this.actionId);
            }
        }
    }

    public OverlayAccountCard(
            AltManagerOverlayScreen altManagerOverlayScreenValue,
            ModalOverlayPanel modalOverlayPanelValue,
            float f,
            float f1,
            float f2,
            float f3,
            int i,
            AltAccountEntry altAccountEntryValue) {
        super(-1, f, f1, f2, f3, i);
        this.modalOverlayPanel = modalOverlayPanelValue;
        this.altAccountEntry = altAccountEntryValue;
        this.actionButtons.add(
                new OverlayIconButton(1, f2 - 15.0F, (f1 + f3) / 2.0F, -1, remix.ui.account.OverlayAccountCard.REMOVE_ICON, 33, 33));
        this.actionButtons.add(
                new OverlayIconButton(2, f2 - 40.0F, (f1 + f3) / 2.0F, -1, remix.ui.account.OverlayAccountCard.EDIT_ICON, 33, 33));
        this.altManagerOverlayScreen = altManagerOverlayScreenValue;
    }

    private void handleAccountAction(int i) {
        int j = OverlayIconButton.getLegacyIconFlowMarker();
        switch (i) {
            case -1:
                ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                ClientServices.altLoginManager.login(this.altAccountEntry);
                if (j == 0) {
                    break;
                }
            case 1:
                ClientServices clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
                ClientServices.altLoginManager.removeAccount(this.altAccountEntry);
                clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
                ClientServices.altLoginManager.setStatusMessage(
                        String.format(
                                "Successfully removed the account \"%s\"!",
                                this.altAccountEntry.getUsername().isEmpty()
                                        ? this.altAccountEntry.getEmail()
                                        : this.altAccountEntry.getUsername()));
                this.modalOverlayPanel.init();
                if (j == 0) {
                    break;
                }
            case 2:
                this.altManagerOverlayScreen.setModalOverlay(
                        new EditAltModalPanel(
                                this.altManagerOverlayScreen,
                                this.modalOverlayPanel,
                                this.altAccountEntry,
                                this.modalOverlayPanel.left,
                                this.modalOverlayPanel.top,
                                this.modalOverlayPanel.right,
                                this.modalOverlayPanel.bottom,
                                this.color));
            case 0:
        }
    }

    public void updateHoverState(int i, int j) {
        int k = OverlayIconButton.getLegacyIconFlowMarker();
        int l = this == AltManagerOverlayScreen.selectedAccountCard ? 1 : 0;
        String text =
                !this.altAccountEntry.getUsername().isEmpty()
                        ? this.altAccountEntry.getUsername()
                        : this.altAccountEntry.getEmail();
        ClientFontRenderer clientFontRenderer = RemixClient.fonts.comfortaa18;
        ClientFontRenderer clientFontRendererValue = RemixClient.fonts.comfortaa16;
        Render2D3DUtils.drawShadow(
                this.left, this.top, this.right, this.bottom, 0.1F);
        Gui.drawRect(
                this.left, this.top, this.right, this.bottom, l != 0 ? -14935012 : this.color);
        clientFontRenderer.drawCenteredString(
                text,
                (this.right - this.left) / 2.0F + this.left,
                (int) ((this.top + this.bottom) / 2.0F - clientFontRenderer.getHeight() / 2.0F - 5.0F),
                -1);
        clientFontRendererValue.drawCenteredString(
                this.altAccountEntry.getEmail(),
                (this.right - this.left) / 2.0F + this.left,
                (int)
                        ((this.top + this.bottom) / 2.0F
                                - clientFontRendererValue.getHeight() / 2.0F
                                + 6.0F),
                -1);
        if (l != 0) {
            for (OverlayIconButton overlayIconButton : this.actionButtons) {
                overlayIconButton.setY1((this.top + this.bottom) / 2.0F);
                overlayIconButton.updateHoverState(i, j);
                if (k != 0) {
                    Entity.setLegacyGlobalFlowMarker(new String[4]);
                    break;
                }
            }
        }

        this.setHovered(
                i >= this.left && j >= this.top && i <= this.right && j <= this.bottom);
    }

    static {
        remix.ui.account.OverlayAccountCard.REMOVE_ICON = new ResourceLocation("remix/gui/altmanager/Remove.png");
        remix.ui.account.OverlayAccountCard.EDIT_ICON = new ResourceLocation("remix/gui/altmanager/Edit.png");
    }

    public AltAccountEntry getAltAccountEntry() {
        return this.altAccountEntry;
    }
}
