package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

public class GuiQualitySettingsOF extends GuiScreen {

    private TooltipProviderOptions tooltipManager = new TooltipProviderOptions(this);
    protected String title;
    private GameSettings settings;

    private static GameSettingsOptions[] enumOptions;
    private GuiScreen prevScreen;

    public void initGui() {
        int i1 = ReflectorMethod.f();
        this.title = I18n.format("of.options.qualityTitle");
        this.buttonList.clear();
        int i = i1;
        int j = 0;

        while (j < optifine.GuiQualitySettingsOF.enumOptions.length) {
            {
                GameSettingsOptions gameSettingsOptions = optifine.GuiQualitySettingsOF.enumOptions[j];
                int k = this.width / 2 - 155 + j % 2 * 160;
                int l = this.height / 6 + 21 * (j / 2) - 12;
                if (!gameSettingsOptions.getEnumFloat()) {
                    this.buttonList.add(
                            new GuiOptionButtonOF(
                                    gameSettingsOptions.returnEnumOrdinal(),
                                    k,
                                    l,
                                    gameSettingsOptions,
                                    this.settings.getKeyBinding(gameSettingsOptions)));
                    if (!(i == 0)) {

                        this.buttonList.add(
                                new GuiOptionSliderOF(
                                        gameSettingsOptions.returnEnumOrdinal(),
                                        k,
                                        l,
                                        gameSettingsOptions));
                    }
                } else {

                    this.buttonList.add(
                            new GuiOptionSliderOF(
                                    gameSettingsOptions.returnEnumOrdinal(),
                                    k,
                                    l,
                                    gameSettingsOptions));
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 168 + 11,
                        I18n.format("gui.done")));
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
        this.tooltipManager.drawTooltips(i, j, this.buttonList);
    }

    static {
        optifine.GuiQualitySettingsOF.enumOptions =
                new GameSettingsOptions[] {
                    GameSettingsOptions.MIPMAP_LEVELS,
                    GameSettingsOptions.MIPMAP_TYPE,
                    GameSettingsOptions.AF_LEVEL,
                    GameSettingsOptions.AA_LEVEL,
                    GameSettingsOptions.CLEAR_WATER,
                    GameSettingsOptions.RANDOM_MOBS,
                    GameSettingsOptions.BETTER_GRASS,
                    GameSettingsOptions.BETTER_SNOW,
                    GameSettingsOptions.CUSTOM_FONTS,
                    GameSettingsOptions.CUSTOM_COLORS,
                    GameSettingsOptions.SWAMP_COLORS,
                    GameSettingsOptions.SMOOTH_BIOMES,
                    GameSettingsOptions.CONNECTED_TEXTURES,
                    GameSettingsOptions.NATURAL_TEXTURES,
                    GameSettingsOptions.CUSTOM_SKY,
                    GameSettingsOptions.CUSTOM_ITEMS,
                    GameSettingsOptions.DYNAMIC_LIGHTS
                };
    }

    public GuiQualitySettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
        this.prevScreen = guiScreen;
        this.settings = gameSettings;
    }

    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(
                        ((GuiOptionButton) guiButton).returnEnumOptions(), 1);
                guiButton.displayString =
                        this.settings.getKeyBinding(GameSettingsOptions.getEnumOptions(guiButton.id));
            }

            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }

            if (guiButton.id != GameSettingsOptions.AA_LEVEL.ordinal()) {
                ScaledResolution scaledResolution =
                        new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                this.setWorldAndResolution(
                        this.mc, scaledResolution.getWidth(), scaledResolution.getHeight());
            }
        }
    }
}
