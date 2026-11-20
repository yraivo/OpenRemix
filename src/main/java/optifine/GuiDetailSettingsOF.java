package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

public class GuiDetailSettingsOF extends GuiScreen {
    private GameSettings settings;
    private GuiScreen prevScreen;

    protected String title;
    private TooltipProviderOptions tooltipManager = new TooltipProviderOptions(this);

    private static GameSettingsOptions[] enumOptions;

    public GuiDetailSettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
        this.prevScreen = guiScreen;
        this.settings = gameSettings;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
        this.tooltipManager.drawTooltips(i, j, this.buttonList);
    }

    static {
        optifine.GuiDetailSettingsOF.enumOptions =
                new GameSettingsOptions[] {
                    GameSettingsOptions.CLOUDS,
                    GameSettingsOptions.CLOUD_HEIGHT,
                    GameSettingsOptions.TREES,
                    GameSettingsOptions.RAIN,
                    GameSettingsOptions.SKY,
                    GameSettingsOptions.STARS,
                    GameSettingsOptions.SUN_MOON,
                    GameSettingsOptions.SHOW_CAPES,
                    GameSettingsOptions.TRANSLUCENT_BLOCKS,
                    GameSettingsOptions.HELD_ITEM_TOOLTIPS,
                    GameSettingsOptions.DROPPED_ITEMS,
                    GameSettingsOptions.VIGNETTE,
                    GameSettingsOptions.DYNAMIC_FOV
                };
    }

    public void initGui() {
        this.title = I18n.format("of.options.detailsTitle", new Object[0]);
        this.buttonList.clear();
        int i1 = ReflectorMethod.cR();
        int j = 0;
        int i = i1;

        while (j < optifine.GuiDetailSettingsOF.enumOptions.length) {
            {
                GameSettingsOptions gameSettingsOptions = optifine.GuiDetailSettingsOF.enumOptions[j];
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
}
