package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import remix.ui.account.AltAccountEntry;
import remix.ui.account.AltAccountType;
import remix.ui.account.AltServiceSwitcher;
import net.minecraft.client.gui.GuiScreen;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.screen.RemixMainMenuScreen;

import com.thealtening.api.data.AccountData;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;

public class GuiIngameMenu extends GuiScreen {
    private static String[] packedTextFragments;

    private int openTicks;
    private int w;

    public void updateScreen() {
        super.updateScreen();
        this.openTicks++;
    }

    static {
        net.minecraft.client.gui.GuiIngameMenu.packedTextFragments = new String[7];
        net.minecraft.client.gui.GuiIngameMenu.packedTextFragments[0] = new String("a");
        net.minecraft.client.gui.GuiIngameMenu.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiIngameMenu.packedTextFragments[2] = new String(":");
        net.minecraft.client.gui.GuiIngameMenu.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.GuiIngameMenu.packedTextFragments[4] = new String("");
    }

    public void initGui() {
        this.w = 0;
        this.buttonList.clear();
        int left = this.width / 2 - 100;
        int right = this.width / 2 + 2;
        int top = this.height / 4 - 16;

        String exitLabel = this.mc.isIntegratedServerRunning() ? "menu.returnToMenu" : "menu.disconnect";
        this.buttonList.add(new GuiButton(1, left, top + 120, I18n.format(exitLabel)));
        this.buttonList.add(new GuiButton(4, left, top + 24, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(0, left, top + 96, 98, 20, I18n.format("menu.options")));

        RemixClient.INSTANCE.getClientServices();
        boolean usesTheAltening = ClientServices.altServiceSwitcher.getServiceLabel().equalsIgnoreCase("TheAltening");
        this.buttonList.add(new GuiButton(69, left, top + 72, 200, 20,
                usesTheAltening ? "Generate Account" : "Clipboard Login"));

        GuiButton shareToLan = new GuiButton(7, right, top + 96, 98, 20, I18n.format("menu.shareToLan"));
        this.buttonList.add(shareToLan);
        this.buttonList.add(new GuiButton(5, left, top + 48, 98, 20, I18n.format("gui.achievements")));
        this.buttonList.add(new GuiButton(6, right, top + 48, 98, 20, I18n.format("gui.stats")));
        shareToLan.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().isPublishedToLan();
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("menu.game", new Object[0]),
                this.width / 2,
                40,
                16777215);
        super.drawScreen(i, j, f);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        boolean legacyMenuFlow = Gui.getLegacyGuiRenderMarker() != null;
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                if (!legacyMenuFlow) {
                    return;
                }
            case 1:
                this.leaveCurrentWorld(button);
                this.mc.displayGuiScreen(new RemixMainMenuScreen(RemixClient.INSTANCE));
            case 2:
            case 3:
            default:
                if (!legacyMenuFlow) {
                    return;
                }
            case 4:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                if (!legacyMenuFlow) {
                    return;
                }
            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.player.getStatFileWriter()));
                if (!legacyMenuFlow) {
                    return;
                }
            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
                if (!legacyMenuFlow) {
                    return;
                }
            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                // The recovered 1.7.1 action also proceeds to the account workflow here.
            case 69:
                this.switchAccountFromPauseMenu(button, legacyMenuFlow);
        }
    }

    private void leaveCurrentWorld(GuiButton button) {
        button.enabled = false;
        this.mc.world.sendQuittingDisconnectingPacket();
        this.mc.loadWorld(null);
    }

    private void switchAccountFromPauseMenu(GuiButton button, boolean legacyMenuFlow) {
        boolean integratedServer = this.mc.isIntegratedServerRunning();
        this.leaveCurrentWorld(button);
        if (integratedServer) {
            this.mc.displayGuiScreen(new RemixMainMenuScreen(RemixClient.INSTANCE));
        }
        if (!integratedServer || legacyMenuFlow) {
            this.mc.displayGuiScreen(new GuiMultiplayer(new RemixMainMenuScreen(RemixClient.INSTANCE)));
        }

        RemixClient.INSTANCE.getClientServices();
        AltServiceSwitcher service = ClientServices.altServiceSwitcher;
        if (service.getServiceLabel().equalsIgnoreCase("TheAltening")) {
            AccountData account = service.getTheAltening().generateAccount();
            if (account != null) {
                ClientServices.altLoginManager.loginWithCredentials(account.getToken(), "a");
            }
            if (!legacyMenuFlow) {
                return;
            }
        }

        try {
            String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            if (clipboard.isEmpty()) {
                return;
            }
            String[] fields = clipboard.split(":");
            AltAccountEntry account = fields.length >= 2 && !legacyMenuFlow
                    ? new AltAccountEntry(fields[0], "", fields[1], AltAccountType.PREMIUM)
                    : new AltAccountEntry(fields[0], fields[0], "", AltAccountType.CRACKED);
            RemixClient.INSTANCE.getClientServices();
            ClientServices.altLoginManager.login(account);
        } catch (Exception ignored) {
            // The original menu leaves clipboard/login failures to the account manager.
        }

        ServerData lastServer = GuiConnecting.lastServerData;
        if (!this.mc.isSingleplayer() && lastServer != null) {
            MinecraftClient client = MinecraftClient.getMinecraft();
            GuiScreen parent = new GuiMultiplayer(new RemixMainMenuScreen(RemixClient.INSTANCE));
            client.displayGuiScreen(new GuiConnecting(parent, client, lastServer));
        }
    }

}
