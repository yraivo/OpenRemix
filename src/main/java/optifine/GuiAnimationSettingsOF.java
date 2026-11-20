package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

public class GuiAnimationSettingsOF extends GuiScreen {

    private GuiScreen prevScreen;
    private static GameSettingsOptions[] enumOptions;
    protected String title;

    private GameSettings settings;

    public GuiAnimationSettingsOF(GuiScreen guiScreen, GameSettings gameSettings) {
        this.prevScreen = guiScreen;
        this.settings = gameSettings;
    }

    public void initGui() {
        int i1 = ReflectorMethod.f();
        this.title = I18n.format("of.options.animationsTitle", new Object[0]);
        int i = i1;
        this.buttonList.clear();
        int j = 0;

        while (j < optifine.GuiAnimationSettingsOF.enumOptions.length) {
            {
                GameSettingsOptions gameSettingsOptions =
                        optifine.GuiAnimationSettingsOF.enumOptions[j];
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
                        210,
                        this.width / 2 - 155,
                        this.height / 6 + 168 + 11,
                        70,
                        20,
                        Lang.get("of.options.animation.allOn")));
        this.buttonList.add(
                new GuiButton(
                        211,
                        this.width / 2 - 155 + 80,
                        this.height / 6 + 168 + 11,
                        70,
                        20,
                        Lang.get("of.options.animation.allOff")));
        this.buttonList.add(
                new GuiOptionButton(
                        200,
                        this.width / 2 + 5,
                        this.height / 6 + 168 + 11,
                        I18n.format("gui.done", new Object[0])));
    }

    static {
        optifine.GuiAnimationSettingsOF.enumOptions =
                new GameSettingsOptions[] {
                    GameSettingsOptions.ANIMATED_WATER,
                    GameSettingsOptions.ANIMATED_LAVA,
                    GameSettingsOptions.ANIMATED_FIRE,
                    GameSettingsOptions.ANIMATED_PORTAL,
                    GameSettingsOptions.ANIMATED_REDSTONE,
                    GameSettingsOptions.ANIMATED_EXPLOSION,
                    GameSettingsOptions.ANIMATED_FLAME,
                    GameSettingsOptions.ANIMATED_SMOKE,
                    GameSettingsOptions.VOID_PARTICLES,
                    GameSettingsOptions.WATER_PARTICLES,
                    GameSettingsOptions.RAIN_SPLASH,
                    GameSettingsOptions.PORTAL_PARTICLES,
                    GameSettingsOptions.POTION_PARTICLES,
                    GameSettingsOptions.DRIPPING_WATER_LAVA,
                    GameSettingsOptions.ANIMATED_TERRAIN,
                    GameSettingsOptions.ANIMATED_TEXTURES,
                    GameSettingsOptions.FIREWORK_PARTICLES,
                    GameSettingsOptions.PARTICLES
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
                this.mc.gameSettings.setAllAnimations(true);
            }

            if (guiButton.id == 211) {
                this.mc.gameSettings.setAllAnimations(false);
            }

            ScaledResolution scaledResolution =
                    new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            this.setWorldAndResolution(
                    this.mc, scaledResolution.getWidth(), scaledResolution.getHeight());
        }
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
    }
}
