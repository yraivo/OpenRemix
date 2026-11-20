package shadersmod.client;

import optifine.OptiFineConfig;

import java.util.Properties;

public class ShaderOptionProperty {
    private String[] userValues;
    private String[] propertyValues;

    private String userName;
    private int value;
    private int defaultValue;
    private static String[] packedTextFragments;
    private int[] values = null;
    private String propertyName;

    static {
        shadersmod.client.ShaderOptionProperty.packedTextFragments = new String[4];
        shadersmod.client.ShaderOptionProperty.packedTextFragments[0] = new String("");
        shadersmod.client.ShaderOptionProperty.packedTextFragments[1] = new String("=");
    }

    public String getPropertyValue() {
        return this.propertyValues[this.value];
    }

    public void resetValue() {
        this.value = this.defaultValue;
    }

    public String getUserName() {
        return this.userName;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public String getUserValue() {
        return this.userValues[this.value];
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public ShaderOptionProperty(
            String text, String[] astring, String textValue, String[] astring1, int valueValue) {
        this.defaultValue = 0;
        this.propertyName = null;
        this.propertyValues = null;
        this.userName = null;
        this.userValues = null;
        this.value = 0;
        this.propertyName = text;
        this.propertyValues = astring;
        this.userName = textValue;
        this.userValues = astring1;
        this.defaultValue = valueValue;
        if (astring.length != astring1.length) {
            throw new IllegalArgumentException(
                    "Property and user values have different lengths: "
                            + astring.length
                            + " != "
                            + astring1.length);
        }

        if (valueValue >= 0 && valueValue < astring.length) {
            this.value = valueValue;
        } else {
            throw new IllegalArgumentException("Invalid default value: " + valueValue);
        }
    }

    public boolean setPropertyValue(String text) {
        if (text == null) {
            this.value = this.defaultValue;
            return false;
        }

        this.value = org.apache.commons.lang3.ArrayUtils.indexOf(this.propertyValues, text);
        if (this.value >= 0 && this.value < this.propertyValues.length) {
            return true;
        }

        this.value = this.defaultValue;
        return false;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + this.propertyName
                + packedTextFragments[1]
                + this.getPropertyValue()
                + " ["
                + OptiFineConfig.arrayToString(this.propertyValues)
                + "], value: "
                + this.value;
    }

    public boolean loadFrom(Properties properties) {
        try {
            this.resetValue();
            if (properties == null) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw shadersmod.client.ShaderOptionProperty.method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        java.lang.String property = properties.getProperty(this.propertyName);

        try {
            if (property == null) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw shadersmod.client.ShaderOptionProperty.method_9a70918140b0a7642dba(illegalargumentexception);
        }

        return this.setPropertyValue(property);
    }

    public void saveTo(Properties properties) {
        try {
            if (properties != null) {
                properties.setProperty(this.getPropertyName(), this.getPropertyValue());
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw shadersmod.client.ShaderOptionProperty.method_9a70918140b0a7642dba(illegalargumentexception);
        }
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int valueValue) {
        this.value = valueValue;
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = this.defaultValue;
        }
    }

    public void method_8d9b536bcdc9991c48fb() {
        this.value += 1;
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = 0;
        }
    }

    // region String decoding support

    // endregion
}
