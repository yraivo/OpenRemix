package net.minecraft.world.biome;

public class BiomeGenBaseHeight {
    public float rootHeight;
    public float variation;

    public BiomeGenBaseHeight attenuate() {
        return new net.minecraft.world.biome.BiomeGenBaseHeight(this.rootHeight * 0.8F, this.variation * 0.6F);
    }

    public BiomeGenBaseHeight(float rootHeightValue, float variationValue) {
        this.rootHeight = rootHeightValue;
        this.variation = variationValue;
    }
}
