package net.minecraft.world;

public class WorldProviderSurface extends WorldProvider {
    private static String[] packedTextFragments;
    private static String vI;

    static {
        net.minecraft.world.WorldProviderSurface.packedTextFragments = new String[2];
        net.minecraft.world.WorldProviderSurface.packedTextFragments[0] = new String("");
        net.minecraft.world.WorldProviderSurface.packedTextFragments[1] = new String("1j/\u0015~f:\u0012x");
        vI = "Overworld";
    }

    // region String decoding support

    // endregion

    public String getInternalNameSuffix() {
        return packedTextFragments[0];
    }

    public String getDimensionName() {
        return vI;
    }

    // region String decoding support

    // endregion
}
