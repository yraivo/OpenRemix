package net.minecraft.client.particle;

import net.minecraft.client.particle.EffectRenderer;

import java.util.concurrent.Callable;

public class EffectRendererLayerCallable implements Callable {
    int a;
    EffectRenderer effectRenderer;


    public EffectRendererLayerCallable(EffectRenderer awjValue, int i) {
        this.effectRenderer = awjValue;
        this.a = i;
    }

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }

    public String method_e0e3092a59fee58fae52() {
        return this.a == 0
                ? "MISC_TEXTURE"
                : (this.a == 1
                        ? "TERRAIN_TEXTURE"
                        : (this.a == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + this.a));
    }
}
