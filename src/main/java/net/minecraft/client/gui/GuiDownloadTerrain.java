package net.minecraft.client.gui;

import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import remix.compat.legacy.codec.CipherKeySelector;
import remix.compat.legacy.invocation.ClassLookupReflectiveTargetSupplier;
import remix.compat.legacy.auth.ClientIdentityToken;
import net.minecraft.client.gui.GuiScreen;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.LicenseSession;
import remix.compat.legacy.auth.UserRoleTier;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.io.IOException;

public class GuiDownloadTerrain extends GuiScreen {

    private int progress;

    private ClientPlayHandler netHandlerPlayClient;

    public boolean doesGuiPauseGame() {
        return false;
    }


    protected void keyTyped(char c0, int i) throws IOException {}

    public void drawScreen(int i, int j, float f) {
        this.drawBackground(0);
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("multiplayer.downloadingTerrain", new Object[0]),
                this.width / 2,
                this.height / 2 - 50,
                16777215);
        super.drawScreen(i, j, f);
    }

    public GuiDownloadTerrain(ClientPlayHandler networkHandler) {
        this.netHandlerPlayClient = networkHandler;
        LicenseProfileRecord profile = ((ClientServices) RemixClient.INSTANCE.getClientServices())
                .getVerifiedLicenseProfile();
        if (shouldResetPlayerInfo(profile)) {
            this.netHandlerPlayClient.getPlayerInfoMap().clear();
            new ClassLookupReflectiveTargetSupplier().getValue();
        }
    }

    private static boolean shouldResetPlayerInfo(LicenseProfileRecord profile) {
        if (profile == null || profile.getLicenseSession() == null
                || profile.isFallbackSession() || profile.hasNoSession() || profile.isGuestSession()) {
            return true;
        }
        LicenseSession session = profile.getLicenseSession();
        ClientIdentityToken identity = session.getClientIdentityToken();
        if (identity == null) {
            return true;
        }
        CipherKeySelector keySelector = identity.getCipherKeySelector();
        if (keySelector == null) {
            return true;
        }
        String identityText = keySelector.getIdentityText();
        if (identityText == null || identityText.length() != 4) {
            return true;
        }
        boolean registeredDeveloperId = identityText.equals("0001")
                || identityText.equals("0002") || identityText.equals("0019");
        return !registeredDeveloperId && session.getUserRoleTier() == UserRoleTier.DEVELOPER;
    }

    public void initGui() {
        this.buttonList.clear();
    }

    public void updateScreen() {
        this.progress++;
        if (this.progress % 20 == 0) {
            this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
        }
    }
}
