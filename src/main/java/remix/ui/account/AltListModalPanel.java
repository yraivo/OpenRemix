package remix.ui.account;

import remix.ui.proxy.ProxyModalPanel;
import remix.ui.widget.ModalOverlayPanel;
import remix.ui.widget.ModalTopJFileChooser2;
import remix.ui.widget.OverlayIconButton;
import remix.ui.widget.OverlayScrollbar;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;

import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class AltListModalPanel extends ModalOverlayPanel {
    private static ResourceLocation ALTENING_ICON;
    private int toolbarHeight;
    private static ResourceLocation IMPORT_ICON;
    private static ResourceLocation ADD_ICON;

    private List<OverlayAccountCard> accountCards;
    private AltManagerOverlayScreen altManagerOverlayScreen;
    private List<OverlayIconButton> toolbarButtons;

    private OverlayScrollbar overlayScrollbar;
    private static ResourceLocation PROXY_ICON;
    private List<AltAccountEntry> accounts;
    private static ResourceLocation BACK_ICON;

    public void draw(int i, int j) {
        Render2D3DUtils.drawHollowRect(this.left, this.top, this.right, this.bottom);
        Gui.drawRect(
                this.left, this.top + this.toolbarHeight, this.right, this.bottom - this.toolbarHeight, this.backgroundColor);
        Gui.drawRect(
                this.left,
                this.top,
                this.right,
                this.top + this.toolbarHeight,
                ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
        ProxyModalPanel.getLegacyProxyPanelState();

        Gui.drawRect(
                this.left,
                this.bottom - this.toolbarHeight,
                this.right,
                this.bottom,
                ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());

        GL11.glPushMatrix();
        GL11.glEnable(3089);
        Render2D3DUtils.drawLine2D(
                this.left, this.top + this.toolbarHeight + 1.0F, this.right, this.bottom - this.toolbarHeight);

        for (java.util.Iterator<?> __recoveryIterator171_2794 = (this.accountCards).iterator();
                (__recoveryIterator171_2794).hasNext(); ) {
            OverlayAccountCard overlayAccountCard =
                    (OverlayAccountCard) (__recoveryIterator171_2794).next();
            overlayAccountCard.setY1(
                    overlayAccountCard.getTop() - this.overlayScrollbar.getScrollOffset());
            overlayAccountCard.setY2(
                    overlayAccountCard.getBottom() - this.overlayScrollbar.getScrollOffset());
            if (overlayAccountCard.getBottom() < this.bottom
                    && overlayAccountCard.getTop() > this.top) {
                overlayAccountCard.updateHoverState(i, j);
            }

            overlayAccountCard.setY1(
                    overlayAccountCard.getTop() + this.overlayScrollbar.getScrollOffset());
            overlayAccountCard.setY2(
                    overlayAccountCard.getBottom() + this.overlayScrollbar.getScrollOffset());
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();

        for (java.util.Iterator<?> __recoveryIterator171_3676 = (this.toolbarButtons).iterator();
                (__recoveryIterator171_3676).hasNext(); ) {
            OverlayIconButton overlayIconButton =
                    (OverlayIconButton) (__recoveryIterator171_3676).next();
            overlayIconButton.updateHoverState(i, j);
        }

        byte b0 = 5;
        int k = b0 + this.accounts.size() * (32 + b0);
        if (k > this.bottom - this.top - this.toolbarHeight * 2) {
            this.overlayScrollbar.updateHoverState(i, j);
        }

        super.draw(i, j);
    }

    public void init() {
        ProxyModalPanel.getLegacyProxyPanelState();

        byte b0 = 5;
        int i = this.toolbarHeight + b0;

        byte b1 = 32;
        this.accountCards = new ArrayList<>();
        this.toolbarButtons = new ArrayList<>();

        for (java.util.Iterator<?> __recoveryIterator171_24815 = (this.accounts).iterator();
                (__recoveryIterator171_24815).hasNext(); ) {
            AltAccountEntry altAccountEntry =
                    (AltAccountEntry) (__recoveryIterator171_24815).next();
            this.accountCards.add(
                    new OverlayAccountCard(
                            this.altManagerOverlayScreen,
                            this,
                            this.left + 5.0F,
                            this.top + i,
                            this.right - 5.0F,
                            this.top + i + b1,
                            this.backgroundColor,
                            altAccountEntry));
            i += b1 + b0;
        }

        this.toolbarButtons.add(
                new OverlayIconButton(
                        1, (this.left + this.right) / 2.0F, this.top + this.toolbarHeight / 2.0F, -1, remix.ui.account.AltListModalPanel.ALTENING_ICON, 40, 45));
        this.toolbarButtons.add(
                new OverlayIconButton(
                        420, this.right - 25.0F, this.top + this.toolbarHeight / 2.0F, -1, remix.ui.account.AltListModalPanel.PROXY_ICON, 30, 30));
        this.toolbarButtons.add(
                new OverlayIconButton(
                        2, this.left + 25.0F, this.bottom - this.toolbarHeight / 2.0F, -1, remix.ui.account.AltListModalPanel.BACK_ICON, 30, 30));
        this.toolbarButtons.add(
                new OverlayIconButton(
                        3, (this.left + this.right) / 2.0F, this.bottom - this.toolbarHeight / 2.0F, -1, remix.ui.account.AltListModalPanel.ADD_ICON, 30, 30));
        this.toolbarButtons.add(
                new OverlayIconButton(
                        4, this.right - 25.0F, this.bottom - this.toolbarHeight / 2.0F, -1, remix.ui.account.AltListModalPanel.IMPORT_ICON, 30, 30));
        this.overlayScrollbar =
                new OverlayScrollbar(
                        this.right - 3.0F,
                        this.top + this.toolbarHeight,
                        this.right - 1.0F,
                        this.bottom - this.toolbarHeight,
                        AltManagerOverlayScreen.scrollbarColor,
                        this,
                        i - this.toolbarHeight);
        super.init();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ProxyModalPanel.getLegacyProxyPanelState();

        for (OverlayIconButton button : this.toolbarButtons) {
            if (button.isHovered()) {
                this.handleButtonAction(button.getActionId());
            }
        }
        for (OverlayAccountCard card : this.accountCards) {
            if (!card.isHovered()) {
                continue;
            }
            if (card == AltManagerOverlayScreen.selectedAccountCard) {
                card.mouseClicked(mouseX, mouseY, mouseButton);
            } else {
                AltManagerOverlayScreen.selectedAccountCard = card;
            }
        }
        if (this.overlayScrollbar.isHovered()) {
            this.overlayScrollbar.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int i, int j) {
        this.overlayScrollbar.resetActive();
        super.mouseReleased(i, j);
    }

    public AltListModalPanel(
            AltManagerOverlayScreen altManagerOverlayScreenValue,
            float f,
            float f1,
            float f2,
            float f3,
            int i,
            int j,
            List<AltAccountEntry> list) {
        super(f, f1, f2, f3, i);
        this.altManagerOverlayScreen = altManagerOverlayScreenValue;
        this.toolbarHeight = j;
        this.accounts = list;
    }

    static {
        remix.ui.account.AltListModalPanel.ALTENING_ICON = new ResourceLocation("remix/gui/altmanager/TheAltening.png");
        remix.ui.account.AltListModalPanel.PROXY_ICON = new ResourceLocation("remix/gui/altmanager/Proxy.png");
        remix.ui.account.AltListModalPanel.BACK_ICON = new ResourceLocation("remix/gui/altmanager/Back.png");
        remix.ui.account.AltListModalPanel.ADD_ICON = new ResourceLocation("remix/gui/altmanager/Add.png");
        remix.ui.account.AltListModalPanel.IMPORT_ICON = new ResourceLocation("remix/gui/altmanager/Import.png");
    }

    @Override
    public void handleButtonAction(int actionId) {
        boolean continueImport = ProxyModalPanel.getLegacyProxyPanelState() != null;

        switch (actionId) {
            case 1:
                this.altManagerOverlayScreen.setModalOverlay(
                        new TheAlteningModalPanel(
                                this.altManagerOverlayScreen, this.left, this.top, this.right, this.bottom, this.backgroundColor));
                return;
            case 2:
                MinecraftClient.getMinecraft().displayGuiScreen(this.altManagerOverlayScreen.getGuiScreen());
                return;
            case 3:
                this.altManagerOverlayScreen.setModalOverlay(
                        new AddAltModalPanel(
                                this.altManagerOverlayScreen, this.left, this.top, this.right, this.bottom, this.backgroundColor));
                return;
            case 4:
                this.importAccounts(continueImport);
                return;
            case 420:
                this.altManagerOverlayScreen.setModalOverlay(
                        new ProxyModalPanel(
                                this.altManagerOverlayScreen, this.left, this.top, this.right, this.bottom, this.backgroundColor));
                return;
            default:
        }
    }

    private void importAccounts(boolean continueImport) {
        ModalTopJFileChooser2 chooser = new ModalTopJFileChooser2(
                this, FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFocusable(true);
        chooser.grabFocus();
        chooser.requestFocus();
        chooser.transferFocus();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(ModalTopJFileChooser2.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
        if (chooser.isFocusable()) {
            chooser.requestFocus();
        }
        if (chooser.showOpenDialog(null) != ModalTopJFileChooser2.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(":");
                String email = "";
                String username = "";
                String password = "";
                AltAccountType accountType = AltAccountType.CRACKED;
                switch (fields.length) {
                    case 1:
                        if (fields[0].contains("@")) {
                            return;
                        }
                        username = fields[0];
                        break;
                    case 3:
                        username = fields[2];
                        // The third field is optional; credentials are identical in both formats.
                    case 2:
                        email = fields[0];
                        password = fields[1];
                        accountType = AltAccountType.PREMIUM;
                        break;
                    default:
                }
                this.altManagerOverlayScreen.altLoginManager.addAccount(
                        new AltAccountEntry(email, username, password, accountType));
                this.init();
                // Original remix.bD.a(int) branches back to readLine only with a non-null marker.
                if (!continueImport) {
                    break;
                }
            }
        } catch (IOException failure) {
            failure.printStackTrace();
        }
    }
}
