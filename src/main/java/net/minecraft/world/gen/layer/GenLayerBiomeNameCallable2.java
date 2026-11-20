package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

import java.util.concurrent.Callable;

public final class GenLayerBiomeNameCallable2 implements Callable {
    BiomeGenBase biomeGenBase;

    public GenLayerBiomeNameCallable2(BiomeGenBase bMAValue) {
        this.biomeGenBase = bMAValue;
    }

    public String method_e0e3092a59fee58fae52() {
        return String.valueOf(this.biomeGenBase);
    }

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }
}
