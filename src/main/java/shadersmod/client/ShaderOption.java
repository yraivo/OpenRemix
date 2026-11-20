package shadersmod.client;

import optifine.OptiFineConfig;
import optifine.StrUtils;

import java.util.Arrays;
import java.util.List;

public abstract class ShaderOption {
    public static String sT;
    private boolean enabled;
    private boolean visible;
    public static String te;
    private String name = null;

    private String[] values;
    private static int[] wx;
    public static String vI;
    private String valueDefault;
    private String value;
    private String description = null;

    private String[] paths;
    private static String[] packedTextFragments;

    public String getValueText(String text) {
        return text;
    }

    static {
        shadersmod.client.ShaderOption.packedTextFragments = new String[4];
        shadersmod.client.ShaderOption.packedTextFragments[0] = new String("");
        shadersmod.client.ShaderOption.packedTextFragments[1] = new String("");
        b((int[]) null);
        vI = "\u00a79";
        sT = "\u00a7c";
        te = "\u00a7a";
    }

    public void resetValue() {
        this.value = this.valueDefault;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getValue() {
        return this.value;
    }

    public String[] getValues() {
        return (String[]) this.values.clone();
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isValidValue(String text) {
        return getIndex(text, this.values) >= 0;
    }

    public ShaderOption(
            String text,
            String textValue,
            String textValue2,
            String[] astring,
            String textValue3,
            String textValue4) {
        this.value = null;
        this.values = null;
        this.valueDefault = null;
        this.paths = null;
        this.enabled = true;
        this.visible = true;
        this.name = text;
        this.description = textValue;
        this.value = textValue2;
        this.values = astring;
        this.valueDefault = textValue3;
        if (textValue4 != null) {
            this.paths = new String[] {textValue4};
        }
    }

    public boolean setValue(String text) {
        int index = getIndex(text, this.values);
        if (index < 0) {
            return false;
        }

        this.value = text;
        return true;
    }

    public static int[] lz() {
        return wx;
    }

    public boolean checkUsed() {
        return false;
    }

    public String getDescriptionText() {
        String text = OptiFineConfig.normalize(this.description);
        text = StrUtils.removePrefix(text, "//");
        return Shaders.translate("option." + this.getName() + ".comment", text);
    }

    public boolean matchesLine(String text) {
        return false;
    }

    // region String decoding support

    // endregion

    public void setEnabled(boolean enabledValue) {
        this.enabled = enabledValue;
    }

    public void nextValue() {
        int index = getIndex(this.value, this.values);
        if (index >= 0) {
            index = (index + 1) % this.values.length;
            this.value = this.values[index];
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getNameText() {
        return Shaders.translate("option." + this.name, this.name);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setDescription(String text) {
        this.description = text;
    }

    public void addPaths(String[] astring) {
        int[] aint1 = lz();
        List list = Arrays.asList(this.paths);
        int[] aint = aint1;
        int i = 0;

        while (i < astring.length) {
            String text = astring[i];
            if (!list.contains(text)) {
                this.paths = (String[]) OptiFineConfig.addObjectToArray(this.paths, text);
            }

            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public boolean isChanged() {
        return !OptiFineConfig.equals(this.value, this.valueDefault);
    }

    public String[] getPaths() {
        return this.paths;
    }

    public String getSourceLine() {
        return null;
    }

    public String getValueDefault() {
        return this.valueDefault;
    }

    public void setVisible(boolean visibleValue) {
        this.visible = visibleValue;
    }

    // region String decoding support

    // endregion

    private static int getIndex(String text, String[] astring) {
        int[] aint1 = lz();
        int i = 0;
        int[] aint = aint1;

        while (i < astring.length) {
            String textValue = astring[i];
            if (textValue.equals(text)) {
                return i;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return -1;
    }

    public String getValueColor(String text) {
        return packedTextFragments[0];
    }

    @Override
    public String toString() {
        return packedTextFragments[1]
                + this.name
                + ", value: "
                + this.value
                + ", valueDefault: "
                + this.valueDefault
                + ", paths: "
                + OptiFineConfig.arrayToString(this.paths);
    }

    public String getName() {
        return this.name;
    }

    public static void b(int[] aint) {
        wx = aint;
    }

    public boolean isUsedInLine(String text) {
        return false;
    }
}
