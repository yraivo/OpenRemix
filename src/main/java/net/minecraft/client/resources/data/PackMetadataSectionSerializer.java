package net.minecraft.client.resources.data;

import net.minecraft.util.ChatComponent;
import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer
        implements JsonSerializer {

    public JsonElement serialize(
            PackMetadataSection packMetadataSection,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("pack_format", packMetadataSection.getPackFormat());
        jsonobject.add(
                "description",
                jsonserializationcontext.serialize(packMetadataSection.getPackDescription()));
        return jsonobject;
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public PackMetadataSection deserializePackMetadataSection(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        ChatComponent chatComponent =
                (ChatComponent)
                        jsondeserializationcontext.deserialize(
                                jsonobject.get("description"), ChatComponent.class);

        try {
            if (chatComponent == null) {
                throw new JsonParseException("Invalid/missing description!");
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        int intValue = JsonUtils.getInt(jsonobject, "pack_format");
        return new PackMetadataSection(chatComponent, intValue);
    }

    public String getSectionName() {
        return "pack";
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize((PackMetadataSection) object, type, jsonserializationcontext);
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializePackMetadataSection(jsonelement, type, jsondeserializationcontext);
    }


}
