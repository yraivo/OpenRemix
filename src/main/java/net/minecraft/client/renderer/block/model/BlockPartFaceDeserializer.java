package net.minecraft.client.renderer.block.model;

import net.minecraft.util.Facing;
import net.minecraft.util.JsonUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

class BlockPartFaceDeserializer implements JsonDeserializer {

    private static String[] packedTextFragments;

    private Facing parseCullFace(JsonObject jsonobject) {
        String text =
                JsonUtils.getString(
                        jsonobject, "cullface", packedTextFragments[0]);
        return Facing.byName(text);
    }

    public BlockPartFace method_fa9069d8ddb49dde2f90(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        Facing facing = this.parseCullFace(jsonobject);
        int i = this.parseTintIndex(jsonobject);
        String text = this.parseTexture(jsonobject);
        BlockFaceUV blockFaceUV =
                (BlockFaceUV)
                        jsondeserializationcontext.deserialize(
                                jsonobject, BlockFaceUV.class);
        return new BlockPartFace(facing, i, text, blockFaceUV);
    }

    private String parseTexture(JsonObject jsonobject) {
        return JsonUtils.getString(jsonobject, "texture");
    }

    static {
        net.minecraft.client.renderer.block.model.BlockPartFaceDeserializer.packedTextFragments = new String[2];
        net.minecraft.client.renderer.block.model.BlockPartFaceDeserializer.packedTextFragments[0] = new String("");
    }

    public Object deserialize(
            JsonElement jsonelement,
            Type type,
            JsonDeserializationContext jsondeserializationcontext) {
        return this.method_fa9069d8ddb49dde2f90(jsonelement, type, jsondeserializationcontext);
    }

    protected int parseTintIndex(JsonObject jsonobject) {
        return JsonUtils.getInt(jsonobject, "tintindex", -1);
    }
}
