package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Facing;

import java.util.List;

public class SimpleBakedModel implements IBakedModel {
    protected List generalQuads;
    protected List faceQuads;
    protected TextureAtlasSprite texture;
    protected ItemCameraTransforms cameraTransforms;
    protected boolean ambientOcclusion;
    protected boolean gui3d;

    public boolean isBuiltInRenderer() {
        return false;
    }

    public boolean isGui3d() {
        return this.gui3d;
    }

    public List getFaceQuads(Facing facing) {
        return (List) this.faceQuads.get(facing.ordinal());
    }

    public List getGeneralQuads() {
        return this.generalQuads;
    }

    public TextureAtlasSprite getTexture() {
        return this.texture;
    }

    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    public SimpleBakedModel(
            List list,
            List listValue,
            boolean flag,
            boolean flag1,
            TextureAtlasSprite aHVValue,
            ItemCameraTransforms azjValue) {
        this.generalQuads = list;
        this.faceQuads = listValue;
        this.ambientOcclusion = flag;
        this.gui3d = flag1;
        this.texture = aHVValue;
        this.cameraTransforms = azjValue;
    }

    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }
}
