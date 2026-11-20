package net.minecraft.client.resources.model;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation {
    private static int ef;

    private String variant;

    public ModelResourceLocation(String text) {
        this(0, parsePathString(text));
    }

    public static void b(int i) {
        ef = i;
    }

    public static int cR() {
        int i = aW();
        return i == 0 ? 67 : 0;
    }

    public ModelResourceLocation(ResourceLocation resourceLocation, String text) {
        this(resourceLocation.toString(), text);
    }

    public String getVariant() {
        return this.variant;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof ModelResourceLocation && super.equals(object)) {
            ModelResourceLocation modelResourceLocation = (ModelResourceLocation) object;
            return this.variant.equals(modelResourceLocation.variant);
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion

    public static int aW() {
        return ef;
    }

    public String toString() {
        return super.toString() + '#' + this.variant;
    }

    public ModelResourceLocation(String text, String textValue) {
        this(0, parsePathString(text + '#' + (textValue == null ? "normal" : textValue)));
    }

    public int hashCode() {
        return 31 * super.hashCode() + this.variant.hashCode();
    }

    protected ModelResourceLocation(int i, String... astring) {
        super(0, new String[] {astring[0], astring[1]});
        this.variant = StringUtils.isEmpty(astring[2]) ? "normal" : astring[2].toLowerCase();
    }

    static {
        b(0);
    }

    protected static String[] parsePathString(String text) {
        String[] astring = new String[] {null, text, null};
        int i = text.indexOf(35);
        String textValue = text;
        if (i >= 0) {
            astring[2] = text.substring(i + 1, text.length());
            if (i > 1) {
                textValue = text.substring(0, i);
            }
        }

        System.arraycopy(ResourceLocation.splitObjectName(textValue), 0, astring, 0, 2);
        return astring;
    }

    // region String decoding support

    // endregion
}
