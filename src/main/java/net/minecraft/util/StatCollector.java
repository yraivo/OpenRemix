package net.minecraft.util;

public class StatCollector {
    private static StringTranslate fallbackTranslator = new StringTranslate();
    private static StringTranslate localizedName = StringTranslate.getInstance();

    public static String translateToLocal(String text) {
        return net.minecraft.util.StatCollector.localizedName.translateKey(text);
    }

    public static String translateToLocalFormatted(String text, Object... aobject) {
        return net.minecraft.util.StatCollector.localizedName.translateKeyFormat(text, aobject);
    }

    public static boolean canTranslate(String text) {
        return net.minecraft.util.StatCollector.localizedName.isKeyTranslated(text);
    }

    public static long getLastTranslationUpdateTimeInMilliseconds() {
        return net.minecraft.util.StatCollector.localizedName.getLastUpdateTimeInMilliseconds();
    }

    public static String translateToFallback(String text) {
        return net.minecraft.util.StatCollector.fallbackTranslator.translateKey(text);
    }
}
