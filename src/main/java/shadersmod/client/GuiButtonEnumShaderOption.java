package shadersmod.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiButtonEnumShaderOption extends GuiButton {

    private static String oF;
    private EnumShaderOption enumShaderOption = null;

    static {
        oF = ": ";
    }

    private static String getButtonText(EnumShaderOption enumShaderOption) {
        String text = I18n.format(enumShaderOption.getResourceKey(), new Object[0]) + oF;
        switch (enumShaderOption) {
            case dl:
                return text + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
            case ds:
                return text + GuiShaders.toStringOnOff(Shaders.configNormalMap);
            case dd:
                return text + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
            case dq:
                return text + GuiShaders.toStringQuality(Shaders.configRenderResMul);
            case di:
                return text + GuiShaders.toStringQuality(Shaders.configShadowResMul);
            case dn:
                return text + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
            case db:
                return text + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
            case dp:
                return text + Shaders.configOldHandLight.getUserValue();
            case du:
                return text + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
            case dm:
                return text + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
            default:
                return text + Shaders.getEnumShaderOption(enumShaderOption);
        }
    }

    // region String decoding support

    // endregion

    public GuiButtonEnumShaderOption(
            EnumShaderOption enumShaderOption, int i, int j, int k, int l) {
        super(enumShaderOption.ordinal(), i, j, k, l, getButtonText(enumShaderOption));
        this.enumShaderOption = enumShaderOption;
    }

    public EnumShaderOption getEnumShaderOption() {
        return this.enumShaderOption;
    }

    // region String decoding support

    // endregion

    public void updateButtonText() {
        this.displayString = getButtonText(this.enumShaderOption);
    }
}
