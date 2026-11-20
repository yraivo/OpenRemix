package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
    BLACK(initializeConstantState(), 0, "BLACK", '0', 0),
    DARK_BLUE("DARK_BLUE", 1, "DARK_BLUE", '1', 1),
    DARK_GREEN("DARK_GREEN", 2, "DARK_GREEN", '2', 2),
    DARK_AQUA("DARK_AQUA", 3, "DARK_AQUA", '3', 3),
    DARK_RED("DARK_RED", 4, "DARK_RED", '4', 4),
    DARK_PURPLE("DARK_PURPLE", 5, "DARK_PURPLE", '5', 5),
    GOLD("GOLD", 6, "GOLD", '6', 6),
    GRAY("GRAY", 7, "GRAY", '7', 7),
    DARK_GRAY("DARK_GRAY", 8, "DARK_GRAY", '8', 8),
    BLUE("BLUE", 9, "BLUE", '9', 9),
    GREEN("GREEN", 10, "GREEN", 'a', 10),
    AQUA("AQUA", 11, "AQUA", 'b', 11),
    RED("RED", 12, "RED", 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 13, "LIGHT_PURPLE", 'd', 13),
    YELLOW("YELLOW", 14, "YELLOW", 'e', 14),
    WHITE("WHITE", 15, "WHITE", 'f', 15),
    OBFUSCATED("OBFUSCATED", 16, "OBFUSCATED", 'k', true),
    BOLD("BOLD", 17, "BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 18, "STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 19, "UNDERLINE", 'n', true),
    ITALIC("ITALIC", 20, "ITALIC", 'o', true),
    RESET("RESET", 21, "RESET", 'r', -1);
    private static EnumChatFormatting[] Xy;

    private static EnumChatFormatting[] Xr;

    private int colorIndex;
    private String controlString;
    private static String[] packedTextFragments;
    private char formattingCode;
    private static Pattern formattingCodePattern;
    private boolean fancyStyling;
    private static Map nameMapping;
    private String name;

    public static EnumChatFormatting getValueByName(String text) {
        return text == null
                ? null
                : (EnumChatFormatting)
                        net.minecraft.util.EnumChatFormatting.nameMapping.get(a(text));
    }

    @Override
    public String toString() {
        return this.controlString;
    }

    private static String a(String text) {
        return text.toLowerCase().replaceAll("[^a-z]", packedTextFragments[0]);
    }

    public static String getTextWithoutFormattingCodes(String text) {
        return text == null
                ? null
                : net.minecraft.util.EnumChatFormatting.formattingCodePattern
                        .matcher(text)
                        .replaceAll(packedTextFragments[2]);
    }

    // region String decoding support

    // endregion

    public static EnumChatFormatting fromColorIndex(int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (i < 0) {
            return net.minecraft.util.EnumChatFormatting.RESET;
        }

        for (EnumChatFormatting enumChatFormatting : values()) {
            if (enumChatFormatting.getColorIndex() == i) {
                return enumChatFormatting;
            }

            if (text == null) {
                break;
            }
        }

        return null;
    }

    public static Collection getValidValues(boolean flag, boolean flag1) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;

        for (EnumChatFormatting enumChatFormatting : values()) {
            if ((!enumChatFormatting.isColor() || flag)
                    && (!enumChatFormatting.isFancyStyling() || flag1)) {
                arrayList.add(enumChatFormatting.getFriendlyName());
            }

            if (textValue == null) {
                break;
            }
        }

        return arrayList;
    }

    static {
        Xy =
                new EnumChatFormatting[] {
                    net.minecraft.util.EnumChatFormatting.BLACK,
                    net.minecraft.util.EnumChatFormatting.DARK_BLUE,
                    net.minecraft.util.EnumChatFormatting.DARK_GREEN,
                    net.minecraft.util.EnumChatFormatting.DARK_AQUA,
                    net.minecraft.util.EnumChatFormatting.DARK_RED,
                    net.minecraft.util.EnumChatFormatting.DARK_PURPLE,
                    net.minecraft.util.EnumChatFormatting.GOLD,
                    net.minecraft.util.EnumChatFormatting.GRAY,
                    net.minecraft.util.EnumChatFormatting.DARK_GRAY,
                    net.minecraft.util.EnumChatFormatting.BLUE,
                    net.minecraft.util.EnumChatFormatting.GREEN,
                    net.minecraft.util.EnumChatFormatting.AQUA,
                    net.minecraft.util.EnumChatFormatting.RED,
                    net.minecraft.util.EnumChatFormatting.LIGHT_PURPLE,
                    net.minecraft.util.EnumChatFormatting.YELLOW,
                    net.minecraft.util.EnumChatFormatting.WHITE,
                    net.minecraft.util.EnumChatFormatting.OBFUSCATED,
                    net.minecraft.util.EnumChatFormatting.BOLD,
                    net.minecraft.util.EnumChatFormatting.STRIKETHROUGH,
                    net.minecraft.util.EnumChatFormatting.UNDERLINE,
                    net.minecraft.util.EnumChatFormatting.ITALIC,
                    net.minecraft.util.EnumChatFormatting.RESET
                };
        Xr =
                new EnumChatFormatting[] {
                    net.minecraft.util.EnumChatFormatting.BLACK,
                    net.minecraft.util.EnumChatFormatting.DARK_BLUE,
                    net.minecraft.util.EnumChatFormatting.DARK_GREEN,
                    net.minecraft.util.EnumChatFormatting.DARK_AQUA,
                    net.minecraft.util.EnumChatFormatting.DARK_RED,
                    net.minecraft.util.EnumChatFormatting.DARK_PURPLE,
                    net.minecraft.util.EnumChatFormatting.GOLD,
                    net.minecraft.util.EnumChatFormatting.GRAY,
                    net.minecraft.util.EnumChatFormatting.DARK_GRAY,
                    net.minecraft.util.EnumChatFormatting.BLUE,
                    net.minecraft.util.EnumChatFormatting.GREEN,
                    net.minecraft.util.EnumChatFormatting.AQUA,
                    net.minecraft.util.EnumChatFormatting.RED,
                    net.minecraft.util.EnumChatFormatting.LIGHT_PURPLE,
                    net.minecraft.util.EnumChatFormatting.YELLOW,
                    net.minecraft.util.EnumChatFormatting.WHITE,
                    net.minecraft.util.EnumChatFormatting.OBFUSCATED,
                    net.minecraft.util.EnumChatFormatting.BOLD,
                    net.minecraft.util.EnumChatFormatting.STRIKETHROUGH,
                    net.minecraft.util.EnumChatFormatting.UNDERLINE,
                    net.minecraft.util.EnumChatFormatting.ITALIC,
                    net.minecraft.util.EnumChatFormatting.RESET
                };
        net.minecraft.util.EnumChatFormatting.nameMapping = Maps.newHashMap();
        net.minecraft.util.EnumChatFormatting.formattingCodePattern =
                Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        for (EnumChatFormatting class_387082d9f087eb9b793e : values()) {
            net.minecraft.util.EnumChatFormatting.nameMapping.put(
                    a(class_387082d9f087eb9b793e.name), class_387082d9f087eb9b793e);
        }
    }

    EnumChatFormatting(
            String text, int k, String textValue, char c0, boolean fancyStylingValue, int j) {
        this.name = textValue;
        this.formattingCode = c0;
        this.fancyStyling = fancyStylingValue;
        this.colorIndex = j;
        this.controlString = __enum_read_Xu()[1] + c0;
    }

    public String getFriendlyName() {
        return this.name().toLowerCase();
    }

    // region String decoding support

    // endregion

    public boolean isFancyStyling() {
        return this.fancyStyling;
    }

    EnumChatFormatting(String text, int j, String textValue, char c0, int k) {
        this(text, j, textValue, c0, false, k);
    }

    // region String decoding support

    // endregion

    public int getColorIndex() {
        return this.colorIndex;
    }

    // region String decoding support

    // endregion

    EnumChatFormatting(String text, int j, String textValue, char c0, boolean flag) {
        this(text, j, textValue, c0, flag, -1);
    }

    public boolean isColor() {
        return !this.fancyStyling && this != net.minecraft.util.EnumChatFormatting.RESET;
    }

    private static EnumChatFormatting[] __recovery_values171() {
        return Xy.clone();
    }

    private static java.lang.String[] __enum_read_Xu() {
        return packedTextFragments;
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.util.EnumChatFormatting.packedTextFragments = new String[5];
        net.minecraft.util.EnumChatFormatting.packedTextFragments[0] = new String("");
        net.minecraft.util.EnumChatFormatting.packedTextFragments[1] = new String("\u00a7");
        net.minecraft.util.EnumChatFormatting.packedTextFragments[2] = new String("");
        net.minecraft.util.EnumChatFormatting.packedTextFragments[3] =
                new String(
                        "-\u0005if\u00a7\u00b1\u00ea\u00c4Wi\u000b\u00a6w\u0018(\u0089o(\u00fd\u00fd\u00b9D\u0003Q\u0006$\u0004\n"
                            + "O\u00f0\u00ef\u0004)z(+\u0004\u001d\u00c4\u00ae\u00fc"
                            + "\t\u00fa\u0019\u00b6\u0005\u0085\u0019\u0019\u00b0Q\r"
                            + "\u00b9\u009e\u000e\u00a0_)e\u0098wOG\u00c5G\u0006!a'p\u001e\u00c3\u0005a\u009250I\u0005"
                            + "\t\u001e\u00872\u0018\b\u0018{\u00cf\u00a9s]g\u00ef\u0005E\u00be\u0010nl\f\u00a7\u00a8$\u00d9\u00b2="
                            + " ^[6\u00db\u0014\u0004\u00b4&\u0082\u00c2\n"
                            + "\u0081x\u0082\u00b4@2\u0088!j\u00ec\n"
                            + "\u00f0\u00b1\u00d2\u00f0\u00d0c\u0004\u009e\u008a\"\u0005\u00aa\u00b7\u00edO\u00d3"
                            + "\t&\u00e5\bz\u008b2o\u00b0\u00fb\t.\u00bb"
                            + "\t\u00b1\u00abMh\u008d`\u0004{\u0084\u0001N\b\u0092\u009f\u009e5Y\u00ce\"\u009d\u0004\u0014\u00b3\u00ec\u00a8\u0006\u00a8\r"
                            + "f\u00d7!d\u000b%\u00c8h\u00df\u0087\u0091\u00e9\"\u00c5BC"
                            + "\tS^\u00a6\r"
                            + "^\u00d9\u00cb|\u00ae\u0003\u00c6b\u00d6"
                            + "\t\u00c4%Tb\u00004\u0000\u00c1\u00d7\u0006U\u00da\u00d9-\u00d6;\u0004\u00cb\u00ef\u00c5X\r"
                            + "G\u00d9\u00d1H\u00a44\u001a;\u0098;\u00baK\u00f8\f\u00f3N\u00ae\u0005\u00e3\u00a6\n"
                            + "-\u001eXs\u00d9\u0004c\u0005x\u00a6\u0004\u00ac\u0013)\u00c7\u0005R\u00f8SS\u00ca"
                            + "\t\u00ab,\u00b9C\u00bd\u0013\u00aaF8\u0006\u00e3?\u0010\u00a2E\u00de"
                            + "\t\u00a4u}\u0088\u00fc\u00a86\u0086\u00b4\u00048\u00cf\u0011\u00ec\u0004\u0018a\u00b2*\u0005\u00152\u0004\u00b7h"
                            + "\tq#\u00e2\u00a2\u00d6)\u00c4\u00d3\u008e\u0005\u0006\u0017\u00d9\u00ae\u009b\u0006\u0000\u0080lU\u00ca"
                            + " \f\u001b\u00fd,T\u0081\u00a1\u00e7\u00ef\b\u00c2?n");
        net.minecraft.util.EnumChatFormatting.packedTextFragments[4] =
                new String(";f\u00ac|\u00a8\n\f\u00fe\u00e1\u00b6\u00fe\u00a0\u008d\u00a1\u0018.");
        return "BLACK";
    }
}
