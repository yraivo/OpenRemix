package net.minecraft.client.particle;

import java.util.concurrent.Callable;

class ParticleTextureCrashDetail implements Callable {
    EffectRenderer field_147897_b;
    int field_147898_a;

    ParticleTextureCrashDetail(EffectRenderer awjValue, int i) {
        this.field_147897_b = awjValue;
        this.field_147898_a = i;
    }


    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return this.field_147898_a == 0
                ? "MISC_TEXTURE"
                : (this.field_147898_a == 1
                        ? "TERRAIN_TEXTURE"
                        : (this.field_147898_a == 3
                                ? "ENTITY_PARTICLE_TEXTURE"
                                : "Unknown - " + this.field_147898_a));
    }
}
