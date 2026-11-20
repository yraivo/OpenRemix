package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

import java.util.concurrent.Callable;

public final class GenLayerBiomeNameCallable1 implements Callable {
    BiomeGenBase biomeGenBase;

    public String method_e0e3092a59fee58fae52() {
        return String.valueOf(this.biomeGenBase);
    }

    public GenLayerBiomeNameCallable1(BiomeGenBase bMAValue) {
        this.biomeGenBase = bMAValue;
    }

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }
}
