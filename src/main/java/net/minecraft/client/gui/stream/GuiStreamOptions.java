package net.minecraft.client.gui.stream;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class GuiStreamOptions extends GuiScreen {

    private String ki;

    private GameSettings apz;
    private boolean yM = false;
    private int C;
    private String jT;
    private static GameSettingsOptions[] aob;
    private static GameSettingsOptions[] arO;
    private GuiScreen parentScreen;

    static {
        aob =
                new GameSettingsOptions[] {
                    GameSettingsOptions.STREAM_BYTES_PER_PIXEL,
                    GameSettingsOptions.STREAM_FPS,
                    GameSettingsOptions.STREAM_KBPS,
                    GameSettingsOptions.STREAM_SEND_METADATA,
                    GameSettingsOptions.STREAM_VOLUME_MIC,
                    GameSettingsOptions.STREAM_VOLUME_SYSTEM,
                    GameSettingsOptions.STREAM_MIC_TOGGLE_BEHAVIOR,
                    GameSettingsOptions.STREAM_COMPRESSION
                };
        arO =
                new GameSettingsOptions[] {
                    GameSettingsOptions.STREAM_CHAT_ENABLED,
                    GameSettingsOptions.STREAM_CHAT_USER_FILTER
                };
    }

    public GuiStreamOptions(GuiScreen anPValue, GameSettings apzValue) {
        this.parentScreen = anPValue;
        this.apz = apzValue;
    }

    public void initGui() {
        int j = 0;
        this.jT = I18n.format("options.stream.title", new Object[0]);
        int k = MathHelper.method_7eec03da329a017ec143();
        this.ki = I18n.format("options.stream.chat.title", new Object[0]);
        int i = k;

        for (GameSettingsOptions gameSettingsOptions : aob) {
            label56:
            {
                if (gameSettingsOptions.getEnumFloat()) {
                    this.buttonList.add(
                            new GuiOptionSlider(
                                    gameSettingsOptions.returnEnumOrdinal(),
                                    this.width / 2 - 155 + j % 2 * 160,
                                    this.height / 6 + 24 * (j >> 1),
                                    gameSettingsOptions));
                    if (i == 0) {
                        break label56;
                    }
                }

                this.buttonList.add(
                        new GuiOptionButton(
                                gameSettingsOptions.returnEnumOrdinal(),
                                this.width / 2 - 155 + j % 2 * 160,
                                this.height / 6 + 24 * (j >> 1),
                                gameSettingsOptions,
                                this.apz.getKeyBinding(gameSettingsOptions)));
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        if (j % 2 == 1) {
            j++;
        }

        this.C = this.height / 6 + 24 * (j >> 1) + 6;
        j += 2;

        for (GameSettingsOptions gameSettingsOptionsValue : arO) {
            label44:
            {
                if (gameSettingsOptionsValue.getEnumFloat()) {
                    this.buttonList.add(
                            new GuiOptionSlider(
                                    gameSettingsOptionsValue.returnEnumOrdinal(),
                                    this.width / 2 - 155 + j % 2 * 160,
                                    this.height / 6 + 24 * (j >> 1),
                                    gameSettingsOptionsValue));
                    if (i == 0) {
                        break label44;
                    }
                }

                this.buttonList.add(
                        new GuiOptionButton(
                                gameSettingsOptionsValue.returnEnumOrdinal(),
                                this.width / 2 - 155 + j % 2 * 160,
                                this.height / 6 + 24 * (j >> 1),
                                gameSettingsOptionsValue,
                                this.apz.getKeyBinding(gameSettingsOptionsValue)));
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 155,
                        this.height / 6 + 168,
                        150,
                        20,
                        I18n.format("gui.done", new Object[0])));
        GuiButton guiButton =
                new GuiButton(
                        201,
                        this.width / 2 + 5,
                        this.height / 6 + 168,
                        150,
                        20,
                        I18n.format("options.stream.ingestSelection", new Object[0]));
        guiButton.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        ((!this.mc.getTwitchStream().isReadyToBroadcast()
                                                || this.mc.getTwitchStream().func_152925_v().length
                                                        <= 0)
                                        && !this.mc.getTwitchStream().func_152908_z()
                                ? 0
                                : 1));
        this.buttonList.add(guiButton);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            MathHelper.method_09e43cd8dae4fda1c39e(++i);
        }
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.jT, this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.ki, this.width / 2, this.C, 16777215);
        if (this.yM) {
            this.drawCenteredString(
                    this.fontRendererObj,
                    EnumChatFormatting.RED + I18n.format("options.stream.changes", new Object[0]),
                    this.width / 2,
                    20 + this.fontRendererObj.FONT_HEIGHT,
                    16777215);
        }

        super.drawScreen(i, j, f);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        int i = MathHelper.method_7eec03da329a017ec143();
        if (guiButton.enabled) {
            label63:
            {
                if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                    GameSettingsOptions gameSettingsOptions =
                            ((GuiOptionButton) guiButton).returnEnumOptions();
                    this.apz.setOptionValue(gameSettingsOptions, 1);
                    guiButton.displayString =
                            this.apz.getKeyBinding(
                                    GameSettingsOptions.getEnumOptions(guiButton.id));
                    if (this.mc.getTwitchStream().isBroadcasting()
                            && gameSettingsOptions != GameSettingsOptions.STREAM_CHAT_ENABLED
                            && gameSettingsOptions != GameSettingsOptions.STREAM_CHAT_USER_FILTER) {
                        this.yM = true;
                    }

                    if (i == 0) {
                        break label63;
                    }
                }

                label52:
                if (guiButton instanceof GuiOptionSlider) {
                    if (guiButton.id == GameSettingsOptions.STREAM_VOLUME_MIC.returnEnumOrdinal()) {
                        this.mc.getTwitchStream().updateStreamVolume();
                        if (i == 0) {
                            break label52;
                        }
                    }

                    if (guiButton.id
                            == GameSettingsOptions.STREAM_VOLUME_SYSTEM.returnEnumOrdinal()) {
                        this.mc.getTwitchStream().updateStreamVolume();
                        if (i == 0) {
                            break label52;
                        }
                    }

                    if (this.mc.getTwitchStream().isBroadcasting()) {
                        this.yM = true;
                    }
                }
            }

            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
                if (i == 0) {
                    return;
                }
            }

            if (guiButton.id == 201) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiIngestServers(this));
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
