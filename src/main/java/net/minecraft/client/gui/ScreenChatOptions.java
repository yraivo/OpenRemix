package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class ScreenChatOptions extends GuiScreen {
    private String jU;

    private static GameSettingsOptions[] aob;
    private GuiScreen parentScreen;
    private GameSettings game_settings;
    private int B;

    private String jT;

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.jU, this.width / 2, 20, 16777215);
        this.drawCenteredString(
                this.fontRendererObj, this.jT, this.width / 2, this.B + 7, 16777215);
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int i = 0;
        this.jU = I18n.format("options.chat.title", new Object[0]);
        this.jT = I18n.format("options.multiplayer.title", new Object[0]);
        GameSettingsOptions[] aobValue = aob;
        int j = aobValue.length;
        String[] astring = astring1;
        int k = 0;

        while (k < j) {
            label21:
            {
                GameSettingsOptions gameSettingsOptions = aobValue[k];
                if (gameSettingsOptions.getEnumFloat()) {
                    this.buttonList.add(
                            new GuiOptionSlider(
                                    gameSettingsOptions.returnEnumOrdinal(),
                                    this.width / 2 - 155 + i % 2 * 160,
                                    this.height / 6 + 24 * (i >> 1),
                                    gameSettingsOptions));
                    if (astring == null) {
                        break label21;
                    }
                }

                this.buttonList.add(
                        new GuiOptionButton(
                                gameSettingsOptions.returnEnumOrdinal(),
                                this.width / 2 - 155 + i % 2 * 160,
                                this.height / 6 + 24 * (i >> 1),
                                gameSettingsOptions,
                                this.game_settings.getKeyBinding(gameSettingsOptions)));
            }

            i++;
            k++;
            if (astring != null) {
                break;
            }
        }

        if (i % 2 == 1) {
            i++;
        }

        this.B = this.height / 6 + 24 * (i >> 1);
        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 120,
                        I18n.format("gui.done", new Object[0])));
    }

    static {
        aob =
                new GameSettingsOptions[] {
                    GameSettingsOptions.CHAT_VISIBILITY,
                    GameSettingsOptions.CHAT_COLOR,
                    GameSettingsOptions.CHAT_LINKS,
                    GameSettingsOptions.CHAT_OPACITY,
                    GameSettingsOptions.CHAT_LINKS_PROMPT,
                    GameSettingsOptions.CHAT_SCALE,
                    GameSettingsOptions.CHAT_HEIGHT_FOCUSED,
                    GameSettingsOptions.CHAT_HEIGHT_UNFOCUSED,
                    GameSettingsOptions.CHAT_WIDTH,
                    GameSettingsOptions.REDUCED_DEBUG_INFO
                };
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                this.game_settings.setOptionValue(
                        ((GuiOptionButton) guiButton).returnEnumOptions(), 1);
                guiButton.displayString =
                        this.game_settings.getKeyBinding(GameSettingsOptions.getEnumOptions(guiButton.id));
            }

            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    public ScreenChatOptions(GuiScreen aocValue, GameSettings gameSettings) {
        this.parentScreen = aocValue;
        this.game_settings = gameSettings;
    }
}
