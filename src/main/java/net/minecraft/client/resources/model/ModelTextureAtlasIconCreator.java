package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.Set;

class ModelTextureAtlasIconCreator implements IIconCreator {
    ModelBakery field_177060_b;
    Set field_177061_a;

    public void registerSprites(TextureMap textureMap) {
        int j = ModelResourceLocation.aW();
        Iterator iterator = this.field_177061_a.iterator();
        int i = j;

        while (iterator.hasNext()) {
            ResourceLocation resourceLocation = (ResourceLocation) iterator.next();
            TextureAtlasSprite textureAtlasSprite =
                    textureMap.registerSprite(resourceLocation);
            ModelBakery.access$000(this.field_177060_b).put(resourceLocation, textureAtlasSprite);
            if (i != 0) {
                break;
            }
        }
    }

    ModelTextureAtlasIconCreator(ModelBakery aHUValue, Set set) {
        this.field_177060_b = aHUValue;
        this.field_177061_a = set;
    }
}
