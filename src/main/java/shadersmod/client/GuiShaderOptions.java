package shadersmod.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

import optifine.Lang;
import optifine.OptiFineConfig;
import optifine.StrUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GuiShaderOptions extends GuiScreen {
    public static String za;
    public static String yn;

    private long mouseStillTime;
    protected String title;
    private GuiScreen prevScreen;
    private int lastMouseY;
    private GameSettings settings;
    private static String[] packedTextFragments;
    private String screenName;

    private int lastMouseX = 0;
    private String screenText;
    public static final String zd;
    private boolean changed;

    public void drawScreen(int i, int j, float f) {
        int[] aint;
        {
            int[] aint1 = ShaderOption.lz();
            this.drawDefaultBackground();
            aint = aint1;
            if (this.screenText != null) {
                this.drawCenteredString(
                        this.fontRendererObj, this.screenText, this.width / 2, 15, 16777215);
                if (!(aint == null)) {

                    this.drawCenteredString(
                            this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
                }
            } else {

                this.drawCenteredString(
                        this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
            }
        }

        super.drawScreen(i, j, f);
        if (Math.abs(i - this.lastMouseX) <= 5 && Math.abs(j - this.lastMouseY) <= 5) {
            this.drawTooltips(i, j, this.buttonList);
            if (aint == null) {
                return;
            }
        }

        this.lastMouseX = i;
        this.lastMouseY = j;
        this.mouseStillTime = System.currentTimeMillis();
    }

    private String[] makeTooltipLines(ShaderOption shaderOption, int i) {
        if (shaderOption instanceof ShaderOptionProfile) {
            return null;
        }

        String text = shaderOption.getNameText();
        String textValue = OptiFineConfig.normalize(shaderOption.getDescriptionText()).trim();
        String[] astring = this.splitDescription(textValue);
        String textValue2 = null;
        if (!text.equals(shaderOption.getName())) {
            textValue2 = Lang.get("of.general.id") + ": " + shaderOption.getName();
        }

        String textValue3 = null;
        if (shaderOption.getPaths() != null) {
            textValue3 =
                    Lang.get("of.general.from")
                            + ": "
                            + OptiFineConfig.arrayToString(shaderOption.getPaths());
        }

        String textValue4 = null;
        if (shaderOption.getValueDefault() != null) {
            String textValue5 =
                    shaderOption.isEnabled()
                            ? shaderOption.getValueText(shaderOption.getValueDefault())
                            : Lang.get("of.general.ambiguous");
            textValue4 = Lang.getDefault() + ": " + textValue5;
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(text);
        arrayList.addAll(Arrays.asList(astring));
        if (textValue2 != null) {
            arrayList.add(textValue2);
        }

        if (textValue3 != null) {
            arrayList.add(textValue3);
        }

        if (textValue4 != null) {
            arrayList.add(textValue4);
        }

        return this.wrapTooltipLines(i, arrayList);
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        if (k == 1) {
            GuiButton guiButton = this.method_32c14d4e86922c79e789(i, j);
            if (guiButton instanceof GuiButtonShaderOption) {
                GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption) guiButton;
                ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();

                try {
                    if (false)
                        throw (java.io.IOException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (shaderOption.isChanged()) {
                        guiButtonShaderOption.playPressSound(this.mc.getSoundHandler());
                        shaderOption.resetValue();
                        this.changed = true;
                        this.method_8d9b536bcdc9991c48fb();
                    }
                } catch (IOException ioexception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
                }
            }
        }
    }

    private String[] splitDescription(String text) {
        int[] aint = ShaderOption.lz();
        if (text.length() <= 0) {
            return new String[0];
        }

        text = StrUtils.removePrefix(text, "//");
        String[] astring = text.split("\\. ");
        int i = 0;

        while (i < astring.length) {
            astring[i] = "- " + astring[i].trim();
            astring[i] = StrUtils.removeSuffix(astring[i], packedTextFragments[2]);
            i++;
            if (aint != null) {
                break;
            }
        }

        return astring;
    }

    private GuiButton method_32c14d4e86922c79e789(int i, int j) {
        int[] aint1 = ShaderOption.lz();
        int k = 0;
        int[] aint = aint1;

        while (k < this.buttonList.size()) {
            GuiButton guiButton = (GuiButton) this.buttonList.get(k);
            int l = GuiVideoSettings.getButtonWidth(guiButton);
            int i1 = GuiVideoSettings.getButtonHeight(guiButton);
            if (i >= guiButton.xPosition
                    && j >= guiButton.yPosition
                    && i < guiButton.xPosition + l
                    && j < guiButton.yPosition + i1) {
                return guiButton;
            }

            k++;
            if (aint != null) {
                break;
            }
        }

        return null;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    private void drawTooltips(int i, int j, List list) {
        int[] aint1 = ShaderOption.lz();
        short short1 = 700;
        int[] aint = aint1;
        if (System.currentTimeMillis() >= this.mouseStillTime + short1) {
            int k = this.width / 2 - 150;
            int l = this.height / 6 - 7;
            if (j <= l + 98) {
                l += 105;
            }

            int i1 = k + 150 + 150;
            int j1 = l + 84 + 10;
            GuiButton guiButton = this.method_32c14d4e86922c79e789(i, j);
            if (guiButton instanceof GuiButtonShaderOption) {
                GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption) guiButton;
                ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
                String[] astring = this.makeTooltipLines(shaderOption, i1 - k);
                if (astring == null) {
                    return;
                }

                this.drawGradientRect(k, l, i1, j1, -536870912, -536870912);
                int k1 = 0;

                while (k1 < astring.length) {
                    String text = astring[k1];
                    int l1 = 14540253;
                    if (text.endsWith(packedTextFragments[1])) {
                        l1 = 16719904;
                    }

                    this.fontRendererObj.drawStringWithShadow(text, k + 5, l + 5 + k1 * 11, l1);
                    k1++;
                    if (aint != null) {
                        break;
                    }
                }
            }
        }
    }

    private String getButtonText(ShaderOption shaderOption, int i) {
        int[] aint1 = ShaderOption.lz();
        String text = shaderOption.getNameText();
        int[] aint = aint1;
        if (shaderOption instanceof ShaderOptionScreen) {
            ShaderOptionScreen shaderOptionScreen = (ShaderOptionScreen) shaderOption;
            return text + "...";
        }

        FontRenderer anp = OptiFineConfig.getMinecraft().fontRendererObj;
        int j = anp.getStringWidth(": " + Lang.getOff()) + 5;

        while (anp.getStringWidth(text) + j >= i && text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            if (aint != null) {
                break;
            }
        }

        String textValue =
                shaderOption.isChanged()
                        ? shaderOption.getValueColor(shaderOption.getValue())
                        : packedTextFragments[0];
        String textValue2 = shaderOption.getValueText(shaderOption.getValue());
        return text + ": " + textValue + textValue2;
    }

    protected void actionPerformed(GuiButton guiButton) {
        int[] aint = ShaderOption.lz();
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiButtonShaderOption) {
                GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption) guiButton;
                ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
                if (shaderOption instanceof ShaderOptionScreen) {
                    String text = shaderOption.getName();
                    GuiShaderOptions guiShaderOptions =
                            new GuiShaderOptions(this, this.settings, text);
                    this.mc.displayGuiScreen(guiShaderOptions);
                    return;
                }

                shaderOption.nextValue();
                this.method_8d9b536bcdc9991c48fb();
                this.changed = true;
            }

            if (guiButton.id == 201) {
                ShaderOption[] shaderOptionArray =
                        Shaders.getChangedOptions(Shaders.getShaderPackOptions());
                int i = 0;

                while (i < shaderOptionArray.length) {
                    ShaderOption shaderOptionValue = shaderOptionArray[i];
                    shaderOptionValue.resetValue();
                    this.changed = true;
                    i++;
                    if (aint != null) {
                        break;
                    }
                }

                this.method_8d9b536bcdc9991c48fb();
            }

            if (guiButton.id == 200) {
                if (this.changed) {
                    Shaders.saveShaderPackOptions();
                    Shaders.uninit();
                }

                this.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }

    private void method_8d9b536bcdc9991c48fb() {
        int[] aint1 = ShaderOption.lz();
        Iterator iterator = this.buttonList.iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            GuiButton guiButton = (GuiButton) iterator.next();
            if (guiButton instanceof GuiButtonShaderOption) {
                GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption) guiButton;
                ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
                if (shaderOption instanceof ShaderOptionProfile) {
                    ShaderOptionProfile shaderOptionProfile = (ShaderOptionProfile) shaderOption;
                    shaderOptionProfile.updateProfile();
                }

                guiButtonShaderOption.displayString =
                        this.getButtonText(shaderOption, guiButtonShaderOption.getButtonWidth());
            }

            if (aint != null) {
                break;
            }
        }
    }

    static {
        shadersmod.client.GuiShaderOptions.packedTextFragments = new String[6];
        shadersmod.client.GuiShaderOptions.packedTextFragments[0] = new String("");
        shadersmod.client.GuiShaderOptions.packedTextFragments[1] = new String("!");
        shadersmod.client.GuiShaderOptions.packedTextFragments[2] = new String(".");
        shadersmod.client.GuiShaderOptions.packedTextFragments[3] = new String("*");
        zd = packedTextFragments[3];
        za = "<empty>";
        yn = "<profile>";
    }

    public GuiShaderOptions(GuiScreen guiScreen, GameSettings gameSettings) {
        this.lastMouseY = 0;

        this.mouseStillTime = 0L;
        this.screenName = null;
        this.screenText = null;
        this.changed = false;
        this.title = "Shader Options";
        this.prevScreen = guiScreen;
        this.settings = gameSettings;
    }

    public void initGui() {
        int[] aint1 = ShaderOption.lz();
        this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
        byte b0 = 100;

        byte b2 = 30;
        int[] aint = aint1;
        byte b3 = 20;
        int i = this.width - 130;
        byte b4 = 120;
        byte b5 = 20;
        int j = 2;
        ShaderOption[] shaderOptionArray = Shaders.getShaderPackOptions(this.screenName);
        if (shaderOptionArray != null) {
            if (shaderOptionArray.length > 18) {
                j = shaderOptionArray.length / 9 + 1;
            }

            int k = 0;

            while (k < shaderOptionArray.length) {
                ShaderOption shaderOption = shaderOptionArray[k];
                if (shaderOption != null && shaderOption.isVisible()) {
                    int l = k % j;
                    int i1 = k / j;
                    int j1 = Math.min(this.width / j, 200);
                    int k1 = (this.width - j1 * j) / 2;
                    int l1 = l * j1 + 5 + k1;
                    int i2 = b2 + i1 * b3;
                    int j2 = j1 - 10;
                    String text = this.getButtonText(shaderOption, j2);
                    GuiButtonShaderOption guiButtonShaderOption =
                            new GuiButtonShaderOption(b0 + k, l1, i2, j2, b5, shaderOption, text);
                    guiButtonShaderOption.enabled = shaderOption.isEnabled();
                    this.buttonList.add(guiButtonShaderOption);
                }

                k++;
                if (aint != null) {
                    break;
                }
            }
        }

        this.buttonList.add(
                new GuiButton(
                        201,
                        this.width / 2 - b4 - 20,
                        this.height / 6 + 168 + 11,
                        b4,
                        b5,
                        I18n.format("controls.reset", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        200,
                        this.width / 2 + 20,
                        this.height / 6 + 168 + 11,
                        b4,
                        b5,
                        I18n.format("gui.done", new Object[0])));
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public GuiShaderOptions(GuiScreen guiScreen, GameSettings gameSettings, String text) {
        this(guiScreen, gameSettings);
        this.screenName = text;
        if (text != null) {
            this.screenText = Shaders.translate("screen." + text, text);
        }
    }

    private String[] wrapTooltipLines(int i, List<String> textList) {
        FontRenderer anp = OptiFineConfig.getMinecraft().fontRendererObj;
        int[] aint1 = ShaderOption.lz();
        ArrayList arrayList = new ArrayList();
        int[] aint = aint1;
        int j = 0;

        while (j < textList.size()) {
            String text = (String) textList.get(j);
            if (text != null && text.length() > 0) {
                for (java.util.Iterator<?> __recoveryIterator171_44937 =
                                (anp.listFormattedStringToWidth(text, i)).iterator();
                        (__recoveryIterator171_44937).hasNext(); ) {
                    String textValue = (String) (__recoveryIterator171_44937).next();
                    arrayList.add(textValue);
                    if (aint != null) {
                        break;
                    }
                }
            }

            j++;
            if (aint != null) {
                break;
            }
        }

        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    // region String decoding support

    // endregion
}
