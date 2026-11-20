package net.minecraft.nbt;

import net.minecraft.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;
import java.util.regex.Pattern;

public class JsonToNBT {
    private static Logger logger;
    private static Pattern Ag;

    private static String[] packedTextFragments;

    private static String parseKeyValue(String text, boolean flag) throws NBTException {
        if (flag) {
            text = text.trim();
            if (text.startsWith(packedTextFragments[7])
                    || text.startsWith(packedTextFragments[8])) {
                return text;
            }
        }

        int i = findNextChar(text, ':');
        if (i != -1) {
            return text.substring(i + 1).trim();
        } else if (flag) {
            return text;
        } else {
            throw new NBTException("Unable to locate name/value separator for string: " + text);
        }
    }

    private static JsonToNBTAny parseNameAndValue(String text, boolean flag) throws NBTException {
        String textValue = parseKeyName(text, flag);
        String textValue2 = parseKeyValue(text, flag);
        return createCompoundFromPairs(textValue, textValue2);
    }

    private static int findNextChar(String text, char c0) {
        String[] astring1 = NBTBase.xf();
        int i = 0;
        byte b0 = 1;
        String[] astring = astring1;

        while (i < text.length()) {
            label39:
            {
                char c1 = text.charAt(i);
                if (c1 == '"') {
                    if (isCharEscaped(text, i)) {
                        break label39;
                    }

                    b0 = (byte) (b0 == 0 ? 1 : 0);
                    if (astring == null) {
                        break label39;
                    }
                }

                if (b0 != 0) {
                    if (c1 == c0) {
                        return i;
                    }

                    if (c1 == '{' || c1 == '[') {
                        return -1;
                    }
                }
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        return -1;
    }

    static int countBrackets(String text) throws NBTException {
        String[] astring1 = NBTBase.xf();
        int i = 0;
        String[] astring = astring1;
        byte b0 = 0;
        Stack stack = new Stack();

        for (int j = 0; j < text.length(); j++) {
            char c0 = text.charAt(j);
            if (c0 == '"') {
                if (isCharEscaped(text, j)) {
                    if (b0 == 0) {
                        throw new NBTException("Illegal use of \\\": " + text);
                    }
                    break;
                }

                b0 = (byte) (b0 == 0 ? 1 : 0);
                if (astring == null) {
                    break;
                }
            }

            if (b0 == 0) {
                if (c0 != '{' && c0 != '[') {
                    if (c0 == '}' && (stack.isEmpty() || (Character) stack.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + text);
                    }

                    if (c0 == ']' && (stack.isEmpty() || (Character) stack.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + text);
                    }
                } else {
                    if (stack.isEmpty()) {
                        i++;
                    }

                    stack.push(c0);
                }
            }
            break;
        }

        if (b0 != 0) {
            throw new NBTException("Unbalanced quotation: " + text);
        }

        if (!stack.isEmpty()) {
            throw new NBTException("Unbalanced brackets: " + text);
        }

        if (i == 0 && !text.isEmpty()) {
            i = 1;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            NBTBase.b(new String[4]);
        }

        return i;
    }

    public static CompoundTag getTagFromJson(String text) throws NBTException {
        text = text.trim();
        if (!text.startsWith(packedTextFragments[0])) {
            throw new NBTException("Invalid tag encountered, expected '{' as first char.");
        } else if (countBrackets(text) != 1) {
            throw new NBTException("Encountered multiple top tags, only one expected");
        } else {
            return (CompoundTag) MS("tag", text).parse();
        }
    }

    private static String splitNextKeyAndValue(String text, boolean flag) throws NBTException {
        int i = findNextChar(text, ':');
        int j = findNextChar(text, ',');
        if (flag) {
            if (i == -1) {
                throw new NBTException("Unable to locate name/value separator for string: " + text);
            }

            if (j != -1 && j < i) {
                throw new NBTException("Name error at: " + text);
            }
        } else if (i == -1 || i > j) {
            i = -1;
        }

        return substringFrom(text, i);
    }

    private static String parseKeyName(String text, boolean flag) throws NBTException {
        if (flag) {
            text = text.trim();
            if (text.startsWith(packedTextFragments[3])
                    || text.startsWith(packedTextFragments[4])) {
                return packedTextFragments[5];
            }
        }

        int i = findNextChar(text, ':');
        if (i != -1) {
            return text.substring(0, i).trim();
        } else if (flag) {
            return packedTextFragments[6];
        } else {
            throw new NBTException("Unable to locate name/value separator for string: " + text);
        }
    }

    private static NBTException method_63611f3fd5128a257bff(NBTException nbtException) {
        return nbtException;
    }

    private static boolean isCharEscaped(String text, int i) {
        return i > 0 && text.charAt(i - 1) == '\\' && !isCharEscaped(text, i - 1);
    }

    static {
        net.minecraft.nbt.JsonToNBT.packedTextFragments = new String[11];
        net.minecraft.nbt.JsonToNBT.packedTextFragments[0] = new String("{");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[1] = new String("{");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[2] = new String("[");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[3] = new String("{");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[4] = new String("[");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[5] = new String("");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[6] = new String("");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[7] = new String("{");
        net.minecraft.nbt.JsonToNBT.packedTextFragments[8] = new String("[");
        net.minecraft.nbt.JsonToNBT.logger = LogManager.getLogger();
        Ag = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
    }

    private static String substringFrom(String text, int i) throws NBTException {
        Stack stack = new Stack();
        int j = i + 1;
        byte b0 = 0;
        byte b1 = 0;
        String[] astring1 = NBTBase.xf();
        byte b2 = 0;
        int k = 0;
        String[] astring = astring1;

        while (j < text.length()) {
            char c0;
            label98:
            {
                c0 = text.charAt(j);
                if (c0 == '"') {
                    if (isCharEscaped(text, j)) {
                        if (b0 == 0) {
                            throw new NBTException("Illegal use of \\\": " + text);
                        }
                        break label98;
                    }

                    b0 = (byte) (b0 == 0 ? 1 : 0);
                    if (b0 != 0 && b2 == 0) {
                        b1 = 1;
                    }

                    if (b0 != 0) {
                        break label98;
                    }

                    k = j;
                    if (astring == null) {
                        break label98;
                    }
                }

                if (b0 == 0) {
                    if (c0 != '{' && c0 != '[') {
                        if (c0 == '}' && (stack.isEmpty() || (Character) stack.pop() != '{')) {
                            throw new NBTException("Unbalanced curly brackets {}: " + text);
                        }

                        if (c0 == ']' && (stack.isEmpty() || (Character) stack.pop() != '[')) {
                            throw new NBTException("Unbalanced square brackets []: " + text);
                        }

                        if (c0 == ',' && stack.isEmpty()) {
                            return text.substring(0, j);
                        }
                    } else {
                        stack.push(c0);
                    }
                }
            }

            if (!Character.isWhitespace(c0)) {
                if (b0 == 0 && b1 != 0 && k != j) {
                    return text.substring(0, k + 1);
                }

                b2 = 1;
            }

            j++;
            if (astring != null) {
                break;
            }
        }

        return text.substring(0, j);
    }

    static JsonToNBTAny MS(String text, String textValue) throws NBTException {
        String[] astring1 = NBTBase.xf();
        textValue = textValue.trim();
        String[] astring = astring1;
        if (textValue.startsWith(packedTextFragments[1])) {
            textValue = textValue.substring(1, textValue.length() - 1);
            JsonToNBTCompound jsonToNBTCompound = new JsonToNBTCompound(text);

            while (textValue.length() > 0) {
                String textValue2 = splitNextKeyAndValue(textValue, true);
                if (textValue2.length() > 0) {
                    byte b1 = 0;
                    jsonToNBTCompound.uQ.add(
                            parseNameAndValue(
                                    textValue2, remix.compat.compiler.JvmBooleanSlots.fromInt(b1)));
                }

                if (textValue.length() < textValue2.length() + 1 && astring == null) {
                    break;
                }

                char c1 = textValue.charAt(textValue2.length());
                if (c1 != ',' && c1 != '{' && c1 != '}' && c1 != '[' && c1 != ']') {
                    throw new NBTException(
                            "Unexpected token '"
                                    + c1
                                    + "' at: "
                                    + textValue.substring(textValue2.length()));
                }

                textValue = textValue.substring(textValue2.length() + 1);
                if (astring != null) {
                    break;
                }
            }

            return jsonToNBTCompound;
        } else if (textValue.startsWith(packedTextFragments[2])
                && !Ag.matcher(textValue).matches()) {
            textValue = textValue.substring(1, textValue.length() - 1);
            JsonToNBTList jsonToNBTList = new JsonToNBTList(text);

            while (textValue.length() > 0) {
                String textValue3 = splitNextKeyAndValue(textValue, false);
                if (textValue3.length() > 0) {
                    byte b0 = 1;
                    jsonToNBTList.uQ.add(
                            parseNameAndValue(
                                    textValue3, remix.compat.compiler.JvmBooleanSlots.fromInt(b0)));
                }

                if (textValue.length() < textValue3.length() + 1 && astring == null) {
                    break;
                }

                char c0 = textValue.charAt(textValue3.length());
                if (c0 != ',' && c0 != '{' && c0 != '}' && c0 != '[' && c0 != ']') {
                    throw new NBTException(
                            "Unexpected token '"
                                    + c0
                                    + "' at: "
                                    + textValue.substring(textValue3.length()));
                }

                textValue = textValue.substring(textValue3.length() + 1);
                if (astring != null) {
                    break;
                }
            }

            return jsonToNBTList;
        } else {
            return new JsonToNBTPrimitive(text, textValue);
        }
    }

    static JsonToNBTAny createCompoundFromPairs(String... astring) throws NBTException {
        return MS(astring[0], astring[1]);
    }
}
