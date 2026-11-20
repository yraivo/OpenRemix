package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

public class GuiPerformanceSettingsOF extends GuiScreen {

    protected String title;

    private GuiScreen prevScreen;
    private static GameSettingsOptions[] enumOptions;

    private TooltipProviderOptions tooltipManager = new TooltipProviderOptions(this);
    private GameSettings settings;

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
        }
    }

    static {
        optifine.GuiPerformanceSettingsOF.enumOptions =
                new GameSettingsOptions[] {
                    GameSettingsOptions.SMOOTH_FPS,
                    GameSettingsOptions.SMOOTH_WORLD,
                    GameSettingsOptions.FAST_RENDER,
                    GameSettingsOptions.FAST_MATH,
                    GameSettingsOptions.CHUNK_UPDATES,
                    GameSettingsOptions.CHUNK_UPDATES_DYNAMIC,
                    GameSettingsOptions.LAZY_CHUNK_LOADING
                };
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
        this.tooltipManager.drawTooltips(i, j, this.buttonList);
    }

    public GuiPerformanceSettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
        this.prevScreen = guiScreen;
        this.settings = gameSettings;
    }

    public void initGui() {
        int i1 = ReflectorMethod.cR();
        this.title = I18n.format("of.options.performanceTitle", new Object[0]);
        int i = i1;
        this.buttonList.clear();
        int j = 0;

        while (j < optifine.GuiPerformanceSettingsOF.enumOptions.length) {
            {
                GameSettingsOptions gameSettingsOptions = optifine.GuiPerformanceSettingsOF.enumOptions[j];
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
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 168 + 11,
                        I18n.format("gui.done", new Object[0])));
    }
}
