package net.minecraft.util;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ChatStyleSerializer implements JsonDeserializer, JsonSerializer {

    public JsonElement serialize(
            Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.serialize((ChatStyle) object, type, jsonserializationcontext);
    }

    public JsonElement serialize(
            ChatStyle chatStyle, Type type, JsonSerializationContext jsonserializationcontext) {
        if (chatStyle.isEmpty()) {
            return null;
        }

        JsonObject jsonobject = new JsonObject();
        if (ChatStyle.access$000(chatStyle) != null) {
            jsonobject.addProperty("bold", ChatStyle.access$000(chatStyle));
        }

        if (ChatStyle.access$100(chatStyle) != null) {
            jsonobject.addProperty("italic", ChatStyle.access$100(chatStyle));
        }

        if (ChatStyle.access$200(chatStyle) != null) {
            jsonobject.addProperty("underlined", ChatStyle.access$200(chatStyle));
        }

        if (ChatStyle.access$300(chatStyle) != null) {
            jsonobject.addProperty("strikethrough", ChatStyle.access$300(chatStyle));
        }

        if (ChatStyle.access$400(chatStyle) != null) {
            jsonobject.addProperty("obfuscated", ChatStyle.access$400(chatStyle));
        }

        if (ChatStyle.access$500(chatStyle) != null) {
            jsonobject.add(
                    "color", jsonserializationcontext.serialize(ChatStyle.access$500(chatStyle)));
        }

        if (ChatStyle.access$600(chatStyle) != null) {
            jsonobject.add(
                    "insertion",
                    jsonserializationcontext.serialize(ChatStyle.access$600(chatStyle)));
        }

        if (ChatStyle.access$700(chatStyle) != null) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty(
                    "action", ChatStyle.access$700(chatStyle).getAction().getCanonicalName());
            jsonobject1.addProperty("value", ChatStyle.access$700(chatStyle).getValue());
            jsonobject.add("clickEvent", jsonobject1);
        }

        if (ChatStyle.access$800(chatStyle) != null) {
            JsonObject jsonobject2 = new JsonObject();
            jsonobject2.addProperty(
                    "action", ChatStyle.access$800(chatStyle).getAction().getCanonicalName());
            jsonobject2.add(
                    "value",
                    jsonserializationcontext.serialize(ChatStyle.access$800(chatStyle).getValue()));
            jsonobject.add("hoverEvent", jsonobject2);
        }

        return jsonobject;
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }


    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext)
            throws JsonParseException {
        return this.deserializeChatStyle(jsonelement, type, jsondeserializationcontext);
    }

    public ChatStyle deserializeChatStyle(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        if (jsonelement.isJsonObject()) {
            ChatStyle chatStyle = new ChatStyle();
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            if (jsonobject == null) {
                return null;
            }

            if (jsonobject.has("bold")) {
                ChatStyle.access$002(chatStyle, jsonobject.get("bold").getAsBoolean());
            }

            if (jsonobject.has("italic")) {
                ChatStyle.access$102(chatStyle, jsonobject.get("italic").getAsBoolean());
            }

            if (jsonobject.has("underlined")) {
                ChatStyle.access$202(chatStyle, jsonobject.get("underlined").getAsBoolean());
            }

            if (jsonobject.has("strikethrough")) {
                ChatStyle.access$302(chatStyle, jsonobject.get("strikethrough").getAsBoolean());
            }

            if (jsonobject.has("obfuscated")) {
                ChatStyle.access$402(chatStyle, jsonobject.get("obfuscated").getAsBoolean());
            }

            if (jsonobject.has("color")) {
                ChatStyle.access$502(
                        chatStyle,
                        (EnumChatFormatting)
                                jsondeserializationcontext.deserialize(
                                        jsonobject.get("color"), EnumChatFormatting.class));
            }

            if (jsonobject.has("insertion")) {
                ChatStyle.access$602(chatStyle, jsonobject.get("insertion").getAsString());
            }

            if (jsonobject.has("clickEvent")) {
                JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
                if (jsonobject1 != null) {
                    JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
                    ClickEventAction clickEventAction =
                            jsonprimitive == null
                                    ? null
                                    : ClickEventAction.getValueByCanonicalName(
                                            jsonprimitive.getAsString());
                    JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
                    String text = jsonprimitive1 == null ? null : jsonprimitive1.getAsString();
                    if (clickEventAction != null
                            && text != null
                            && clickEventAction.shouldAllowInChat()) {
                        ChatStyle.access$702(chatStyle, new ClickEvent(clickEventAction, text));
                    }
                }
            }

            if (jsonobject.has("hoverEvent")) {
                JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
                if (jsonobject2 != null) {
                    JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
                    HoverEventAction hoverEventAction =
                            jsonprimitive2 == null
                                    ? null
                                    : HoverEventAction.getValueByCanonicalName(
                                            jsonprimitive2.getAsString());
                    ChatComponent chatComponent =
                            (ChatComponent)
                                    jsondeserializationcontext.deserialize(
                                            jsonobject2.get("value"), ChatComponent.class);
                    if (hoverEventAction != null
                            && chatComponent != null
                            && hoverEventAction.shouldAllowInChat()) {
                        ChatStyle.access$802(
                                chatStyle, new HoverEvent(hoverEventAction, chatComponent));
                    }
                }
            }

            return chatStyle;
        } else {
            return null;
        }
    }
}
