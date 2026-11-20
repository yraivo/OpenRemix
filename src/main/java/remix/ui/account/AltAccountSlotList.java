package remix.ui.account;

import net.minecraft.client.gui.GuiScreen;
import remix.ui.widget.ScrollableGuiSlot;
import remix.util.render.Render2D3DUtils;
import remix.util.render.WorldRenderUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;

import java.awt.Color;

public final class AltAccountSlotList extends ScrollableGuiSlot {
    private AltLoginManager altLoginManager;
    protected static ClientFontRenderer accountDetailsFont;

    protected static ClientFontRenderer usernameFont;

    protected void drawBackground() {
        WorldRenderUtils.drawBorderedRect(
                0.0, 0.0, this.width, this.height, 1426063360, 1426063360, 1.0);
    }

    protected void drawSlot(int i, int j, int k, int l, int i2, int j2) {
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        if (i >= 0 && i < this.altLoginManager.getAccounts().size()) {
            boolean selected = this.isSelected(i);
            int i1 = selected ? -14671840 : new Color(-14671840).darker().getRGB();
            Gui.drawRect(j, k, j + 200, k + l, i1);
            Render2D3DUtils.drawShadow(j, k, j + 200, k + l, 0.1F);
            AltAccountEntry altAccountEntry =
                    (AltAccountEntry) this.altLoginManager.getAccounts().get(i);
            if (this.mc
                    .getSession()
                    .getUsername()
                    .equalsIgnoreCase(altAccountEntry.getUsername())) {
                Gui.drawRect(
                        j - 12, k, j - 8, k + l, new Color(46, 204, 113).getRGB());
                Gui.drawRect(
                        j - 14, k, j - 10, k + l, new Color(39, 174, 96).getRGB());
            }

            String textValue = altAccountEntry.getEmail();
            String textValue2 = altAccountEntry.getUsername();
            ClientFontRenderer clientFontRenderer = remix.ui.account.AltAccountSlotList.accountDetailsFont;
            int j1 = (int) clientFontRenderer.getHeight();
            int k1 = (int) remix.ui.account.AltAccountSlotList.usernameFont.getHeight();
            int l1 = selected ? new Color(-14671840).darker().getRGB() : -14671840;
            Gui.drawRect(
                    j + 2, k + l / 2 - 14, j + 4 + 26, k + l / 2 - 12 + 26, l1);
            remix.ui.account.AltAccountSlotList.usernameFont.drawStringWithShadow(
                    textValue2,
                    j - 2 + this.cM() / 2 - remix.ui.account.AltAccountSlotList.usernameFont.getStringWidth(textValue2) / 2,
                    k + l / 2 - k1 - k1 / 4,
                    16777215);
            clientFontRenderer.drawStringWithShadow(
                    textValue,
                    j - 2 + this.cM() / 2 - clientFontRenderer.getStringWidth(textValue) / 2,
                    k + l / 2 + j1 / 4 + 2,
                    16777215);
            this.mc
                    .getTextureManager()
                    .bindTexture(
                            altAccountEntry.avatarTexture == null
                                    ? AltAccountEntry.defaultAvatarTexture
                                    : altAccountEntry.avatarTexture);
            GuiScreen.drawScaledCustomSizeModalRect(
                    j + 4, k + l / 2 - 12, 0.0F, 0.0F, 24.0F, 24.0F, 24.0F, 24.0F, 24.0F, 24.0F);
            if (altAccountEntry.loginDisabled) {
                WorldRenderUtils.drawRoundedOutline(
                        j, k, j + this.cM() - 4, k + l, -1.1576279E9F, -1157627904, 0);
                clientFontRenderer.drawStringWithShadow(
                        "Authenticating...",
                        j
                                - 2
                                + this.cM() / 2
                                - clientFontRenderer.getStringWidth("Authenticating...") / 2,
                        k + l / 2 - j1 / 2,
                        -1401332);
            }

            if (text == null) {
                return;
            }
        }

        remix.ui.account.AltAccountSlotList.accountDetailsFont.drawStringWithShadow(
                "\u00a7cFailed to load account.",
                j
                        - 2
                        + this.cM() / 2
                        - this.mc.fontRendererObj.getStringWidth("\u00a7cFailed to load account.")
                                / 2,
                k + l / 2 - this.mc.fontRendererObj.FONT_HEIGHT / 2,
                16777215);
    }

    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
    }

    static {
        remix.ui.account.AltAccountSlotList.accountDetailsFont = RemixClient.fonts.roboto18;
        remix.ui.account.AltAccountSlotList.usernameFont = RemixClient.fonts.roboto21;
    }

    protected int getContentHeight() {
        return this.getSize() * this.slotHeight;
    }

    protected boolean isSelected(int i) {
        return this.mc.currentScreen instanceof AltManagerScreen
                && i == ((AltManagerScreen) this.mc.currentScreen).selectedAccountIndex;
    }

    protected void elementClicked(int i, boolean flag, int k, int l) {
        if (this.mc.currentScreen instanceof AltManagerScreen) {
            AltManagerScreen currentScreen =
                    (AltManagerScreen) this.mc.currentScreen;
            currentScreen.selectedAccountIndex = i;
            int j = currentScreen.selectedAccountIndex >= 0 && currentScreen.selectedAccountIndex < this.getSize() ? 1 : 0;
            if (flag && j != 0) {
                this.altLoginManager.login(
                        (AltAccountEntry) this.altLoginManager.getAccounts().get(i));
            }
        }
    }

    public int getSize() {
        return this.altLoginManager.getAccounts().size();
    }

    public AltAccountSlotList(
            MinecraftClient minecraftClient, int i, int j, int k, int l, int i1) {
        super(minecraftClient, i, j, k, l, i1);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        this.altLoginManager = ClientServices.altLoginManager;
    }
}
