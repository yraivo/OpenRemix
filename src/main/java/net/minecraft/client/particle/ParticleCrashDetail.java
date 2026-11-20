package net.minecraft.client.particle;

import java.util.concurrent.Callable;

class ParticleCrashDetail implements Callable {
    EntityFX field_147214_a;
    EffectRenderer field_147213_b;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    ParticleCrashDetail(EffectRenderer awjValue, EntityFX awiValue) {
        this.field_147213_b = awjValue;
        this.field_147214_a = awiValue;
    }

    public String getCrashDetail() {
        return this.field_147214_a.toString();
    }
}
