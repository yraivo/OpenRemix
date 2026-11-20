package shadersmod.client;

public class PropertyDefaultTrueFalse extends ShaderOptionProperty {

    public static String[] USER_VALUES;
    public static String[] PROPERTY_VALUES;

    public boolean isDefault() {
        return this.getValue() == 0;
    }

    // region String decoding support

    // endregion

    public PropertyDefaultTrueFalse(String text, String textValue, int i) {
        super(
                text,
                shadersmod.client.PropertyDefaultTrueFalse.PROPERTY_VALUES,
                textValue,
                shadersmod.client.PropertyDefaultTrueFalse.USER_VALUES,
                i);
    }

    static {
        shadersmod.client.PropertyDefaultTrueFalse.PROPERTY_VALUES = new String[] {"default", "true", "false"};
        shadersmod.client.PropertyDefaultTrueFalse.USER_VALUES = new String[] {"Default", "ON", "OFF"};
    }

    // region String decoding support

    // endregion

    public boolean isFalse() {
        return this.getValue() == 2;
    }

    public boolean isTrue() {
        return this.getValue() == 1;
    }

    // region String decoding support

    // endregion
}
