package net.minecraft.client.renderer.block.model;

import net.minecraft.util.JsonUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class BlockFaceUVDeserializer implements JsonDeserializer {
    private static String[] packedTextFragments;

    public BlockFaceUV method_d2646691961fbc291c09(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        float[] afloat = this.parseUV(jsonobject);
        int i = this.parseRotation(jsonobject);
        return new BlockFaceUV(afloat, i);
    }

    private float[] parseUV(JsonObject jsonobject) {
        int i = BakedQuad.cM();
        if (!jsonobject.has("uv")) {
            return null;
        }

        JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "uv");
        if (jsonarray.size() != 4) {
            throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
        }

        float[] afloat = new float[4];
        int j = 0;

        while (j < afloat.length) {
            afloat[j] = JsonUtils.getFloat(jsonarray.get(j), "uv[" + j + packedTextFragments[0]);
            j++;
            if (i == 0) {
                break;
            }
        }

        return afloat;
    }

    static {
        net.minecraft.client.renderer.block.model.BlockFaceUVDeserializer.packedTextFragments = new String[3];
        net.minecraft.client.renderer.block.model.BlockFaceUVDeserializer.packedTextFragments[0] = new String("]");
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_d2646691961fbc291c09(jsonelement, type, jsondeserializationcontext);
    }

    protected int parseRotation(JsonObject jsonobject) {
        int i = JsonUtils.getInt(jsonobject, "rotation", 0);
        if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
            return i;
        } else {
            throw new JsonParseException(
                    "Invalid rotation " + i + " found, only 0/90/180/270 allowed");
        }
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }
}
