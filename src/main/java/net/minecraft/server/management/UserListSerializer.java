package net.minecraft.server.management;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class UserListSerializer implements JsonDeserializer, JsonSerializer {
    UserList byc;

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.deserializeEntry(jsonelement, type, jsondeserializationcontext);
    }

    private UserListSerializer(UserList bycValue) {
        this.byc = bycValue;
    }

    public JsonElement serializeEntry(
            UserListEntry userListEntry,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();
        userListEntry.onSerialization(jsonobject);
        return jsonobject;
    }

    public UserListEntry deserializeEntry(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            return this.byc.createEntry(jsonobject);
        } else {
            return null;
        }
    }

    UserListSerializer(UserList userList, Object object) {
        this(userList);
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serializeEntry(
                (UserListEntry) object, type, jsonserializationcontext);
    }
}
