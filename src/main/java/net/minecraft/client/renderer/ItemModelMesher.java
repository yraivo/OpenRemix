package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Map.Entry;

public class ItemModelMesher {
    private Map simpleShapesCache;
    private final Map simpleShapes = Maps.newHashMap();
    private Map shapers;
    private ModelManager modelManager;

    public void register(Item item, int i, ModelResourceLocation modelResourceLocation) {
        this.simpleShapes.put(this.getIndex(item, i), modelResourceLocation);
        this.simpleShapesCache.put(
                this.getIndex(item, i), this.modelManager.getModel(modelResourceLocation));
    }

    public ItemModelMesher(ModelManager aASValue) {
        this.simpleShapesCache = Maps.newHashMap();
        this.shapers = Maps.newHashMap();
        this.modelManager = aASValue;
    }

    public void register(Item item, ItemMeshDefinition itemMeshDefinition) {
        this.shapers.put(item, itemMeshDefinition);
    }

    public TextureAtlasSprite getParticleIcon(Item item) {
        return this.getParticleIcon(item, 0);
    }

    public void rebuildCache() {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        this.simpleShapesCache.clear();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_1819 =
                        (this.simpleShapes.entrySet()).iterator();
                (__recoveryIterator171_1819).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1819).next();
            this.simpleShapesCache.put(
                    entry.getKey(),
                    this.modelManager.getModel((ModelResourceLocation) entry.getValue()));
            if (i == 0) {
                break;
            }
        }
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    protected int getMetadata(ItemStack itemStack) {
        return itemStack.isItemStackDamageable() ? 0 : itemStack.getItemDamage();
    }

    protected IBakedModel getItemModel(Item item, int i) {
        return (IBakedModel) this.simpleShapesCache.get(this.getIndex(item, i));
    }

    public IBakedModel getItemModel(ItemStack itemStack) {
        Item item = itemStack.getItem();
        IBakedModel itemModel = this.getItemModel(item, this.getMetadata(itemStack));
        if (itemModel == null) {
            ItemMeshDefinition itemMeshDefinition = (ItemMeshDefinition) this.shapers.get(item);
            if (itemMeshDefinition != null) {
                itemModel =
                        this.modelManager.getModel(itemMeshDefinition.getModelLocation(itemStack));
            }
        }

        if (itemModel == null) {
            itemModel = this.modelManager.getMissingModel();
        }

        return itemModel;
    }

    public TextureAtlasSprite getParticleIcon(Item item, int i) {
        return this.getItemModel(new ItemStack(item, 1, i)).getTexture();
    }

    private int getIndex(Item item, int i) {
        return Item.getIdFromItem(item) << 16 | i;
    }
}
