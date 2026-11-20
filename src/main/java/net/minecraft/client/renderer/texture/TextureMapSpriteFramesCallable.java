package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

import java.util.concurrent.Callable;

public class TextureMapSpriteFramesCallable implements Callable {
    private static String c;

    TextureAtlasSprite aFl;
    TextureMap aFm;

    public TextureMapSpriteFramesCallable(TextureMap aFmValue, TextureAtlasSprite aFlValue) {
        this.aFm = aFmValue;
        this.aFl = aFlValue;
    }

    public String getCrashDetail() {
        return this.aFl.getFrameCount() + c;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    // region String decoding support

    // endregion

    static {
        c = " frames";
    }

    // region String decoding support

    // endregion
}
