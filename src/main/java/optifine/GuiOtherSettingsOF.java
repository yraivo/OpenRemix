package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback {
    private static GameSettingsOptions[] enumOptions;
    private GuiScreen prevScreen;
    private static String[] packedTextFragments;
    private TooltipProviderOptions tooltipManager = new TooltipProviderOptions(this);

    protected String title;
    private GameSettings settings;

    static {
        optifine.GuiOtherSettingsOF.packedTextFragments = new String[3];
        optifine.GuiOtherSettingsOF.packedTextFragments[0] = new String("");
        optifine.GuiOtherSettingsOF.enumOptions =
                new GameSettingsOptions[] {
                    GameSettingsOptions.LAGOMETER,
                    GameSettingsOptions.PROFILER,
                    GameSettingsOptions.WEATHER,
                    GameSettingsOptions.TIME,
                    GameSettingsOptions.USE_FULLSCREEN,
                    GameSettingsOptions.FULLSCREEN_MODE,
                    GameSettingsOptions.SHOW_FPS,
                    GameSettingsOptions.AUTOSAVE_TICKS,
                    GameSettingsOptions.ANAGLYPH
                };
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

            if (guiButton.id == 210) {
                this.mc.gameSettings.saveOptions();
                GuiYesNo guiYesNo =
                        new GuiYesNo(
                                this,
                                I18n.format("of.message.other.reset", new Object[0]),
                                packedTextFragments[0],
                                9999);
                this.mc.displayGuiScreen(guiYesNo);
            }
        }
    }

    public GuiOtherSettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
        this.prevScreen = guiScreen;
        this.settings = gameSettings;
    }

    public void confirmClicked(boolean flag, int i) {
        if (flag) {
            this.mc.gameSettings.resetSettings();
        }

        this.mc.displayGuiScreen(this);
    }

    public void initGui() {
        this.title = I18n.format("of.options.otherTitle", new Object[0]);
        int i1 = ReflectorMethod.cR();
        this.buttonList.clear();
        int i = i1;
        int j = 0;

        while (j < optifine.GuiOtherSettingsOF.enumOptions.length) {
            {
                GameSettingsOptions gameSettingsOptions = optifine.GuiOtherSettingsOF.enumOptions[j];
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
                    if (!(i != 0)) {

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
            if (i == 0) {
                break;
            }
        }

        this.buttonList.add(
                new GuiButton(
                        210,
                        this.width / 2 - 100,
                        this.height / 6 + 168 + 11 - 44,
                        I18n.format("of.options.other.reset", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 168 + 11,
                        I18n.format("gui.done", new Object[0])));
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
        this.tooltipManager.drawTooltips(i, j, this.buttonList);
    }
}
