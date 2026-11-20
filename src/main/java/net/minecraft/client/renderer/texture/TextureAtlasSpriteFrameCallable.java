package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;

import java.util.concurrent.Callable;

public class TextureAtlasSpriteFrameCallable implements Callable {
    TextureAtlasSprite textureAtlasSprite;
    int[][] aFh;

    public String method_e0e3092a59fee58fae52() {
        int l = TextureManager.method_beea3e16fe3ec0995eb9();
        StringBuilder stringbuilder = new StringBuilder();
        int[][] aint = this.aFh;
        int j = aint.length;
        int k = 0;
        int i = l;

        while (k < j) {
            int[] aint1 = aint[k];
            if (stringbuilder.length() > 0) {
                stringbuilder.append(", ");
            }

            stringbuilder.append(aint1 == null ? "null" : aint1.length);
            k++;
            if (i != 0) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    public TextureAtlasSpriteFrameCallable(TextureAtlasSprite aFgValue, int[][] aint) {
        this.textureAtlasSprite = aFgValue;
        this.aFh = aint;
    }

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }


}
