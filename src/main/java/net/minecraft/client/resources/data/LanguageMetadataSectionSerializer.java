package net.minecraft.client.resources.data;

import net.minecraft.client.resources.Language;
import net.minecraft.util.JsonUtils;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map.Entry;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer {

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public String getSectionName() {
        return "language";
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializeLanguageMetadataSection(jsonelement, type, jsondeserializationcontext);
    }


    public LanguageMetadataSection deserializeLanguageMetadataSection(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        HashSet hashset = Sets.newHashSet();

        for (Entry entry : jsonobject.entrySet()) {
            String key = (String) entry.getKey();
            JsonObject jsonobject1 =
                    JsonUtils.getJsonObject((JsonElement) entry.getValue(), "language");
            String text = JsonUtils.getString(jsonobject1, "region");
            String textValue = JsonUtils.getString(jsonobject1, "name");
            boolean flag =
                    JsonUtils.getBoolean(jsonobject1, "bidirectional", false);

            try {
                if (text.isEmpty()) {
                    throw new JsonParseException(
                            "Invalid language->'" + key + "'->region: empty value");
                }
            } catch (JsonParseException jsonparseexception) {
                throw method_a7757c6047e9e5ecea97(jsonparseexception);
            }

            try {
                if (textValue.isEmpty()) {
                    throw new JsonParseException(
                            "Invalid language->'" + key + "'->name: empty value");
                }
            } catch (JsonParseException jsonparseexception1) {
                throw method_a7757c6047e9e5ecea97(jsonparseexception1);
            }

            if (!hashset.add(new Language(key, text, textValue, flag))) {
                throw new JsonParseException("Duplicate language->'" + key + "' defined");
            }
        }

        return new LanguageMetadataSection(hashset);
    }
}
