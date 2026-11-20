package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.settings.KeyBinding;

import java.io.IOException;

public class GuiControls extends GuiScreen {
    protected String screenTitle = "Controls";

    private GuiScreen parentScreen;
    private GuiKeyBindingList keyBindingList;
    private static GameSettingsOptions[] optionsArr;
    private GuiButton buttonReset;
    public KeyBinding buttonId = null;

    private GameSettings options;
    public long time;

    protected void keyTyped(char c0, int i) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.buttonId != null) {
            label33:
            {
                if (i == 1) {
                    this.options.setOptionKeyBinding(this.buttonId, 0);
                    if (astring == null) {
                        break label33;
                    }
                }

                if (i != 0) {
                    this.options.setOptionKeyBinding(this.buttonId, i);
                    if (astring == null) {
                        break label33;
                    }
                }

                if (c0 > 0) {
                    this.options.setOptionKeyBinding(this.buttonId, c0 + 256);
                }
            }

            this.buttonId = null;
            this.time = MinecraftClient.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
            if (astring == null) {
                return;
            }
        }

        super.keyTyped(c0, i);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.buttonId != null) {
            this.options.setOptionKeyBinding(this.buttonId, -100 + k);
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
            if (astring == null) {
                return;
            }
        }

        if (k != 0 || !this.keyBindingList.mouseClicked(i, j, k)) {
            super.mouseClicked(i, j, k);
        }
    }

    public void initGui() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.keyBindingList = new GuiKeyBindingList(this, this.mc);
        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 155,
                        this.height - 29,
                        150,
                        20,
                        I18n.format("gui.done", new Object[0])));
        String[] astring = astring1;
        this.buttonList.add(
                this.buttonReset =
                        new GuiButton(
                                201,
                                this.width / 2 - 155 + 160,
                                this.height - 29,
                                150,
                                20,
                                I18n.format("controls.resetAll", new Object[0])));
        this.screenTitle = I18n.format("controls.title", new Object[0]);
        int i = 0;

        for (GameSettingsOptions gameSettingsOptions :
                net.minecraft.client.gui.GuiControls.optionsArr) {
            label17:
            {
                if (gameSettingsOptions.getEnumFloat()) {
                    this.buttonList.add(
                            new GuiOptionSlider(
                                    gameSettingsOptions.returnEnumOrdinal(),
                                    this.width / 2 - 155 + i % 2 * 160,
                                    18 + 24 * (i >> 1),
                                    gameSettingsOptions));
                    if (astring == null) {
                        break label17;
                    }
                }

                this.buttonList.add(
                        new GuiOptionButton(
                                gameSettingsOptions.returnEnumOrdinal(),
                                this.width / 2 - 155 + i % 2 * 160,
                                18 + 24 * (i >> 1),
                                gameSettingsOptions,
                                this.options.getKeyBinding(gameSettingsOptions)));
            }

            i++;
            if (astring != null) {
                break;
            }
        }
    }

    static {
        net.minecraft.client.gui.GuiControls.optionsArr =
                new GameSettingsOptions[] {
                    GameSettingsOptions.INVERT_MOUSE,
                    GameSettingsOptions.SENSITIVITY,
                    GameSettingsOptions.TOUCHSCREEN
                };
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.keyBindingList.handleMouseInput();
    }

    protected void mouseReleased(int i, int j, int k) {
        if (k != 0 || !this.keyBindingList.releaseMouseOnEntries(i, j, k)) {
            super.mouseReleased(i, j, k);
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
            if (astring == null) {
                return;
            }
        }

        if (guiButton.id == 201) {
            for (KeyBinding keyBinding : this.mc.gameSettings.keyBindings) {
                keyBinding.setKeyCode(keyBinding.getKeyCodeDefault());
                if (astring != null) {
                    break;
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            if (astring == null) {
                return;
            }
        }

        if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
            this.options.setOptionValue(((GuiOptionButton) guiButton).returnEnumOptions(), 1);
            guiButton.displayString =
                    this.options.getKeyBinding(GameSettingsOptions.getEnumOptions(guiButton.id));
        }
    }

    public void drawScreen(int i, int j, float f) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(i, j, f);
        String[] astring = astring1;
        this.drawCenteredString(
                this.fontRendererObj, this.screenTitle, this.width / 2, 8, 16777215);
        byte b0 = 1;

        for (KeyBinding keyBinding : this.options.keyBindings) {
            if (keyBinding.getKeyCode() != keyBinding.getKeyCodeDefault()) {
                b0 = 0;
                if (astring == null) {
                    break;
                }
            }

            if (astring != null) {
                break;
            }
        }

        this.buttonReset.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((b0 == 0 ? 1 : 0));
        super.drawScreen(i, j, f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GuiControls(GuiScreen aocValue, GameSettings apzValue) {
        super();
        this.parentScreen = aocValue;
        this.options = apzValue;
    }
}
