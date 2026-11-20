package net.minecraft.client.renderer.block.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import remix.compat.vecmath.Vector3f;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class ItemTransformVec3fDeserializer implements JsonDeserializer {
    private static Vector3f SCALE_DEFAULT;

    private static String[] packedTextFragments;
    private static Vector3f TRANSLATION_DEFAULT;
    private static Vector3f ROTATION_DEFAULT;

    public ItemTransformVec3f method_8cdce8acf0c4e057a811(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        Vector3f vector3f =
                this.parseVector3f(jsonobject, "rotation", net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.ROTATION_DEFAULT);
        int j = BakedQuad.eR();
        Vector3f vector3fValue =
                this.parseVector3f(jsonobject, "translation", net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.TRANSLATION_DEFAULT);
        vector3fValue.scale(0.0625F);
        MathHelper.clamp(vector3fValue.x, -1.5, 1.5);
        MathHelper.clamp(vector3fValue.y, -1.5, 1.5);
        int i = j;
        MathHelper.clamp(vector3fValue.z, -1.5, 1.5);
        Vector3f vector3fValue2 =
                this.parseVector3f(jsonobject, "scale", net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.SCALE_DEFAULT);

        try {
            MathHelper.clamp(vector3fValue2.x, -1.5, 1.5);
            MathHelper.clamp(vector3fValue2.y, -1.5, 1.5);
            MathHelper.clamp(vector3fValue2.z, -1.5, 1.5);
            ItemTransformVec3f itemTransformVec3f =
                    new ItemTransformVec3f(
                            vector3f,
                            vector3fValue,
                            vector3fValue2);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                BakedQuad.b(++i);
            }

            return itemTransformVec3f;
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    private Vector3f parseVector3f(
            JsonObject jsonobject,
            String text,
            Vector3f vector3f) {
        int i = BakedQuad.eR();

        try {
            if (!jsonobject.has(text)) {
                return vector3f;
            }
        } catch (JsonParseException jsonparseexception1) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception1);
        }

        JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, text);

        try {
            if (jsonarray.size() != 3) {
                throw new JsonParseException(
                        "Expected 3 " + text + " values, found: " + jsonarray.size());
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        float[] afloat = new float[3];
        int j = 0;

        while (j < afloat.length) {
            afloat[j] =
                    JsonUtils.getFloat(
                            jsonarray.get(j),
                            text + packedTextFragments[0] + j + packedTextFragments[1]);
            j++;
            if (i != 0) {
                break;
            }
        }

        return new Vector3f(afloat);
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_8cdce8acf0c4e057a811(jsonelement, type, jsondeserializationcontext);
    }

    static {
        net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.packedTextFragments = new String[4];
        net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.packedTextFragments[0] = new String("[");
        net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.packedTextFragments[1] = new String("]");
        net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
        net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
        net.minecraft.client.renderer.block.model.ItemTransformVec3fDeserializer.SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);
    }
}
