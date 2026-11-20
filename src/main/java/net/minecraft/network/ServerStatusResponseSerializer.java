package net.minecraft.network;

import net.minecraft.util.ChatComponent;
import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ServerStatusResponseSerializer implements JsonDeserializer, JsonSerializer {

    public JsonElement serialize(
            ServerStatusResponse serverStatusResponse,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();
        if (serverStatusResponse.getServerDescription() != null) {
            jsonobject.add(
                    "description",
                    jsonserializationcontext.serialize(
                            serverStatusResponse.getServerDescription()));
        }

        if (serverStatusResponse.getPlayerCountData() != null) {
            jsonobject.add(
                    "players",
                    jsonserializationcontext.serialize(serverStatusResponse.getPlayerCountData()));
        }

        if (serverStatusResponse.getProtocolVersionInfo() != null) {
            jsonobject.add(
                    "version",
                    jsonserializationcontext.serialize(
                            serverStatusResponse.getProtocolVersionInfo()));
        }

        if (serverStatusResponse.getFavicon() != null) {
            jsonobject.addProperty("favicon", serverStatusResponse.getFavicon());
        }

        return jsonobject;
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize((ServerStatusResponse) object, type, jsonserializationcontext);
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.deserializeServerStatusResponse(jsonelement, type, jsondeserializationcontext);
    }


    public ServerStatusResponse deserializeServerStatusResponse(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "status");
        ServerStatusResponse serverStatusResponse = new ServerStatusResponse();
        if (jsonobject.has("description")) {
            serverStatusResponse.setServerDescription(
                    (ChatComponent)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("description"), ChatComponent.class));
        }

        if (jsonobject.has("players")) {
            serverStatusResponse.setPlayerCountData(
                    (ServerStatusResponsePlayerCountData)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("players"),
                                    ServerStatusResponsePlayerCountData.class));
        }

        if (jsonobject.has("version")) {
            serverStatusResponse.setProtocolVersionInfo(
                    (ServerStatusResponseMinecraftProtocolVersionIdentifier)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("version"),
                                    ServerStatusResponseMinecraftProtocolVersionIdentifier.class));
        }

        if (jsonobject.has("favicon")) {
            serverStatusResponse.setFavicon(JsonUtils.getString(jsonobject, "favicon"));
        }

        return serverStatusResponse;
    }
}
