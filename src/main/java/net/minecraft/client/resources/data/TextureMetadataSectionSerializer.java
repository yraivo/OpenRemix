package net.minecraft.client.resources.data;

import net.minecraft.util.JsonUtils;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer {

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializeTextureMetadataSection(jsonelement, type, jsondeserializationcontext);
    }

    public TextureMetadataSection deserializeTextureMetadataSection(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        boolean flag3 = BaseMetadataSectionSerializer.method_d87039b07bd6961907f2();
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        boolean flag = flag3;
        boolean flag1 = JsonUtils.getBoolean(jsonobject, "blur", false);
        boolean flag2 = JsonUtils.getBoolean(jsonobject, "clamp", false);
        ArrayList arrayList = Lists.newArrayList();
        if (jsonobject.has("mipmaps")) {
            try {
                JsonArray jsonarray = jsonobject.getAsJsonArray("mipmaps");
                int i = 0;

                while (i < jsonarray.size()) {
                    JsonElement jsonelement1 = jsonarray.get(i);

                    label42:
                    {
                        try {
                            if (jsonelement1.isJsonPrimitive()) {
                                try {
                                    arrayList.add(jsonelement1.getAsInt());
                                    break label42;
                                } catch (NumberFormatException numberformatexception1) {
                                    throw new JsonParseException(
                                            "Invalid texture->mipmap->"
                                                    + i
                                                    + ": expected number, was "
                                                    + jsonelement1,
                                            numberformatexception1);
                                }
                            }
                        } catch (ClassCastException classcastexception) {
                            throw method_1a1f08aee5fe72868e61(classcastexception);
                        }

                        try {
                            if (jsonelement1.isJsonObject()) {
                                throw new JsonParseException(
                                        "Invalid texture->mipmap->"
                                                + i
                                                + ": expected number, was "
                                                + jsonelement1);
                            }
                        } catch (NumberFormatException numberformatexception) {
                            throw method_1a1f08aee5fe72868e61(numberformatexception);
                        }
                    }

                    i++;
                    if (!flag) {
                        break;
                    }
                }
            } catch (ClassCastException classcastexception1) {
                throw new JsonParseException(
                        "Invalid texture->mipmaps: expected array, was "
                                + jsonobject.get("mipmaps"),
                        classcastexception1);
            }
        }

        return new TextureMetadataSection(flag1, flag2, arrayList);
    }

    public String getSectionName() {
        return "texture";
    }


    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }
}
