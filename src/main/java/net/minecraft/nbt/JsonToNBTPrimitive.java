package net.minecraft.nbt;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.regex.Pattern;

class JsonToNBTPrimitive extends JsonToNBTAny {
    private static Pattern BYTE;

    private static Pattern INTEGER;
    protected String jsonValue;
    private static String[] packedTextFragments;
    private static Pattern LONG;
    private static Pattern DOUBLE_UNTYPED;
    private static Splitter SPLITTER;
    private static Pattern FLOAT;
    private static Pattern DOUBLE;
    private static Pattern SHORT;

    public JsonToNBTPrimitive(String text, String textValue) {
        this.json = text;
        this.jsonValue = textValue;
    }

    private static NumberFormatException method_a0514df28616b2633822(
            NumberFormatException numberformatexception) {
        return numberformatexception;
    }

    public NBTBase parse() {
        String[] astring = NBTBase.xf();

        try {
            if (net.minecraft.nbt.JsonToNBTPrimitive.DOUBLE.matcher(this.jsonValue).matches()) {
                return new NBTTagDouble(
                        Double.parseDouble(
                                this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }

            if (net.minecraft.nbt.JsonToNBTPrimitive.FLOAT.matcher(this.jsonValue).matches()) {
                return new NBTTagFloat(
                        Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }

            if (net.minecraft.nbt.JsonToNBTPrimitive.BYTE.matcher(this.jsonValue).matches()) {
                return new NBTTagByte(
                        Byte.parseByte(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }

            if (net.minecraft.nbt.JsonToNBTPrimitive.LONG.matcher(this.jsonValue).matches()) {
                return new NBTTagLong(
                        Long.parseLong(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }

            if (net.minecraft.nbt.JsonToNBTPrimitive.SHORT.matcher(this.jsonValue).matches()) {
                return new NBTTagShort(
                        Short.parseShort(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
            }

            if (net.minecraft.nbt.JsonToNBTPrimitive.INTEGER.matcher(this.jsonValue).matches()) {
                return new NBTTagInt(Integer.parseInt(this.jsonValue));
            }

            if (net.minecraft.nbt.JsonToNBTPrimitive.DOUBLE_UNTYPED.matcher(this.jsonValue).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.jsonValue));
            }

            if (this.jsonValue.equalsIgnoreCase("true")
                    || this.jsonValue.equalsIgnoreCase("false")) {
                return new NBTTagByte((byte) (Boolean.parseBoolean(this.jsonValue) ? 1 : 0));
            }
        } catch (NumberFormatException numberformatexception1) {
            this.jsonValue = this.jsonValue.replaceAll("\\\\\"", packedTextFragments[0]);
            return new NBTTagString(this.jsonValue);
        }

        if (this.jsonValue.startsWith(packedTextFragments[1])
                && this.jsonValue.endsWith(packedTextFragments[2])) {
            String text = this.jsonValue.substring(1, this.jsonValue.length() - 1);
            String[] astring1 =
                    (String[])
                            Iterables.toArray(
                                    net.minecraft.nbt.JsonToNBTPrimitive.SPLITTER.split(text), String.class);

            try {
                int[] aint = new int[astring1.length];
                int j = 0;

                while (j < astring1.length) {
                    aint[j] = Integer.parseInt(astring1[j].trim());
                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                return new NBTTagIntArray(aint);
            } catch (NumberFormatException numberformatexception) {
                return new NBTTagString(this.jsonValue);
            }
        } else {
            if (this.jsonValue.startsWith(packedTextFragments[3])
                    && this.jsonValue.endsWith(packedTextFragments[4])) {
                this.jsonValue = this.jsonValue.substring(1, this.jsonValue.length() - 1);
            }

            this.jsonValue = this.jsonValue.replaceAll("\\\\\"", packedTextFragments[5]);
            StringBuilder stringbuilder = new StringBuilder();
            int i = 0;

            while (i < this.jsonValue.length()) {
                {
                    if (i < this.jsonValue.length() - 1
                            && this.jsonValue.charAt(i) == '\\'
                            && this.jsonValue.charAt(i + 1) == '\\') {
                        stringbuilder.append('\\');
                        i++;
                        if (!(astring == null)) {

                            stringbuilder.append(this.jsonValue.charAt(i));
                        }
                    } else {

                        stringbuilder.append(this.jsonValue.charAt(i));
                    }
                }

                i++;
                if (astring != null) {
                    break;
                }
            }

            return new NBTTagString(stringbuilder.toString());
        }
    }

    static {
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments = new String[8];
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments[0] = new String("\"");
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments[1] = new String("[");
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments[2] = new String("]");
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments[3] = new String("\"");
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments[4] = new String("\"");
        net.minecraft.nbt.JsonToNBTPrimitive.packedTextFragments[5] = new String("\"");
        net.minecraft.nbt.JsonToNBTPrimitive.DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
        net.minecraft.nbt.JsonToNBTPrimitive.FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
        net.minecraft.nbt.JsonToNBTPrimitive.BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
        net.minecraft.nbt.JsonToNBTPrimitive.LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
        net.minecraft.nbt.JsonToNBTPrimitive.SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
        net.minecraft.nbt.JsonToNBTPrimitive.INTEGER = Pattern.compile("[-+]?[0-9]+");
        net.minecraft.nbt.JsonToNBTPrimitive.DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        net.minecraft.nbt.JsonToNBTPrimitive.SPLITTER = Splitter.on(',').omitEmptyStrings();
    }
}
