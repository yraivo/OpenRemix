package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

import java.util.concurrent.Callable;

public class TextureMapSpriteSizeCallable implements Callable {
    TextureAtlasSprite aFl;
    TextureMap aFm;

    private static String c;

    static {
        c = " x ";
    }

    public TextureMapSpriteSizeCallable(TextureMap aFmValue, TextureAtlasSprite aFlValue) {
        this.aFm = aFmValue;
        this.aFl = aFlValue;
    }

    // region String decoding support

    // endregion

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return this.aFl.getIconWidth() + c + this.aFl.getIconHeight();
    }

    // region String decoding support

    // endregion
}
