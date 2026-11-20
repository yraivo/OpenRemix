package shadersmod.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

import optifine.Lang;
import optifine.OptiFineConfig;

import java.io.File;
import java.io.IOException;

public class GuiShaders extends GuiScreen {
    private static float[] HAND_DEPTH_VALUES;

    public static final int EnumOS_UNKNOWN = 0;
    public static final int EnumOS_LINUX = 4;
    private static float[] QUALITY_MULTIPLIERS;
    private static String[] packedTextFragments;
    public static final int EnumOS_OSX = 2;
    protected String screenTitle = "Shaders";
    private static String[] QUALITY_MULTIPLIER_NAMES;
    private static String[] HAND_DEPTH_NAMES;
    protected GuiScreen parentGui;
    public static final int EnumOS_SOLARIS = 3;
    public static final int EnumOS_WINDOWS = 1;
    private int updateTimer = -1;

    private ShaderPackSelectionList shaderList;

    public void drawCenteredString(String text, int i, int j, int k) {
        this.drawCenteredString(this.fontRendererObj, text, i, j, k);
    }

    public void updateScreen() {
        super.updateScreen();
        this.updateTimer--;
    }

    public void updateButtons() {
        int[] aint1 = ShaderOption.lz();
        boolean enabled = OptiFineConfig.isShaders();
        int[] aint = aint1;

        for (GuiButton guiButton : this.buttonList) {
            if (guiButton.id != 201
                    && guiButton.id != 202
                    && guiButton.id != EnumShaderOption.dl.ordinal()) {
                guiButton.enabled = enabled;
            }

            if (aint != null) {
                break;
            }
        }
    }

    public static String toStringValue(float f, float[] afloat, String[] astring) {
        int valueIndex = getValueIndex(f, afloat);
        return astring[valueIndex];
    }

    public static String toStringAa(int i) {
        return i == 2 ? "FXAA 2x" : (i == 4 ? "FXAA 4x" : Lang.getOff());
    }

    public void initGui() {
        this.screenTitle = I18n.format("of.options.shadersTitle", new Object[0]);
        if (Shaders.shadersConfig == null) {
            Shaders.loadConfig();
        }

        byte b0 = 120;
        byte b1 = 20;
        int i = this.width - b0 - 10;
        byte b2 = 30;
        byte b3 = 20;
        int j = this.width - b0 - 20;
        this.shaderList =
                new ShaderPackSelectionList(this, j, this.height, b2, this.height - 50, 16);
        this.shaderList.registerScrollButtons(7, 8);
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.dl, i, 0 * b3 + b2, b0, b1));
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.ds, i, 1 * b3 + b2, b0, b1));
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.dd, i, 2 * b3 + b2, b0, b1));
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.dq, i, 3 * b3 + b2, b0, b1));
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.di, i, 4 * b3 + b2, b0, b1));
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.dn, i, 5 * b3 + b2, b0, b1));
        this.buttonList.add(
                new GuiButtonEnumShaderOption(EnumShaderOption.dp, i, 6 * b3 + b2, b0, b1));
        int k = Math.min(150, j / 2 - 10);
        this.buttonList.add(
                new GuiButton(
                        201,
                        j / 4 - k / 2,
                        this.height - 25,
                        k,
                        b1,
                        Lang.get("of.options.shaders.shadersFolder")));
        this.buttonList.add(
                new GuiButton(
                        202,
                        j / 4 * 3 - k / 2,
                        this.height - 25,
                        k,
                        b1,
                        I18n.format("gui.done", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        203,
                        i,
                        this.height - 25,
                        b0,
                        b1,
                        Lang.get("of.options.shaders.shaderOptions")));
        this.updateButtons();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static String toStringQuality(float f) {
        return toStringValue(
                f,
                shadersmod.client.GuiShaders.QUALITY_MULTIPLIERS,
                shadersmod.client.GuiShaders.QUALITY_MULTIPLIER_NAMES);
    }

    public void drawScreen(int i, int j, float f) {
        int[] aint1 = ShaderOption.lz();
        this.drawDefaultBackground();
        int[] aint = aint1;
        this.shaderList.drawScreen(i, j, f);
        if (this.updateTimer <= 0) {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }

        {
            this.drawCenteredString(
                    this.fontRendererObj,
                    this.screenTitle + packedTextFragments[0],
                    this.width / 2,
                    15,
                    16777215);
            String text =
                    "OpenGL: "
                            + Shaders.glVersionString
                            + ", "
                            + Shaders.glVendorString
                            + ", "
                            + Shaders.glRendererString;
            int k = this.fontRendererObj.getStringWidth(text);
            if (k < this.width - 5) {
                this.drawCenteredString(
                        this.fontRendererObj, text, this.width / 2, this.height - 40, 8421504);
                if (!(aint == null)) {

                    this.drawString(this.fontRendererObj, text, 5, this.height - 40, 8421504);
                }
            } else {

                this.drawString(this.fontRendererObj, text, 5, this.height - 40, 8421504);
            }
        }

        super.drawScreen(i, j, f);
    }

    public GuiShaders(GuiScreen guiScreen, GameSettings gameSettings) {
        this.parentGui = guiScreen;
    }

    public static int getOSType() {
        String text = System.getProperty("os.name").toLowerCase();
        return text.contains("win")
                ? 1
                : (text.contains("mac")
                        ? 2
                        : (text.contains("solaris")
                                ? 3
                                : (text.contains("sunos")
                                        ? 3
                                        : (text.contains("linux")
                                                ? 4
                                                : (text.contains("unix") ? 4 : 0)))));
    }

    public static String toStringOnOff(boolean flag) {
        String text = Lang.getOn();
        String textValue = Lang.getOff();
        return flag ? text : textValue;
    }

    public MinecraftClient getMc() {
        return this.mc;
    }

    public static int getValueIndex(float f, float[] afloat) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < afloat.length) {
            float f1 = afloat[i];
            if (f1 >= f) {
                return i;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return afloat.length - 1;
    }

    protected void actionPerformed(GuiButton guiButton) {
        int[] aint = ShaderOption.lz();
        if (guiButton.enabled) {
            if (guiButton instanceof GuiButtonEnumShaderOption) {
                GuiButtonEnumShaderOption guiButtonEnumShaderOption;
                label214:
                {
                    label206:
                    {
                        label205:
                        {
                            label204:
                            {
                                label215:
                                {
                                    label202:
                                    {
                                        label216:
                                        {
                                            label217:
                                            {
                                                guiButtonEnumShaderOption =
                                                        (GuiButtonEnumShaderOption) guiButton;
                                                switch (guiButtonEnumShaderOption
                                                        .getEnumShaderOption()) {
                                                    case dl:
                                                        Shaders.nextAntialiasingLevel();
                                                        Shaders.uninit();
                                                        if (aint == null) {
                                                            break label214;
                                                        }
                                                    case ds:
                                                        Shaders.configNormalMap =
                                                                remix.compat.compiler
                                                                        .JvmBooleanSlots.fromInt(
                                                                        (!Shaders.configNormalMap
                                                                                ? 1
                                                                                : 0));
                                                        this.mc.scheduleResourcesRefresh();
                                                        if (aint == null) {
                                                            break label214;
                                                        }
                                                    case dd:
                                                        Shaders.configSpecularMap =
                                                                remix.compat.compiler
                                                                        .JvmBooleanSlots.fromInt(
                                                                        (!Shaders.configSpecularMap
                                                                                ? 1
                                                                                : 0));
                                                        this.mc.scheduleResourcesRefresh();
                                                        if (aint == null) {
                                                            break label214;
                                                        }
                                                    case dq:
                                                        int i;
                                                        float[] afloat;
                                                        label188:
                                                        {
                                                            float configRenderResMul =
                                                                    Shaders.configRenderResMul;
                                                            afloat =
                                                                    shadersmod.client.GuiShaders
                                                                            .QUALITY_MULTIPLIERS;
                                                            String[] astring =
                                                                    shadersmod.client.GuiShaders
                                                                            .QUALITY_MULTIPLIER_NAMES;
                                                            i =
                                                                    getValueIndex(
                                                                            configRenderResMul,
                                                                            afloat);
                                                            if (isShiftKeyDown()) {
                                                                if (--i >= 0) {
                                                                    break label188;
                                                                }

                                                                i = afloat.length - 1;
                                                                if (aint == null) {
                                                                    break label188;
                                                                }
                                                            }

                                                            if (++i >= afloat.length) {
                                                                i = 0;
                                                            }
                                                        }

                                                        Shaders.configRenderResMul = afloat[i];
                                                        Shaders.scheduleResize();
                                                        if (aint == null) {
                                                            break label214;
                                                        }
                                                    case di:
                                                        int j;
                                                        float[] afloat1;
                                                        label180:
                                                        {
                                                            float configShadowResMul =
                                                                    Shaders.configShadowResMul;
                                                            afloat1 =
                                                                    shadersmod.client.GuiShaders
                                                                            .QUALITY_MULTIPLIERS;
                                                            String[] astring1 =
                                                                    shadersmod.client.GuiShaders
                                                                            .QUALITY_MULTIPLIER_NAMES;
                                                            j =
                                                                    getValueIndex(
                                                                            configShadowResMul,
                                                                            afloat1);
                                                            if (isShiftKeyDown()) {
                                                                if (--j >= 0) {
                                                                    break label180;
                                                                }

                                                                j = afloat1.length - 1;
                                                                if (aint == null) {
                                                                    break label180;
                                                                }
                                                            }

                                                            if (++j >= afloat1.length) {
                                                                j = 0;
                                                            }
                                                        }

                                                        Shaders.configShadowResMul = afloat1[j];
                                                        Shaders.scheduleResizeShadow();
                                                        if (aint == null) {
                                                            break label214;
                                                        }
                                                    case dn:
                                                        break;
                                                    case db:
                                                        break label217;
                                                    case dp:
                                                        break label216;
                                                    case dm:
                                                        break label202;
                                                    case df:
                                                        break label215;
                                                    case dh:
                                                        break label204;
                                                    case dc:
                                                        break label205;
                                                    case du:
                                                        break label206;
                                                    default:
                                                        break label214;
                                                }

                                                int k;
                                                float[] afloat2;
                                                label172:
                                                {
                                                    float f2 = Shaders.configHandDepthMul;
                                                    afloat2 =
                                                            shadersmod.client.GuiShaders
                                                                    .HAND_DEPTH_VALUES;
                                                    String[] astring2 =
                                                            shadersmod.client.GuiShaders
                                                                    .HAND_DEPTH_NAMES;
                                                    k = getValueIndex(f2, afloat2);
                                                    if (isShiftKeyDown()) {
                                                        if (--k >= 0) {
                                                            break label172;
                                                        }

                                                        k = afloat2.length - 1;
                                                        if (aint == null) {
                                                            break label172;
                                                        }
                                                    }

                                                    if (++k >= afloat2.length) {
                                                        k = 0;
                                                    }
                                                }

                                                Shaders.configHandDepthMul = afloat2[k];
                                                if (aint == null) {
                                                    break label214;
                                                }
                                            }

                                            Shaders.configCloudShadow =
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            (!Shaders.configCloudShadow ? 1 : 0));
                                            if (aint == null) {
                                                break label214;
                                            }
                                        }

                                        Shaders.configOldHandLight.method_8d9b536bcdc9991c48fb();
                                        Shaders.updateBlockLightLevel();
                                        this.mc.scheduleResourcesRefresh();
                                        if (aint == null) {
                                            break label214;
                                        }
                                    }

                                    Shaders.configTweakBlockDamage =
                                            remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                    (!Shaders.configTweakBlockDamage ? 1 : 0));
                                    if (aint == null) {
                                        break label214;
                                    }
                                }

                                Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                                Shaders.configTexMinFilN =
                                        Shaders.configTexMinFilS = Shaders.configTexMinFilB;
                                guiButton.displayString =
                                        "Tex Min: "
                                                + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
                                ShadersTex.updateTextureMinMagFilter();
                                if (aint == null) {
                                    break label214;
                                }
                            }

                            Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                            guiButton.displayString =
                                    "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
                            ShadersTex.updateTextureMinMagFilter();
                            if (aint == null) {
                                break label214;
                            }
                        }

                        Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                        guiButton.displayString =
                                "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
                        ShadersTex.updateTextureMinMagFilter();
                        if (aint == null) {
                            break label214;
                        }
                    }

                    Shaders.configShadowClipFrustrum =
                            remix.compat.compiler.JvmBooleanSlots.fromInt(
                                    (!Shaders.configShadowClipFrustrum ? 1 : 0));
                    guiButton.displayString =
                            "ShadowClipFrustrum: "
                                    + toStringOnOff(Shaders.configShadowClipFrustrum);
                    ShadersTex.updateTextureMinMagFilter();
                }

                guiButtonEnumShaderOption.updateButtonText();
                if (aint == null) {
                    return;
                }
            }

            label142:
            {
                label228:
                {
                    switch (guiButton.id) {
                        case 201:
                            try {
                                remix.util.io.FileActions.openDirectory(Shaders.shaderpacksdir);
                            } catch (java.io.IOException failure) {
                                failure.printStackTrace();
                            }
                            return;
                        case 202:
                            break;
                        case 203:
                            break label228;
                        default:
                            break label142;
                    }

                    new File(Shaders.shadersdir, "current.cfg");

                    try {
                        Shaders.storeConfig();
                    } catch (Exception exception) {
                    }

                    this.mc.displayGuiScreen(this.parentGui);
                    if (aint == null) {
                        return;
                    }
                }

                GuiShaderOptions guiShaderOptions =
                        new GuiShaderOptions(this, OptiFineConfig.getGameSettings());
                OptiFineConfig.getMinecraft().displayGuiScreen(guiShaderOptions);
                if (aint == null) {
                    return;
                }
            }

            this.shaderList.actionPerformed(guiButton);
        }
    }

    public static String toStringHandDepth(float f) {
        return toStringValue(
                f,
                shadersmod.client.GuiShaders.HAND_DEPTH_VALUES,
                shadersmod.client.GuiShaders.HAND_DEPTH_NAMES);
    }

    static {
        shadersmod.client.GuiShaders.packedTextFragments = new String[3];
        shadersmod.client.GuiShaders.packedTextFragments[0] = new String(" ");
        shadersmod.client.GuiShaders.QUALITY_MULTIPLIERS =
                new float[] {0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F};
        shadersmod.client.GuiShaders.QUALITY_MULTIPLIER_NAMES =
                new String[] {"0.5x", "0.7x", "1x", "1.5x", "2x"};
        shadersmod.client.GuiShaders.HAND_DEPTH_VALUES = new float[] {0.0625F, 0.125F, 0.25F};
        shadersmod.client.GuiShaders.HAND_DEPTH_NAMES = new String[] {"0.5x", "1x", "2x"};
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.shaderList.handleMouseInput();
    }
}
