package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {
    private IBakedModel defaultModel;
    private BlockModelShapes modelProvider;
    private TextureMap texMap;
    private IRegistry modelRegistry;

    public ModelManager(TextureMap aFmValue) {
        this.texMap = aFmValue;
        this.modelProvider = new BlockModelShapes(this);
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        ModelBakery modelBakery =
                new ModelBakery(iResourceManager, this.texMap, this.modelProvider);
        this.modelRegistry = modelBakery.setupModelRegistry();
        this.defaultModel = (IBakedModel) this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
        this.modelProvider.reloadModels();
    }

    public TextureMap getTextureMap() {
        return this.texMap;
    }

    public IBakedModel getModel(ModelResourceLocation modelResourceLocation) {
        if (modelResourceLocation == null) {
            return this.defaultModel;
        }

        IBakedModel object = (IBakedModel) this.modelRegistry.getObject(modelResourceLocation);
        return object == null ? this.defaultModel : object;
    }

    public BlockModelShapes getBlockModelShapes() {
        return this.modelProvider;
    }

    public IBakedModel getMissingModel() {
        return this.defaultModel;
    }
}
