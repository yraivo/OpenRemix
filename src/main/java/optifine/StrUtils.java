package optifine;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StrUtils {
    private static String[] packedTextFragments;

    public static String removePrefixSuffix(String text, String textValue, String textValue2) {
        return optifine.StrUtils.removePrefixSuffix(
                text, new java.lang.String[] {textValue}, new java.lang.String[] {textValue2});
    }

    public static String[] remove(String[] textArray, int p171_1, int p171_2) {
        int k = optifine.ReflectorMethod.cR();
        if (textArray == null) {
            return textArray;
        }

        if (p171_2 <= 0 || p171_1 >= textArray.length) {
            return textArray;
        }

        if (p171_1 >= p171_2) {
            return textArray;
        }

        java.util.ArrayList arrayList = new java.util.ArrayList(textArray.length);
        int l = 0;

        while (l < textArray.length) {
            java.lang.String text = textArray[l];
            if (l < p171_1 || l >= p171_2) {
                ((java.util.ArrayList) (arrayList)).add(((java.lang.Object) (text)));
            }

            l++;
            if (k == 0) {
                break;
            }
        }

        return (java.lang.String[])
                ((java.lang.Object[])
                        (((java.util.ArrayList) (arrayList))
                                .toArray(
                                        ((java.lang.Object[])
                                                (new java.lang.String
                                                        [((int)
                                                                (((java.util.ArrayList) (arrayList))
                                                                        .size()))])))));
    }

    public static boolean equalsMask(
            String text, String textValue, char p171_2, char p171_3) {
        int i = optifine.ReflectorMethod.cR();
        if (textValue != null && text != null) {
            if (((int) (((java.lang.String) (textValue)).indexOf(((int) (p171_2))))) < 0) {
                return ((int) (((java.lang.String) (textValue)).indexOf(((int) (p171_3))))) < 0
                        ? ((boolean)
                                (((java.lang.String) (textValue))
                                        .equals(((java.lang.Object) (text)))))
                        : optifine.StrUtils.equalsMaskSingle(text, textValue, p171_3);
            }

            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.lang.String textValue2 =
                    ((java.lang.String)
                            (((java.lang.StringBuilder)
                                            (((java.lang.StringBuilder)
                                                    (((java.lang.StringBuilder)
                                                                    (((java.lang.StringBuilder)
                                                                            (((java.lang
                                                                                                    .StringBuilder)
                                                                                            (new java
                                                                                                    .lang
                                                                                                    .StringBuilder()))
                                                                                    .append(
                                                                                            ((java
                                                                                                            .lang
                                                                                                            .String)
                                                                                                    (optifine.StrUtils
                                                                                                            .packedTextFragments[
                                                                                                            0])))))))
                                                            .append(((char) (p171_2)))))))
                                    .toString()));
            if (((boolean)
                    (((java.lang.String) (textValue))
                            .startsWith(((java.lang.String) (textValue2)))))) {
                ((java.util.ArrayList) (arrayList))
                        .add(((java.lang.Object) (optifine.StrUtils.packedTextFragments[1])));
            }

            java.util.StringTokenizer stringtokenizer =
                    new java.util.StringTokenizer(textValue, textValue2);

            while (((boolean)
                    (((java.util.StringTokenizer) (stringtokenizer)).hasMoreElements()))) {
                ((java.util.ArrayList) (arrayList))
                        .add(
                                ((java.lang.Object)
                                        (((java.lang.String)
                                                (((java.util.StringTokenizer) (stringtokenizer))
                                                        .nextToken())))));
                if (i == 0) {
                    break;
                }
            }

            if (((boolean)
                    (((java.lang.String) (textValue))
                            .endsWith(((java.lang.String) (textValue2)))))) {
                ((java.util.ArrayList) (arrayList))
                        .add(((java.lang.Object) (optifine.StrUtils.packedTextFragments[2])));
            }

            java.lang.String textValue3 =
                    (java.lang.String)
                            ((java.lang.Object) (((java.util.ArrayList) (arrayList)).get(0)));
            if (!optifine.StrUtils.startsWithMaskSingle(text, textValue3, p171_3)) {
                return false;
            }

            java.lang.String textValue4 =
                    (java.lang.String)
                            ((java.lang.Object)
                                    (((java.util.ArrayList) (arrayList))
                                            .get(
                                                    ((int)
                                                            (((int)
                                                                            (((java.util.ArrayList)
                                                                                            (arrayList))
                                                                                    .size()))
                                                                    - 1)))));
            if (!optifine.StrUtils.endsWithMaskSingle(text, textValue4, p171_3)) {
                return false;
            }

            int j = 0;
            int k = 0;

            while (k < ((int) (((java.util.ArrayList) (arrayList)).size()))) {
                java.lang.String textValue5 =
                        (java.lang.String)
                                ((java.lang.Object)
                                        (((java.util.ArrayList) (arrayList)).get(((int) (k)))));
                if (((int) (((java.lang.String) (textValue5)).length())) > 0) {
                    int l = optifine.StrUtils.indexOfMaskSingle(text, textValue5, j, p171_3);
                    if (l < 0) {
                        return false;
                    }

                    j = l + ((int) (((java.lang.String) (textValue5)).length()));
                }

                k++;
                if (i == 0) {
                    break;
                }
            }

            return true;
        } else {
            return (textValue == text);
        }
    }

    private static boolean startsWithMaskSingle(String text, String textValue, char p171_2) {
        if (text != null && textValue != null) {
            if (((int) (((java.lang.String) (text)).length()))
                    < ((int) (((java.lang.String) (textValue)).length()))) {
                return false;
            }

            java.lang.String textValue2 =
                    ((java.lang.String)
                            (((java.lang.String) (text))
                                    .substring(
                                            0,
                                            ((int)
                                                    (((int)
                                                            (((java.lang.String) (textValue))
                                                                    .length())))))));
            return optifine.StrUtils.equalsMaskSingle(textValue2, textValue, p171_2);
        } else {
            return (text == textValue);
        }
    }

    public static boolean isEmpty(String text) {
        try {
            if (!optifine.StrUtils.a(text)) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception);
        }

        return false;
    }

    public static String getSegment(String text, String textValue, String textValue2) {
        if (text != null && textValue != null && textValue2 != null) {
            int i = ((int) (((java.lang.String) (text)).indexOf(((java.lang.String) (textValue)))));
            if (i < 0) {
                return null;
            }

            int j =
                    ((int)
                            (((java.lang.String) (text))
                                    .indexOf(((java.lang.String) (textValue2)), ((int) (i)))));
            return j < 0
                    ? null
                    : ((java.lang.String)
                            (((java.lang.String) (text))
                                    .substring(
                                            ((int) (i)),
                                            ((int)
                                                    (j
                                                            + ((int)
                                                                    (((java.lang.String)
                                                                                    (textValue2))
                                                                            .length())))))));
        } else {
            return null;
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        optifine.StrUtils.packedTextFragments[0] = new String("");
        optifine.StrUtils.packedTextFragments[1] = new String("");
        optifine.StrUtils.packedTextFragments[2] = new String("");
        optifine.StrUtils.packedTextFragments[3] = new String("");
        optifine.StrUtils.packedTextFragments[4] = new String("");
        optifine.StrUtils.packedTextFragments[5] = new String("");
        optifine.StrUtils.packedTextFragments[6] = new String("");
        optifine.StrUtils.packedTextFragments[7] = new String("");
        optifine.StrUtils.packedTextFragments[8] = new String("");
        optifine.StrUtils.packedTextFragments[9] = new String("");
        optifine.StrUtils.packedTextFragments[10] = new String("");
        optifine.StrUtils.packedTextFragments[11] = new String("");
        optifine.StrUtils.packedTextFragments[12] = new String("");
    }

    // endregion

    public static String addIfNotContains(String text, String textValue) {
        int k = ReflectorMethod.f();
        int j = 0;
        int i = k;

        while (j < textValue.length()) {
            if (text.indexOf(textValue.charAt(j)) < 0) {
                text = text + textValue.charAt(j);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return text;
    }

    public static String removePrefix(String text, String[] astring) {
        int i = ReflectorMethod.cR();
        if (text != null && astring != null) {
            int j = text.length();
            int k = 0;

            while (k < astring.length) {
                String textValue = astring[k];
                text = removePrefix(text, textValue);
                if (text.length() != j && i != 0) {
                    break;
                }

                k++;
                if (i == 0) {
                    break;
                }
            }

            return text;
        } else {
            return text;
        }
    }

    public static String[] split(String text, String textValue) {
        int i = ReflectorMethod.cR();
        if (text == null || text.length() <= 0) {
            return new String[0];
        }

        if (textValue == null) {
            return new String[] {text};
        }

        ArrayList arrayList = new ArrayList();
        int j = 0;
        int k = 0;

        while (k < text.length()) {
            char c0 = text.charAt(k);
            if (equals(c0, textValue)) {
                arrayList.add(text.substring(j, k));
                j = k + 1;
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        arrayList.add(text.substring(j, text.length()));
        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    private static NumberFormatException method_a0514df28616b2633822(
            NumberFormatException numberformatexception) {
        return numberformatexception;
    }

    private static int indexOfMaskSingle(String text, String textValue, int i, char c0) {
        int j = ReflectorMethod.cR();
        if (text == null || textValue == null) {
            return -1;
        }

        if (i >= 0 && i <= text.length()) {
            if (text.length() < i + textValue.length()) {
                return -1;
            }

            int k = i;

            while (k + textValue.length() <= text.length()) {
                String textValue2 = text.substring(k, k + textValue.length());
                if (equalsMaskSingle(textValue2, textValue, c0)) {
                    return k;
                }

                k++;
                if (j == 0) {
                    break;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    // region String decoding support

    // endregion

    public static String stringInc(String text) {
        int i = optifine.StrUtils.parseInt(text, -1);

        try {
            if (i == -1) {
                return optifine.StrUtils.packedTextFragments[6];
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception1);
        }

        java.lang.String textValue = optifine.StrUtils.packedTextFragments[7] + ++i;

        try {
            if (textValue.length() > text.length()) {
                return optifine.StrUtils.packedTextFragments[8];
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception);
        }

        return optifine.StrUtils.fillLeft(
                optifine.StrUtils.packedTextFragments[9] + i, text.length(), '0');
    }

    public static String removePrefix(String text, String textValue) {
        if (text != null && textValue != null) {
            if (((boolean)
                    (((java.lang.String) (text)).startsWith(((java.lang.String) (textValue)))))) {
                text =
                        ((java.lang.String)
                                (((java.lang.String) (text))
                                        .substring(
                                                ((int)
                                                        (((int)
                                                                (((java.lang.String) (textValue))
                                                                        .length())))))));
            }

            return text;
        } else {
            return text;
        }
    }

    public static boolean equals(Object p171_0, Object p171_1) {
        return ((p171_0 == p171_1
                ? true
                : (p171_0 != null
                                && ((boolean)
                                        (((java.lang.Object) (p171_0))
                                                .equals(((java.lang.Object) (p171_1)))))
                        ? true
                        : p171_1 != null
                                && ((boolean)
                                        (((java.lang.Object) (p171_1))
                                                .equals(((java.lang.Object) (p171_0))))))));
    }

    public static int findSuffix(String[] textArray, String text) {
        int i = optifine.ReflectorMethod.cR();

        try {
            if (textArray == null || text == null) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception1);
        }

        int j = 0;

        while (j < textArray.length) {
            java.lang.String textValue = textArray[j];

            try {
                if (textValue.endsWith(text)) {
                    return j;
                }
            } catch (java.lang.NumberFormatException numberformatexception) {
                throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return -1;
    }

    public static boolean equalsMask(String text, String[] astring, char c0) {
        int k = ReflectorMethod.cR();
        int j = 0;
        int i = k;

        while (j < astring.length) {
            String textValue = astring[j];
            if (equalsMask(text, textValue, c0)) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    private static boolean endsWithMaskSingle(String text, String textValue, char p171_2) {
        if (text != null && textValue != null) {
            if (((int) (((java.lang.String) (text)).length()))
                    < ((int) (((java.lang.String) (textValue)).length()))) {
                return false;
            }

            java.lang.String textValue2 =
                    ((java.lang.String)
                            (((java.lang.String) (text))
                                    .substring(
                                            ((int)
                                                    (((int) (((java.lang.String) (text)).length()))
                                                            - ((int)
                                                                    (((java.lang.String)
                                                                                    (textValue))
                                                                            .length())))),
                                            ((int)
                                                    (((int)
                                                            (((java.lang.String) (text))
                                                                    .length())))))));
            return optifine.StrUtils.equalsMaskSingle(textValue2, textValue, p171_2);
        } else {
            return (text == textValue);
        }
    }

    public static String removePrefixSuffix(
            String text, String[] textArray, String[] textArrayValue) {
        text = optifine.StrUtils.removePrefix(text, textArray);
        return optifine.StrUtils.removeSuffix(text, textArrayValue);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static String fillLeft(String text, int i, char c0) {
        int j = ReflectorMethod.cR();
        if (text == null) {
            text = packedTextFragments[10];
        }

        if (text.length() >= i) {
            return text;
        }

        StringBuffer stringbuffer = new StringBuffer(text);

        while (stringbuffer.length() < i) {
            stringbuffer.insert(0, c0);
            if (j == 0) {
                break;
            }
        }

        return stringbuffer.toString();
    }

    public static String removeSuffix(String text, String[] astring) {
        int i = ReflectorMethod.cR();
        if (text != null && astring != null) {
            int j = text.length();
            int k = 0;

            while (k < astring.length) {
                String textValue = astring[k];
                text = removeSuffix(text, textValue);
                if (text.length() != j && i != 0) {
                    break;
                }

                k++;
                if (i == 0) {
                    break;
                }
            }

            return text;
        } else {
            return text;
        }
    }

    private static boolean equalsMaskSingle(String text, String textValue, char p171_2) {
        int i = optifine.ReflectorMethod.f();
        if (text != null && textValue != null) {
            if (((int) (((java.lang.String) (text)).length()))
                    != ((int) (((java.lang.String) (textValue)).length()))) {
                return false;
            }

            int j = 0;

            while (j < ((int) (((java.lang.String) (textValue)).length()))) {
                char c1 = ((char) (((java.lang.String) (textValue)).charAt(((int) (j)))));
                if (c1 != p171_2
                        && ((char) (((java.lang.String) (text)).charAt(((int) (j))))) != c1) {
                    return false;
                }

                j++;
                if (i != 0) {
                    break;
                }
            }

            return true;
        } else {
            return (text == textValue);
        }
    }

    public static boolean equalsTrim(String text, String textValue) {
        if (text != null) {
            text = text.trim();
        }

        if (textValue != null) {
            textValue = textValue.trim();
        }

        return optifine.StrUtils.equals(text, textValue);
    }

    public static int findPrefix(String[] textArray, String text) {
        int i = optifine.ReflectorMethod.f();

        try {
            if (textArray == null || text == null) {
                return -1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception1);
        }

        int j = 0;

        while (j < textArray.length) {
            java.lang.String textValue = textArray[j];

            try {
                if (textValue.startsWith(text)) {
                    return j;
                }
            } catch (java.lang.NumberFormatException numberformatexception) {
                throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return -1;
    }

    public static String fillRight(String text, int i, char c0) {
        int j = ReflectorMethod.f();
        if (text == null) {
            text = packedTextFragments[11];
        }

        if (text.length() >= i) {
            return text;
        }

        StringBuffer stringbuffer = new StringBuffer(text);

        while (stringbuffer.length() < i) {
            stringbuffer.append(c0);
            if (j != 0) {
                break;
            }
        }

        return stringbuffer.toString();
    }

    public static boolean a(String text) {
        try {
            if (text == null) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception1);
        }

        try {
            if (text.trim().length() <= 0) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception);
        }

        return false;
    }

    private static boolean equals(char p171_0, String text) {
        int k = optifine.ReflectorMethod.cR();
        int j = 0;
        int i = k;

        while (j < text.length()) {
            try {
                if (text.charAt(j) == p171_0) {
                    return true;
                }
            } catch (java.lang.NumberFormatException numberformatexception) {
                throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public static boolean equalsMask(String text, String textValue, char c0) {
        int i = ReflectorMethod.f();
        if (textValue == null || text == null) {
            return textValue == text;
        }

        if (textValue.indexOf(c0) < 0) {
            return textValue.equals(text);
        }

        ArrayList arrayList = new ArrayList();
        String textValue2 = packedTextFragments[3] + c0;
        if (textValue.startsWith(textValue2)) {
            arrayList.add(packedTextFragments[4]);
        }

        StringTokenizer stringtokenizer = new StringTokenizer(textValue, textValue2);

        while (stringtokenizer.hasMoreElements()) {
            arrayList.add(stringtokenizer.nextToken());
            if (i != 0) {
                break;
            }
        }

        if (textValue.endsWith(textValue2)) {
            arrayList.add(packedTextFragments[5]);
        }

        String textValue3 = (String) arrayList.get(0);
        if (!text.startsWith(textValue3)) {
            return false;
        }

        String textValue4 = (String) arrayList.get(arrayList.size() - 1);
        if (!text.endsWith(textValue4)) {
            return false;
        }

        int j = 0;
        int k = 0;

        while (k < arrayList.size()) {
            String textValue5 = (String) arrayList.get(k);
            if (textValue5.length() > 0) {
                int l = text.indexOf(textValue5, j);
                if (l < 0) {
                    return false;
                }

                j = l + textValue5.length();
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        return true;
    }

    public static boolean startsWith(String text, String[] astring) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return false;
        }

        if (astring == null) {
            return false;
        }

        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            if (text.startsWith(textValue)) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public static boolean endsWith(String text, String[] astring) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return false;
        }

        if (astring == null) {
            return false;
        }

        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            if (text.endsWith(textValue)) {
                return true;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public static int parseInt(String text, int p171_1) {
        try {
            if (text == null) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw optifine.StrUtils.method_a0514df28616b2633822(numberformatexception1);
        }

        try {
            return java.lang.Integer.parseInt(text);
        } catch (java.lang.NumberFormatException numberformatexception) {
            return p171_1;
        }
    }

    public static String replaceSuffix(
            String text, String textValue, String textValue2) {
        if (text != null && textValue != null) {
            if (textValue2 == null) {
                textValue2 = optifine.StrUtils.packedTextFragments[12];
            }

            if (((boolean)
                    (((java.lang.String) (text)).endsWith(((java.lang.String) (textValue)))))) {
                text =
                        ((java.lang.String)
                                (((java.lang.String) (text))
                                        .substring(
                                                0,
                                                ((int)
                                                        (((int)
                                                                        (((java.lang.String) (text))
                                                                                .length()))
                                                                - ((int)
                                                                        (((java.lang.String)
                                                                                        (textValue))
                                                                                .length())))))));
            }

            return ((java.lang.String)
                    (((java.lang.StringBuilder)
                                    (((java.lang.StringBuilder)
                                            (((java.lang.StringBuilder)
                                                            (((java.lang.StringBuilder)
                                                                    (((java.lang.StringBuilder)
                                                                                    (new java.lang
                                                                                            .StringBuilder()))
                                                                            .append(
                                                                                    ((java.lang
                                                                                                    .String)
                                                                                            (text)))))))
                                                    .append(((java.lang.String) (textValue2)))))))
                            .toString()));
        } else {
            return text;
        }
    }

    public static String removeSuffix(String text, String textValue) {
        if (text != null && textValue != null) {
            if (((boolean)
                    (((java.lang.String) (text)).endsWith(((java.lang.String) (textValue)))))) {
                text =
                        ((java.lang.String)
                                (((java.lang.String) (text))
                                        .substring(
                                                0,
                                                ((int)
                                                        (((int)
                                                                        (((java.lang.String) (text))
                                                                                .length()))
                                                                - ((int)
                                                                        (((java.lang.String)
                                                                                        (textValue))
                                                                                .length())))))));
            }

            return text;
        } else {
            return text;
        }
    }

    static {
        packedTextFragments = new String[13];
        unpackLiteralFragments();
    }
}
