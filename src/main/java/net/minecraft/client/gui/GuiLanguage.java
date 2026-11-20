package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiLanguage extends GuiScreen {
    private GuiLanguageList list;
    private static String[] packedTextFragments;
    private LanguageManager languageManager;

    protected GuiScreen parentScreen;

    private GuiOptionButton confirmSettingsBtn;
    private GuiOptionButton forceUnicodeFontBtn;
    private GameSettings game_settings_3;

    public void initGui() {
        this.buttonList.add(
                this.forceUnicodeFontBtn =
                        new GuiOptionButton(
                                100,
                                this.width / 2 - 155,
                                this.height - 38,
                                GameSettingsOptions.FORCE_UNICODE_FONT,
                                this.game_settings_3.getKeyBinding(GameSettingsOptions.FORCE_UNICODE_FONT)));
        this.buttonList.add(
                this.confirmSettingsBtn =
                        new GuiOptionButton(
                                6,
                                this.width / 2 - 155 + 160,
                                this.height - 38,
                                I18n.format("gui.done", new Object[0])));
        this.list = new GuiLanguageList(this, this.mc);
        this.list.registerScrollButtons(7, 8);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 5:
                    if (astring == null) {
                        break;
                    }
                case 6:
                    this.mc.displayGuiScreen(this.parentScreen);
                    if (astring == null) {
                        break;
                    }
                case 100:
                    if (!(guiButton instanceof GuiOptionButton)) {
                        break;
                    }

                    this.game_settings_3.setOptionValue(
                            ((GuiOptionButton) guiButton).returnEnumOptions(), 1);
                    guiButton.displayString = this.game_settings_3.getKeyBinding(GameSettingsOptions.FORCE_UNICODE_FONT);
                    ScaledResolution scaledResolution =
                            new ScaledResolution(
                                    this.mc, this.mc.displayWidth, this.mc.displayHeight);
                    int width = scaledResolution.getWidth();
                    int j = scaledResolution.getHeight();
                    this.setWorldAndResolution(this.mc, width, j);
                    if (astring == null) {
                        break;
                    }
                default:
                    this.list.actionPerformed(guiButton);
            }
        }
    }

    static {
        net.minecraft.client.gui.GuiLanguage.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiLanguage.packedTextFragments[0] = new String("(");
        net.minecraft.client.gui.GuiLanguage.packedTextFragments[1] = new String(")");
    }

    public GuiLanguage(
            GuiScreen guiScreen, GameSettings gameSettings, LanguageManager aqiValue) {
        this.parentScreen = guiScreen;
        this.game_settings_3 = gameSettings;
        this.languageManager = aqiValue;
    }

    static LanguageManager method_fa1c1ad0737ddbeaeb72(
            GuiLanguage guiLanguage) {
        return guiLanguage.languageManager;
    }

    static GuiOptionButton method_4ee61c0f11121b526f9e(
            GuiLanguage guiLanguage) {
        return guiLanguage.forceUnicodeFontBtn;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void drawScreen(int i, int j, float f) {
        this.list.drawScreen(i, j, f);
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("options.language", new Object[0]),
                this.width / 2,
                16,
                16777215);
        this.drawCenteredString(
                this.fontRendererObj,
                packedTextFragments[0]
                        + I18n.format("options.languageWarning", new Object[0])
                        + packedTextFragments[1],
                this.width / 2,
                this.height - 56,
                8421504);
        super.drawScreen(i, j, f);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    static GameSettings method_0e23fa9a30eb0fa24070(
            GuiLanguage guiLanguage) {
        return guiLanguage.game_settings_3;
    }

    static GuiOptionButton method_a40fa5101c49b3efea19(
            GuiLanguage guiLanguage) {
        return guiLanguage.confirmSettingsBtn;
    }
}
