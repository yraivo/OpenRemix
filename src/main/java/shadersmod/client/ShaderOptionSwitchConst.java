package shadersmod.client;

import optifine.StrUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderOptionSwitchConst extends ShaderOptionSwitch {

    private static Pattern PATTERN_CONST;

    public boolean checkUsed() {
        return false;
    }

    public static ShaderOption parseOption(String text, String textValue) {
        Matcher matcher = shadersmod.client.ShaderOptionSwitchConst.PATTERN_CONST.matcher(text);
        if (!matcher.matches()) {
            return null;
        } else {
            String textValue2 = matcher.group(1);
            String textValue3 = matcher.group(2);
            String textValue4 = matcher.group(3);
            if (textValue2 != null && textValue2.length() > 0) {
                textValue = StrUtils.removePrefix(textValue, "/shaders/");
                ShaderOptionSwitchConst shaderOptionSwitchConst =
                        new ShaderOptionSwitchConst(textValue2, textValue4, textValue3, textValue);
                shaderOptionSwitchConst.setVisible(false);
                return shaderOptionSwitchConst;
            } else {
                return null;
            }
        }
    }

    public String getSourceLine() {
        return "const bool "
                + this.getName()
                + " = "
                + this.getValue()
                + "; // Shader option "
                + this.getValue();
    }

    public ShaderOptionSwitchConst(
            String text, String textValue, String textValue2, String textValue3) {
        super(text, textValue, textValue2, textValue3);
    }

    static {
        shadersmod.client.ShaderOptionSwitchConst.PATTERN_CONST =
                Pattern.compile(
                        "^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
    }

    public boolean matchesLine(String text) {
        Matcher matcher = shadersmod.client.ShaderOptionSwitchConst.PATTERN_CONST.matcher(text);
        if (!matcher.matches()) {
            return false;
        }

        String textValue = matcher.group(1);
        return textValue.matches(this.getName());
    }
}
