package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map.Entry;

public class IChatComponentSerializer implements JsonDeserializer, JsonSerializer {
    private static Gson GSON;

    public static ChatComponent jsonToComponent(String text) {
        return (ChatComponent)
                net.minecraft.util.IChatComponentSerializer.GSON.fromJson(text, ChatComponent.class);
    }

    private void serializeChatStyle(
            ChatStyle chatStyle,
            JsonObject jsonobject,
            JsonSerializationContext jsonserializationcontext) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        JsonElement jsonelement = jsonserializationcontext.serialize(chatStyle);
        String textValue = text;
        if (jsonelement.isJsonObject()) {
            JsonObject jsonobject1 = (JsonObject) jsonelement;

            for (java.util.Iterator<?> __recoveryIterator171_23203 =
                            (jsonobject1.entrySet()).iterator();
                    (__recoveryIterator171_23203).hasNext(); ) {
                Entry entry = (Entry) (__recoveryIterator171_23203).next();
                jsonobject.add((String) entry.getKey(), (JsonElement) entry.getValue());
                if (textValue == null) {
                    break;
                }
            }
        }
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializeChatComponent(jsonelement, type, jsondeserializationcontext);
    }

    static {
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeHierarchyAdapter(
                ChatComponent.class, new IChatComponentSerializer());
        gsonbuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyleSerializer());
        gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
        net.minecraft.util.IChatComponentSerializer.GSON = gsonbuilder.create();
    }

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize(
                (ChatComponent) object, type, jsonserializationcontext);
    }

    public static String componentToJson(ChatComponent chatComponent) {
        return net.minecraft.util.IChatComponentSerializer.GSON.toJson(chatComponent);
    }

    public JsonElement serialize(
            ChatComponent chatComponent,
            Type type,
            JsonSerializationContext jsonserializationcontext) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (chatComponent instanceof ChatComponentText
                && chatComponent.getChatStyle().isEmpty()
                && chatComponent.getSiblings().isEmpty()) {
            return new JsonPrimitive(
                    ((ChatComponentText) chatComponent).getChatComponentText_TextValue());
        }

        JsonObject jsonobject = new JsonObject();
        if (!chatComponent.getChatStyle().isEmpty()) {
            this.serializeChatStyle(
                    chatComponent.getChatStyle(), jsonobject, jsonserializationcontext);
        }

        if (!chatComponent.getSiblings().isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for (java.util.Iterator<?> __recoveryIterator171_29034 =
                            (chatComponent.getSiblings()).iterator();
                    (__recoveryIterator171_29034).hasNext(); ) {
                ChatComponent chatComponentValue =
                        (ChatComponent) (__recoveryIterator171_29034).next();
                jsonarray.add(
                        this.serialize(
                                chatComponentValue,
                                chatComponentValue.getClass(),
                                jsonserializationcontext));
                if (text == null) {
                    break;
                }
            }

            jsonobject.add("extra", jsonarray);
        }

        if (chatComponent instanceof ChatComponentText) {
            jsonobject.addProperty(
                    "text", ((ChatComponentText) chatComponent).getChatComponentText_TextValue());
            if (text != null) {
                return jsonobject;
            }
        }

        if (chatComponent instanceof ChatComponentTranslation) {
            ChatComponentTranslation chatComponentTranslation =
                    (ChatComponentTranslation) chatComponent;
            jsonobject.addProperty("translate", chatComponentTranslation.getKey());
            if (chatComponentTranslation.getFormatArgs() != null
                    && chatComponentTranslation.getFormatArgs().length > 0) {
                JsonArray jsonarray1 = new JsonArray();

                for (Object object : chatComponentTranslation.getFormatArgs()) {
                    {
                        if (object instanceof ChatComponent) {
                            jsonarray1.add(
                                    this.serialize(
                                            (ChatComponent) object,
                                            object.getClass(),
                                            jsonserializationcontext));
                            if (!(text != null)) {

                                jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                            }
                        } else {

                            jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                        }
                    }

                    if (text == null) {
                        break;
                    }
                }

                jsonobject.add("with", jsonarray1);
            }

            if (text != null) {
                return jsonobject;
            }
        }

        if (chatComponent instanceof ChatComponentScore) {
            ChatComponentScore chatComponentScore = (ChatComponentScore) chatComponent;
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("name", chatComponentScore.getName());
            jsonobject1.addProperty("objective", chatComponentScore.getObjective());
            jsonobject1.addProperty("value", chatComponentScore.getUnformattedTextForChat());
            jsonobject.add("score", jsonobject1);
            if (text != null) {
                return jsonobject;
            }
        }

        if (!(chatComponent instanceof ChatComponentSelector)) {
            throw new IllegalArgumentException(
                    "Don't know how to serialize " + chatComponent + " as a Component");
        }

        ChatComponentSelector chatComponentSelector =
                (ChatComponentSelector) chatComponent;
        jsonobject.addProperty(
                "selector", chatComponentSelector.getSelector());
        return jsonobject;
    }

    public ChatComponent deserializeChatComponent(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (jsonelement.isJsonPrimitive()) {
            return new ChatComponentText(jsonelement.getAsString());
        }

        if (!jsonelement.isJsonObject()) {
            if (!jsonelement.isJsonArray()) {
                throw new JsonParseException(
                        "Don't know how to turn " + jsonelement.toString() + " into a Component");
            }

            JsonArray jsonarray1 = jsonelement.getAsJsonArray();
            ChatComponent chatComponent = null;

            for (java.util.Iterator<?> __recoveryIterator171_32836 = (jsonarray1).iterator();
                    (__recoveryIterator171_32836).hasNext(); ) {
                JsonElement jsonelement1 = (JsonElement) (__recoveryIterator171_32836).next();
                {
                    ChatComponent chatComponentValue =
                            this.deserializeChatComponent(
                                    jsonelement1,
                                    jsonelement1.getClass(),
                                    jsondeserializationcontext);
                    if (chatComponent == null) {
                        chatComponent = chatComponentValue;
                        if (!(text != null)) {

                            chatComponent.appendSibling(chatComponentValue);
                        }
                    } else {

                        chatComponent.appendSibling(chatComponentValue);
                    }
                }

                if (text == null) {
                    break;
                }
            }

            return chatComponent;
        } else {
            JsonObject jsonobject;
            Object object;
            label124:
            {
                jsonobject = jsonelement.getAsJsonObject();
                if (jsonobject.has("text")) {
                    object = new ChatComponentText(jsonobject.get("text").getAsString());
                    if (text != null) {
                        break label124;
                    }
                }

                if (jsonobject.has("translate")) {
                    {
                        String asString = jsonobject.get("translate").getAsString();
                        if (jsonobject.has("with")) {
                            JsonArray jsonarray = jsonobject.getAsJsonArray("with");
                            Object[] aobject = new Object[jsonarray.size()];
                            int i = 0;

                            while (i < aobject.length) {
                                aobject[i] =
                                        this.deserializeChatComponent(
                                                jsonarray.get(i), type, jsondeserializationcontext);
                                if (aobject[i] instanceof ChatComponentText) {
                                    ChatComponentText chatComponentText =
                                            (ChatComponentText) aobject[i];
                                    if (chatComponentText.getChatStyle().isEmpty()
                                            && chatComponentText.getSiblings().isEmpty()) {
                                        aobject[i] =
                                                chatComponentText.getChatComponentText_TextValue();
                                    }
                                }

                                i++;
                                if (text == null) {
                                    break;
                                }
                            }

                            object = new ChatComponentTranslation(asString, aobject);
                            if (!(text != null)) {

                                object = new ChatComponentTranslation(asString, new Object[0]);
                            }
                        } else {

                            object = new ChatComponentTranslation(asString, new Object[0]);
                        }
                    }

                    if (text != null) {
                        break label124;
                    }
                }

                if (jsonobject.has("score")) {
                    JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
                    if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                        throw new JsonParseException(
                                "A score component needs a least a name and an objective");
                    }

                    object =
                            new ChatComponentScore(
                                    JsonUtils.getString(jsonobject1, "name"),
                                    JsonUtils.getString(jsonobject1, "objective"));
                    if (jsonobject1.has("value")) {
                        ((ChatComponentScore) object)
                                .setValue(JsonUtils.getString(jsonobject1, "value"));
                    }

                    if (text != null) {
                        break label124;
                    }
                }

                if (!jsonobject.has("selector")) {
                    throw new JsonParseException(
                            "Don't know how to turn "
                                    + jsonelement.toString()
                                    + " into a Component");
                }

                object =
                        new ChatComponentSelector(JsonUtils.getString(jsonobject, "selector"));
            }

            if (jsonobject.has("extra")) {
                JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
                if (jsonarray2.size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }

                int j = 0;

                while (j < jsonarray2.size()) {
                    ((ChatComponent) object)
                            .appendSibling(
                                    this.deserializeChatComponent(
                                            jsonarray2.get(j), type, jsondeserializationcontext));
                    j++;
                    if (text == null) {
                        break;
                    }
                }
            }

            ((ChatComponent) object)
                    .setChatStyle(
                            (ChatStyle)
                                    jsondeserializationcontext.deserialize(
                                            jsonelement, ChatStyle.class));
            return (ChatComponent) object;
        }
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }
}
