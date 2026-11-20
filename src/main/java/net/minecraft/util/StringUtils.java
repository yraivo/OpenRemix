package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
    private static String[] packedTextFragments;

    private static Pattern patternControlCode;

    public static String ticksToElapsedTime(int i) {
        int j = i / 20;
        int k = j / 60;
        j %= 60;
        return j < 10 ? k + ":0" + j : k + packedTextFragments[0] + j;
    }

    public static boolean isNullOrEmpty(String text) {
        return org.apache.commons.lang3.StringUtils.isEmpty(text);
    }

    public static String stripControlCodes(String text) {
        return net.minecraft.util.StringUtils.patternControlCode
                .matcher(text)
                .replaceAll(packedTextFragments[1]);
    }

    static {
        net.minecraft.util.StringUtils.packedTextFragments = new String[3];
        net.minecraft.util.StringUtils.packedTextFragments[0] = new String(":");
        net.minecraft.util.StringUtils.packedTextFragments[1] = new String("");
        net.minecraft.util.StringUtils.patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    }
}
