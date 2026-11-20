package net.minecraft.client.renderer.block.model;

import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ModelBlockDefinitionVariantDeserializer implements JsonDeserializer {

    private static String[] packedTextFragments;

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    protected String parseModel(JsonObject jsonobject) {
        return JsonUtils.getString(jsonobject, "model");
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_5bc1e15ecd1523335048(jsonelement, type, jsondeserializationcontext);
    }

    protected int parseWeight(JsonObject jsonobject) {
        return JsonUtils.getInt(jsonobject, "weight", 1);
    }

    private ResourceLocation makeModelLocation(String text) {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        return new ResourceLocation(
                resourceLocation.getResourceDomain(),
                "block/" + resourceLocation.getResourcePath());
    }

    private boolean parseUvLock(JsonObject jsonobject) {
        return JsonUtils.getBoolean(jsonobject, "uvlock", false);
    }

    protected ModelRotation parseRotation(JsonObject jsonobject) {
        int i = JsonUtils.getInt(jsonobject, packedTextFragments[0], 0);
        int j = JsonUtils.getInt(jsonobject, packedTextFragments[1], 0);
        ModelRotation modelRotation = ModelRotation.getModelRotation(i, j);

        try {
            if (modelRotation == null) {
                throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
            } else {
                return modelRotation;
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }
    }

    public ModelBlockDefinitionVariant method_5bc1e15ecd1523335048(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        String text = this.parseModel(jsonobject);
        ModelRotation modelRotation = this.parseRotation(jsonobject);
        boolean flag = this.parseUvLock(jsonobject);
        int i = this.parseWeight(jsonobject);
        return new ModelBlockDefinitionVariant(this.makeModelLocation(text), modelRotation, flag, i);
    }

    static {
        net.minecraft.client.renderer.block.model.ModelBlockDefinitionVariantDeserializer.packedTextFragments = new String[4];
        net.minecraft.client.renderer.block.model.ModelBlockDefinitionVariantDeserializer.packedTextFragments[0] = new String("x");
        net.minecraft.client.renderer.block.model.ModelBlockDefinitionVariantDeserializer.packedTextFragments[1] = new String("y");
    }
}
