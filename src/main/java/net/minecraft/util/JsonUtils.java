package net.minecraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

    private static String[] packedTextFragments;

    public static JsonArray getJsonArray(JsonObject jsonobject, String text) {
        if (jsonobject.has(text)) {
            return getJsonArray(jsonobject.get(text), text);
        } else {
            throw new JsonSyntaxException("Missing " + text + ", expected to find a JsonArray");
        }
    }

    public static String getString(JsonElement jsonelement, String text) {
        if (jsonelement.isJsonPrimitive()) {
            return jsonelement.getAsString();
        } else {
            throw new JsonSyntaxException(
                    "Expected " + text + " to be a string, was " + toString(jsonelement));
        }
    }

    public static String getString(
            JsonObject p171_0, String text, String textValue) {
        try {
            if (p171_0.has(text)) {
                return net.minecraft.util.JsonUtils.getString(p171_0.get(text), text);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return textValue;
    }

    // region String decoding support

    // endregion

    public static boolean isBoolean(JsonObject p171_0, String text) {
        try {
            if (!net.minecraft.util.JsonUtils.method_76ecbc1d786081c114e9(p171_0, text)) {
                return false;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_0.getAsJsonPrimitive(text).isBoolean();
    }

    public static boolean method_7cc6238b82e560d487c5(JsonObject p171_0, String text) {
        try {
            if (!net.minecraft.util.JsonUtils.method_76ecbc1d786081c114e9(p171_0, text)) {
                return false;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_0.getAsJsonPrimitive(text).isString();
    }

    public static float getFloat(JsonObject p171_0, String text, float p171_2) {
        try {
            if (p171_0.has(text)) {
                return net.minecraft.util.JsonUtils.getFloat(p171_0.get(text), text);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_2;
    }

    public static boolean isString(JsonElement p171_0) {
        try {
            if (!p171_0.isJsonPrimitive()) {
                return false;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_0.getAsJsonPrimitive().isString();
    }

    public static int getInt(JsonObject p171_0, String text, int p171_2) {
        try {
            if (p171_0.has(text)) {
                return net.minecraft.util.JsonUtils.getInt(p171_0.get(text), text);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_2;
    }

    public static JsonObject getJsonObject(JsonObject jsonobject, String text) {
        if (jsonobject.has(text)) {
            return getJsonObject(jsonobject.get(text), text);
        } else {
            throw new JsonSyntaxException("Missing " + text + ", expected to find a JsonObject");
        }
    }

    static {
        net.minecraft.util.JsonUtils.packedTextFragments = new String[6];
        net.minecraft.util.JsonUtils.packedTextFragments[0] = new String(")");
        net.minecraft.util.JsonUtils.packedTextFragments[1] = new String(")");
        net.minecraft.util.JsonUtils.packedTextFragments[2] = new String(")");
        net.minecraft.util.JsonUtils.packedTextFragments[3] = new String(")");
    }

    public static boolean getBoolean(
            JsonObject p171_0, String text, boolean p171_2) {
        try {
            if (p171_0.has(text)) {
                return net.minecraft.util.JsonUtils.getBoolean(p171_0.get(text), text);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_2;
    }

    private static JsonSyntaxException method_51ad581886d92fd195f5(
            JsonSyntaxException jsonsyntaxexception) {
        return jsonsyntaxexception;
    }

    public static boolean method_ccc125694653ea34445a(JsonObject p171_0, String text) {
        try {
            if (p171_0 == null) {
                return false;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception1) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception1);
        }

        try {
            if (p171_0.get(text) != null) {
                return true;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return false;
    }

    public static JsonArray getJsonArray(JsonElement jsonelement, String text) {
        if (jsonelement.isJsonArray()) {
            return jsonelement.getAsJsonArray();
        } else {
            throw new JsonSyntaxException(
                    "Expected " + text + " to be a JsonArray, was " + toString(jsonelement));
        }
    }

    public static boolean getBoolean(JsonObject jsonobject, String text) {
        if (jsonobject.has(text)) {
            return getBoolean(jsonobject.get(text), text);
        } else {
            throw new JsonSyntaxException("Missing " + text + ", expected to find a Boolean");
        }
    }

    // region String decoding support

    // endregion

    public static int getInt(JsonObject jsonobject, String text) {
        if (jsonobject.has(text)) {
            return getInt(jsonobject.get(text), text);
        } else {
            throw new JsonSyntaxException("Missing " + text + ", expected to find a Int");
        }
    }

    public static float getFloat(JsonElement jsonelement, String text) {
        if (jsonelement.isJsonPrimitive() && jsonelement.getAsJsonPrimitive().isNumber()) {
            return jsonelement.getAsFloat();
        } else {
            throw new JsonSyntaxException(
                    "Expected " + text + " to be a Float, was " + toString(jsonelement));
        }
    }

    public static boolean method_6f269b44202e870a5c79(JsonObject p171_0, String text) {
        try {
            if (!net.minecraft.util.JsonUtils.method_ccc125694653ea34445a(p171_0, text)) {
                return false;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_0.get(text).isJsonArray();
    }

    public static float getFloat(JsonObject jsonobject, String text) {
        if (jsonobject.has(text)) {
            return getFloat(jsonobject.get(text), text);
        } else {
            throw new JsonSyntaxException("Missing " + text + ", expected to find a Float");
        }
    }

    public static String toString(JsonElement jsonelement) {
        String text = StringUtils.abbreviateMiddle(String.valueOf(jsonelement), "...", 10);
        if (jsonelement == null) {
            return "null (missing)";
        }

        if (jsonelement.isJsonNull()) {
            return "null (json)";
        }

        if (jsonelement.isJsonArray()) {
            return "an array (" + text + packedTextFragments[0];
        }

        if (jsonelement.isJsonObject()) {
            return "an object (" + text + packedTextFragments[1];
        }

        if (jsonelement.isJsonPrimitive()) {
            JsonPrimitive jsonprimitive = jsonelement.getAsJsonPrimitive();
            if (jsonprimitive.isNumber()) {
                return "a number (" + text + packedTextFragments[2];
            }

            if (jsonprimitive.isBoolean()) {
                return "a boolean (" + text + packedTextFragments[3];
            }
        }

        return text;
    }

    public static boolean method_76ecbc1d786081c114e9(JsonObject p171_0, String text) {
        try {
            if (!net.minecraft.util.JsonUtils.method_ccc125694653ea34445a(p171_0, text)) {
                return false;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_0.get(text).isJsonPrimitive();
    }

    public static JsonArray getJsonArray(
            JsonObject p171_0, String text, JsonArray p171_2) {
        try {
            if (p171_0.has(text)) {
                return net.minecraft.util.JsonUtils.getJsonArray(p171_0.get(text), text);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_2;
    }

    public static JsonObject getJsonObject(
            JsonObject p171_0, String text, JsonObject p171_2) {
        try {
            if (p171_0.has(text)) {
                return net.minecraft.util.JsonUtils.getJsonObject(p171_0.get(text), text);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw net.minecraft.util.JsonUtils.method_51ad581886d92fd195f5(jsonsyntaxexception);
        }

        return p171_2;
    }

    public static int getInt(JsonElement jsonelement, String text) {
        if (jsonelement.isJsonPrimitive() && jsonelement.getAsJsonPrimitive().isNumber()) {
            return jsonelement.getAsInt();
        } else {
            throw new JsonSyntaxException(
                    "Expected " + text + " to be a Int, was " + toString(jsonelement));
        }
    }

    public static boolean getBoolean(JsonElement jsonelement, String text) {
        if (jsonelement.isJsonPrimitive()) {
            return jsonelement.getAsBoolean();
        } else {
            throw new JsonSyntaxException(
                    "Expected " + text + " to be a Boolean, was " + toString(jsonelement));
        }
    }

    public static JsonObject getJsonObject(JsonElement jsonelement, String text) {
        if (jsonelement.isJsonObject()) {
            return jsonelement.getAsJsonObject();
        } else {
            throw new JsonSyntaxException(
                    "Expected " + text + " to be a JsonObject, was " + toString(jsonelement));
        }
    }

    public static String getString(JsonObject jsonobject, String text) {
        if (jsonobject.has(text)) {
            return getString(jsonobject.get(text), text);
        } else {
            throw new JsonSyntaxException("Missing " + text + ", expected to find a string");
        }
    }
}
