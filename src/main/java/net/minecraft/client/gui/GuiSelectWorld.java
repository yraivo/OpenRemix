package net.minecraft.client.gui;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.client.gui.GuiScreen;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback {
    private String Et;
    private GuiButton deleteButton;
    private static Logger logger;

    private GuiButton recreateButton;
    private GuiSelectWorldList asN;
    private List nr;
    protected GuiScreen parentScreen;
    protected String jU;
    private String[] xT;
    private GuiButton selectButton;
    private static String[] packedTextFragments;
    private boolean nh;
    private GuiButton renameButton;
    private final DateFormat asP = new SimpleDateFormat();

    private boolean aor;
    private int B;
    private String yn;

    static int access$100(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.B;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                String text = this.S(this.B);
                if (text != null) {
                    this.nh = true;
                    GuiYesNo guiYesNo = method_20e491b2eb2fdcb36a99(this, text, this.B);
                    this.mc.displayGuiScreen(guiYesNo);
                }

                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 1) {
                this.method_30be6ce7816ed46ef891(this.B);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 3) {
                this.mc.displayGuiScreen(new GuiCreateWorld(this));
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 6) {
                this.mc.displayGuiScreen(
                        new GuiRenameWorld(this, this.method_4419e981e8474809a672(this.B)));
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 7) {
                GuiCreateWorld guiCreateWorld = new GuiCreateWorld(this);
                ISaveHandler iSaveHandler =
                        this.mc
                                .getSaveLoader()
                                .getSaveLoader(this.method_4419e981e8474809a672(this.B), false);
                WorldInfo worldInfo = iSaveHandler.loadWorldInfo();
                iSaveHandler.flush();
                guiCreateWorld.method_f5d3cb21a2f987583571(worldInfo);
                this.mc.displayGuiScreen(guiCreateWorld);
                if (astring == null) {
                    return;
                }
            }

            this.asN.actionPerformed(guiButton);
        }
    }

    protected String S(int i) {
        String text = ((SaveFormatComparator) this.nr.get(i)).getDisplayName();
        if (StringUtils.isEmpty(text)) {
            text =
                    I18n.format("selectWorld.world", new Object[0])
                            + packedTextFragments[0]
                            + (i + 1);
        }

        return text;
    }

    static GuiButton access$500(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.recreateButton;
    }

    public GuiSelectWorld(GuiScreen anPValue) {
        this.jU = "Select world";
        this.xT = new String[4];
        this.parentScreen = anPValue;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    static String access$800(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.yn;
    }

    public void initGui() {
        this.jU = I18n.format("selectWorld.title", new Object[0]);

        try {
            this.method_8d9b536bcdc9991c48fb();
        } catch (AnvilConverterException anvilConverterException) {
            net.minecraft.client.gui.GuiSelectWorld.logger.error("Couldn't load level list", anvilConverterException);
            this.mc.displayGuiScreen(
                    new GuiErrorScreen(
                            "Unable to load worlds", anvilConverterException.getMessage()));
            return;
        }

        this.Et = I18n.format("selectWorld.world", new Object[0]);
        this.yn = I18n.format("selectWorld.conversion", new Object[0]);
        this.xT[WorldSettingsGameType.SURVIVAL.getID()] =
                I18n.format("gameMode.survival", new Object[0]);
        this.xT[WorldSettingsGameType.CREATIVE.getID()] =
                I18n.format("gameMode.creative", new Object[0]);
        this.xT[WorldSettingsGameType.ADVENTURE.getID()] =
                I18n.format("gameMode.adventure", new Object[0]);
        this.xT[WorldSettingsGameType.SPECTATOR.getID()] =
                I18n.format("gameMode.spectator", new Object[0]);
        this.asN = new GuiSelectWorldList(this, this.mc);
        this.asN.registerScrollButtons(4, 5);
        this.method_784e95624d6d70fe7f50();
    }

    static List access$000(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.nr;
    }

    public static GuiYesNo method_20e491b2eb2fdcb36a99(
            GuiYesNoCallback guiYesNoCallback, String text, int i) {
        String textValue = I18n.format("selectWorld.deleteQuestion", new Object[0]);
        String textValue2 =
                packedTextFragments[1]
                        + text
                        + "' "
                        + I18n.format("selectWorld.deleteWarning", new Object[0]);
        String textValue3 = I18n.format("selectWorld.deleteButton", new Object[0]);
        String textValue4 = I18n.format("gui.cancel", new Object[0]);
        return new GuiYesNo(guiYesNoCallback, textValue, textValue2, textValue3, textValue4, i);
    }

    private void method_8d9b536bcdc9991c48fb() throws AnvilConverterException {
        ISaveFormat saveLoader = this.mc.getSaveLoader();
        this.nr = saveLoader.getSaveList();
        Collections.sort(this.nr);
        this.B = -1;
    }

    public void method_30be6ce7816ed46ef891(int i) {
        this.mc.displayGuiScreen((GuiScreen) null);
        if (!this.aor) {
            this.aor = true;
            String text = this.method_4419e981e8474809a672(i);
            if (text == null) {
                text = "World" + i;
            }

            String textValue = this.S(i);
            if (textValue == null) {
                textValue = "World" + i;
            }

            if (this.mc.getSaveLoader().canLoadWorld(text)) {
                this.mc.launchIntegratedServer(text, textValue, (WorldSettings) null);
            }
        }
    }

    static GuiButton access$200(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.selectButton;
    }

    static GuiButton access$300(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.deleteButton;
    }

    public void drawScreen(int i, int j, float f) {
        this.asN.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, this.jU, this.width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

    protected String method_4419e981e8474809a672(int i) {
        return ((SaveFormatComparator) this.nr.get(i)).getFileName();
    }

    public void method_784e95624d6d70fe7f50() {
        this.buttonList.add(
                this.selectButton =
                        new GuiButton(
                                1,
                                this.width / 2 - 154,
                                this.height - 52,
                                150,
                                20,
                                I18n.format("selectWorld.select", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        3,
                        this.width / 2 + 4,
                        this.height - 52,
                        150,
                        20,
                        I18n.format("selectWorld.create", new Object[0])));
        this.buttonList.add(
                this.renameButton =
                        new GuiButton(
                                6,
                                this.width / 2 - 154,
                                this.height - 28,
                                72,
                                20,
                                I18n.format("selectWorld.rename", new Object[0])));
        this.buttonList.add(
                this.deleteButton =
                        new GuiButton(
                                2,
                                this.width / 2 - 76,
                                this.height - 28,
                                72,
                                20,
                                I18n.format("selectWorld.delete", new Object[0])));
        this.buttonList.add(
                this.recreateButton =
                        new GuiButton(
                                7,
                                this.width / 2 + 4,
                                this.height - 28,
                                72,
                                20,
                                I18n.format("selectWorld.recreate", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 + 82,
                        this.height - 28,
                        72,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.selectButton.enabled = false;
        this.deleteButton.enabled = false;
        this.renameButton.enabled = false;
        this.recreateButton.enabled = false;
    }

    static {
        net.minecraft.client.gui.GuiSelectWorld.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiSelectWorld.packedTextFragments[0] = new String(" ");
        net.minecraft.client.gui.GuiSelectWorld.packedTextFragments[1] = new String("'");
        net.minecraft.client.gui.GuiSelectWorld.logger = LogManager.getLogger();
    }

    static String access$600(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.Et;
    }

    static DateFormat access$700(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.asP;
    }

    static GuiButton access$400(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.renameButton;
    }

    static int access$102(GuiSelectWorld guiSelectWorld, int i) {
        return guiSelectWorld.B = i;
    }

    public void confirmClicked(boolean flag, int i) {
        if (this.nh) {
            this.nh = false;
            if (flag) {
                ISaveFormat saveLoader = this.mc.getSaveLoader();
                saveLoader.flushCache();
                saveLoader.deleteWorldDirectory(this.method_4419e981e8474809a672(i));

                try {
                    this.method_8d9b536bcdc9991c48fb();
                } catch (AnvilConverterException anvilConverterException) {
                    net.minecraft.client.gui.GuiSelectWorld.logger.error(
                            "Couldn't load level list", anvilConverterException);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    static String[] access$900(GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.xT;
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.asN.handleMouseInput();
    }
}
