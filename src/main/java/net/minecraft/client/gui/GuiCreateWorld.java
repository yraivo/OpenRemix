package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Random;

public class GuiCreateWorld extends GuiScreen {
    private GuiTextField xJ;
    private static String[] disallowedFilenames;
    private boolean ym;
    private GuiTextField xO;
    private boolean yf;
    private GuiButton btnMoreOptions;
    private boolean nh;
    private boolean lJ;
    private String ET;
    private String Eg;
    private String gameMode = "survival";
    private static String[] packedTextFragments;
    private boolean lK;
    private String ED;
    public String chunkProviderSettingsJson;

    private String ki;
    private int selectedIndex;
    private GuiButton btnCustomizeType;

    private GuiButton btnGameMode;
    private GuiButton btnAllowCommands;
    private GuiButton btnMapFeatures;
    private String Ez;
    private GuiButton btnBonusItems;
    private GuiScreen parentScreen;
    private boolean yM;
    private boolean xX;
    private GuiButton btnMapType;
    private String jU;

    private void method_784e95624d6d70fe7f50() {
        String[] astring;
        {
            String[] astring1 = Gui.getLegacyGuiRenderMarker();
            this.btnGameMode.displayString =
                    I18n.format("selectWorld.gameMode", new Object[0])
                            + ": "
                            + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
            this.ET =
                    I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
            this.Ez =
                    I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
            this.btnMapFeatures.displayString =
                    I18n.format("selectWorld.mapFeatures", new Object[0]) + packedTextFragments[2];
            astring = astring1;
            if (this.yf) {
                this.btnMapFeatures.displayString =
                        this.btnMapFeatures.displayString
                                + I18n.format("options.on", new Object[0]);
                if (!(astring == null)) {

                    this.btnMapFeatures.displayString =
                            this.btnMapFeatures.displayString
                                    + I18n.format("options.off", new Object[0]);
                }
            } else {

                this.btnMapFeatures.displayString =
                        this.btnMapFeatures.displayString
                                + I18n.format("options.off", new Object[0]);
            }
        }

        {
            this.btnBonusItems.displayString =
                    I18n.format("selectWorld.bonusItems", new Object[0]) + packedTextFragments[3];
            if (this.lK && !this.xX) {
                this.btnBonusItems.displayString =
                        this.btnBonusItems.displayString + I18n.format("options.on", new Object[0]);
                if (!(astring == null)) {

                    this.btnBonusItems.displayString =
                            this.btnBonusItems.displayString
                                    + I18n.format("options.off", new Object[0]);
                }
            } else {

                this.btnBonusItems.displayString =
                        this.btnBonusItems.displayString
                                + I18n.format("options.off", new Object[0]);
            }
        }

        this.btnMapType.displayString =
                I18n.format("selectWorld.mapType", new Object[0])
                        + packedTextFragments[4]
                        + I18n.format(
                                WorldType.worldTypes[this.selectedIndex].getTranslateName(),
                                new Object[0]);
        this.btnAllowCommands.displayString =
                I18n.format("selectWorld.allowCommands", new Object[0]) + packedTextFragments[5];
        if (this.yM && !this.xX) {
            this.btnAllowCommands.displayString =
                    this.btnAllowCommands.displayString + I18n.format("options.on", new Object[0]);
            if (astring == null) {
                return;
            }
        }

        this.btnAllowCommands.displayString =
                this.btnAllowCommands.displayString + I18n.format("options.off", new Object[0]);
    }

    public void method_f5d3cb21a2f987583571(WorldInfo worldInfo) {
        this.Eg =
                I18n.format("selectWorld.newWorld.copyOf", new Object[] {worldInfo.getWorldName()});
        this.ED = worldInfo.getSeed() + packedTextFragments[12];
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.selectedIndex = worldInfo.getTerrainType().getWorldTypeID();
        this.chunkProviderSettingsJson = worldInfo.getGeneratorOptions();
        this.yf = worldInfo.isMapFeaturesEnabled();
        this.yM = worldInfo.areCommandsAllowed();
        String[] astring = astring1;
        if (worldInfo.isHardcoreModeEnabled()) {
            this.gameMode = "hardcore";
            if (astring == null) {
                return;
            }
        }

        if (worldInfo.getGameType().isSurvivalOrAdventure()) {
            this.gameMode = "survival";
            if (astring == null) {
                return;
            }
        }

        if (worldInfo.getGameType().isCreative()) {
            this.gameMode = "creative";
        }
    }

    protected void keyTyped(char c0, int i) throws IOException {
        {
            String[] astring = Gui.getLegacyGuiRenderMarker();
            if (this.xO.isFocused() && !this.ym) {
                this.xO.textboxKeyTyped(c0, i);
                this.Eg = this.xO.getText();
                if (!(astring == null)) {

                    if (this.xJ.isFocused() && this.ym) {
                        this.xJ.textboxKeyTyped(c0, i);
                        this.ED = this.xJ.getText();
                    }
                }
            } else {

                if (this.xJ.isFocused() && this.ym) {
                    this.xJ.textboxKeyTyped(c0, i);
                    this.ED = this.xJ.getText();
                }
            }
        }

        if (i == 28 || i == 156) {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }

        ((GuiButton) this.buttonList.get(0)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.xO.getText().length() > 0 ? 1 : 0));
        this.method_8d9b536bcdc9991c48fb();
    }

    public GuiCreateWorld(GuiScreen guiScreen) {
        this.yf = true;
        this.chunkProviderSettingsJson = packedTextFragments[0];
        this.parentScreen = guiScreen;
        this.ED = packedTextFragments[1];
        this.Eg = I18n.format("selectWorld.newWorld", new Object[0]);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 155,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("selectWorld.create", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 + 5,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(
                this.btnGameMode =
                        new GuiButton(
                                2,
                                this.width / 2 - 75,
                                115,
                                150,
                                20,
                                I18n.format("selectWorld.gameMode", new Object[0])));
        this.buttonList.add(
                this.btnMoreOptions =
                        new GuiButton(
                                3,
                                this.width / 2 - 75,
                                187,
                                150,
                                20,
                                I18n.format("selectWorld.moreWorldOptions", new Object[0])));
        this.buttonList.add(
                this.btnMapFeatures =
                        new GuiButton(
                                4,
                                this.width / 2 - 155,
                                100,
                                150,
                                20,
                                I18n.format("selectWorld.mapFeatures", new Object[0])));
        this.btnMapFeatures.visible = false;
        this.buttonList.add(
                this.btnBonusItems =
                        new GuiButton(
                                7,
                                this.width / 2 + 5,
                                151,
                                150,
                                20,
                                I18n.format("selectWorld.bonusItems", new Object[0])));
        this.btnBonusItems.visible = false;
        this.buttonList.add(
                this.btnMapType =
                        new GuiButton(
                                5,
                                this.width / 2 + 5,
                                100,
                                150,
                                20,
                                I18n.format("selectWorld.mapType", new Object[0])));
        this.btnMapType.visible = false;
        this.buttonList.add(
                this.btnAllowCommands =
                        new GuiButton(
                                6,
                                this.width / 2 - 155,
                                151,
                                150,
                                20,
                                I18n.format("selectWorld.allowCommands", new Object[0])));
        this.btnAllowCommands.visible = false;
        this.buttonList.add(
                this.btnCustomizeType =
                        new GuiButton(
                                8,
                                this.width / 2 + 5,
                                120,
                                150,
                                20,
                                I18n.format("selectWorld.customizeType", new Object[0])));
        this.btnCustomizeType.visible = false;
        this.xO = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.xO.setFocused(true);
        this.xO.setText(this.Eg);
        this.xJ = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.xJ.setText(this.ED);
        this.method_5a0449839d28b4a71843(this.ym);
        this.method_8d9b536bcdc9991c48fb();
        this.method_784e95624d6d70fe7f50();
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        super.mouseClicked(i, j, k);
        String[] astring = astring1;
        if (this.ym) {
            this.xJ.mouseClicked(i, j, k);
            if (astring == null) {
                return;
            }
        }

        this.xO.mouseClicked(i, j, k);
    }

    public void updateScreen() {
        this.xO.updateCursorCounter();
        this.xJ.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void drawScreen(int i, int j, float f) {
        label24:
        {
            String[] astring1 = Gui.getLegacyGuiRenderMarker();
            this.drawDefaultBackground();
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("selectWorld.create", new Object[0]),
                    this.width / 2,
                    20,
                    -1);
            String[] astring = astring1;
            if (this.ym) {
                this.drawString(
                        this.fontRendererObj,
                        I18n.format("selectWorld.enterSeed", new Object[0]),
                        this.width / 2 - 100,
                        47,
                        -6250336);
                this.drawString(
                        this.fontRendererObj,
                        I18n.format("selectWorld.seedInfo", new Object[0]),
                        this.width / 2 - 100,
                        85,
                        -6250336);
                if (this.btnMapFeatures.visible) {
                    this.drawString(
                            this.fontRendererObj,
                            I18n.format("selectWorld.mapFeatures.info", new Object[0]),
                            this.width / 2 - 150,
                            122,
                            -6250336);
                }

                if (this.btnAllowCommands.visible) {
                    this.drawString(
                            this.fontRendererObj,
                            I18n.format("selectWorld.allowCommands.info", new Object[0]),
                            this.width / 2 - 150,
                            172,
                            -6250336);
                }

                this.xJ.drawTextBox();
                if (!WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice()) {
                    break label24;
                }

                this.fontRendererObj.drawSplitString(
                        I18n.format(
                                WorldType.worldTypes[this.selectedIndex].func_151359_c(),
                                new Object[0]),
                        this.btnMapType.xPosition + 2,
                        this.btnMapType.yPosition + 22,
                        this.btnMapType.getButtonWidth(),
                        10526880);
                if (astring == null) {
                    break label24;
                }
            }

            this.drawString(
                    this.fontRendererObj,
                    I18n.format("selectWorld.enterName", new Object[0]),
                    this.width / 2 - 100,
                    47,
                    -6250336);
            this.drawString(
                    this.fontRendererObj,
                    I18n.format("selectWorld.resultFolder", new Object[0])
                            + packedTextFragments[11]
                            + this.jU,
                    this.width / 2 - 100,
                    85,
                    -6250336);
            this.xO.drawTextBox();
            this.drawString(this.fontRendererObj, this.ET, this.width / 2 - 100, 137, -6250336);
            this.drawString(this.fontRendererObj, this.Ez, this.width / 2 - 100, 149, -6250336);
        }

        super.drawScreen(i, j, f);
    }

    private void method_5a0449839d28b4a71843(boolean flag) {
        String[] astring;
        label77:
        {
            String[] astring1 = Gui.getLegacyGuiRenderMarker();
            this.ym = flag;
            astring = astring1;
            if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
                this.btnGameMode.visible =
                        remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ym ? 1 : 0));
                this.btnGameMode.enabled = false;
                if (this.ki == null) {
                    this.ki = this.gameMode;
                }

                this.gameMode = "spectator";
                this.btnMapFeatures.visible = false;
                this.btnBonusItems.visible = false;
                this.btnMapType.visible = this.ym;
                this.btnAllowCommands.visible = false;
                this.btnCustomizeType.visible = false;
                if (astring == null) {
                    break label77;
                }
            }

            this.btnGameMode.visible =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ym ? 1 : 0));
            this.btnGameMode.enabled = true;
            if (this.ki != null) {
                this.gameMode = this.ki;
                this.ki = null;
            }

            this.btnMapFeatures.visible =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (this.ym
                                            && WorldType.worldTypes[this.selectedIndex]
                                                    != WorldType.CUSTOMIZED
                                    ? 1
                                    : 0));
            this.btnBonusItems.visible = this.ym;
            this.btnMapType.visible = this.ym;
            this.btnAllowCommands.visible = this.ym;
            this.btnCustomizeType.visible =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ym
                                            || WorldType.worldTypes[this.selectedIndex]
                                                            != WorldType.FLAT
                                                    && WorldType.worldTypes[this.selectedIndex]
                                                            != WorldType.CUSTOMIZED
                                    ? 0
                                    : 1));
        }

        this.method_784e95624d6d70fe7f50();
        if (this.ym) {
            this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
            if (astring == null) {
                return;
            }
        }

        this.btnMoreOptions.displayString =
                I18n.format("selectWorld.moreWorldOptions", new Object[0]);
    }

    public static String method_fc44f0c5b97e0a2b0ad7(ISaveFormat iSaveFormat, String text) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        text = text.replaceAll("[\\./\"]", packedTextFragments[6]);
        String[] astring = astring1;

        for (String textValue : net.minecraft.client.gui.GuiCreateWorld.disallowedFilenames) {
            if (text.equalsIgnoreCase(textValue)) {
                text = packedTextFragments[7] + text + packedTextFragments[8];
            }

            if (astring != null) {
                break;
            }
        }

        while (iSaveFormat.getWorldInfo(text) != null) {
            text = text + packedTextFragments[9];
            if (astring != null) {
                break;
            }
        }

        return text;
    }

    static {
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments = new String[14];
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[2] = new String(" ");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[3] = new String(" ");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[4] = new String(" ");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[5] = new String(" ");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[6] = new String("_");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[7] = new String("_");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[8] = new String("_");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[9] = new String("-");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[10] = new String("");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[11] = new String(" ");
        net.minecraft.client.gui.GuiCreateWorld.packedTextFragments[12] = new String("");
        net.minecraft.client.gui.GuiCreateWorld.disallowedFilenames =
                new String[] {
                    "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4",
                    "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5",
                    "LPT6", "LPT7", "LPT8", "LPT9"
                };
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 0) {
                this.mc.displayGuiScreen((GuiScreen) null);
                if (this.nh) {
                    return;
                }

                this.nh = true;
                long i = new Random().nextLong();
                String text = this.xJ.getText();
                if (!StringUtils.isEmpty(text)) {
                    try {
                        long j = Long.parseLong(text);

                        if (j != 0L) {
                            i = j;
                        }
                    } catch (NumberFormatException numberformatexception) {
                        i = text.hashCode();
                    }
                }

                WorldSettingsGameType worldSettingsGameType =
                        WorldSettingsGameType.getByName(this.gameMode);
                WorldSettings worldSettings =
                        new WorldSettings(
                                i,
                                worldSettingsGameType,
                                this.yf,
                                this.xX,
                                WorldType.worldTypes[this.selectedIndex]);
                worldSettings.setWorldName(this.chunkProviderSettingsJson);
                if (this.lK && !this.xX) {
                    worldSettings.enableBonusChest();
                }

                if (this.yM && !this.xX) {
                    worldSettings.enableCommands();
                }

                this.mc.launchIntegratedServer(this.jU, this.xO.getText().trim(), worldSettings);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 3) {
                this.ha();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 2) {
                label175:
                {
                    if (this.gameMode.equals("survival")) {
                        if (!this.lJ) {
                            this.yM = false;
                        }

                        this.xX = false;
                        this.gameMode = "hardcore";
                        this.xX = true;
                        this.btnAllowCommands.enabled = false;
                        this.btnBonusItems.enabled = false;
                        this.method_784e95624d6d70fe7f50();
                        if (astring == null) {
                            break label175;
                        }
                    }

                    if (this.gameMode.equals("hardcore")) {
                        if (!this.lJ) {
                            this.yM = true;
                        }

                        this.xX = false;
                        this.gameMode = "creative";
                        this.method_784e95624d6d70fe7f50();
                        this.xX = false;
                        this.btnAllowCommands.enabled = true;
                        this.btnBonusItems.enabled = true;
                        if (astring == null) {
                            break label175;
                        }
                    }

                    if (!this.lJ) {
                        this.yM = false;
                    }

                    this.gameMode = "survival";
                    this.method_784e95624d6d70fe7f50();
                    this.btnAllowCommands.enabled = true;
                    this.btnBonusItems.enabled = true;
                    this.xX = false;
                }

                this.method_784e95624d6d70fe7f50();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 4) {
                this.yf = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.yf ? 1 : 0));
                this.method_784e95624d6d70fe7f50();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 7) {
                this.lK = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.lK ? 1 : 0));
                this.method_784e95624d6d70fe7f50();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 5) {
                this.selectedIndex++;
                if (this.selectedIndex >= WorldType.worldTypes.length) {
                    this.selectedIndex = 0;
                }

                while (!this.canSelectCurWorldType()) {
                    this.selectedIndex++;
                    if (this.selectedIndex >= WorldType.worldTypes.length) {
                        this.selectedIndex = 0;
                        if (astring != null) {
                            break;
                        }
                    }
                }

                this.chunkProviderSettingsJson = packedTextFragments[10];
                this.method_784e95624d6d70fe7f50();
                this.method_5a0449839d28b4a71843(this.ym);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 6) {
                this.lJ = true;
                this.yM = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.yM ? 1 : 0));
                this.method_784e95624d6d70fe7f50();
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 8) {
                if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
                    this.mc.displayGuiScreen(
                            new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
                    if (astring == null) {
                        return;
                    }
                }

                this.mc.displayGuiScreen(
                        new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
            }
        }
    }

    private boolean canSelectCurWorldType() {
        WorldType worldType = WorldType.worldTypes[this.selectedIndex];
        return worldType != null && worldType.getCanBeCreated()
                ? (worldType == WorldType.DEBUG_WORLD ? isShiftKeyDown() : true)
                : false;
    }

    private void method_8d9b536bcdc9991c48fb() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.jU = this.xO.getText().trim();
        String[] astring = astring1;

        for (char c0 : ChatAllowedCharacters.allowedCharactersArray) {
            this.jU = this.jU.replace(c0, '_');
            if (astring != null) {
                break;
            }
        }

        if (StringUtils.isEmpty(this.jU)) {
            this.jU = "World";
        }

        this.jU = method_fc44f0c5b97e0a2b0ad7(this.mc.getSaveLoader(), this.jU);
    }

    private void ha() {
        this.method_5a0449839d28b4a71843(!this.ym);
    }
}
