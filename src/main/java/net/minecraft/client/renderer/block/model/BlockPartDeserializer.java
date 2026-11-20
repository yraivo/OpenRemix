package net.minecraft.client.renderer.block.model;

import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import remix.compat.vecmath.Vector3f;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

class BlockPartDeserializer implements JsonDeserializer {
    private static String[] packedTextFragments;

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    private float parseAngle(JsonObject jsonobject) {
        float floatValue = JsonUtils.getFloat(jsonobject, "angle");
        if (floatValue != 0.0F
                && MathHelper.abs(floatValue) != 22.5F
                && MathHelper.abs(floatValue) != 45.0F) {
            throw new JsonParseException(
                    "Invalid rotation " + floatValue + " found, only -45/-22.5/0/22.5/45 allowed");
        } else {
            return floatValue;
        }
    }

    private BlockPartRotation parseRotation(JsonObject jsonobject) {
        BlockPartRotation blockPartRotation = null;
        if (jsonobject.has("rotation")) {
            JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "rotation");
            Vector3f vector3f =
                    this.parsePosition(jsonobject1, "origin");
            vector3f.scale(0.0625F);
            FacingAxis facingAxis = this.parseAxis(jsonobject1);
            float f = this.parseAngle(jsonobject1);
            boolean flag = JsonUtils.getBoolean(jsonobject1, "rescale", false);
            blockPartRotation =
                    new BlockPartRotation(vector3f, facingAxis, f, flag);
        }

        return blockPartRotation;
    }

    private Vector3f parsePosition(
            JsonObject jsonobject, String text) {
        int k = BakedQuad.eR();
        JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, text);
        int i = k;
        if (jsonarray.size() != 3) {
            throw new JsonParseException(
                    "Expected 3 " + text + " values, found: " + jsonarray.size());
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

    private FacingAxis parseAxis(JsonObject jsonobject) {
        String text = JsonUtils.getString(jsonobject, "axis");
        FacingAxis facingAxis = FacingAxis.byName(text.toLowerCase());
        if (facingAxis == null) {
            throw new JsonParseException("Invalid rotation axis: " + text);
        } else {
            return facingAxis;
        }
    }

    private Facing parseEnumFacing(String text) {
        Facing facing = Facing.byName(text);
        if (facing == null) {
            throw new JsonParseException("Unknown facing: " + text);
        } else {
            return facing;
        }
    }

    private Vector3f parsePositionTo(JsonObject jsonobject) {
        Vector3f vector3f =
                this.parsePosition(jsonobject, "to");
        if (vector3f.x >= -16.0F
                && vector3f.y >= -16.0F
                && vector3f.z >= -16.0F
                && vector3f.x <= 32.0F
                && vector3f.y <= 32.0F
                && vector3f.z <= 32.0F) {
            return vector3f;
        } else {
            throw new JsonParseException(
                    "'to' specifier exceeds the allowed boundaries: " + vector3f);
        }
    }

    static {
        net.minecraft.client.renderer.block.model.BlockPartDeserializer.packedTextFragments = new String[4];
        net.minecraft.client.renderer.block.model.BlockPartDeserializer.packedTextFragments[0] = new String("[");
        net.minecraft.client.renderer.block.model.BlockPartDeserializer.packedTextFragments[1] = new String("]");
    }

    private Vector3f parsePositionFrom(JsonObject jsonobject) {
        Vector3f vector3f =
                this.parsePosition(jsonobject, "from");
        if (vector3f.x >= -16.0F
                && vector3f.y >= -16.0F
                && vector3f.z >= -16.0F
                && vector3f.x <= 32.0F
                && vector3f.y <= 32.0F
                && vector3f.z <= 32.0F) {
            return vector3f;
        } else {
            throw new JsonParseException(
                    "'from' specifier exceeds the allowed boundaries: "
                            + vector3f);
        }
    }

    private Map parseFacesCheck(
            JsonDeserializationContext jsondeserializationcontext, JsonObject jsonobject) {
        Map map = this.parseFaces(jsondeserializationcontext, jsonobject);
        if (map.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
        } else {
            return map;
        }
    }

    private Map parseFaces(
            JsonDeserializationContext jsondeserializationcontext, JsonObject jsonobject) {
        EnumMap enummap = Maps.newEnumMap(Facing.class);
        int j = BakedQuad.cM();
        JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "faces");
        int i = j;

        for (Entry entry : jsonobject1.entrySet()) {
            Facing facing = this.parseEnumFacing((String) entry.getKey());
            enummap.put(
                    facing,
                    (BlockPartFace)
                            jsondeserializationcontext.deserialize(
                                    (JsonElement) entry.getValue(),
                                    BlockPartFace.class));
            if (i == 0) {
                break;
            }
        }

        return enummap;
    }

    public BlockPart method_74774268cd51638e024c(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        Vector3f vector3f =
                this.parsePositionFrom(jsonobject);
        Vector3f vector3fValue =
                this.parsePositionTo(jsonobject);
        BlockPartRotation blockPartRotation =
                this.parseRotation(jsonobject);
        Map map = this.parseFacesCheck(jsondeserializationcontext, jsonobject);
        if (jsonobject.has("shade")
                && !JsonUtils.isBoolean(jsonobject, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
        }

        boolean flag = JsonUtils.getBoolean(jsonobject, "shade", true);
        return new BlockPart(
                vector3f,
                vector3fValue,
                map,
                blockPartRotation,
                flag);
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_74774268cd51638e024c(jsonelement, type, jsondeserializationcontext);
    }
}
