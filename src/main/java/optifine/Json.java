package optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {

    public static int[] parseIntArray(JsonElement jsonelement, int i, int[] aint) {
        int j = ReflectorMethod.cR();

        try {
            if (jsonelement == null) {
                return aint;
            }
        } catch (JsonParseException jsonparseexception1) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception1);
        }

        JsonArray jsonarray = jsonelement.getAsJsonArray();

        try {
            if (jsonarray.size() != i) {
                throw new JsonParseException(
                        "Wrong array length: "
                                + jsonarray.size()
                                + ", should be: "
                                + i
                                + ", array: "
                                + jsonarray);
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        int[] aint1 = new int[jsonarray.size()];
        int k = 0;

        while (k < aint1.length) {
            aint1[k] = jsonarray.get(k).getAsInt();
            k++;
            if (j == 0) {
                break;
            }
        }

        return aint1;
    }

    public static float getFloat(JsonObject jsonobject, String text, float f) {
        JsonElement jsonelement = jsonobject.get(text);

        try {
            if (jsonelement == null) {
                return f;
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        return jsonelement.getAsFloat();
    }

    public static float[] parseFloatArray(
            JsonElement jsonelement, int i, float[] afloat) {
        int j = ReflectorMethod.cR();

        try {
            if (jsonelement == null) {
                return afloat;
            }
        } catch (JsonParseException jsonparseexception1) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception1);
        }

        JsonArray jsonarray = jsonelement.getAsJsonArray();

        try {
            if (jsonarray.size() != i) {
                throw new JsonParseException(
                        "Wrong array length: "
                                + jsonarray.size()
                                + ", should be: "
                                + i
                                + ", array: "
                                + jsonarray);
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        float[] afloat1 = new float[jsonarray.size()];
        int k = 0;

        while (k < afloat1.length) {
            afloat1[k] = jsonarray.get(k).getAsFloat();
            k++;
            if (j == 0) {
                break;
            }
        }

        return afloat1;
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public static int[] parseIntArray(JsonElement jsonelement, int i) {
        return parseIntArray(jsonelement, i, (int[]) null);
    }

    public static float[] parseFloatArray(JsonElement jsonelement, int i) {
        return parseFloatArray(jsonelement, i, (float[]) null);
    }


    public static String getString(JsonObject jsonobject, String text) {
        return getString(jsonobject, text, (String) null);
    }

    public static boolean getBoolean(
            JsonObject jsonobject, String text, boolean flag) {
        JsonElement jsonelement = jsonobject.get(text);

        try {
            if (jsonelement == null) {
                return flag;
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        return jsonelement.getAsBoolean();
    }

    public static String getString(JsonObject jsonobject, String text, String textValue) {
        JsonElement jsonelement = jsonobject.get(text);

        try {
            if (jsonelement == null) {
                return textValue;
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        return jsonelement.getAsString();
    }
}
