package net.minecraft.client.resources.data;

import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer {
    private static String[] packedTextFragments;

    public FontMetadataSection deserializeFontMetadataSection(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        boolean flag1 = BaseMetadataSectionSerializer.method_b417d7500ec59d69e373();
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        float[] afloat = new float[256];
        float[] afloat1 = new float[256];
        float[] afloat2 = new float[256];
        boolean flag = flag1;
        float f = 1.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        if (jsonobject.has("characters")) {
            if (!jsonobject.get("characters").isJsonObject()) {
                throw new JsonParseException(
                        "Invalid font->characters: expected object, was "
                                + jsonobject.get("characters"));
            }

            JsonObject jsonobject1 = jsonobject.getAsJsonObject("characters");
            if (jsonobject1.has("default")) {
                if (!jsonobject1.get("default").isJsonObject()) {
                    throw new JsonParseException(
                            "Invalid font->characters->default: expected object, was "
                                    + jsonobject1.get("default"));
                }

                JsonObject jsonobject2 = jsonobject1.getAsJsonObject("default");
                f = JsonUtils.getFloat(jsonobject2, "width", f);
                Validate.inclusiveBetween(0.0, Float.MAX_VALUE, f, "Invalid default width");
                f1 = JsonUtils.getFloat(jsonobject2, "spacing", f1);
                Validate.inclusiveBetween(0.0, Float.MAX_VALUE, f1, "Invalid default spacing");
                f2 = JsonUtils.getFloat(jsonobject2, "left", f1);
                Validate.inclusiveBetween(0.0, Float.MAX_VALUE, f2, "Invalid default left");
            }

            int i = 0;

            while (i < 256) {
                JsonElement jsonelement1 = jsonobject1.get(Integer.toString(i));
                float f3 = f;
                float f4 = f1;
                float f5 = f2;
                if (jsonelement1 != null) {
                    JsonObject jsonobject3 =
                            JsonUtils.getJsonObject(
                                    jsonelement1, "characters[" + i + packedTextFragments[0]);
                    f3 = JsonUtils.getFloat(jsonobject3, "width", f);
                    Validate.inclusiveBetween(0.0, Float.MAX_VALUE, f3, "Invalid width");
                    f4 = JsonUtils.getFloat(jsonobject3, "spacing", f1);
                    Validate.inclusiveBetween(0.0, Float.MAX_VALUE, f4, "Invalid spacing");
                    f5 = JsonUtils.getFloat(jsonobject3, "left", f2);
                    Validate.inclusiveBetween(0.0, Float.MAX_VALUE, f5, "Invalid left");
                }

                afloat[i] = f3;
                afloat1[i] = f4;
                afloat2[i] = f5;
                i++;
                if (flag) {
                    break;
                }
            }
        }

        return new FontMetadataSection(afloat, afloat2, afloat1);
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializeFontMetadataSection(jsonelement, type, jsondeserializationcontext);
    }

    static {
        net.minecraft.client.resources.data.FontMetadataSectionSerializer.packedTextFragments = new String[3];
        net.minecraft.client.resources.data.FontMetadataSectionSerializer.packedTextFragments[0] = new String("]");
    }

    public String getSectionName() {
        return "font";
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }
}
