package remix.ui.account;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleMetadata;
import remix.ui.font.ClientFontRenderer;
import remix.ui.widget.ModalOverlayPanel;

import java.awt.Color;
import java.io.IOException;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class AltManagerOverlayScreen extends GuiScreen {
    public AltLoginManager altLoginManager;
    public static final int x = -14671840;
    private ModalOverlayPanel activePanel;
    public static final int scrollbarColor = new Color(-14935012).brighter().getRGB();
    public static final int w = -14935012;
    private ModalOverlayPanel previousPanel;
    private GuiScreen guiScreen;
    public static OverlayAccountCard selectedAccountCard;

    public void drawScreen(int i, int j, float f) {
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        Gui.drawRect(
                0.0, 0.0, scaledResolution.getWidth(), scaledResolution.getHeight(), -14935012);
        ClientFontRenderer clientFontRenderer = RemixClient.fonts.comfortaa18;
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        clientFontRenderer.drawCenteredString(
                ClientServices.altLoginManager.getStatusMessage(),
                scaledResolution.getWidth() / 2,
                scaledResolution.getHeight() / 2 - 150,
                -1);
        int l = ModuleMetadata.getLegacyModuleMetadataFlowToken();
        this.activePanel.draw(i, j);
        int k = l;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ModuleMetadata.setLegacyModuleMetadataFlowState(++k);
        }
    }

    public void displayParentScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        this.activePanel.mouseClicked(i, j, k);
    }

    protected void keyTyped(char c0, int i) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.activePanel.keyTyped(c0, i);
            if (i == 1) {
                this.returnToPreviousPanel();
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
        }
    }

    public void initGui() {
        int j = ModuleMetadata.getLegacyModuleMetadataFlowState();
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int i = j;
        if (this.activePanel == null) {
            float f = scaledResolution.getWidth() / 2 - 200;
            float f1 = scaledResolution.getHeight() / 2 - 120;
            float f2 = scaledResolution.getWidth() / 2 + 200;
            float f3 = scaledResolution.getHeight() / 2 + 120;
            ClientServices clientServices =
                    (ClientServices) RemixClient.INSTANCE.getClientServices();
            this.activePanel =
                    new AltListModalPanel(
                            this,
                            f,
                            f1,
                            f2,
                            f3,
                            -14671840,
                            30,
                            ClientServices.altLoginManager.getAccounts());
        }

        this.activePanel.setBounds(
                scaledResolution.getWidth() / 2 - 200,
                scaledResolution.getHeight() / 2 - 120,
                scaledResolution.getWidth() / 2 + 200,
                scaledResolution.getHeight() / 2 + 120);
        if (this.previousPanel != null) {
            this.previousPanel.setBounds(
                    scaledResolution.getWidth() / 2 - 200,
                    scaledResolution.getHeight() / 2 - 120,
                    scaledResolution.getWidth() / 2 + 200,
                    scaledResolution.getHeight() / 2 + 120);
        }

        this.activePanel.init();
        if (i != 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public GuiScreen getGuiScreen() {
        return this.guiScreen;
    }

    public void returnToPreviousPanel() {
        int i = ModuleMetadata.getLegacyModuleMetadataFlowState();
        if (this.previousPanel == null && this.guiScreen != null) {
            MinecraftClient.getMinecraft().displayGuiScreen(this.guiScreen);
            if (i == 0) {
                return;
            }
        }

        this.setModalOverlay(this.previousPanel);
        this.previousPanel = null;
    }

    public AltManagerOverlayScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        this.altLoginManager = ClientServices.altLoginManager;
    }

    public void setModalOverlay(ModalOverlayPanel activePanelValue) {
        this.previousPanel = this.activePanel;
        this.activePanel = activePanelValue;
        this.activePanel.init();
    }

    protected void mouseReleased(int i, int j, int k) {
        this.activePanel.mouseReleased(i, j);
    }

    static {
        LookAndFeel lookandfeel = UIManager.getLookAndFeel();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable throwable) {
            lookandfeel = null;
        }
    }
}
