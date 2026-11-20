package net.minecraft.network;

import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ServerStatusResponseMinecraftProtocolVersionIdentifierSerializer implements JsonDeserializer, JsonSerializer {


    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.deserializeServerStatusResponseMinecraftProtocolVersionIdentifier(jsonelement, type, jsondeserializationcontext);
    }

    public JsonElement serialize(
            ServerStatusResponseMinecraftProtocolVersionIdentifier
                    serverStatusResponseMinecraftProtocolVersionIdentifier,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty(
                "name", serverStatusResponseMinecraftProtocolVersionIdentifier.getName());
        jsonobject.addProperty(
                "protocol", serverStatusResponseMinecraftProtocolVersionIdentifier.getProtocol());
        return jsonobject;
    }

    public ServerStatusResponseMinecraftProtocolVersionIdentifier deserializeServerStatusResponseMinecraftProtocolVersionIdentifier(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "version");
        return new ServerStatusResponseMinecraftProtocolVersionIdentifier(
                JsonUtils.getString(jsonobject, "name"), JsonUtils.getInt(jsonobject, "protocol"));
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize(
                (ServerStatusResponseMinecraftProtocolVersionIdentifier) object,
                type,
                jsonserializationcontext);
    }
}
