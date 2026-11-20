package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Facing;

import java.util.List;

public interface IBakedModel {
    TextureAtlasSprite getTexture();

    ItemCameraTransforms getItemCameraTransforms();

    boolean isBuiltInRenderer();

    List getGeneralQuads();

    boolean isGui3d();

    List getFaceQuads(Facing facing);

    boolean isAmbientOcclusion();
}
