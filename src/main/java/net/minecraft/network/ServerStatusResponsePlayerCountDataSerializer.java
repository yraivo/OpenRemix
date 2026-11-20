package net.minecraft.network;

import net.minecraft.util.JsonUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;

import java.lang.reflect.Type;
import java.util.UUID;

public class ServerStatusResponsePlayerCountDataSerializer
        implements JsonDeserializer, JsonSerializer {

    private static String[] packedTextFragments;

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize(
                (ServerStatusResponsePlayerCountData) object, type, jsonserializationcontext);
    }

    public JsonElement serialize(
            ServerStatusResponsePlayerCountData serverStatusResponsePlayerCountData,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        JsonObject jsonobject = new JsonObject();
        String textValue = text;
        jsonobject.addProperty("max", serverStatusResponsePlayerCountData.getMaxPlayers());
        jsonobject.addProperty(
                "online", serverStatusResponsePlayerCountData.getOnlinePlayerCount());
        if (serverStatusResponsePlayerCountData.getPlayers() != null
                && serverStatusResponsePlayerCountData.getPlayers().length > 0) {
            JsonArray jsonarray = new JsonArray();
            int i = 0;

            while (i < serverStatusResponsePlayerCountData.getPlayers().length) {
                JsonObject jsonobject1 = new JsonObject();
                UUID uuid = serverStatusResponsePlayerCountData.getPlayers()[i].getId();
                jsonobject1.addProperty(
                        "id", uuid == null ? packedTextFragments[1] : uuid.toString());
                jsonobject1.addProperty(
                        "name", serverStatusResponsePlayerCountData.getPlayers()[i].getName());
                jsonarray.add(jsonobject1);
                i++;
                if (textValue == null) {
                    break;
                }
            }

            jsonobject.add("sample", jsonarray);
        }

        return jsonobject;
    }

    static {
        net.minecraft.network.ServerStatusResponsePlayerCountDataSerializer.packedTextFragments = new String[4];
        net.minecraft.network.ServerStatusResponsePlayerCountDataSerializer.packedTextFragments[0] =
                new String("]");
        net.minecraft.network.ServerStatusResponsePlayerCountDataSerializer.packedTextFragments[1] = new String("");
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.deserializeServerStatusResponsePlayerCountData(jsonelement, type, jsondeserializationcontext);
    }

    public ServerStatusResponsePlayerCountData deserializeServerStatusResponsePlayerCountData(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        String text = PacketBuffer.getLegacyPacketBufferFlowMarker();
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "players");
        String textValue = text;
        ServerStatusResponsePlayerCountData serverStatusResponsePlayerCountData =
                new ServerStatusResponsePlayerCountData(
                        JsonUtils.getInt(jsonobject, "max"),
                        JsonUtils.getInt(jsonobject, "online"));
        if (JsonUtils.method_6f269b44202e870a5c79(jsonobject, "sample")) {
            JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sample");
            if (jsonarray.size() > 0) {
                GameProfile[] agameprofile = new GameProfile[jsonarray.size()];
                int i = 0;

                while (i < agameprofile.length) {
                    JsonObject jsonobject1 =
                            JsonUtils.getJsonObject(
                                    jsonarray.get(i), "player[" + i + packedTextFragments[0]);
                    String textValue2 = JsonUtils.getString(jsonobject1, "id");
                    agameprofile[i] =
                            new GameProfile(
                                    UUID.fromString(textValue2),
                                    JsonUtils.getString(jsonobject1, "name"));
                    i++;
                    if (textValue == null) {
                        break;
                    }
                }

                serverStatusResponsePlayerCountData.setPlayers(agameprofile);
            }
        }

        return serverStatusResponsePlayerCountData;
    }
}
