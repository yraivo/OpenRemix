package net.minecraft.client.renderer.block.model;

import net.minecraft.util.JsonUtils;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class ModelBlockDefinitionDeserializer implements JsonDeserializer {
    private static String a;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected List parseVariantsList(
            JsonDeserializationContext jsondeserializationcontext, JsonObject jsonobject) {
        int j = BakedQuad.cM();
        JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, a);
        ArrayList arrayList = Lists.newArrayList();
        int i = j;

        for (Entry entry : jsonobject1.entrySet()) {
            arrayList.add(this.parseVariants(jsondeserializationcontext, entry));
            if (i == 0) {
                break;
            }
        }

        return arrayList;
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_87cd9dd281d6373ebcef(jsonelement, type, jsondeserializationcontext);
    }

    protected ModelBlockDefinitionVariants parseVariants(
            JsonDeserializationContext jsondeserializationcontext, Entry entry) {
        int j = BakedQuad.eR();
        String key = (String) entry.getKey();
        int i = j;
        ArrayList arrayList = Lists.newArrayList();
        JsonElement jsonelement = (JsonElement) entry.getValue();
        if (jsonelement.isJsonArray()) {
            for (JsonElement jsonelement1 : jsonelement.getAsJsonArray()) {
                arrayList.add(
                        (ModelBlockDefinitionVariant)
                                jsondeserializationcontext.deserialize(
                                        jsonelement1, ModelBlockDefinitionVariant.class));
                if (i != 0) {
                    break;
                }
            }

            if (i == 0) {
                return new ModelBlockDefinitionVariants(key, arrayList);
            }
        }

        arrayList.add(
                (ModelBlockDefinitionVariant)
                        jsondeserializationcontext.deserialize(
                                jsonelement, ModelBlockDefinitionVariant.class));
        return new ModelBlockDefinitionVariants(key, arrayList);
    }

    static {
        a = "variants";
    }

    public ModelBlockDefinition method_87cd9dd281d6373ebcef(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        List list = this.parseVariantsList(jsondeserializationcontext, jsonobject);
        return new ModelBlockDefinition((java.util.Collection) list);
    }
}
