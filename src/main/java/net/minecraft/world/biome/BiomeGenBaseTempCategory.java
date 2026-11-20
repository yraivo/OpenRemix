package net.minecraft.world.biome;

public enum BiomeGenBaseTempCategory {
    OCEAN("OCEAN", 0),
    COLD("COLD", 1),
    MEDIUM("MEDIUM", 2),
    WARM("WARM", 3);

    private static BiomeGenBaseTempCategory[] aik;

    BiomeGenBaseTempCategory(String text, int j) {}

    // region String decoding support

    // endregion

    static {
        aik =
                new BiomeGenBaseTempCategory[] {
                    net.minecraft.world.biome.BiomeGenBaseTempCategory.OCEAN,
                    net.minecraft.world.biome.BiomeGenBaseTempCategory.COLD,
                    net.minecraft.world.biome.BiomeGenBaseTempCategory.MEDIUM,
                    net.minecraft.world.biome.BiomeGenBaseTempCategory.WARM
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
