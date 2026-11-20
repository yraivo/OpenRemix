package net.minecraft.client.particle;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;

import java.util.concurrent.Callable;

public class EffectRendererParticleCallable implements Callable {
    EffectRenderer effectRenderer;
    EntityFX entityFX;

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }

    public EffectRendererParticleCallable(EffectRenderer awjValue, EntityFX awiValue) {
        this.effectRenderer = awjValue;
        this.entityFX = awiValue;
    }

    public String method_e0e3092a59fee58fae52() {
        return this.entityFX.toString();
    }
}
