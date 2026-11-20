package net.minecraft.client.renderer.block.model;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ModelBlockDeserializer implements JsonDeserializer {

    private static String[] packedTextFragments;

    protected boolean getAmbientOcclusionEnabled(JsonObject jsonobject) {
        return JsonUtils.getBoolean(jsonobject, "ambientocclusion", true);
    }

    private Map getTextures(JsonObject jsonobject) {
        int j = BakedQuad.cM();
        HashMap hashmap = Maps.newHashMap();
        int i = j;
        if (jsonobject.has("textures")) {
            JsonObject jsonobject1 = jsonobject.getAsJsonObject("textures");

            for (Entry entry : jsonobject1.entrySet()) {
                hashmap.put(entry.getKey(), ((JsonElement) entry.getValue()).getAsString());
                if (i == 0) {
                    break;
                }
            }
        }

        return hashmap;
    }

    protected List getModelElements(
            JsonDeserializationContext jsondeserializationcontext, JsonObject jsonobject) {
        int j = BakedQuad.eR();
        ArrayList arrayList = Lists.newArrayList();
        int i = j;
        if (jsonobject.has("elements")) {
            for (JsonElement jsonelement : JsonUtils.getJsonArray(jsonobject, "elements")) {
                arrayList.add(
                        (BlockPart)
                                jsondeserializationcontext.deserialize(
                                        jsonelement, BlockPart.class));
                if (i != 0) {
                    break;
                }
            }
        }

        return arrayList;
    }

    static {
        net.minecraft.client.renderer.block.model.ModelBlockDeserializer.packedTextFragments = new String[3];
        net.minecraft.client.renderer.block.model.ModelBlockDeserializer.packedTextFragments[0] = new String("");
    }

    private String getParent(JsonObject jsonobject) {
        return JsonUtils.getString(jsonobject, "parent", packedTextFragments[0]);
    }

    public ModelBlock method_c627fe4c1046fa8f1236(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        List list = this.getModelElements(jsondeserializationcontext, jsonobject);
        String text = this.getParent(jsonobject);
        boolean empty = StringUtils.isEmpty(text);
        boolean emptyValue = list.isEmpty();
        if (emptyValue && empty) {
            throw new JsonParseException(
                    "BlockModel requires either elements or parent, found neither");
        }

        if (!empty && !emptyValue) {
            throw new JsonParseException(
                    "BlockModel requires either elements or parent, found both");
        }

        Map map = this.getTextures(jsonobject);
        boolean flag2 = this.getAmbientOcclusionEnabled(jsonobject);
        ItemCameraTransforms ayU = ItemCameraTransforms.DEFAULT;
        if (jsonobject.has("display")) {
            JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "display");
            ayU =
                    (ItemCameraTransforms)
                            jsondeserializationcontext.deserialize(
                                    jsonobject1, ItemCameraTransforms.class);
        }

        return emptyValue
                ? new ModelBlock(new ResourceLocation(text), map, flag2, true, ayU)
                : new ModelBlock(list, map, flag2, true, ayU);
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_c627fe4c1046fa8f1236(jsonelement, type, jsondeserializationcontext);
    }
}
