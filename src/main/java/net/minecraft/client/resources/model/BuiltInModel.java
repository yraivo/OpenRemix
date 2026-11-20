package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Facing;

import java.util.List;

public class BuiltInModel implements IBakedModel {
    private ItemCameraTransforms cameraTransforms;

    public boolean isAmbientOcclusion() {
        return false;
    }

    public boolean isBuiltInRenderer() {
        return true;
    }

    public List getFaceQuads(Facing facing) {
        return null;
    }

    public TextureAtlasSprite getTexture() {
        return null;
    }

    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }

    public List getGeneralQuads() {
        return null;
    }

    public BuiltInModel(ItemCameraTransforms ayUValue) {
        this.cameraTransforms = ayUValue;
    }

    public boolean isGui3d() {
        return true;
    }
}
