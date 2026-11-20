package shadersmod.client;

import optifine.StrUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderOptionVariableConst extends ShaderOptionVariable {
    private String type = null;
    private static String[] packedTextFragments;
    private static Pattern PATTERN_CONST;

    public ShaderOptionVariableConst(
            String text,
            String textValue,
            String textValue2,
            String textValue3,
            String[] astring,
            String textValue4) {
        super(text, textValue2, textValue3, astring, textValue4);
        this.type = textValue;
    }

    public boolean matchesLine(String text) {
        Matcher matcher = shadersmod.client.ShaderOptionVariableConst.PATTERN_CONST.matcher(text);
        if (!matcher.matches()) {
            return false;
        }

        String textValue = matcher.group(2);
        return textValue.matches(this.getName());
    }

    static {
        shadersmod.client.ShaderOptionVariableConst.packedTextFragments = new String[6];
        shadersmod.client.ShaderOptionVariableConst.packedTextFragments[0] = new String(" ");
        shadersmod.client.ShaderOptionVariableConst.packedTextFragments[1] = new String("[");
        shadersmod.client.ShaderOptionVariableConst.packedTextFragments[2] = new String("]");
        shadersmod.client.ShaderOptionVariableConst.packedTextFragments[3] = new String("");
        shadersmod.client.ShaderOptionVariableConst.PATTERN_CONST =
                Pattern.compile(
                        "^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
    }

    public String getSourceLine() {
        return "const "
                + this.type
                + packedTextFragments[0]
                + this.getName()
                + " = "
                + this.getValue()
                + "; // Shader option "
                + this.getValue();
    }

    public static ShaderOption kC(String text, String textValue) {
        Matcher matcher = shadersmod.client.ShaderOptionVariableConst.PATTERN_CONST.matcher(text);
        if (!matcher.matches()) {
            return null;
        }

        String textValue2 = matcher.group(1);
        String textValue3 = matcher.group(2);
        String textValue4 = matcher.group(3);
        String textValue5 = matcher.group(4);
        String textValue6 =
                StrUtils.getSegment(textValue5, packedTextFragments[1], packedTextFragments[2]);
        if (textValue6 != null && textValue6.length() > 0) {
            textValue5 = textValue5.replace(textValue6, packedTextFragments[3]).trim();
        }

        String[] astring = parseValues(textValue4, textValue6);
        if (textValue3 != null && textValue3.length() > 0) {
            textValue = StrUtils.removePrefix(textValue, "/shaders/");
            return new ShaderOptionVariableConst(
                    textValue3, textValue2, textValue5, textValue4, astring, textValue);
        } else {
            return null;
        }
    }
}
