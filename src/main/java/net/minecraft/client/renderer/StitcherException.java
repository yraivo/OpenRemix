package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.StitcherHolder;

public class StitcherException extends RuntimeException {
    private StitcherHolder holder;

    public StitcherException(StitcherHolder aOvValue, String text) {
        super(text);
        this.holder = aOvValue;
    }
}
