package shadersmod.client;

import optifine.OptiFineConfig;
import optifine.StrUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderOptionVariable extends ShaderOption {

    private static String[] Dz;
    private static Pattern PATTERN_VARIABLE;

    public String getValueColor(String text) {
        return "\u00a7a";
    }

    public ShaderOptionVariable(
            String text, String textValue, String textValue2, String[] astring, String textValue3) {
        super(text, textValue, textValue2, astring, textValue2, textValue3);
        this.setVisible(this.getValues().length > 1);
    }

    static {
        shadersmod.client.ShaderOptionVariable.Dz = new String[9];
        shadersmod.client.ShaderOptionVariable.Dz[0] = new String(" ");
        shadersmod.client.ShaderOptionVariable.Dz[1] = new String("[");
        shadersmod.client.ShaderOptionVariable.Dz[2] = new String("]");
        shadersmod.client.ShaderOptionVariable.Dz[3] = new String("");
        shadersmod.client.ShaderOptionVariable.Dz[4] = new String("[");
        shadersmod.client.ShaderOptionVariable.Dz[5] = new String("]");
        shadersmod.client.ShaderOptionVariable.Dz[6] = new String(" ");
        shadersmod.client.ShaderOptionVariable.Dz[7] =
                new String(
                        "T\u00b7\u008f#\u0080\u00ee\u0080\u0016\u00fe\u0002d\u00e9\b*\u00d5\u00c0\u009d\u00c8\u00d52H");
        shadersmod.client.ShaderOptionVariable.Dz[8] =
                new String(
                        "2Xn6\u00ea\u0006\u001cV\u00b7\u00c0\u0017\u00fe\u0093@_\u0089I\u00ec\u0003\u00bf?\u00d8\u00cb\u00cd\u00b6w^\u0012\f\u00c40\u00a4*<4L\u00faZ\u0090\u0086\u00e99\u00e4l{~\u009e\u00e3F\u00cf\u00a1\u0002\u00b4[\u00b9\u00e8\u00ed\u00d9[\u0012Q,\u00ef\u00af}r\u0094W.\u00be\u00ec\u009d\u0002&O\u00c5\r\u00da");
        shadersmod.client.ShaderOptionVariable.PATTERN_VARIABLE =
                Pattern.compile("^\\s*#define\\s+([A-Za-z0-9_]+)\\s+(-?[0-9\\.]*)F?f?\\s*(//.*)?$");
    }

    public static String[] parseValues(String text, String textValue) {
        String[] astring = new String[] {text};
        if (textValue == null) {
            return astring;
        }

        textValue = textValue.trim();
        textValue = StrUtils.removePrefix(textValue, Dz[4]);
        textValue = StrUtils.removeSuffix(textValue, Dz[5]);
        textValue = textValue.trim();
        if (textValue.length() <= 0) {
            return astring;
        }

        String[] astring1 = OptiFineConfig.tokenize(textValue, Dz[6]);
        if (astring1.length <= 0) {
            return astring;
        }

        if (!Arrays.asList(astring1).contains(text)) {
            astring1 = (String[]) OptiFineConfig.addObjectToArray(astring1, text, 0);
        }

        return astring1;
    }

    public static ShaderOption parseOption(String text, String textValue) {
        Matcher matcher = shadersmod.client.ShaderOptionVariable.PATTERN_VARIABLE.matcher(text);
        if (!matcher.matches()) {
            return null;
        }

        String textValue2 = matcher.group(1);
        String textValue3 = matcher.group(2);
        String textValue4 = matcher.group(3);
        String textValue5 = StrUtils.getSegment(textValue4, Dz[1], Dz[2]);
        if (textValue5 != null && textValue5.length() > 0) {
            textValue4 = textValue4.replace(textValue5, Dz[3]).trim();
        }

        String[] astring = parseValues(textValue3, textValue5);
        if (textValue2 != null && textValue2.length() > 0) {
            textValue = StrUtils.removePrefix(textValue, "/shaders/");
            return new ShaderOptionVariable(textValue2, textValue4, textValue3, astring, textValue);
        } else {
            return null;
        }
    }

    public String getSourceLine() {
        return "#define "
                + this.getName()
                + Dz[0]
                + this.getValue()
                + " // Shader option "
                + this.getValue();
    }

    public boolean matchesLine(String text) {
        Matcher matcher = shadersmod.client.ShaderOptionVariable.PATTERN_VARIABLE.matcher(text);
        if (!matcher.matches()) {
            return false;
        }

        String textValue = matcher.group(1);
        return textValue.matches(this.getName());
    }
}
