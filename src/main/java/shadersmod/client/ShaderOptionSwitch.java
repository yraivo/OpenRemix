package shadersmod.client;

import optifine.Lang;
import optifine.OptiFineConfig;
import optifine.StrUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderOptionSwitch extends ShaderOption {

    private static Pattern PATTERN_DEFINE;

    private static Pattern PATTERN_IFDEF;

    public static boolean isTrue(String text) {
        return Boolean.valueOf(text);
    }

    public String getValueText(String text) {
        return isTrue(text) ? Lang.getOn() : Lang.getOff();
    }

    public boolean checkUsed() {
        return true;
    }

    public String getSourceLine() {
        return isTrue(this.getValue())
                ? "#define " + this.getName() + " // Shader option ON"
                : "//#define " + this.getName() + " // Shader option OFF";
    }

    static {
        shadersmod.client.ShaderOptionSwitch.PATTERN_DEFINE =
                Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
        shadersmod.client.ShaderOptionSwitch.PATTERN_IFDEF =
                Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
    }

    public String getValueColor(String text) {
        return isTrue(text) ? "\u00a7a" : "\u00a7c";
    }

    public static ShaderOption parseOption_ShaderOption(String text, String textValue) {
        Matcher matcher = shadersmod.client.ShaderOptionSwitch.PATTERN_DEFINE.matcher(text);
        if (!matcher.matches()) {
            return null;
        } else {
            String textValue2 = matcher.group(1);
            String textValue3 = matcher.group(2);
            String textValue4 = matcher.group(3);
            if (textValue3 != null && textValue3.length() > 0) {
                boolean flag = OptiFineConfig.equals(textValue2, "//");
                int i = !flag ? 1 : 0;
                textValue = StrUtils.removePrefix(textValue, "/shaders/");
                return new ShaderOptionSwitch(
                        textValue3,
                        textValue4,
                        String.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(i)),
                        textValue);
            } else {
                return null;
            }
        }
    }

    public boolean matchesLine(String text) {
        Matcher matcher = shadersmod.client.ShaderOptionSwitch.PATTERN_DEFINE.matcher(text);
        if (!matcher.matches()) {
            return false;
        }

        String textValue = matcher.group(2);
        return textValue.matches(this.getName());
    }

    public boolean isUsedInLine(String text) {
        Matcher matcher = shadersmod.client.ShaderOptionSwitch.PATTERN_IFDEF.matcher(text);
        if (matcher.matches()) {
            String textValue = matcher.group(2);
            if (textValue.equals(this.getName())) {
                return true;
            }
        }

        return false;
    }

    public ShaderOptionSwitch(String text, String textValue, String textValue2, String textValue3) {
        super(text, textValue, textValue2, new String[] {"true", "false"}, textValue2, textValue3);
    }
}
