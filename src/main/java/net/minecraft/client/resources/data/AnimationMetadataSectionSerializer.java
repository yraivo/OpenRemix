package net.minecraft.client.resources.data;

import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer
        implements JsonSerializer {
    private static String[] packedTextFragments;

    static {
        net.minecraft.client.resources.data.AnimationMetadataSectionSerializer.packedTextFragments = new String[4];
        net.minecraft.client.resources.data.AnimationMetadataSectionSerializer.packedTextFragments[0] = new String("]");
        net.minecraft.client.resources.data.AnimationMetadataSectionSerializer.packedTextFragments[1] = new String("]");
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize(
                (AnimationMetadataSection) object, type, jsonserializationcontext);
    }

    public String getSectionName() {
        return "animation";
    }

    public JsonElement serialize(
            AnimationMetadataSection animationMetadataSection,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        boolean flag1 = BaseMetadataSectionSerializer.method_d87039b07bd6961907f2();
        JsonObject jsonobject = new JsonObject();
        boolean flag = flag1;

        try {
            jsonobject.addProperty("frametime", animationMetadataSection.getFrameTime());
            if (animationMetadataSection.getFrameWidth() != -1) {
                jsonobject.addProperty("width", animationMetadataSection.getFrameWidth());
            }
        } catch (ClassCastException classcastexception3) {
            throw method_b4651e2fc58714ff8e18(classcastexception3);
        }

        try {
            if (animationMetadataSection.getFrameHeight() != -1) {
                jsonobject.addProperty("height", animationMetadataSection.getFrameHeight());
            }
        } catch (ClassCastException classcastexception1) {
            throw method_b4651e2fc58714ff8e18(classcastexception1);
        }

        if (animationMetadataSection.getFrameCount() > 0) {
            JsonArray jsonarray = new JsonArray();
            int i = 0;

            while (i < animationMetadataSection.getFrameCount()) {
                label42:
                {
                    if (animationMetadataSection.frameHasTime(i)) {
                        JsonObject jsonobject1 = new JsonObject();

                        try {
                            jsonobject1.addProperty(
                                    "index", animationMetadataSection.getFrameIndex(i));
                            jsonobject1.addProperty(
                                    "time",
                                    animationMetadataSection.method_46b7d87f71f96e2a0dba(i));
                            jsonarray.add(jsonobject1);
                            if (flag) {
                                break label42;
                            }
                        } catch (ClassCastException classcastexception2) {
                            throw method_b4651e2fc58714ff8e18(classcastexception2);
                        }
                    }

                    try {
                        jsonarray.add(new JsonPrimitive(animationMetadataSection.getFrameIndex(i)));
                    } catch (ClassCastException classcastexception) {
                        throw method_b4651e2fc58714ff8e18(classcastexception);
                    }
                }

                i++;
                if (!flag) {
                    break;
                }
            }

            jsonobject.add("frames", jsonarray);
        }

        return jsonobject;
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializeAnimationMetadataSection(jsonelement, type, jsondeserializationcontext);
    }

    private AnimationFrame parseAnimationFrame(int i, JsonElement jsonelement) {
        try {
            if (jsonelement.isJsonPrimitive()) {
                return new AnimationFrame(
                        JsonUtils.getInt(jsonelement, "frames[" + i + packedTextFragments[0]));
            }
        } catch (ClassCastException classcastexception) {
            throw method_b4651e2fc58714ff8e18(classcastexception);
        }

        if (jsonelement.isJsonObject()) {
            JsonObject jsonobject =
                    JsonUtils.getJsonObject(jsonelement, "frames[" + i + packedTextFragments[1]);
            int j = JsonUtils.getInt(jsonobject, "time", -1);

            try {
                if (jsonobject.has("time")) {

                    long k1 = 1L;

                    Validate.inclusiveBetween(k1, 2147483647L, j, "Invalid frame time");
                }
            } catch (ClassCastException classcastexception1) {
                throw method_b4651e2fc58714ff8e18(classcastexception1);
            }

            int k = JsonUtils.getInt(jsonobject, "index");

            long l = 0L;

            Validate.inclusiveBetween(l, 2147483647L, k, "Invalid frame index");
            return new AnimationFrame(k, j);
        } else {
            return null;
        }
    }

    private static ClassCastException method_b4651e2fc58714ff8e18(
            ClassCastException classcastexception) {
        return classcastexception;
    }

    public AnimationMetadataSection deserializeAnimationMetadataSection(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        boolean flag2 = BaseMetadataSectionSerializer.method_b417d7500ec59d69e373();
        ArrayList arrayList = Lists.newArrayList();
        boolean flag = flag2;
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "metadata section");
        int i = JsonUtils.getInt(jsonobject, "frametime", 1);

        try {
            if (i != 1) {

                long i1 = 1L;

                Validate.inclusiveBetween(i1, 2147483647L, i, "Invalid default frame time");
            }
        } catch (ClassCastException classcastexception2) {
            throw method_b4651e2fc58714ff8e18(classcastexception2);
        }

        if (jsonobject.has("frames")) {
            try {
                JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "frames");
                int j = 0;

                while (j < jsonarray.size()) {
                    JsonElement jsonelement1 = jsonarray.get(j);
                    AnimationFrame animationFrame =
                            this.parseAnimationFrame(j, jsonelement1);

                    try {
                        if (animationFrame != null) {
                            arrayList.add(animationFrame);
                        }
                    } catch (ClassCastException classcastexception1) {
                        throw method_b4651e2fc58714ff8e18(classcastexception1);
                    }

                    j++;
                    if (flag) {
                        Entity.setLegacyGlobalFlowMarker(new String[5]);
                        break;
                    }
                }
            } catch (ClassCastException classcastexception4) {
                throw new JsonParseException(
                        "Invalid animation->frames: expected array, was "
                                + jsonobject.get("frames"),
                        classcastexception4);
            }
        }

        int l = JsonUtils.getInt(jsonobject, "width", -1);
        int k = JsonUtils.getInt(jsonobject, "height", -1);

        try {
            if (l != -1) {

                long j1 = 1L;

                Validate.inclusiveBetween(j1, 2147483647L, l, "Invalid width");
            }
        } catch (ClassCastException classcastexception) {
            throw method_b4651e2fc58714ff8e18(classcastexception);
        }

        try {
            if (k != -1) {

                long k1 = 1L;

                Validate.inclusiveBetween(k1, 2147483647L, k, "Invalid height");
            }
        } catch (ClassCastException classcastexception3) {
            throw method_b4651e2fc58714ff8e18(classcastexception3);
        }

        boolean flag1 = JsonUtils.getBoolean(jsonobject, "interpolate", false);
        return new AnimationMetadataSection(arrayList, l, k, i, flag1);
    }
}
