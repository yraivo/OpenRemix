package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.stream.GuiStreamOptions;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.stream.IStream;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

import optifine.OptiFineCapeEditorScreen;

import java.io.IOException;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback {

    private static String[] packedTextFragments;
    private static GameSettingsOptions[] primaryOptions;
    private GuiScreen parentScreen;
    private GuiButton difficultyButton;
    protected String screenTitle = "Options";
    private GameSettings gameSettings;
    private GuiLockIconButton difficultyLockButton;

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                GameSettingsOptions gameSettingsOptions =
                        ((GuiOptionButton) guiButton).returnEnumOptions();
                this.gameSettings.setOptionValue(gameSettingsOptions, 1);
                guiButton.displayString =
                        this.gameSettings.getKeyBinding(
                                GameSettingsOptions.getEnumOptions(guiButton.id));
            }

            if (guiButton.id == 108) {
                this.mc
                        .world
                        .getWorldInfo()
                        .setDifficulty(
                                EnumDifficulty.getDifficultyEnum(
                                        this.mc.world.getDifficulty().getDifficultyId() + 1));
                this.difficultyButton.displayString =
                        this.formatDifficultyLabel(this.mc.world.getDifficulty());
            }

            if (guiButton.id == 109) {
                this.mc.displayGuiScreen(
                        new GuiYesNo(
                                this,
                                new ChatComponentTranslation("difficulty.lock.title", new Object[0])
                                        .getFormattedText(),
                                new ChatComponentTranslation(
                                                "difficulty.lock.question",
                                                new Object[] {
                                                    new ChatComponentTranslation(
                                                            this.mc
                                                                    .world
                                                                    .getWorldInfo()
                                                                    .getDifficulty()
                                                                    .getDifficultyResourceKey(),
                                                            new Object[0])
                                                })
                                        .getFormattedText(),
                                109));
            }

            if (guiButton.id == 110) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
            }

            if (guiButton.id == 8675309) {
                this.mc.entityRenderer.activateNextShader();
            }

            if (guiButton.id == 101) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.gameSettings));
            }

            if (guiButton.id == 100) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiControls(this, this.gameSettings));
            }

            if (guiButton.id == 102) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(
                        new GuiLanguage(this, this.gameSettings, this.mc.getLanguageManager()));
            }

            if (guiButton.id == 103) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.gameSettings));
            }

            if (guiButton.id == 104) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSnooper(this, this.gameSettings));
            }

            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }

            if (guiButton.id == 105) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }

            if (guiButton.id == 106) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.gameSettings));
            }

            if (guiButton.id == 107) {
                this.mc.gameSettings.saveOptions();
                IStream stream = this.mc.getTwitchStream();
                boolean canOpenStreamOptions = stream.func_152936_l() && stream.func_152928_D();
                if (canOpenStreamOptions) {
                    this.mc.displayGuiScreen(new GuiStreamOptions(this, this.gameSettings));
                }
                if (!canOpenStreamOptions || astring != null) {
                    GuiStreamUnavailable.showUnavailableScreen(this);
                }
            }

            if (guiButton.id == 62727568) {
                MinecraftClient.getMinecraft().displayGuiScreen(new OptiFineCapeEditorScreen(this));
            }
        }
    }

    static {
        net.minecraft.client.gui.GuiOptions.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiOptions.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiOptions.primaryOptions =
                new GameSettingsOptions[] {GameSettingsOptions.FOV};
    }

    public GuiOptions(GuiScreen aocValue, GameSettings gameSettings) {
        this.parentScreen = aocValue;
        this.gameSettings = gameSettings;
    }

    public void confirmClicked(boolean flag, int i) {
        this.mc.displayGuiScreen(this);
        if (i == 109 && flag && this.mc.world != null) {
            this.mc.world.getWorldInfo().setDifficultyLocked(true);
            this.difficultyLockButton.setLocked(true);
            this.difficultyLockButton.enabled = false;
            this.difficultyButton.enabled = false;
        }
    }

    public String formatDifficultyLabel(EnumDifficulty enumDifficulty) {
        ChatComponentText chatComponentText = new ChatComponentText(packedTextFragments[0]);
        chatComponentText.appendSibling(
                new ChatComponentTranslation("options.difficulty", new Object[0]));
        chatComponentText.appendText(": ");
        chatComponentText.appendSibling(
                new ChatComponentTranslation(
                        enumDifficulty.getDifficultyResourceKey(), new Object[0]));
        return chatComponentText.getFormattedText();
    }

    public void initGui() {
        int i = 0;
        this.screenTitle = I18n.format("options.title", new Object[0]);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GameSettingsOptions[] aobValue = net.minecraft.client.gui.GuiOptions.primaryOptions;
        int j = aobValue.length;
        int k = 0;
        String[] astring = astring1;

        while (k < j) {
            GameSettingsOptions option = aobValue[k];
            boolean slider = option.getEnumFloat();
            if (slider) {
                this.buttonList.add(
                        new GuiOptionSlider(
                                option.returnEnumOrdinal(),
                                this.width / 2 - 155 + i % 2 * 160,
                                this.height / 6 - 12 + 24 * (i >> 1),
                                option));
                if (astring != null) {
                    Entity.setLegacyGlobalFlowMarker(new String[2]);
                }
            }
            if (!slider || astring != null) {
                this.buttonList.add(
                        new GuiOptionButton(
                                option.returnEnumOrdinal(),
                                this.width / 2 - 155 + i % 2 * 160,
                                this.height / 6 - 12 + 24 * (i >> 1),
                                option,
                                this.gameSettings.getKeyBinding(option)));
            }

            i++;
            k++;
            if (astring != null) {
                break;
            }
        }

        if (this.mc.world != null) {
            EnumDifficulty difficulty = this.mc.world.getDifficulty();
            this.difficultyButton =
                    new GuiButton(
                            108,
                            this.width / 2 - 155 + i % 2 * 160,
                            this.height / 6 - 12 + 24 * (i >> 1),
                            150,
                            20,
                            this.formatDifficultyLabel(difficulty));
            this.buttonList.add(this.difficultyButton);
            boolean canLockDifficulty =
                    this.mc.isSingleplayer()
                            && !this.mc.world.getWorldInfo().isHardcoreModeEnabled();
            if (canLockDifficulty) {
                this.difficultyButton.setWidth(this.difficultyButton.getButtonWidth() - 20);
                this.difficultyLockButton =
                        new GuiLockIconButton(
                                109,
                                this.difficultyButton.xPosition
                                        + this.difficultyButton.getButtonWidth(),
                                this.difficultyButton.yPosition);
                this.buttonList.add(this.difficultyLockButton);
                this.difficultyLockButton.setLocked(
                        this.mc.world.getWorldInfo().isDifficultyLocked());
                this.difficultyLockButton.enabled =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!this.difficultyLockButton.isLocked() ? 1 : 0));
                this.difficultyButton.enabled =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!this.difficultyLockButton.isLocked() ? 1 : 0));
            }
            if (!canLockDifficulty || astring != null) {
                this.difficultyButton.enabled = false;
            }
        }

        this.buttonList.add(
                new GuiButton(
                        110,
                        this.width / 2 - 155,
                        this.height / 6 + 48 - 6,
                        150,
                        20,
                        I18n.format("options.skinCustomisation", new Object[0])));
        this.buttonList.add(
                new GuiOptionsGuiButton1(
                        this,
                        8675309,
                        this.width / 2 + 5,
                        this.height / 6 + 48 - 6,
                        150,
                        20,
                        "Super Secret Settings..."));
        this.buttonList.add(
                new GuiButton(
                        106,
                        this.width / 2 - 155,
                        this.height / 6 + 72 - 6,
                        150,
                        20,
                        I18n.format("options.sounds", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        107,
                        this.width / 2 + 5,
                        this.height / 6 + 72 - 6,
                        150,
                        20,
                        I18n.format("options.stream", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        101,
                        this.width / 2 - 155,
                        this.height / 6 + 96 - 6,
                        150,
                        20,
                        I18n.format("options.video", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        100,
                        this.width / 2 + 5,
                        this.height / 6 + 96 - 6,
                        150,
                        20,
                        I18n.format("options.controls", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        102,
                        this.width / 2 - 155,
                        this.height / 6 + 120 - 6,
                        150,
                        20,
                        I18n.format("options.language", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        103,
                        this.width / 2 + 5,
                        this.height / 6 + 120 - 6,
                        150,
                        20,
                        I18n.format("options.multiplayer.title", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        105,
                        this.width / 2 - 155,
                        this.height / 6 + 144 - 6,
                        150,
                        20,
                        I18n.format("options.resourcepack", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        104,
                        this.width / 2 + 5,
                        this.height / 6 + 144 - 6,
                        150,
                        20,
                        I18n.format("options.snooper.view", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 - 100,
                        this.height / 6 + 168,
                        I18n.format("gui.done", new Object[0])));
        GuiButton guiButton;
        this.buttonList.add(
                guiButton = new GuiButton(62727568, 2, this.height - 20, 100, 20, "Optifine Cape"));
        if (MinecraftClient.getMinecraft().world == null) {
            guiButton.visible = false;
        }
    }

    private static IOException preserveOptionsIOException(IOException ioexception) {
        return ioexception;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
        super.drawScreen(i, j, f);
    }
}
