package net.minecraft.server.management;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

class PlayerProfileCacheSerializer implements JsonDeserializer, JsonSerializer {
    PlayerProfileCache bzn;
    private static String[] packedTextFragments;

    public PlayerProfileCacheProfileEntry method_f717c0c4532056426920(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            JsonElement jsonelement1 = jsonobject.get("name");
            JsonElement jsonelement2 = jsonobject.get("uuid");
            JsonElement jsonelement3 = jsonobject.get("expiresOn");

            try {
                if (false)
                    throw (java.text.ParseException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (jsonelement1 == null || jsonelement2 == null) {
                    return null;
                }
            } catch (ParseException parseexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_e5ed422eec646371f3de(parseexception2));
            }

            String asString = jsonelement2.getAsString();
            String asStringValue = jsonelement1.getAsString();
            Date date = null;
            if (jsonelement3 != null) {
                try {
                    date = PlayerProfileCache.dateFormat.parse(jsonelement3.getAsString());
                } catch (ParseException parseexception) {
                    date = null;
                }
            }

            try {
                if (false)
                    throw (java.text.ParseException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (asStringValue == null || asString == null) {
                    return null;
                }
            } catch (ParseException parseexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_e5ed422eec646371f3de(parseexception1));
            }

            UUID uuid;
            try {
                uuid = UUID.fromString(asString);
            } catch (Throwable throwable) {
                return null;
            }

            PlayerProfileCache bznValue = this.bzn;
            this.bzn.getClass();
            return new PlayerProfileCacheProfileEntry(
                    bznValue, new GameProfile(uuid, asStringValue), date, null);
        } else {
            return null;
        }
    }

    static {
        net.minecraft.server.management.PlayerProfileCacheSerializer.packedTextFragments =
                new String[3];
        net.minecraft.server.management.PlayerProfileCacheSerializer.packedTextFragments[0] =
                new String("");
    }

    private static ParseException method_e5ed422eec646371f3de(ParseException parseexception) {
        return parseexception;
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_f717c0c4532056426920(jsonelement, type, jsondeserializationcontext);
    }

    PlayerProfileCacheSerializer(PlayerProfileCache playerProfileCache, Object object) {
        this(playerProfileCache);
    }

    private PlayerProfileCacheSerializer(PlayerProfileCache bznValue) {
        this.bzn = bznValue;
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.method_163b6da1c61cf4b05ef9(
                (PlayerProfileCacheProfileEntry) object, type, jsonserializationcontext);
    }

    public JsonElement method_163b6da1c61cf4b05ef9(
            PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("name", playerProfileCacheProfileEntry.getGameProfile().getName());
        UUID uuid = playerProfileCacheProfileEntry.getGameProfile().getId();
        jsonobject.addProperty("uuid", uuid == null ? packedTextFragments[0] : uuid.toString());
        jsonobject.addProperty(
                "expiresOn",
                PlayerProfileCache.dateFormat.format(
                        playerProfileCacheProfileEntry.getExpirationDate()));
        return jsonobject;
    }
}
