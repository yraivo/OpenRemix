package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import optifine.GuiAnimationSettingsOF;
import optifine.GuiDetailSettingsOF;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.GuiOtherSettingsOF;
import optifine.GuiPerformanceSettingsOF;
import optifine.GuiQualitySettingsOF;
import optifine.Lang;
import optifine.OptiFineConfig;
import optifine.TooltipProviderOptions;
import net.minecraft.client.gui.GuiScreen;
import remix.ui.clickgui.ClickGuiScreen;
import shadersmod.client.GuiShaders;

import java.io.IOException;

public class GuiVideoSettings extends GuiScreen {
    private static String[] packedTextFragments;
    private TooltipProviderOptions optionsRowList;

    private GameSettings guiGameSettings;
    private GuiScreen parentGuiScreen;

    private static GameSettingsOptions[] videoOptions;
    protected String screenTitle = "Video Settings";

    public void initGui() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        String[] astring = astring1;
        int i = 0;

        while (i < net.minecraft.client.gui.GuiVideoSettings.videoOptions.length) {
            GameSettingsOptions gameSettingsOptions = net.minecraft.client.gui.GuiVideoSettings.videoOptions[i];
            if (gameSettingsOptions != null) {
                int x = this.width / 2 - 155 + i % 2 * 160;
                int y = this.height / 6 + 21 * (i / 2) - 12;
                boolean slider = gameSettingsOptions.getEnumFloat();
                if (slider) {
                    this.buttonList.add(
                            new GuiOptionSliderOF(gameSettingsOptions.returnEnumOrdinal(), x, y, gameSettingsOptions));
                }
                if (!slider || astring != null) {
                    this.buttonList.add(
                            new GuiOptionButtonOF(
                                    gameSettingsOptions.returnEnumOrdinal(),
                                    x,
                                    y,
                                    gameSettingsOptions,
                                    this.guiGameSettings.getKeyBinding(gameSettingsOptions)));
                }
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        i = this.height / 6 + 21 * (net.minecraft.client.gui.GuiVideoSettings.videoOptions.length / 2) - 12;

        int l = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(231, l, i, Lang.get("of.options.shaders")));
        l = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, l, i, Lang.get("of.options.quality")));
        i += 21;
        l = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(201, l, i, Lang.get("of.options.details")));
        l = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(212, l, i, Lang.get("of.options.performance")));
        i += 21;
        l = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(211, l, i, Lang.get("of.options.animations")));
        l = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(222, l, i, Lang.get("of.options.other")));
        i += 21;
        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 168 + 11,
                        I18n.format("gui.done", new Object[0])));
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            int i = this.guiGameSettings.guiScale;
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.guiGameSettings.setOptionValue(
                        ((GuiOptionButton) guiButton).returnEnumOptions(), 1);
                guiButton.displayString =
                        this.guiGameSettings.getKeyBinding(GameSettingsOptions.getEnumOptions(guiButton.id));
            }

            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }

            if (this.guiGameSettings.guiScale != i) {
                ScaledResolution scaledResolution =
                        new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                int j = scaledResolution.getWidth();
                int k = scaledResolution.getHeight();
                ClickGuiScreen.scaleFactor = scaledResolution.getScaleFactor();
                this.setWorldAndResolution(this.mc, j, k);
            }

            if (guiButton.id == 201) {
                this.mc.gameSettings.saveOptions();
                GuiDetailSettingsOF guiDetailSettingsOF =
                        new GuiDetailSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiDetailSettingsOF);
            }

            if (guiButton.id == 202) {
                this.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF guiQualitySettingsOF =
                        new GuiQualitySettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiQualitySettingsOF);
            }

            if (guiButton.id == 211) {
                this.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF guiAnimationSettingsOF =
                        new GuiAnimationSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiAnimationSettingsOF);
            }

            if (guiButton.id == 212) {
                this.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF guiPerformanceSettingsOF =
                        new GuiPerformanceSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiPerformanceSettingsOF);
            }

            if (guiButton.id == 222) {
                this.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF guiOtherSettingsOF = new GuiOtherSettingsOF(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiOtherSettingsOF);
            }

            if (guiButton.id == 231) {
                if (OptiFineConfig.isAntialiasing() || OptiFineConfig.jb()) {
                    OptiFineConfig.showGuiMessage(
                            Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
                    return;
                }

                if (OptiFineConfig.isAnisotropicFiltering()) {
                    OptiFineConfig.showGuiMessage(
                            Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
                    return;
                }

                if (OptiFineConfig.isFastRender()) {
                    OptiFineConfig.showGuiMessage(
                            Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
                    return;
                }

                this.mc.gameSettings.saveOptions();
                GuiShaders guiShaders = new GuiShaders(this, this.guiGameSettings);
                this.mc.displayGuiScreen(guiShaders);
            }
        }
    }

    public static void drawGradientRect(
            GuiScreen guiScreen, int i, int j, int k, int l, int i1, int j1) {
        guiScreen.drawGradientRect(i, j, k, l, i1, j1);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
        String text = OptiFineConfig.getVersion();
        String textValue = "HD_U";
        if (textValue.equals("HD")) {
            text = "OptiFine HD H6";
        }

        if (textValue.equals("HD_U")) {
            text = "OptiFine HD H6 Ultra";
        }

        if (textValue.equals(packedTextFragments[0])) {
            text = "OptiFine H6 Light";
        }

        this.drawString(this.fontRendererObj, text, 2, this.height - 10, 8421504);
        String textValue2 = "Minecraft 1.8";
        int k = this.fontRendererObj.getStringWidth(textValue2);
        this.drawString(
                this.fontRendererObj, textValue2, this.width - k - 2, this.height - 10, 8421504);
        super.drawScreen(i, j, f);
        this.optionsRowList.drawTooltips(i, j, this.buttonList);
    }

    public static int getButtonWidth(GuiButton guiButton) {
        return guiButton.width;
    }

    public GuiVideoSettings(GuiScreen guiScreen, GameSettings gameSettings) {
        this.optionsRowList = new TooltipProviderOptions(this);
        this.parentGuiScreen = guiScreen;
        this.guiGameSettings = gameSettings;
    }

    public static int getButtonHeight(GuiButton guiButton) {
        return guiButton.height;
    }

    static {
        net.minecraft.client.gui.GuiVideoSettings.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiVideoSettings.packedTextFragments[0] = new String("L");
        net.minecraft.client.gui.GuiVideoSettings.videoOptions =
                new GameSettingsOptions[] {
                    GameSettingsOptions.aRM,
                    GameSettingsOptions.RENDER_DISTANCE,
                    GameSettingsOptions.aRU,
                    GameSettingsOptions.FRAMERATE_LIMIT,
                    GameSettingsOptions.AO_LEVEL,
                    GameSettingsOptions.VIEW_BOBBING,
                    GameSettingsOptions.aTo,
                    GameSettingsOptions.USE_VBO,
                    GameSettingsOptions.GAMMA,
                    GameSettingsOptions.BLOCK_ALTERNATIVES,
                    GameSettingsOptions.FOG_FANCY,
                    GameSettingsOptions.FOG_START
                };
    }
}
