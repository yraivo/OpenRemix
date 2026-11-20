package net.minecraft.util;

import net.minecraft.entity.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class ResourceLocation {
    private static Entity[] aXG;
    protected String resourcePath;
    private static String f;
    protected String resourceDomain;

    public static String[] splitObjectName(String text) {
        String[] astring = new String[] {null, text};
        int i = text.indexOf(58);
        if (i >= 0) {
            astring[1] = text.substring(i + 1, text.length());
            if (i > 1) {
                astring[0] = text.substring(0, i);
            }
        }

        return astring;
    }

    // region String decoding support

    // endregion

    public static void b(Entity[] aXGValue) {
        aXG = aXGValue;
    }

    public String getResourcePath() {
        return this.resourcePath;
    }

    @Override
    public int hashCode() {
        return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
    }

    public ResourceLocation(String text) {
        this(0, splitObjectName(text));
    }

    public ResourceLocation(int i, String... astring) {
        this.resourceDomain = StringUtils.isEmpty(astring[0]) ? f : astring[0].toLowerCase();
        this.resourcePath = astring[1];
        Validate.notNull(this.resourcePath);
    }

    static {
        if (cB() == null) {
            b(new Entity[4]);
        }
        f = "minecraft";
    }

    public String getResourceDomain() {
        return this.resourceDomain;
    }

    public static Entity[] cB() {
        return aXG;
    }

    // region String decoding support

    // endregion

    public ResourceLocation(String text, String textValue) {
        this(0, text, textValue);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ResourceLocation)) {
            return false;
        }

        ResourceLocation resourceLocation = (ResourceLocation) object;
        return this.resourceDomain.equals(resourceLocation.resourceDomain)
                && this.resourcePath.equals(resourceLocation.resourcePath);
    }

    @Override
    public String toString() {
        return this.resourceDomain + ':' + this.resourcePath;
    }
}
