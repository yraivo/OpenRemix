package net.minecraft.client.resources;

import java.util.Map;

public class I18n {
    private static Locale i18nLocale;
    private static String[] packedTextFragments;
    private static String legacyResourceFlowMarker;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.resources.I18n.packedTextFragments[0] = new String("EtbxX");
    }

    // endregion

    public static void setLegacyResourceFlowMarker(String text) {
        net.minecraft.client.resources.I18n.legacyResourceFlowMarker = text;
    }

    public static Map getLocaleProperties() {
        return net.minecraft.client.resources.I18n.i18nLocale.properties;
    }

    static void setLocale(Locale aGaValue) {
        net.minecraft.client.resources.I18n.i18nLocale = aGaValue;
    }

    public static String format(String text, Object... aobject) {
        return net.minecraft.client.resources.I18n.i18nLocale.formatMessage(text, aobject);
    }

    public static String getLegacyResourceFlowMarker() {
        return net.minecraft.client.resources.I18n.legacyResourceFlowMarker;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (getLegacyResourceFlowMarker() == null) {
            setLegacyResourceFlowMarker(packedTextFragments[0]);
        }
    }

    // region String decoding support

    // endregion
}
