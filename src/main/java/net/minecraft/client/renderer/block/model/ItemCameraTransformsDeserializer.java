package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

class ItemCameraTransformsDeserializer implements JsonDeserializer {

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_d2cbb9d5c279240e3c0a(jsonelement, type, jsondeserializationcontext);
    }


    public ItemCameraTransforms method_d2cbb9d5c279240e3c0a(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        ItemTransformVec3f azb = ItemTransformVec3f.DEFAULT;
        ItemTransformVec3f azbValue = ItemTransformVec3f.DEFAULT;
        ItemTransformVec3f azbValue2 = ItemTransformVec3f.DEFAULT;
        ItemTransformVec3f azbValue3 = ItemTransformVec3f.DEFAULT;
        if (jsonobject.has("thirdperson")) {
            azb =
                    (ItemTransformVec3f)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("thirdperson"), ItemTransformVec3f.class);
        }

        if (jsonobject.has("firstperson")) {
            azbValue =
                    (ItemTransformVec3f)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("firstperson"), ItemTransformVec3f.class);
        }

        if (jsonobject.has("head")) {
            azbValue2 =
                    (ItemTransformVec3f)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("head"), ItemTransformVec3f.class);
        }

        if (jsonobject.has("gui")) {
            azbValue3 =
                    (ItemTransformVec3f)
                            jsondeserializationcontext.deserialize(
                                    jsonobject.get("gui"), ItemTransformVec3f.class);
        }

        return new ItemCameraTransforms(azb, azbValue, azbValue2, azbValue3);
    }
}
