package net.minecraft.util;

public class ChatAllowedCharacters {
    public static final char[] allowedCharactersArray =
            new char[] {
                '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'
            };

    public static boolean isAllowedCharacter(char c0) {
        return c0 != 167 && c0 >= ' ' && c0 != 127;
    }

    public static String filterAllowedCharacters(String text) {
        StringBuilder stringbuilder = new StringBuilder();
        char[] achar = text.toCharArray();
        String textValue = Vec3i.getLegacyCoordinateFlowMarker();
        int length = achar.length;
        int j = 0;
        String textValue2 = textValue;

        while (j < length) {
            char c0 = achar[j];
            if (isAllowedCharacter(c0)) {
                stringbuilder.append(c0);
            }

            j++;
            if (textValue2 == null) {
                break;
            }
        }

        return stringbuilder.toString();
    }
}
