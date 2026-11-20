package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandom;

import java.util.List;

public class WeightedBakedModel implements IBakedModel {
    private int totalWeight;
    private IBakedModel baseModel;
    private List models;

    public boolean isBuiltInRenderer() {
        return this.baseModel.isBuiltInRenderer();
    }

    public WeightedBakedModel(List modelsValue) {
        this.models = modelsValue;
        this.totalWeight = WeightedRandom.getTotalWeight(modelsValue);
        this.baseModel = ((WeightedBakedModelMyWeighedRandomItem) modelsValue.get(0)).model;
    }

    public ItemCameraTransforms getItemCameraTransforms() {
        return this.baseModel.getItemCameraTransforms();
    }

    public TextureAtlasSprite getTexture() {
        return this.baseModel.getTexture();
    }

    public List getFaceQuads(Facing facing) {
        return this.baseModel.getFaceQuads(facing);
    }

    public boolean isAmbientOcclusion() {
        return this.baseModel.isAmbientOcclusion();
    }

    public IBakedModel getAlternativeModel(long i) {
        return ((WeightedBakedModelMyWeighedRandomItem)
                        WeightedRandom.getRandomItem(
                                this.models, Math.abs((int) i >> 16) % this.totalWeight))
                .model;
    }

    public List getGeneralQuads() {
        return this.baseModel.getGeneralQuads();
    }

    public boolean isGui3d() {
        return this.baseModel.isGui3d();
    }
}
