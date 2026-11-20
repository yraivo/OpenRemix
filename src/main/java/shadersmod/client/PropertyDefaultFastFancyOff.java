package shadersmod.client;

import optifine.OptiFineConfig;

public class PropertyDefaultFastFancyOff extends ShaderOptionProperty {

    public static String[] USER_VALUES;
    public static String[] PROPERTY_VALUES;

    public boolean isFancy() {
        return this.getValue() == 2;
    }

    public boolean isDefault() {
        return this.getValue() == 0;
    }

    public boolean isFast() {
        return this.getValue() == 1;
    }

    static {
        shadersmod.client.PropertyDefaultFastFancyOff.PROPERTY_VALUES =
                new String[] {"default", "fast", "fancy", "off"};
        shadersmod.client.PropertyDefaultFastFancyOff.USER_VALUES =
                new String[] {"Default", "Fast", "Fancy", "OFF"};
    }

    public PropertyDefaultFastFancyOff(String text, String textValue, int i) {
        super(
                text,
                shadersmod.client.PropertyDefaultFastFancyOff.PROPERTY_VALUES,
                textValue,
                shadersmod.client.PropertyDefaultFastFancyOff.USER_VALUES,
                i);
    }

    public boolean setPropertyValue(String text) {
        if (OptiFineConfig.equals(text, "none")) {
            text = "off";
        }

        return super.setPropertyValue(text);
    }

    public boolean jvm171_d_c8dbaa2b061a2d() {
        return this.getValue() == 3;
    }
}
