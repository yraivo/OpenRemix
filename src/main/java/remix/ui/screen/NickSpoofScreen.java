package remix.ui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.Session;
import net.minecraft.util.SessionType;
import net.minecraft.client.gui.GuiScreen;
import remix.util.network.NordVpnProxyManager;

import java.io.IOException;

public final class NickSpoofScreen extends GuiScreen {
    protected GuiTextField spoofedUsernameField;
    protected GuiTextField sessionUsernameField;
    protected GuiScreen parentScreen;

    public static MinecraftClient minecraft;
    protected GuiTextField spoofedIpField;

    protected void keyTyped(char character, int keyCode) throws IOException {
        boolean legacyFocusFallthrough = NordVpnProxyManager.getLegacyProxyFlowState() != null;
        if (keyCode == 1) {
            remix.ui.screen.NickSpoofScreen.minecraft.displayGuiScreen(this.parentScreen);
            return;
        }
        if (keyCode == 15) {
            this.cycleFocus(legacyFocusFallthrough);
        }
        if (this.spoofedIpField.isFocused()) {
            this.spoofedIpField.textboxKeyTyped(character, keyCode);
        }
        if (this.spoofedUsernameField.isFocused()) {
            this.spoofedUsernameField.textboxKeyTyped(character, keyCode);
        }
        if (this.sessionUsernameField.isFocused()) {
            this.sessionUsernameField.textboxKeyTyped(character, keyCode);
        }
    }

    private void cycleFocus(boolean legacyFocusFallthrough) {
        if (this.sessionUsernameField.isFocused()) {
            this.sessionUsernameField.setFocused(false);
            this.spoofedUsernameField.setFocused(true);
            if (!legacyFocusFallthrough) {
                return;
            }
        }
        if (this.spoofedUsernameField.isFocused()) {
            this.spoofedUsernameField.setFocused(false);
            this.spoofedIpField.setFocused(true);
            if (!legacyFocusFallthrough) {
                return;
            }
        }
        if (this.spoofedIpField.isFocused()) {
            this.spoofedIpField.setFocused(false);
            this.sessionUsernameField.setFocused(true);
        }
    }

    static {
        remix.ui.screen.NickSpoofScreen.minecraft = MinecraftClient.getMinecraft();
    }

    public NickSpoofScreen(GuiScreen guiScreen) {
        this.parentScreen = guiScreen;
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        boolean legacyReturnAfterReconnect = NordVpnProxyManager.getLegacyProxyFlowState() != null;
        if (button.id == 1) {
            Session session = remix.ui.screen.NickSpoofScreen.minecraft.getSession();
            OfflineUuidResolver.setClientSession(
                    new Session(this.sessionUsernameField.getText(), session.getPlayerID(), session.getToken(),
                            SessionType.LEGACY.name()));
            OfflineUuidResolver.setSpoofedUsername(this.spoofedUsernameField.getText());
            OfflineUuidResolver.setSpoofedIp(this.spoofedIpField.getText());
            remix.ui.screen.NickSpoofScreen.minecraft.displayGuiScreen(this.parentScreen);
            boolean reconnect = remix.ui.screen.NickSpoofScreen.minecraft.getCurrentServerData() != null && remix.ui.screen.NickSpoofScreen.minecraft.world != null;
            if (reconnect) {
                ServerData server = remix.ui.screen.NickSpoofScreen.minecraft.getCurrentServerData();
                remix.ui.screen.NickSpoofScreen.minecraft.world.sendQuittingDisconnectingPacket();
                remix.ui.screen.NickSpoofScreen.minecraft.loadWorld(null);
                remix.ui.screen.NickSpoofScreen.minecraft.displayGuiScreen(new GuiConnecting(this.parentScreen, remix.ui.screen.NickSpoofScreen.minecraft, server));
            }
            if (!reconnect || legacyReturnAfterReconnect) {
                remix.ui.screen.NickSpoofScreen.minecraft.displayGuiScreen(this.parentScreen);
            }
            if (!legacyReturnAfterReconnect) {
                return;
            }
        }
        if (button.id == 2) {
            remix.ui.screen.NickSpoofScreen.minecraft.displayGuiScreen(this.parentScreen);
            if (!legacyReturnAfterReconnect) {
                return;
            }
        }
        if (button.id == 3) {
            OfflineUuidResolver.setSpoofEnabled(!OfflineUuidResolver.isSpoofEnabled());
            button.displayString = this.formatSpoofStatus();
        }
    }

    public void initGui() {
        short short1 = 200;
        byte b0 = 20;
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(1, this.width / 2 - 100, this.height / 4 + 95, short1, b0, "Save"));
        this.buttonList.add(
                new GuiButton(
                        2,
                        this.width / 2 - 100,
                        this.height / 4 + 95 + b0 + 4,
                        short1,
                        b0,
                        "Cancel"));
        this.buttonList.add(
                new GuiButton(
                        3,
                        this.width / 2 - 100,
                        this.height / 4 + 95 + b0 + 28,
                        short1,
                        b0,
                        this.formatSpoofStatus()));
        this.sessionUsernameField =
                new GuiTextField(
                        2, this.fontRendererObj, this.width / 2 - 100, this.height / 5, short1, b0);
        this.spoofedUsernameField =
                new GuiTextField(
                        1,
                        this.fontRendererObj,
                        this.width / 2 - 100,
                        this.height / 5 + 40,
                        short1,
                        b0);
        this.spoofedIpField =
                new GuiTextField(
                        0,
                        this.fontRendererObj,
                        this.width / 2 - 100,
                        this.height / 5 + 80,
                        short1,
                        b0);
        this.spoofedIpField.setText(OfflineUuidResolver.getSpoofedIp());
        this.spoofedUsernameField.setText(OfflineUuidResolver.getSpoofedUsername());
        this.sessionUsernameField.setText(remix.ui.screen.NickSpoofScreen.minecraft.getSession().getUsername());
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        this.spoofedIpField.mouseClicked(i, j, k);
        this.spoofedUsernameField.mouseClicked(i, j, k);
        this.sessionUsernameField.mouseClicked(i, j, k);
        super.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                "Real nick",
                this.width / 2,
                this.sessionUsernameField.yPosition - 15,
                16777215);
        this.drawCenteredString(
                this.fontRendererObj,
                "Fake nick",
                this.width / 2,
                this.spoofedUsernameField.yPosition - 15,
                16777215);
        this.drawCenteredString(
                this.fontRendererObj, "Fake IP", this.width / 2, this.spoofedIpField.yPosition - 15, 16777215);
        this.spoofedIpField.drawTextBox();
        this.spoofedUsernameField.drawTextBox();
        this.sessionUsernameField.drawTextBox();
        super.drawScreen(i, j, f);
    }

    private String formatSpoofStatus() {
        return OfflineUuidResolver.isSpoofEnabled() ? "\u00a7aEnabled" : "\u00a74Disabled";
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
}
