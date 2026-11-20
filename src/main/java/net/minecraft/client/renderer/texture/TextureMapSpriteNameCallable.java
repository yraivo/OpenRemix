package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

import java.util.concurrent.Callable;

public class TextureMapSpriteNameCallable implements Callable {
    TextureAtlasSprite aFl;
    TextureMap aFm;

    public String getCrashDetail() {
        return this.aFl.getIconName();
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public TextureMapSpriteNameCallable(TextureMap aFmValue, TextureAtlasSprite aFlValue) {
        this.aFm = aFmValue;
        this.aFl = aFlValue;
    }
}
